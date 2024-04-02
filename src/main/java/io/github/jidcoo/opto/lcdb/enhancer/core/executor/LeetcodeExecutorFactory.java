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

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ReflectUtil;

import java.lang.reflect.Method;
import java.util.Objects;

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
     * Product a LeetcodeExecutor instance by
     * {@link LeetcodeJavaDebugEnhancer} instance.
     *
     * @param enhancer the LeetcodeJavaDebugEnhancer instance.
     * @return the LeetcodeExecutor instance.
     */
    public static LeetcodeExecutor getLeetcodeExecutor(LeetcodeJavaDebugEnhancer enhancer) {
        // Hate the npe.
        AssertUtil.nonNull(enhancer, "The enhancer cannot be null.");
        Method enhancementPoint = enhancer.getEnhancementPoint();
        Object target = enhancer;
        // Try to look for the inner class Solution in AT if the enhancementPoint is null.
        if (Objects.isNull(enhancementPoint)) {
            target = ReflectUtil.resolveSolutionInstance(enhancer);
            AssertUtil.nonNull(target, "Cannot resolve the inner class Solution from the AT enhancer instance.");
        }
        return new LeetcodeExecutor(target, enhancementPoint);
    }
}
