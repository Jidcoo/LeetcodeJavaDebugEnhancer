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

package io.github.jidcoo.opto.lcdb.enhancer.core.parser.builtin;

import io.github.jidcoo.opto.lcdb.enhancer.base.BaseParameterAcceptStrategy;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.base.Strategizable;
import io.github.jidcoo.opto.lcdb.enhancer.utils.GsonUtil;

import javax.lang.model.type.NullType;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Set;

/**
 * <p>GodParameterAcceptStrategy is an parameter
 * acceptance strategy.</p>
 * <p>The parameter acceptance ability of
 * GodParameterAcceptStrategy depends on Gson.
 * See {@link GsonUtil}.</p>
 * <p>And also GodParameterAcceptStrategy is a great
 * ParameterAcceptStrategy that it can accept
 * the {@link Object}, the {@link Void}, the eight
 * basic data types and their wrapped types of Java,
 * the {@link NullType} value and so on.</p>
 * <p>How magical!!! So I named it <tt>God</tt> -
 * ParameterAcceptStrategy.</p>
 *
 * @author Jidcoo
 * @see BaseParameterAcceptStrategy
 * @see GsonUtil
 * @since 1.0
 */
@Require
public final class GodParameterAcceptStrategy extends BaseParameterAcceptStrategy<Object> {

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
        // This can handle most situations!!!
        // How magical!!!
        // How beautiful the world is!!!
        return GsonUtil.fromJson(GsonUtil.toJson(object), type.getParameterizedType());
    }

    /**
     * Get the order of the object.
     *
     * @return the int order of the object.
     */
    @Override
    public int getOrder() {
        // GodParameterAcceptStrategy is the lowest priority strategy.
        return Integer.MIN_VALUE;
    }

    /**
     * Get the acceptable type.
     *
     * @return the acceptable type.
     */
    @Override
    public Class<?> getAcceptableType() {
        // Return Void.class to accept most types.
        return Void.class;
    }
}
