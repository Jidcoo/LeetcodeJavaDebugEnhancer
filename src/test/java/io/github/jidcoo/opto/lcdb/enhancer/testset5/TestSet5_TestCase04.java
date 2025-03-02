package io.github.jidcoo.opto.lcdb.enhancer.testset5;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.TreeNode;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class TestSet5_TestCase04 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[1,null,2,null,3,null,4,null,null]", "[2,1,3]");
        expectString("[2,1,3,null,null,null,4]", "[2,1,3]");
    }

    class Solution {
        List<Integer> inorderSeq;

        public TreeNode balanceBST(TreeNode root) {
            inorderSeq = new ArrayList<>();
            getInorder(root);
            return build(0, inorderSeq.size() - 1);
        }

        private void getInorder(TreeNode o) {
            if (o.left != null) {
                getInorder(o.left);
            }
            inorderSeq.add(o.val);
            if (o.right != null) {
                getInorder(o.right);
            }
        }

        private TreeNode build(int l, int r) {
            int mid = (l + r) >> 1;
            TreeNode o = new TreeNode(inorderSeq.get(mid));
            if (l <= mid - 1) {
                o.left = build(l, mid - 1);
            }
            if (mid + 1 <= r) {
                o.right = build(mid + 1, r);
            }
            return o;
        }
    }
}
