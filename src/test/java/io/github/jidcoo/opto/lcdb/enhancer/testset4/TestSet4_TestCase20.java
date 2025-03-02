package io.github.jidcoo.opto.lcdb.enhancer.testset4;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class TestSet4_TestCase20 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"BrowserHistory\",\"visit\",\"visit\",\"visit\",\"back\",\"back\"," +
                "\"forward\",\"visit\",\"forward\",\"back\",\"back\"] " +
                "[[\"leetcode.com\"],[\"google.com\"],[\"facebook.com\"],[\"youtube.com\"],[1],[1],[1],[\"linkedin" +
                ".com\"],[2],[2],[7]]");
        expectString("[null,null,null,null,\"facebook.com\",\"google.com\",\"facebook.com\",null,\"linkedin.com\"," +
                "\"google.com\",\"leetcode.com\"]");
    }

    class BrowserHistory {
        private List<String> urls;
        private int currIndex;

        public BrowserHistory(String homepage) {
            this.urls = new ArrayList<>();
            this.urls.add(homepage);
            this.currIndex = 0;
        }

        public void visit(String url) {
            while (urls.size() > currIndex + 1) {
                urls.remove(urls.size() - 1);
            }
            urls.add(url);
            this.currIndex++;
        }

        public String back(int steps) {
            currIndex = Math.max(currIndex - steps, 0);
            return urls.get(currIndex);
        }

        public String forward(int steps) {
            currIndex = Math.min(currIndex + steps, urls.size() - 1);
            return urls.get(currIndex);
        }
    }
}
