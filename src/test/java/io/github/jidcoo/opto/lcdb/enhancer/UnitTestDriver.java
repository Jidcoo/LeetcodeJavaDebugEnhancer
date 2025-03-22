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

import io.github.jidcoo.opto.lcdb.enhancer.base.OutputConsumer;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.builtin.MultipleOutputConsumer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>UnitTestDriver is a driver that run a junit-test on this project.
 * <p>All junit-test classes must extend from this class!!!
 *
 * @author Jidcoo
 * @see UnitTestDriver#registerDriver()
 * @see UnitTestDriver#registerDriverWithAutoCustomStdIn(String...)
 * @see UnitTestDriver#expectString(String...)
 * @see UnitTestDriver#ignoreTestResult()
 */
@RunWith(JUnit4.class)
public abstract class UnitTestDriver extends LeetcodeJavaDebugEnhancer {

    private static final UnitTestOutputCollector __GLOBAL__OC__ = new UnitTestOutputCollector();

    private final static Lock __GLOBAL__SYS_STD_IN_CHANEL_LOCK__ = new ReentrantLock();

    private final static InputStream __GLOBAL__SYS_STD_IN_HOLDER__ = System.in;

    private final String __UNIT_TEST_ID__ = UUID.randomUUID().toString();

    private String __TEST_EXPECTED_RESULT__;

    private InputStream __CUSTOM_STD_IN_HOLDER__;

    private boolean __IS__REGISTERED__;

    private boolean __IS__IGNORED__OUTPUT__;

    @Before
    public final void __PROCESS_BEFORE_ON_DRIVER__() {
        __GLOBAL__OC__.attach(this);
    }

    @After
    public final void __PROCESS_AFTER_ON_DRIVER__() {
        __GLOBAL__OC__.detach(this);
        if (__CUSTOM_STD_IN_HOLDER__ != null) {
            try {
                __CUSTOM_STD_IN_HOLDER__.close();
            } catch (IOException ignored) {
            }
            __CUSTOM_STD_IN_HOLDER__ = null;
            System.setIn(__GLOBAL__SYS_STD_IN_HOLDER__);
            __GLOBAL__SYS_STD_IN_CHANEL_LOCK__.unlock();
        }
    }

    @Test
    public final void __PROCESS_TEST_ON_DRIVER__() {
        Assert.assertTrue(__IS__REGISTERED__);
        LeetcodeJavaDebugEnhancer.main(null);
        if (__IS__IGNORED__OUTPUT__) {
            return;
        }
        Assert.assertEquals(__TEST_EXPECTED_RESULT__, __GLOBAL__OC__.collectOutput(this));
    }

    @Override
    public OutputConsumer getOutputConsumer() {
        OutputConsumer originOutputConsumer = super.getOutputConsumer();
        List<OutputConsumer> outputConsumers = new ArrayList<>();
        outputConsumers.add(__GLOBAL__OC__);
        if (originOutputConsumer != null) {
            outputConsumers.add(originOutputConsumer);
        }
        return new MultipleOutputConsumer(outputConsumers);
    }

    /**
     * Ignore the test result.
     */
    protected final void ignoreTestResult() {
        if (!__IS__IGNORED__OUTPUT__) {
            __IS__IGNORED__OUTPUT__ = true;
        }
    }

    /**
     * Assert that the test result with expected result.
     *
     * @param expectedResult the expected results.
     */
    protected final void expectString(String... expectedResult) {
        if (__IS__IGNORED__OUTPUT__) {
            return;
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < expectedResult.length; i++) {
            if (i > 0) {
                buf.append("\n");
            }
            buf.append(expectedResult[i]);
        }
        __TEST_EXPECTED_RESULT__ = buf.toString();
    }

    /**
     * Register driver to cur junit-test instance.
     *
     * @apiNote Before running the test, one of {@link UnitTestDriver#registerDriver()}
     * or {@link UnitTestDriver#registerDriverWithAutoCustomStdIn(String...)}
     * must be called to register the driver to current junit-test instance.
     */
    protected final void registerDriver() {
        if (!__IS__REGISTERED__) {
            System.setProperty("sun.java.command", this.getClass().getName());
            __IS__REGISTERED__ = true;
        }
    }

