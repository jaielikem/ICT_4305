package parking;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Immutable value object representing a monetary amount in USD.
 */
public class Money {
    private final BigDecimal amount;

    public Money(double amount) {
        this.amount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public Money(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public BigDecimal getAmount() { return amount; }

    @Override
    public String toString() {
        return "$" + amount.toPlainString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Money)) return false;
        Money other = (Money) obj;
        return this.amount.compareTo(other.amount) == 0;
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }
}
