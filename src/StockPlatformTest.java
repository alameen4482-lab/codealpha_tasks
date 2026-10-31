/**
 * Automated test suite to verify all business logic, calculations,
 * validations, and edge cases for the Stock Trading Platform.
 * 
 * @author CodeAlpha Intern
 */
public class StockPlatformTest {

    private static int passedTests = 0;
    private static int totalTests = 0;

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("               RUNNING AUTOMATED TEST SUITE FOR STOCK PLATFORM                  ");
        System.out.println("================================================================================");

        testInitialBalance();
        testBuyStockValid();
        testInsufficientCash();
        testInvalidBuyQuantity();
        testMultipleBuysAveragePrice();
        testSellStockValid();
        testSellMoreSharesThanOwned();
        testSellUnownedStock();
        testInvalidSellQuantity();
        testPortfolioMetricsAndProfitLoss();
        testTransactionHistoryLog();
        testPriceUpdatesAndSimulation();

        System.out.println("================================================================================");
        System.out.printf(" TEST SUMMARY: %d/%d TESTS PASSED (%.1f%%)%n",
                passedTests, totalTests, ((double) passedTests / totalTests) * 100.0);
        System.out.println("================================================================================");

        if (passedTests != totalTests) {
            System.err.println("Some tests failed!");
            System.exit(1);
        }
    }

    private static void assertTrue(String testName, boolean condition) {
        totalTests++;
        if (condition) {
            System.out.printf("  [PASS] %s%n", testName);
            passedTests++;
        } else {
            System.err.printf("  [FAIL] %s%n", testName);
        }
    }

    private static void assertEquals(String testName, double expected, double actual, double delta) {
        totalTests++;
        if (Math.abs(expected - actual) <= delta) {
            System.out.printf("  [PASS] %s (Expected: %.2f, Got: %.2f)%n", testName, expected, actual);
            passedTests++;
        } else {
            System.err.printf("  [FAIL] %s (Expected: %.2f, Got: %.2f)%n", testName, expected, actual);
        }
    }

    private static void testInitialBalance() {
        Portfolio p = new Portfolio(100000.00);
        assertEquals("Initial cash balance should be 100,000.00", 100000.00, p.getCashBalance(), 0.001);
        assertTrue("Portfolio should initially have no holdings", !p.hasHoldings());
    }

    private static void testBuyStockValid() {
        Portfolio p = new Portfolio(100000.00);
        Stock aapl = new Stock("AAPL", "Apple Inc.", 150.00);

        boolean success = p.buyStock(aapl, 10);
        assertTrue("Buying 10 AAPL shares with sufficient cash should succeed", success);
        assertEquals("Cash balance after buying 10 AAPL @ $150 should be $98,500.00", 98500.00, p.getCashBalance(), 0.001);
        assertTrue("Portfolio should contain AAPL", p.getHolding("AAPL") != null);
        assertEquals("AAPL holding shares count should be 10", 10, p.getSharesOwned("AAPL"), 0.001);
        assertEquals("AAPL average price should be $150.00", 150.00, p.getHolding("AAPL").getAveragePrice(), 0.001);
    }

    private static void testInsufficientCash() {
        Portfolio p = new Portfolio(1000.00);
        Stock msft = new Stock("MSFT", "Microsoft Corp.", 400.00);

        boolean success = p.buyStock(msft, 5); // Needs 2000.00, has 1000.00
        assertTrue("Buying with insufficient cash should fail", !success);
        assertEquals("Cash balance should remain unchanged after failed buy", 1000.00, p.getCashBalance(), 0.001);
        assertEquals("Shares owned of MSFT should remain 0", 0, p.getSharesOwned("MSFT"), 0.001);
    }

    private static void testInvalidBuyQuantity() {
        Portfolio p = new Portfolio(10000.00);
        Stock tsla = new Stock("TSLA", "Tesla Inc.", 200.00);

        assertTrue("Buying 0 shares should fail", !p.buyStock(tsla, 0));
        assertTrue("Buying negative shares should fail", !p.buyStock(tsla, -5));
        assertTrue("Buying null stock should fail", !p.buyStock(null, 5));
    }

    private static void testMultipleBuysAveragePrice() {
        Portfolio p = new Portfolio(100000.00);
        Stock goog = new Stock("GOOGL", "Alphabet Inc.", 100.00);

        p.buyStock(goog, 10); // 10 @ $100 = $1,000
        goog.setCurrentPrice(200.00);
        p.buyStock(goog, 10); // 10 @ $200 = $2,000 -> Total 20 shares for $3,000 -> Avg: $150

        Portfolio.Holding h = p.getHolding("GOOGL");
        assertEquals("Total GOOGL shares should be 20", 20, h.getQuantity(), 0.001);
        assertEquals("Total GOOGL invested should be $3,000.00", 3000.00, h.getTotalInvested(), 0.001);
        assertEquals("Average purchase price should be $150.00", 150.00, h.getAveragePrice(), 0.001);
    }

    private static void testSellStockValid() {
        Portfolio p = new Portfolio(100000.00);
        Stock nvda = new Stock("NVDA", "NVIDIA Corp.", 100.00);

        p.buyStock(nvda, 20); // Spend $2,000. Cash = $98,000
        nvda.setCurrentPrice(150.00);
        boolean sellSuccess = p.sellStock(nvda, 10); // Sell 10 @ $150 = $1,500. Cash = $99,500

        assertTrue("Selling 10 owned NVDA shares should succeed", sellSuccess);
        assertEquals("Cash balance after sell should be $99,500.00", 99500.00, p.getCashBalance(), 0.001);
        assertEquals("Remaining NVDA shares should be 10", 10, p.getSharesOwned("NVDA"), 0.001);
        assertEquals("Remaining total invested for NVDA should be $1,000.00", 1000.00, p.getHolding("NVDA").getTotalInvested(), 0.001);
    }

    private static void testSellMoreSharesThanOwned() {
        Portfolio p = new Portfolio(100000.00);
        Stock meta = new Stock("META", "Meta Platforms Inc.", 500.00);

        p.buyStock(meta, 5); // Owns 5 shares
        boolean sellOver = p.sellStock(meta, 10); // Try to sell 10

        assertTrue("Selling more shares than owned should fail", !sellOver);
        assertEquals("Shares owned of META should remain 5", 5, p.getSharesOwned("META"), 0.001);
    }

    private static void testSellUnownedStock() {
        Portfolio p = new Portfolio(100000.00);
        Stock nflx = new Stock("NFLX", "Netflix Inc.", 600.00);

        boolean sellUnowned = p.sellStock(nflx, 2);
        assertTrue("Selling unowned stock should fail", !sellUnowned);
    }

    private static void testInvalidSellQuantity() {
        Portfolio p = new Portfolio(100000.00);
        Stock amzn = new Stock("AMZN", "Amazon.com Inc.", 180.00);
        p.buyStock(amzn, 10);

        assertTrue("Selling 0 shares should fail", !p.sellStock(amzn, 0));
        assertTrue("Selling negative shares should fail", !p.sellStock(amzn, -4));
    }

    private static void testPortfolioMetricsAndProfitLoss() {
        TradingPlatform tp = new TradingPlatform();
        Portfolio p = tp.getPortfolio();

        Stock aapl = tp.getMarket().get("AAPL"); // $189.50
        Stock msft = tp.getMarket().get("MSFT"); // $425.30

        p.buyStock(aapl, 10); // $1,895.00
        p.buyStock(msft, 10); // $4,253.00

        double totalInvested = p.calculateTotalInvested();
        assertEquals("Total invested should be $6,148.00", 6148.00, totalInvested, 0.01);

        // Simulate price change
        aapl.setCurrentPrice(200.00); // 10 * 200 = 2000
        msft.setCurrentPrice(400.00); // 10 * 400 = 4000

        double marketVal = p.calculateStockMarketValue(tp.getMarket());
        assertEquals("Stock market value should be $6,000.00", 6000.00, marketVal, 0.01);

        double pnl = p.calculateTotalUnrealizedProfitLoss(tp.getMarket());
        assertEquals("Unrealized P/L should be -$148.00", -148.00, pnl, 0.01);

        double netWorth = p.calculateTotalNetWorth(tp.getMarket());
        assertEquals("Total net worth should be cash + market value", p.getCashBalance() + marketVal, netWorth, 0.01);
    }

    private static void testTransactionHistoryLog() {
        Portfolio p = new Portfolio(100000.00);
        Stock tsla = new Stock("TSLA", "Tesla Inc.", 250.00);

        p.buyStock(tsla, 10);
        p.sellStock(tsla, 5);

        assertEquals("Transaction history should have 2 records", 2, p.getTransactionHistory().size(), 0.001);
        assertTrue("First transaction type should be BUY", p.getTransactionHistory().get(0).getType().equals("BUY"));
        assertTrue("Second transaction type should be SELL", p.getTransactionHistory().get(1).getType().equals("SELL"));
    }

    private static void testPriceUpdatesAndSimulation() {
        Stock stock = new Stock("TEST", "Test Corp.", 100.00);
        stock.setCurrentPrice(110.00);
        assertEquals("Current price should be 110.00", 110.00, stock.getCurrentPrice(), 0.001);
        assertEquals("Previous price should be 100.00", 100.00, stock.getPreviousPrice(), 0.001);
        assertEquals("Price change percentage should be +10%", 10.0, stock.getPriceChangePercentage(), 0.001);
    }
}
