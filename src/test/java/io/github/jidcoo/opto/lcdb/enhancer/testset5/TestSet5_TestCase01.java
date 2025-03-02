package io.github.jidcoo.opto.lcdb.enhancer.testset5;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.TreeNode;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class TestSet5_TestCase01 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[1,null,2,3]", "[]", "[1]");
        expectString("[1,3,2]", "[]", "[1]");
    }

    class Solution {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> res = new ArrayList<Integer>();
            inorder(root, res);
            return res;
        }

        public void inorder(TreeNode root, List<Integer> res) {
            if (root == null) {
                return;
            }
            inorder(root.left, res);
            res.add(root.val);
            inorder(root.right, res);
        }
    }
}
