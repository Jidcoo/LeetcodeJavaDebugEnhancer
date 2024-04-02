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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A bean util.
 *
 * @author Jidcoo
 * @since 1.0
 */
public class BeanUtil {

    /**
     * Collect beans with specified conditions under
     * the specified package.
     *
     * @param type        the bean type.
     * @param packageName the specified package name.
     * @param classFilter the class type filter.
     * @param beanCreator the bean creator.
     * @return the bean list.
     */
    public static <T> List<T> collectBeans(Class<? extends T> type, String packageName,
                                           Function<Class, Boolean> classFilter,
                                           Function<Class<? extends T>, T> beanCreator) {
        AssertUtil.nonNull(type, "The bean type cannot be null.");
        AssertUtil.isTrue(!StringUtil.isBlank(packageName), "The beans package name cannot be blank.");
        AssertUtil.nonNull(classFilter, "The class filter cannot be null.");
        AssertUtil.nonNull(beanCreator, "The bean creator cannot be null.");
        List<Class<?>> classesByPackage = PackageUtil.getClassesByPackage(packageName);
        if (!ContainerCheckUtil.isListEmpty(classesByPackage)) {
            return classesByPackage.stream().filter(klass -> classFilter.apply(klass)).map(klass -> beanCreator.apply((Class<? extends T>) klass)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
