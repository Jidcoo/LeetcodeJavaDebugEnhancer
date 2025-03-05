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

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}
 * with data structure design problem.
 *
 * @author Jidcoo
 */
public class TestSet4_TestCase06 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"NumArray\", \"sumRange\", \"update\", \"sumRange\"] [[[1, 3, 5]], [0, " +
                "2], [1, 2], [0, 2]]");
        expectString("[null,9,null,8]");
    }

    class NumArray {
        private int[] segmentTree;
        private int n;

        public NumArray(int[] nums) {
            n = nums.length;
            segmentTree = new int[nums.length * 4];
            build(0, 0, n - 1, nums);
        }

        public void update(int index, int val) {
            change(index, val, 0, 0, n - 1);
        }

        public int sumRange(int left, int right) {
            return range(left, right, 0, 0, n - 1);
        }

        private void build(int node, int s, int e, int[] nums) {
            if (s == e) {
                segmentTree[node] = nums[s];
                return;
            }
            int m = s + (e - s) / 2;
            build(node * 2 + 1, s, m, nums);
            build(node * 2 + 2, m + 1, e, nums);
            segmentTree[node] = segmentTree[node * 2 + 1] + segmentTree[node * 2 + 2];
        }

        private void change(int index, int val, int node, int s, int e) {
            if (s == e) {
                segmentTree[node] = val;
                return;
            }
            int m = s + (e - s) / 2;
            if (index <= m) {
                change(index, val, node * 2 + 1, s, m);
            } else {
                change(index, val, node * 2 + 2, m + 1, e);
            }
            segmentTree[node] = segmentTree[node * 2 + 1] + segmentTree[node * 2 + 2];
        }

        private int range(int left, int right, int node, int s, int e) {
            if (left == s && right == e) {
                return segmentTree[node];
            }
            int m = s + (e - s) / 2;
            if (right <= m) {
                return range(left, right, node * 2 + 1, s, m);
            } else if (left > m) {
                return range(left, right, node * 2 + 2, m + 1, e);
            } else {
                return range(left, m, node * 2 + 1, s, m) + range(m + 1, right, node * 2 + 2, m + 1, e);
            }
        }
    }
}
