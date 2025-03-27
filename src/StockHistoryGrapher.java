import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class StockHistoryGrapher {

    public static void printGraphForStock(String targetStockName) {
        List<Integer> prices = new ArrayList<>();
        List<String> timestamps = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("stockHistory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 예: 삼성전자,2025-03-26T16:49:44.764243100,90
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String stockName = parts[0].trim();
                    String timestamp = parts[1].trim();
                    int price = Integer.parseInt(parts[2].trim());

                    if (stockName.equalsIgnoreCase(targetStockName)) {
                        timestamps.add(timestamp);
                        prices.add(price);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("파일 읽기 오류: " + e.getMessage());
            return;
        }

        if (prices.isEmpty()) {
            System.out.println("해당 종목의 주가 데이터가 없습니다.");
            return;
        }

        printAsciiGraph(prices, timestamps, targetStockName);
    }

    private static void printAsciiGraph(List<Integer> prices, List<String> timestamps, String stockName) {
        int height = 10;
        int width = prices.size();

        int max = Collections.max(prices);
        int min = Collections.min(prices);

        char[][] graph = new char[height][width];
        for (char[] row : graph) {
            Arrays.fill(row, ' ');
        }

        for (int i = 0; i < prices.size(); i++) {
            int price = prices.get(i);
            int y = (int) ((price - min) / (double)(max - min) * (height - 1));
            y = height - 1 - y;
            graph[y][i] = '*';
        }

        // 그래프 출력
        System.out.println("\n[" + stockName + "] 주가 변동 그래프");
        for (char[] row : graph) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }

        // X축 시간 (HH:MM)
        System.out.println("-".repeat(width));
        for (int i = 0; i < width; i++) {
            if (i % 2 == 0) {
                String time = timestamps.get(i).split("T")[1];
                System.out.print(time.substring(0, 5));
            } else {
                System.out.print("     ");
            }
        }
        System.out.println();
    }
}
