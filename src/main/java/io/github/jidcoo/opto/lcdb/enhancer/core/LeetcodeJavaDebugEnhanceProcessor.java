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

package io.github.jidcoo.opto.lcdb.enhancer.core;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.core.pipeline.LeetcodeJavaDebugEnhancerPipelineProcessor;
import io.github.jidcoo.opto.lcdb.enhancer.core.proxy.DebugEnhancerProxy;
import io.github.jidcoo.opto.lcdb.enhancer.utils.EnhancerLogUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ReflectUtil;

/**
 * <p>LeetcodeJavaDebugEnhanceProcessor is a primary enhancer.</p>
 *
 * <p>In version 1.0.1 and later, the control and support for
 * all features of {@link LeetcodeJavaDebugEnhancer} have been
 * moved from {@link LeetcodeJavaDebugEnhanceProcessor} to
 * {@link LeetcodeJavaDebugEnhancerPipelineProcessor}.</p>
 *
 * <p>By the way, today is a so bad day for me. Because I
 * still need coding on my day off. Can you give me
 * a great star to encourage me?
 * </p>
 *
 * @author Jidcoo
 * @since 1.0
 */
public final class LeetcodeJavaDebugEnhanceProcessor {

    /**
     * <p>Do leetcode debugging enhance process with an <tt>AT</tt> class.</p>
     * <p>I don't want to say much, please take a look at the code.</p>
     *
     * @param AT the <tt>AT</tt> class.
     */
    public static void process(Class<? extends LeetcodeJavaDebugEnhancer> AT) throws Exception, Error {
        // Create an AT instance enhancer at first and then wrap it as a proxy(DebugEnhancerProxy since 1.0.2).
        LeetcodeJavaDebugEnhancer enhancer = new DebugEnhancerProxy(ReflectUtil.createInstance(AT));
        // Setup EnhancerLog log level.
        EnhancerLogUtil.setLogLevel(enhancer.getEnhancerLogLevel());

        EnhancerLogUtil.logI("Start leetcode debugging enhancer at (AT) class: %s", AT.getSimpleName());

        // Do process debugging enhancement pipeline with AT instance. (Since 1.0.1)
        LeetcodeJavaDebugEnhancerPipelineProcessor.process(enhancer);

        // Now, the enhancement work has ended here. It's time to say goodbye.
        // Wishing all programmers around the world the true joy of life in Leetcode.
        // May you be so powerful that you don't need debugging and have no bugs.
        // Good luck!!!.
        EnhancerLogUtil.logI("Stop leetcode debugging enhancer at (AT) class: %s", AT.getSimpleName());
    }
}
