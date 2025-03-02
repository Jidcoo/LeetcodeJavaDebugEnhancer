package io.github.jidcoo.opto.lcdb.enhancer.testset3;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.ListNode;
import org.junit.Before;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

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
