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
     * Create a MethodLeetcodeInvoker instance.
     *
     * @param method the base method.
     * @param id     the invoker id.
     */
    MethodLeetcodeInvoker(Method method, Integer id) {
        AssertUtil.nonNull(method, "The method cannot be null.");
        this.method = method;
        // Make accessible.
        this.method.setAccessible(true);
        this.id = id;
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
        return this.method.invoke(object, args);
    }
}
