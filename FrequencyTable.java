/* FrequencyTable: Creating a frequency table, counting occurrences of the features &
 * corresponding label throughout dataset.
 * Author: Kori Okeshola */

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
        classCount = new HashMap<>();
        featureCount = new HashMap<>();
        numInstances = 0;
    }
}