    /**
     * Register driver to cur junit-test instance with auto custom
     * system standard input.
     *
     * @apiNote Before running the test, one of {@link UnitTestDriver#registerDriver()}
     * or {@link UnitTestDriver#registerDriverWithAutoCustomStdIn(String...)}
     * must be called to register the driver to current junit-test instance.
     */
    protected final void registerDriverWithAutoCustomStdIn(String... stdIn) {
        Assert.assertNotNull(stdIn);
        if (stdIn.length == 0) {
            registerDriver();
        } else {
            StringBuilder buf = new StringBuilder();
            for (int i = 0; i < stdIn.length; i++) {
                if (i > 0) {
                    buf.append("\n");
                }
                buf.append(stdIn[i]);
            }
            if (!__IS__REGISTERED__) {
                ByteArrayInputStream in = new ByteArrayInputStream(buf.toString().getBytes());
                __GLOBAL__SYS_STD_IN_CHANEL_LOCK__.lock();
                __CUSTOM_STD_IN_HOLDER__ = in;
                System.setIn(__CUSTOM_STD_IN_HOLDER__);
                registerDriver();
            }
        }

    }

    private static class UnitTestOutputCollector implements OutputConsumer {

        private final Map<String, StringBuffer> UNITTEST_OUTPUT_POOL;

        private final ThreadLocal<String> UNITTEST_INSTANCE_REGISTRY;

        UnitTestOutputCollector() {
            UNITTEST_OUTPUT_POOL = new ConcurrentHashMap<>();
            UNITTEST_INSTANCE_REGISTRY = new InheritableThreadLocal<>();
        }

        void attach(UnitTestDriver driver) {
            Assert.assertNotNull(driver);
            Assert.assertNotNull(driver.__UNIT_TEST_ID__);
            Assert.assertEquals(false, UNITTEST_OUTPUT_POOL.containsKey(driver.__UNIT_TEST_ID__));
            Assert.assertNull(UNITTEST_INSTANCE_REGISTRY.get());
            UNITTEST_INSTANCE_REGISTRY.set(driver.__UNIT_TEST_ID__);
            UNITTEST_OUTPUT_POOL.put(driver.__UNIT_TEST_ID__, new StringBuffer());
        }

        void detach(UnitTestDriver driver) {
            UNITTEST_INSTANCE_REGISTRY.remove();
            Assert.assertNotNull(driver);
            Assert.assertNotNull(driver.__UNIT_TEST_ID__);
            StringBuffer output = UNITTEST_OUTPUT_POOL.remove(driver.__UNIT_TEST_ID__);
            if (output != null) {
                output.delete(0, output.length());
            }
        }

        String collectOutput(UnitTestDriver driver) {
            Assert.assertNotNull(driver);
            Assert.assertNotNull(driver.__UNIT_TEST_ID__);
            Assert.assertEquals(true, UNITTEST_OUTPUT_POOL.containsKey(driver.__UNIT_TEST_ID__));
            Assert.assertNotNull(UNITTEST_INSTANCE_REGISTRY.get());
            Assert.assertEquals(driver.__UNIT_TEST_ID__, UNITTEST_INSTANCE_REGISTRY.get());
            StringBuffer output = UNITTEST_OUTPUT_POOL.get(driver.__UNIT_TEST_ID__);
            Assert.assertNotNull(output);
            String ret = output.toString();
            output.delete(0, ret.length());
            Assert.assertEquals(driver.__UNIT_TEST_ID__, UNITTEST_INSTANCE_REGISTRY.get());
            return ret;
        }

        @Override
        public void consumeNextOutput(String output) {
            String unitTestInstance = UNITTEST_INSTANCE_REGISTRY.get();
            Assert.assertNotNull(unitTestInstance);
            Assert.assertEquals(true, UNITTEST_OUTPUT_POOL.containsKey(unitTestInstance));
            StringBuffer outputBuffer = UNITTEST_OUTPUT_POOL.get(unitTestInstance);
            if (outputBuffer.length() > 0) {
                outputBuffer.append("\n");
            }
            outputBuffer.append(output);
        }

        @Override
        public void close() throws Exception {

        }
    }
}
