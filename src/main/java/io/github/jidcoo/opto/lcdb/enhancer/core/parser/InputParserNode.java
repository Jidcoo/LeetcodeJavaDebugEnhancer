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

/**
 * <p>InputParserNode is an input parse node.</p>
 * <p>An InputParserNode will focus on a part of
 * the entire input parsing process organized by
 * the {@link InputParserChain}.
 * </p>
 *
 * <p>Note: InputParserNode implements the {@link Order}
 * and it' s also an abstract class. So please do not
 * forget to implement the {@link #getOrder()} method.
 * Because it will directly reflect the priority of
 * this input-parser-node!!!
 * </p>
 *
 * @author Jidcoo
 * @since 1.0
 */
abstract class InputParserNode implements Order {

    /**
     * Parse input with context.
     *
     * @param context the instance parser context.
     * @return the parsed result object.
     * @apiNote You can call the {@link InputParserContext#peekInput()} method
     *          of the context to view the output of the previous node.
     *          But the prerequisite is that the
     *          {@link InputParserContext#getInputStackSize()} method of
     *          the context returns an int greater than 0.
     */
    abstract Object parse(InputParserContext context);
}
