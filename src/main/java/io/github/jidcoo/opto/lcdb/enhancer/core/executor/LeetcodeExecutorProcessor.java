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

import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;

/**
 * <p>LeetcodeExecutorProcessor is a publicly available
 * LeetcodeExecutor processor. It has used a proxy to
 * access {@link LeetcodeExecutor}.</p>
 *
 * <p>The main {@link #process(Object, Object)} method
 * of LeetcodeExecutorProcessor is to proxy external callers
 * to execute {@link LeetcodeExecutor}.
 * </p>
 *
 * @author Jidcoo
 * @since 1.0
 */
public final class LeetcodeExecutorProcessor {

    /**
     * Do real execute with LeetcodeExecutor instance and Input instance.
     *
     * @param executor    the LeetcodeExecutor instance.
     * @param inputObject the input object.
     * @return the executor output object.
     */
    public static Object process(Object executor, Object inputObject) {
        AssertUtil.nonNull(executor, "The executor cannot be null.");
        AssertUtil.isTrue((executor instanceof LeetcodeExecutor), "The executor is not a LeetcodeExecutor.");
        // Do real execute logic and return the executor output.
        Object output = ((LeetcodeExecutor) (executor)).execute(inputObject);
        return output;
    }
}
