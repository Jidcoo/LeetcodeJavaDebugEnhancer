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

package io.github.jidcoo.opto.lcdb.enhancer.testset1;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}.
 *
 * @author Jidcoo
 */
public class TestSet1_TestCase08 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[[3,0,8,4],[2,4,5,7],[9,2,6,3],[0,3,1,0]]", "[[0,0,0],[0,0,0],[0,0,0]]");
        expectString("35", "0");
    }

    class Solution {
        public int maxIncreaseKeepingSkyline(int[][] grid) {
            int n = grid.length;
            int[] rowMax = new int[n];
            int[] colMax = new int[n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    rowMax[i] = Math.max(rowMax[i], grid[i][j]);
                    colMax[j] = Math.max(colMax[j], grid[i][j]);
                }
            }
            int ans = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    ans += Math.min(rowMax[i], colMax[j]) - grid[i][j];
                }
            }
            return ans;
        }
    }
}
