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
    private TestData results;
    private String[] dataSources = {"data/arrival_time_g10.txt",
        "data/duration_g10.txt",
        "data/position_g10.txt",
        "data/speed_g10.txt"};

    public InputDataReadTest() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    private void readInputDataFiles(int i) {
        FileWriter fw = null;
        try {
            String file = dataSources[i];
            fw = new FileWriter("data/output.txt", true);
            idm = new InputDataModeller(file);
            results = idm.calculateHistogram(fw);
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
    public void uniformDsitrChiSquareTest() {
        for (int i = 0; i < dataSources.length; i++) {
            readInputDataFiles(i);
            int k = 8;
            int N = results.getN();
            double e = N / k;
            int[] frequency = new int[k];
            double acc = 0;
            double step = results.getMax() / k;

            for (Double d : results.getData()) {

                for (int j = 0; j < k; j++) {
                    if ((d >= j * step) && (d < (j + 1) * step)) {
                        frequency[j]++;
                        break;
                    }
                }

            }

            for (int j = 0; j < k; j++) {
                System.out.println(frequency[j]);
                acc += Math.pow(frequency[j] - e, 2) / e;
            }
            System.out.println("Uniform Distribution\nX^2(" + (k - 2) + ", 0.005) = "
                    + acc + ((acc > 12.6) ? " > 12.6, rejected" : " < 12.6, not rejected"));
        }
    }
}
