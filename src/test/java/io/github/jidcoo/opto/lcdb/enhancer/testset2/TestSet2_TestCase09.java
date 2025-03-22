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
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}.
 *
 * @author Jidcoo
 */
public class TestSet2_TestCase09 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]", "[[1,2,3],[1,2,3],[1,2,3]]");
        expectString("[20,24]", "[1,1]");
    }

    class Solution {
        public int[] smallestRange(List<List<Integer>> nums) {
            int rangeLeft = 0, rangeRight = Integer.MAX_VALUE;
            int minRange = rangeRight - rangeLeft;
            int max = Integer.MIN_VALUE;
            int size = nums.size();
            int[] next = new int[size];
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>(new Comparator<Integer>() {
                public int compare(Integer index1, Integer index2) {
                    return nums.get(index1).get(next[index1]) - nums.get(index2).get(next[index2]);
                }
            });
            for (int i = 0; i < size; i++) {
                priorityQueue.offer(i);
                max = Math.max(max, nums.get(i).get(0));
            }
            while (true) {
                int minIndex = priorityQueue.poll();
                int curRange = max - nums.get(minIndex).get(next[minIndex]);
                if (curRange < minRange) {
                    minRange = curRange;
                    rangeLeft = nums.get(minIndex).get(next[minIndex]);
                    rangeRight = max;
                }
                next[minIndex]++;
                if (next[minIndex] == nums.get(minIndex).size()) {
                    break;
                }
                priorityQueue.offer(minIndex);
                max = Math.max(max, nums.get(minIndex).get(next[minIndex]));
            }
            return new int[]{rangeLeft, rangeRight};
        }
    }
}
