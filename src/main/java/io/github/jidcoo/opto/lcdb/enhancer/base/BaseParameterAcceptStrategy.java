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

import java.util.Map;
import java.util.Set;

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
    protected abstract Parameter acceptParameter(Object object, java.lang.reflect.Parameter type,
                                                 Map<Class<?>, Set<BaseParameterAcceptStrategy<?>>> strategiesMap) throws Throwable;

    /**
     * Accept the object by the class type.
     *
     * @param classType     the class type.
     * @param object        the object.
     * @param strategiesMap the strategies map that can be used during the acceptance process.
     * @return the accepted output.
     */
    @Override
    public final Parameter accept(Object classType, Object object,
                                  Map<Class<?>, Set<BaseParameterAcceptStrategy<?>>> strategiesMap) throws Throwable {
        // Do real call the acceptParameter() method.
        return acceptParameter(object, (java.lang.reflect.Parameter) classType, strategiesMap);
    }
}
