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

package io.github.jidcoo.opto.lcdb.enhancer.testset2;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.TreeNode;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.builtin.StringInputProvider;
import org.junit.Before;

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}.
 *
 * @author Jidcoo
 */
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
