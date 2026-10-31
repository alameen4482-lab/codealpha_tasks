# CodeAlpha Stock Trading Platform (Task 2)

A robust, console-based **Stock Trading Simulation Platform** built with **Core Java (Java SE 17)** for the **CodeAlpha Java Programming Internship (Task 2)**. 

This platform allows users to simulate buying and selling stocks in real-time, monitor their investment portfolio, analyze performance with profit/loss metrics, and inspect transaction history logs with complete input validation.

---

## 📌 Project Overview

The **Stock Trading Platform** simulates an active stock brokerage environment. The user starts with a demo cash balance of **$100,000.00** and can trade a curated catalog of major market stocks (Apple, Microsoft, Google, Amazon, Tesla, NVIDIA, Meta, Netflix).

### Key Highlights:
- **Simulated Real-Time Market Data:** Static baseline stock prices with a realistic daily fluctuation engine (-5% to +5%).
- **Position & Cost-Basis Tracking:** Accurately computes weighted average purchase prices when adding shares across multiple orders.
- **Profit & Loss Metrics:** Live unrealized P/L in both dollar amounts and percentages on a per-stock position and portfolio-wide basis.
- **Audit & Transaction History:** Full immutable ledger recording every buy and sell order with timestamps, share counts, executed price, and total order volume.
- **Zero External Dependencies:** Built entirely with standard Java collections and OOP principles without third-party frameworks.

---

## 🚀 Key Features

1. **Market Data Feed**
   - Displays stock symbol, company name, live market price, and 24h percentage change.
2. **Buy Orders**
   - Purchase shares with automatic cash balance validation and immediate portfolio cost-basis calculation.
3. **Sell Orders**
   - Liquidate shares with strict verification of owned quantity, updating available cash and realized return.
4. **Portfolio Analytics**
   - View open positions, shares owned, average cost basis, current market value, total capital invested, and unrealized profit/loss.
5. **Account & Cash Summary**
   - Real-time snapshot of liquid cash, capital deployed in equities, total net worth, and all-time portfolio ROI.
6. **Transaction History Log**
   - Complete ledger of all executed trades with unique transaction IDs (`TXN-1001`), timestamps, order types, quantities, and settled values.
7. **Simulate Market Price Fluctuation**
   - Dynamic simulation tool to trigger random market movements and observe changing unrealized P/L.
8. **Robust Input Validation & Error Handling**
   - Prevents invalid stock tickers.
   - Prevents zero, negative, or non-numeric share quantities.
   - Prevents overselling (selling more shares than owned).
   - Prevents orders exceeding available cash balance.
   - Graceful menu handling for invalid selections without crashing.

---

## 🧠 Object-Oriented Programming (OOP) Concepts Used

| Concept | Implementation in Code |
| :--- | :--- |
| **Classes & Objects** | Separate classes for each domain entity: `Stock`, `Portfolio`, `Transaction`, `TradingPlatform`, and `Main`. |
| **Encapsulation** | All fields in `Stock`, `Transaction`, and `Portfolio` are `private`. State is manipulated safely through public getters, setters, and business methods (`buyStock`, `sellStock`, `updatePrice`). |
| **Data Cohesion** | `Portfolio.Holding` encapsulates individual position state and maintains the weighted average cost formula. |
| **Collections Framework** | `LinkedHashMap<String, Stock>` for predictable stock market indexing, `ArrayList<Transaction>` for transaction logs, and `Map<String, Holding>` for fast position lookups. |
| **Separation of Concerns (SoC)** | `Main` handles entry, `TradingPlatform` manages CLI UI and validation, `Portfolio` manages account and position math, `Stock` manages market assets, and `Transaction` manages audit records. |

---

## 📁 Project Structure

```text
CodeAlpha_StockTradingPlatform/
├── .gitignore                      # Git ignore rules for Java binaries and IDE files
├── README.md                       # Complete project documentation
├── bin/                            # Compiled bytecode (.class files)
└── src/
    ├── Main.java                   # Application entry point
    ├── Stock.java                  # Stock entity model with price change tracking
    ├── Transaction.java            # Immutable transaction record model
    ├── Portfolio.java              # Portfolio holding logic, cost-basis, & P/L
    ├── TradingPlatform.java        # CLI menu, market feed, & validation engine
    └── StockPlatformTest.java      # Automated unit test suite (35+ test cases)
```

---

## 🛠️ How to Compile

Ensure you have **Java JDK 8+** (Java 17 or higher recommended) installed.

### Option 1: Compile from Terminal / PowerShell
```bash
# Navigate to the project root directory
cd CodeAlpha_StockTradingPlatform

# Compile all source files into the bin directory
javac -d bin src/*.java
```

---

## ▶️ How to Run

