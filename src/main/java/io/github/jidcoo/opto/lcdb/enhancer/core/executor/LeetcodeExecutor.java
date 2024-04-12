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

package io.github.jidcoo.opto.lcdb.enhancer.core.executor;

import io.github.jidcoo.opto.lcdb.enhancer.base.EnhancerException;
import io.github.jidcoo.opto.lcdb.enhancer.base.LeetcodeInvoker;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>LeetcodeExecutor is an executor used to
 * execute leetcode method. </p>
 * <p>The most important point of LeetcodeExecutor
 * is the {@link #execute(Object input)} method.
 * </p>
 *
 * <p>LeetcodeExecutor takes the final input and
 * executes invoker to obtain the final output
 * of <tt>AT</tt> and return it.</p>
 *
 * @author Jidcoo
 * @since 1.0
 */
final class LeetcodeExecutor {

    /**
     * The leetcode instance.
     */
    private final Object instance;

    /**
     * The leetcode executor.
     *
     * @since 1.0.1
     */
    private LeetcodeInvoker executor;

    /**
     * The candidate leetcode invokers.
     *
     * @since 1.0.1
     */
    private final List<LeetcodeInvoker> candidateInvokers;

    /**
     * The leetcode invoker response type.
     */
    private Class<?> invokerResponseType;

    /**
     * Create a LeetcodeExecutor instance.
     *
     * @param instance the leetcode instance.
     * @param executor the leetcode executor.
     * @since 1.0.1
     */
    LeetcodeExecutor(Object instance, LeetcodeInvoker executor) {
        this.instance = instance;
        this.executor = executor;
        this.candidateInvokers = new ArrayList<>();
        if (Objects.nonNull(executor)) {
            // Add executor to candidate leetcode invokers list.
            this.candidateInvokers.add(executor);
        }
    }

    /**
     * Do execute <tt>AT</tt> with input object.
     *
     * @param input input object.
     * @return the <tt>AT</tt> return.
     * @throws EnhancerException if method invoke error.
     * @throws RuntimeException  if leetcode execute failed
     *                           or invoker error.
     */
    Object execute(Object input) {
        AssertUtil.nonNull(executor, "The leetcode executor cannot be null.");
        try {
            return executor.invoke(instance, (Object[]) input);
        } catch (InvocationTargetException exception) {
            throw new EnhancerException(exception.getCause());
        } catch (Throwable exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Get the leetcode instance.
     *
     * @return the leetcode instance.
     */
    Object getInstance() {
        return instance;
    }

    /**
     * Get the leetcode executor.
     *
     * @return the leetcode executor.
     */
    LeetcodeInvoker getExecutor() {
        return executor;
    }

    /**
     * Set the leetcode executor.
     *
     * @param executor the leetcode executor.
     * @since 1.0.1
     */
    void setExecutor(LeetcodeInvoker executor) {
        this.executor = executor;
    }

    /**
     * Get the candidate leetcode invokers list.
     *
     * @return the candidate leetcode invokers list.
     * @since 1.0.1
     */
    List<LeetcodeInvoker> getCandidateInvokers() {
        return this.candidateInvokers;
    }
}