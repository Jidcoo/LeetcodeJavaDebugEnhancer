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
import io.github.jidcoo.opto.lcdb.enhancer.base.EnhancerException;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * <p>EnhancerProxyHandler is a proxy invocation handler
 * used for enhancing {@link LeetcodeJavaDebugEnhancer}
 * agents.
 *
 * <p>In versions 1.0.3 and later, all new feature
 * implementations will be based on {@link EnhancerProxyHandler}.
 *
 * @author Jidcoo
 * @see LeetcodeJavaDebugEnhancer
 * @see ProxyPointInterceptorManager
 * @since 1.0.3
 */
final class EnhancerProxyHandler implements InvocationHandler {

    /**
     * The LeetcodeJavaDebugEnhancer target.
     */
    private final LeetcodeJavaDebugEnhancer target;

    /**
     * ProxyPointInterceptor manager.
     */
    private ProxyPointInterceptorManager proxyPointInterceptorManager;

    /**
     * Create an EnhancerProxyHandler instance.
     *
     * @param enhancer The LeetcodeJavaDebugEnhancer instance.
     */
    EnhancerProxyHandler(LeetcodeJavaDebugEnhancer enhancer) {
        AssertUtil.nonNull(enhancer, "The LeetcodeJavaDebugEnhancer instance cannot be null.");
        this.target = enhancer;
        this.proxyPointInterceptorManager = new ProxyPointInterceptorManager();
    }

    /**
     * Return the LeetcodeJavaDebugEnhancer target.
     *
     * @return the LeetcodeJavaDebugEnhancer target.
     */
    LeetcodeJavaDebugEnhancer getTarget() {
        return target;
    }

    /**
     * Processes a method invocation on a proxy instance and returns
     * the result.  This method will be invoked on an invocation handler
     * when a method is invoked on a proxy instance that it is
     * associated with.
     *
     * @param proxy  the proxy instance that the method was invoked on
     * @param method the {@code Method} instance corresponding to
     *               the interface method invoked on the proxy instance.  The declaring
     *               class of the {@code Method} object will be the interface that
     *               the method was declared in, which may be a superinterface of the
     *               proxy interface that the proxy class inherits the method through.
     * @param args   an array of objects containing the values of the
     *               arguments passed in the method invocation on the proxy instance,
     *               or {@code null} if interface method takes no arguments.
     *               Arguments of primitive types are wrapped in instances of the
     *               appropriate primitive wrapper class, such as
     *               {@code java.lang.Integer} or {@code java.lang.Boolean}.
     * @return the value to return from the method invocation on the
     * proxy instance.  If the declared return type of the interface
     * method is a primitive type, then the value returned by
     * this method must be an instance of the corresponding primitive
     * wrapper class; otherwise, it must be a type assignable to the
     * declared return type.  If the value returned by this method is
     * {@code null} and the interface method's return type is
     * primitive, then a {@code NullPointerException} will be
     * thrown by the method invocation on the proxy instance.  If the
     * value returned by this method is otherwise not compatible with
     * the interface method's declared return type as described above,
     * a {@code ClassCastException} will be thrown by the method
     * invocation on the proxy instance.
     * @throws Throwable the exception to throw from the method
     *                   invocation on the proxy instance.  The exception's type must be
     *                   assignable either to any of the exception types declared in the
     *                   {@code throws} clause of the interface method or to the
     *                   unchecked exception types {@code java.lang.RuntimeException}
     *                   or {@code java.lang.Error}.  If a checked exception is
     *                   thrown by this method that is not assignable to any of the
     *                   exception types declared in the {@code throws} clause of
     *                   the interface method, then an
     *                   {@link UndeclaredThrowableException} containing the
     *                   exception that was thrown by this method will be thrown by the
     *                   method invocation on the proxy instance.
     * @see UndeclaredThrowableException
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return proxyPointInvoke(proxy, method, args);
    }

    /**
     * Do real proxy point invoke.
     *
     * @param proxy the proxy instance.
     * @param point the proxy method point.
     * @param args  invoke args.
     * @return final proxy invoke result.
     */
    private Object proxyPointInvoke(Object proxy, Method point, Object... args) {
        LeetcodeJavaDebugEnhancer enhancer = (LeetcodeJavaDebugEnhancer) proxy;
        String pointName = point.getName();
        ProxyPointParameterView parameterView = new ProxyPointParameterView(point.getParameterTypes(), args);
        Object result;
        try {
            proxyPointInterceptorManager.doInterceptOnBefore(enhancer, pointName, parameterView);
            result = point.invoke(target, args);
            return proxyPointInterceptorManager.doInterceptOnAfter(enhancer, pointName, result);
        } catch (Throwable throwable) {
            throw new EnhancerException("proxy invoke error: " + throwable.getMessage(), throwable);
        }
    }
}
