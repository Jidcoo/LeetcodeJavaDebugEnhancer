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
import io.github.jidcoo.opto.lcdb.enhancer.base.*;
import io.github.jidcoo.opto.lcdb.enhancer.core.executor.LeetcodeExecutorFactory;
import io.github.jidcoo.opto.lcdb.enhancer.core.executor.LeetcodeExecutorProcessor;
import io.github.jidcoo.opto.lcdb.enhancer.core.executor.LeetcodeInvokerFactory;
import io.github.jidcoo.opto.lcdb.enhancer.core.parser.InputParserProcessor;
import io.github.jidcoo.opto.lcdb.enhancer.core.printer.OutputPrinterProcessor;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.BeanUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ReflectUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>LeetcodeJavaDebugEnhancerPipeline is a pipeline
 * used to run enhanced pipeline processes.</p>
 * <p>In order to adapt to complex scenarios,
 * LeetcodeJavaDebugEnhancerPipeline takes on
 * more proactive enhancement work here.</p>
 *
 * @author Jidcoo
 * @since 1.0.1
 */
final class LeetcodeJavaDebugEnhancerPipeline extends PipelineRunner {

    /**
     * The enhancer instance of this pipeline.
     */
    private final LeetcodeJavaDebugEnhancer leetcodeJavaDebugEnhancer;

    /**
     * The input provider instance of this pipeline.
     */
    private final InputProvider inputProvider;

    /**
     * The output consumer instance of this pipeline.
     */
    private final OutputConsumer outputConsumer;

    /**
     * The output printer instance of this pipeline.
     */
    private final Object outputPrinter;

    /**
     * The leetcode executor instance of this pipeline.
     */
    private final Object leetcodeExecutor;

    /**
     * The input parser instance of this pipeline.
     */
    private final Object inputParser;

    /**
     * The builtin pipeline runner map.
     */
    private final Map<Integer, PipelineRunner> pipelineRunnerMap;

    /**
     * The pipeline runner invokers from the builtin pipeline runner.
     */
    private final List<LeetcodeInvoker> pipelineRunnerInvokers;

    /**
     * Built-in pipeline runner set package location.
     */
    private static final String BUILT_IN_PIPELINE_RUNNER_PACKAGE = "io.github.jidcoo.opto.lcdb.enhancer.core.pipeline";

    /**
     * Create a LeetcodeJavaDebugEnhancerPipeline instance.
     *
     * @param leetcodeJavaDebugEnhancer the enhancer instance.
     * @param inputProvider             the input provider.
     * @param outputConsumer            the output consumer.
     * @param outputPrinter             the output printer instance.
     * @param leetcodeExecutor          the bootstrap leetcode executor instance.
     * @param inputParser               the input parser instance.
     */
    LeetcodeJavaDebugEnhancerPipeline(LeetcodeJavaDebugEnhancer leetcodeJavaDebugEnhancer,
                                      InputProvider inputProvider, OutputConsumer outputConsumer,
                                      Object outputPrinter, Object leetcodeExecutor, Object inputParser) {
        AssertUtil.nonNull(leetcodeJavaDebugEnhancer, "The enhancer cannot be null.");
        this.leetcodeJavaDebugEnhancer = leetcodeJavaDebugEnhancer;
        AssertUtil.nonNull(inputProvider, "The input provider cannot be null.");
        this.inputProvider = inputProvider;
        AssertUtil.nonNull(outputConsumer, "The output consumer cannot be null.");
        this.outputConsumer = outputConsumer;
        AssertUtil.nonNull(outputPrinter, "The output printer cannot be null.");
        this.outputPrinter = outputPrinter;
        AssertUtil.nonNull(leetcodeExecutor, "The leetcode executor cannot be null.");
        this.leetcodeExecutor = leetcodeExecutor;
        AssertUtil.nonNull(inputParser, "The input parser cannot be null.");
        this.inputParser = inputParser;

        // Build up runner map and invokers list.
        pipelineRunnerMap = new HashMap<>();
        pipelineRunnerInvokers = new ArrayList<>();
        // Collect all builtin pipeline runners instance and sort it.
        List<PipelineRunner> builtinPipelineRunners = BeanUtil.collectBeans(PipelineRunner.class,
                BUILT_IN_PIPELINE_RUNNER_PACKAGE,
                klass -> klass.isAnnotationPresent(Require.class) && ReflectUtil.isExtendsClass(klass,
                        PipelineRunner.class) && !Modifier.isAbstract(klass.getModifiers()),
                klass -> ReflectUtil.createInstance(klass))
                .stream().filter(Objects::nonNull).sorted(Comparator.comparingInt(Order::getOrder).reversed())
                .collect(Collectors.toList());
        // Dispatch each builtinPipelineRunner into the pipelineRunnerMap.
        for (PipelineRunner builtinPipelineRunner : builtinPipelineRunners) {
            // Get all candidate methods from the builtinPipelineRunner.
            for (Method candidateMethod : builtinPipelineRunner.getClass().getDeclaredMethods()) {
                // Filter out methods without @Require annotation.
                if (!candidateMethod.isAnnotationPresent(Require.class)) {
                    continue;
                }
                // Create a LeetcodeInvoker instance by the candidateMethod.
                LeetcodeInvoker leetcodeInvoker = LeetcodeInvokerFactory.getLeetcodeInvoker(candidateMethod);
                // Add leetcodeInvoker to pipelineRunnerInvokers list.
                pipelineRunnerInvokers.add(leetcodeInvoker);
                // Map leetcodeInvoker's id -> pipelineRunner owner.
                pipelineRunnerMap.put(leetcodeInvoker.getId(), builtinPipelineRunner);
            }
        }
    }

