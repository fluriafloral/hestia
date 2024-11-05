package reserves.internal.reserve;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import reserves.internal.room.Room;
import reserves.internal.room.RoomRepository;
import reserves.internal.roomType.RoomType;
import reserves.internal.roomType.RoomTypeRepository;

@DataJpaTest
public class ReserveRepositoryTest {

    @Autowired
    private ReserveRepository reserveRepo;

    @Autowired
    private RoomTypeRepository roomTypeRepo;

    @Autowired
    private RoomRepository roomRepo;
    
    private Reserve reserve;
    private RoomType roomType;
    private Room room;
    private LocalDate start;
    private LocalDate end;
    
    @BeforeEach 
    public void setUp(){
        roomType = roomTypeRepo.save(new RoomType("shared standard", true));
        room = roomRepo.save(new Room("1", roomType, 6));
        start = LocalDate.of(2000, 01, 01);
        end = LocalDate.of(2000, 01, 02);
        reserve = reserveRepo.save(new Reserve(start, end, room));
    }

    @Test
    void testSaveCausesOverbooking() {
        assertTrue(reserveRepo.saveCausesOverbooking(room, start, end));
    }

    @Test
    void testUpdateCausesOverbooking() {
        Reserve overbookingReserve = reserveRepo.save(new Reserve(end, end.plusDays(1), room));
        assertTrue(reserveRepo.updateCausesOverbooking(overbookingReserve.getId(), room, start, end));
    }

    @Test
    void testFindByEnd() {
        assertTrue(reserveRepo.findByEndDate(end).stream().allMatch(r -> reserve.getEndDate().equals(end)));
    }

    @Test
    void testFindByStart() {
        assertTrue(reserveRepo.findByStartDate(start).stream().allMatch(r -> reserve.getStartDate().equals(start)));
    }

    @Test
    void testFindByStartDateBetween() {
        assertTrue(reserveRepo.findByStartDateBetween(start, end).stream().allMatch(r -> !reserve.getStartDate().isBefore(start) && !reserve.getStartDate().isAfter(end)));
    }
}
