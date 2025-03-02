package io.github.jidcoo.opto.lcdb.enhancer.testset3;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.ListNode;
import org.junit.Before;

import java.util.ArrayDeque;
import java.util.Deque;

public class TestSet3_TestCase05 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[7,2,4,3] [5,6,4]", "[2,4,3] [5,6,4]", "[0] [0]");
        expectString("[7,8,0,7]", "[8,0,7]", "[0]");
    }

    class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            Deque<Integer> stack1 = new ArrayDeque<Integer>();
            Deque<Integer> stack2 = new ArrayDeque<Integer>();
            while (l1 != null) {
                stack1.push(l1.val);
                l1 = l1.next;
            }
            while (l2 != null) {
                stack2.push(l2.val);
                l2 = l2.next;
            }
            int carry = 0;
            ListNode ans = null;
            while (!stack1.isEmpty() || !stack2.isEmpty() || carry != 0) {
                int a = stack1.isEmpty() ? 0 : stack1.pop();
                int b = stack2.isEmpty() ? 0 : stack2.pop();
                int cur = a + b + carry;
                carry = cur / 10;
                cur %= 10;
                ListNode curnode = new ListNode(cur);
                curnode.next = ans;
                ans = curnode;
            }
            return ans;
        }
    }
}
