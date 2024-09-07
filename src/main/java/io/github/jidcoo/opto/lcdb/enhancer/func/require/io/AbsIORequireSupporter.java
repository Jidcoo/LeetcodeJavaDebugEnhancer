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

package io.github.jidcoo.opto.lcdb.enhancer.func.require.io;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.base.EnhancerException;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.base.Requires;
import io.github.jidcoo.opto.lcdb.enhancer.core.proxy.DebugEnhancerProxy;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ContainerCheckUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.EnhancerLogUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>AbsIORequireSupporter is an abstract class used to
 * support annotating config input and output sources
 * using {@link Require} annotation.
 *
 * @author Jidcoo
 * @see Require
 * @see io.github.jidcoo.opto.lcdb.enhancer.base.InputProvider
 * @see io.github.jidcoo.opto.lcdb.enhancer.base.OutputConsumer
 * @since 1.0.2
 */
abstract class AbsIORequireSupporter<IO_SOURCE> {

    /**
     * Acceptable require io source types in @Require annotation.
     */
    private final Map<Class<? extends IO_SOURCE>, Function<String, IO_SOURCE>> ACCEPTABLE_IO_SOURCE_TYPES =
            new ConcurrentHashMap<>();

    /**
     * Enable an io source type to require interceptor.
     *
     * @param type      the io source type.
     * @param generator the io source generator
     */
    protected final void enableIOSourceType(Class<? extends IO_SOURCE> type, Function<String, IO_SOURCE> generator) {
        AssertUtil.nonNull(type, "The io source type cannot be null.");
        AssertUtil.nonNull(type, "The io source generator cannot be null.");
        ACCEPTABLE_IO_SOURCE_TYPES.put(type, generator);
    }

    /**
     * Disable an io source type to require interceptor.
     *
     * @param type the io source type.
     * @return the io source generator for the io source type you want to disable.
     */
    protected final Function<String, IO_SOURCE> disableIOSourceType(Class<? extends IO_SOURCE> type) {
        AssertUtil.nonNull(type, "The io source type cannot be null.");
        return ACCEPTABLE_IO_SOURCE_TYPES.remove(type);
    }

    /**
     * Generate the io source instance.
     *
     * @param source the io source.
     * @param type   the io source type.
     * @return the io source instance.
     */
    protected IO_SOURCE generateIOSource(String source, Class<? extends IO_SOURCE> type) {
        AssertUtil.nonNull(type, "The io source type cannot be null.");
        if (!ACCEPTABLE_IO_SOURCE_TYPES.containsKey(type)) {
            throw new EnhancerException("Unsupported io source type in IO-Require-Annotation interceptor: " + type.getSimpleName());
        }
        Function<String, IO_SOURCE> generator = ACCEPTABLE_IO_SOURCE_TYPES.get(type);
        AssertUtil.nonNull(generator, "The io source generator in type " + type.getSimpleName() + " cannot be null.");
        return generator.apply(source);
    }

    /**
     * Aware all acceptable io source require annotations.
     *
     * @param leetcodeJavaDebugEnhancer the LeetcodeJavaDebugEnhancer instance.
     * @return the acceptable input provider require annotations list.
     */
    @SuppressWarnings("all")
    protected List<Require> awareAcceptableIOSourceRequire(LeetcodeJavaDebugEnhancer leetcodeJavaDebugEnhancer) {
        LeetcodeJavaDebugEnhancer source = DebugEnhancerProxy.awareSource(leetcodeJavaDebugEnhancer);
        AssertUtil.nonNull(source, "The leetcodeJavaDebugEnhancer cannot be null.");
        Class<?> sourceKlass = source.getClass();
        Requires requiresAnnotation = sourceKlass.getAnnotation(Requires.class);
        Require requireAnnotation = sourceKlass.getAnnotation(Require.class);
        List<Require> requireList;
        if (Objects.nonNull(requiresAnnotation)) {
            requireList = Arrays.asList(requiresAnnotation.value());
        } else if (Objects.nonNull(requireAnnotation)) {
            requireList = Collections.singletonList(requireAnnotation);
        } else {
            return null;
        }
        requireList = requireList.stream().filter(require -> {
            Class<?>[] types = require.types();
            if (types.length == 0) {
                return false;
            }
            for (Class<?> type : types) {
                if (!ACCEPTABLE_IO_SOURCE_TYPES.containsKey(type)) {
                    EnhancerLogUtil.logW("Unaccepted io source type in IO-Require-Annotation(types are %s, values are" +
                                    " %s): %s",
                            Arrays.toString(types), Arrays.toString(require.values()), type.getSimpleName());
                    return false;
                }
            }
            return true;
        }).filter(require -> {
            int typesLength = require.types().length;
            int valuesLength = require.values().length;
            if (typesLength > 1 && typesLength > valuesLength) {
                EnhancerLogUtil.logW("Invalid types-length(%d) and values-length(%d) in IO-Require-Annotation(types " +
                                "are %s, values are %s): " +
                                "Only supports single type and multi value or types and values with the same length.",
                        typesLength, valuesLength, Arrays.toString(require.types()), Arrays.toString(require.values()));
                return false;
            }
            return true;
        }).collect(Collectors.toList());
        return requireList;
    }

    /**
     * Aware io sources from the specified LeetcodeJavaDebugEnhancer instance.
     *
     * @param leetcodeJavaDebugEnhancer the LeetcodeJavaDebugEnhancer instance.
     * @return the io sources list.
     */
    protected final List<IO_SOURCE> awareIOSources(LeetcodeJavaDebugEnhancer leetcodeJavaDebugEnhancer) {
        List<Require> requireList = awareAcceptableIOSourceRequire(leetcodeJavaDebugEnhancer);
        List<IO_SOURCE> ioSourceList = new ArrayList<>();
        if (!ContainerCheckUtil.isListEmpty(requireList)) {
            for (Require require : requireList) {
                Class<?>[] types = require.types();
                String[] values = require.values();
                List<IO_SOURCE> curSources;
                if (types.length == 1) {
                    @SuppressWarnings("unchecked")
                    Class<? extends IO_SOURCE> ioSourceType = (Class<? extends IO_SOURCE>) types[0];
                    curSources = Arrays.stream(values)
                            .map(source -> generateIOSource(source, ioSourceType))
                            .filter(Objects::nonNull).collect(Collectors.toList());
                } else {
                    curSources = IntStream.range(0, types.length).boxed().map((pos) -> {
                        @SuppressWarnings("unchecked")
                        Class<? extends IO_SOURCE> ioSourceType = (Class<? extends IO_SOURCE>) types[pos];
                        String source = values[pos];
                        return generateIOSource(source, ioSourceType);
                    }).filter(Objects::nonNull).collect(Collectors.toList());
                }
                if (!ContainerCheckUtil.isListEmpty(curSources)) {
                    ioSourceList.addAll(curSources);
                }
            }
        }
        return ioSourceList;
    }
}
