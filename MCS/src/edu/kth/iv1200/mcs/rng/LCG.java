package edu.kth.iv1200.mcs.rng;

/**
 * @author Cuneyt Caliskan
 */
public class LCG {

    /**
     * The initial seed and the value calculated for each new random value.
     */
    private double Xi;
    /**
     * LCG parameter.
     */
    public static double m = Math.pow(2, 31) - 1;
    /**
     * LCG parameter.
     */
    public static double a = 314159269;
    /**
     * LCG parameter.
     */
    public static double c = 453806245;
    private double R1;
    private double R2;

    /**
     * 
     * @param Xi - The seed value.
     * @param expMean - Mean value for exponential distribution.
     * @param interArrivalMean  - the interarrival mean time in minutes.
     */
    public LCG(double Xi) {
        this.Xi = Xi;
    }

    /**
     * Generates a uniform random number on the interval[0,1) using Linear Congruential Generation method.
     * @return Uniformly distributed random number on the interval [0,1).
     */
    public synchronized double nextRand() {
        Xi = (a * Xi + c) % m;
        return Xi / m;
    }

    /**
     * Generates the next event time which is exponentially distributed
     * @return The next event time.
     */
    public synchronized double nextExp(double mean) {
        double result = -mean * Math.log(nextRand());
        return result;
    }

    /**
     * Generated the next uniformly distributed event time on [0, b)
     * @param b Maximum range value
     * @return The next uniformly distributed value between 0 and b
     */
    public synchronized double nextUniform(double b) {
        double result = b * nextRand();
        return result;
    }

    public synchronized double nextNormal(double m, double stdev) {
        double result = 0;
        boolean flag = true;
        double Z = 0;
        if (flag) {
            R1 = nextRand();
            R2 = nextRand();
            Z = Math.sqrt(-2 * Math.log(R1)) * Math.cos(2 * Math.PI * R2);
        } else {
            Z = Math.sqrt(-2 * Math.log(R1)) * Math.sin(2 * Math.PI * R2);
        }
        flag = !flag;
        result = m + stdev * Z;
        return result;
    }
}
