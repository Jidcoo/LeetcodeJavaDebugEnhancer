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
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.base.Strategizable;
import io.github.jidcoo.opto.lcdb.enhancer.utils.*;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
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
 * of input objects based on built-in or external parameter
 * acceptance strategies by {@link #acceptParameter(Object, Type, Map)}
 * </p>
 *
 * @author Jidcoo
 * @see BaseParameterAcceptStrategy
 * @see ParameterAcceptResult
 * @see IRMatchInputParserNode
 * @since 1.0
 */
final class ParameterAcceptor extends BaseParameterAcceptStrategy<ParameterAcceptResult> {

    /**
     * Builtin parameter acceptance strategy map.
     */
    private final Map<Class<?>, Set<BaseParameterAcceptStrategy<?>>> builtinAcceptStrategyMap;

    /**
     * Built-in  parameter acceptance strategy set package location.
     */
    private static final String BUILT_IN_PARAMETER_ACCEPTANCE_STRATEGY_PACKAGE = "io.github.jidcoo.opto.lcdb.enhancer.core.parser.builtin";

    /**
     * Create a ParameterAcceptor instance.
     */
    @SuppressWarnings("all")
    ParameterAcceptor() {
        this.builtinAcceptStrategyMap = new HashMap<>();
        // Collect all builtin parameter acceptance strategies.
        List<BaseParameterAcceptStrategy> strategies = BeanUtil.collectBeans(BaseParameterAcceptStrategy.class,
                BUILT_IN_PARAMETER_ACCEPTANCE_STRATEGY_PACKAGE,
                (Class type) -> type.isAnnotationPresent(Require.class) && ReflectUtil.isExtendsClass(type,
                        BaseParameterAcceptStrategy.class) && !Modifier.isAbstract(type.getModifiers()), (Class<?
                        extends BaseParameterAcceptStrategy> beanType) -> ReflectUtil.createInstance(beanType)).stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (ContainerUtil.isNotEmpty(strategies)) {
            // Add all strategies to the builtinAcceptStrategyMap.
            for (BaseParameterAcceptStrategy<?> strategy : strategies) {
                addParameterAcceptStrategy(strategy.getAcceptableType(), strategy, builtinAcceptStrategyMap);
            }
        }
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
    protected ParameterAcceptResult acceptParameter(Object object, Type type,
                                                    Map<Class<?>, Set<BaseParameterAcceptStrategy<?>>> strategiesMap) {
        if (Objects.isNull(strategiesMap)) {
            // Use built-in parameter accepting strategies.
            strategiesMap = this.builtinAcceptStrategyMap;
        }
        return commonAcceptingFunction(strategiesMap, type, object);
    }

    /**
     * Get the order of the object.
     *
     * @return the int order of the object.
     */
    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    /**
     * Get the acceptable type.
     *
     * @return the acceptable type.
     */
    @Override
    public Class<ParameterAcceptResult> getAcceptableType() {
        return ParameterAcceptResult.class;
    }

    /**
     * Return a combination strategy set containing built-in strategies and custom strategies.
     *
     * @param  strategies the custom strategies.
     * @return null if the custom strategies list is empty,
     *         else a combination parameter accepting strategy set.
     * @since 1.0.3
     */
    Map<Class<?>, Set<BaseParameterAcceptStrategy<?>>> combineCustomStrategies(List<BaseParameterAcceptStrategy<?>> strategies) {
        if (ContainerUtil.isEmpty(strategies)) {
            return null;
        }
        Map<Class<?>, Set<BaseParameterAcceptStrategy<?>>> combinedCustomStrategiesMap = new HashMap<>();
        // Build map by origin builtinAcceptStrategyMap.
        this.builtinAcceptStrategyMap.forEach((key, val) -> {
            val.forEach(strategy -> addParameterAcceptStrategy(key, strategy, combinedCustomStrategiesMap));
        });
        // Combine the custom strategies.
        strategies.forEach(strategy -> addParameterAcceptStrategy(strategy.getAcceptableType(), strategy,
                combinedCustomStrategiesMap));
        return combinedCustomStrategiesMap;
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
                key -> new TreeSet<>(OrderUtil.descComparator()));
        // Add the strategy to the set.
        strategySet.add(strategy);
    }
}
