import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

class StockRepository {
    private final Random random = new Random();
    private Timer timer; // 타이머 객체를 필드로 관리

    // 주식 정보를 저장할 파일 (형식 - "주식명,주가")
    private final String STOCK_FILE = "data/stocks.txt";

    // 주식 정보 목록 (메모리)
    private ArrayList<Stock> stockList = new ArrayList<>();

    // 주식 가격 변동 시작
    public void startStockPriceFluctuation() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (stockList) {
                    for (Stock stock : stockList) {
                        int fluctuation = random.nextInt(21) - 10; // -10 ~ +10
                        int newPrice = Math.max(1, stock.getStockPrice() + fluctuation);
                        stock.setStockPrice(newPrice);
                    }
                    saveStockList(); // 파일에 저장
                }
            }
        }, 0, 5000); // 5초마다 변동 (테스트용으로 충분히 길게 설정)
    }

    // 타이머 종료 메서드 추가
    public void stopStockPriceFluctuation() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            System.out.println("주식 가격 변동 타이머 종료.");
        }
    }

    public void loadStockList() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STOCK_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Stock stock = parseLineToStock(line);
                if (stock != null) {
                    stockList.add(stock);
                }
            }
            System.out.println("주식 목록 로드 완료: " + stockList.size() + "개");
        } catch (IOException e) {
            System.out.println("파일이 없거나 불러오는 중 오류 발생. 초기화 진행.");
            stockList.add(new Stock("TechCorp", 100));
            stockList.add(new Stock("GreenEnergy", 80));
            stockList.add(new Stock("HealthPlus", 120));
        }
    }

    public void saveStockList() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STOCK_FILE))) {
            for (Stock stock : stockList) {
                writer.write(stock.getStockName() + "," + stock.getStockPrice());
                writer.newLine();
            }
            //System.out.println("stocks.txt에 저장 완료.");
        } catch (IOException e) {
            System.out.println("파일 저장 실패: " + e.getMessage());
        }
    }

    public void saveStockHistory() {

    }

    // 파일 라인을 Stock 객체로 변환
    private Stock parseLineToStock(String line) {
        String fileds[] = line.split(",");
        if (fileds.length > 1) {
            return new Stock(fileds[0], Integer.parseInt(fileds[1]));
        } else {
            System.out.println("파일 라인을 분석할 수 없습니다. line=" + line);
            return null;
        }
    }

    public ArrayList<Stock> getStockList() {
        return stockList;
    }

    public String getStockListForMenu() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stockList.size(); i++) {
            sb.append(i + 1);
            sb.append(". ");
            sb.append(stockList.get(i).toString());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    // 오버로딩
    public Stock findStock(int index) {
        if (index >= 0 && index < stockList.size()) {
            return stockList.get(index);
        }
        return null;
    }

    // 오버로딩
    public Stock findStock(String name) {
        for (Stock stock : stockList) {
            if (stock.getStockName().equals(name)) {
                return stock;
            }
        }
        return null;
    }

}
