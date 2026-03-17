package parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ParkingOffice.
 *
 * Tests verify: customer registration, car/permit registration,
 * parking transactions, and charge calculations.
 */
class ParkingOfficeTest {

    private ParkingOffice office;
    private Address officeAddress;
    private ParkingLot lotA;
    private Customer alice;
    private Car compactCar;
    private Car suvCar;
    private Car evCar;

    @BeforeEach
    void setUp() {
        officeAddress = new Address("1 Campus Dr", "Denver", "CO", "80210");
        office = new ParkingOffice("DU Parking Office", officeAddress);

        Address lotAddress = new Address("2 Lot Ave", "Denver", "CO", "80210");
        lotA = new ParkingLot("LOT-A", "Main Lot", lotAddress, 100, new Money(10.00));

        office.addParkingLot(lotA);

        alice = new Customer("Alice Smith", "303-555-0100",
                new Address("3 Oak St", "Denver", "CO", "80220"));

        compactCar = new Car("ABC-1234", CarType.COMPACT);
        suvCar     = new Car("XYZ-9999", CarType.SUV);
        evCar      = new Car("EV-0001",  CarType.ELECTRIC);
    }

    // ------------------------------------------------------------------ name
    @Test
    @DisplayName("getParkingOfficeName returns correct name")
    void testGetParkingOfficeName() {
        assertEquals("DU Parking Office", office.getParkingOfficeName());
    }

    // ---------------------------------------------------------- registration
    @Test
    @DisplayName("register(Customer) adds customer to list")
    void testRegisterCustomer() {
        office.register(alice);
        assertTrue(office.getListOfCustomers().contains(alice));
    }

    @Test
    @DisplayName("registering same customer twice does not duplicate")
    void testRegisterCustomerNoDuplicate() {
        office.register(alice);
        office.register(alice);
        assertEquals(1, office.getListOfCustomers().size());
    }

    @Test
    @DisplayName("register(Customer, Car) returns a valid ParkingPermit")
    void testRegisterCarReturnsPermit() {
        office.register(alice);
        ParkingPermit permit = office.register(alice, compactCar);
        assertNotNull(permit);
        assertEquals(alice, permit.getCustomer());
        assertEquals(compactCar, permit.getCar());
    }

    @Test
    @DisplayName("register(Customer, Car) adds car to customer")
    void testRegisterCarAddedToCustomer() {
        office.register(alice);
        office.register(alice, compactCar);
        assertTrue(alice.getCars().contains(compactCar));
    }

    @Test
    @DisplayName("register car for unregistered customer throws exception")
    void testRegisterCarUnregisteredCustomerThrows() {
        assertThrows(IllegalArgumentException.class, () ->
            office.register(alice, compactCar)
        );
    }

    // ------------------------------------------------------------- parking
    @Test
    @DisplayName("park returns a non-null ParkingTransaction")
    void testParkReturnsTransaction() {
        office.register(alice);
        ParkingPermit permit = office.register(alice, compactCar);
        ParkingTransaction txn = office.park(new Date(), permit, lotA);
        assertNotNull(txn);
    }

    @Test
    @DisplayName("park transaction has correct permit and lot")
    void testParkTransactionDetails() {
        office.register(alice);
        ParkingPermit permit = office.register(alice, compactCar);
        ParkingTransaction txn = office.park(new Date(), permit, lotA);
        assertEquals(permit, txn.getPermit());
        assertEquals(lotA,   txn.getLot());
    }

    // ---------------------------------------------------- charge calculation
    @Test
    @DisplayName("compact car charged base rate $10.00")
    void testCompactCarCharge() {
        office.register(alice);
        ParkingPermit permit = office.register(alice, compactCar);
        office.park(new Date(), permit, lotA);
        assertEquals(new Money(10.00), office.getParkingCharges(permit));
    }

    @Test
    @DisplayName("SUV car charged 150% of base rate = $15.00")
    void testSuvCarCharge() {
        office.register(alice);
        ParkingPermit permit = office.register(alice, suvCar);
        office.park(new Date(), permit, lotA);
        assertEquals(new Money(15.00), office.getParkingCharges(permit));
    }

    @Test
    @DisplayName("Electric car charged 80% of base rate = $8.00")
    void testElectricCarCharge() {
        office.register(alice);
        ParkingPermit permit = office.register(alice, evCar);
        office.park(new Date(), permit, lotA);
        assertEquals(new Money(8.00), office.getParkingCharges(permit));
    }

    @Test
    @DisplayName("multiple parks accumulate charges correctly")
    void testMultipleParkingCharges() {
        office.register(alice);
        ParkingPermit permit = office.register(alice, compactCar);
        office.park(new Date(), permit, lotA);
        office.park(new Date(), permit, lotA);
        office.park(new Date(), permit, lotA);
        assertEquals(new Money(30.00), office.getParkingCharges(permit));
    }

    @Test
    @DisplayName("getParkingCharges(Customer) sums across all permits")
    void testCustomerChargesAcrossPermits() {
        office.register(alice);
        ParkingPermit p1 = office.register(alice, compactCar);
        ParkingPermit p2 = office.register(alice, suvCar);
        office.park(new Date(), p1, lotA);  // $10
        office.park(new Date(), p2, lotA);  // $15
        assertEquals(new Money(25.00), office.getParkingCharges(alice));
    }

    @Test
    @DisplayName("getParkingCharges returns zero when no parks recorded")
    void testZeroChargesWhenNoParking() {
        office.register(alice);
        ParkingPermit permit = office.register(alice, compactCar);
        assertEquals(new Money(0.00), office.getParkingCharges(permit));
    }

    // ------------------------------------------------------------ lot list
    @Test
    @DisplayName("parking lot is added to office lot list")
    void testParkingLotAdded() {
        assertTrue(office.getListOfParkingLots().contains(lotA));
    }
}
