package reserves.internal.guest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class GuestRepositoryTest {

    @Autowired
    private GuestRepository guestRepo;

    @Test
    void testFindByDocumentNumber() {
        Guest guest = new Guest();
        guest.setDocumentNumber("123456789");

        assertEquals(guest.getDocumentNumber(), guestRepo.findByDocumentNumber("123456789").getDocumentNumber());
    }
}
