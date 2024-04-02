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

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;

/**
 * <p>InputParserFactory is a factory class
 * to product the {@link InputParser}
 * instance.</p>
 *
 * @author Jidcoo
 * @since 1.0
 */
public final class InputParserFactory {

    /**
     * Product a InputParser instance by
     * {@link LeetcodeJavaDebugEnhancer} instance.
     *
     * @param enhancer the LeetcodeJavaDebugEnhancer instance.
     * @return the InputParser instance.
     */
    public static InputParser getInputParser(LeetcodeJavaDebugEnhancer enhancer) {
        AssertUtil.nonNull(enhancer, "The enhancer cannot be null.");
        return new InputParser();
    }
}
