/*
 * Copyright (C) 2024-2026 Jidcoo(https://github.com/jidcoo).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.jidcoo.opto.lcdb.enhancer.testset4;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}
 * with data structure design problem.
 *
 * @author Jidcoo
 */
public class TestSet4_TestCase03 extends UnitTestDriver {


    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"WordDictionary\",\"addWord\",\"addWord\",\"addWord\",\"search\"," +
                "\"search\",\"search\",\"search\"] [[],[\"bad\"],[\"dad\"],[\"mad\"],[\"pad\"],[\"bad\"],[\".ad\"]," +
                "[\"b..\"]]");
        expectString("[null,null,null,null,false,true,true,true]");
    }

    class WordDictionary {
        private Trie root;

        public WordDictionary() {
            root = new Trie();
        }

        public void addWord(String word) {
            root.insert(word);
        }

        public boolean search(String word) {
            return dfs(word, 0, root);
        }

        private boolean dfs(String word, int index, Trie node) {
            if (index == word.length()) {
                return node.isEnd();
            }
            char ch = word.charAt(index);
            if (Character.isLetter(ch)) {
                int childIndex = ch - 'a';
                Trie child = node.getChildren()[childIndex];
                if (child != null && dfs(word, index + 1, child)) {
                    return true;
                }
            } else {
                for (int i = 0; i < 26; i++) {
                    Trie child = node.getChildren()[i];
                    if (child != null && dfs(word, index + 1, child)) {
                        return true;
                    }
                }
            }
            return false;
        }


        class Trie {
            private Trie[] children;
            private boolean isEnd;

            public Trie() {
                children = new Trie[26];
                isEnd = false;
            }

            public void insert(String word) {
                Trie node = this;
                for (int i = 0; i < word.length(); i++) {
                    char ch = word.charAt(i);
                    int index = ch - 'a';
                    if (node.children[index] == null) {
                        node.children[index] = new Trie();
                    }
                    node = node.children[index];
                }
                node.isEnd = true;
            }

            public Trie[] getChildren() {
                return children;
            }

            public boolean isEnd() {
                return isEnd;
            }
        }
    }
}
