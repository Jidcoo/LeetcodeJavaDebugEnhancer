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

import io.github.jidcoo.opto.lcdb.enhancer.base.LeetcodeInvoker;

import java.util.List;

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
     * The target invoker used for debug.
     *
     * @since 1.0.1
     */
    private LeetcodeInvoker targetInvoker;

    /**
     * The candidate leetcode invokers list for input parsing.
     *
     * @since 1.0.1
     */
    private List<LeetcodeInvoker> candidateInvokers;

    /**
     * The input used for debug.
     */
    private final Object input;

    /**
     * Create an InputParseTask instance.
     *
     * @param targetInstance    the target instance used for debug.
     * @param candidateInvokers the candidate leetcode invokers list for input
     *                          parsing.
     * @param input             the input used for debug.
     * @since 1.0.1
     */
    InputParseTask(Object targetInstance, List<LeetcodeInvoker> candidateInvokers, Object input) {
        this.targetInstance = targetInstance;
        this.candidateInvokers = candidateInvokers;
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
     * Get the target invoker used for debug.
     *
     * @return the target invoker.
     * @since 1.0.1
     */
    LeetcodeInvoker getTargetInvoker() {
        return targetInvoker;
    }

    /**
     * Set the target invoker used for debug.
     *
     * @param targetInvoker the target invoker.
     * @since 1.0.1
     */
    void setTargetInvoker(LeetcodeInvoker targetInvoker) {
        this.targetInvoker = targetInvoker;
    }

    /**
     * Get the candidate leetcode invokers list for input parsing.
     *
     * @return the candidate invokers list.
     * @since 1.0.1
     */
    List<LeetcodeInvoker> getCandidateInvokers() {
        return candidateInvokers;
    }

    /**
     * Get the input used for debug.
     *
     * @return the input.
     */
    Object getInput() {
        return input;
    }
}
