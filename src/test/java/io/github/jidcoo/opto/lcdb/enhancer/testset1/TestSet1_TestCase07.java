package io.github.jidcoo.opto.lcdb.enhancer.testset1;

import io.github.jidcoo.opto.lcdb.enhancer.UnitTestDriver;
import org.junit.Before;

public class TestSet1_TestCase07 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[1,2,3,4]", "[4,2,3,15]");
        expectString("6", "5");
    }

    class Solution {
        final int[] PRIMES = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        final int NUM_MAX = 30;
        final int MOD = 1000000007;

        public int numberOfGoodSubsets(int[] nums) {
            int[] freq = new int[NUM_MAX + 1];
            for (int num : nums) {
                ++freq[num];
            }

            int[] f = new int[1 << PRIMES.length];
            f[0] = 1;
            for (int i = 0; i < freq[1]; ++i) {
                f[0] = f[0] * 2 % MOD;
            }

            for (int i = 2; i <= NUM_MAX; ++i) {
                if (freq[i] == 0) {
                    continue;
                }

                int subset = 0, x = i;
                boolean check = true;
                for (int j = 0; j < PRIMES.length; ++j) {
                    int prime = PRIMES[j];
                    if (x % (prime * prime) == 0) {
                        check = false;
                        break;
                    }
                    if (x % prime == 0) {
                        subset |= (1 << j);
                    }
                }
                if (!check) {
                    continue;
                }

                // 动态规划
                for (int mask = (1 << PRIMES.length) - 1; mask > 0; --mask) {
                    if ((mask & subset) == subset) {
                        f[mask] = (int) ((f[mask] + ((long) f[mask ^ subset]) * freq[i]) % MOD);
                    }
                }
            }

            int ans = 0;
            for (int mask = 1, maskMax = (1 << PRIMES.length); mask < maskMax; ++mask) {
                ans = (ans + f[mask]) % MOD;
            }

            return ans;
        }
    }
}
