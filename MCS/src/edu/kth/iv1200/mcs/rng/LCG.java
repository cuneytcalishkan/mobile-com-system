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
     * The mean value for exponential distribution.
     */
    private double mean;
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

    /**
     * 
     * @param Xi - The seed value.
     * @param expMean - Mean value for exponential distribution.
     * @param interArrivalMean  - the interarrival mean time in minutes.
     */
    public LCG(double Xi, double expMean, double interArrivalMean) {
        this.Xi = Xi;
        this.mean = expMean;
    }

    /**
     * Generates a uniform random number on the interval[0,1) using Linear Congruential Generation method.
     * @return Uniformly distributed random number on the interval [0,1).
     */
    public double nextRand() {
        Xi = (a * Xi + c) % m;
        return Xi / m;
    }

    /**
     * Generates the next event time which is exponentially distributed
     * @return The next event time.
     */
    public double nextExp() {
        double result = -mean * Math.log(nextRand());
        return result;
    }
}
