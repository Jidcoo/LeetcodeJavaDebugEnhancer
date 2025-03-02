package io.github.jidcoo.opto.lcdb.enhancer.testset2;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.TreeNode;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.builtin.StringInputProvider;
import org.junit.Before;

@Require(types = StringInputProvider.class, values = {"[1,2,3]", "[-10,9,20,null,null,15,7]"})
public class TestSet2_TestCase04 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriver();
        expectString("6", "42");
    }

    class Solution {
        int maxSum;

        public int maxPathSum(TreeNode root) {
            maxSum = Integer.MIN_VALUE;
            maxGain(root);
            return maxSum;
        }

        private int maxGain(TreeNode node) {
            if (node == null) {
                return 0;
            }

            int leftGain = Math.max(maxGain(node.left), 0);
            int rightGain = Math.max(maxGain(node.right), 0);

            int priceNewpath = node.val + leftGain + rightGain;

            maxSum = Math.max(maxSum, priceNewpath);

            return node.val + Math.max(leftGain, rightGain);
        }
    }
}
