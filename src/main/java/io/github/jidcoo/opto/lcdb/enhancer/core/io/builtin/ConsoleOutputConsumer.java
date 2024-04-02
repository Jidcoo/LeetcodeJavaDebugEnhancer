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

import io.github.jidcoo.opto.lcdb.enhancer.base.OutputConsumer;

/**
 * <p>ConsoleOutputConsumer is a {@link OutputConsumer} and
 * extends on {@link BaseBufferWriterOutputConsumer}.</p>
 *
 * <p>ConsoleOutputConsumer use the {@link System#out}
 * as output source.
 * </p>
 *
 * @author Jidcoo
 * @see OutputConsumer
 * @see BaseBufferWriterOutputConsumer
 * @see System#out
 * @since 1.0
 */
public class ConsoleOutputConsumer extends BaseBufferWriterOutputConsumer {

    /**
     * Create a ConsoleOutputConsumer.
     */
    public ConsoleOutputConsumer() {
        // Use the stdout as output source.
        super(System.out);
    }
}
