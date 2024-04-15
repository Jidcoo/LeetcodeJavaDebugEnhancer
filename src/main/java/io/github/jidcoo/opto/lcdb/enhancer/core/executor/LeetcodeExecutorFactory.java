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

import java.util.Objects;

import io.github.jidcoo.opto.lcdb.enhancer.base.LeetcodeInvoker;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;

/**
 * <p>LeetcodeExecutorFactory is a factory class
 * to product the {@link LeetcodeExecutor}
 * instance.</p>
 *
 * @author Jidcoo
 * @since 1.0
 */
public final class LeetcodeExecutorFactory {

    /**
     * Product a LeetcodeExecutor instance by leetcode instance.
     *
     * @param instance         the leetcode instance.
     * @param leetcodeInvokers the leetcode invokers.
     * @return the LeetcodeExecutor instance.
     * @since 1.0.1
     */
    public static LeetcodeExecutor getLeetcodeExecutor(Object instance, LeetcodeInvoker... leetcodeInvokers) {
        AssertUtil.nonNull(instance, "The instance cannot be null.");
        LeetcodeExecutor executor = new LeetcodeExecutor(instance, null);
        for (LeetcodeInvoker leetcodeInvoker : leetcodeInvokers) {
            if (Objects.nonNull(leetcodeInvoker)) {
                // Add non-null leetcode invoker.
                executor.getCandidateInvokers().add(leetcodeInvoker);
            }
        }
        return executor;
    }
}
