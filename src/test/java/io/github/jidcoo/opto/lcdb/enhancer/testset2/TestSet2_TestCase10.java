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

package io.github.jidcoo.opto.lcdb.enhancer.testset2;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}.
 *
 * @author Jidcoo
 */
public class TestSet2_TestCase10 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("\"this apple is sweet\" \"this apple is sour\"",
                "\"apple apple\" \"banana\"");
        expectString("[\"sweet\",\"sour\"]", "[\"banana\"]");
    }

    class Solution {
        public String[] uncommonFromSentences(String s1, String s2) {
            Map<String, Integer> freq = new HashMap<String, Integer>();
            insert(s1, freq);
            insert(s2, freq);

            List<String> ans = new ArrayList<String>();
            for (Map.Entry<String, Integer> entry : freq.entrySet()) {
                if (entry.getValue() == 1) {
                    ans.add(entry.getKey());
                }
            }
            return ans.toArray(new String[0]);
        }

        public void insert(String s, Map<String, Integer> freq) {
            String[] arr = s.split(" ");
            for (String word : arr) {
                freq.put(word, freq.getOrDefault(word, 0) + 1);
            }
        }
    }
}
