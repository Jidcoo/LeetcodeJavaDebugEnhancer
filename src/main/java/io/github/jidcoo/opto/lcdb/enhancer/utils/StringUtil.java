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
 * A common string util.
 *
 * @author Jidcoo
 * @since 1.0
 */
public class StringUtil {

    /**
     * Check if CharSequence is empty.
     *
     * @param cs the CharSequence
     * @return true if the CharSequence is empty.
     */
    public static boolean isEmpty(CharSequence cs) {
        return Objects.isNull(cs) || cs.length() == 0;
    }

    /**
     * Check if CharSequence is blank.
     *
     * @param cs the CharSequence.
     * @return true if the CharSequence is blank.
     */
    public static boolean isBlank(CharSequence cs) {
        if (isEmpty(cs)) {
            return true;
        }
        int strLen = cs.length();
        for (int i = 0; i < strLen; ++i) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
}
