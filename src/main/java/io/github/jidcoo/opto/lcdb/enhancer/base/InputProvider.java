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

package io.github.jidcoo.opto.lcdb.enhancer.base;

/**
 * <p>InputProvider is an input provider. </p>
 * <p>It will provide a string parameter input source
 * for method executes. </p>
 *
 * @author Jidcoo
 * @apiNote Please do not forget to implement the
 *          {@link #close()} method for {@link AutoCloseable}.
 *          We will call the {@link #close()} method
 *          at the appropriate time to close
 *          the resources used.
 * @since 1.0
 */
public interface InputProvider extends AutoCloseable {

    /**
     * Provide a next string input.
     *
     * @return a string input.
     */
    String provideNextInput();

    /**
     * Determine if the input is ending.
     *
     * @param input a string input got from {@link #provideNextInput()}}.
     * @return true if the input is ending.
     */
    boolean isEnd(String input);
}
