package io.github.jidcoo.opto.lcdb.enhancer.testset4;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

public class TestSet4_TestCase08 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"MagicDictionary\", \"buildDict\", \"search\", \"search\", \"search\", " +
                "\"search\"] [[], [[\"hello\", \"leetcode\"]], [\"hello\"], [\"hhllo\"], [\"hell\"], [\"leetcoded\"]]");
        expectString("[null,null,false,true,false,false]");
    }

    class MagicDictionary {
        private String[] words;

        public MagicDictionary() {

        }

        public void buildDict(String[] dictionary) {
            words = dictionary;
        }

        public boolean search(String searchWord) {
            for (String word : words) {
                if (word.length() != searchWord.length()) {
                    continue;
                }

                int diff = 0;
                for (int i = 0; i < word.length(); ++i) {
                    if (word.charAt(i) != searchWord.charAt(i)) {
                        ++diff;
                        if (diff > 1) {
                            break;
                        }
                    }
                }
                if (diff == 1) {
                    return true;
                }
            }
            return false;
        }
    }
}
