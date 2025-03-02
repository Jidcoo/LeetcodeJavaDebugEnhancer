package io.github.jidcoo.opto.lcdb.enhancer.testset5;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.TreeNode;
import org.junit.Before;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

public class TestSet5_TestCase05 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[2,3,5,8,13,21,34]", "[0,1,2,0,0,0,0,1,1,1,1,2,2,2,2]");
        expectString("[2,5,3,8,13,21,34]", "[0,2,1,0,0,0,0,2,2,2,2,1,1,1,1]");
    }

    class Solution {
        public TreeNode reverseOddLevels(TreeNode root) {
            Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
            queue.offer(root);
            boolean isOdd = false;
            while (!queue.isEmpty()) {
                int sz = queue.size();
                List<TreeNode> arr = new ArrayList<TreeNode>();
                for (int i = 0; i < sz; i++) {
                    TreeNode node = queue.poll();
                    if (isOdd) {
                        arr.add(node);
                    }
                    if (node.left != null) {
                        queue.offer(node.left);
                        queue.offer(node.right);
                    }
                }
                if (isOdd) {
                    for (int l = 0, r = sz - 1; l < r; l++, r--) {
                        int temp = arr.get(l).val;
                        arr.get(l).val = arr.get(r).val;
                        arr.get(r).val = temp;
                    }
                }
                isOdd ^= true;
            }
            return root;
        }
    }
}
