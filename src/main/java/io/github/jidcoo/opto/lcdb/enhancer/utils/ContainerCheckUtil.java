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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A common java data container check util.
 *
 * @author Jidcoo
 * @since 1.0
 */
public class ContainerCheckUtil {

    /**
     * Check if the map is empty.
     *
     * @param map the map.
     * @return true if the map is null or empty.
     */
    public static boolean isMapEmpty(Map map) {
        return Objects.isNull(map) || map.isEmpty();
    }

    /**
     * Check if the set is empty.
     *
     * @param set the set.
     * @return true if the set is null or empty.
     */
    public static boolean isSetEmpty(Set set) {
        return Objects.isNull(set) || set.isEmpty();
    }

    /**
     * Check if the list is empty.
     *
     * @param list the list.
     * @return true if the list is null or empty.
     */
    public static boolean isListEmpty(List list) {
        return Objects.isNull(list) || list.isEmpty();
    }
}
