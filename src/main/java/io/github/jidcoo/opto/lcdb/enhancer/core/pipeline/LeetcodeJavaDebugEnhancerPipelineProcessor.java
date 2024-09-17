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
import io.github.jidcoo.opto.lcdb.enhancer.base.InputProvider;
import io.github.jidcoo.opto.lcdb.enhancer.base.OutputConsumer;
import io.github.jidcoo.opto.lcdb.enhancer.core.executor.LeetcodeExecutorFactory;
import io.github.jidcoo.opto.lcdb.enhancer.core.executor.LeetcodeInvokerFactory;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.IOFactory;
import io.github.jidcoo.opto.lcdb.enhancer.core.parser.InputParserFactory;
import io.github.jidcoo.opto.lcdb.enhancer.core.printer.OutputPrinterFactory;
import io.github.jidcoo.opto.lcdb.enhancer.core.proxy.DebugEnhancerProxy;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ReflectUtil;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <p>LeetcodeJavaDebugEnhancerPipelineProcessor is a publicly
 * available LeetcodeJavaDebugEnhancerPipeline processor.
 * It has used a proxy to access {@link LeetcodeJavaDebugEnhancerPipeline}.
 * </p>
 *
 * <p>The main {@link #process(LeetcodeJavaDebugEnhancer)} method
 * of LeetcodeJavaDebugEnhancerPipelineProcessor is to proxy
 * external callers to execute {@link LeetcodeJavaDebugEnhancerPipeline}.
 * </p>
 *
 * <p>All features of the {@link LeetcodeJavaDebugEnhancer} will
 * be controlled and dominated by this processor in version
 * <tt>1.0.1</tt> and later. </p>
 *
 * @author Jidcoo
 * @since 1.0.1
 */
public final class LeetcodeJavaDebugEnhancerPipelineProcessor {

    /**
     * Do starting real enhancement pipeline with
     * LeetcodeJavaDebugEnhancer instance.
     *
     * @param enhancer the LeetcodeJavaDebugEnhancer instance.
     */
    public static void process(LeetcodeJavaDebugEnhancer enhancer) throws Exception, Error {
        // Check NPE.
        AssertUtil.nonNull(enhancer, "The enhancer cannot be null.");
        // Create a LeetcodeExecutor from the enhancer.
        Object leetcodeExecutor = createBootstrapLeetcodeExecutor(enhancer);
        // Create an OutputPrinter from the enhancer.
        Object outputPrinter = OutputPrinterFactory.getOutputPrinter(enhancer);
        // Create a InputParser from the enhancer.
        Object inputParser = InputParserFactory.getInputParser(enhancer);

        try (
                // Get or create a InputProvider from the enhancer.
                InputProvider inputProvider = IOFactory.getInputProvider(enhancer);
                // Get or create OutputConsumer from the enhancer.
                OutputConsumer outputConsumer = IOFactory.getOutputConsumer(enhancer)

        ) {
            // Create a pipeline instance by enhancer's components.
            LeetcodeJavaDebugEnhancerPipeline pipeline = new LeetcodeJavaDebugEnhancerPipeline(enhancer,
                    inputProvider, outputConsumer, outputPrinter, leetcodeExecutor, inputParser);
            // Run pipeline.
            pipeline.run();
        }

    }

    /**
     * Create a bootstrap leetcode executor instance by enhancer.
     *
     * @param enhancer the LeetcodeJavaDebugEnhancer instance.
     * @return the leetcode executor instance.
     */
    private static Object createBootstrapLeetcodeExecutor(LeetcodeJavaDebugEnhancer enhancer) {
        // At first, if the enhancement point from the enhancer is not null,
        // we will prioritize using it.
        final LeetcodeJavaDebugEnhancer __enhancer__ = enhancer;
        enhancer = DebugEnhancerProxy.awareSource(__enhancer__);
        Method enhancementPoint;
        if (Objects.nonNull((enhancementPoint = __enhancer__.getEnhancementPoint()))) {
            return LeetcodeExecutorFactory.getLeetcodeExecutor(enhancer,
                    LeetcodeInvokerFactory.getLeetcodeInvoker(enhancementPoint));
        }

        // Then, we will try to resolve all first level INNER-CLASS in AT.
        Class<?>[] innerClasses = ReflectUtil.resolveInnerClasses(enhancer.getClass());
        // The innerClasses length must be greater than zero.
        AssertUtil.isTrue(innerClasses.length > 0, "Cannot resolve any inner class from the AT instance.");
        // Unfortunately, we are currently unable to handle situations where
        // there are multiple INNER-CLASS in AT.
        AssertUtil.isTrue(innerClasses.length < 2, "Multiple inner classes were found in AT instance. There can only be one inner class in AT instance.");
        // Use this only inner class as an instance of leetcode executor.
        Class<?> bossInnerClassInstance = innerClasses[0];
        // If the name of bossInnerClassInstance is "Solution",
        // then we only need to instantiate the class.
        if ("Solution".equals(bossInnerClassInstance.getSimpleName())) {
            return LeetcodeExecutorFactory.getLeetcodeExecutor(ReflectUtil.createInstance(bossInnerClassInstance,
                    new Class[]{enhancer.getClass()}, enhancer));
        }
        // Create a leetcode executor by bossInnerClassInstance and return it.
        return LeetcodeExecutorFactory.getLeetcodeExecutor(bossInnerClassInstance);
    }
}