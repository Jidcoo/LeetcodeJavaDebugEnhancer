package io.github.jidcoo.opto.lcdb.enhancer.testset4;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

public class TestSet4_TestCase12 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"Allocator\", \"allocate\", \"allocate\", \"allocate\", \"freeMemory\", " +
                "\"allocate\", \"allocate\", \"allocate\", \"freeMemory\", \"allocate\", \"freeMemory\"] [[10], [1, " +
                "1], [1, 2], [1, 3], [2], [3, 4], [1, 1], [1, 1], [1], [10, 2], [7]]");
        expectString("[null,0,1,2,1,3,1,6,3,-1,0]");
    }

    class Allocator {
        private int n;
        private int[] memory;

        public Allocator(int n) {
            this.n = n;
            this.memory = new int[n];
        }

        public int allocate(int size, int mID) {
            int count = 0;
            for (int i = 0; i < n; ++i) {
                if (memory[i] != 0) {
                    count = 0;
                } else {
                    ++count;
                    if (count == size) {
                        for (int j = i - count + 1; j <= i; ++j) {
                            memory[j] = mID;
                        }
                        return i - count + 1;
                    }
                }
            }
            return -1;
        }

        public int freeMemory(int mID) {
            int count = 0;
            for (int i = 0; i < n; ++i) {
                if (memory[i] == mID) {
                    ++count;
                    memory[i] = 0;
                }
            }
            return count;
        }
    }
}
