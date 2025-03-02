package io.github.jidcoo.opto.lcdb.enhancer.testset3;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.ListNode;
import org.junit.Before;

public class TestSet3_TestCase08 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[5,4,2,1]", "[4,2,2,3]", "[1,100000]");
        expectString("6", "7", "100001");
    }

    class Solution {
        public int pairSum(ListNode head) {
            ListNode prev = null, curr = head, last = head;
            while (last != null) {
                last = last.next.next;
                ListNode temp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = temp;
            }
            int max = 0;
            while (curr != null) {
                max = Math.max(max, prev.val + curr.val);
                prev = prev.next;
                curr = curr.next;
            }
            return max;
        }
    }
}
