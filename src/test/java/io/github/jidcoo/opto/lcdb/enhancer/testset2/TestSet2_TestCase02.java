package io.github.jidcoo.opto.lcdb.enhancer.testset2;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.builtin.StringInputProvider;
import org.junit.Before;

import java.util.*;

@Require(types = StringInputProvider.class, values = {"\"hit\" \"cog\" [\"hot\",\"dot\",\"dog\",\"lot\",\"log\"," +
        "\"cog\"]", "\"hit\" \"cog\" [\"hot\",\"dot\",\"dog\",\"lot\",\"log\"]"})
public class TestSet2_TestCase02 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriver();
        expectString("[[\"hit\",\"hot\",\"dot\",\"dog\",\"cog\"],[\"hit\",\"hot\",\"lot\",\"log\",\"cog\"]]", "[]");
    }

    class Solution {
        public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
            List<List<String>> res = new ArrayList<>();
            Set<String> dict = new HashSet<>(wordList);
            if (!dict.contains(endWord)) {
                return res;
            }

            dict.remove(beginWord);

            Map<String, Integer> steps = new HashMap<String, Integer>();
            steps.put(beginWord, 0);
            Map<String, List<String>> from = new HashMap<String, List<String>>();
            int step = 1;
            boolean found = false;
            int wordLen = beginWord.length();
            Queue<String> queue = new ArrayDeque<String>();
            queue.offer(beginWord);
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    String currWord = queue.poll();
                    char[] charArray = currWord.toCharArray();
                    for (int j = 0; j < wordLen; j++) {
                        char origin = charArray[j];
                        for (char c = 'a'; c <= 'z'; c++) {
                            charArray[j] = c;
                            String nextWord = String.valueOf(charArray);
                            if (steps.containsKey(nextWord) && step == steps.get(nextWord)) {
                                from.get(nextWord).add(currWord);
                            }
                            if (!dict.contains(nextWord)) {
                                continue;
                            }
                            dict.remove(nextWord);
                            queue.offer(nextWord);

                            from.putIfAbsent(nextWord, new ArrayList<>());
                            from.get(nextWord).add(currWord);
                            steps.put(nextWord, step);
                            if (nextWord.equals(endWord)) {
                                found = true;
                            }
                        }
                        charArray[j] = origin;
                    }
                }
                step++;
                if (found) {
                    break;
                }
            }

            if (found) {
                Deque<String> path = new ArrayDeque<>();
                path.add(endWord);
                backtrack(from, path, beginWord, endWord, res);
            }
            return res;
        }

        public void backtrack(Map<String, List<String>> from, Deque<String> path, String beginWord, String cur,
                              List<List<String>> res) {
            if (cur.equals(beginWord)) {
                res.add(new ArrayList<>(path));
                return;
            }
            for (String precursor : from.get(cur)) {
                path.addFirst(precursor);
                backtrack(from, path, beginWord, precursor, res);
                path.removeFirst();
            }
        }
    }
}
