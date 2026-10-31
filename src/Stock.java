import java.text.DecimalFormat;

/**
 * Represents a stock in the market.
 * Encapsulates stock details including ticker symbol, company name, and current market price.
 * 
 * @author CodeAlpha Intern
 */
public class Stock {
    private String symbol;
    private String companyName;
    private double currentPrice;
    private double previousPrice;

    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("$#,##0.00");

    /**
     * Constructs a new Stock with given details.
     *
     * @param symbol       The ticker symbol of the stock (e.g., AAPL)
     * @param companyName  The official company name (e.g., Apple Inc.)
     * @param currentPrice The initial market price
     */
    public Stock(String symbol, String companyName, double currentPrice) {
        this.symbol = symbol.toUpperCase().trim();
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.previousPrice = currentPrice;
    }

    // --- Getters and Setters ---

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getPreviousPrice() {
        return previousPrice;
    }

    /**
     * Updates the market price of the stock.
     *
     * @param newPrice The updated market price
     */
    public void setCurrentPrice(double newPrice) {
        if (newPrice > 0) {
            this.previousPrice = this.currentPrice;
            this.currentPrice = newPrice;
        }
    }

    /**
     * Calculates the percentage change compared to the previous price.
     *
     * @return Percentage change as a double
     */
    public double getPriceChangePercentage() {
        if (previousPrice == 0) return 0.0;
        return ((currentPrice - previousPrice) / previousPrice) * 100.0;
    }

    @Override
    public String toString() {
        return String.format("%-8s | %-22s | %10s", symbol, companyName, CURRENCY_FORMAT.format(currentPrice));
    }
}
