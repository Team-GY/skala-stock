import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PriceHistory {
    private List<String> priceHistory = new ArrayList<>();

    public void addPrice(int price, String name) {
        String timestamp = LocalDateTime.now().toString();
        String entry = name + "," + timestamp +","+ price;
        priceHistory.add(entry);
        saveToFile(entry);
    }

    private void saveToFile(String entry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("stockHistory.txt", true))) {
            writer.write(entry);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getHistory() {
        return priceHistory;
    }
}
