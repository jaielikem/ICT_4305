# Project Structure

```
parking-system/
│
├── pom.xml                          # Maven build configuration
├── README.md                        # Project documentation
├── REFLECTION.md                    # Assignment reflection (487 words)
├── CLASS_DIAGRAM.mermaid           # Visual class diagram (Mermaid format)
├── verify.sh                        # Compilation verification script
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── university/
│   │               └── parking/
│   │                   ├── Address.java              # Physical address class
│   │                   ├── CarType.java              # Car type enumeration
│   │                   ├── Car.java                  # Car/vehicle class
│   │                   ├── Customer.java             # Customer class
│   │                   ├── ParkingLot.java          # Parking lot class
│   │                   └── ParkingTransaction.java   # Transaction tracking
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── university/
│                   └── parking/
│                       ├── AddressTest.java          # Address unit tests
│                       ├── CarTypeTest.java          # CarType unit tests
│                       ├── CarTest.java              # Car unit tests
│                       ├── CustomerTest.java         # Customer unit tests
│                       ├── ParkingLotTest.java      # ParkingLot unit tests
│                       └── ParkingTransactionTest.java # Transaction tests
│
└── target/                          # Compiled output (generated)
    ├── classes/                     # Compiled main classes
    └── test-classes/                # Compiled test classes
```

## File Descriptions

### Main Source Files

**Address.java** (123 lines)
- Represents physical addresses with street, city, state, and ZIP code
- Two constructors: with and without secondary address
- `getAddressInfo()` method returns formatted address string

**CarType.java** (28 lines)
- Enumeration defining COMPACT and SUV car types
- Includes `getDiscountMultiplier()` method (0.8 for COMPACT, 1.0 for SUV)
- Implements 20% discount for compact cars

**Car.java** (115 lines)
- Represents a vehicle with permit, license, type, and owner
- Two constructors: with and without initial permit
- Methods: `isPermitValid()`, `assignPermit()`
- Validates permit expiration using LocalDate

**Customer.java** (126 lines)
- Represents parking system users
- Manages list of registered cars
- `register()` method creates cars and auto-generates permits
- `findCarByLicense()` for car lookup
- Implements defensive copying in `getRegisteredCars()`

**ParkingLot.java** (151 lines)
- Manages parking facility with capacity tracking
- Uses HashMap for O(1) car lookup
- `entry()` method validates permits and capacity
- `exit()` method completes transactions
- Tracks current occupancy and available spaces

**ParkingTransaction.java** (94 lines)
- Records parking events with entry/exit times
- Uses Instant for precise timestamps
- Calculates parking duration and hours (rounded up)
- Supports ongoing and completed transactions

### Test Files

**AddressTest.java** (8 tests)
- Tests both constructors
- Validates address formatting with/without secondary address
- Tests getters, setters, and toString()

**CarTypeTest.java** (5 tests)
- Verifies discount multipliers
- Tests enum values and valueOf()
- Confirms 20% discount calculation

**CarTest.java** (9 tests)
- Tests both constructors
- Validates permit checking (valid, invalid, expired)
- Tests assignPermit() functionality
- Verifies getters and setters

**CustomerTest.java** (9 tests)
- Tests customer creation
- Validates car registration process
- Tests unique permit generation
- Verifies findCarByLicense()
- Tests defensive copying

**ParkingLotTest.java** (11 tests)
- Tests lot creation and capacity
- Validates entry with valid/invalid permits
- Tests full lot scenario
- Verifies exit process and transaction completion
- Tests car parking status checks

**ParkingTransactionTest.java** (8 tests)
- Tests transaction creation
- Validates duration calculations
- Tests hour rounding logic
- Verifies exit time setting

### Configuration Files

**pom.xml**
- Maven build configuration
- Configures Java 11 compiler
- Includes JUnit 5 dependencies
- Surefire plugin for test execution

**verify.sh**
- Bash script to verify compilation
- Creates output directories
- Compiles all classes
- Lists compiled files
- Provides test execution instructions

### Documentation Files

**README.md**
- Comprehensive project overview
- Usage examples
- Compilation instructions
- Design decisions summary

**REFLECTION.md**
- 487-word assignment reflection
- Discusses challenges and learnings
- Explains implementation decisions
- Includes academic citations (Turabian format)

**CLASS_DIAGRAM.mermaid**
- Visual representation of all classes
- Shows relationships and cardinalities
- Includes all attributes and methods
- Can be rendered in GitHub or Mermaid Live Editor

## Total Statistics

- **Main Classes**: 6 files, ~637 lines of code
- **Test Classes**: 6 files, ~50 test methods
- **Documentation**: 3 files
- **Configuration**: 2 files
- **Total Lines of Code**: ~1,200+ lines (excluding comments and blank lines)

## How to Use This Project

1. **Clone or download** the entire parking-system directory
2. **Navigate** to the project root
3. **Compile** using Maven (`mvn compile`) or the verify.sh script
4. **Run tests** using Maven (`mvn test`) or JUnit Console Launcher
5. **Review** the README.md and REFLECTION.md for detailed information
6. **View** CLASS_DIAGRAM.mermaid in any Mermaid viewer

## Requirements Met

✅ Customer, Car, and ParkingLot classes implemented  
✅ Address class implemented  
✅ CarType enum implemented  
✅ All required methods present  
✅ Additional helper methods added  
✅ JUnit tests for all classes (50 tests total)  
✅ Class diagram created (Mermaid format)  
✅ 250-500 word reflection (487 words)  
✅ Turabian citations in reflection  
✅ Code compiles successfully  
✅ Professional formatting and documentation  
