/* Screen: GUI for Assignment.
 * Author: Kori Okeshola
 * Date: 10th April 2025 */

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

 
public class Screen extends JFrame
{
    // Import file
    File myFile = new File("road_blocked_dataset.csv");

    // Features of dataset
    private String[] AccidentReported = {"Yes", "No"};
    private String[] WeatherConditions = {"Clear", "Stormy"};
    private String[] TrafficLevel = {"High", "Low"};
    private String[] RoadConstruction = {"Yes", "No"};

    // Feature selectors
    private JComboBox<String> feature1;
    private JComboBox<String> feature2;
    private JComboBox<String> feature3;
    private JComboBox<String> feature4;

    // Label for result calculated
    private JLabel resultLabel;

    // Hashmap for storing prediction data
    private static Map<String, Map<String, Integer>> frequencyTable = new HashMap<>();
    
    // Run predictions for 3 random permutations
    public static void main(String[] args)
    {
        // Sample data for 3 permutations
        Map<String, Integer> count_1 = new HashMap<>();
        count_1.put("yes", 1);
        count_1.put("no", 1);
        frequencyTable.put("Yes,Stormy,High,No", count_1);

        Map<String, Integer> count_2 = new HashMap<>();
        count_2.put("yes", 4);
        count_2.put("no", 1);
        frequencyTable.put("Yes,Stormy,Low,Yes", count_2);

        Map<String, Integer> count_3 = new HashMap<>();
        count_3.put("yes", 2);
        count_3.put("no", 3);
        frequencyTable.put("No,Clear,Low,No", count_3);

        // Safely run and display GUI
        SwingUtilities.invokeLater(() -> new Screen("Prediction Functionality"));
        
    }

    // Constructor
    public Screen(String title)
    {
        // Inherit from JFrame
        super(title);

        // Set width, height and layout
        setSize(500,500);
        setLayout(new GridLayout(7, 2, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Safely close screen upon exit

        // Create ComboBoxes i.e. drop-down menus for selected option e.g. yes/no
        feature1 = new JComboBox<>(AccidentReported);
        feature2 = new JComboBox<>(WeatherConditions);
        feature3 = new JComboBox<>(TrafficLevel);
        feature4 = new JComboBox<>(RoadConstruction);

        resultLabel = new JLabel("Prediction: "); // Label displaying result of prediction
        JButton predictButton = new JButton("Predict"); // Button for running prediction
        JButton trainButton = new JButton("Train Model"); // Button which uses predictions to train the model

        // Action listeners, informing button to run the function in question when clicked
        predictButton.addActionListener(e -> predict());
        trainButton.addActionListener(e -> {
            try
            {
                trainModel();
            }
            catch (IOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        // Add buttons to frame
        add(predictButton);
        add(trainButton);

        // Add feature labels to frame
        add(new JLabel("\nFeature 1 (Accident/No Accident):"));
        add(feature1);
        add(new JLabel("\nFeature 2 (Clear/Stormy):"));
        add(feature2);
        add(new JLabel("\nFeature 3 (High Traffic/Low Traffic):"));
        add(feature3);
        add(new JLabel("\nFeature 4 (Construction/No Construction):"));
        add(feature4);
         
        // Add result label to frame
        add(resultLabel);

        // Screen will only appear if this is set
        setVisible(true);

    } // End of constructor

    // Method for training Naive Bayes model
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

                // While loop for reading a line until there are no more to be read
                while((line = br.readLine()) != null)
                {
                    // Comma-separate each line into the respective columns
                    String[] valueEntry = line.split(",");

                    // Error checking method for value entry array, for skipping any lines which are missing values or not separated correctly
                    if(valueEntry.length != 5)
                    {
                        continue;
                    }

                    // Join the first 4 values (features) into a key
                    String key = valueEntry[0] + "," + valueEntry[1] + "," + valueEntry[2] + "," + valueEntry[3];

                    // Identify 5th entry as the label
                    String label = valueEntry[4].trim();

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
            }

            // Print ending statement upon completion & full table
            System.out.println("Model training completed!");
            System.out.println(frequencyTable);
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace(); // Print error if file cannot be found/opened successfully
        }

    } // End of trainModel()

    // Method for prediction logic
    private void predict()
    {
        // Combine features into a key string
        String key = feature1.getSelectedItem() + "," +
                    feature2.getSelectedItem() + "," +
                    feature3.getSelectedItem() + "," +
                    feature4.getSelectedItem();

        // Check if the current combination exists in the table
        if (frequencyTable.containsKey(key))
        {
            // Count all "yes" and "no" instances
            Map<String, Integer> count = frequencyTable.get(key);

            int yes = count.get("yes");
            int no = count.get("no");
            int total = yes + no;

            // Calculate probability of the road being blocked
            double probabilityOfYes = (double) yes / total * 100;

            // Calculate probability of the road not being blocked
            double probabilityOfNo = (double) no / total * 100;

            // Display yes result in string format
            resultLabel.setText(String.format("Prediction: %.1f%% chance of road being blocked", probabilityOfYes));

            // Display no result in string format
            resultLabel.setText(String.format("Prediction: %.1f%% chance of road NOT being blocked", probabilityOfNo));
        }
        else
        {
            resultLabel.setText("Permutation not found");
        }

    } // End of predict()
    
} // End of class
