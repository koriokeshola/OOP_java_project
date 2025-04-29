# OOP_java_project
Supervised machine learning classifier based on the Naïve Bayes classifier, coded in Java.

Control.java
Main entry point for running the GUI, allowing user input and adding sample data to the frequency table.

FrequencyTable.java
Stores the frequency table for training the clasifier. It tracks the occurences for feature combinations and maps them to their corresponding yes or no label. This is used for calculating the probability of each outcome.

NaiveBayesClassifier.java
Implements the Naive Bayes model, the core basis of the project. It is used to train the classifier, calculate probabilities using the frequency table, and and henceforth makes predictions. It includes methods for training the model and predicting the outcomes.

PredictionGUI.java
The GUI (Graphical User Interface) for accepting user input to test permutations and predict their outcome. Serves as an interface for displaying prediction results.

ReadFile.java
Handles the dataset file, containing 200 records. It parses each line using the Record class to create objects of type Record.

Record.java
Represents a single record from the dataset. It stores the feature values and corresponding label. Serves as the structure for the data used in the classifier.
