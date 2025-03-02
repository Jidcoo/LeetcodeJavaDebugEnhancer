package io.github.jidcoo.opto.lcdb.enhancer.testset5;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.TreeNode;
import org.junit.Before;

import java.util.ArrayDeque;
import java.util.Deque;

public class TestSet5_TestCase08 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[3,1,4,null,2] 1", "[5,3,6,2,4,null,null,1] 3");
        expectString("1", "3");
    }

    class Solution {
        public int kthSmallest(TreeNode root, int k) {
            Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
            while (root != null || !stack.isEmpty()) {
                while (root != null) {
                    stack.push(root);
                    root = root.left;
                }
                root = stack.pop();
                --k;
                if (k == 0) {
                    break;
                }
                root = root.right;
            }
            return root.val;
        }
    }
}
