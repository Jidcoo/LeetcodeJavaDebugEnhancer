package io.github.jidcoo.opto.lcdb.enhancer.testset2;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.builtin.StringInputProvider;
import org.junit.Before;

@Require(types = StringInputProvider.class, values = "[[1,1,1],[1,0,1],[1,1,1]]")
public class TestSet2_TestCase01 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriver();
        ignoreTestResult();
    }

    class Solution {
        public void setZeroes(int[][] matrix) {
            int m = matrix.length, n = matrix[0].length;
            boolean[] row = new boolean[m];
            boolean[] col = new boolean[n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][j] == 0) {
                        row[i] = col[j] = true;
                    }
                }
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (row[i] || col[j]) {
                        matrix[i][j] = 0;
                    }
                }
            }
        }
    }
}
