/* Record: This file contains the getters for the dataset's features and label.
 * It represents a single row of the dataset, mapping each feature to its corresponding value.
 * Author: Kori Okeshola */

public class Record
{
    private String accident;
    private String weather;
    private String traffic;
    private String construction;
    private String label;

    // Constructor
    public Record(String accident, String weather, String traffic, String construction, String label)
    {
        this.accident = accident;
        this.weather = weather;
        this.traffic = traffic;
        this.construction = construction;
        this.label = label;
    }

    // Getters
    public String getAccident()
    {
        return accident;
    }

    public String getWeather()
    {
        return weather;
    }

    public String getTraffic()
    {
        return traffic;
    }

    public String getConstruction()
    {
        return construction;
    }

    public String getLabel()
    {
        return label;
    }

    public String getKey()
    {
        return String.join(",", accident, weather, traffic, construction);
    }
}
