import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MersenneTwisterBigInt {
    private static final int N = 624;
    private static final int M = 397;
    private static final int MATRIX_A = 0x9908B0DF;
    private static final int UPPER_MASK = 0x80000000;
    private static final int LOWER_MASK = 0x7FFFFFFF;

    private int[] mt = new int[N];
    private int mti = N + 1;

    public MersenneTwisterBigInt(int seed) {
        mt[0] = seed;
        for (mti = 1; mti < N; mti++) {
            mt[mti] = (1812433253 * (mt[mti - 1] ^ (mt[mti - 1] >>> 30)) + mti);
        }
    }

    public int nextInt() {
        int y;
        int[] mag01 = {0x0, MATRIX_A};

        if (mti >= N) {
            int kk;

            for (kk = 0; kk < N - M; kk++) {
                y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
            }
            for (; kk < N - 1; kk++) {
                y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
            }
            y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
            mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

            mti = 0;
        }

        y = mt[mti++];

        y ^= (y >>> 11);
        y ^= (y << 7) & 0x9D2C5680;
        y ^= (y << 15) & 0xEFC60000;
        y ^= (y >>> 18);

        return y;
    }

    public BigInteger nextBigInt(int bitLength) {
        int numInts = (bitLength + 31) / 32;
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < numInts; i++) {
            int value = nextInt();
            result = result.shiftLeft(32).add(BigInteger.valueOf(value & 0xFFFFFFFFL));
        }
        return result;
    }

    public static void main(String[] args) {
        int seed = 42;
        MersenneTwisterBigInt mt = new MersenneTwisterBigInt(seed);

        List<Integer> bitLengths = List.of(512, 1024, 2048);
        for (int bitLength : bitLengths) {
            long startTime = System.nanoTime();
            BigInteger randomBigInt = mt.nextBigInt(bitLength);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;

            long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            ThreadMXBean bean = ManagementFactory.getThreadMXBean();
            long cpuTime = bean.getCurrentThreadCpuTime();

            System.out.println("Generated number with " + bitLength + " bits: " + randomBigInt);
            System.out.println("Time taken (nanoseconds): " + duration);
            System.out.println("Memory used (bytes): " + (memoryAfter - memoryBefore));
            System.out.println("CPU time (nanoseconds): " + cpuTime);
        }
    }
}
