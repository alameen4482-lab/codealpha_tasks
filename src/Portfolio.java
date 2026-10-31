import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the user's trading portfolio.
 * Encapsulates cash balance, stock holdings, transaction history,
 * and calculations for market value, total invested capital, and profit/loss.
 * 
 * @author CodeAlpha Intern
 */
public class Portfolio {

    /**
     * Inner class representing a single stock position/holding in the portfolio.
     */
    public static class Holding {
        private String symbol;
        private String companyName;
        private int quantity;
        private double totalInvested;

        public Holding(String symbol, String companyName, int quantity, double pricePerShare) {
            this.symbol = symbol.toUpperCase().trim();
            this.companyName = companyName;
            this.quantity = quantity;
            this.totalInvested = quantity * pricePerShare;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getCompanyName() {
            return companyName;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getTotalInvested() {
            return totalInvested;
        }

        public double getAveragePrice() {
            if (quantity <= 0) return 0.0;
            return totalInvested / quantity;
        }

        public void addShares(int additionalShares, double pricePerShare) {
            this.quantity += additionalShares;
            this.totalInvested += (additionalShares * pricePerShare);
        }

        public void removeShares(int sharesToSell) {
            if (sharesToSell >= this.quantity) {
                this.quantity = 0;
                this.totalInvested = 0.0;
            } else {
                double avgPrice = getAveragePrice();
                this.quantity -= sharesToSell;
                this.totalInvested -= (sharesToSell * avgPrice);
            }
        }
    }

    private double cashBalance;
    private double initialDeposit;
    private Map<String, Holding> holdings;
    private List<Transaction> transactionHistory;

    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("$#,##0.00");
    private static final DecimalFormat PERCENT_FORMAT = new DecimalFormat("+#,##0.00%;-#,##0.00%");

    /**
     * Constructs a new Portfolio with an initial cash deposit.
     *
     * @param initialCash Starting cash balance (e.g., $100,000.00)
     */
    public Portfolio(double initialCash) {
        this.cashBalance = initialCash;
        this.initialDeposit = initialCash;
        this.holdings = new LinkedHashMap<>();
        this.transactionHistory = new ArrayList<>();
    }

    // --- Cash Operations ---

    public double getCashBalance() {
        return cashBalance;
    }

    public double getInitialDeposit() {
        return initialDeposit;
    }

    public void depositCash(double amount) {
        if (amount > 0) {
            this.cashBalance += amount;
        }
    }

    // --- Trading Logic ---

    /**
     * Buys a specified quantity of shares for a given stock.
     *
     * @param stock    The Stock object to buy
     * @param quantity Number of shares
     * @return true if successful, false otherwise
     */
    public boolean buyStock(Stock stock, int quantity) {
        if (stock == null || quantity <= 0) {
            return false;
        }

        double totalCost = quantity * stock.getCurrentPrice();
        if (totalCost > cashBalance) {
            return false; // Insufficient cash
        }

        // Deduct cash
        cashBalance -= totalCost;

        // Update holdings
        String sym = stock.getSymbol();
        if (holdings.containsKey(sym)) {
            holdings.get(sym).addShares(quantity, stock.getCurrentPrice());
        } else {
            holdings.put(sym, new Holding(sym, stock.getCompanyName(), quantity, stock.getCurrentPrice()));
        }

        // Record transaction
        Transaction txn = new Transaction("BUY", sym, stock.getCompanyName(), quantity, stock.getCurrentPrice());
        transactionHistory.add(txn);

        return true;
    }

    /**
     * Sells a specified quantity of shares for a given stock.
     *
     * @param stock    The Stock object to sell
     * @param quantity Number of shares to sell
     * @return true if successful, false otherwise
     */
    public boolean sellStock(Stock stock, int quantity) {
        if (stock == null || quantity <= 0) {
            return false;
        }

        String sym = stock.getSymbol();
        if (!holdings.containsKey(sym)) {
            return false; // Stock not owned
        }

        Holding holding = holdings.get(sym);
        if (quantity > holding.getQuantity()) {
            return false; // Trying to sell more shares than owned
        }

        double revenue = quantity * stock.getCurrentPrice();
        cashBalance += revenue;

        holding.removeShares(quantity);
        if (holding.getQuantity() == 0) {
            holdings.remove(sym);
        }

        // Record transaction
        Transaction txn = new Transaction("SELL", sym, stock.getCompanyName(), quantity, stock.getCurrentPrice());
        transactionHistory.add(txn);

        return true;
    }

    // --- Holdings and Portfolio Value Accessors ---

    public Holding getHolding(String symbol) {
        return holdings.get(symbol.toUpperCase().trim());
    }

    public List<Holding> getHoldingsList() {
        return new ArrayList<>(holdings.values());
    }

    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    public int getSharesOwned(String symbol) {
        Holding h = holdings.get(symbol.toUpperCase().trim());
        return h != null ? h.getQuantity() : 0;
    }

    public boolean hasHoldings() {
        return !holdings.isEmpty();
    }

    /**
     * Calculates the current market value of all held stocks.
     *
     * @param marketStocks Map of available market stocks
     * @return Total current market value of open stock positions
     */
    public double calculateStockMarketValue(Map<String, Stock> marketStocks) {
        double value = 0.0;
        for (Holding h : holdings.values()) {
            Stock s = marketStocks.get(h.getSymbol());
            double currentPrice = (s != null) ? s.getCurrentPrice() : h.getAveragePrice();
            value += h.getQuantity() * currentPrice;
        }
        return value;
    }

    /**
     * Calculates total capital currently invested in active holdings.
     *
     * @return Total invested amount
     */
    public double calculateTotalInvested() {
        double total = 0.0;
        for (Holding h : holdings.values()) {
            total += h.getTotalInvested();
        }
        return total;
    }

    /**
     * Calculates total unrealized profit/loss across all active stock positions.
     *
     * @param marketStocks Map of available market stocks
     * @return Total profit or loss in dollars
     */
    public double calculateTotalUnrealizedProfitLoss(Map<String, Stock> marketStocks) {
        return calculateStockMarketValue(marketStocks) - calculateTotalInvested();
    }

    /**
     * Calculates total account net worth (cash + current stock value).
     *
     * @param marketStocks Map of available market stocks
     * @return Total account net worth
     */
    public double calculateTotalNetWorth(Map<String, Stock> marketStocks) {
        return cashBalance + calculateStockMarketValue(marketStocks);
    }
}
