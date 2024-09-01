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
import io.github.jidcoo.opto.lcdb.enhancer.base.*;
import io.github.jidcoo.opto.lcdb.enhancer.core.executor.LeetcodeInvokerFactory;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ReflectUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.StringUtil;

import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * <p>DebugEnhancerProxy is a static proxy class
 * used to enhance and intercept the functionality of
 * {@link LeetcodeJavaDebugEnhancer}.
 *
 * <p>In versions 1.0.2 and later, all new feature
 * implementations will be based on it.
 * DebugEnhancerProxy intercepts and processes methods
 * with {@link ProxyPoint} as an enhanced granularity.
 *
 * @author Jidcoo
 * @see LeetcodeJavaDebugEnhancer
 * @see ProxyPointInterceptor
 * @see ProxyPointInterceptorManager
 * @since 1.0.2
 */
public final class DebugEnhancerProxy extends LeetcodeJavaDebugEnhancer {

    /**
     * No param proxy point type.
     */
    private static final Class<?>[] DEFAULT_NO_PARAM_TYPE = new Class[]{};

    /**
     * Proxy method points map.
     */
    private final Map<String, ProxyPoint> PROXY_POINTS_MAP = Arrays.asList(
            new ProxyPoint("getEnhancementPoint", DEFAULT_NO_PARAM_TYPE),
            new ProxyPoint("getInputProvider", DEFAULT_NO_PARAM_TYPE),
            new ProxyPoint("getOutputConsumer", DEFAULT_NO_PARAM_TYPE),
            new ProxyPoint("getOutputPrintStrategies", DEFAULT_NO_PARAM_TYPE),
            new ProxyPoint("getEnhancerLogLevel", DEFAULT_NO_PARAM_TYPE)
    ).stream().collect(Collectors.toMap(ProxyPoint::getPointName, proxyPoint -> proxyPoint));

    /**
     * Proxy point instances map.
     */
    private Map<String, LeetcodeInvoker> proxyPointInstancesMap;

    /**
     * ProxyPointInterceptor manager.
     */
    private ProxyPointInterceptorManager proxyPointInterceptorManager;

    /**
     * The LeetcodeJavaDebugEnhancer target.
     */
    private final LeetcodeJavaDebugEnhancer target;

    /**
     * Create a DebugEnhancerProxy instance.
     *
     * @param target The LeetcodeJavaDebugEnhancer target.
     */
    public DebugEnhancerProxy(LeetcodeJavaDebugEnhancer target) {
        AssertUtil.nonNull(target, "The LeetcodeJavaDebugEnhancer target cannot be null.");
        Class<?> targetClass = target.getClass();
        proxyPointInstancesMap = new HashMap<>();
        proxyPointInterceptorManager = new ProxyPointInterceptorManager(PROXY_POINTS_MAP.keySet());
        PROXY_POINTS_MAP.forEach((proxyPointName, proxyPoint) -> proxyPointInstancesMap.put(proxyPointName,
                proxyPoint.findPoint(targetClass)));
        this.target = target;
    }

    /**
     * Do real proxy point invoke.
     *
     * @param pointName  the proxy point name.
     * @param returnType the return type.
     * @param args       invoke args.
     * @return final proxy invoke result.
     */
    private <RETURN> RETURN proxyPointInvoke(String pointName, Class<RETURN> returnType, Object... args) {
        ProxyPoint proxyPoint = PROXY_POINTS_MAP.getOrDefault(pointName, null);
        AssertUtil.nonNull(proxyPoint, "Cannot found the proxy point: " + pointName);
        LeetcodeInvoker proxyPointInvoker = proxyPointInstancesMap.getOrDefault(pointName, null);
        AssertUtil.nonNull(proxyPointInvoker, "Cannot found the proxy point invoker: " + pointName);
        ProxyPointParameterView parameterView = new ProxyPointParameterView(proxyPoint.getParamTypes(), args);
        Object result;
        try {
            proxyPointInterceptorManager.doInterceptOnBefore(this, pointName, parameterView);
            result = proxyPointInvoker.invoke(target, args);
            return returnType.cast(proxyPointInterceptorManager.doInterceptOnAfter(this, pointName, result));
        } catch (Throwable throwable) {
            throw new EnhancerException("proxy invoke error: " + throwable.getMessage(), throwable);
        }
    }

