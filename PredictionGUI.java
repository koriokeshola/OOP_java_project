/* PredictionGUI: This is the Graphical User Interface for Assignment.
 * Author: Kori Okeshola */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PredictionGUI extends JFrame
{
    // Declare classifier instance so functions can be called
    private NaiveBayesClassifier classifier;

    // Read dataset file
    File myFile = new File("road_blocked_dataset.csv");

    // Features of dataset
    private String[] AccidentReported = {"Yes", "No"};
    private String[] WeatherConditions = {"Clear", "Stormy"};
    private String[] TrafficLevel = {"High", "Low"};
    private String[] RoadConstruction = {"Yes", "No"};

    // Drop-down feature selectors
    private JComboBox<String> accident;
    private JComboBox<String> weather;
    private JComboBox<String> traffic;
    private JComboBox<String> construction;

    // Label for result calculated
    private JLabel resultLabel;

    // Constructor
    public PredictionGUI(String title)
    {
        // Inherit from JFrame
        super(title);

        // Initialise classifier instance
        classifier = new NaiveBayesClassifier();

        // Use BorderLayout for layout
        setLayout(new BorderLayout(10, 10));

        // Set the default close operation and frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Safely close screen upon exit
        setSize(500, 500);

        // Panels for input form
        JPanel featurePanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new java.awt.GridLayout(4, 2, 10, 10));

        // Add feature labels and input box to grid
        inputPanel.add(new JLabel("Accident Reported:"));
        accident = new JComboBox<>(AccidentReported);
        inputPanel.add(accident);

        inputPanel.add(new JLabel("Weather Conditions:"));
        weather = new JComboBox<>(WeatherConditions);
        inputPanel.add(weather);

        inputPanel.add(new JLabel("Traffic Level:"));
        traffic = new JComboBox<>(TrafficLevel);
        inputPanel.add(traffic);

        inputPanel.add(new JLabel("Road Construction:"));
        construction = new JComboBox<>(RoadConstruction);
        inputPanel.add(construction);

        // Add input grid to form panel
        formPanel.add(inputPanel, BorderLayout.CENTER);
        // Add form to feature panel
        featurePanel.add(formPanel, BorderLayout.CENTER);

        // Buttons for predicting and training model
        JPanel buttonPanel = new JPanel();
        JButton predictButton = new JButton("Predict");
        JButton trainButton = new JButton("Train Model");

        // Action listener for when predict button is pressed
        predictButton.addActionListener(e -> {
            try {
                // Get selected values from the dropdowns
                String accidentValue = accident.getSelectedItem().toString();
                String weatherValue = weather.getSelectedItem().toString();
                String trafficValue = traffic.getSelectedItem().toString();
                String constructionValue = construction.getSelectedItem().toString();
        
                // Call the predict method from NaiveBayesClassifier
                String result = classifier.predict(accidentValue, weatherValue, trafficValue, constructionValue);
                resultLabel.setText("Prediction: " + result); // Display the prediction result
            }
            catch (IllegalStateException ex)
            {
                resultLabel.setText("Model is not yet trained.");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                resultLabel.setText("Error occurred during prediction.");
            }
        });

        // Action listener for when train button is pressed
        trainButton.addActionListener(e -> {
            try {
                // Call the trainModel method from NaiveBayesClassifier
                classifier.trainModel();
                resultLabel.setText("Model trained successfully!");
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        });

        // Add buttons to panel
        buttonPanel.add(trainButton);
        buttonPanel.add(predictButton);

        // Add result label for prediction and set its size
        resultLabel = new JLabel("Prediction: ");
        resultLabel.setPreferredSize(new Dimension(400, 30));

        // Add panels to frame
        add(resultLabel, BorderLayout.NORTH);
        add(featurePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true); // Frame will only display if this is set

    } // End of constructor
}
