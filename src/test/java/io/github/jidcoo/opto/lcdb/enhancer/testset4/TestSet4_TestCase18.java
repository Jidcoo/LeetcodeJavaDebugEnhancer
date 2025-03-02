package io.github.jidcoo.opto.lcdb.enhancer.testset4;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class TestSet4_TestCase18 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"MyCalendar\", \"book\", \"book\", \"book\"] " +
                "[[], [10, 20], [15, 25], [20, 30]]");
        expectString("[null,true,false,true]");
    }

    class MyCalendar {
        List<int[]> booked;

        public MyCalendar() {
            booked = new ArrayList<int[]>();
        }

        public boolean book(int start, int end) {
            for (int[] arr : booked) {
                int l = arr[0], r = arr[1];
                if (l < end && start < r) {
                    return false;
                }
            }
            booked.add(new int[]{start, end});
            return true;
        }
    }
}
