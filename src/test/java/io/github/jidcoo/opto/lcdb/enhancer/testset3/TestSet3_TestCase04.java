package io.github.jidcoo.opto.lcdb.enhancer.testset3;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.ListNode;
import org.junit.Before;

import java.util.HashSet;
import java.util.Set;

public class TestSet3_TestCase04 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[1,2,3] [1,2,3,4,5]", "[1] [1,2,1,2,1,2]", "[5] [1,2,3,4]");
        expectString("[4,5]", "[2,2,2]", "[1,2,3,4]");
    }

    class Solution {
        public ListNode modifiedList(int[] nums, ListNode head) {
            Set<Integer> set = new HashSet<>(nums.length);
            for (int x : nums) {
                set.add(x);
            }
            ListNode dummy = new ListNode(0, head);
            ListNode cur = dummy;
            while (cur.next != null) {
                if (set.contains(cur.next.val)) {
                    cur.next = cur.next.next;
                } else {
                    cur = cur.next;
                }
            }
            return dummy.next;
        }
    }
}
