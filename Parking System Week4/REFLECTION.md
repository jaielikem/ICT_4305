# Week 4 Assignment Reflection
## Parking System Classes

### What Did I Find Difficult or Easy?

The implementation of the core classes (Address, Car, Customer, and ParkingLot) was relatively straightforward once I established the relationships between them. The most challenging aspect was deciding how to handle parking transactions and ensuring that the system properly tracked cars entering and exiting parking lots. Creating a separate `ParkingTransaction` class helped encapsulate this complexity and will make future billing features easier to implement.

I found the enumeration for `CarType` particularly easy to implement, and adding the discount multiplier method directly to the enum was an elegant solution that keeps the pricing logic close to the car type definition. This follows the principle of cohesion in object-oriented design.

The most difficult part was determining the appropriate level of validation and error handling. For example, deciding whether to throw exceptions when a car with an invalid permit tries to enter a lot, versus simply returning a boolean, required careful consideration of how the system would be used in practice.

### What Helped Me?

The class diagram provided in the assignment was invaluable for understanding the basic structure and relationships. However, I found that implementing the actual functionality required me to think beyond what was explicitly shown in the diagram. The use of modern Java time classes (`LocalDate` and `Instant`) instead of the older `Date` class significantly simplified working with dates and timestamps.

JUnit testing was particularly helpful in validating my implementation decisions. Writing tests first for some methods (following test-driven development principles) helped clarify exactly what behavior each method should exhibit. For instance, testing the `ParkingLot.entry()` method with various scenarios (valid permits, expired permits, full lot) helped ensure robust validation logic.

### What Did I Wish I Knew Before?

I wish I had better understood the distinction between `LocalDate` and `Instant` before starting. I initially considered using `LocalDate` for parking transactions but realized that `Instant` provides more precise timestamp functionality needed for calculating parking duration. Understanding this distinction earlier would have saved refactoring time.

Additionally, I wish I had known more about JUnit 5's assertion methods and best practices from the start. Learning about `assertThrows()` for exception testing and the importance of testing both success and failure cases would have helped me write more comprehensive tests from the beginning.

### Implementation Decisions and Reasoning

**1. ParkingTransaction as a Separate Class**
Rather than tracking entry/exit times directly in the `ParkingLot` class, I created a dedicated `ParkingTransaction` class. This decision was based on the Single Responsibility Principle—the parking lot should manage space availability, while transactions should track individual parking events. This separation will make it easier to implement billing features in the future.

**2. Automatic Permit Generation in Customer.register()**
I chose to have the `register()` method automatically generate and assign permits to cars rather than requiring separate method calls. This ensures that every registered car immediately has a valid permit and reduces the chance of programmer error. The one-year expiration period is set automatically, which aligns with typical university parking permit policies.

**3. Defensive Copying in getRegisteredCars()**
The `Customer.getRegisteredCars()` method returns a new `ArrayList` rather than the internal list. This prevents external code from modifying the customer's car list directly, maintaining encapsulation and data integrity. While this adds a small performance cost, it's worthwhile for maintaining system consistency.

**4. Using HashMap for Parking Lot Tracking**
I implemented parking lot tracking using a `HashMap<String, ParkingTransaction>` with license plates as keys. This provides O(1) lookup performance when checking if a car is parked or retrieving transaction information, which is important for a system that may need to handle many concurrent parking operations.

**5. Comprehensive Validation**
I implemented validation at multiple levels: `Car.isPermitValid()` checks permit expiration, `ParkingLot.entry()` validates permits and capacity, and the `Customer.register()` method ensures permits are properly assigned. This multi-layered approach creates a robust system that catches errors early and provides clear feedback.

### References

Oracle Corporation. 2024. "Java SE 11 Documentation." Accessed February 1, 2026. https://docs.oracle.com/en/java/javase/11/

JUnit Team. 2024. "JUnit 5 User Guide." Accessed February 1, 2026. https://junit.org/junit5/docs/current/user-guide/

Martin, Robert C. 2008. *Clean Code: A Handbook of Agile Software Craftsmanship*. Upper Saddle River, NJ: Prentice Hall.

**Word Count: 487**
