import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Main application controller for the Stock Trading Platform.
 * Manages the market feed, user interactions, menus, and business logic execution.
 * 
 * @author CodeAlpha Intern
 */
public class TradingPlatform {

    private Map<String, Stock> market;
    private Portfolio portfolio;
    private Scanner scanner;
    private Random random;

    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("$#,##0.00");
    private static final DecimalFormat PERCENT_FORMAT = new DecimalFormat("+#,##0.00%;-#,##0.00%");

    /**
     * Initializes the trading platform with simulated market stocks and a starting cash balance.
     */
    public TradingPlatform() {
        this.market = new LinkedHashMap<>();
        this.portfolio = new Portfolio(100000.00); // Demo balance: $100,000.00
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        initializeMarket();
    }

    /**
     * Initializes the predefined list of market stocks with realistic prices.
     */
    private void initializeMarket() {
        addMarketStock(new Stock("AAPL", "Apple Inc.", 189.50));
        addMarketStock(new Stock("MSFT", "Microsoft Corp.", 425.30));
        addMarketStock(new Stock("GOOGL", "Alphabet Inc.", 178.60));
        addMarketStock(new Stock("AMZN", "Amazon.com Inc.", 185.20));
        addMarketStock(new Stock("TSLA", "Tesla Inc.", 252.40));
        addMarketStock(new Stock("NVDA", "NVIDIA Corp.", 124.75));
        addMarketStock(new Stock("META", "Meta Platforms Inc.", 512.10));
        addMarketStock(new Stock("NFLX", "Netflix Inc.", 675.80));
    }

    private void addMarketStock(Stock stock) {
        market.put(stock.getSymbol(), stock);
    }

