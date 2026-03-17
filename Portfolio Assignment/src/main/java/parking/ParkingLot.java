package parking;

import java.math.BigDecimal;

/**
 * Represents a physical parking lot with a daily rate.
 */
public class ParkingLot {
    private final String lotId;
    private final String lotName;
    private final Address address;
    private final int capacity;
    private Money dailyRate;

    public ParkingLot(String lotId, String lotName, Address address, int capacity, Money dailyRate) {
        this.lotId     = lotId;
        this.lotName   = lotName;
        this.address   = address;
        this.capacity  = capacity;
        this.dailyRate = dailyRate;
    }

    /**
     * Calculates parking charge for a given car type.
     * SUVs pay a 50 % surcharge; EVs receive a 20 % discount.
     */
    public Money calculateCharge(CarType carType) {
        BigDecimal base = dailyRate.getAmount();
        BigDecimal charge;
        switch (carType) {
            case SUV:      charge = base.multiply(BigDecimal.valueOf(1.50)); break;
            case ELECTRIC: charge = base.multiply(BigDecimal.valueOf(0.80)); break;
            default:       charge = base;
        }
        return new Money(charge);
    }

    public String getLotId()       { return lotId; }
    public String getLotName()     { return lotName; }
    public Address getAddress()    { return address; }
    public int getCapacity()       { return capacity; }
    public Money getDailyRate()    { return dailyRate; }

    public void setDailyRate(Money dailyRate) { this.dailyRate = dailyRate; }

    @Override
    public String toString() {
        return "ParkingLot[id=" + lotId + ", name=" + lotName + "]";
    }
}
