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

import java.util.LinkedList;
import java.util.Queue;

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}
 * with data structure design problem.
 *
 * @author Jidcoo
 */
public class TestSet4_TestCase15 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"StreamChecker\", \"query\", \"query\", \"query\", \"query\", \"query\"," +
                " \"query\", \"query\", \"query\", \"query\", \"query\", \"query\", \"query\"] " +
                "[[[\"cd\", \"f\", \"kl\"]], [\"a\"], [\"b\"], [\"c\"], [\"d\"], [\"e\"], [\"f\"], [\"g\"], [\"h\"], " +
                "[\"i\"], [\"j\"], [\"k\"], [\"l\"]]");
        expectString("[null,false,false,false,true,false,true,false,false,false,false,false,true]");
    }

    class StreamChecker {

        class TrieNode {
            TrieNode[] children;
            boolean isEnd;
            TrieNode fail;

            public TrieNode() {
                children = new TrieNode[26];
            }

            public TrieNode getChild(int index) {
                return children[index];
            }

            public void setChild(int index, TrieNode node) {
                children[index] = node;
            }

            public boolean getIsEnd() {
                return isEnd;
            }

            public void setIsEnd(boolean b) {
                isEnd = b;
            }

            public TrieNode getFail() {
                return fail;
            }

            public void setFail(TrieNode node) {
                fail = node;
            }
        }

        TrieNode root;
        TrieNode temp;

        public StreamChecker(String[] words) {
            root = new TrieNode();
            for (String word : words) {
                TrieNode cur = root;
                for (int i = 0; i < word.length(); i++) {
                    int index = word.charAt(i) - 'a';
                    if (cur.getChild(index) == null) {
                        cur.setChild(index, new TrieNode());
                    }
                    cur = cur.getChild(index);
                }
                cur.setIsEnd(true);
            }
            root.setFail(root);
            Queue<TrieNode> q = new LinkedList<>();
            for (int i = 0; i < 26; i++) {
                if (root.getChild(i) != null) {
                    root.getChild(i).setFail(root);
                    q.add(root.getChild(i));
                } else {
                    root.setChild(i, root);
                }
            }
            while (!q.isEmpty()) {
                TrieNode node = q.poll();
                node.setIsEnd(node.getIsEnd() || node.getFail().getIsEnd());
                for (int i = 0; i < 26; i++) {
                    if (node.getChild(i) != null) {
                        node.getChild(i).setFail(node.getFail().getChild(i));
                        q.offer(node.getChild(i));
                    } else {
                        node.setChild(i, node.getFail().getChild(i));
                    }
                }
            }

            temp = root;
        }

        public boolean query(char letter) {
            temp = temp.getChild(letter - 'a');
            return temp.getIsEnd();
        }
    }
}
