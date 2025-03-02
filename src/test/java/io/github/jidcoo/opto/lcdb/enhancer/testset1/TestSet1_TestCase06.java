package io.github.jidcoo.opto.lcdb.enhancer.testset1;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestSet1_TestCase06 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[-1,0,1,2,-1,-4]", "[0,1,1]", "[0,0,0]");
        expectString("[[-1,-1,2],[-1,0,1]]", "[]", "[[0,0,0]]");
    }

    class Solution {
        public List<List<Integer>> threeSum(int[] nums) {
            int n = nums.length;
            Arrays.sort(nums);
            List<List<Integer>> ans = new ArrayList<List<Integer>>();
            for (int first = 0; first < n; ++first) {
                if (first > 0 && nums[first] == nums[first - 1]) {
                    continue;
                }
                int third = n - 1;
                int target = -nums[first];
                for (int second = first + 1; second < n; ++second) {
                    if (second > first + 1 && nums[second] == nums[second - 1]) {
                        continue;
                    }
                    while (second < third && nums[second] + nums[third] > target) {
                        --third;
                    }
                    if (second == third) {
                        break;
                    }
                    if (nums[second] + nums[third] == target) {
                        List<Integer> list = new ArrayList<Integer>();
                        list.add(nums[first]);
                        list.add(nums[second]);
                        list.add(nums[third]);
                        ans.add(list);
                    }
                }
            }
            return ans;
        }
    }
}
