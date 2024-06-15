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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * <p>FileInputProvider is a {@link InputProvider} and
 * extends on {@link BaseBufferReaderInputProvider}.</p>
 *
 * <p>FileInputProvider use the {@link InputStream}
 * or the {@link File} as input source.
 * </p>
 *
 * @author Jidcoo
 * @see InputProvider
 * @see BaseBufferReaderInputProvider
 * @see InputStream
 * @see File
 * @since 1.0
 */
public class FileInputProvider extends BaseBufferReaderInputProvider {

    /**
     * Create a FileInputProvider by abstract input stream.
     *
     * @param inputStream the input stream.
     */
    public FileInputProvider(InputStream inputStream) {
        super(inputStream);
    }

    /**
     * Create a FileInputProvider by file.
     *
     * @param file the file.
     */
    public FileInputProvider(File file) throws FileNotFoundException {
        this(new FileInputStream(file));
    }

    /**
     * Create a FileInputProvider by file path.
     *
     * @param filePath the file path.
     */
    public FileInputProvider(String filePath) throws FileNotFoundException {
        this(new File(filePath));
    }
}
