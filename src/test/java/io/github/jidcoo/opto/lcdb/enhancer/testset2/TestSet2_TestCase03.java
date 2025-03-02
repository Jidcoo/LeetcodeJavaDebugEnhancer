package io.github.jidcoo.opto.lcdb.enhancer.testset2;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

public class TestSet2_TestCase03 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("2","3");
        expectString("2","3");
    }

    class Solution {
        public int climbStairs(int n) {
            int p = 0, q = 0, r = 1;
            for (int i = 1; i <= n; ++i) {
                p = q;
                q = r;
                r = p + q;
            }
            return r;
        }
    }
}
