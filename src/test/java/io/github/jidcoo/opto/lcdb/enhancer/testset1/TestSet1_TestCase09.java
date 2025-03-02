package io.github.jidcoo.opto.lcdb.enhancer.testset1;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class TestSet1_TestCase09 extends UnitTestDriver {
    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]",
                "[[2,1],[1,2]]");
        expectString("[[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]", "[[0,0],[0,1],[1,0],[1,1]]");
    }

    class Solution {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int[][] heights;
        int m, n;

        public List<List<Integer>> pacificAtlantic(int[][] heights) {
            this.heights = heights;
            this.m = heights.length;
            this.n = heights[0].length;
            boolean[][] pacific = new boolean[m][n];
            boolean[][] atlantic = new boolean[m][n];
            for (int i = 0; i < m; i++) {
                dfs(i, 0, pacific);
            }
            for (int j = 1; j < n; j++) {
                dfs(0, j, pacific);
            }
            for (int i = 0; i < m; i++) {
                dfs(i, n - 1, atlantic);
            }
            for (int j = 0; j < n - 1; j++) {
                dfs(m - 1, j, atlantic);
            }
            List<List<Integer>> result = new ArrayList<List<Integer>>();
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (pacific[i][j] && atlantic[i][j]) {
                        List<Integer> cell = new ArrayList<Integer>();
                        cell.add(i);
                        cell.add(j);
                        result.add(cell);
                    }
                }
            }
            return result;
        }

        public void dfs(int row, int col, boolean[][] ocean) {
            if (ocean[row][col]) {
                return;
            }
            ocean[row][col] = true;
            for (int[] dir : dirs) {
                int newRow = row + dir[0], newCol = col + dir[1];
                if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n && heights[newRow][newCol] >= heights[row][col]) {
                    dfs(newRow, newCol, ocean);
                }
            }
        }
    }
}
