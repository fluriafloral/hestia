package reserves.internal.reserve;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reserves.internal.guest.Guest;

public class ReserveTest {
    
    private Reserve reserve;

    private Guest newGuest;

    @BeforeEach
    public void setUp() {
        reserve = new Reserve(new ArrayList<>());
        newGuest = new Guest("John Doe", "123456789");
    }

    @Test
    void testAddnewGuest() {
        reserve.addGuest(newGuest);
        assertTrue(reserve.getGuests().stream().anyMatch(guest -> guest.getDocumentNumber().equals(newGuest.getDocumentNumber())));
    }

    @Test
    void testRemovenewGuest() {
        reserve.removeGuest(newGuest.getDocumentNumber());
        assertTrue(reserve.getGuests().stream().noneMatch(guest -> guest.getDocumentNumber().equals(newGuest.getDocumentNumber())));
    }
}
