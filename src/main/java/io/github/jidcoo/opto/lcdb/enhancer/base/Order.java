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
 * <p>Order is an interface related to order.  </p>
 * <p>It's used to support object sorting functionality.
 * The larger the int returned by the {@link #getOrder()}
 * method, the higher the priority or the order
 * of the object. </p>
 *
 * @author Jidcoo
 * @since 1.0
 */
public interface Order {

    /**
     * Get the order of the object.
     *
     * @return the int order of the object.
     */
    int getOrder();
}
