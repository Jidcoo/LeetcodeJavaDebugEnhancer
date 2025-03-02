package io.github.jidcoo.opto.lcdb.enhancer.testset5;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.ListNode;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.TreeNode;
import org.junit.Before;

public class TestSet5_TestCase03 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[-10,-3,0,5,9]", "[]");
        expectString("[0,-3,9,-10,null,5]", "[]");
    }

    class Solution {
        public TreeNode sortedListToBST(ListNode head) {
            return buildTree(head, null);
        }

        public TreeNode buildTree(ListNode left, ListNode right) {
            if (left == right) {
                return null;
            }
            ListNode mid = getMedian(left, right);
            TreeNode root = new TreeNode(mid.val);
            root.left = buildTree(left, mid);
            root.right = buildTree(mid.next, right);
            return root;
        }

        public ListNode getMedian(ListNode left, ListNode right) {
            ListNode fast = left;
            ListNode slow = left;
            while (fast != right && fast.next != right) {
                fast = fast.next;
                fast = fast.next;
                slow = slow.next;
            }
            return slow;
        }
    }
}
