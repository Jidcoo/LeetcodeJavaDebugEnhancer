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

import io.github.jidcoo.opto.lcdb.enhancer.base.Order;

import java.util.Comparator;
import java.util.List;

/**
 * A common order util for {@link io.github.jidcoo.opto.lcdb.enhancer.base.Order}.
 *
 * @author Jidcoo
 * @see Order
 * @since 1.0.3
 */
public class OrderUtil {

    private static final Comparator<? super Order> ASC_ORDER_CMP = Comparator.comparingInt(Order::getOrder);

    private static final Comparator<? super Order> DESC_ORDER_CMP = ASC_ORDER_CMP.reversed();

    /**
     * ASC sort.
     *
     * @param list the order-able list.
     */
    public static void ascSort(List<? extends Order> list) {
        orderSort(list, ASC_ORDER_CMP);
    }

    /**
     * DESC sort.
     *
     * @param list the order-able list.
     */
    public static void descSort(List<? extends Order> list) {
        orderSort(list, DESC_ORDER_CMP);
    }

    /**
     * Get asc order comparator.
     *
     * @return Comparator
     */
    public static Comparator<? super Order> ascComparator() {
        return ASC_ORDER_CMP;
    }

    /**
     * Get desc order comparator.
     *
     * @return Comparator
     */
    public static Comparator<? super Order> descComparator() {
        return DESC_ORDER_CMP;
    }

    private static <Sortable> void orderSort(List<? extends Sortable> list, Comparator<? super Sortable> cmp) {
        AssertUtil.nonNull(list, "The list cannot be null.");
        list.sort(cmp);
    }
}
