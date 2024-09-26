package reserves.internal.guest;

import java.util.List;
import java.util.Optional;

public interface GuestService {

    Guest saveGuest(Guest guest);
    List<Guest> findAllGuests();
    Optional<Guest> findGuestById(Long id);
    Optional<Guest> findGuestByDocumentNumber(String documentNumber);
    Guest updateGuest(Guest guest);
    void deleteGuest(Long id);
}
