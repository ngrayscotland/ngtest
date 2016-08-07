# Neil Gray - Simple Stock Test

## Gradle build
The simple stock module is built using a gradle build with standard Maven style directory structure for the Java source and test classes.

The build generates a jar file based on the project name 'jpsimplestock'.

Generate the jar file using `./gradlew build`

## Design
A summary of the design for the simple stock module is illustrated in the image file 'simple-stock-design-uml.png'.
The following sections detail contents of the various packages in the system.

### Interfaces

The SimpleStock module can be accessed by the following set of interfaces.  Consumers should code to the interfaces rather than the implementations to ensure loose
coupling and to facilitate testing.

- `StockStore` store and retrieve stocks.
- `StockCalculator` provides functions to determine dividend yield and PE ratio.
- `TradeEngine` provides functions to register trades, retrieve trades, calculate the volume weighted stock price and the all share index.

The implementation classes correspond in name to the interfaces <Interface>Impl e.g. `TradeEngineImpl`.

### Stock
The 'org.ng.simplestock.stock' package contains the stock store for registering and retrieving stocks
and the stock calculator for performing calculations against stocks based on price.

### Trade
The 'org.ng.simplestock.trade' package contains the trade engine that allows trades to be registered. 
The trade engine also provides functions for the calculation of the volume weighted stock price
and all share index.

### Domain

The `org.ng.simplestock.domain` package defines the domain model for the module.

The `Stock` type is an abstract class that currently has two concrete implementations `CommonStock` and `PreferredStock`.

The 'Trade' type is used for detailing an individual trade and has an associated `TradeType` enum that defines if the trade was a buy or sell.

The `BigDecimal` class is used for all monetary value storage in the system to benefit from core Java support in that class for rounding and other functions.
There is a `BigDecimalUtil` helper class that should be used for BigDecimal instance creation to ensure that the scale is set correctly on all instances.

### Exceptions

The 'org.ng.simplestock.exception' package contains the exceptions that may be generated during use of the simple stock system.
The javadoc for each interface document when those exceptions can occur.
