/* NaiveBayesClassifier: This file contains the main logic for the classifier.
 * Author: Kori Okeshola */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NaiveBayesClassifier
{
    // Check if model is trained before allowing prediction
    private static boolean isTrained = false;
    

    // Frequency table for storing prediction data
    static Map<String, Map<String, Integer>> frequencyTable = new HashMap<>();
    static Map<String, Integer> labelCount = new HashMap<>();
    static int totalRecords;

    public NaiveBayesClassifier()
    {
        NaiveBayesClassifier.frequencyTable = new HashMap<>();
        NaiveBayesClassifier.labelCount = new HashMap<>();
        NaiveBayesClassifier.totalRecords = 0;
    }

    // Predicts the label based on the given feature values.
    public static String predict(String accident, String weather, String traffic, String construction)
    {
        if(!isTrained)
        {
            throw new IllegalStateException("Model is not yet trained.");
        }

        // Efficient way of comma-delimiting data
        String key = String.join(",", accident, weather, traffic, construction);

        // Get the number of occurrences for each label
        if (frequencyTable.containsKey(key))
        {
            Map<String, Integer> labelCount = frequencyTable.get(key);
            String bestLabel = "";
            double maxProbability = -1;

            // Calculate the probability for each label
            for (String label : labelCount.keySet())
            {
                double probability = calculateProbability(key, label);
                if (probability > maxProbability)
                {
                    maxProbability = probability;
                    bestLabel = label;
                }
            }
            return bestLabel;
            }
        }

        // If combination isn't found in the frequency table
        return "Combination not found.";
    }

    public Map<String, Map<String, Integer>> getFrequencyTable()
    {
        return frequencyTable;
    }

    public void trainModel() throws IOException
    {
        // Clear any previous data to prevent overlapping data
        frequencyTable.clear();
        labelCount.clear();
        totalRecords = 0;
        
        try (Scanner scanner = new Scanner(csvFile))
        {
            // Skip header
            if (scanner.hasNextLine())
            {
                scanner.nextLine();
            }

            // Read each line
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                // Split each line into comma-separated columns
                String[] valueEntry = line.split(",");

                Record record = new Record(
                    valueEntry[0],
                    valueEntry[1],
                    valueEntry[2],
                    valueEntry[3],
                    valueEntry[4]               
                );

                // Extract feature values (first 4) and label (5th value)
                String key = record.getKey();
                // Split each line into comma-separated columns
                String label = record.getLabel();

                // Creates a new entry for each unique permutation, if it does not yet exist
                if(!frequencyTable.containsKey(key))
                {
                    frequencyTable.put(key, new HashMap<>());
                }

                // Use map to check how many times this combination has had yes or no so far
                // String = yes/no label, Integer = frequency
                Map<String, Integer> labelCount = frequencyTable.get(key);
                
                // Increment count for current label
                labelCount.put(label, labelCount.getOrDefault(label, 0) + 1);
            }

            isTrained = true;

            // Print ending statement upon completion & full table
            System.out.println("Model training completed!");
            System.out.println(frequencyTable);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("File not found: " + e.getMessage());
            throw e;
        }
        catch (IOException e)
        {
            System.err.println("Error reading file: " + e.getMessage());
            throw e;
        }

         // Calculate probability using Naive Bayes formula
        private static double calculateProbability(String key, String label)
        {
            int labelFrequency = labelCount.get(label);
            double priorProbability = (double) labelFrequency / totalRecords;
        
            Map<String, Integer> featureCount = frequencyTable.get(key);
            int featureFrequency = featureCount.get(label);
        
            // Calculate probability of features given label
            double likelihood = (double) featureFrequency / labelFrequency;
        
            // Return the product of prior and likelihood
            return priorProbability * likelihood;
        }
    }
}
