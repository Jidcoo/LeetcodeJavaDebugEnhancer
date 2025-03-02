package io.github.jidcoo.opto.lcdb.enhancer.testset5;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.TreeNode;
import org.junit.Before;

public class TestSet5_TestCase07 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[3,2,1,6,0,5]", "[3,2,1]");
        expectString("[6,3,5,null,2,0,null,null,1]", "[3,null,2,null,1]");
    }

    class Solution {
        public TreeNode constructMaximumBinaryTree(int[] nums) {
            return construct(nums, 0, nums.length - 1);
        }

        public TreeNode construct(int[] nums, int left, int right) {
            if (left > right) {
                return null;
            }
            int best = left;
            for (int i = left + 1; i <= right; ++i) {
                if (nums[i] > nums[best]) {
                    best = i;
                }
            }
            TreeNode node = new TreeNode(nums[best]);
            node.left = construct(nums, left, best - 1);
            node.right = construct(nums, best + 1, right);
            return node;
        }
    }
}
