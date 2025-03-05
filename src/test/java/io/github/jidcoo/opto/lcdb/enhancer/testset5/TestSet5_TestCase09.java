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

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}
 * with data structure {@link TreeNode}.
 *
 * @author Jidcoo
 */
public class TestSet5_TestCase09 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[2,1,4] [1,0,3]", "[1,null,8] [8,1]");
        expectString("[0,1,1,2,3,4]", "[1,1,8,8]");
    }

    class Solution {
        public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
            List<Integer> nums1 = new ArrayList<Integer>();
            List<Integer> nums2 = new ArrayList<Integer>();
            inorder(root1, nums1);
            inorder(root2, nums2);

            List<Integer> merged = new ArrayList<Integer>();
            int p1 = 0, p2 = 0;
            while (true) {
                if (p1 == nums1.size()) {
                    merged.addAll(nums2.subList(p2, nums2.size()));
                    break;
                }
                if (p2 == nums2.size()) {
                    merged.addAll(nums1.subList(p1, nums1.size()));
                    break;
                }
                if (nums1.get(p1) < nums2.get(p2)) {
                    merged.add(nums1.get(p1++));
                } else {
                    merged.add(nums2.get(p2++));
                }
            }
            return merged;
        }

        private void inorder(TreeNode node, List<Integer> res) {
            if (node != null) {
                inorder(node.left, res);
                res.add(node.val);
                inorder(node.right, res);
            }
        }
    }
}
