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

package io.github.jidcoo.opto.lcdb.enhancer.core.parser;

import java.lang.reflect.Method;

/**
 * <p>InputParseTask is an input parse task holder.</p>
 * <p>InputParseTask is only used for
 * {@link InputParser#parse(InputParseTask)} to submit
 * an input parse task.
 * </p>
 *
 * @author Jidcoo
 * @since 1.0
 */
final class InputParseTask {

    /**
     * The target instance used for debug.
     */
    private final Object targetInstance;

    /**
     * The target method used for debug.
     */
    private Method targetMethod;

    /**
     * The string input used for debug.
     */
    private final String input;

    /**
     * Create an InputParseTask instance.
     *
     * @param targetInstance the target instance used for debug.
     * @param targetMethod   the target method used for debug.
     * @param input          the string input used for debug.
     */
    InputParseTask(Object targetInstance, Method targetMethod, String input) {
        this.targetInstance = targetInstance;
        this.targetMethod = targetMethod;
        this.input = input;
    }

    /**
     * Get the target instance used for debug.
     *
     * @return the target instance.
     */
    Object getTargetInstance() {
        return targetInstance;
    }

    /**
     * Get the target method used for debug.
     *
     * @return the target method.
     */
    Method getTargetMethod() {
        return targetMethod;
    }

    /**
     * Set the target method.
     *
     * @param targetMethod the target method.
     */
    public void setTargetMethod(Method targetMethod) {
        this.targetMethod = targetMethod;
    }

    /**
     * Get the input used for debug.
     *
     * @return the input.
     */
    String getInput() {
        return input;
    }
}
