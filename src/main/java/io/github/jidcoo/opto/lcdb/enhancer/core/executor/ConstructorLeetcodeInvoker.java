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

package io.github.jidcoo.opto.lcdb.enhancer.core.executor;

import io.github.jidcoo.opto.lcdb.enhancer.base.LeetcodeInvoker;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;

/**
 * <p>ConstructorLeetcodeInvoker is an invoker
 * used for {@link Constructor} type.</p>
 *
 * @author Jidcoo
 * @see LeetcodeInvoker
 * @since 1.0.1
 */
final class ConstructorLeetcodeInvoker implements LeetcodeInvoker {

    /**
     * The base constructor;
     */
    private final Constructor<?> constructor;

    /**
     * The invoker id.
     */
    private final Integer id;

    /**
     * Friendly matching mode flag.
     */
    private boolean matchingFriendly;

    /**
     * Create a ConstructorLeetcodeInvoker instance.
     *
     * @param constructor the base constructor.
     * @param id          the invoker id.
     */
    ConstructorLeetcodeInvoker(Constructor<?> constructor, Integer id) {
        AssertUtil.nonNull(constructor, "The constructor cannot be null.");
        this.constructor = constructor;
        // Make accessible.
        this.constructor.setAccessible(true);
        this.id = id;
        // Friendly-Matching-Mode is closed by default.
        this.matchingFriendly = false;
    }

    /**
     * Get id of this invoker.
     *
     * @return the id.
     */
    @Override
    public Integer getId() {
        return this.id;
    }

    /**
     * Get parameter count of this invoker.
     *
     * @return parameter count of this invoker.
     */
    @Override
    public int getParameterCount() {
        int parameterCount = this.constructor.getParameterCount();
        if (parameterCount > 0 && matchingFriendly) {
            return parameterCount - 1;
        }
        return parameterCount;
    }

    /**
     * Get name of this invoker.
     *
     * @return the invoker name.
     */
    @Override
    public String getInvokerName() {
        String name = this.constructor.getName();
        if (name.contains("$") && matchingFriendly) {
            return name.substring(name.lastIndexOf('$') + 1);
        }
        return name;
    }

    /**
     * Get parameter types array of this invoker.
     *
     * @return the parameter types array.
     */
    @Override
    public Class<?>[] getParameterTypes() {
        Class<?>[] parameterTypes = this.constructor.getParameterTypes();
        if (parameterTypes.length > 0 && matchingFriendly) {
            return Arrays.stream(parameterTypes).skip(1).toArray(Class[]::new);
        }
        return parameterTypes;
    }

    /**
     * Get parameters of this invoker.
     *
     * @return the parameters array.
     */
    @Override
    public Parameter[] getParameters() {
        Parameter[] parameters = this.constructor.getParameters();
        if (parameters.length > 0 && matchingFriendly) {
            return Arrays.stream(parameters).skip(1).toArray(Parameter[]::new);
        }
        return parameters;
    }

    /**
     * Get return type of this invoker.
     *
     * @return the return type.
     */
    @Override
    public Class<?> getReturnType() {
        return this.constructor.getDeclaringClass();
    }

    /**
     * Invoke this invoker with args by invoker holder.
     *
     * @param object the object holding this invoker.
     * @param args   the invoking args.
     * @return the invoked result.
     * @throws Throwable throw an exception when there is an error
     *                   in the invoking process.
     */
    @Override
    public Object invoke(Object object, Object... args) throws Throwable {
        Object[] initArgsArray;
        if (Modifier.isStatic(this.constructor.getDeclaringClass().getModifiers())) {
            initArgsArray = new Object[args.length];
            System.arraycopy(args, 0, initArgsArray, 0, args.length);
        } else {
            initArgsArray = new Object[args.length + 1];
            if (Objects.nonNull(object)) {
                Class<?> outerClass0 = this.constructor.getParameterTypes()[0];
                // Check whether the outerClass of this constructor matches the class of the object
                AssertUtil.isTrue(outerClass0.isAssignableFrom(object.getClass()), String.format("The object(type is "
                        + "%s) is not the outer class instance of this invoker(real " + "outer class is %s).",
                        object.getClass(), outerClass0));
            }
            initArgsArray[0] = object;
            System.arraycopy(args, 0, initArgsArray, 1, args.length);
        }
        return this.constructor.newInstance(initArgsArray);
    }

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
    @Override
    public String toGenericString() {
        return this.constructor.toGenericString();
    }
}
