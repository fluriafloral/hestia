package reserves.internal.room;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.willDoNothing;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import reserves.internal.room.Room.RoomStatus;
import reserves.internal.roomType.RoomType;
import reserves.internal.roomType.RoomTypeRepository;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTests {

    @InjectMocks
    private RoomServiceImpl roomService;

    @Mock
    private RoomRepository roomRepo;

    @Mock
    private RoomTypeRepository roomTypeRepo;

    private Room room;

    private RoomType roomType;

    @BeforeEach
    public void setUp() {
        roomType = new RoomType("Test RoomType", false);
        room = new Room("Test Room", roomType, 1);
        room.setStatus(RoomStatus.FREE);
    }

    @Test
    void testSaveRoom() {

        when(roomRepo.save(room)).thenReturn(room);

        Room savedRoom = roomService.saveRoom(room);

        assertThat(savedRoom).isNotNull();
        assertEquals(room.getName(), savedRoom.getName());
    }

    @Test
    void testFindAllRooms() {

        Room room1 = new Room("Test Room 1", roomType, 1);

        when(roomRepo.findAll()).thenReturn(List.of(room, room1));

        List<Room> roomList = roomService.findAllRooms();

        assertThat(roomList).isNotNull();
        assertThat(roomList.size()).isEqualTo(2);
    }

    @Test
    void testGetRoomByNameSuccess() {
        
        when(roomRepo.findByName(room.getName())).thenReturn(Optional.of(room));

        Room retrievedRoom = roomService.findRoomByName(room.getName()).get();

        assertThat(retrievedRoom).isNotNull();
        assertEquals(room.getName(), retrievedRoom.getName());
    }

    @Test
    void testGetRoomByNameSuccessFail() {

        String roomNameNotFound = "Test Not Found";
        given(roomRepo.findByName(roomNameNotFound)).isEmpty();

        Optional<Room> retrievedRoom = roomService.findRoomByName(roomNameNotFound);

        assertThat(retrievedRoom).isEmpty();
    }

    @Test
    void testGetRoomByStatus() {

        RoomStatus status = RoomStatus.FREE;
        given(roomRepo.findByStatus(status)).allMatch(r -> r.getStatus().equals(status));

        List<Room> roomList = roomService.findRoomsByStatus(status);

        assertThat(roomList).isNotNull();
        assertTrue(roomList.stream().allMatch(r -> r.getStatus().equals(status)));
    }

    @Test
    void testGetRoomByType() {

        given(roomRepo.findByRoomType(roomType)).allMatch(r -> r.getRoomType().getName().equals(roomType.getName()));

        List<Room> roomList = roomService.findRoomsByType(roomType);

        assertThat(roomList).isNotNull();
        assertTrue(roomList.stream().allMatch(r -> r.getName().equals(roomType.getName())));
    }

    @Test
    void testUpdateRoom() {

        String nameUpdate = "Test Update";
        when(roomRepo.save(room)).thenReturn(room);
        room.setName(nameUpdate);

        Room updatedRoom = roomService.updateRoom(room);

        assertEquals(nameUpdate, updatedRoom.getName());
    }

    @Test
    void testDeleteRoom() {

        Long roomId = room.getId();
        willDoNothing().given(roomRepo).deleteById(roomId);

        roomService.deleteRoom(roomId);

        verify(roomRepo, times(1)).deleteById(roomId);
        assertThat(roomRepo.existsByName(room.getName())).isFalse();
    }
}