    /**
     * Run this pipeline.
     */
    void run() {
        // Now we can happily run the io loop to perform leetcode debugging enhancements.
        while (true) {
            // Provide the next string input from the InputProvider.
            String input = inputProvider.provideNextInput();
            // We need to break this loop when the input indicates end.
            if (inputProvider.isEnd(input)) {
                break;
            }
            Object bossLeetcodeExecutor = leetcodeExecutor;
            // Do enhance before input parsing.
            bossLeetcodeExecutor = doEnhanceBeforeInputParseProcess(bossLeetcodeExecutor);
            // Parse the string input to input object.
            Object inputObject = InputParserProcessor.process(inputParser, bossLeetcodeExecutor, input);
            // Do enhance after input parsing.
            bossLeetcodeExecutor = doEnhanceAfterInputParseProcess(bossLeetcodeExecutor);
            // Execute leetcode target and get the output object.
            Object outputObject = LeetcodeExecutorProcessor.process(bossLeetcodeExecutor, inputObject);
            // Print the output object.
            String output = OutputPrinterProcessor.process(outputPrinter, bossLeetcodeExecutor, outputObject);
            // Consume the next output string to the OutputConsumer.
            outputConsumer.consumeNextOutput(output);
        }
    }

    /**
     * Do enhance before input parse process.
     * In this function, we will add all pipeline runner
     * invokers as additional candidate invokers to
     * leetcodeExecutor.
     *
     * @param bossLeetcodeExecutor the leetcode executor instance.
     * @return the final leetcode executor.
     */
    private Object doEnhanceBeforeInputParseProcess(Object bossLeetcodeExecutor) {
        // Aware candidate leetcode invoker list from the bossLeetcodeExecutor.
        List<LeetcodeInvoker> candidateInvokers = ReflectUtil.getFieldValue("candidateInvokers", List.class,
                bossLeetcodeExecutor);
        // Add all builtin pipeline runner invokers as additional candidate invokers.
        if (Objects.nonNull(candidateInvokers)) {
            candidateInvokers.addAll(pipelineRunnerInvokers);
        }
        // Just return the origin bossLeetcodeExecutor.
        return bossLeetcodeExecutor;
    }

    /**
     * Do enhance after input parse process.
     * In this function, we will make a decision on
     * a suitable leetcode executor as the final
     * leetcode executor.
     *
     * @param bossLeetcodeExecutor the leetcode executor instance.
     * @return the final leetcode executor.
     */
    private Object doEnhanceAfterInputParseProcess(Object bossLeetcodeExecutor) {
        // Aware leetcode invoker from the bossLeetcodeExecutor.
        LeetcodeInvoker leetcodeInvoker = ReflectUtil.getFieldValue("executor", LeetcodeInvoker.class,
                bossLeetcodeExecutor);
        if (!pipelineRunnerMap.containsKey(leetcodeInvoker.getId())) {
            // If this current leetcodeInvoker is not a pipeline runner invoker,
            // then we consider the bossLeetcodeExecutor to be the final leetcode executor.
            // So just return it.
            return bossLeetcodeExecutor;
        }

        // Get pipeline runner instance by invoker id.
        PipelineRunner pipelineRunner = pipelineRunnerMap.get(leetcodeInvoker.getId());
        // Set up the pipelineRunner.
        setupPipelineRunner(pipelineRunner);
        // Create a new leetcode executor as the final leetcode executor.
        Object finalLeetcodeExecutor = LeetcodeExecutorFactory.getLeetcodeExecutor(pipelineRunner, leetcodeInvoker);
        // Set the bossLeetcodeExecutor's invoker to null.
        ReflectUtil.setFieldValue("executor", LeetcodeInvoker.class, null, bossLeetcodeExecutor);
        // Return the final leetcode executor.
        return finalLeetcodeExecutor;
    }

    /**
     * Set up pipeline runner with this current pipeline instance.
     *
     * @param pipelineRunner the pipeline runner.
     */
    private void setupPipelineRunner(PipelineRunner pipelineRunner) {
        // Set cur pipeline instance to the pipelineRunner.
        ReflectUtil.setFieldValue("baseRunner", PipelineRunner.class, this, pipelineRunner);
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
     * Get enhancer of this pipeline.
     *
     * @return enhancer.
     */
    protected LeetcodeJavaDebugEnhancer getEnhancer() {
        return leetcodeJavaDebugEnhancer;
    }

    /**
     * Get input provider of this pipeline.
     *
     * @return input provider.
     */
    protected InputProvider getInputProvider() {
        return inputProvider;
    }

    /**
     * Get output consumer of this pipeline.
     *
     * @return output consumer.
     */
    protected OutputConsumer getOutputConsumer() {
        return outputConsumer;
    }

    /**
     * Get output printer of this pipeline.
     *
     * @return output printer instance.
     */
    protected Object getOutputPrinter() {
        return outputPrinter;
    }

    /**
     * Get leetcode executor of this pipeline.
     *
     * @return leetcode executor instance.
     */
    protected Object getLeetcodeExecutor() {
        return leetcodeExecutor;
    }

    /**
     * Get input parser of this pipeline runner.
     *
     * @return input parser instance.
     */
    protected Object getInputParser() {
        return inputParser;
    }
}