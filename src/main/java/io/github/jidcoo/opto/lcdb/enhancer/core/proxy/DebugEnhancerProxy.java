package io.github.jidcoo.opto.lcdb.enhancer.core.proxy;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.base.EnhancerException;
import io.github.jidcoo.opto.lcdb.enhancer.base.LeetcodeInvoker;
import io.github.jidcoo.opto.lcdb.enhancer.core.executor.LeetcodeInvokerFactory;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.StringUtil;

import java.util.*;

public final class DebugEnhancerProxy extends LeetcodeJavaDebugEnhancer {

    /**
     * No param proxy point type.
     */
    private static final Class<?>[] DEFAULT_NO_PARAM_TYPE = new Class[]{};

    /**
     * Proxy method point sets.
     */
    private final Set<ProxyPoint> PROXY_POINTS_SETS =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(new ProxyPoint("getEnhancementPoint",
                    DEFAULT_NO_PARAM_TYPE), new ProxyPoint("getInputProvider", DEFAULT_NO_PARAM_TYPE),
                    new ProxyPoint("getOutputConsumer", DEFAULT_NO_PARAM_TYPE), new ProxyPoint(
                            "getOutputPrintStrategies", DEFAULT_NO_PARAM_TYPE), new ProxyPoint("getEnhancerLogLevel",
                            DEFAULT_NO_PARAM_TYPE))));

    /**
     * Proxy point instances map.
     */
    private Map<String, LeetcodeInvoker> PROXY_POINTS_INSTANCES;

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
        PROXY_POINTS_SETS.forEach(proxyPoint -> PROXY_POINTS_INSTANCES.put(proxyPoint.getPointName(),
                proxyPoint.findPoint(targetClass)));


        this.target = target;
    }

    /**
     * Do proxy invoke.
     *
     * @param pointName  the proxy point name.
     * @param returnType the return type.
     * @param args       invoke args.
     * @return final proxy invoke result.
     */
    private <RETURN> RETURN proxyInvoke(String pointName, Class<RETURN> returnType, Object... args) {

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

    final class ProxyPoint {

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
                return LeetcodeInvokerFactory.getLeetcodeInvoker(klass.getDeclaredMethod(this.pointName,
                        this.paramTypes));
            } catch (NoSuchMethodException e) {
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
}
