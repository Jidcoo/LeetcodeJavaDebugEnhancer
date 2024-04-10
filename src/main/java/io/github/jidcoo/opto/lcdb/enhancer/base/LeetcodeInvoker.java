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

package io.github.jidcoo.opto.lcdb.enhancer.base;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;

import java.lang.reflect.Parameter;

/**
 * <p>LeetcodeInvoker is an invoker used to
 * execute the Leetcode algorithm.</p>
 * <p>The purpose of LeetcodeInvoker is to help
 * {@link LeetcodeJavaDebugEnhancer}
 * better match, select and execute
 * Leetcode algorithms.</p>
 *
 * @author Jidcoo
 * @since 1.0.1
 */
public interface LeetcodeInvoker {

    /**
     * Get parameter count of this invoker.
     *
     * @return parameter count of this invoker.
     */
    int getParameterCount();

    /**
     * Get parameter types array of this invoker.
     *
     * @return the parameter types array.
     */
    Class<?>[] getParameterTypes();

    /**
     * Get parameters of this invoker.
     *
     * @return the parameters array.
     */
    Parameter[] getParameters();

    /**
     * Get name of this invoker.
     *
     * @return the invoker name.
     */
    String getInvokerName();

    /**
     * Get return type of this invoker.
     *
     * @return the return type.
     */
    Class<?> getReturnType();

    /**
     * Invoke this invoker with args by invoker holder.
     *
     * @param object the object holding this invoker.
     * @param args   the invoking args.
     * @return the invoked result.
     * @throws Throwable throw an exception when there is an error
     *                   in the invoking process.
     */
    Object invoke(Object object, Object... args) throws Throwable;
}
