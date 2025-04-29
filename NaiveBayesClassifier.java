/* Screen: This file contains the main logic for the classifier.
 * Author: Kori Okeshola */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NaiveBayesClassifier
{
    // Check if model is trained before allowing prediction
    private static boolean isTrained = false;

    // Frequency table for storing prediction data
    static Map<String, Map<String, Integer>> frequencyTable = new HashMap<>();

    public NaiveBayesClassifier()
    {
        this.frequencyTable = new HashMap<>();
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
            Map<String, Integer> count = frequencyTable.get(key);
            int yes = count.get("Yes");
            int no = count.get("No");

            if (yes > no)
            {
                return "Yes";
            }
            else if(no > yes)
            {
                return "No";
            }
            else
            {
                return "Equal number of occurrences";
            }
        }

        // If combination isn't found in the frequency table
        return "Combination not found.";
    }

    public Map<String, Map<String, Integer>> getFrequencyTable()
    {
        return frequencyTable;
    }

    private void trainModel() throws IOException
    {
        // Clear any previous data to prevent overlapping data
        frequencyTable.clear();
        
        try
        {
            // Open dataset file to allow for reading of each line
            try (BufferedReader br = new BufferedReader(new FileReader("road_blocked_dataset.csv")))
            {
                String line;

                while ((line = br.readLine()) != null)
                {
                    // Trim whitespace and skip empty lines
                    line = line.trim();
                    if (line.isEmpty())
                    {
                        continue;
                    }
                }

                // Split each line into comma-separated columns
                String[] valueEntry = line.split(",");

                // Extract feature values (first 4) and label (5th value)
                String key = String.join(",", valueEntry[0].trim(), valueEntry[1].trim(), valueEntry[2].trim(), valueEntry[3].trim());
                String label = valueEntry[4].trim().toLowerCase();  // Convert to lowercase for consistency

                // Creates a new entry for each unique permutation, if it does not yet exist
                if(!frequencyTable.containsKey(key))
                {
                    frequencyTable.put(key, new HashMap<>());
                    frequencyTable.get(key).put("yes", 0);
                    frequencyTable.get(key).put("no", 0);
                }

                // Use map to check how many times this combination has had yes or no so far
                // String = yes/no label, Integer = frequency
                Map<String, Integer> labelCount = frequencyTable.get(key);
                
                // Increment count for current label
                labelCount.put(label, labelCount.get(label) + 1);
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
    }
}
