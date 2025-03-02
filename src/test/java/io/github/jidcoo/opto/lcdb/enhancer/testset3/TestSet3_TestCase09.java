package io.github.jidcoo.opto.lcdb.enhancer.testset3;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.ListNode;
import org.junit.Before;

public class TestSet3_TestCase09 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[0,3,1,0,4,5,2,0]");
        expectString("[4,11]");
    }

    class Solution {
        public ListNode mergeNodes(ListNode head) {
            ListNode dummy = new ListNode();
            ListNode tail = dummy;
            int total = 0;
            for (ListNode cur = head.next; cur != null; cur = cur.next) {
                if (cur.val == 0) {
                    ListNode node = new ListNode(total);
                    tail.next = node;
                    tail = tail.next;
                    total = 0;
                } else {
                    total += cur.val;
                }
            }

            return dummy.next;
        }
    }
}
