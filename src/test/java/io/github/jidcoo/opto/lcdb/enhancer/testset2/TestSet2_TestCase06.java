package io.github.jidcoo.opto.lcdb.enhancer.testset2;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.TreeNode;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.builtin.StringInputProvider;
import org.junit.Before;

@Require(types = StringInputProvider.class, values = {"[10,5,15,3,7,null,18] 7 15", "[10,5,15,3,7,13,18,1,null,6] 6 10"})
public class TestSet2_TestCase06 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriver();
        expectString("32", "23");
    }

    class Solution {
        public int rangeSumBST(TreeNode root, int low, int high) {
            if (root == null) {
                return 0;
            }
            if (root.val > high) {
                return rangeSumBST(root.left, low, high);
            }
            if (root.val < low) {
                return rangeSumBST(root.right, low, high);
            }
            return root.val + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high);
        }
    }
}
