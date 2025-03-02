package io.github.jidcoo.opto.lcdb.enhancer.testset1;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.TreeNode;
import org.junit.Before;

public class TestSet1_TestCase10 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[1,1,1,1,1,null,1]","[2,2,2,5,2]");
        expectString("true", "false");
    }

    class Solution {
        public boolean isUnivalTree(TreeNode root) {
            if (root == null) {
                return true;
            }
            if (root.left != null) {
                if (root.val != root.left.val || !isUnivalTree(root.left)) {
                    return false;
                }
            }
            if (root.right != null) {
                if (root.val != root.right.val || !isUnivalTree(root.right)) {
                    return false;
                }
            }
            return true;
        }
    }
}
