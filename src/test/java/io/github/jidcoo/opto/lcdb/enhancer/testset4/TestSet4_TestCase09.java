package io.github.jidcoo.opto.lcdb.enhancer.testset4;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

public class TestSet4_TestCase09 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[\"MapSum\", \"insert\", \"sum\", \"insert\", \"sum\"] [[], [\"apple\", " +
                "3], [\"ap\"], [\"app\", 2], [\"ap\"]]");
        expectString("[null,null,3,null,5]");
    }

    class MapSum {
        Map<String, Integer> map;

        public MapSum() {
            map = new HashMap<>();
        }

        public void insert(String key, int val) {
            map.put(key, val);
        }

        public int sum(String prefix) {
            int res = 0;
            for (String s : map.keySet()) {
                if (s.startsWith(prefix)) {
                    res += map.get(s);
                }
            }
            return res;
        }
    }
}
