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

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * A common reflect util for java reflect feature.
 *
 * @author Jidcoo
 * @since 1.0
 */
public class ReflectUtil {

    /**
     * Create an instance from the class by default class constructor.
     *
     * @param clazz clazz object.
     * @return the instance created by the input class.
     */
    public static <T> T createInstance(Class<T> clazz) {
        AssertUtil.nonNull(clazz, "The class cannot be null.");
        try {
            Constructor<T> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Resolve the inner class Solution and instantiate
     * it from the <tt>AT</tt> object.
     *
     * @param object the <tt>AT</tt> object.
     * @return the Solution instance.
     */
    public static Object resolveSolutionInstance(Object object) {
        AssertUtil.nonNull(object, "The object cannot be null.");
        AssertUtil.isTrue((object instanceof LeetcodeJavaDebugEnhancer), "The object is not an inherited object from "
                + "LeetcodeJavaDebugEnhancer");
        Class<?> __AT__class = object.getClass();
        Class<?>[] declaredClasses = __AT__class.getDeclaredClasses();
        for (Class<?> declaredClass : declaredClasses) {
            // Match the simple name of the class.
            if ("Solution".equals(declaredClass.getSimpleName())) {
                try {
                    Constructor<?> declaredConstructor = declaredClass.getDeclaredConstructor(__AT__class);
                    declaredConstructor.setAccessible(true);
                    return declaredConstructor.newInstance(object);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // Sorry, we cannot find any inner class named "Solution".
        throw new RuntimeException("No inner class Solution found in class: " + object.getClass());
    }

    /**
     * Check if the clazz implements the interface.
     *
     * @param clazz          the class.
     * @param interfaceClass the interface.
     * @return true if the class implements the interface.
     */
    public static boolean isImplementInterface(Class<?> clazz, Class<?> interfaceClass) {
        AssertUtil.nonNull(clazz, "The class cannot be null.");
        AssertUtil.nonNull(interfaceClass, "The interface cannot be null.");
        return Arrays.stream(clazz.getInterfaces()).anyMatch(clz -> interfaceClass == clz);
    }

    /**
     * Check if the clazz extends the super class.
     *
     * @param clazz      the class.
     * @param superClass this super class.
     * @return true if the class extends the super class.
     */
    public static boolean isExtendsClass(Class<?> clazz, Class<?> superClass) {
        AssertUtil.nonNull(clazz, "The class cannot be null.");
        AssertUtil.nonNull(superClass, "The super class cannot be null.");
        return superClass.isAssignableFrom(clazz);
    }

    /**
     * Get field from the object by field name and field type.
     *
     * @param fieldName the field name.
     * @param fieldType the field type.
     * @param obj       the object.
     * @return the field
     */
    public static Field getField(String fieldName, Class fieldType, Object obj) {
        AssertUtil.isTrue(!StringUtil.isBlank(fieldName), "The fieldName cannot be blank.");
        AssertUtil.nonNull(fieldType, "The fieldType cannot be null.");
        AssertUtil.nonNull(obj, "The obj cannot be null.");
        try {
            Field declaredField = obj.getClass().getDeclaredField(fieldName);
            AssertUtil.isTrue(Objects.equals(declaredField.getType(), fieldType),
                    "The type of the field " + fieldName + " in object " + obj + " is " + declaredField.getType().getSimpleName() + ", not " + fieldType.getSimpleName() + ".");
            return declaredField;
        } catch (Exception | Error e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get field value from the object by field name and field type.
     *
     * @param fieldName the field name.
     * @param fieldType the field type.
     * @param obj       the object.
     * @return the field value
     */
    public static <T> T getFieldValue(String fieldName, Class<? extends T> fieldType, Object obj) {
        Field field = getField(fieldName, fieldType, obj);
        try {
            field.setAccessible(true);
            return (T) field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set field value to the object by field name and field type.
     *
     * @param fieldName the field name.
     * @param fieldType the field type.
     * @param value     the field value.
     * @param obj       the object.
     */
    public static <T> void setFieldValue(String fieldName, Class<? extends T> fieldType, Object value, Object obj) {
        Field field = getField(fieldName, fieldType, obj);
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the method from the target class by the specified
     * method name and the specified parameter types.
     *
     * @param target         the target class.
     * @param methodName     the method name.
     * @param parameterTypes the parameter types.
     * @return the method.
     */
    public static Method getMethod(Class<?> target, String methodName, Class<?>... parameterTypes) {
        AssertUtil.nonNull(target, "The target class cannot be null.");
        AssertUtil.isTrue(!StringUtil.isBlank(methodName), "The method name cannot be blank.");
        try {
            return target.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
