package parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Parking System classes.
 */
public class ParkingSystemTest {

    private ParkingOffice office;
    private ParkingLot    lot;

    @BeforeEach
    void setUp() {
        Address officeAddr = new Address("100 Main St", "Springfield", "IL", "62701");
        office = new ParkingOffice("City Parking Office", officeAddr);

        Address lotAddr = new Address("200 Park Ave", "Springfield", "IL", "62702");
        lot = new ParkingLot("LOT-001", "Downtown Lot", lotAddr, 50, Money.of(2.50));
        office.addLot(lot);
    }

    // ------------------------------------------------------------------
    // Money tests
    // ------------------------------------------------------------------

    @Test
    void money_storesCorrectCents() {
        Money m = new Money(250);
        assertEquals(250, m.getCents());
    }

    @Test
    void money_getDollarsIsCorrect() {
        Money m = Money.of(3.75);
        assertEquals(3.75, m.getDollars(), 0.001);
    }

    @Test
    void money_toStringFormatsCorrectly() {
        Money m = Money.of(2.50);
        assertEquals("$2.50", m.toString());
    }

    @Test
    void money_additionWorks() {
        Money a = Money.of(1.00);
        Money b = Money.of(2.50);
        assertEquals(Money.of(3.50), a.add(b));
    }

    // ------------------------------------------------------------------
    // Customer registration tests
    // ------------------------------------------------------------------

    @Test
    void register_customerAddedToOffice() {
        Address addr = new Address("1 Elm St", "Springfield", "IL", "62701");
        Customer c = office.register("Alice Smith", addr, "555-0100");

        assertNotNull(c);
        assertEquals("Alice Smith", c.getName());
        assertEquals(1, office.getCustomers().size());
    }

    @Test
    void getCustomer_returnsCorrectCustomer() {
        Address addr = new Address("2 Oak Ave", "Springfield", "IL", "62701");
        office.register("Bob Jones", addr, "555-0200");

        Customer found = office.getCustomer("Bob Jones");
        assertNotNull(found);
        assertEquals("Bob Jones", found.getName());
    }

    @Test
    void getCustomer_returnsNullWhenNotFound() {
        assertNull(office.getCustomer("Nobody"));
    }

    // ------------------------------------------------------------------
    // Car registration / permit tests
    // ------------------------------------------------------------------

    @Test
    void register_carIssuesPermit() {
        Address addr = new Address("3 Pine Rd", "Springfield", "IL", "62701");
        Customer c = office.register("Carol White", addr, "555-0300");

        ParkingPermit permit = office.register(c, "IL-CAROL-01", CarType.COMPACT);

        assertNotNull(permit);
        assertEquals("IL-CAROL-01", permit.getCar().getLicense());
        assertEquals(CarType.COMPACT, permit.getCar().getType());
        assertEquals(c.getCustomerId(), permit.getCar().getOwnerId());
        assertEquals(1, office.getCars().size());
        assertEquals(1, office.getPermits().size());
    }

    @Test
    void carType_enumValuesExist() {
        // Ensure all expected enum values are present
        assertNotNull(CarType.valueOf("COMPACT"));
        assertNotNull(CarType.valueOf("SUV"));
        assertNotNull(CarType.valueOf("TRUCK"));
        assertNotNull(CarType.valueOf("ELECTRIC"));
        assertNotNull(CarType.valueOf("MOTORCYCLE"));
        assertNotNull(CarType.valueOf("HANDICAPPED"));
    }

    // ------------------------------------------------------------------
    // ParkingLot / entry / charge tests
    // ------------------------------------------------------------------

    @Test
    void lotEntry_generatesCharge() {
        Address addr = new Address("4 Maple Ln", "Springfield", "IL", "62701");
        Customer c = office.register("Dave Green", addr, "555-0400");
        ParkingPermit permit = office.register(c, "IL-DAVE-01", CarType.SUV);

        ParkingCharge charge = lot.enter(permit);

        assertNotNull(charge);
        assertEquals(permit.getPermitId(), charge.getPermitId());
        assertEquals("LOT-001", charge.getLotId());
        assertEquals(Money.of(2.50), charge.getAmount());
    }

    @Test
    void lotEntry_recordedAfterAddCharge() {
        Address addr = new Address("5 Cedar Ct", "Springfield", "IL", "62701");
        Customer c = office.register("Eve Black", addr, "555-0500");
        ParkingPermit permit = office.register(c, "IL-EVE-01", CarType.ELECTRIC);

        ParkingCharge charge = lot.enter(permit);
        assertNotNull(charge);
        Money chargedAmount = office.addCharge(charge);

        assertEquals(Money.of(2.50), chargedAmount);
        assertEquals(1, office.getCharges().size());
    }

    @Test
    void getTotalCharges_sumsMultipleCharges() {
        Address addr = new Address("6 Birch Blvd", "Springfield", "IL", "62701");
        Customer c = office.register("Frank Red", addr, "555-0600");
        ParkingPermit permit = office.register(c, "IL-FRANK-01", CarType.TRUCK);

        // Enter lot three times
        for (int i = 0; i < 3; i++) {
            ParkingCharge ch = lot.enter(permit);
            assertNotNull(ch);
            office.addCharge(ch);
            lot.exit(permit);   // exit so we can re-enter
        }

        Money total = office.getTotalCharges(permit.getPermitId());
        assertEquals(Money.of(7.50), total);
    }

    @Test
    void lot_fullCapacity_returnsNull() {
        Address lotAddr = new Address("7 Tiny Ln", "Springfield", "IL", "62701");
        ParkingLot tinyLot = new ParkingLot("LOT-TINY", "Tiny Lot", lotAddr, 1, Money.of(1.00));
        office.addLot(tinyLot);

        Address addr1 = new Address("7a St", "Springfield", "IL", "62701");
        Address addr2 = new Address("7b St", "Springfield", "IL", "62701");
        Customer c1 = office.register("G One", addr1, "555-0701");
        Customer c2 = office.register("G Two", addr2, "555-0702");

        ParkingPermit p1 = office.register(c1, "IL-G1", CarType.COMPACT);
        ParkingPermit p2 = office.register(c2, "IL-G2", CarType.COMPACT);

        assertNotNull(tinyLot.enter(p1));   // first car fits
        assertNull(tinyLot.enter(p2));      // lot is full
    }

    // ------------------------------------------------------------------
    // ParkingCharge toString test
    // ------------------------------------------------------------------

    @Test
    void parkingCharge_toStringContainsExpectedFields() {
        ParkingCharge charge = new ParkingCharge("PERMIT-123", "LOT-001", Money.of(5.00));
        String s = charge.toString();
        assertTrue(s.contains("PERMIT-123"));
        assertTrue(s.contains("LOT-001"));
        assertTrue(s.contains("$5.00"));
    }
}
