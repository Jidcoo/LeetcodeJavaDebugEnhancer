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

package io.github.jidcoo.opto.lcdb.enhancer.core.pipeline;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.base.InputProvider;
import io.github.jidcoo.opto.lcdb.enhancer.base.Order;
import io.github.jidcoo.opto.lcdb.enhancer.base.OutputConsumer;

/**
 * <p>PipelineRunner provides important components
 * in the enhancer runtime pipeline.</p>
 *
 * <p>Note: PipelineRunner implements
 * the {@link Order}, please do not forget to
 * implement the {@link #getOrder()} method.
 * Because it will directly reflect the priority of
 * this pipeline runner object!!!
 * </p>
 *
 * @author Jidcoo
 * @since 1.0.1
 */
abstract class PipelineRunner implements Order {

    /**
     * The base pipeline runner.
     */
    private PipelineRunner baseRunner;

    /**
     * Get enhancer of this pipeline runner.
     *
     * @return enhancer.
     */
    protected LeetcodeJavaDebugEnhancer getEnhancer() {
        return baseRunner.getEnhancer();
    }

    /**
     * Get input provider of this pipeline runner.
     *
     * @return input provider.
     */
    protected InputProvider getInputProvider() {
        return baseRunner.getInputProvider();
    }

    /**
     * Get output consumer of this pipeline runner.
     *
     * @return output consumer.
     */
    protected OutputConsumer getOutputConsumer() {
        return baseRunner.getOutputConsumer();
    }

    /**
     * Get output printer of this pipeline runner.
     *
     * @return output printer instance.
     */
    protected Object getOutputPrinter() {
        return baseRunner.getOutputPrinter();
    }

    /**
     * Get leetcode executor of this pipeline runner.
     *
     * @return leetcode executor instance.
     */
    protected Object getLeetcodeExecutor() {
        return baseRunner.getLeetcodeExecutor();
    }

    /**
     * Get input parser of this pipeline runner.
     *
     * @return input parser instance.
     */
    protected Object getInputParser() {
        return baseRunner.getInputParser();
    }
}
