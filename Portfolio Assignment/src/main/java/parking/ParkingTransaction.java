package parking;

import java.util.Date;

/**
 * Records a single parking event — one permit parked in one lot on one date.
 */
public class ParkingTransaction {
    private static int nextId = 9000;

    private final String transactionId;
    private final Date date;
    private final ParkingPermit permit;
    private final ParkingLot lot;
    private final Money charge;

    public ParkingTransaction(Date date, ParkingPermit permit, ParkingLot lot, Money charge) {
        this.transactionId = "TXN-" + nextId++;
        this.date          = date;
        this.permit        = permit;
        this.lot           = lot;
        this.charge        = charge;
    }

    public String getTransactionId()  { return transactionId; }
    public Date getDate()             { return new Date(date.getTime()); }
    public ParkingPermit getPermit()  { return permit; }
    public ParkingLot getLot()        { return lot; }
    public Money getCharge()          { return charge; }

    @Override
    public String toString() {
        return "ParkingTransaction[id=" + transactionId
                + ", permit=" + permit.getPermitId()
                + ", lot=" + lot.getLotId()
                + ", charge=" + charge + "]";
    }
}
