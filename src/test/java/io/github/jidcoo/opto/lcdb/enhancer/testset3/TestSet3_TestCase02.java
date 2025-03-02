package io.github.jidcoo.opto.lcdb.enhancer.testset3;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.ListNode;
import org.junit.Before;

public class TestSet3_TestCase02 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[1,2,3,4,5] 2 4", "[5] 1 1");
        expectString("[1,4,3,2,5]", "[5]");
    }

    class Solution {
        public ListNode reverseBetween(ListNode head, int left, int right) {
            ListNode dummyNode = new ListNode(-1);
            dummyNode.next = head;

            ListNode pre = dummyNode;
            for (int i = 0; i < left - 1; i++) {
                pre = pre.next;
            }

            ListNode rightNode = pre;
            for (int i = 0; i < right - left + 1; i++) {
                rightNode = rightNode.next;
            }

            ListNode leftNode = pre.next;
            ListNode curr = rightNode.next;

            pre.next = null;
            rightNode.next = null;

            reverseLinkedList(leftNode);

            pre.next = rightNode;
            leftNode.next = curr;
            return dummyNode.next;
        }

        private void reverseLinkedList(ListNode head) {
            ListNode pre = null;
            ListNode cur = head;

            while (cur != null) {
                ListNode next = cur.next;
                cur.next = pre;
                pre = cur;
                cur = next;
            }
        }
    }
}
