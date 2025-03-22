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

package io.github.jidcoo.opto.lcdb.enhancer.testset4;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}
 * with data structure design problem.
 *
 * @author Jidcoo
 */
public class TestSet4_TestCase09 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"MapSum\", \"insert\", \"sum\", \"insert\", \"sum\"] [[], [\"apple\", " +
                "3], [\"ap\"], [\"app\", 2], [\"ap\"]]");
        expectString("[null,null,3,null,5]");
    }

    class MapSum {
        Map<String, Integer> map;

        public MapSum() {
            map = new HashMap<>();
        }

        public void insert(String key, int val) {
            map.put(key, val);
        }

        public int sum(String prefix) {
            int res = 0;
            for (String s : map.keySet()) {
                if (s.startsWith(prefix)) {
                    res += map.get(s);
                }
            }
            return res;
        }
    }
}
