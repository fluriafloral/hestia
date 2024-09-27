package reserves.internal.tariff;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="T_TARIFF")
public class Tariff implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="TARIFF_NAME", nullable=false)
    private String name;

    @Column(name="BEGINNING_DATE", nullable=false)
    private LocalDate beginningDate;

    @Column(name="END_DATE", nullable=false)
    private LocalDate endDate;

    @Column(name="MINIMAL_DAYS")
    private int minimalAmountOfDays;

    @Column(name="MAXIMUM_DAYS")
    private int maximalAmountOfDays;

    public Tariff(String name, LocalDate beginningDate, LocalDate endDate) {
        this.name = name;
        this.beginningDate = beginningDate;
        this.endDate = endDate;
    }

    public Tariff() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(LocalDate beginningDate) {
        this.beginningDate = beginningDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getMinimalAmountOfDays() {
        return minimalAmountOfDays;
    }

    public void setMinimalAmountOfDays(int minimalAmountOfDays) {
        this.minimalAmountOfDays = minimalAmountOfDays;
    }

    public int getMaximalAmountOfDays() {
        return maximalAmountOfDays;
    }

    public void setMaximalAmountOfDays(int maximalAmountOfDays) {
        this.maximalAmountOfDays = maximalAmountOfDays;
    }
}
