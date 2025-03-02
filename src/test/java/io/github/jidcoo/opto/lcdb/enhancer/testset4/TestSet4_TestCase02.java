package io.github.jidcoo.opto.lcdb.enhancer.testset4;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

import java.util.Deque;
import java.util.LinkedList;

public class TestSet4_TestCase02 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"MinStack\",\"push\",\"push\",\"push\",\"getMin\",\"pop\",\"top\"," +
                "\"getMin\"] [[],[-2],[0],[-3],[],[],[],[]]");
        expectString("[null,null,null,null,-3,null,0,-2]");
    }

    class MinStack {
        Deque<Integer> xStack;
        Deque<Integer> minStack;

        public MinStack() {
            xStack = new LinkedList<Integer>();
            minStack = new LinkedList<Integer>();
            minStack.push(Integer.MAX_VALUE);
        }

        public void push(int x) {
            xStack.push(x);
            minStack.push(Math.min(minStack.peek(), x));
        }

        public void pop() {
            xStack.pop();
            minStack.pop();
        }

        public int top() {
            return xStack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }
}
