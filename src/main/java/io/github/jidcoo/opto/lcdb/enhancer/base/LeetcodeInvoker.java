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
     * Get id of this invoker.
     *
     * @return the id.
     */
    Integer getId();

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

    /**
     * Returns a string describing this {@code Constructor},
     * including type parameters.  The string is formatted as the
     * constructor access modifiers, if any, followed by an
     * angle-bracketed comma separated list of the constructor's type
     * parameters, if any, followed by the fully-qualified name of the
     * declaring class, followed by a parenthesized, comma-separated
     * list of the constructor's generic formal parameter types.
     * <p>
     * If this constructor was declared to take a variable number of
     * arguments, instead of denoting the last parameter as
     * "<tt><i>Type</i>[]</tt>", it is denoted as
     * "<tt><i>Type</i>...</tt>".
     * <p>
     * A space is used to separate access modifiers from one another
     * and from the type parameters or return type.  If there are no
     * type parameters, the type parameter list is elided; if the type
     * parameter list is present, a space separates the list from the
     * class name.  If the constructor is declared to throw
     * exceptions, the parameter list is followed by a space, followed
     * by the word "{@code throws}" followed by a
     * comma-separated list of the thrown exception types.
     *
     * <p>The only possible modifiers for constructors are the access
     * modifiers {@code public}, {@code protected} or
     * {@code private}.  Only one of these may appear, or none if the
     * constructor has default (package) access.
     *
     * @return a string describing this {@code Constructor},
     * include type parameters
     */
    String toGenericString();
}
