package common.core.Utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TextFileReadWrite {
    private String ForwardOrderId;
    private String DealerId;
    private String Group;
    private int Quantity;
    private int field5;

    public TextFileReadWrite(String ForwardOrderId, String DealerId, String Group, int Quantity, int field5) {
        this.ForwardOrderId = ForwardOrderId;
        this.DealerId = DealerId;
        this.Group = Group;
        this.Quantity = Quantity;
        this.field5 = field5;
    }

    public TextFileReadWrite() {

    }
    public String getForwardOrderId() {
        return ForwardOrderId;
    }

    public String getDealerId() {
        return DealerId;
    }

    public String getGroup() {
        return Group;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getField5() {
        return field5;
    }
    static List<TextFileReadWrite> testDataList;

    // Add getters if needed

    // @Override
    public String toString() {
        return "TextFileReadWrite{" +
                "ForwardOrderId='" + ForwardOrderId + '\'' +
                ", DealerId='" + DealerId + '\'' +
                ", Group='" + Group + '\'' +
                ", Quantity=" + Quantity +
                ", field5=" + field5 +
                '}';
    }


    public Map<String, Integer> FileReadAndWrite(String filePath) {

        testDataList = new ArrayList<>();
        Map<String, Integer> aggregatedData = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                TextFileReadWrite testData = new TextFileReadWrite(fields[0], fields[1], fields[2], Integer.parseInt(fields[3]), Integer.parseInt(fields[4]));
                testDataList.add(testData);

                // Aggregate data based on matching field3
                aggregatedData.merge(fields[2], Integer.parseInt(fields[3]), Integer::sum);
            }
            } catch (IOException | NumberFormatException e){}

        return aggregatedData;
    }

    public Map<String, Integer> getGroupNameWithQuantityForDealer(String filePath, String dealerID)
    {
        testDataList = new ArrayList<>();
        Map<String, Map<String, Integer>> aggregatedData = new HashMap<>();
        Set<String> uniqueDealerId = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                TextFileReadWrite testData = new TextFileReadWrite(fields[0], fields[1], fields[2], Integer.parseInt(fields[3]), Integer.parseInt(fields[4]));
                testDataList.add(testData);

                // Aggregate data based on matching DealerId, field3, and field4
                aggregatedData
                        .computeIfAbsent(testData.getDealerId(), k -> new HashMap<>())
                        .merge(testData.getGroup(), testData.getField5(), Integer::sum);

                // Add unique values of DealerId
                uniqueDealerId.add(testData.getDealerId());
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        // Search for a specific DealerId
        Map<String, Integer> groupWithQuntity = searchDealerId(dealerID, aggregatedData);
        return groupWithQuntity;
    }

    // Method to search for a specific DealerId
    private static Map<String, Integer> searchDealerId(String dealerId, Map<String, Map<String, Integer>> aggregatedData) {
        Map<String, Integer> innerMap = new HashMap<>();;
        if (aggregatedData.containsKey(dealerId)) {
            System.out.println("\nSearch Result for DealerId: " + dealerId);
            innerMap = aggregatedData.get(dealerId);
            for (Map.Entry<String, Integer> entry : innerMap.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();
                System.out.println("Key: " + key + ", Value: " + value);
            }
        } else {
            System.out.println("\nDealerId " + dealerId + " not found in the data.");
        }
        return innerMap;
    }
}
