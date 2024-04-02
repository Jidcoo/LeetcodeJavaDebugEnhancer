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

import java.util.Objects;

/**
 * A simple assert util.
 *
 * @author Jidcoo
 * @since 1.0
 */
public class AssertUtil {

    /**
     * Assert the flag is true.
     *
     * @param flag boolean flag.
     * @param msg  the message you want to show if the flag is false.
     */
    public static void isTrue(boolean flag, String msg) {
        if (!flag) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * Assert the object is non-null object.
     *
     * @param object the object.
     * @param msg    the message you want to show if the object is null.
     */
    public static void nonNull(Object object, String msg) {
        isTrue(Objects.nonNull(object), msg);
    }
}
