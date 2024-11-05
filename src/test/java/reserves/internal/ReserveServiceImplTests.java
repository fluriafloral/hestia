package reserves.internal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
import reserves.internal.reserve.Reserve;
import reserves.internal.reserve.ReserveRepository;
import reserves.internal.room.Room;
import reserves.internal.roomType.RoomType;

@ExtendWith(MockitoExtension.class)
public class ReserveServiceImplTests {

    @InjectMocks
    private ReserveServiceImpl reserveService;

    @Mock
    private ReserveRepository reserveRepo;
    
    private Reserve reserve;
    private Room room;
    private final List<Guest> guestList = new ArrayList<>();
    
    private final LocalDate startDate = LocalDate.of(2000, 1, 1);
    private final LocalDate endDate = LocalDate.of(2000, 1, 2);

    @BeforeEach
    public void setUp() {
        room = new Room("1", new RoomType("shared standard", true), 6);
        guestList.add(new Guest("John Doe", "123456789"));
        reserve = new Reserve(startDate, endDate, room, guestList);
    }

    @Test
    void testSaveReserveSuccess() {

        when(reserveRepo.save(reserve)).thenReturn(reserve);

        Reserve savedReserve = reserveService.saveReserve(reserve);

        assertThat(savedReserve).isNotNull();
        assertEquals(reserve.getStartDate(), savedReserve.getStartDate());
        assertEquals(reserve.getEndDate(), savedReserve.getEndDate());
        assertEquals(reserve.getRoom(), savedReserve.getRoom());
    }

    @Test
    void testSaveReserveFailRoomAlreadyReserved() {

        Reserve overbookingReserve = new Reserve(startDate, endDate, room, guestList);
        when(reserveRepo.saveCausesOverbooking(room, startDate, endDate)).thenReturn(true);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            reserveService.saveReserve(overbookingReserve);
        });

        verify(reserveRepo, never()).save(any(Reserve.class));
        assertTrue(exception.getMessage().contains(overbookingReserve.getRoom().getName() + " is already reserved for some of these dates"));
    }

    @Test
    void testFindAllReserves() {

        Reserve reserve1 = new Reserve(endDate, endDate.plusDays(1), room, guestList);

        when(reserveRepo.findAll()).thenReturn(List.of(reserve, reserve1));

        List<Reserve> listReserves = reserveService.findAllReserves();

        assertThat(listReserves).isNotNull();
        assertEquals(listReserves.size(), 2);
    }

    @Test
    void testFindReserveById() {

        when(reserveRepo.findById(reserve.getId())).thenReturn(Optional.of(reserve));

        Reserve retrievedReserve = reserveService.findReserveById(reserve.getId()).get();

        assertThat(retrievedReserve).isNotNull();
        assertEquals(reserve.getId(), retrievedReserve.getId());
        assertEquals(reserve.getRoom(), retrievedReserve.getRoom());
        assertEquals(reserve.getStartDate(), retrievedReserve.getStartDate());
        assertEquals(reserve.getEndDate(), retrievedReserve.getEndDate());
    }

    @Test
    void testFindReserveByStartDate() {
        
        when(reserveRepo.findByStartDate(startDate)).thenReturn(List.of(reserve));

        List<Reserve> listReserves = reserveService.findReserveByStartDate(startDate);

        assertThat(listReserves).allMatch(r -> r.getStartDate().equals(startDate));
    }

    @Test
    void testFindReserveByEndDate() {

        when(reserveRepo.findByEndDate(endDate)).thenReturn(List.of(reserve));

        List<Reserve> listReserves = reserveService.findReserveByEndDate(endDate);

        assertThat(listReserves).allMatch(r -> r.getEndDate().equals(endDate));
    }

    @Test
    void testFindReserveByPeriod() {

        Reserve reserve1 = new Reserve(endDate, endDate.plusDays(1), room, guestList);
        when(reserveRepo.findByStartDateBetween(startDate, endDate)).thenReturn(List.of(reserve, reserve1));

        List<Reserve> listReserves = reserveService.findReserveByPeriod(startDate, endDate);

        assertThat(listReserves).allMatch(r -> r.getStartDate().isAfter(startDate.minusDays(1)) && r.getStartDate().isBefore(endDate.plusDays(1)));
    }

    @Test
    void testUpdateReserveSuccess() {

        LocalDate endDateUpdate = LocalDate.of(2000, 1, 3);
        when(reserveRepo.save(reserve)).thenReturn(reserve);
        reserve.setEndDate(endDateUpdate);

        Reserve updatedReserve = reserveService.updateReserve(reserve);

        assertEquals(reserve.getId(), updatedReserve.getId());
        assertEquals(reserve.getEndDate(), updatedReserve.getEndDate());
    }


    @Test
    void testUpdateReserveFailAlreadyReserved() {

        Reserve overbookingReserve = new Reserve(endDate, endDate.plusDays(1), room, guestList);
        when(reserveRepo.updateCausesOverbooking(overbookingReserve.getId(), room, startDate, endDate.plusDays(1))).thenReturn(true);
        overbookingReserve.setStartDate(startDate);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            reserveService.updateReserve(overbookingReserve);
        });

        verify(reserveRepo, never()).save(any(Reserve.class));
        assertTrue(exception.getMessage().contains(overbookingReserve.getRoom().getName() + " is already reserved for some of these dates"));
    }

    @Test
    void testDeleteReserve() {

        Long reserveId = reserve.getId();
        willDoNothing().given(reserveRepo).deleteById(reserveId);

        reserveService.deleteReserve(reserveId);

        verify(reserveRepo, times(1)).deleteById(reserveId);
        assertThat(reserveRepo.existsById(reserveId)).isFalse();
    }
}