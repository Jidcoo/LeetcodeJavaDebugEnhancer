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

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}
 * with data structure {@link TreeNode}.
 *
 * @author Jidcoo
 */
public class TestSet5_TestCase06 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[5,1,2,3,null,6,4] 3 6", "[2,1] 2 1");
        expectString("\"UURL\"", "\"L\"");
    }

    class Solution {
        StringBuilder res, start, dest;

        public String getDirections(TreeNode root, int startValue, int destValue) {
            res = new StringBuilder();
            start = new StringBuilder();
            dest = new StringBuilder();
            TreeNode ancestor = findRoot(root, startValue, destValue);
            dfsStart(ancestor, startValue);
            dfsDest(ancestor, destValue);
            return res.toString();
        }

        private void dfsStart(TreeNode root, int startValue) {
            if (root == null) {
                return;
            }
            if (root.val == startValue) {
                res.append(start);
                return;
            }
            start.append("U");
            dfsStart(root.left, startValue);
            start.deleteCharAt(start.length() - 1);
            start.append("U");
            dfsStart(root.right, startValue);
            start.deleteCharAt(start.length() - 1);
        }

        private void dfsDest(TreeNode root, int destValue) {
            if (root == null) {
                return;
            }
            if (root.val == destValue) {
                res.append(dest);
                return;
            }
            dest.append("L");
            dfsDest(root.left, destValue);
            dest.deleteCharAt(dest.length() - 1);
            dest.append("R");
            dfsDest(root.right, destValue);
            dest.deleteCharAt(dest.length() - 1);
        }

        private TreeNode findRoot(TreeNode root, int startValue, int destValue) {
            if (root == null || root.val == startValue || root.val == destValue) return root;
            TreeNode left = findRoot(root.left, startValue, destValue);
            TreeNode right = findRoot(root.right, startValue, destValue);
            if (left == null) return right;
            if (right == null) return left;
            return root;
        }
    }
}
