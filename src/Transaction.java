import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a buy or sell transaction executed by the user.
 * Encapsulates details such as transaction ID, type, stock, quantity, price, and timestamp.
 * 
 * @author CodeAlpha Intern
 */
public class Transaction {
    private static int counter = 1000;

    private String transactionId;
    private String type; // "BUY" or "SELL"
    private String stockSymbol;
    private String companyName;
    private int quantity;
    private double pricePerShare;
    private double totalAmount;
    private LocalDateTime timestamp;

    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("$#,##0.00");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructs a new Transaction record.
     *
     * @param type          Type of transaction: "BUY" or "SELL"
     * @param stockSymbol   Ticker symbol of the stock
     * @param companyName   Name of the company
     * @param quantity      Number of shares traded
     * @param pricePerShare Price per share at the time of trade
     */
    public Transaction(String type, String stockSymbol, String companyName, int quantity, double pricePerShare) {
        this.transactionId = "TXN-" + (++counter);
        this.type = type.toUpperCase().trim();
        this.stockSymbol = stockSymbol.toUpperCase().trim();
        this.companyName = companyName;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.totalAmount = quantity * pricePerShare;
        this.timestamp = LocalDateTime.now();
    }

    // --- Getters ---

    public String getTransactionId() {
        return transactionId;
    }

    public String getType() {
        return type;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePerShare() {
        return pricePerShare;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFormattedTimestamp() {
        return timestamp.format(DATE_FORMAT);
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-19s | %-5s | %-6s | %6d shares @ %-10s | Total: %12s",
                transactionId,
                getFormattedTimestamp(),
                type,
                stockSymbol,
                quantity,
                CURRENCY_FORMAT.format(pricePerShare),
                CURRENCY_FORMAT.format(totalAmount));
    }
}
