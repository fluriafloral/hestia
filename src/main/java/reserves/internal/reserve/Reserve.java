package reserves.internal.reserve;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import reserves.internal.guest.Guest;
import reserves.internal.room.Room;

@Entity
@Table(name="T_RESERVE")
public class Reserve {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name="CHECK_IN", nullable=false)
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    @Column(name="CHECK_OUT", nullable=false)
    private LocalDate endDate;

    @OneToMany
    @JoinColumn(name="GUEST_ID")
    private List<Guest> guests;

    @ManyToOne
    @JoinColumn(name="ROOM_ID", nullable=false, unique=false)
    private Room room;

    public Reserve(LocalDate startDate, LocalDate endDate, Room room, List<Guest> guests) {

        if (startDate == null) { throw new IllegalArgumentException("start date missing"); }
        if (endDate == null) { throw new IllegalArgumentException("end date missing"); }
        if (endDate.isBefore(startDate)) { throw new IllegalArgumentException("end date before start date"); }
        if (room == null) { throw new IllegalArgumentException("room missing"); }
        if (guests == null) { throw new IllegalArgumentException("guests list missing"); }

        this.startDate = startDate;
        this.endDate = endDate;
        this.room = room;
        this.guests = guests;
    }

    public Reserve(LocalDate startDate, LocalDate endDate, Room room) {

        if (startDate == null) { throw new IllegalArgumentException("start date missing"); }
        if (endDate == null) { throw new IllegalArgumentException("end date missing"); }
        if (endDate.isBefore(startDate)) { throw new IllegalArgumentException("end date before start date"); }
        if (room == null || guests.isEmpty()) { throw new IllegalArgumentException("room missing"); }

        this.startDate = startDate;
        this.endDate = endDate;
        this.room = room;
    } 

    public Reserve(Long id) {
        this.id = id;
    }

    public Reserve() {
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    public void addGuest(Guest guest) {
        guests.add(guest);
    }

    public void removeGuest(String documentNumber) {
        guests.removeIf(guest -> guest.getDocumentNumber().equals(documentNumber));
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