### Run the Interactive Trading Platform:
```bash
java -cp bin Main
```

### Run the Automated Test Suite:
```bash
java -cp bin StockPlatformTest
```

---

## 💻 Example Usage & CLI Walkthrough

### 1. Main Menu
```text
================================================================================
                   CODEALPHA STOCK TRADING PLATFORM (V1.0)                      
================================================================================
 Welcome, Trader! Your simulated trading account is ready.
 Initial Demo Cash Balance: $100,000.00
================================================================================

--------------------------------------------------------------------------------
                              MAIN TRADING MENU                                 
--------------------------------------------------------------------------------
  1. View Available Market Stocks
  2. Buy Stocks
  3. Sell Stocks
  4. View My Portfolio (Holdings & P/L)
  5. View Cash Balance & Account Summary
  6. View Transaction History
  7. Simulate Market Price Updates
  8. Exit Platform
--------------------------------------------------------------------------------
```

### 2. View Market Stocks
```text
================================================================================
                             LIVE MARKET DATA                                   
================================================================================
SYMBOL   | COMPANY NAME             |  CURRENT PRICE |   PRICE CHANGE
--------------------------------------------------------------------------------
AAPL     | Apple Inc.               |        $189.50 |         0.00%
MSFT     | Microsoft Corp.          |        $425.30 |         0.00%
GOOGL    | Alphabet Inc.            |        $178.60 |         0.00%
AMZN     | Amazon.com Inc.          |        $185.20 |         0.00%
TSLA     | Tesla Inc.               |        $252.40 |         0.00%
NVDA     | NVIDIA Corp.             |        $124.75 |         0.00%
META     | Meta Platforms Inc.      |        $512.10 |         0.00%
NFLX     | Netflix Inc.             |        $675.80 |         0.00%
================================================================================
```

### 3. Buying Stocks (with Validation)
```text
--- [ BUY STOCK ORDER ] ---
Available Cash Balance: $100,000.00

Enter Stock Symbol to BUY (or 0 to cancel): AAPL
Enter quantity of shares to buy: 50

================================================================================
[SUCCESS] Buy Order Executed Successfully!
  Bought:    50 shares of AAPL (Apple Inc.)
  Price:     $189.50 per share
  Total:     $9,475.00
  New Cash:  $90,525.00
================================================================================
```

### 4. Viewing Portfolio & P/L
```text
========================================================================================================
                                         YOUR PORTFOLIO                                                 
========================================================================================================
SYMBOL | COMPANY            | SHARES |   AVG COST |      PRICE |     INVESTED |    MKT VALUE |    PROFIT/LOSS
--------------------------------------------------------------------------------------------------------
AAPL   | Apple Inc.         |     40 |    $189.50 |    $185.68 |    $7,580.00 |    $7,427.20 | -$152.80 (-2.02%)
MSFT   | Microsoft Corp.    |     20 |    $425.30 |    $413.99 |    $8,506.00 |    $8,279.80 | -$226.20 (-2.66%)
--------------------------------------------------------------------------------------------------------
 TOTAL INVESTED CAPITAL :      $16,086.00
 TOTAL STOCK MARKET VALUE:      $15,707.00
 UNREALIZED PROFIT/LOSS  :        -$379.00 (-2.36%)
 CASH BALANCE            :      $83,914.00
 TOTAL NET WORTH         :      $99,621.00
========================================================================================================
```

### 5. Viewing Transaction History
```text
========================================================================================================
                                         TRANSACTION HISTORY                                            
========================================================================================================
TXN ID     | TIMESTAMP           | TYPE  | SYMBOL |     SHARES @ PRICE |    TOTAL VALUE
--------------------------------------------------------------------------------------------------------
TXN-1001   | 2026-10-08 21:32:58 | BUY   | AAPL   |       50 @ $189.50 |      $9,475.00
TXN-1002   | 2026-10-08 21:32:58 | BUY   | MSFT   |       20 @ $425.30 |      $8,506.00
TXN-1003   | 2026-10-08 21:32:58 | SELL  | AAPL   |       10 @ $189.50 |      $1,895.00
========================================================================================================
```

---

## 🧪 Automated Testing

The repository includes a standalone test runner (`StockPlatformTest.java`) containing 35 comprehensive assertions testing:
- Valid and invalid buy/sell operations
- Insufficient cash checks
- Overselling protection
- Multiple purchase weighted average price calculations
- Unrealized profit/loss computation
- Transaction history integrity
- Market simulation price updates

```bash
java -cp bin StockPlatformTest
```
**Output:** `TEST SUMMARY: 35/35 TESTS PASSED (100.0%)`

---

## 📜 License
This project is developed as part of the **CodeAlpha Java Programming Internship Task 2**.
