package com.report.util;

public class GoUpstairs {
    public int countWays(int n) {
        long[] pre = {1, 2, 4};
        if (n <= 0) {
            return 0;
        } else if (n <= 3) {
            return (int) pre[n - 1];
        } else {
            for (int i = 4; i <= n; i++) {
                long tmp = ((pre[0] + pre[1]) % 1000000007 + pre[2]) % 1000000007;
                pre[0] = pre[1];
                pre[1] = pre[2];
                pre[2] = tmp;
            }
        }
        return (int) pre[2];
    }
}