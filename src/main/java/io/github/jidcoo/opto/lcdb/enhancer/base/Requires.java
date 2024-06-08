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

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Container annotation that aggregates several {@link Require} annotations.
 *
 * <p>Can be used natively, declaring several nested {@link Require} annotations.
 * Can also be used in conjunction with Java 8's support for repeatable annotations,
 * where {@link Require} can simply be declared several times on the same class,
 * implicitly generating this container annotation.</p>
 *
 * @author Jidcoo
 * @see Require
 * @since 1.0.1
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Requires {

    /**
     * You can require more things for debugging.
     *
     * @return the {@link Require} annotation array.
     */
    Require[] value();
}
