package reserves.internal.sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import reserves.internal.reserve.Reserve;
import reserves.internal.tariff.Tariff;

@Entity
@Table(name="T_SALE")
public class Sale {

    public enum SaleChannel {
        BOOKING,
        EXPEDIA,
        AIRBNB, 
        WHATSAPP,
        SOCIAL_MEDIA,
        TELEPHONE,
        IN_PERSON, 
        OTHER
    }

    public enum PaymentMethod {
        CREDIT_CARD,
        DEBIT_CARD,
        CASH,
        PIX,
        OTHER
    }

    public enum SaleStatus {
        ANALYZING,
        PAID,
        CANCELED,
        RESERVED,
        PARTIAL_PAYMENT,
        DECLINED_PAYMENT
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name="SALE_STATUS")
    @Enumerated(EnumType.STRING) 
    private SaleStatus status;

    @Enumerated(EnumType.STRING)
    private SaleChannel channel;
    
    private String channelId; //  operation number generated by the channel, important to interact with in Reserves sites (booking, expedia, etc...) API's

    @OneToOne
    @JoinColumn(name="TARIFF_ID")
    private Tariff tariff;

    private BigDecimal taxes;

    @Column(name="PARTIAL_PAYMENTS")
    private BigDecimal partialPayments;

    private BigDecimal discounts;

    @Column(name="DAILY_PRICE")
    private BigDecimal dailyPrice;

    private String observations;

    @OneToMany
    @JoinColumn(name="RESERVE_ID")
    private List<Reserve> reserves;

    public Sale(List<Reserve> reserves) {
        this.reserves = reserves;
    }

    public Sale(){
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public SaleStatus getSaleStatus() {
        return status;
    }

    public void setSaleStatus(SaleStatus status) {
        this.status = status;
    }

    public SaleChannel getSaleChannel() {
        return channel;
    }

    public void setSaleChannel(SaleChannel channel) {
        this.channel = channel;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public BigDecimal getPartialPayments() {
        return partialPayments;
    }

    public void setPartialPayments(BigDecimal partialPayments) {
        this.partialPayments = partialPayments;
    }

    public BigDecimal getDiscounts() {
        return discounts;
    }

    public void setDiscounts(BigDecimal discounts) {
        this.discounts = discounts;
    }

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public List<Reserve> getReserves() {
        return reserves;
    }

    public void setReserves(List<Reserve> reserves) {
        this.reserves = reserves;
    }

    public void addReserve(Reserve reserve) {
        this.reserves.add(reserve);
    }

    public void removeReserve(Long reserveId) {
        this.reserves.removeIf(reserve -> (Objects.equals(reserve.getId(), reserveId)));
    }
}
