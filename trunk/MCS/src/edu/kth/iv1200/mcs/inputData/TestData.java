/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kth.iv1200.mcs.inputData;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author cuneyt
 */
public class TestData {

    private ArrayList<Double> data;
    private int[] freq;
    private double average;
    private double max;
    private int k;
    private double stepSize;
    private int N;

    public TestData(ArrayList<Double> data, int[] freq, double average, double max, int k, double stepSize, int N) {
        this.data = data;
        this.freq = freq;
        this.average = average;
        this.max = max;
        this.k = k;
        this.stepSize = stepSize;
        this.N = N;
    }

    @Override
    public String toString() {

        String newLine = System.getProperty("line.separator");
        DecimalFormat df = new DecimalFormat("##");
        DecimalFormat ddf = new DecimalFormat(".####");
        StringBuilder sb = new StringBuilder();
        sb.append("Sample size:");
        sb.append(N);
        sb.append(newLine);
        sb.append("Number of Intervals:");
        sb.append(k);
        sb.append(newLine);
        sb.append("Step size:");
        sb.append(ddf.format(stepSize));
        sb.append(newLine);
        sb.append("Average value:");
        sb.append(ddf.format(average));
        sb.append(newLine);
        sb.append("Maximum value:");
        sb.append(ddf.format(max));
        sb.append(newLine);
        sb.append("Expected frequency:");
        sb.append(ddf.format((double) N / k));
        sb.append(newLine);
        sb.append("Observed frequencies");
        sb.append(newLine);
        sb.append("-------------------------------------------------------------");
        sb.append(newLine);
        for (int i = 0; i < freq.length; i++) {
            sb.append(df.format(freq[i]));
            sb.append(":");
            for (int j = 0; j < freq[i]; j++) {
                sb.append("*");
            }
            sb.append(newLine);
        }


        return sb.toString();
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public ArrayList<Double> getData() {
        return data;
    }

    public void setData(ArrayList<Double> data) {
        this.data = data;
    }

    public int[] getFreq() {
        return freq;
    }

    public void setFreq(int[] freq) {
        this.freq = freq;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public double getStepSize() {
        return stepSize;
    }

    public void setStepSize(double stepSize) {
        this.stepSize = stepSize;
    }

    public int getN() {
        return N;
    }

    public void setN(int N) {
        this.N = N;
    }
}
