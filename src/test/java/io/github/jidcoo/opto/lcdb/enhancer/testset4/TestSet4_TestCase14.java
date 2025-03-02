package io.github.jidcoo.opto.lcdb.enhancer.testset4;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class TestSet4_TestCase14 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"SnapshotArray\",\"set\",\"snap\",\"set\",\"get\"]" +
                " [[3],[0,5],[],[0,6],[0,0]]");
        expectString("[null,null,0,null,5]");
    }

    @SuppressWarnings("unchecked")
    class SnapshotArray {
        private int snap_cnt;
        private List<int[]>[] data;

        public SnapshotArray(int length) {
            snap_cnt = 0;
            data = new List[length];
            for (int i = 0; i < length; i++) {
                data[i] = new ArrayList<int[]>();
            }
        }

        public void set(int index, int val) {
            data[index].add(new int[]{snap_cnt, val});
        }

        public int snap() {
            return snap_cnt++;
        }

        public int get(int index, int snap_id) {
            int x = binarySearch(index, snap_id);
            return x == 0 ? 0 : data[index].get(x - 1)[1];
        }

        private int binarySearch(int index, int snap_id) {
            int low = 0, high = data[index].size();
            while (low < high) {
                int mid = low + (high - low) / 2;
                int[] pair = data[index].get(mid);
                if (pair[0] > snap_id + 1 || (pair[0] == snap_id + 1 && pair[1] >= 0)) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }
            return low;
        }
    }
}
