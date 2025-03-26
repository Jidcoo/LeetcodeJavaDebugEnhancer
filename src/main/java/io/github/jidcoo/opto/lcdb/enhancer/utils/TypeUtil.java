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

package io.github.jidcoo.opto.lcdb.enhancer.utils;

import java.lang.reflect.*;
import java.util.List;

/**
 * Java type util.
 *
 * @author Jidcoo
 * @since 1.0.3
 */
public class TypeUtil {

    /**
     * Obtain list element type from a {@link List} type.
     *
     * @param listType a {@link List} type.
     * @return the list element type.
     */
    public static Type obtainListElementType(Type listType) {
        if (listType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) listType;
            Type rawType = pt.getRawType();

            AssertUtil.isTrue((rawType instanceof Class && List.class.isAssignableFrom((Class<?>) rawType)),
                    "The type must be a java.util.List type.");

            Type[] typeArgs = pt.getActualTypeArguments();
            if (typeArgs.length == 0) {
                return Object.class;
            }

            Type firstArg = typeArgs[0];
            if (firstArg instanceof WildcardType) {
                WildcardType wt = (WildcardType) firstArg;
                Type[] upperBounds = wt.getUpperBounds();
                return upperBounds.length > 0 ? upperBounds[0] : Object.class;
            }
            return firstArg;
        } else if (listType instanceof Class && List.class.isAssignableFrom((Class<?>) listType)) {
            return Object.class;
        }
        throw new IllegalArgumentException("The type must be a java.util.List type.");
    }

    /**
     * Obtain raw type from the specified type.
     *
     * @param type the specified type.
     * @return the raw type of the specified type.
     */
    public static Class<?> obtainRawTypeOfType(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            return obtainRawTypeOfType(rawType);
        } else if (type instanceof GenericArrayType) {
            GenericArrayType arrayType = (GenericArrayType) type;
            Type componentType = arrayType.getGenericComponentType();
            Class<?> componentRawType = obtainRawTypeOfType(componentType);
            return Array.newInstance(componentRawType, 0).getClass();
        } else if (type instanceof TypeVariable) {
            return Object.class;
        } else if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            Type[] upperBounds = wildcardType.getUpperBounds();
            return upperBounds.length > 0 ? obtainRawTypeOfType(upperBounds[0]) : Object.class;
        }
        throw new IllegalArgumentException("Cannot obtain raw type: " + type);
    }
}
