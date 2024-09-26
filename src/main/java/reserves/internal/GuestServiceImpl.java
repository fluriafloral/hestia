package reserves.internal;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import reserves.internal.guest.Guest;
import reserves.internal.guest.GuestRepository;
import reserves.internal.guest.GuestService;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepo;

    public GuestServiceImpl(GuestRepository guestRepo) {
        this.guestRepo = guestRepo;
    }

    @Override
    public Guest saveGuest(Guest guest) {
        if (guest.getName().isBlank()) { throw new IllegalArgumentException("Name cannot be empty"); }
        if (guest.getDocumentNumber().isBlank()) { throw new IllegalArgumentException("Document Number cannot be empty"); }
        if (guestRepo.existsByDocumentNumber(guest.getDocumentNumber())) { throw new IllegalArgumentException("Document Number already declared"); }
        if (guest.getDocumentType() == null) { throw new IllegalArgumentException("Document Type cannot be empty"); }

        return guestRepo.save(guest);
    }

    @Override
    public List<Guest> findAllGuests() {
        return guestRepo.findAll();
    }

    @Override
    public Optional<Guest> findGuestById(Long id) {
        return guestRepo.findById(id);
    }

    @Override
    public Optional<Guest> findGuestByDocumentNumber(String documentNumber) {
        return guestRepo.findByDocumentNumber(documentNumber);
    }

    @Override
    public Guest updateGuest(Guest guest) {
        if (guest.getName().isBlank()) { throw new IllegalArgumentException("Name cannot be empty"); }
        if (guest.getDocumentNumber().isBlank()) { throw new IllegalArgumentException("Document Number cannot be empty"); }
        if (guest.getDocumentType() == null) { throw new IllegalArgumentException("Document Type cannot be empty"); }

        return guestRepo.save(guest);
    }

    @Override
    public void deleteGuest(Long id) {
        guestRepo.deleteById(id);
    }
}
