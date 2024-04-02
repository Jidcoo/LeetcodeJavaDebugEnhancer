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

package io.github.jidcoo.opto.lcdb.enhancer.core;

import io.github.jidcoo.opto.lcdb.enhancer.core.parser.InputParserProcessor;
import io.github.jidcoo.opto.lcdb.enhancer.utils.EnhancerLogUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ReflectUtil;
import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.base.InputProvider;
import io.github.jidcoo.opto.lcdb.enhancer.base.OutputConsumer;
import io.github.jidcoo.opto.lcdb.enhancer.core.executor.LeetcodeExecutorFactory;
import io.github.jidcoo.opto.lcdb.enhancer.core.executor.LeetcodeExecutorProcessor;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.IOFactory;
import io.github.jidcoo.opto.lcdb.enhancer.core.parser.InputParserFactory;
import io.github.jidcoo.opto.lcdb.enhancer.core.printer.OutputPrinterFactory;
import io.github.jidcoo.opto.lcdb.enhancer.core.printer.OutputPrinterProcessor;

/**
 * <p>LeetcodeJavaDebugEnhanceProcessor is a primary enhancer.</p>
 * <p>All features of the {@link LeetcodeJavaDebugEnhancer} will
 * be controlled and dominated by this processor. </p>
 *
 * <p>By the way, today is a so bad day for me. Because I
 * still need coding on my day off. Can you give me
 * a great star to encourage me?
 * </p>
 *
 * @author Jidcoo
 * @since 1.0
 */
public class LeetcodeJavaDebugEnhanceProcessor {

    /**
     * <p>Do leetcode debugging enhance process with an <tt>AT</tt> class.</p>
     * <p>I don't want to say much, please take a look at the code.</p>
     *
     * @param AT the <tt>AT</tt> class.
     */
    public static void process(Class<? extends LeetcodeJavaDebugEnhancer> AT) throws Exception, Error {
        // Create an AT instance enhancer at first.
        LeetcodeJavaDebugEnhancer enhancer = ReflectUtil.createInstance(AT);
        // Setup EnhancerLog log level.
        EnhancerLogUtil.setLogLevel(enhancer.getEnhancerLogLevel());

        EnhancerLogUtil.logI("Starting do debugging enhance at (AT) class: %s", AT.getSimpleName());
        // Create a LeetcodeExecutor from the enhancer.
        Object leetcodeExecutor = LeetcodeExecutorFactory.getLeetcodeExecutor(enhancer);
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
            // Now we can happily run the io loop to perform leetcode debugging enhancements.
            EnhancerLogUtil.logI("Running leetcode debugging enhancer at (AT) class: %s", AT.getSimpleName());
            while (true) {
                // Provide the next string input from the InputProvider.
                String input = inputProvider.provideNextInput();
                // We need to break this loop when the input indicates end.
                if (inputProvider.isEnd(input)) {
                    break;
                }
                // Parse the string input to input object.
                Object inputObject = InputParserProcessor.process(inputParser, leetcodeExecutor, input);
                // Execute leetcode target and get the output object.
                Object outputObject = LeetcodeExecutorProcessor.process(leetcodeExecutor, inputObject);
                // Print the output object.
                String output = OutputPrinterProcessor.process(outputPrinter, leetcodeExecutor, outputObject);
                // Consume the next output string to the OutputConsumer.
                outputConsumer.consumeNextOutput(output);
            }
        }

        // Now, the enhancement work has ended here. It's time to say goodbye.
        // Wishing all programmers around the world the true joy of life in Leetcode.
        // May you be so powerful that you don't need debugging and have no bugs.
        // Good luck!!!.
        EnhancerLogUtil.logI("Stopped leetcode debugging enhancer at (AT) class: %s", AT.getSimpleName());
    }

}
