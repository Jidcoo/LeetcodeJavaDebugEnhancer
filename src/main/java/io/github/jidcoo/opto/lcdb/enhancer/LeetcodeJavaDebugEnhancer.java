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

package io.github.jidcoo.opto.lcdb.enhancer;

import io.github.jidcoo.opto.lcdb.enhancer.base.BasePrintingStrategy;
import io.github.jidcoo.opto.lcdb.enhancer.base.EnhancerException;
import io.github.jidcoo.opto.lcdb.enhancer.core.LeetcodeJavaDebugEnhanceProcessor;
import io.github.jidcoo.opto.lcdb.enhancer.utils.EnhancerLogUtil;
import io.github.jidcoo.opto.lcdb.enhancer.base.InputProvider;
import io.github.jidcoo.opto.lcdb.enhancer.base.OutputConsumer;

import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;

/**
 * <p>Let's start our dreams here! [↖（^ω^）↗]</p>
 *
 * <p>LeetcodeJavaDebugEnhancer is a debugging enhanced startup class.</p>
 * <p>A public algorithm class is called <tt>Algorithm-Target(AT)</tt>.</p>
 * <p>Notice: All <tt>AT</tt> that require debugging must extends from this class!!!</p>
 *
 * @author Jidcoo
 * @since 1.0
 */
public abstract class LeetcodeJavaDebugEnhancer {

    /**
     * LeetcodeJavaDebugEnhancer version.
     */
    private static final String VERSION = "1.0.1";

    /**
     * <p>If you need to use a method in <tt>AT</tt> as a starting point for
     * debugging enhancements, please provide an instance of that method. And
     * then the {@link LeetcodeJavaDebugEnhancer} will start from that point.</p>
     *
     * <p>If the method returns null, the {@link LeetcodeJavaDebugEnhancer} will look for
     * the internal class Solution in <tt>AT</tt> and find a suitable startup point
     * from Solution as a debugging enhancement startup point.</p>
     *
     * <p>When the <tt>AT</tt> is a data structure design class, it is
     * necessary to return a non-null debugging enhancement startup point. Otherwise,
     * the {@link LeetcodeJavaDebugEnhancer} will not be able to start normally
     * with enhanced performance</p>
     *
     * @return the enhancements point.
     */
    public Method getEnhancementPoint() {
        return null;
    }

    /**
     * <p>If you need to customize an input provider, please return a valid instance of
     * the input provider. For example, you can provide the input sources from
     * files or networks.</p>
     *
     * <p>If the method returns null, the {@link LeetcodeJavaDebugEnhancer} will use
     * the console as the input provider</p>
     *
     * @return the input provider.
     */
    public InputProvider getInputProvider() {
        return null;
    }

    /**
     * <p>If you need to customize an output consumer, please return a valid instance of
     * the output consumer. For example, you can consume the output sources to files
     * or networks.</p>
     *
     * <p>If the method returns null, the {@link LeetcodeJavaDebugEnhancer} will use
     * the console as the output consumer</p>
     *
     * @return the output consumer.
     */
    public OutputConsumer getOutputConsumer() {
        return null;
    }

    /**
     * <p>If you need to customize the printing of the output object, please return
     * a list of printing strategies, and the {@link LeetcodeJavaDebugEnhancer} will
     * try to find the appropriate strategy from this list to print the output object.
     * </p>
     *
     * @return a list of printing strategies
     */
    public List<BasePrintingStrategy<?>> getOutputPrintStrategies() {
        return null;
    }

    /**
     * Return the LeetcodeJavaDebugEnhancer log level.
     * By default, logging is turned off.
     * <p>The only available log levels are {@link Level#OFF},
     * {@link Level#SEVERE}, {@link Level#WARNING}
     * and {@link Level#INFO}. </p>
     *
     * @return the log level.
     * @see Level#OFF
     * @see Level#SEVERE
     * @see Level#WARNING
     * @see Level#INFO
     */
    public Level getEnhancerLogLevel() {
        return Level.OFF;
    }

    /**
     * Return the LeetcodeJavaDebugEnhancer version.
     *
     * @return the LeetcodeJavaDebugEnhancer version.
     */
    public final String getEnhancerVersion() {
        return VERSION;
    }

    /**
     * LeetcodeJavaDebugEnhancer starting main point.
     *
     * @param args start args.
     */
    public static void main(String[] args) {
        String __AT__ = System.getProperty("sun.java.command");
        System.out.println("LeetcodeJavaDebugEnhancer[" + VERSION + "] started.");
        if (!"io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer".equals(__AT__)) {
            try {
                // Let's do a great work here now.
                LeetcodeJavaDebugEnhanceProcessor.process((Class<? extends LeetcodeJavaDebugEnhancer>) Class.forName(__AT__));
            } catch (Exception | Error err) {
                EnhancerLogUtil.logE("LeetcodeJavaDebugEnhancer[%s] runtime error: %s", VERSION, err.getMessage());
                throw new EnhancerException("LeetcodeJavaDebugEnhancer runtime error: " + err.getMessage(), err);
            }
        } else {
            throw new EnhancerException("Cannot enhance from the abstract LeetcodeJavaDebugEnhancer.");
        }
    }

}
