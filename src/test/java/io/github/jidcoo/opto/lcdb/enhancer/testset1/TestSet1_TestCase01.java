package io.github.jidcoo.opto.lcdb.enhancer.testset1;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

public class TestSet1_TestCase01 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[2,7,11,15] 9", "[3,2,4] 6", "[3,3] 6");
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
