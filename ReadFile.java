/* ReadFile: Load dataset file.
 * Author: Kori Okeshola */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFile
{
    // Loads data from dataset into a list of objects of type Record.
    public static List<Record> readFile(String fileName) throws IOException
    {
        // List of records for parsing
        List<Record> records = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(fileName)))
        {
            // Skip header
            if (scanner.hasNextLine())
            {
                scanner.nextLine();
            }

            // Comma separate the values while loading
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                
                records.add(new Record(
                    values[0],
                    values[1],
                    values[2],
                    values[3],
                    values[4]
                ));
            }
          
            return records;
        }
    }
}
