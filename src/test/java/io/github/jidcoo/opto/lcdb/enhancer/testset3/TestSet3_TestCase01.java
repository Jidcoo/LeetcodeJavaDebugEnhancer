package io.github.jidcoo.opto.lcdb.enhancer.testset3;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.ListNode;
import org.junit.Before;

public class TestSet3_TestCase01 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[1,2,3,4,5] 2", "[1] 1", "[1,2] 1");
        expectString("[1,2,3,5]", "[]", "[1]");
    }

    class Solution {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            int i = 0;
            ListNode fast = head;
            while (i < n) {
                fast = fast.next;
                ++i;
            }
            ListNode last;
            ListNode dummy = new ListNode(0, head);
            ListNode slow = dummy;
            while (fast != null) {
                fast = fast.next;
                slow = slow.next;
            }
            slow.next = slow.next.next;
            return dummy.next;
        }
    }
}
