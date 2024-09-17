/*
 * Copyright (C) 2024-2026 Jidcoo(https://github.com/jidcoo).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.jidcoo.opto.lcdb.enhancer.core.pipeline;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.base.LeetcodeInvoker;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.core.executor.LeetcodeExecutorFactory;
import io.github.jidcoo.opto.lcdb.enhancer.core.executor.LeetcodeExecutorProcessor;
import io.github.jidcoo.opto.lcdb.enhancer.core.executor.LeetcodeInvokerFactory;
import io.github.jidcoo.opto.lcdb.enhancer.core.parser.InputParserProcessor;
import io.github.jidcoo.opto.lcdb.enhancer.core.proxy.DebugEnhancerProxy;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ContainerCheckUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ReflectUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * DataStructureDesignScenePipelineRunner is a
 * {@link PipelineRunner} used to support
 * leetcode data structure design questions.
 *
 * @author Jidcoo
 * @since 1.0.1
 */
@Require
final class DataStructureDesignScenePipelineRunner extends PipelineRunner {

    /**
     * Process on data structure design scene question.
     *
     * @param operations operation list.
     * @param data data list.
     * @return question result.
     */
    @Require
    List<Object> processOnDataStructureDesignScene(List<String> operations, List<List<Object>> data) {
        AssertUtil.isTrue(!ContainerCheckUtil.isListEmpty(operations), "The operation list cannot be empty.");
        AssertUtil.isTrue(!ContainerCheckUtil.isListEmpty(data), "The data list cannot be empty.");
        AssertUtil.isTrue(operations.size() == data.size(), "The size of lists operation and data is not equal.");
        // Aware inner-class from leetcode executor.
        Class<?> dataStructureKlass = (Class<?>) ReflectUtil.getFieldValue("instance", Object.class,
                getLeetcodeExecutor());
        AssertUtil.nonNull(dataStructureKlass, "The data structure class instance cannot be null.");
        // Collect all constructors and method from dataStructureKlass.
        List<LeetcodeInvoker> constructorInvokers = Arrays.stream(dataStructureKlass.getDeclaredConstructors())
                .map(LeetcodeInvokerFactory::getLeetcodeInvoker)
                 // Enable Friendly-Matching-Mode to ConstructorLeetcodeInvoker instance.
                .peek(leetcodeInvoker -> ReflectUtil.setFieldValue("matchingFriendly", boolean.class, Boolean.TRUE, leetcodeInvoker))
                .collect(Collectors.toList());
        List<LeetcodeInvoker> methodInvokers = Arrays.stream(dataStructureKlass.getDeclaredMethods())
                .map(LeetcodeInvokerFactory::getLeetcodeInvoker)
                .collect(Collectors.toList());
        // Map invokerName -> List<LeetcodeInvoker>
        Map<String, List<LeetcodeInvoker>> invokersMap = Stream.concat(constructorInvokers.stream(),
                methodInvokers.stream()).collect(Collectors.groupingBy(LeetcodeInvoker::getInvokerName));

        List<Object> invokerReturnCollector = new ArrayList<>();
        Object dataStructureInstance = null;
        // Run operations stream.
        for (int idx = 0; idx < operations.size(); idx++) {
            String operation = operations.get(idx);
            List<Object> input = data.get(idx);
            // Get candidate leetcode invokers by cur operation.
            List<LeetcodeInvoker> leetcodeInvokers = invokersMap.get(operation);
            AssertUtil.isTrue(!ContainerCheckUtil.isListEmpty(leetcodeInvokers),
                    "Cannot find any candidate leetcode invoker by operation: " + operation);
            // Create a LeetcodeExecutor instance.
            Object leetcodeExecutor = LeetcodeExecutorFactory.getLeetcodeExecutor(idx == 0 ? getEnhancer()
                            : dataStructureInstance,
                    leetcodeInvokers.stream().toArray(LeetcodeInvoker[]::new));
            // Parse input.
            Object inputObject = InputParserProcessor.process(getInputParser(), leetcodeExecutor, input);
            // Process leetcode exec.
            Object outputObject = LeetcodeExecutorProcessor.process(leetcodeExecutor, inputObject);
            // Collect leetcode exec return object.
            if (idx == 0) {
                dataStructureInstance = outputObject;
                // Ignore the data structure instance return base on Leetcode output style.
                invokerReturnCollector.add(null);
            } else {
                invokerReturnCollector.add(outputObject);
            }
        }

        return invokerReturnCollector;
    }

    /**
     * Get the order of the object.
     *
     * @return the int order of the object.
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * Get enhancer of this pipeline runner.
     *
     * @return enhancer.
     */
    @Override
    protected LeetcodeJavaDebugEnhancer getEnhancer() {
        // Return the origin enhancer.
        return DebugEnhancerProxy.awareSource(super.getEnhancer());
    }
}