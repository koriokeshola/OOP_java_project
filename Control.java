/* Screen: Control for Assignment GUI.
 * Author: Kori Okeshola
 * Date: 10th April 2025 */

 public class Control
{
    public static void main(String[] args) throws Exception
    {
        // Add sample data to frequency table
        Map<String, Integer> count_1 = new HashMap<>();
        count_1.put("yes", 1);
        count_1.put("no", 1);
        NaiveBayesClassifier.frequencyTable.put("Yes,Stormy,High,No", count_1);

        Map<String, Integer> count_2 = new HashMap<>();
        count_2.put("yes", 4);
        count_2.put("no", 1);
        NaiveBayesClassifier.frequencyTable.put("Yes,Stormy,Low,Yes", count_2);

        Map<String, Integer> count_3 = new HashMap<>();
        count_3.put("yes", 2);
        count_3.put("no", 3);
        NaiveBayesClassifier.frequencyTable.put("No,Clear,Low,No", count_3);

        // Launch GUI
        SwingUtilities.invokeLater(() -> new PredictionGUI("Prediction GUI"));
    }
}
