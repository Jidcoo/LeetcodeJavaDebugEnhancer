package io.github.jidcoo.opto.lcdb.enhancer.testset1;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

public class TestSet1_TestCase05 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[1,2,3,4]", "[1,1,1,1,1]", "[3,1,2,10,1]");
        expectString("[1,3,6,10]", "[1,2,3,4,5]", "[3,4,6,16,17]");
    }

    class Solution {
        public int[] runningSum(int[] nums) {
            int n = nums.length;
            for (int i = 1; i < n; i++) {
                nums[i] += nums[i - 1];
            }
            return nums;
        }
    }
}
