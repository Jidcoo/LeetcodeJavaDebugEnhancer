package io.github.jidcoo.opto.lcdb.enhancer.testset4;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSet4_TestCase19 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"TopVotedCandidate\", \"q\", \"q\", \"q\", \"q\", \"q\", \"q\"] " +
                "[[[0, 1, 1, 0, 0, 1, 0], [0, 5, 10, 15, 20, 25, 30]], [3], [12], [25], [15], [24], [8]]");
        expectString("[null,0,1,1,0,0,1]");
    }

    class TopVotedCandidate {
        List<Integer> tops;
        Map<Integer, Integer> voteCounts;
        int[] times;

        public TopVotedCandidate(int[] persons, int[] times) {
            tops = new ArrayList<Integer>();
            voteCounts = new HashMap<Integer, Integer>();
            voteCounts.put(-1, -1);
            int top = -1;
            for (int i = 0; i < persons.length; ++i) {
                int p = persons[i];
                voteCounts.put(p, voteCounts.getOrDefault(p, 0) + 1);
                if (voteCounts.get(p) >= voteCounts.get(top)) {
                    top = p;
                }
                tops.add(top);
            }
            this.times = times;
        }

        public int q(int t) {
            int l = 0, r = times.length - 1;
            while (l < r) {
                int m = l + (r - l + 1) / 2;
                if (times[m] <= t) {
                    l = m;
                } else {
                    r = m - 1;
                }
            }
            return tops.get(l);
        }
    }
}
