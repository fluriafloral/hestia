package reserves.dailyRate;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import reserves.internal.roomType.RoomType;
import reserves.internal.tariff.Tariff;

@Entity
@Table(name="T_DAILY_RATE")
public class DailyRate {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="ROOM_TYPE_ID")
    private RoomType roomType;

    @ManyToOne
    @JoinColumn(name="TARIFF_ID")
    private Tariff tariff;

    @Column(name="BASE_VALUE")
    private BigDecimal baseValue;

    @Column(name="INCREASE_PER_GUEST")
    private BigDecimal increasePerGuest;

    @Column(name="INCREASE_PER_CHILDREN")
    private BigDecimal increasePerChildren;

    public DailyRate() {
    }

    public Long getId() {
        return id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public BigDecimal getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(BigDecimal baseValue) {
        this.baseValue = baseValue;
    }

    public BigDecimal getIncreasePerGuest() {
        return increasePerGuest;
    }

    public void setIncreasePerGuest(BigDecimal increasePerGuest) {
        this.increasePerGuest = increasePerGuest;
    }

    public BigDecimal getIncreasePerChildren() {
        return increasePerChildren;
    }

    public void setIncreasePerChildren(BigDecimal increasePerChildren) {
        this.increasePerChildren = increasePerChildren;
    }
}
