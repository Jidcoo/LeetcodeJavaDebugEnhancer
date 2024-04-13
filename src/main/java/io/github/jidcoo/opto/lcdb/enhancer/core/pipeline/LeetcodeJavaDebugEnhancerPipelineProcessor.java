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
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;

/**
 * <p>LeetcodeJavaDebugEnhancerPipelineProcessor is a publicly 
 * available LeetcodeJavaDebugEnhancerPipeline processor. 
 * It has used a proxy to access {@link LeetcodeJavaDebugEnhancerPipeline}.
 * </p>
 *
 * <p>The main {@link #process(Object)} method
 * of LeetcodeJavaDebugEnhancerPipelineProcessor is to proxy 
 * external callers to execute {@link LeetcodeJavaDebugEnhancerPipeline}.
 * </p>
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
    public static void process(LeetcodeJavaDebugEnhancer enhancer) {
        // Check NPE.
        AssertUtil.nonNull(enhancer,"The enhancer cannot be null.");
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
            LeetcodeJavaDebugEnhancerPipeline pipeline = new LeetcodeJavaDebugEnhancerPipeline(enhancer, inputProvider, outputConsumer, outputPrinter, leetcodeExecutor, inputParser);
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

        return null;
    }
}