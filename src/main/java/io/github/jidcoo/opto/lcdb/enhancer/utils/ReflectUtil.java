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
     * Create an instance from the class by custom class constructor.
     *
     * @param clazz                     clazz object.
     * @param constructorParameterTypes the parameter type array used to find constructor.
     * @param constructorParameters     the parameters used to initialize object.
     * @return the instance created by the class.
     * @since 1.0.1
     */
    public static <T> T createInstance(Class<T> clazz, Class<?>[] constructorParameterTypes,
                                       Object... constructorParameters) {
        AssertUtil.nonNull(clazz, "The class cannot be null.");
        if (Objects.isNull(constructorParameterTypes)) {
            constructorParameterTypes = new Class[0];
        }
        try {
            Constructor<T> declaredConstructor = clazz.getDeclaredConstructor(constructorParameterTypes);
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(constructorParameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create an instance from the class by default class constructor.
     *
     * @param clazz clazz object.
     * @return the instance created by the class.
     * @since 1.0.1
     */
    public static <T> T createInstance(Class<T> clazz) {
        return createInstance(clazz, null);
    }

    /**
     * Resolve all inner-class from the given class.
     *
     * @param clazz the given class object.
     * @return the inner-class array in the given class.
     * @since 1.0.1
     */
    public static Class<?>[] resolveInnerClasses(Class<?> clazz) {
        AssertUtil.nonNull(clazz, "The clazz cannot be null.");
        return clazz.getDeclaredClasses();
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
     * @since 1.0.1
     */
    public static Field getField(String fieldName, Class fieldType, Object obj) {
        AssertUtil.isTrue(!StringUtil.isBlank(fieldName), "The fieldName cannot be blank.");
        AssertUtil.nonNull(fieldType, "The fieldType cannot be null.");
        AssertUtil.nonNull(obj, "The obj cannot be null.");
        Field field = null;
        Class<?> classFinder = obj.getClass();
        while (Objects.isNull(field) && Objects.nonNull(classFinder)) {
            try {
                field = classFinder.getDeclaredField(fieldName);
            } catch (Exception | Error e) {
                classFinder = classFinder.getSuperclass();
            }
        }
        AssertUtil.nonNull(field, "Cannot match any field by field name [" + fieldName + "] in object: " + obj);
        AssertUtil.isTrue(Objects.equals(field.getType(), fieldType),
                "The type of the field " + fieldName + " in " + "object " + obj + " is " + field.getType().getSimpleName() + ", not " + fieldType.getSimpleName() + ".");
        return field;
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
        Method method = null;
        Class<?> classFinder = target;
        while (Objects.isNull(method) && Objects.nonNull(classFinder)) {
            try {
                method = classFinder.getDeclaredMethod(methodName, parameterTypes);
            } catch (Exception | Error e) {
                classFinder = classFinder.getSuperclass();
            }
        }
        AssertUtil.nonNull(method,
                "Cannot match any method by method name [" + methodName + "] and parameters " + Arrays.toString(parameterTypes) + " in class: " + target.getName());
        return method;
    }
}
