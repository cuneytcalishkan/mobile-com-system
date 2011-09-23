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
     * The mean service time in minutes.
     */
    private double serviceMean;
    /**
     * The mean interarrival time in minutes.
     */
    private double interArrivalMean;
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
     * @param serviceMean - the service mean time in minutes.
     * @param interArrivalMean  - the interarrival mean time in minutes.
     */
    public LCG(double Xi, double serviceMean, double interArrivalMean) {
        this.Xi = Xi;
        this.serviceMean = serviceMean;
        this.interArrivalMean = interArrivalMean;
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
     * Generates the next arrival event time which is exponentially distributed.
     * 
     * @return The next arrival time.
     */
    public double nextArrivalExp() {
        double result = -interArrivalMean * Math.log(nextRand());
        return result;
    }

    /**
     * Generates the next departure event time which is exponentially distributed
     * @return The next departure time.
     */
    public double nextDepartureExp() {
        double result = -serviceMean * Math.log(nextRand());
        return result;
    }
}
