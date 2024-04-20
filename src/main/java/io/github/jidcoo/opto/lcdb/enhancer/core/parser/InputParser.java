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

package io.github.jidcoo.opto.lcdb.enhancer.core.parser;

import io.github.jidcoo.opto.lcdb.enhancer.base.Order;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ContainerCheckUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.PackageUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ReflectUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>InputParser is an input parser.</p>
 *
 * <p>InputParser accepts an {@link InputParseTask} through
 * the {@link #parse(InputParseTask)} method, then calls
 * {@link InputParserChain#parse(InputParserContext)}, and
 * finally returns the chain output.</p>
 *
 * @author Jidcoo
 * @see InputParserChain
 * @see InputParseTask
 * @see InputParserContext
 * @see InputParserNode
 * @since 1.0
 */
final class InputParser {

    /**
     * InputParserNode implementations package location
     */
    private static final String INPUT_PARSER_NODE_PACKAGE = "io.github.jidcoo.opto.lcdb.enhancer.core.parser";

    /**
     * The InputParserChain in this parser.
     */
    private InputParserChain parserChain;

    /**
     * Create an InputParser instance.
     */
    InputParser() {
        List<InputParserNode> inputParserNodes = new ArrayList<>();
        // Get all class in the INPUT_PARSER_NODE_PACKAGE.
        List<Class<?>> classesByPackage = PackageUtil.getClassesByPackage(INPUT_PARSER_NODE_PACKAGE);
        if (!ContainerCheckUtil.isListEmpty(classesByPackage)) {
            for (Class<?> clazz : classesByPackage) {
                if (ReflectUtil.isExtendsClass(clazz, InputParserNode.class)) {
                    // Instantiate only nodes that contain the @Require annotation.
                    if (!clazz.isAnnotationPresent(Require.class)) {
                        continue;
                    }
                    // Create InputParserNode instance by class.
                    InputParserNode inputParserNode =
                            ReflectUtil.createInstance((Class<? extends InputParserNode>) clazz);
                    // Add to inputParserNodes.
                    inputParserNodes.add(inputParserNode);
                }
            }
        }
        // Sort the inputParserNodes by Order.
        inputParserNodes.sort(Comparator.comparingInt(Order::getOrder).reversed());
        // Create an InputParserChain by inputParserNodes.
        parserChain = new InputParserChain(inputParserNodes);
    }

    /**
     * Parse an input from an InputParseTask instance.
     *
     * @param inputParseTask the InputParseTask instance.
     * @return the output of the parser.
     */
    Object parse(InputParseTask inputParseTask) {
        // Create a context.
        InputParserContext inputParserContext = createParserContext(inputParseTask);
        // Execute parsing chain and return the chain output.
        Object inputObject = parserChain.parse(inputParserContext);
        // Set the final leetcode invoker from inputParserContext to the inputParseTask.
        inputParseTask.setTargetInvoker(inputParserContext.getTargetInvoker());
        return inputObject;
    }

    /**
     * Create an InputParserContext instance by InputParseTask.
     *
     * @param inputParseTask the InputParseTask instance.
     * @return the InputParserContext instance.
     */
    private InputParserContext createParserContext(InputParseTask inputParseTask) {
        AssertUtil.nonNull(inputParseTask, "The inputParseTask cannot be null");
        AssertUtil.nonNull(inputParseTask.getTargetInstance(), "The target cannot be null");
        AssertUtil.nonNull(inputParseTask.getInput(), "The input cannot be null");
        return new InputParserContext(inputParseTask.getTargetInstance(), inputParseTask.getInput(),
                inputParseTask.getCandidateInvokers());
    }
}
