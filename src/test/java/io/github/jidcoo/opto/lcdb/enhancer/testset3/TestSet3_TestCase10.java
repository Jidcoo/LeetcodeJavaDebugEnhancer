package io.github.jidcoo.opto.lcdb.enhancer.testset3;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.ListNode;
import org.junit.Before;


public class TestSet3_TestCase10 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[1,2,3,4,5] 2");
        expectString("[4,5,1,2,3]");
    }

    class Solution {
        public ListNode rotateRight(ListNode head, int k) {
            if (k == 0 || head == null || head.next == null) {
                return head;
            }
            int n = 1;
            ListNode iter = head;
            while (iter.next != null) {
                iter = iter.next;
                n++;
            }
            int add = n - k % n;
            if (add == n) {
                return head;
            }
            iter.next = head;
            while (add-- > 0) {
                iter = iter.next;
            }
            ListNode ret = iter.next;
            iter.next = null;
            return ret;
        }
    }
}
