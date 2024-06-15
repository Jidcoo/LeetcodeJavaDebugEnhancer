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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>LeetcodeInvokerFactory is a factory class
 * to product the {@link LeetcodeInvoker}
 * instance.</p>
 *
 * @author Jidcoo
 * @since 1.0.1
 */
public final class LeetcodeInvokerFactory {

    /**
     * LeetcodeInvoke id generator.
     */
    private static final AtomicInteger INVOKER_ID_GENERATOR = new AtomicInteger(1);

    /**
     * Product a LeetcodeInvoker instance by
     * {@link Method} instance.
     *
     * @param method the method instance.
     * @return the LeetcodeInvoker instance.
     */
    public static LeetcodeInvoker getLeetcodeInvoker(Method method) {
        // We don't need to perform non-null check here.
        return new MethodLeetcodeInvoker(method, INVOKER_ID_GENERATOR.getAndIncrement());
    }

    /**
     * Product a LeetcodeInvoker instance by
     * {@link Constructor} instance.
     *
     * @param constructor the constructor instance.
     * @return the LeetcodeInvoker instance.
     */
    public static LeetcodeInvoker getLeetcodeInvoker(Constructor<?> constructor) {
        // We don't need to perform non-null check here.
        return new ConstructorLeetcodeInvoker(constructor, INVOKER_ID_GENERATOR.getAndIncrement());
    }
}
