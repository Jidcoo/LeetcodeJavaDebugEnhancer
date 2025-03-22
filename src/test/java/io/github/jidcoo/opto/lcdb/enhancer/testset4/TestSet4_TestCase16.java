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

import java.util.*;

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}
 * with data structure design problem.
 *
 * @author Jidcoo
 */
public class TestSet4_TestCase16 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"MajorityChecker\", \"query\", \"query\", \"query\"] " +
                "[[[1, 1, 2, 2, 1, 1]], [0, 5, 4], [0, 3, 3], [2, 3, 2]]");
        expectString("[null,1,-1,2]");
    }

    class MajorityChecker {
        public static final int K = 20;
        private int[] arr;
        private Map<Integer, List<Integer>> loc;
        private Random random;

        public MajorityChecker(int[] arr) {
            this.arr = arr;
            this.loc = new HashMap<Integer, List<Integer>>();
            for (int i = 0; i < arr.length; ++i) {
                loc.putIfAbsent(arr[i], new ArrayList<Integer>());
                loc.get(arr[i]).add(i);
            }
            this.random = new Random();
        }

        public int query(int left, int right, int threshold) {
            int length = right - left + 1;

            for (int i = 0; i < K; ++i) {
                int x = arr[left + random.nextInt(length)];
                List<Integer> pos = loc.get(x);
                int occ = searchEnd(pos, right) - searchStart(pos, left);
                if (occ >= threshold) {
                    return x;
                } else if (occ * 2 >= length) {
                    return -1;
                }
            }

            return -1;
        }

        private int searchStart(List<Integer> pos, int target) {
            int low = 0, high = pos.size();
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (pos.get(mid) >= target) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }
            return low;
        }

        private int searchEnd(List<Integer> pos, int target) {
            int low = 0, high = pos.size();
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (pos.get(mid) > target) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }
            return low;
        }
    }
}
