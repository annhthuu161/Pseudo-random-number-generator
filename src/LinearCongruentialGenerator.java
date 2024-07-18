public class LinearCongruentialGenerator {
    private long a;
    private long c;
    private long m;
    private long current;

    public LinearCongruentialGenerator(long seed, long a, long c, long m) {
        this.current = seed;
        this.a = a;
        this.c = c;
        this.m = m;
    }

    public long next() {
        current = (a * current + c) % m;
        return current;
    }

    public double random() {
        return (double) next() / m;
    }
}
