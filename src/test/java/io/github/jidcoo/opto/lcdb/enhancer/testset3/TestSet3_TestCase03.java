package io.github.jidcoo.opto.lcdb.enhancer.testset3;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.ListNode;
import org.junit.Before;

import java.util.HashSet;
import java.util.Set;

public class TestSet3_TestCase03 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[0,1,2,3] [0,1,3]", "[0,1,2,3,4] [0,3,1,4]");
        expectString("2", "2");
    }

    class Solution {
        public int numComponents(ListNode head, int[] nums) {
            Set<Integer> numsSet = new HashSet<Integer>();
            for (int num : nums) {
                numsSet.add(num);
            }
            boolean inSet = false;
            int res = 0;
            while (head != null) {
                if (numsSet.contains(head.val)) {
                    if (!inSet) {
                        inSet = true;
                        res++;
                    }
                } else {
                    inSet = false;
                }
                head = head.next;
            }
            return res;
        }
    }
}
