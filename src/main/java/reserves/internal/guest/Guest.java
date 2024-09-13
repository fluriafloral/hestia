package reserves.internal.guest;

import java.time.LocalDate;
import java.time.Period;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/*
 * A guest hosted in the accomodation
 * 
 * A guest calculates if a person is underage, if so, they cannot stay in a shared room
 */
@Entity
@Table(name = "T_GUEST")
public class Guest {

    public enum DocumentType {
        CPF,
        RG,
        PASSPORT_NUMBER,
        OTHER
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="GUEST_NAME")
    private String name;

    @Column(name = "DOCUMENT_NUMBER")
    private String documentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "DOCUMENT_TYPE")
    private DocumentType documentType;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    private String email;

    private String telephone;

    private String address;

    public Guest(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public Guest() {
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

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isUnderage() {
        return Period.between(birthDate, LocalDate.now()).getYears() < 18;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
