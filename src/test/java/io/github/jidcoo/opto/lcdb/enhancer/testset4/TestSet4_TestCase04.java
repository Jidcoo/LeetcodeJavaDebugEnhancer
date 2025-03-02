package io.github.jidcoo.opto.lcdb.enhancer.testset4;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

public class TestSet4_TestCase04 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"NumArray\", \"sumRange\", \"sumRange\", \"sumRange\"] [[[-2, 0, 3, -5, " +
                "2, -1]], [0, 2], [2, 5], [0, 5]]");
        expectString("[null,1,-1,-3]");
    }

    class NumArray {
        int[] sums;

        public NumArray(int[] nums) {
            int n = nums.length;
            sums = new int[n + 1];
            for (int i = 0; i < n; i++) {
                sums[i + 1] = sums[i] + nums[i];
            }
        }

        public int sumRange(int i, int j) {
            return sums[j + 1] - sums[i];
        }
    }
}
