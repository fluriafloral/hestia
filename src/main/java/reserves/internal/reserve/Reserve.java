package reserves.internal.reserve;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @Column(name="CHECK_IN")
    private LocalDate start;

    @Temporal(TemporalType.DATE)
    @Column(name="CHECK_OUT")
    private LocalDate end;

    @OneToMany
    @JoinColumn(name="GUEST_ID")
    private List<Guest> guests;

    @JoinColumn(name="ROOM_ID")
    private Room room;

    public Reserve(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public Reserve(List<Guest> guests) {
        this.guests = guests;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
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
