package reserves.internal.room;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import reserves.internal.room.Room.RoomStatus;

@DataJpaTest
public class RoomRepositoryTests {

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private RoomTypeRepository roomTypeRepo;

    @Test
    void testFindByRoomType() {
        RoomType roomType = roomTypeRepo.getReferenceById(0L);

        List<Room> retrievedroomTypeList = roomRepo.findByRoomType(roomType);

        assertAll(retrievedroomTypeList.stream().map(room -> () -> assertEquals(roomType.getId(), room.getRoomType().getId())));
    }

    @Test
    void testFindByStatus() {
        List<Room> retrievedFreeList = roomRepo.findByStatus(RoomStatus.FREE);
        List<Room> retrievedOccupiedList = roomRepo.findByStatus(RoomStatus.OCCUPIED);
        List<Room> retrievedUncleanList = roomRepo.findByStatus(RoomStatus.UNCLEAN);
        List<Room> retrievedInMaintenanceList = roomRepo.findByStatus(RoomStatus.IN_MAINTENANCE);

        assertTrue(retrievedFreeList.stream().allMatch(room -> room.getStatus() == RoomStatus.FREE));
        assertTrue(retrievedOccupiedList.stream().allMatch(room -> room.getStatus() == RoomStatus.OCCUPIED));
        assertTrue(retrievedUncleanList.stream().allMatch(room -> room.getStatus() == RoomStatus.UNCLEAN));
        assertTrue(retrievedInMaintenanceList.stream().allMatch(room -> room.getStatus() == RoomStatus.IN_MAINTENANCE));
    }
}