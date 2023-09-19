

package pay.point.sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SalesPredictor {

    public static void main(String[] args) throws IOException {
        // Load the sales data file
        List<Double> salesData = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("sales_data.csv"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            double sales = Double.parseDouble(values[1]);
            salesData.add(sales);
        }

        // Prompt the user for the month to predict sales for
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the month to predict sales for (1-12): ");
        int month = scanner.nextInt();

        // Create the input and output arrays for the linear regression model
        double[] input = new double[salesData.size() - 1];
        double[] output = new double[salesData.size() - 1];
        for (int i = 0; i < salesData.size() - 1; i++) {
            input[i] = i + 1;
            output[i] = salesData.get(i + 1);
        }

        // Train the linear regression model on the sales data
        LinearRegressionModel model = new LinearRegressionModel(input, output);

        // Predict sales for the specified month
        double predictedSales = model.predict(month);

        // Print the predicted sales to the console
        System.out.println("The predicted sales for month " + month + " is " + predictedSales);
    }

    static class LinearRegressionModel {
        private double[] input;
        private double[] output;
        private double intercept;
        private double slope;

        public LinearRegressionModel(double[] input, double[] output) {
            this.input = input;
            this.output = output;
            double sumX = 0;
            double sumY = 0;
            double sumXY = 0;
            double sumX2 = 0;
            for (int i = 0; i < input.length; i++) {
                sumX += input[i];
                sumY += output[i];
                sumXY += input[i] * output[i];
                sumX2 += input[i] * input[i];
            }
            double n = input.length;
            double xBar = sumX / n;
            double yBar = sumY / n;
            double denominator = sumX2 - (sumX * sumX) / n;
            slope = (sumXY - (sumX * sumY) / n) / denominator;
            intercept = yBar - slope * xBar;
        }

        public double predict(double input) {
            return intercept + slope * input;
        }
    }
}