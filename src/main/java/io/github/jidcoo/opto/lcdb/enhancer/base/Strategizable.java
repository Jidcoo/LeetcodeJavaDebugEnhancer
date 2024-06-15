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

import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ContainerCheckUtil;

import javax.lang.model.type.NullType;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * <p>Strategizable is an interface for
 * object acceptance strategies. </p>
 *
 * <p>Note: Strategizable extends from
 * the {@link Order}, please do not forget to
 * implement the {@link #getOrder()} method.
 * Because it will directly reflect the priority of
 * this strategizable object!!!
 * </p>
 *
 * @author Jidcoo
 * @since 1.0
 */
public interface Strategizable<AcceptableType, Output, Strategy> extends Order {

    /**
     * Get the acceptable type.
     *
     * @return the acceptable type.
     */
    Class<? extends AcceptableType> getAcceptableType();

    /**
     * Get the object type.
     *
     * <p>Return {@link NullType} if the object is null.</p>
     *
     * @param object the object to be accepted.
     * @return the object type.
     */
    default Class<?> getObjectType(Object object) {
        if (Objects.isNull(object)) {
            return NullType.class;
        }
        // Return the object if the object instanceof Class.
        if (object instanceof Class) {
            return (Class<?>) object;
        }
        return object.getClass();
    }

    /**
     * Check the object can accept for this acceptable class.
     *
     * @param object the object.
     * @throws RuntimeException if the accepted type get from
     *                          {@link #getAcceptableType()}
     *                          is null or the object is not
     *                          accepted for this acceptable
     *                          class.
     */
    default void checkObjectAcceptable(Object object) {
        Class<?> acceptedType;
        AssertUtil.nonNull((acceptedType = getAcceptableType()),
                "The acceptable type cannot be null in " + this.getClass().getSimpleName() + ".");
        AssertUtil.isTrue(Objects.equals(acceptedType, getObjectType(object)),
                "The object is not accepted in " + this.getClass().getSimpleName() + ": " + object);
    }

    /**
     * <p>Find the most appropriate acceptance
     * strategy set for the object to be accepted.
     * </p>
     *
     * @param object        the object to be accepted.
     * @param strategiesMap the available accepting strategies map.
     *                      The key is the output object class to which this Strategy
     *                      applies. The value is a set of strategy with the same accepted type.
     *                      And the set is sorted the priority of strategy based on {@code getOrder()}.
     * @return the strategy set.
     * @throws RuntimeException if not find any appropriate strategy set for the object.
     */
    default Set<Strategy> findStrategySet(Object object, final Map<Class<?>, Set<Strategy>> strategiesMap) {
        Set<Strategy> strategySet = strategiesMap.getOrDefault(getObjectType(object),
                strategiesMap.getOrDefault(Void.class, null));
        AssertUtil.isTrue(!ContainerCheckUtil.isSetEmpty(strategySet), "Cannot find any appropriate accepted " +
                "strategy" + " set for the object: " + object);
        return strategySet;
    }

    /**
     * Accept the object by the class type.
     *
     * @param classType     the class type.
     * @param object        the object.
     * @param strategiesMap the strategies map that can be used during the acceptance process.
     * @return the accepted output.
     */
    default Output accept(Object classType, Object object, Map<Class<?>, Set<Strategy>> strategiesMap) throws Throwable {
        // By default, a runtime exception is thrown here.
        throw new RuntimeException("Stub!");
    }
}
