package parking;

import java.time.Instant;

/**
 * Records a parking charge incurred when a car enters a parking lot.
 */
public class ParkingCharge {
    private final String permitId;
    private final String lotId;
    private final Instant incurred;
    private final Money amount;

    public ParkingCharge(String permitId, String lotId, Money amount) {
        this.permitId  = permitId;
        this.lotId     = lotId;
        this.incurred  = Instant.now();
        this.amount    = amount;
    }

    public String getPermitId()  { return permitId; }
    public String getLotId()     { return lotId; }
    public Instant getIncurred() { return incurred; }
    public Money getAmount()     { return amount; }

    @Override
    public String toString() {
        return "ParkingCharge{permitId='" + permitId + "', lotId='" + lotId +
               "', incurred=" + incurred + ", amount=" + amount + "}";
    }
}
