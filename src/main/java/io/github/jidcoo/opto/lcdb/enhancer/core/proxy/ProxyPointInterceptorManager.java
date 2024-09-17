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

package io.github.jidcoo.opto.lcdb.enhancer.core.proxy;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.base.Order;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.utils.*;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>ProxyPointInterceptorManager is a proxy point interceptor
 * manager.
 *
 * <p>It will scan all possible {@link ProxyPointInterceptor}
 * for the {@link DebugEnhancerProxy} proxy point surround
 * interception processing.
 *
 * @author Jidcoo
 * @see ProxyPointInterceptor
 * @see DebugEnhancerProxy
 * @since 1.0.2
 */
final class ProxyPointInterceptorManager {

    /**
     * ProxyPointInterceptor instances map(interceptPoint->List<ProxyPointInterceptor>).
     */
    private final Map<String, List<ProxyPointInterceptor>> proxyPointInterceptorsMap;

    /**
     * ProxyPointInterceptor scanner base package location.
     */
    private static final String PROXY_POINT_INTERCEPTOR_SCANNER_BASE_PACKAGE = "io.github.jidcoo.opto.lcdb.enhancer.func";

    /**
     * Create a ProxyPointInterceptorManager instance.
     *
     * @param allowedProxyPointsSet the all allowed proxy points set.
     */
    ProxyPointInterceptorManager(Set<String> allowedProxyPointsSet) {
        AssertUtil.nonNull(allowedProxyPointsSet, "The allowed proxy points set cannot be null.");
        // init all proxy point interceptors.
        proxyPointInterceptorsMap = BeanUtil.collectBeans(ProxyPointInterceptor.class, PROXY_POINT_INTERCEPTOR_SCANNER_BASE_PACKAGE,
                (Class type) -> {
                    boolean baseValidResult = type.isAnnotationPresent(Require.class)
                            && ReflectUtil.isImplementInterface(type, ProxyPointInterceptor.class)
                            && !Modifier.isAbstract(type.getModifiers());
                    if (!baseValidResult) {
                        return false;
                    }
                    Require requireAnnotation = (Require) type.getAnnotation(Require.class);
                    return requireAnnotation.types().length > 0 && ProxyPointInterceptor.class.equals(requireAnnotation.types()[0]);
                },
                (Class<? extends ProxyPointInterceptor> beanType) -> {
                    ProxyPointInterceptor interceptor = ReflectUtil.createInstance(beanType);
                    if (allowedProxyPointsSet.contains(interceptor.interceptPoint())) {
                        return interceptor;
                    }
                    EnhancerLogUtil.logW("Invalid interceptor, detected a disallowed intercept-proxy-point: " + interceptor.interceptPoint());
                    return null;
                }
        ).stream().filter(Objects::nonNull).collect(
                Collectors.groupingBy(ProxyPointInterceptor::interceptPoint,
                Collectors.collectingAndThen(Collectors.toList(),
                list -> {
                    list.sort(Comparator.comparingInt(Order::getOrder).reversed());
                    return list;
                }
        )));
    }

    /**
     * Intercept and process parameters before
     * invoking proxy point.
     *
     * @param leetcodeJavaDebugEnhancer the LeetcodeJavaDebugEnhancer instance.
     * @param pointName     the proxy point name.
     * @param parameterView the proxy point parameter view.
     */
    @SuppressWarnings("unchecked")
    public void doInterceptOnBefore(LeetcodeJavaDebugEnhancer leetcodeJavaDebugEnhancer,
                                    String pointName, ProxyPointParameterView parameterView) {
        AssertUtil.isTrue(!StringUtil.isBlank(pointName), "The proxy point name cannot be blank.");
        AssertUtil.nonNull(parameterView, "The proxy point parameter view cannot be null.");
        List<ProxyPointInterceptor> interceptors = proxyPointInterceptorsMap.getOrDefault(pointName, null);
        if (ContainerCheckUtil.isListEmpty(interceptors)) {
            return;
        }
        for (ProxyPointInterceptor interceptor : interceptors) {
            interceptor.onBefore(leetcodeJavaDebugEnhancer, parameterView);
        }
    }

    /**
     * Intercept and process proxy point result
     * after invoking proxy point.
     *
     * @param leetcodeJavaDebugEnhancer the LeetcodeJavaDebugEnhancer instance.
     * @param pointName the proxy point name.
     * @param o         the proxy point result.
     * @return the processed proxy point result.
     */
    @SuppressWarnings("unchecked")
    public Object doInterceptOnAfter(LeetcodeJavaDebugEnhancer leetcodeJavaDebugEnhancer,
                                     String pointName, Object o) {
        AssertUtil.isTrue(!StringUtil.isBlank(pointName), "The proxy point name cannot be blank.");
        List<ProxyPointInterceptor> interceptors = proxyPointInterceptorsMap.getOrDefault(pointName, null);
        if (!ContainerCheckUtil.isListEmpty(interceptors)) {
            for (ProxyPointInterceptor interceptor : interceptors) {
                o = interceptor.onAfter(leetcodeJavaDebugEnhancer, o);
            }
        }
        return o;
    }
}
