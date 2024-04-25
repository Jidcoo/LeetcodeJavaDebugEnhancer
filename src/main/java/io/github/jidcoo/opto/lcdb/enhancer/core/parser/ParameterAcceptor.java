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

package io.github.jidcoo.opto.lcdb.enhancer.core.parser;

import io.github.jidcoo.opto.lcdb.enhancer.base.BaseParameterAcceptStrategy;
import io.github.jidcoo.opto.lcdb.enhancer.base.Order;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.base.Strategizable;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.BeanUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ContainerCheckUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ReflectUtil;

import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>ParameterAcceptor is a parameter acceptor
 * used to dynamically accept leetcode input as
 * input parameters that match the type of
 * leetcode invoker.
 * </p>
 *
 * <p>ParameterAcceptor performs appropriate acceptance
 * of input objects based on built-in parameter
 * acceptance strategies and external acceptance
 * strategies by {@link #accept(Parameter, Object)}.
 * </p>
 *
 * @author Jidcoo
 * @see BaseParameterAcceptStrategy
 * @see ParameterAcceptResult
 * @see IRMatchInputParserNode
 * @since 1.0
 */
final class ParameterAcceptor extends BaseParameterAcceptStrategy<Object> {

    /**
     * Builtin parameter acceptance strategy map.
     */
    private Map<Class<?>, Set<BaseParameterAcceptStrategy<?>>> builtinAcceptStrategyMap;

    /**
     * BaseParameterAcceptStrategy comparator.
     */
    private final Comparator<Order> strategyComparator = Comparator.comparingInt(Order::getOrder).reversed();

    /**
     * Built-in  parameter acceptance strategy set package location.
     */
    private static final String BUILT_IN_PARAMETER_ACCEPTANCE_STRATEGY_PACKAGE = "io.github.jidcoo.opto.lcdb.enhancer.core.parser.builtin";

    /**
     * Create a ParameterAcceptor instance.
     */
    ParameterAcceptor() {
        this.builtinAcceptStrategyMap = new HashMap<>();
        // Collect all builtin parameter acceptance strategies.
        List<BaseParameterAcceptStrategy> strategies = BeanUtil.collectBeans(BaseParameterAcceptStrategy.class,
                BUILT_IN_PARAMETER_ACCEPTANCE_STRATEGY_PACKAGE,
                (Class type) -> type.isAnnotationPresent(Require.class) && ReflectUtil.isExtendsClass(type,
                        BaseParameterAcceptStrategy.class) && !Modifier.isAbstract(type.getModifiers()), (Class<?
                        extends BaseParameterAcceptStrategy> beanType) -> ReflectUtil.createInstance(beanType)).stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (!ContainerCheckUtil.isListEmpty(strategies)) {
            // Add all strategies to the builtinAcceptStrategyMap.
            for (BaseParameterAcceptStrategy<?> strategy : strategies) {
                addParameterAcceptStrategy(strategy.getAcceptableType(), strategy, builtinAcceptStrategyMap);
            }
        }
    }

    /**
     * Wrap the strategy add function.
     *
     * @param type        the accepted class type.
     * @param strategy    the acceptance strategy.
     * @param strategyMap the acceptance strategy map.
     */
    private void addParameterAcceptStrategy(Class<?> type, BaseParameterAcceptStrategy<?> strategy, Map<Class<?>,
            Set<BaseParameterAcceptStrategy<?>>> strategyMap) {
        AssertUtil.nonNull(strategy, "The parameter acceptance strategy cannot be null.");
        AssertUtil.nonNull(type, "The type of the " + strategy + " cannot be null.");
        // Get the strategySet by clazz.
        Set<BaseParameterAcceptStrategy<?>> strategySet = strategyMap.computeIfAbsent(type,
                key -> new TreeSet<>(strategyComparator));
        // Add the strategy to the set.
        strategySet.add(strategy);
    }

    /**
     * Accept an object with the parameter type.
     *
     * @param invokerParameterType the leetcode invoker parameter type.
     * @param object               the input object for accepting.
     * @return the parameter acceptance result.
     */
    public ParameterAcceptResult accept(Parameter invokerParameterType, Object object) {
        // Create a tracer stack for tracking the acceptance process.
        Stack<ParameterAcceptStrategyTracer> tracerStack = new Stack<>();

        try {
            // Find the strategy set for the parameter acceptance.
            Set<BaseParameterAcceptStrategy<?>> strategySet = findStrategySet(invokerParameterType.getType(),
                    builtinAcceptStrategyMap);
            for (BaseParameterAcceptStrategy<?> acceptStrategy : strategySet) {
                try {
                    // Try to accept the parameter and return the accepted result.
                    return ParameterAcceptResult.accept(acceptStrategy.accept(invokerParameterType, object,
                            builtinAcceptStrategyMap));
                } catch (Throwable e) {
                    // Push the throwable with the object tracer into stack.
                    tracerStack.push(new ParameterAcceptStrategyTracer(acceptStrategy.getClass().getName(), e));
                }
            }
        } catch (Throwable throwable) {
            // Push the throwable with the object tracer into stack.
            tracerStack.push(new ParameterAcceptStrategyTracer(null, throwable));
        }

        // Return the rejected result.
        return ParameterAcceptResult.reject(object, tracerStack);
    }

    /**
     * Accept the object.
     *
     * @param object        the object.
     * @param type          the parameter type.
     * @param strategiesMap the strategies map that can be used during this accepting process.
     *                      <p>The key is the output object class to which this BaseParameterAcceptStrategy
     *                      applies. The value is a set of strategy with the same accepted type.
     *                      And the set is sorted the priority of {@link Strategizable} based on
     *                      {@code getOrder()}.
     *                      </p>
     * @return the accepted parameter.
     */
    @Override
    protected Object acceptParameter(Object object, Parameter type,
                                     Map<Class<?>, Set<BaseParameterAcceptStrategy<?>>> strategiesMap) throws Throwable {
        // This method is not supported in ParameterAcceptor.
        throw new RuntimeException("Unsupported!");
    }

    /**
     * Get the order of the object.
     *
     * @return the int order of the object.
     */
    @Override
    public int getOrder() {
        // This method is not supported in ParameterAcceptor.
        throw new RuntimeException("Unsupported!");
    }

    /**
     * Get the acceptable type.
     *
     * @return the acceptable type.
     */
    @Override
    public Class<?> getAcceptableType() {
        // This method is not supported in ParameterAcceptor.
        throw new RuntimeException("Unsupported!");
    }
}
