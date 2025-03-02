package io.github.jidcoo.opto.lcdb.enhancer.testset4;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

public class TestSet4_TestCase13 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"TextEditor\", \"addText\", \"deleteText\", \"addText\", " +
                "\"cursorRight\", \"cursorLeft\", \"deleteText\", \"cursorLeft\", \"cursorRight\"] " +
                "[[], [\"leetcode\"], [4], [\"practice\"], [3], [8], [10], [2], [6]]");
        expectString("[null,null,4,null,\"etpractice\",\"leet\",4,\"\",\"practi\"]");
    }

    class TextEditor {

        private Node cursor;

        public TextEditor() {
            cursor = new Node('\0');
        }

        public void addText(String text) {
            for (char c : text.toCharArray()) {
                cursor.insert(c);
            }
        }

        public int deleteText(int k) {
            int count = 0;
            while (k > 0 && cursor.prev != null) {
                cursor.remove();
                k--;
                count++;
            }
            return count;
        }

        public String cursorLeft(int k) {
            while (k > 0 && cursor.prev != null) {
                cursor = cursor.prev;
                k--;
            }
            Node head = cursor;
            for (int i = 0; i < 10 && head.prev != null; i++) {
                head = head.prev;
            }
            return head.range(cursor);
        }

        public String cursorRight(int k) {
            while (k > 0 && cursor.next != null) {
                cursor = cursor.next;
                k--;
            }
            Node head = cursor;
            for (int i = 0; i < 10 && head.prev != null; i++) {
                head = head.prev;
            }
            return head.range(cursor);
        }

        class Node {
            char val;
            Node prev, next;

            Node(char val) {
                this.val = val;
            }

            void insert(char val) {
                Node node = new Node(val);
                node.next = this;
                node.prev = this.prev;
                if (this.prev != null) {
                    this.prev.next = node;
                }
                this.prev = node;
            }

            void remove() {
                Node node = this.prev;
                this.prev = node.prev;
                if (node.prev != null) {
                    node.prev.next = this;
                }
            }

            String range(Node end) {
                StringBuilder sb = new StringBuilder();
                Node node = this;
                while (node != end) {
                    sb.append(node.val);
                    node = node.next;
                }
                return sb.toString();
            }
        }
    }
}
