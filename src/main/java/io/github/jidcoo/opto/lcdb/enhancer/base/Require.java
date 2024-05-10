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

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>Require is a powerful function
 * annotation. </p>
 *
 * <p>You can use this annotation
 * to require anything you want during
 * the enhanced debugging runtime.</p>
 *
 * @author Jidcoo
 * @since 1.0.1
 */
@Target({TYPE, FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
@Repeatable(Requires.class)
public @interface Require {

    /**
     * The requirement string values;
     *
     * @return requirement string values.
     */
    String[] values() default "";

    /**
     * The requirement types.
     *
     * @return requirement types.
     */
    Class<?>[] types() default {};
}
