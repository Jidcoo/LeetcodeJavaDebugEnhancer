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
import io.github.jidcoo.opto.lcdb.enhancer.base.InputProvider;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.builtin.ConsoleInputProvider;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.builtin.FileInputProvider;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.builtin.MultipleInputProvider;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.builtin.StringInputProvider;
import io.github.jidcoo.opto.lcdb.enhancer.core.proxy.ProxyPointInterceptor;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ContainerCheckUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.StringUtil;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * <p>InputProviderRequireInterceptor is a proxy-point
 * interceptor used to support annotating config input
 * provider using {@link Require} annotation.
 *
 * @author Jidcoo
 * @see InputProvider
 * @see AbsIORequireSupporter
 * @see ProxyPointInterceptor
 * @since 1.0.2
 */
@Require(types = ProxyPointInterceptor.class)
final class InputProviderRequireInterceptor extends AbsIORequireSupporter<InputProvider>
        implements ProxyPointInterceptor<InputProvider> {

    /**
     * Create an InputProviderRequireInterceptor instance.
     */
    @SuppressWarnings("all")
    InputProviderRequireInterceptor() {
        enableIOSourceType(ConsoleInputProvider.class, (source) -> new ConsoleInputProvider());
        enableIOSourceType(StringInputProvider.class, (source) -> new StringInputProvider(source));
        enableIOSourceType(FileInputProvider.class, (source) -> {
            AssertUtil.isTrue(!StringUtil.isBlank(source), "The file path string value cannot be blank in " +
                    "FileInputProvider Require type.");
            try {
                return new FileInputProvider(source);
            } catch (FileNotFoundException e) {
                throw new EnhancerException("The file cannot found in FileInputProvider Require: " + source, e);
            }
        });
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
     * Return the proxy point name that needs to
     * be intercepted.
     *
     * @return the proxy point name.
     */
    @Override
    public String interceptPoint() {
        return "getInputProvider";
    }

    /**
     * Intercept and process proxy point result
     * after invoking proxy point.
     *
     * @param leetcodeJavaDebugEnhancer the LeetcodeJavaDebugEnhancer instance.
     * @param inputProvider             the proxy point result.
     * @return the processed proxy point result.
     */
    @Override
    @SuppressWarnings("all")
    public InputProvider onAfter(LeetcodeJavaDebugEnhancer leetcodeJavaDebugEnhancer, InputProvider inputProvider) {
        List<InputProvider> inputProviderList = awareIOSources(leetcodeJavaDebugEnhancer);
        if (!ContainerCheckUtil.isListEmpty(inputProviderList)) {
            if (Objects.nonNull(inputProvider)) {
                inputProviderList.add(inputProvider);
            }
            inputProvider = new MultipleInputProvider(inputProviderList);
        }
        return inputProvider;
    }
}
