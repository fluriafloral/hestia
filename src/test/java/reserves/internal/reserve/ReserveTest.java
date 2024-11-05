package reserves.internal.reserve;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reserves.internal.guest.Guest;
import reserves.internal.room.Room;
import reserves.internal.roomType.RoomType;

public class ReserveTest {
    
    private Reserve reserve;

    private final LocalDate startDate = LocalDate.of(2000, 01, 01);
    private final LocalDate endDate = LocalDate.of(2000, 01, 02);
    private final List<Guest> guestList = new ArrayList<>();
    private final Guest guest = new Guest("John Doe", "123456789");
    private final Room room = new Room("1", new RoomType("shared standard", true), 6);

    @BeforeEach
    public void setUp() {
        guestList.add(guest);
        reserve = new Reserve(startDate, endDate, room, guestList);
    }

    @Test
    void testConstructorFailNullStartDate() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reserve = new Reserve(null, endDate, room, guestList);
        });

        assertTrue(exception.getMessage().contains("start date missing"));
    }

    @Test
    void testConstructorFailNullEndDate() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reserve = new Reserve(startDate, null, room, guestList);
        });

        assertTrue(exception.getMessage().contains("end date missing"));
    }

    @Test 
    void testConstructorFailInvalidEndDate() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reserve = new Reserve(endDate, startDate, room, guestList);
        });

        assertTrue(exception.getMessage().contains("end date before start date"));
    }

    @Test
    void testConstructorFailNullRoom() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reserve = new Reserve(startDate, endDate, null, guestList);
        });

        assertTrue(exception.getMessage().contains("room missing"));
    }

    @Test
    void testConstructorFailNullGuestList() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reserve = new Reserve(startDate, endDate, room, null);
        });

        assertTrue(exception.getMessage().contains("guests list missing"));
    }

    @Test 
    void testReserveAddGuest() {

        Guest newGuest = new Guest("Jane Doe", "987654321");

        reserve.addGuest(newGuest);
        
        assertThat(reserve.getGuests()).contains(newGuest);
    }

    @Test 
    void testReserveRemoveGuest() {
        
        reserve.removeGuest("123456789");

        assertThat(reserve.getGuests()).doesNotContain(guest);
    }
}
