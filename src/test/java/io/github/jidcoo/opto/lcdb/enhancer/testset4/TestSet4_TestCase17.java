package io.github.jidcoo.opto.lcdb.enhancer.testset4;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestSet4_TestCase17 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"AllOne\", \"inc\", \"inc\", \"getMaxKey\", \"getMinKey\", \"inc\", " +
                "\"getMaxKey\", \"getMinKey\"] " +
                "[[], [\"hello\"], [\"hello\"], [], [], [\"leet\"], [], []]");
        expectString("[null,null,null,\"hello\",\"hello\",null,\"hello\",\"leet\"]");
    }

    class AllOne {
        Node root;
        Map<String, Node> nodes;

        public AllOne() {
            root = new Node();
            root.prev = root;
            root.next = root;
            nodes = new HashMap<String, Node>();
        }

        public void inc(String key) {
            if (nodes.containsKey(key)) {
                Node cur = nodes.get(key);
                Node nxt = cur.next;
                if (nxt == root || nxt.count > cur.count + 1) {
                    nodes.put(key, cur.insert(new Node(key, cur.count + 1)));
                } else {
                    nxt.keys.add(key);
                    nodes.put(key, nxt);
                }
                cur.keys.remove(key);
                if (cur.keys.isEmpty()) {
                    cur.remove();
                }
            } else {
                if (root.next == root || root.next.count > 1) {
                    nodes.put(key, root.insert(new Node(key, 1)));
                } else {
                    root.next.keys.add(key);
                    nodes.put(key, root.next);
                }
            }
        }

        public void dec(String key) {
            Node cur = nodes.get(key);
            if (cur.count == 1) {
                nodes.remove(key);
            } else {
                Node pre = cur.prev;
                if (pre == root || pre.count < cur.count - 1) {
                    nodes.put(key, cur.prev.insert(new Node(key, cur.count - 1)));
                } else {
                    pre.keys.add(key);
                    nodes.put(key, pre);
                }
            }
            cur.keys.remove(key);
            if (cur.keys.isEmpty()) {
                cur.remove();
            }
        }

        public String getMaxKey() {
            return root.prev != null ? root.prev.keys.iterator().next() : "";
        }

        public String getMinKey() {
            return root.next != null ? root.next.keys.iterator().next() : "";
        }

        class Node {
            Node prev;
            Node next;
            Set<String> keys;
            int count;

            public Node() {
                this("", 0);
            }

            public Node(String key, int count) {
                this.count = count;
                keys = new HashSet<String>();
                keys.add(key);
            }

            public Node insert(Node node) {
                node.prev = this;
                node.next = this.next;
                node.prev.next = node;
                node.next.prev = node;
                return node;
            }

            public void remove() {
                this.prev.next = this.next;
                this.next.prev = this.prev;
            }
        }
    }

}
