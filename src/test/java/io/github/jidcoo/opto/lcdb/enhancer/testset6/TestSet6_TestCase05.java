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

package io.github.jidcoo.opto.lcdb.enhancer.testset6;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

/**
 * Tests for {@link LeetcodeJavaDebugEnhancer#getEnhancerPayload()}
 *
 * @author Jidcoo
 */
public class TestSet6_TestCase05 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"TestSet6_LRUCache\", \"put\", \"put\", \"get\", \"put\", \"get\", \"put\", " +
                "\"get\", \"get\", \"get\"] " +
                "[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]");
        expectString("[null,null,null,1,null,-1,null,-1,3,4]");
    }

    @Override
    public Class<?> getEnhancerPayload() {
        return TestSet6_LRUCache.class;
    }
}
