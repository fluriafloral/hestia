package reserves.internal.room;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import reserves.internal.room.Room.RoomStatus;
import reserves.internal.roomType.RoomType;
import reserves.internal.roomType.RoomTypeRepository;

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

    @ParameterizedTest
    @MethodSource("setStatus")
    void testFindByStatus(RoomStatus status) {
        List<Room> retrievedByStatusList = roomRepo.findByStatus(status);
        assertTrue(retrievedByStatusList.stream().allMatch(room -> room.getStatus().equals(status)));
    }

    private static Stream<Arguments> setStatus() {
        return Stream.of (
            Arguments.of(RoomStatus.FREE),
            Arguments.of(RoomStatus.OCCUPIED), 
            Arguments.of(RoomStatus.UNCLEAN), 
            Arguments.of(RoomStatus.IN_MAINTENANCE)
        );
    }
}