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

import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.StringUtil;
import io.github.jidcoo.opto.lcdb.enhancer.base.InputProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <p>BaseBufferReaderInputProvider is a {@link InputProvider}
 * for {@link BufferedReader} Input.</p>
 *
 * <p>BaseBufferReaderInputProvider uses {@link StringUtil#isBlank(CharSequence)}
 * as the criterion for determining whether to end the input.
 * See {@link #isEnd(String)}.
 * </p>
 *
 * @author Jidcoo
 * @see InputProvider
 * @see BufferedReader
 * @since 1.0
 */
public abstract class BaseBufferReaderInputProvider implements InputProvider {

    /**
     * The base BufferedReader.
     */
    private final BufferedReader bufferedReader;

    /**
     * Create a BaseBufferReaderInputProvider.
     *
     * @param bufferedReader the BufferedReader instance.
     */
    public BaseBufferReaderInputProvider(BufferedReader bufferedReader) {
        AssertUtil.nonNull(bufferedReader, "The bufferedReader cannot be null.");
        this.bufferedReader = bufferedReader;
    }

    /**
     * Create a BaseBufferReaderInputProvider by InputStream.
     *
     * @param inputStream the InputStream.
     */
    public BaseBufferReaderInputProvider(InputStream inputStream) {
        AssertUtil.nonNull(inputStream, "The inputStream cannot be null.");
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    /**
     * Provide a next string input.
     *
     * @return a string input.
     */
    @Override
    public String provideNextInput() {
        try {
            return this.bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Determine if the input is ending.
     *
     * @param input a string input got from {@link #provideNextInput()}}.
     * @return true if the input is ending.
     */
    @Override
    public boolean isEnd(String input) {
        return StringUtil.isBlank(input);
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
     * <p>Note that unlike the {@link java.io.Closeable#close close}
     * method of {@link java.io.Closeable}, this {@code close} method
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
        this.bufferedReader.close();
    }
}
