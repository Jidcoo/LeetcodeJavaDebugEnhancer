package io.github.jidcoo.opto.lcdb.enhancer.testset1;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.builtin.StringInputProvider;
import org.junit.Before;

@Require(types = StringInputProvider.class, values = "[2,7,11,15] 9")
@Require(types = StringInputProvider.class, values = {"[3,2,4] 6", "[3,3] 6"})
public class TestSet1_TestCase03 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriver();
        expectString("[0,1]", "[1,2]", "[0,1]");
    }

    class Solution {
        public int[] twoSum(int[] nums, int target) {
            int n = nums.length;
            for (int i = 0; i < n; ++i) {
                for (int j = i + 1; j < n; ++j) {
                    if (nums[i] + nums[j] == target) {
                        return new int[]{i, j};
                    }
                }
            }
            return new int[0];
        }
    }
}
