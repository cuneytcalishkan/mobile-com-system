/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kth.iv1200.mcs.inputData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cuneyt Caliskan
 */
public class InputDataModeller {

    private String dataSource;
    private BufferedReader br;
    private ArrayList<Double> data;

    public InputDataModeller(String dataSource) {
        try {
            this.dataSource = dataSource;
            this.br = new BufferedReader(new FileReader(dataSource));
            this.data = new ArrayList<Double>();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InputDataModeller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TestData calculateHistogram(Writer out) {
        try {
            int k;
            String newLine = System.getProperty("line.separator");
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split("\t");
                data.add(Double.parseDouble(columns[columns.length - 1]));
            }
            out.write("----------------------------------------------------------------" + newLine);
            out.write(dataSource + newLine);

            java.util.Collections.sort(data);
            k = (int) Math.floor(Math.sqrt(data.size()));

            double stepSize = (data.get(data.size() - 1) - data.get(0)) / k;
            stepSize += stepSize * 0.001;
            int[] freq = new int[k];
            double acc = 0;
            for (Double d : data) {
                for (int i = 1; i <= k; i++) {
                    if ((d >= ((stepSize * (i - 1)) + data.get(0))) && (d < (i * stepSize) + data.get(0))) {
                        freq[i - 1]++;
                        break;
                    }
                }
                acc += d;
            }
            TestData result = new TestData(data, freq, acc / data.size(), data.get(data.size() - 1), k, stepSize, data.size());
            out.write(result.toString());
            return result;
        } catch (IOException ex) {
            Logger.getLogger(InputDataModeller.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
