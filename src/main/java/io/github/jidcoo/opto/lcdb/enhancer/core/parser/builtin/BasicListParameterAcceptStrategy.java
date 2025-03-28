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
import io.github.jidcoo.opto.lcdb.enhancer.core.parser.ParameterAcceptResult;
import io.github.jidcoo.opto.lcdb.enhancer.utils.*;

import java.lang.reflect.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>BasicListParameterAcceptStrategy is a parameter
 * acceptance strategy used to accept {@link java.util.List} type
 * and accept it to a array as {@link java.util.List}
 * instance.</p>
 *
 * @author Jidcoo
 * @see BaseParameterAcceptStrategy
 * @since 1.0.3
 */
@Require
@SuppressWarnings("rawtypes")
public final class BasicListParameterAcceptStrategy extends BaseParameterAcceptStrategy<List> {

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
    protected List<?> acceptParameter(Object object, Type type,
                                      Map<Class<?>, Set<BaseParameterAcceptStrategy<?>>> strategiesMap) {
        AssertUtil.nonNull(object, "The object cannot be null.");
        AssertUtil.isTrue((object instanceof String || object instanceof List),
                "The object is not a String object or a List object.");
        Type elementType = TypeUtil.obtainListElementType(type);
        Type elementRawType = TypeUtil.obtainRawTypeOfType(elementType);
        List<?> originList = object instanceof List ? (List<?>) object : parseString2List((String) object);
        if (elementRawType == Object.class) {
            return originList;
        }
        return originList.stream().map(ele -> {
            if (Objects.nonNull(ele) && ele.getClass().equals(elementRawType)) {
                // Quick return.
                return ele;
            }
            ParameterAcceptResult parameterAcceptResult = commonAcceptingFunction(strategiesMap, elementType, ele);
            if (parameterAcceptResult.isAccepted()) {
                return parameterAcceptResult.getObject();
            }
            String logBuf = "BasicListParameterAcceptStrategy: Cannot accept list ele: " +
                    ele +
                    ", ele-type: " +
                    elementType +
                    ": " +
                    parameterAcceptResult;
            EnhancerLogUtil.logW("%s", logBuf);
            throw new RuntimeException("Cannot accept list element: " + ele);
        }).collect(Collectors.toList());
    }

    /**
     * Get the order of the object.
     *
     * @return the int order of the object.
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * Get the acceptable type.
     *
     * @return the acceptable type.
     */
    @Override
    public Class<? extends List> getAcceptableType() {
        return List.class;
    }

    private List<?> parseString2List(String string) {
        AssertUtil.isTrue(!StringUtil.isBlank(string), "The string cannot be blank.");
        return GsonUtil.fromJson(string, List.class);
    }
}
