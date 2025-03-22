/*
 * Copyright (C) 2024-2026 Jidcoo(https://github.com/jidcoo).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.jidcoo.opto.lcdb.enhancer.testset5;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.TreeNode;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}
 * with data structure {@link TreeNode}.
 *
 * @author Jidcoo
 */
public class TestSet5_TestCase02 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[3,9,20,15,7] [9,3,15,20,7]", "[-1] [-1]");
        expectString("[3,9,20,null,null,15,7]", "[-1]");
    }

    class Solution {
        private Map<Integer, Integer> indexMap;

        public TreeNode myBuildTree(int[] preorder, int[] inorder, int preorder_left, int preorder_right,
                                    int inorder_left, int inorder_right) {
            if (preorder_left > preorder_right) {
                return null;
            }

            int preorder_root = preorder_left;
            int inorder_root = indexMap.get(preorder[preorder_root]);

            TreeNode root = new TreeNode(preorder[preorder_root]);
            int size_left_subtree = inorder_root - inorder_left;
            root.left = myBuildTree(preorder, inorder, preorder_left + 1, preorder_left + size_left_subtree,
                    inorder_left, inorder_root - 1);
            root.right = myBuildTree(preorder, inorder, preorder_left + size_left_subtree + 1, preorder_right,
                    inorder_root + 1, inorder_right);
            return root;
        }

        public TreeNode buildTree(int[] preorder, int[] inorder) {
            int n = preorder.length;
            indexMap = new HashMap<Integer, Integer>();
            for (int i = 0; i < n; i++) {
                indexMap.put(inorder[i], i);
            }
            return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1);
        }
    }
}
