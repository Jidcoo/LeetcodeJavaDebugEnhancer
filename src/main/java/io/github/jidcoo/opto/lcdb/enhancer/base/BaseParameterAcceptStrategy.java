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

package io.github.jidcoo.opto.lcdb.enhancer.base;

import io.github.jidcoo.opto.lcdb.enhancer.core.parser.ParameterAcceptResult;
import io.github.jidcoo.opto.lcdb.enhancer.core.parser.ParameterAcceptStrategyTracer;
import io.github.jidcoo.opto.lcdb.enhancer.utils.TypeUtil;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * <p>BaseParameterAcceptStrategy is an abstract class
 * for parameter accept strategies. </p>
 *
 * <p>When performing dynamic matching between
 * the leetcode invoker and leetcode input, it is necessary
 * to use BaseParameterAcceptStrategy to specialize
 * certain parameters and parameter types in order to
 * meet the matching rules of the leetcode invoker and
 * leetcode input.
 * </p>
 *
 * @author Jidcoo
 * @see Strategizable
 * @see Order
 * @since 1.0
 */
public abstract class BaseParameterAcceptStrategy<Parameter> implements Strategizable<Parameter, Parameter,
        BaseParameterAcceptStrategy<?>> {

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
    protected abstract Parameter acceptParameter(Object object, Type type,
                                                 Map<Class<?>, Set<BaseParameterAcceptStrategy<?>>> strategiesMap);

    /**
     * Common accepting parameter function.
     *
     * @param strategies    the strategy set for parameter accepting.
     * @param parameterType the parameter type.
     * @param object        the parameter object.
     * @return {@link ParameterAcceptResult}
     * @since 1.0.3
     */
    protected ParameterAcceptResult commonAcceptingFunction(Map<Class<?>, Set<BaseParameterAcceptStrategy<?>>> strategies,
                                                            Type parameterType,
                                                            Object object) {
        // Create a tracer stack for tracking the acceptance process.
        Stack<ParameterAcceptStrategyTracer> tracerStack = new Stack<>();

        try {
            // Find the strategy set for the parameter acceptance.
            Set<BaseParameterAcceptStrategy<?>> strategySet = findStrategySet(
                    TypeUtil.obtainRawTypeOfType(parameterType),
                    strategies
            );
            for (BaseParameterAcceptStrategy<?> acceptStrategy : strategySet) {
                try {
                    // Try to accept the parameter and return the accepted result.
                    return ParameterAcceptResult.accept(acceptStrategy.accept(parameterType, object, strategies));
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
     * Accept the object by the class type.
     *
     * @param classType     the class type.
     * @param object        the object.
     * @param strategiesMap the strategies map that can be used during the acceptance process.
     * @return the accepted output.
     */
    @Override
    public final Parameter accept(Type classType, Object object,
                                  Map<Class<?>, Set<BaseParameterAcceptStrategy<?>>> strategiesMap) throws Throwable {
        // Do real call the acceptParameter() method.
        return acceptParameter(object, classType, strategiesMap);
    }
}
