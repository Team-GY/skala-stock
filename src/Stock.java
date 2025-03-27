public class Stock {
    String stockName;
    int stockPrice;
    PriceHistory priceHistory;

    public Stock() {
        this.priceHistory = new PriceHistory();
    }

    public Stock(String name, int price) {
        this.stockName = name;
        this.stockPrice = price;
        this.priceHistory = new PriceHistory();
        priceHistory.addPrice(price, this.stockName); // 초기 가격 기록
    }

    public String getStockName() {
        return this.stockName;
    }

    public int getStockPrice() {
        return this.stockPrice;
    }

    public void setStockPrice(int price) {
        this.stockPrice = price;
        priceHistory.addPrice(price, this.stockName); // 가격 변동 시 히스토리에 추가
    }

    // 히스토리 반환
    public String getPriceHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append(stockName).append(" 주가 변동 히스토리:\n");
        for (String entry : priceHistory.getHistory()) {
            sb.append(entry).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return stockName + ":" + stockPrice;
    }
}
