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

package io.github.jidcoo.opto.lcdb.enhancer.testset3;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.ListNode;
import org.junit.Before;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}
 * with data structure {@link ListNode}.
 *
 * @author Jidcoo
 */
public class TestSet3_TestCase06 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[2,1,5]", "[2,7,4,3,5]");
        expectString("[5,5,0]", "[7,0,5,5,0]");
    }

    class Solution {
        public int[] nextLargerNodes(ListNode head) {
            List<Integer> ans = new ArrayList<Integer>();
            Deque<int[]> stack = new ArrayDeque<int[]>();

            ListNode cur = head;
            int idx = -1;
            while (cur != null) {
                ++idx;
                ans.add(0);
                while (!stack.isEmpty() && stack.peek()[0] < cur.val) {
                    ans.set(stack.pop()[1], cur.val);
                }
                stack.push(new int[]{cur.val, idx});
                cur = cur.next;
            }

            int size = ans.size();
            int[] arr = new int[size];
            for (int i = 0; i < size; ++i) {
                arr[i] = ans.get(i);
            }
            return arr;
        }
    }
}
