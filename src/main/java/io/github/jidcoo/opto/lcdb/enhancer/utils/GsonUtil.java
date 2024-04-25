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

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * A common json util based on opensource
 * project <tt>Gson</tt>.
 *
 * @author Jidcoo
 * @see <a href="https://github.com/google/gson">Gson</a>
 * @since 1.0
 */
public class GsonUtil {

    /**
     * The Gson singleton instance.
     */
    private static final Gson GSON = new Gson();

    /**
     * Just making default constructor private for non-instantiability.
     */
    private GsonUtil() {
        throw new AssertionError();
    }

    /**
     * Return the object by the json string and the specified type.
     *
     * @param str  the json string.
     * @param type the specified type.
     * @return the object.
     */
    public static <T> T fromJson(String str, Class<? extends T> type) {
        return GSON.fromJson(str, type);
    }

    /**
     * Return the object by the json string and the specified type.
     *
     * @param str  the json string.
     * @param type the specified type.
     * @return the object.
     * @since 1.0.1
     */
    public static <T> T fromJson(String str, Type type) {
        return GSON.fromJson(str, type);
    }

    /**
     * Return the json string representation of an object.
     *
     * @param object the object.
     * @return the json string.
     */
    public static String toJson(Object object) {
        return GSON.toJson(object);
    }
}
