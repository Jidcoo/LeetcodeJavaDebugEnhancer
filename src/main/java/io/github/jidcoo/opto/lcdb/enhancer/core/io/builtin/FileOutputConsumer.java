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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * <p>FileOutputConsumer is a {@link OutputConsumer} and
 * extends on {@link BaseBufferWriterOutputConsumer}.</p>
 *
 * <p>FileOutputConsumer use the {@link OutputStream}
 * or the {@link File} as output source.
 * </p>
 *
 * @author Jidcoo
 * @see OutputConsumer
 * @see BaseBufferWriterOutputConsumer
 * @see OutputStream
 * @see File
 * @since 1.0
 */
public class FileOutputConsumer extends BaseBufferWriterOutputConsumer {

    /**
     * Create a FileOutputConsumer by abstract output stream.
     *
     * @param outputStream the output stream.
     */
    public FileOutputConsumer(OutputStream outputStream) {
        super(outputStream);
    }

    /**
     * Create a FileOutputConsumer by file.
     *
     * @param file the file.
     */
    public FileOutputConsumer(File file) throws FileNotFoundException {
        this(new FileOutputStream(file));
    }

    /**
     * Create a FileOutputConsumer by file path.
     *
     * @param filePath the file path.
     */
    public FileOutputConsumer(String filePath) throws FileNotFoundException {
        this(new File(filePath));
    }
}
