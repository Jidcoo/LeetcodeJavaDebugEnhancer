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

package io.github.jidcoo.opto.lcdb.enhancer.func.payload;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.core.proxy.EnhancerProxyFactory;
import io.github.jidcoo.opto.lcdb.enhancer.core.proxy.ProxyPointInterceptor;
import io.github.jidcoo.opto.lcdb.enhancer.utils.EnhancerLogUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ReflectUtil;

import java.util.Objects;

/**
 * <p>EnhancerPayloadInterceptor is a proxy-point
 * interceptor used to resolve inner "Solution" class
 * from <tt>AT</tt> class as enhancer payload.
 *
 * @author Jidcoo
 * @see LeetcodeJavaDebugEnhancer#getEnhancerPayload()
 * @see ProxyPointInterceptor
 * @since 1.0.3
 */
@Require(types = ProxyPointInterceptor.class)
final class EnhancerPayloadInterceptor implements ProxyPointInterceptor<Class<?>> {

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
     * Return the proxy point name that needs to
     * be intercepted.
     *
     * @return the proxy point name.
     */
    @Override
    public String interceptPoint() {
        return "getEnhancerPayload";
    }

    /**
     * Intercept and process proxy point result
     * after invoking proxy point.
     *
     * @param leetcodeJavaDebugEnhancer the LeetcodeJavaDebugEnhancer instance.
     * @param aClass                    the proxy point result.
     * @return the processed proxy point result.
     */
    @Override
    public Class<?> onAfter(LeetcodeJavaDebugEnhancer leetcodeJavaDebugEnhancer, Class<?> aClass) {
        if (Objects.nonNull(aClass)) {
            return aClass;
        }
        // Try to load inner class from AT instance.
        LeetcodeJavaDebugEnhancer target = EnhancerProxyFactory.awareSourceEnhancer(leetcodeJavaDebugEnhancer);
        if (Objects.isNull(target)) {
            EnhancerLogUtil.logW("EnhancerPayloadInterceptor: cannot aware source enhancer from the AT instance.");
            return null;
        }
        Class<?>[] innerClasses = ReflectUtil.resolveInnerClasses(target.getClass());
        if (innerClasses.length == 0) {
            EnhancerLogUtil.logW("EnhancerPayloadInterceptor: cannot resolve any inner class from the AT instance.");
            return null;
        }
        // Unfortunately, we are currently unable to handle situations where
        // there are multiple INNER-CLASS in AT.
        if (innerClasses.length > 1) {
            EnhancerLogUtil.logW("EnhancerPayloadInterceptor: multiple inner classes were found in AT instance, there" +
                    " can only be one inner class in AT instance.");
            return null;
        }
        // Use this only inner class as an instance of leetcode executor.
        return innerClasses[0];
    }
}
