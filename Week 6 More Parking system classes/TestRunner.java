package parking;

import java.util.*;

/**
 * Lightweight test runner — mirrors every test in ParkingSystemTest
 * without requiring a JUnit JAR on the classpath.
 *
 * Run with:
 *   javac -d out/test src/test/java/parking/TestRunner.java
 *   java  -cp out/main:out/test parking.TestRunner
 */
public class TestRunner {

    // ── tiny assertion helpers ──────────────────────────────────────────
    static void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual))
            throw new AssertionError("Expected <" + expected + "> but was <" + actual + ">");
    }
    static void assertEquals(double expected, double actual, double delta) {
        if (Math.abs(expected - actual) > delta)
            throw new AssertionError("Expected <" + expected + "> but was <" + actual + ">");
    }
    static void assertEquals(long expected, long actual) {
        if (expected != actual)
            throw new AssertionError("Expected <" + expected + "> but was <" + actual + ">");
    }
    static void assertNotNull(Object o) {
        if (o == null) throw new AssertionError("Expected non-null value");
    }
    static void assertNull(Object o) {
        if (o != null) throw new AssertionError("Expected null but was <" + o + ">");
    }
    static void assertTrue(boolean condition) {
        if (!condition) throw new AssertionError("Expected true");
    }

    // ── test counter ────────────────────────────────────────────────────
    static int passed = 0, failed = 0;

    static void run(String name, Runnable test) {
        try {
            test.run();
            System.out.printf("  [PASS] %s%n", name);
            passed++;
        } catch (AssertionError | Exception e) {
            System.out.printf("  [FAIL] %s  ← %s%n", name, e.getMessage());
            failed++;
        }
    }

    // ── shared setup (recreated per group) ─────────────────────────────
    static ParkingOffice newOffice() {
        Address addr = new Address("100 Main St", "Springfield", "IL", "62701");
        return new ParkingOffice("City Parking Office", addr);
    }
    static ParkingLot newLot(String id) {
        Address addr = new Address("200 Park Ave", "Springfield", "IL", "62702");
        return new ParkingLot(id, "Downtown Lot", addr, 50, Money.of(2.50));
    }

    // ── tests ───────────────────────────────────────────────────────────
    public static void main(String[] args) {

        System.out.println("=".repeat(55));
        System.out.println("  Parking System — Test Suite");
        System.out.println("=".repeat(55));

        // ── Money ───────────────────────────────────────────────────────
        System.out.println("\n[ Money ]");

        run("money_storesCorrectCents", () -> {
            Money m = new Money(250);
            assertEquals(250L, m.getCents());
        });

        run("money_getDollarsIsCorrect", () -> {
            Money m = Money.of(3.75);
            assertEquals(3.75, m.getDollars(), 0.001);
        });

        run("money_toStringFormatsCorrectly", () -> {
            Money m = Money.of(2.50);
            assertEquals("$2.50", m.toString());
        });

        run("money_additionWorks", () -> {
            Money a = Money.of(1.00);
            Money b = Money.of(2.50);
            assertEquals(Money.of(3.50), a.add(b));
        });

        // ── Customer registration ────────────────────────────────────────
        System.out.println("\n[ Customer Registration ]");

        run("register_customerAddedToOffice", () -> {
            ParkingOffice office = newOffice();
            Address addr = new Address("1 Elm St", "Springfield", "IL", "62701");
            Customer c = office.register("Alice Smith", addr, "555-0100");
            assertNotNull(c);
            assertEquals("Alice Smith", c.getName());
            assertEquals(1, office.getCustomers().size());
        });

        run("getCustomer_returnsCorrectCustomer", () -> {
            ParkingOffice office = newOffice();
            Address addr = new Address("2 Oak Ave", "Springfield", "IL", "62701");
            office.register("Bob Jones", addr, "555-0200");
            Customer found = office.getCustomer("Bob Jones");
            assertNotNull(found);
            assertEquals("Bob Jones", found.getName());
        });

        run("getCustomer_returnsNullWhenNotFound", () -> {
            ParkingOffice office = newOffice();
            assertNull(office.getCustomer("Nobody"));
        });

        // ── Car / Permit ─────────────────────────────────────────────────
        System.out.println("\n[ Car Registration & Permits ]");

        run("register_carIssuesPermit", () -> {
            ParkingOffice office = newOffice();
            Address addr = new Address("3 Pine Rd", "Springfield", "IL", "62701");
            Customer c = office.register("Carol White", addr, "555-0300");
            ParkingPermit permit = office.register(c, "IL-CAROL-01", CarType.COMPACT);
            assertNotNull(permit);
            assertEquals("IL-CAROL-01", permit.getCar().getLicense());
            assertEquals(CarType.COMPACT, permit.getCar().getType());
            assertEquals(c.getCustomerId(), permit.getCar().getOwnerId());
            assertEquals(1, office.getCars().size());
            assertEquals(1, office.getPermits().size());
        });

        run("carType_enumValuesExist", () -> {
            assertNotNull(CarType.valueOf("COMPACT"));
            assertNotNull(CarType.valueOf("SUV"));
            assertNotNull(CarType.valueOf("TRUCK"));
            assertNotNull(CarType.valueOf("ELECTRIC"));
            assertNotNull(CarType.valueOf("MOTORCYCLE"));
            assertNotNull(CarType.valueOf("HANDICAPPED"));
        });

        // ── Parking Lot / Charges ────────────────────────────────────────
        System.out.println("\n[ Parking Lot Entry & Charges ]");

        run("lotEntry_generatesCharge", () -> {
            ParkingOffice office = newOffice();
            ParkingLot lot = newLot("LOT-001");
            office.addLot(lot);
            Address addr = new Address("4 Maple Ln", "Springfield", "IL", "62701");
            Customer c = office.register("Dave Green", addr, "555-0400");
            ParkingPermit permit = office.register(c, "IL-DAVE-01", CarType.SUV);
            ParkingCharge charge = lot.enter(permit);
            assertNotNull(charge);
            assertEquals(permit.getPermitId(), charge.getPermitId());
            assertEquals("LOT-001", charge.getLotId());
            assertEquals(Money.of(2.50), charge.getAmount());
        });

        run("lotEntry_recordedAfterAddCharge", () -> {
            ParkingOffice office = newOffice();
            ParkingLot lot = newLot("LOT-001");
            office.addLot(lot);
            Address addr = new Address("5 Cedar Ct", "Springfield", "IL", "62701");
            Customer c = office.register("Eve Black", addr, "555-0500");
            ParkingPermit permit = office.register(c, "IL-EVE-01", CarType.ELECTRIC);
            ParkingCharge charge = lot.enter(permit);
            assertNotNull(charge);
            Money chargedAmount = office.addCharge(charge);
            assertEquals(Money.of(2.50), chargedAmount);
            assertEquals(1, office.getCharges().size());
        });

        run("getTotalCharges_sumsMultipleCharges", () -> {
            ParkingOffice office = newOffice();
            ParkingLot lot = newLot("LOT-001");
            office.addLot(lot);
            Address addr = new Address("6 Birch Blvd", "Springfield", "IL", "62701");
            Customer c = office.register("Frank Red", addr, "555-0600");
            ParkingPermit permit = office.register(c, "IL-FRANK-01", CarType.TRUCK);
            for (int i = 0; i < 3; i++) {
                ParkingCharge ch = lot.enter(permit);
                assertNotNull(ch);
                office.addCharge(ch);
                lot.exit(permit);
            }
            Money total = office.getTotalCharges(permit.getPermitId());
            assertEquals(Money.of(7.50), total);
        });

        run("lot_fullCapacity_returnsNull", () -> {
            ParkingOffice office = newOffice();
            Address lotAddr = new Address("7 Tiny Ln", "Springfield", "IL", "62701");
            ParkingLot tinyLot = new ParkingLot("LOT-TINY", "Tiny Lot", lotAddr, 1, Money.of(1.00));
            office.addLot(tinyLot);
            Address a1 = new Address("7a St", "Springfield", "IL", "62701");
            Address a2 = new Address("7b St", "Springfield", "IL", "62701");
            Customer c1 = office.register("G One", a1, "555-0701");
            Customer c2 = office.register("G Two", a2, "555-0702");
            ParkingPermit p1 = office.register(c1, "IL-G1", CarType.COMPACT);
            ParkingPermit p2 = office.register(c2, "IL-G2", CarType.COMPACT);
            assertNotNull(tinyLot.enter(p1));   // fits
            assertNull(tinyLot.enter(p2));      // full
        });

        // ── ParkingCharge toString ────────────────────────────────────────
        System.out.println("\n[ ParkingCharge toString ]");

        run("parkingCharge_toStringContainsExpectedFields", () -> {
            ParkingCharge charge = new ParkingCharge("PERMIT-123", "LOT-001", Money.of(5.00));
            String s = charge.toString();
            assertTrue(s.contains("PERMIT-123"));
            assertTrue(s.contains("LOT-001"));
            assertTrue(s.contains("$5.00"));
        });

        // ── Summary ──────────────────────────────────────────────────────
        System.out.println("\n" + "=".repeat(55));
        int total = passed + failed;
        System.out.printf("  Results: %d/%d tests passed", passed, total);
        if (failed == 0) System.out.println("  ✓  ALL TESTS PASSED");
        else             System.out.println("  ✗  " + failed + " FAILURE(S)");
        System.out.println("=".repeat(55));

        System.exit(failed > 0 ? 1 : 0);
    }
}
