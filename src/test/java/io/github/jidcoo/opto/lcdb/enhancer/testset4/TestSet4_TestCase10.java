package io.github.jidcoo.opto.lcdb.enhancer.testset4;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

import java.util.PriorityQueue;

public class TestSet4_TestCase10 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"KthLargest\", \"add\", \"add\", \"add\", \"add\", \"add\"] " +
                "[[3, [4, 5, 8, 2]], [3], [5], [10], [9], [4]]");
        expectString("[null,4,5,5,8,8]");
    }

    class KthLargest {
        PriorityQueue<Integer> pq;
        int k;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            pq = new PriorityQueue<Integer>();
            for (int x : nums) {
                add(x);
            }
        }

        public int add(int val) {
            pq.offer(val);
            if (pq.size() > k) {
                pq.poll();
            }
            return pq.peek();
        }
    }
}