    /**
     * Return the LeetcodeJavaDebugEnhancer target.
     *
     * @return the LeetcodeJavaDebugEnhancer target.
     */
    public LeetcodeJavaDebugEnhancer getTarget() {
        return target;
    }

    /**
     * Return the source enhancer from the special LeetcodeJavaDebugEnhancer instance.
     *
     * @param enhancer the LeetcodeJavaDebugEnhancer instance.
     * @return the source LeetcodeJavaDebugEnhancer instance.
     */
    public static LeetcodeJavaDebugEnhancer awareSource(LeetcodeJavaDebugEnhancer enhancer) {
        if (Objects.isNull(enhancer)) {
            return null;
        }
        if (enhancer instanceof DebugEnhancerProxy) {
            return ((DebugEnhancerProxy) enhancer).getTarget();
        }
        return enhancer;
    }

    /**
     * ProxyPoint is a method point that describes
     * the need to wrap around a proxy.
     */
    private final class ProxyPoint {

        private final String pointName;

        private final Class<?>[] paramTypes;

        ProxyPoint(String pointName, Class<?>[] paramTypes) {
            AssertUtil.isTrue(!StringUtil.isBlank(pointName), "The proxy point name cannot be blank.");
            AssertUtil.nonNull(paramTypes, "The proxy point param types cannot be null.");
            this.pointName = pointName;
            this.paramTypes = paramTypes;
        }

        String getPointName() {
            return pointName;
        }

        Class<?>[] getParamTypes() {
            return paramTypes;
        }

        LeetcodeInvoker findPoint(Class<?> klass) {
            AssertUtil.nonNull(klass, "The class cannot be null.");
            try {
                return LeetcodeInvokerFactory.getLeetcodeInvoker(ReflectUtil.getMethod(klass, this.pointName,
                        this.paramTypes));
            } catch (Exception e) {
                e.printStackTrace();
                throw new EnhancerException("Cannot found proxy point in class " + klass.getSimpleName() + ", point " + "name is " + this.pointName + ", param type is " + Arrays.toString(paramTypes) + ".");
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProxyPoint that = (ProxyPoint) o;
            return Objects.equals(pointName, that.pointName) && Arrays.equals(paramTypes, that.paramTypes);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(pointName);
            result = 31 * result + Arrays.hashCode(paramTypes);
            return result;
        }
    }

    /*************** STATIC PROXY FUNCTIONS START***************/

    /**
     * Static proxy on method :
     * getEnhancementPoint().
     *
     * @return the enhancement point.
     */
    @Override
    public Method getEnhancementPoint() {
        return proxyPointInvoke("getEnhancementPoint", Method.class);
    }

    /**
     * Static proxy on method :
     * getInputProvider().
     *
     * @return the input provider.
     */
    @Override
    public InputProvider getInputProvider() {
        return proxyPointInvoke("getInputProvider", InputProvider.class);
    }

    /**
     * Static proxy on method :
     * getOutputConsumer().
     *
     * @return the output consumer.
     */
    @Override
    public OutputConsumer getOutputConsumer() {
        return proxyPointInvoke("getOutputConsumer", OutputConsumer.class);
    }

    /**
     * Static proxy on method :
     * getOutputPrintStrategies().
     *
     * @return the output print strategies list.
     */
    @Override
    public List<BasePrintingStrategy<?>> getOutputPrintStrategies() {
        return proxyPointInvoke("getOutputPrintStrategies", List.class);
    }

    /**
     * Static proxy on method :
     * getEnhancerLogLevel().
     *
     * @return the enhancer log level.
     */
    @Override
    public Level getEnhancerLogLevel() {
        return proxyPointInvoke("getEnhancerLogLevel", Level.class);
    }

    /*************** STATIC PROXY FUNCTIONS END***************/
}
