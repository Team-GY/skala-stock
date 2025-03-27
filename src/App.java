import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) {
        SkalaStockMarket skalaStockMarket = new SkalaStockMarket();
        skalaStockMarket.start();

        StockRepository stockRepository = new StockRepository();
        stockRepository.startStockPriceFluctuation();
    }
}
