# University Parking System

## Project Overview
This project implements a parking management system for a university with multiple parking lots. The system manages customers, their registered cars, and parking transactions across different parking lots.

## Features
- Customer registration and management
- Car registration with automatic permit generation
- Multiple parking lots with capacity management
- Parking transaction tracking with entry/exit times
- Support for different car types (COMPACT and SUV) with automatic discount application
- 20% discount for compact cars compared to SUVs
- Permit expiration validation

## Class Structure

### Core Classes

#### 1. **Address**
- Represents physical addresses for customers and parking lots
- Supports both primary and secondary street addresses
- Provides formatted address output

#### 2. **CarType** (Enum)
- Defines car types: COMPACT and SUV
- Includes discount multiplier logic (20% discount for compact cars)

#### 3. **Car**
- Represents a registered vehicle
- Tracks permit information and expiration dates
- Validates permit status
- Associates with customer (owner)

#### 4. **Customer**
- Represents a registered parking system user
- Can register multiple cars
- Automatically generates permits for registered cars
- Manages customer information (ID, name, address, phone)

#### 5. **ParkingLot**
- Represents a physical parking facility
- Manages capacity and current occupancy
- Tracks cars currently parked
- Validates permits on entry
- Creates parking transactions

#### 6. **ParkingTransaction**
- Records parking events (entry and exit times)
- Calculates parking duration
- Supports billing calculations

## Compilation and Testing

### Prerequisites
- Java JDK 11 or higher
- JUnit 5.9.3 or higher
- Maven 3.6+ (optional, for automated build)

### Manual Compilation

```bash
# Compile main classes
javac -d target/classes src/main/java/com/university/parking/*.java

# Compile test classes (requires JUnit 5 in classpath)
javac -cp target/classes:junit-platform-console-standalone-1.9.3.jar \
      -d target/test-classes \
      src/test/java/com/university/parking/*.java

# Run tests
java -jar junit-platform-console-standalone-1.9.3.jar \
     --class-path target/classes:target/test-classes \
     --scan-class-path
```

### Using Maven

```bash
# Compile code
mvn clean compile

# Run tests
mvn test

# Create JAR file
mvn package
```

## Class Relationships

```
Customer (1) -----> (0..*) Car
   |
   |---> (1) Address

ParkingLot (1) -----> (1) Address
   |
   |---> (0..*) ParkingTransaction

Car (1) -----> (1) CarType [enum]

ParkingTransaction (1) -----> (1) Car
```

## Usage Examples

### Registering a Customer and Car

```java
// Create customer address
Address customerAddress = new Address(
    "123 University Ave", 
    "Denver", 
    "CO", 
    "80208"
);

// Create customer
Customer customer = new Customer(
    "CUST001", 
    "John Smith", 
    customerAddress, 
    "303-555-1234"
);

// Register a car (permit is automatically generated)
Car car = customer.register("ABC123", CarType.COMPACT);
System.out.println("Permit: " + car.getPermit());
System.out.println("Expires: " + car.getPermitExpiration());
```

### Using a Parking Lot

```java
// Create parking lot
Address lotAddress = new Address(
    "100 Parking Way", 
    "Denver", 
    "CO", 
    "80208"
);
ParkingLot lot = new ParkingLot("LOT-A", lotAddress, 50);

// Car enters parking lot
lot.entry(car);
System.out.println("Available spaces: " + lot.getAvailableSpaces());

// Car exits parking lot
ParkingTransaction transaction = lot.exit("ABC123");
System.out.println("Parked for: " + transaction.getHoursParked() + " hours");
```

## Test Coverage

All classes have comprehensive JUnit tests covering:
- Constructor validation
- Getter/setter functionality
- Business logic (permit validation, capacity management, etc.)
- Edge cases (expired permits, full parking lots, etc.)
- Exception handling

### Test Classes
- `AddressTest` - 8 test methods
- `CarTypeTest` - 5 test methods
- `CarTest` - 9 test methods
- `CustomerTest` - 9 test methods
- `ParkingLotTest` - 11 test methods
- `ParkingTransactionTest` - 8 test methods

**Total: 50 unit tests**

## Design Decisions

### 1. Use of java.time Classes
- Implemented `LocalDate` for permit expiration dates
- Implemented `Instant` for parking transaction timestamps
- More modern and thread-safe than legacy `java.util.Date`

### 2. Permit Generation
- Automatic permit generation in `Customer.register()` method
- Centralized logic ensures consistency
- One-year default expiration period

### 3. ParkingTransaction Class
- Separate class for transaction tracking
- Enables future billing system integration
- Supports both ongoing and completed transactions

### 4. Validation Strategy
- Permit validation in both `Car` and `ParkingLot` classes
- Defensive programming with meaningful exceptions
- Clear error messages for debugging

### 5. Encapsulation
- Defensive copying in `getRegisteredCars()` to prevent external modification
- Package-private relationships where appropriate
- Clear separation of concerns

## Future Enhancements

Potential areas for expansion:
1. Billing system integration
2. Different fee structures per parking lot
3. Reserved parking spaces
4. Monthly permits vs. daily permits
5. Real-time parking availability API
6. Mobile app integration
7. Payment processing
8. Parking violation tracking

## Authors
Created for University of Denver - Object-Oriented Programming Course
Week 4 Assignment - Parking System Classes

## License
Educational use only - University of Denver
