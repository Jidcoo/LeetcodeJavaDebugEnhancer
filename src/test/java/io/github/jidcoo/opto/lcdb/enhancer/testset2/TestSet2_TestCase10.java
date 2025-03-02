package io.github.jidcoo.opto.lcdb.enhancer.testset2;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSet2_TestCase10 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("\"this apple is sweet\" \"this apple is sour\"",
                "\"apple apple\" \"banana\"");
        expectString("[\"sweet\",\"sour\"]", "[\"banana\"]");
    }

    class Solution {
        public String[] uncommonFromSentences(String s1, String s2) {
            Map<String, Integer> freq = new HashMap<String, Integer>();
            insert(s1, freq);
            insert(s2, freq);

            List<String> ans = new ArrayList<String>();
            for (Map.Entry<String, Integer> entry : freq.entrySet()) {
                if (entry.getValue() == 1) {
                    ans.add(entry.getKey());
                }
            }
            return ans.toArray(new String[0]);
        }

        public void insert(String s, Map<String, Integer> freq) {
            String[] arr = s.split(" ");
            for (String word : arr) {
                freq.put(word, freq.getOrDefault(word, 0) + 1);
            }
        }
    }
}