    /**
     * Starts the interactive command-line trading system.
     */
    public void start() {
        displayWelcomeBanner();

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = readIntInput("Enter your choice (1-8): ");
            System.out.println();

            switch (choice) {
                case 1:
                    displayMarketStocks();
                    break;
                case 2:
                    handleBuyStock();
                    break;
                case 3:
                    handleSellStock();
                    break;
                case 4:
                    displayPortfolio();
                    break;
                case 5:
                    displayAccountSummary();
                    break;
                case 6:
                    displayTransactionHistory();
                    break;
                case 7:
                    simulateMarketFluctuation();
                    break;
                case 8:
                    running = false;
                    displayExitMessage();
                    break;
                default:
                    System.out.println("[ERROR] Invalid choice. Please select an option between 1 and 8.");
            }
            System.out.println();
        }
    }

    // --- UI & Menus ---

    private void displayWelcomeBanner() {
        System.out.println("================================================================================");
        System.out.println("                   CODEALPHA STOCK TRADING PLATFORM (V1.0)                      ");
        System.out.println("================================================================================");
        System.out.println(" Welcome, Trader! Your simulated trading account is ready.");
        System.out.println(" Initial Demo Cash Balance: " + CURRENCY_FORMAT.format(portfolio.getCashBalance()));
        System.out.println("================================================================================");
        System.out.println();
    }

    private void displayMainMenu() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("                              MAIN TRADING MENU                                 ");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("  1. View Available Market Stocks");
        System.out.println("  2. Buy Stocks");
        System.out.println("  3. Sell Stocks");
        System.out.println("  4. View My Portfolio (Holdings & P/L)");
        System.out.println("  5. View Cash Balance & Account Summary");
        System.out.println("  6. View Transaction History");
        System.out.println("  7. Simulate Market Price Updates");
        System.out.println("  8. Exit Platform");
        System.out.println("--------------------------------------------------------------------------------");
    }

    /**
     * Displays all available market stocks in a clean tabular view.
     */
    public void displayMarketStocks() {
        System.out.println("================================================================================");
        System.out.println("                             LIVE MARKET DATA                                   ");
        System.out.println("================================================================================");
        System.out.printf("%-8s | %-24s | %14s | %14s%n", "SYMBOL", "COMPANY NAME", "CURRENT PRICE", "PRICE CHANGE");
        System.out.println("--------------------------------------------------------------------------------");

        for (Stock stock : market.values()) {
            double changePct = stock.getPriceChangePercentage();
            String changeStr = (changePct == 0.0) ? "  0.00%" : String.format("%+6.2f%%", changePct);
            System.out.printf("%-8s | %-24s | %14s | %14s%n",
                    stock.getSymbol(),
                    stock.getCompanyName(),
                    CURRENCY_FORMAT.format(stock.getCurrentPrice()),
                    changeStr);
        }
        System.out.println("================================================================================");
    }

    /**
     * Handles the stock purchase workflow with thorough validation.
     */
    public void handleBuyStock() {
        System.out.println("--- [ BUY STOCK ORDER ] ---");
        displayMarketStocks();
        System.out.println("Available Cash Balance: " + CURRENCY_FORMAT.format(portfolio.getCashBalance()));
        System.out.println();

        System.out.print("Enter Stock Symbol to BUY (or 0 to cancel): ");
        String symbol = scanner.nextLine().trim().toUpperCase();

        if (symbol.equals("0")) {
            System.out.println("[INFO] Buy order cancelled.");
            return;
        }

        Stock stock = market.get(symbol);
        if (stock == null) {
            System.out.println("[ERROR] Invalid stock symbol '" + symbol + "'. Stock not found in market.");
            return;
        }

        int quantity = readIntInput("Enter quantity of shares to buy: ");
        if (quantity <= 0) {
            System.out.println("[ERROR] Quantity must be greater than 0.");
            return;
        }

        double totalCost = quantity * stock.getCurrentPrice();
        if (totalCost > portfolio.getCashBalance()) {
            System.out.println("[ERROR] Insufficient cash balance!");
            System.out.println("  Required:  " + CURRENCY_FORMAT.format(totalCost));
            System.out.println("  Available: " + CURRENCY_FORMAT.format(portfolio.getCashBalance()));
            System.out.println("  Shortage:  " + CURRENCY_FORMAT.format(totalCost - portfolio.getCashBalance()));
            return;
        }

        boolean success = portfolio.buyStock(stock, quantity);
        if (success) {
            System.out.println();
            System.out.println("================================================================================");
            System.out.println("[SUCCESS] Buy Order Executed Successfully!");
            System.out.println("  Bought:    " + quantity + " shares of " + stock.getSymbol() + " (" + stock.getCompanyName() + ")");
            System.out.println("  Price:     " + CURRENCY_FORMAT.format(stock.getCurrentPrice()) + " per share");
            System.out.println("  Total:     " + CURRENCY_FORMAT.format(totalCost));
            System.out.println("  New Cash:  " + CURRENCY_FORMAT.format(portfolio.getCashBalance()));
            System.out.println("================================================================================");
        } else {
            System.out.println("[ERROR] Failed to execute buy order.");
        }
    }

    /**
     * Handles the stock selling workflow with validation.
     */
    public void handleSellStock() {
        System.out.println("--- [ SELL STOCK ORDER ] ---");
        if (!portfolio.hasHoldings()) {
            System.out.println("[INFO] You currently do not own any stocks in your portfolio.");
            return;
        }

        displayPortfolio();
        System.out.println();

        System.out.print("Enter Stock Symbol to SELL (or 0 to cancel): ");
        String symbol = scanner.nextLine().trim().toUpperCase();

        if (symbol.equals("0")) {
            System.out.println("[INFO] Sell order cancelled.");
            return;
        }

        Stock stock = market.get(symbol);
        if (stock == null) {
            System.out.println("[ERROR] Invalid stock symbol '" + symbol + "'.");
            return;
        }

        int ownedShares = portfolio.getSharesOwned(symbol);
        if (ownedShares <= 0) {
            System.out.println("[ERROR] You do not own any shares of " + symbol + ".");
            return;
        }

        System.out.println("You currently own " + ownedShares + " shares of " + symbol + ".");
        int quantity = readIntInput("Enter quantity of shares to sell: ");

        if (quantity <= 0) {
            System.out.println("[ERROR] Quantity must be greater than 0.");
            return;
        }

        if (quantity > ownedShares) {
            System.out.println("[ERROR] Insufficient shares! You own " + ownedShares + " shares but tried to sell " + quantity + " shares.");
            return;
        }

        double revenue = quantity * stock.getCurrentPrice();
        boolean success = portfolio.sellStock(stock, quantity);
        if (success) {
            System.out.println();
            System.out.println("================================================================================");
            System.out.println("[SUCCESS] Sell Order Executed Successfully!");
            System.out.println("  Sold:      " + quantity + " shares of " + stock.getSymbol() + " (" + stock.getCompanyName() + ")");
            System.out.println("  Price:     " + CURRENCY_FORMAT.format(stock.getCurrentPrice()) + " per share");
            System.out.println("  Revenue:   " + CURRENCY_FORMAT.format(revenue));
            System.out.println("  New Cash:  " + CURRENCY_FORMAT.format(portfolio.getCashBalance()));
            System.out.println("================================================================================");
        } else {
            System.out.println("[ERROR] Failed to execute sell order.");
        }
    }

    /**
     * Displays the complete portfolio holdings with purchase price, current price,
     * invested amount, current market value, and profit/loss breakdown.
     */
    public void displayPortfolio() {
        System.out.println("========================================================================================================");
        System.out.println("                                         YOUR PORTFOLIO                                                 ");
        System.out.println("========================================================================================================");

        List<Portfolio.Holding> holdings = portfolio.getHoldingsList();
        if (holdings.isEmpty()) {
            System.out.println(" Your portfolio is currently empty. Buy stocks from the market to start investing!");
            System.out.println(" Cash Balance: " + CURRENCY_FORMAT.format(portfolio.getCashBalance()));
            System.out.println("========================================================================================================");
            return;
        }

        System.out.printf("%-6s | %-18s | %6s | %10s | %10s | %12s | %12s | %14s%n",
                "SYMBOL", "COMPANY", "SHARES", "AVG COST", "PRICE", "INVESTED", "MKT VALUE", "PROFIT/LOSS");
        System.out.println("--------------------------------------------------------------------------------------------------------");

        for (Portfolio.Holding h : holdings) {
            Stock stock = market.get(h.getSymbol());
            double currentPrice = (stock != null) ? stock.getCurrentPrice() : h.getAveragePrice();
            double currentValue = h.getQuantity() * currentPrice;
            double invested = h.getTotalInvested();
            double pnl = currentValue - invested;
            double pnlPct = (invested > 0) ? (pnl / invested) : 0.0;

            String pnlFormatted = String.format("%s (%s)",
                    CURRENCY_FORMAT.format(pnl),
                    PERCENT_FORMAT.format(pnlPct));

            System.out.printf("%-6s | %-18s | %6d | %10s | %10s | %12s | %12s | %14s%n",
                    h.getSymbol(),
                    truncate(h.getCompanyName(), 18),
                    h.getQuantity(),
                    CURRENCY_FORMAT.format(h.getAveragePrice()),
                    CURRENCY_FORMAT.format(currentPrice),
                    CURRENCY_FORMAT.format(invested),
                    CURRENCY_FORMAT.format(currentValue),
                    pnlFormatted);
        }

        System.out.println("--------------------------------------------------------------------------------------------------------");
        double totalInvested = portfolio.calculateTotalInvested();
        double totalStockValue = portfolio.calculateStockMarketValue(market);
        double totalPnL = portfolio.calculateTotalUnrealizedProfitLoss(market);
        double totalPnLPct = (totalInvested > 0) ? (totalPnL / totalInvested) : 0.0;

        System.out.printf(" TOTAL INVESTED CAPITAL : %15s%n", CURRENCY_FORMAT.format(totalInvested));
        System.out.printf(" TOTAL STOCK MARKET VALUE: %15s%n", CURRENCY_FORMAT.format(totalStockValue));
        System.out.printf(" UNREALIZED PROFIT/LOSS  : %15s (%s)%n", CURRENCY_FORMAT.format(totalPnL), PERCENT_FORMAT.format(totalPnLPct));
        System.out.printf(" CASH BALANCE            : %15s%n", CURRENCY_FORMAT.format(portfolio.getCashBalance()));
        System.out.printf(" TOTAL NET WORTH         : %15s%n", CURRENCY_FORMAT.format(portfolio.calculateTotalNetWorth(market)));
        System.out.println("========================================================================================================");
    }

    /**
     * Displays a concise account summary including cash, investments, net worth, and returns.
     */
    public void displayAccountSummary() {
        double cash = portfolio.getCashBalance();
        double stockValue = portfolio.calculateStockMarketValue(market);
        double invested = portfolio.calculateTotalInvested();
        double netWorth = portfolio.calculateTotalNetWorth(market);
        double initialDeposit = portfolio.getInitialDeposit();
        double totalReturn = netWorth - initialDeposit;
        double returnPct = (initialDeposit > 0) ? (totalReturn / initialDeposit) : 0.0;

        System.out.println("================================================================================");
        System.out.println("                         ACCOUNT SUMMARY & CASH BALANCE                         ");
        System.out.println("================================================================================");
        System.out.printf("  Initial Deposit:         %18s%n", CURRENCY_FORMAT.format(initialDeposit));
        System.out.printf("  Available Cash:          %18s%n", CURRENCY_FORMAT.format(cash));
        System.out.printf("  Invested in Stocks:      %18s%n", CURRENCY_FORMAT.format(invested));
        System.out.printf("  Current Stock Value:     %18s%n", CURRENCY_FORMAT.format(stockValue));
        System.out.println("  --------------------------------------------------");
        System.out.printf("  TOTAL ACCOUNT NET WORTH: %18s%n", CURRENCY_FORMAT.format(netWorth));
        System.out.printf("  TOTAL OVERALL RETURN:    %18s (%s)%n", CURRENCY_FORMAT.format(totalReturn), PERCENT_FORMAT.format(returnPct));
        System.out.println("================================================================================");
    }

    /**
     * Displays the complete transaction history log.
     */
    public void displayTransactionHistory() {
        System.out.println("========================================================================================================");
        System.out.println("                                         TRANSACTION HISTORY                                            ");
        System.out.println("========================================================================================================");

        List<Transaction> history = portfolio.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println(" No transactions recorded yet. Execute buy or sell orders to view trading history.");
            System.out.println("========================================================================================================");
            return;
        }

        System.out.printf("%-10s | %-19s | %-5s | %-6s | %18s | %14s%n",
                "TXN ID", "TIMESTAMP", "TYPE", "SYMBOL", "SHARES @ PRICE", "TOTAL VALUE");
        System.out.println("--------------------------------------------------------------------------------------------------------");

        for (Transaction txn : history) {
            String details = String.format("%d @ %s", txn.getQuantity(), CURRENCY_FORMAT.format(txn.getPricePerShare()));
            System.out.printf("%-10s | %-19s | %-5s | %-6s | %18s | %14s%n",
                    txn.getTransactionId(),
                    txn.getFormattedTimestamp(),
                    txn.getType(),
                    txn.getStockSymbol(),
                    details,
                    CURRENCY_FORMAT.format(txn.getTotalAmount()));
        }

        System.out.println("========================================================================================================");
    }

    /**
     * Simulates realistic price movements for all stocks in the market (-5% to +5%).
     */
    public void simulateMarketFluctuation() {
        System.out.println("[SIMULATION] Simulating market trading day price updates...");
        for (Stock stock : market.values()) {
            // Random fluctuation between -5.0% and +5.0%
            double changePercent = (random.nextDouble() * 10.0 - 5.0) / 100.0;
            double newPrice = Math.round((stock.getCurrentPrice() * (1.0 + changePercent)) * 100.0) / 100.0;
            if (newPrice < 1.0) newPrice = 1.0;
            stock.setCurrentPrice(newPrice);
        }
        System.out.println("[SIMULATION] Market prices updated successfully! Check 'Live Market Data' or 'Portfolio'.");
        displayMarketStocks();
    }

    private void displayExitMessage() {
        System.out.println("================================================================================");
        System.out.println(" Thank you for trading with CodeAlpha Stock Trading Platform. Have a great day!");
        System.out.println("================================================================================");
    }

    // --- Input Validation Helpers ---

    private int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid numeric input. Please enter a valid integer.");
            }
        }
    }

    private String truncate(String str, int maxLen) {
        if (str == null) return "";
        if (str.length() <= maxLen) return str;
        return str.substring(0, maxLen - 2) + "..";
    }

    // --- Getter for testing ---
    public Portfolio getPortfolio() {
        return portfolio;
    }

    public Map<String, Stock> getMarket() {
        return market;
    }
}
