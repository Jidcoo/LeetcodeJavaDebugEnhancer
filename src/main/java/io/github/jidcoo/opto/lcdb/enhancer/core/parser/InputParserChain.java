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

import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ContainerCheckUtil;

import java.util.List;

/**
 * <p>InputParserChain is an input source parsing chain. </p>
 * <p>The input parsing process is designed based on
 * the chain of responsibility pattern.</p>
 *
 * <p>InputParserChain also extends {@link InputParserNode}.
 * But in here, the main function of the {@link #parse(InputParserContext)}
 * method is only to organize and schedule the parsing chain
 * to complete the entire input parsing process.
 * </p>
 *
 * @author Jidcoo
 * @since 1.0
 */
class InputParserChain extends InputParserNode {

    private final List<InputParserNode> nodes;

    /**
     * Create a InputParserChain instance by
     * given InputParserNode list.
     *
     * @param nodes the InputParserNode list.
     */
    InputParserChain(List<InputParserNode> nodes) {
        AssertUtil.isTrue(!ContainerCheckUtil.isListEmpty(nodes), "The InputParserNode list cannot be empty.");
        this.nodes = nodes;
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
     * Parse input with context.
     *
     * @param context the instance parser context.
     * @return the parsed result object.
     * @implNote This method does not participate in
     * the actual parsing chain.
     */
    @Override
    final Object parse(InputParserContext context) {
        for (InputParserNode node : nodes) {
            // Check if the input stack of the context is empty.
            AssertUtil.isTrue(context.getInputStackSize() > 0,
                    "Error no input in context: " + context + ", node: " + node);
            // Do parse on node
            Object curNodeOutput = node.parse(context);
            // Push the last node output to the input stack of the parser context.
            context.pushInput(curNodeOutput);
        }
        // Finally, check again if the input stack of the context is empty.
        AssertUtil.isTrue(context.getInputStackSize() > 0, "Error no output in context: " + context);
        // Only peek the last input as the final output.
        return context.peekInput();
    }
}
