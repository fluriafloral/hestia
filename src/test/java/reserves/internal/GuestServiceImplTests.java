package reserves.internal;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import reserves.internal.guest.Guest;
import reserves.internal.guest.Guest.DocumentType;
import reserves.internal.guest.GuestRepository;

@ExtendWith(MockitoExtension.class)
public class GuestServiceImplTests {

    @InjectMocks
    private GuestServiceImpl guestService;

    @Mock
    private GuestRepository guestRepo;

    private Guest guest;

    @BeforeEach
    public void setUp() {
        guest = new Guest("John Doe", "1234567890", DocumentType.CPF);
    }

    @Test
    void testSaveGuestSuccess() {

        when(guestRepo.save(guest)).thenReturn(guest);

        Guest savedGuest = guestService.saveGuest(guest);

        assertThat(savedGuest).isNotNull();
        assertEquals(guest.getDocumentNumber(), savedGuest.getDocumentNumber());
    }

    @Test
    void testSaveGuestFailEmptyDocumentNumber() {

        Guest guestEmptyDocumentNumber = new Guest("John Doe", "");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            guestService.saveGuest(guestEmptyDocumentNumber);
        });

        verify(guestRepo, never()).save(any(Guest.class));
        assertTrue(exception.getMessage().contains("Document Number cannot be empty"));
    }

    @Test
    void testSaveGuestFailAlreadyExists() {

        Guest guestAlreadyExists = new Guest("John Doe", "1234567890");

        when(guestRepo.existsByDocumentNumber(guestAlreadyExists.getDocumentNumber())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            guestService.saveGuest(guestAlreadyExists);
        });

        verify(guestRepo, never()).save(any(Guest.class));
        assertTrue(exception.getMessage().contains("Document Number already declared"));
    }

    @Test
    void testFindAllGuests() {

        Guest guest1 = new Guest("Jane Doe", "0000000000");

        when(guestRepo.findAll()).thenReturn(List.of(guest, guest1));

        List<Guest> guestList = guestService.findAllGuests();

        assertThat(guestList).isNotNull();
        assertEquals(guestList.size(), 2);
    }

    @Test
    void testFindGuestByDocumentNumberSuccess() {

        when(guestRepo.findByDocumentNumber(guest.getDocumentNumber())).thenReturn(Optional.of(guest));
    
        Guest retrievedGuest = guestService.findGuestByDocumentNumber(guest.getDocumentNumber()).get();
        
        assertThat(retrievedGuest).isNotNull();
        assertEquals(guest.getDocumentNumber(), retrievedGuest.getDocumentNumber());
    }

    @Test
    void testFindGuestByDocumentNumberFail() {

        String documentNumberNotFound = "-1";
        given(guestRepo.findByDocumentNumber(documentNumberNotFound)).isEmpty();
        
        Optional<Guest> retrievedGuest = guestService.findGuestByDocumentNumber(documentNumberNotFound);

        assertThat(retrievedGuest).isEmpty();
    }

    @Test
    void testFindGuestByIdSuccess() {

        Long guestId = guest.getId();
        when(guestRepo.findById(guestId)).thenReturn(Optional.of(guest));

        Guest retrievedGuest = guestService.findGuestById(guestId).get();

        assertThat(retrievedGuest).isNotNull();
        assertEquals(guestId, retrievedGuest.getId());
    }

    @Test
    void testFindGuestByIdFail() {

        Long guestId = -1L;
        when(guestRepo.findById(guestId)).thenReturn(Optional.empty());

        Optional<Guest> retrievedGuest = guestService.findGuestById(guestId);

        assertThat(retrievedGuest).isEmpty();
    }

    @Test
    void testUpdateGuest() {

        String nameUpdate = "John Doe Update";
        when(guestRepo.save(guest)).thenReturn(guest);
        guest.setName(nameUpdate);

        Guest updatedGuest = guestService.updateGuest(guest);

        assertEquals(guest.getName(), updatedGuest.getName());
    }

    @Test
    void testDeleteGuest() {

        Long guestId = guest.getId();
        willDoNothing().given(guestRepo).deleteById(guestId);

        guestService.deleteGuest(guestId);

        verify(guestRepo, times(1)).deleteById(guestId);
        assertThat(guestRepo.existsByDocumentNumber(guest.getDocumentNumber())).isFalse();
    }
}
