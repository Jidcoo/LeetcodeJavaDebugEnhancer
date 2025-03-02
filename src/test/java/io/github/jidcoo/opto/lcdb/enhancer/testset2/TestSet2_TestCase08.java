package io.github.jidcoo.opto.lcdb.enhancer.testset2;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class TestSet2_TestCase08 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn(
                "[\"5\",\"2\",\"C\",\"D\",\"+\"]",
                "[\"5\",\"-2\",\"4\",\"C\",\"D\",\"9\",\"+\",\"+\"]",
                "[\"1\"]"
        );
        expectString("30", "27", "1");
    }

    class Solution {
        public int calPoints(String[] ops) {
            int ret = 0;
            List<Integer> points = new ArrayList<Integer>();
            for (String op : ops) {
                int n = points.size();
                switch (op.charAt(0)) {
                    case '+':
                        ret += points.get(n - 1) + points.get(n - 2);
                        points.add(points.get(n - 1) + points.get(n - 2));
                        break;
                    case 'D':
                        ret += 2 * points.get(n - 1);
                        points.add(2 * points.get(n - 1));
                        break;
                    case 'C':
                        ret -= points.get(n - 1);
                        points.remove(n - 1);
                        break;
                    default:
                        ret += Integer.parseInt(op);
                        points.add(Integer.parseInt(op));
                        break;
                }
            }
            return ret;
        }
    }
}
