package io.github.jidcoo.opto.lcdb.enhancer.testset1;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

public class TestSet1_TestCase08 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[[3,0,8,4],[2,4,5,7],[9,2,6,3],[0,3,1,0]]", "[[0,0,0],[0,0,0],[0,0,0]]");
        expectString("35", "0");
    }

    class Solution {
        public int maxIncreaseKeepingSkyline(int[][] grid) {
            int n = grid.length;
            int[] rowMax = new int[n];
            int[] colMax = new int[n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    rowMax[i] = Math.max(rowMax[i], grid[i][j]);
                    colMax[j] = Math.max(colMax[j], grid[i][j]);
                }
            }
            int ans = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    ans += Math.min(rowMax[i], colMax[j]) - grid[i][j];
                }
            }
            return ans;
        }
    }
}
