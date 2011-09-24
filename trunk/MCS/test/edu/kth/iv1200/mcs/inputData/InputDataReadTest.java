/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kth.iv1200.mcs.inputData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author cuneyt
 */
public class InputDataReadTest {

    private InputDataModeller idm;
    private TestData[] results;
    private String[] dataSources = {"data/arrival_time_g10.txt",
        "data/duration_g10.txt",
        "data/position_g10.txt",
        "data/speed_g10.txt"};

    public InputDataReadTest() {
        readInputDataFiles();
    }

    private void readInputDataFiles() {
        results = new TestData[dataSources.length];
        FileWriter fw = null;
        try {
            fw = new FileWriter("data/output.txt", false);
            for (int i = 0; i < results.length; i++) {
                String file = dataSources[i];
                idm = new InputDataModeller(file);
                results[i] = idm.calculateHistogram(fw);
            }
        } catch (IOException ex) {
            Logger.getLogger(InputDataReadTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(InputDataReadTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Test
    public void uniformDistrChiSquareTest() {
        FileWriter fw = null;
        try {
            fw = new FileWriter("data/uniformOutput.txt", false);
            String newLine = System.getProperty("line.separator");

            for (int i = 0; i < results.length; i++) {

                int k = 8;
                int N = results[i].getN();
                double e = N / k;
                int[] frequency = new int[k];
                double acc = 0, sqr = 0;
                double oacc = 0, eacc = 0;
                double b = results[i].getMax() * (results[i].getN() + 1) / results[i].getN();
                double F[] = new double[k + 1];
                double equProb = Math.pow(k, -1);
                for (int j = 0; j < F.length; j++) {
                    F[j] = j * equProb;
                }
                double a[] = new double[F.length];
                for (int j = 0; j < a.length; j++) {
                    a[j] = b * F[j];
                }
                for (Double d : results[i].getData()) {

                    for (int j = 1; j < a.length; j++) {
                        if ((d >= a[j - 1]) && (d < a[j])) {
                            frequency[j - 1]++;
                            break;
                        }
                    }

                }
                fw.write(dataSources[i] + newLine);
                fw.write("Obs\tExp\tX^2" + newLine);
                for (int j = 0; j < k; j++) {
                    oacc += frequency[j];
                    eacc += e;
                    sqr = Math.pow(frequency[j] - e, 2) / e;
                    acc += sqr;
                    fw.write(frequency[j] + "\t" + e + "\t" + sqr + newLine);
                }
                fw.write("----------------------------------------------------" + newLine);
                fw.write(oacc + "\t" + eacc + "\t" + acc + newLine);
                fw.write("X^2(" + (k - 2) + ", 0.005) = "
                        + acc + ((acc > 12.6) ? " > 12.6, rejected" : " < 12.6, not rejected") + newLine);
                fw.write("----------------------------------------------------" + newLine);
            }
        } catch (IOException ex) {
            Logger.getLogger(InputDataReadTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(InputDataReadTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Test
    public void expDistrChiSquareTest() {
        FileWriter fw = null;
        try {
            String newLine = System.getProperty("line.separator");
            fw = new FileWriter("data/expOutput.txt", false);
            for (int i = 0; i < results.length; i++) {

                double lambda = Math.pow(results[i].getAverage(), -1);
                double acc = 0, sqr = 0, oacc = 0, eacc = 0;
                int k = 8;
                double e = results[i].getN() / k;
                int[] freq = new int[k];
                double F[] = new double[k + 1];
                double equProb = Math.pow(k, -1);
                for (int j = 0; j < F.length; j++) {
                    F[j] = j * equProb;
                }
                double a[] = new double[F.length];
                for (int j = 0; j < a.length; j++) {
                    a[j] = -1 * (Math.log(1 - F[j]) / lambda);
                }

                for (Double d : results[i].getData()) {
                    for (int j = 1; j < a.length; j++) {
                        if ((d >= a[j - 1]) && (d < a[j])) {
                            freq[j - 1]++;
                            break;
                        }
                    }
                }
                fw.write(dataSources[i] + newLine);
                fw.write("Obs\tExp\tX^2" + newLine);
                for (int j = 0; j < freq.length; j++) {
                    oacc += freq[j];
                    eacc += e;
                    sqr = Math.pow(freq[j] - e, 2) / e;
                    acc += sqr;
                    fw.write(freq[j] + "\t" + e + "\t" + sqr + newLine);
                }
                fw.write("----------------------------------------------------" + newLine);
                fw.write(oacc + "\t" + eacc + "\t" + acc + newLine);
                fw.write("X^2(6, 0.005) = "
                        + acc + ((acc > 12.6) ? " > 12.6, rejected" : " < 12.6, not rejected") + newLine);
                fw.write("----------------------------------------------------" + newLine);

            }
        } catch (IOException ex) {
            Logger.getLogger(InputDataReadTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(InputDataReadTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
