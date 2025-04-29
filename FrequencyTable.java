import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class FrequencyTable
{
    private Map<String, Integer> classCount;
    private Map<String, Map<String, Map<String, Integer>>> featureCount;
    private int numInstances;
    private List<String> featureNames;
    private String labelName;

    // Constructor
    public FrequencyTable(List<String> featureNames, String labelName)
    {
        this.featureNames = featureNames;
        this.labelName = labelName;
        this.classCount = new HashMap<>();
        this.featureCount = new HashMap<>();
        this.numInstances = 0;
    }

    public void updateTable(Record record)
    {
        // Get yes or no label
        String label = record.getLabel();
        
        // Increment count for label
        classCount.put(label, classCount.getOrDefault(label, 0) + 1);

        String[] features = {"Accident", "Weather", "Traffic", "Construction"};
        String[] values =
        {
            record.getAccident(),
            record.getWeather(),
            record.getTraffic(),
            record.getConstruction()
        };

        // Create feature map if it doesn't exist yet
        if (!featureCount.containsKey(label))
        {
            featureCount.put(label, new HashMap<>());
        }

        for (int i = 0; i < features.length; i++)
        {
            String feature = features[i];
            String value = values[i];

            // Ensure feature map exists for the current feature
            if (!featureCount.get(label).containsKey(feature))
            {
                featureCount.get(label).put(feature, new HashMap<>());
            }

            Map<String, Integer> valueMap = featureCount.get(label).get(feature);
            valueMap.put(value, valueMap.getOrDefault(value, 0) + 1);
        }

        numInstances++;
    }   
}
