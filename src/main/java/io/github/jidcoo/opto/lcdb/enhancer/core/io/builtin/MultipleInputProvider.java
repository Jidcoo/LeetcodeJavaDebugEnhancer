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

package io.github.jidcoo.opto.lcdb.enhancer.core.io.builtin;

import io.github.jidcoo.opto.lcdb.enhancer.base.InputProvider;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ContainerCheckUtil;

import java.io.Closeable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>MultipleInputProvider is a {@link InputProvider} that
 * supports multiple <b>serial</b> input sources.</p>
 *
 * @author Jidcoo
 * @see InputProvider
 * @since 1.0.2
 */
public final class MultipleInputProvider implements InputProvider {

    /**
     * All available input providers.
     */
    private final List<InputProvider> providers;

    /**
     * Current enable input provider position.
     */
    private int pos = 0;

    /**
     * The input provider closed flag.
     */
    private boolean closeFlag = false;

    /**
     * Create a MultipleInputProvider by input provider list.
     *
     * @param inputProviderList the non-null and non-empty input provider list
     */
    public MultipleInputProvider(List<InputProvider> inputProviderList) {
        AssertUtil.nonNull(inputProviderList, "The inputProviderList cannot be null.");
        inputProviderList = inputProviderList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        AssertUtil.isTrue(!ContainerCheckUtil.isListEmpty(inputProviderList), "The inputProviderList cannot be empty.");
        this.providers = Collections.unmodifiableList(inputProviderList);
    }

    /**
     * Provide a next string input.
     *
     * @return a string input.
     */
    @Override
    public String provideNextInput() {
        if (closeFlag) {
            return null;
        }
        if (pos >= providers.size()) {
            return null;
        }
        String input = providers.get(pos).provideNextInput();
        if (isEnd(input)) {
            pos++;
            return provideNextInput();
        }
        return input;
    }

    /**
     * Determine if the input is ending.
     *
     * @param input a string input got from {@link #provideNextInput()}}.
     * @return true if the input is ending.
     */
    @Override
    public boolean isEnd(String input) {
        if (closeFlag) {
            return true;
        }
        if (pos >= providers.size()) {
            return true;
        }
        return providers.get(pos).isEnd(input);
    }

    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     *
     * <p>While this interface method is declared to throw {@code
     * Exception}, implementers are <em>strongly</em> encouraged to
     * declare concrete implementations of the {@code close} method to
     * throw more specific exceptions, or to throw no exception at all
     * if the close operation cannot fail.
     *
     * <p> Cases where the close operation may fail require careful
     * attention by implementers. It is strongly advised to relinquish
     * the underlying resources and to internally <em>mark</em> the
     * resource as closed, prior to throwing the exception. The {@code
     * close} method is unlikely to be invoked more than once and so
     * this ensures that the resources are released in a timely manner.
     * Furthermore it reduces problems that could arise when the resource
     * wraps, or is wrapped, by another resource.
     *
     * <p><em>Implementers of this interface are also strongly advised
     * to not have the {@code close} method throw {@link
     * InterruptedException}.</em>
     * <p>
     * This exception interacts with a thread's interrupted status,
     * and runtime misbehavior is likely to occur if an {@code
     * InterruptedException} is {@linkplain Throwable#addSuppressed
     * suppressed}.
     * <p>
     * More generally, if it would cause problems for an
     * exception to be suppressed, the {@code AutoCloseable.close}
     * method should not throw it.
     *
     * <p>Note that unlike the {@link Closeable#close close}
     * method of {@link Closeable}, this {@code close} method
     * is <em>not</em> required to be idempotent.  In other words,
     * calling this {@code close} method more than once may have some
     * visible side effect, unlike {@code Closeable.close} which is
     * required to have no effect if called more than once.
     * <p>
     * However, implementers of this interface are strongly encouraged
     * to make their {@code close} methods idempotent.
     *
     * @throws Exception if this resource cannot be closed
     */
    @Override
    public void close() throws Exception {
        closeFlag = true;
        for (InputProvider provider : providers) {
            try {
                provider.close();
            } catch (Exception ignored) {
            }
        }
    }
}
