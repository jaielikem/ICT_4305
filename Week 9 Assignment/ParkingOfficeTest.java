package parking;

import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Parking Office system.
 * Covers: customer/permit registration, getCustomerIds, getPermitIds,
 * getPermitIds(Customer), charge calculation, equals/hashCode.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ParkingOfficeTest {

    private ParkingOffice office;
    private Customer alice;
    private Customer bob;
    private Car aliceSuv;
    private Car aliceCompact;
    private Car bobSuv;
    private ParkingLot entryExitLot;
    private ParkingLot entryOnlyLot;

    @BeforeEach
    void setUp() {
        office = new ParkingOffice("University Parking Office");

        alice = new Customer("C001", "Alice Smith", "alice@uni.edu");
        bob   = new Customer("C002", "Bob Jones",  "bob@uni.edu");
        office.addCustomer(alice);
        office.addCustomer(bob);

        aliceSuv     = new Car("ABC-111", CarType.SUV);
        aliceCompact = new Car("ABC-222", CarType.COMPACT);
        bobSuv       = new Car("XYZ-999", CarType.SUV);

        entryExitLot = new ParkingLot("LOT-A", "Main Lot",  2.00, 10.00, ParkingLot.ScanMode.ENTRY_EXIT);
        entryOnlyLot = new ParkingLot("LOT-B", "North Lot", 1.50,  8.00, ParkingLot.ScanMode.ENTRY_ONLY);
        office.addLot(entryExitLot);
        office.addLot(entryOnlyLot);
    }

    // -----------------------------------------------------------------------
    // Customer registration
    // -----------------------------------------------------------------------

    @Test @Order(1)
    void testGetCustomerIds_returnsAllRegisteredCustomers() {
        Collection<String> ids = office.getCustomerIds();
        assertEquals(2, ids.size());
        assertTrue(ids.contains("C001"));
        assertTrue(ids.contains("C002"));
    }

    @Test @Order(2)
    void testGetCustomerIds_emptyOffice() {
        ParkingOffice empty = new ParkingOffice("Empty Office");
        assertTrue(empty.getCustomerIds().isEmpty());
    }

    // -----------------------------------------------------------------------
    // Permit registration
    // -----------------------------------------------------------------------

    @Test @Order(3)
    void testGetPermitIds_returnsAllPermits() {
        office.addPermit(alice, aliceSuv);
        office.addPermit(alice, aliceCompact);
        office.addPermit(bob, bobSuv);

        Collection<String> ids = office.getPermitIds();
        assertEquals(3, ids.size());
    }

    @Test @Order(4)
    void testGetPermitIds_customer_returnsOnlyThatCustomersPermits() {
        ParkingPermit p1 = office.addPermit(alice, aliceSuv);
        ParkingPermit p2 = office.addPermit(alice, aliceCompact);
        office.addPermit(bob, bobSuv);

        Collection<String> alicePermits = office.getPermitIds(alice);
        assertEquals(2, alicePermits.size());
        assertTrue(alicePermits.contains(p1.getPermitId()));
        assertTrue(alicePermits.contains(p2.getPermitId()));
    }

    @Test @Order(5)
    void testGetPermitIds_customer_noPermitsReturnsEmpty() {
        Collection<String> alicePermits = office.getPermitIds(alice);
        assertTrue(alicePermits.isEmpty());
    }

    // -----------------------------------------------------------------------
    // equals / hashCode
    // -----------------------------------------------------------------------

    @Test @Order(6)
    void testCustomerEquals_sameId() {
        Customer duplicate = new Customer("C001", "Alice Other Name", "other@uni.edu");
        assertEquals(alice, duplicate);
        assertEquals(alice.hashCode(), duplicate.hashCode());
    }

    @Test @Order(7)
    void testCustomerEquals_differentId() {
        assertNotEquals(alice, bob);
    }

    @Test @Order(8)
    void testCarEquals_sameLicensePlate() {
        Car duplicate = new Car("ABC-111", CarType.COMPACT); // same plate, different type
        assertEquals(aliceSuv, duplicate);
        assertEquals(aliceSuv.hashCode(), duplicate.hashCode());
    }

    @Test @Order(9)
    void testPermitEquals_samePermitId() {
        ParkingPermit p1 = office.addPermit(alice, aliceSuv);
        ParkingPermit same = new ParkingPermit(p1.getPermitId(), bob, bobSuv);
        assertEquals(p1, same);
        assertEquals(p1.hashCode(), same.hashCode());
    }

    @Test @Order(10)
    void testParkingLotEquals_sameLotId() {
        ParkingLot duplicate = new ParkingLot("LOT-A", "Different Name", 5.00, 20.00, ParkingLot.ScanMode.ENTRY_ONLY);
        assertEquals(entryExitLot, duplicate);
    }

    // -----------------------------------------------------------------------
    // Charge calculation — ENTRY_EXIT lot
    // -----------------------------------------------------------------------

    @Test @Order(11)
    void testEntryExitLot_suvCharge() {
        // 2 hours @ $2.00/hr = $4.00
        double charge = entryExitLot.calculateCharge(CarType.SUV, 2.0, 0);
        assertEquals(4.00, charge, 0.001);
    }

    @Test @Order(12)
    void testEntryExitLot_compactDiscount() {
        // 2 hours @ $2.00/hr = $4.00 → 20% off = $3.20
        double charge = entryExitLot.calculateCharge(CarType.COMPACT, 2.0, 0);
        assertEquals(3.20, charge, 0.001);
    }

    // -----------------------------------------------------------------------
    // Charge calculation — ENTRY_ONLY lot
    // -----------------------------------------------------------------------

    @Test @Order(13)
    void testEntryOnlyLot_noOvernightSuvCharge() {
        // Entry: $1.50, 0 overnight days
        double charge = entryOnlyLot.calculateCharge(CarType.SUV, 0, 0);
        assertEquals(1.50, charge, 0.001);
    }

    @Test @Order(14)
    void testEntryOnlyLot_overnightSuvCharge() {
        // Entry: $1.50 + 2 overnight @ $8.00 = $17.50
        double charge = entryOnlyLot.calculateCharge(CarType.SUV, 0, 2);
        assertEquals(17.50, charge, 0.001);
    }

    @Test @Order(15)
    void testEntryOnlyLot_compactDiscount() {
        // ($1.50 + $8.00) * 0.80 = $7.60
        double charge = entryOnlyLot.calculateCharge(CarType.COMPACT, 0, 1);
        assertEquals(7.60, charge, 0.001);
    }

    // -----------------------------------------------------------------------
    // Full parking flow
    // -----------------------------------------------------------------------

    @Test @Order(16)
    void testParkAndExit_entryExitLot_correctCharge() {
        ParkingPermit permit = office.addPermit(alice, aliceSuv);
        LocalDateTime entry = LocalDateTime.of(2024, 3, 1, 9, 0);
        LocalDateTime exit  = LocalDateTime.of(2024, 3, 1, 11, 0); // 2 hours

        ParkingTransaction tx = office.park(permit, entryExitLot, entry);
        office.exit(tx, exit);

        assertEquals(4.00, tx.getCharge(), 0.001); // 2hr * $2.00
    }

    @Test @Order(17)
    void testTotalCharges_multipleTransactions() {
        ParkingPermit permit = office.addPermit(alice, aliceSuv);

        LocalDateTime entry1 = LocalDateTime.of(2024, 3, 1, 9, 0);
        LocalDateTime exit1  = LocalDateTime.of(2024, 3, 1, 11, 0); // 2 hrs → $4.00
        ParkingTransaction tx1 = office.park(permit, entryExitLot, entry1);
        office.exit(tx1, exit1);

        LocalDateTime entry2 = LocalDateTime.of(2024, 3, 2, 8, 0);
        LocalDateTime exit2  = LocalDateTime.of(2024, 3, 2, 10, 0); // 2 hrs → $4.00
        ParkingTransaction tx2 = office.park(permit, entryExitLot, entry2);
        office.exit(tx2, exit2);

        assertEquals(8.00, office.getTotalCharges(alice), 0.001);
    }

    @Test @Order(18)
    void testTotalCharges_onlyBillsCorrectCustomer() {
        ParkingPermit alicePermit = office.addPermit(alice, aliceSuv);
        ParkingPermit bobPermit   = office.addPermit(bob, bobSuv);

        LocalDateTime entry = LocalDateTime.of(2024, 3, 1, 9, 0);
        LocalDateTime exit  = LocalDateTime.of(2024, 3, 1, 10, 0); // 1 hr → $2.00

        ParkingTransaction aliceTx = office.park(alicePermit, entryExitLot, entry);
        office.exit(aliceTx, exit);

        ParkingTransaction bobTx = office.park(bobPermit, entryExitLot, entry);
        office.exit(bobTx, exit);

        assertEquals(2.00, office.getTotalCharges(alice), 0.001);
        assertEquals(2.00, office.getTotalCharges(bob),   0.001);
    }
}
