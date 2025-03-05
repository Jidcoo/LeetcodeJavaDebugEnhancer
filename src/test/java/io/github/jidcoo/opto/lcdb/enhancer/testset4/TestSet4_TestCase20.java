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

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}
 * with data structure design problem.
 *
 * @author Jidcoo
 */
public class TestSet4_TestCase20 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"BrowserHistory\",\"visit\",\"visit\",\"visit\",\"back\",\"back\"," +
                "\"forward\",\"visit\",\"forward\",\"back\",\"back\"] " +
                "[[\"leetcode.com\"],[\"google.com\"],[\"facebook.com\"],[\"youtube.com\"],[1],[1],[1],[\"linkedin" +
                ".com\"],[2],[2],[7]]");
        expectString("[null,null,null,null,\"facebook.com\",\"google.com\",\"facebook.com\",null,\"linkedin.com\"," +
                "\"google.com\",\"leetcode.com\"]");
    }

    class BrowserHistory {
        private List<String> urls;
        private int currIndex;

        public BrowserHistory(String homepage) {
            this.urls = new ArrayList<>();
            this.urls.add(homepage);
            this.currIndex = 0;
        }

        public void visit(String url) {
            while (urls.size() > currIndex + 1) {
                urls.remove(urls.size() - 1);
            }
            urls.add(url);
            this.currIndex++;
        }

        public String back(int steps) {
            currIndex = Math.max(currIndex - steps, 0);
            return urls.get(currIndex);
        }

        public String forward(int steps) {
            currIndex = Math.min(currIndex + steps, urls.size() - 1);
            return urls.get(currIndex);
        }
    }
}
