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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

/**
 * <p>MethodLeetcodeInvoker is an invoker
 * used for {@link Method} type.</p>
 *
 * @author Jidcoo
 * @see LeetcodeInvoker
 * @since 1.0.1
 */
final class MethodLeetcodeInvoker implements LeetcodeInvoker {

    /**
     * The base method;
     */
    private final Method method;

    /**
     * The invoker id.
     */
    private final Integer id;

    /**
     * The invoker order;
     *
     * @since 1.0.3
     */
    private final int order;

    /**
     * Create a MethodLeetcodeInvoker instance.
     *
     * @param method the base method.
     * @param id     the invoker id.
     */
    MethodLeetcodeInvoker(Method method, Integer id) {
        this.method = method;
        // Make accessible.
        this.method.setAccessible(true);
        this.id = id;
        this.order = id;
    }

    /**
     * Create a MethodLeetcodeInvoker instance by custom order.
     *
     * @param method the base method.
     * @param id     the invoker id.
     * @param order  the invoker order.
     * @see io.github.jidcoo.opto.lcdb.enhancer.base.Order
     * @since 1.0.3
     */
    MethodLeetcodeInvoker(Method method, Integer id, Integer order) {
        this.method = method;
        // Make accessible.
        this.method.setAccessible(true);
        this.id = id;
        this.order = order;
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
        return this.method.getParameterCount();
    }

    /**
     * Get name of this invoker.
     *
     * @return the invoker name.
     */
    @Override
    public String getInvokerName() {
        return this.method.getName();
    }

    /**
     * Get parameter types array of this invoker.
     *
     * @return the parameter types array.
     */
    @Override
    public Class<?>[] getParameterTypes() {
        return this.method.getParameterTypes();
    }

    /**
     * Get parameters of this invoker.
     *
     * @return the parameters array.
     */
    @Override
    public Parameter[] getParameters() {
        return this.method.getParameters();
    }

    /**
     * Get return type of this invoker.
     *
     * @return the return type.
     */
    @Override
    public Class<?> getReturnType() {
        return this.method.getReturnType();
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
        if (!Modifier.isStatic(this.method.getModifiers())) {
            // Check whether the declared class of this method matches the class of the object
            Class<?> declaringClass = this.method.getDeclaringClass();
            AssertUtil.isTrue(declaringClass.isAssignableFrom(object.getClass()),
                    String.format("The object(type is %s) is not the owner of this invoker(real owner is %s).",
                            object.getClass(), declaringClass));
        }
        return this.method.invoke(object, args);
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
        return this.method.toGenericString();
    }

    /**
     * Verify whether this leetcode invoke is suitable for the specified class.
     *
     * @param klass the specified class.
     * @return true if suitable.
     * @since 1.0.3
     */
    @Override
    public boolean isSuitable(Class<?> klass) {
        return this.method.getDeclaringClass() == klass;
    }

    /**
     * Get the order of the object.
     *
     * @return the int order of the object.
     * @since 1.0.3
     */
    @Override
    public int getOrder() {
        return this.order;
    }
}
