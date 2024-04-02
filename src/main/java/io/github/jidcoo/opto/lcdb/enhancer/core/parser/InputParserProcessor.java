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
import io.github.jidcoo.opto.lcdb.enhancer.utils.ReflectUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.StringUtil;

import java.lang.reflect.Method;

/**
 * <p>InputParserProcessor is a publicly available
 * InputParser processor. It has used a proxy to
 * access {@link InputParser}.</p>
 *
 * <p>The main {@link #process(Object, Object, String)} method
 * of InputParserProcessor is to proxy external callers
 * to execute {@link InputParser}.
 * </p>
 *
 * @author Jidcoo
 * @since 1.0
 */
public final class InputParserProcessor {

    /**
     * Do real parse with InputParser instance and input.
     *
     * @param parser   the InputParser instance.
     * @param executor the LeetcodeExecutor instance.
     * @param input    the input string.
     * @return the executor output object.
     */
    public static Object process(Object parser, Object executor, String input) {
        AssertUtil.nonNull(parser, "The parser cannot be null.");
        AssertUtil.nonNull(executor, "The parser cannot be null.");
        AssertUtil.isTrue(!StringUtil.isBlank(input), "The input cannot be blank.");
        AssertUtil.isTrue((parser instanceof InputParser), "The parser is not a InputParser.");
        // Get the leetcode target instance and target method from LeetcodeExecutor.
        Object targetInstance = ReflectUtil.getFieldValue("instance", Object.class, executor);
        Method targetExecutor = ReflectUtil.getFieldValue("executor", Method.class, executor);
        // Create an InputParseTask instance.
        InputParseTask inputParseTask = new InputParseTask(targetInstance, targetExecutor, input);
        // Do real parse logic and return the parser output.
        Object output = ((InputParser) (parser)).parse(inputParseTask);
        // Set the final leetcode invoker from inputParseTask to the LeetcodeExecutor.
        ReflectUtil.setFieldValue("invoker", Method.class, inputParseTask.getTargetMethod(), executor);
        // Set the leetcode invoker response type to the LeetcodeExecutor.
        ReflectUtil.setFieldValue("invokerResponseType", Class.class,
                inputParseTask.getTargetMethod().getReturnType(), executor);
        return output;
    }
}
