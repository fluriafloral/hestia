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

import reserves.internal.room.Room;
import reserves.internal.room.Room.RoomStatus;
import reserves.internal.room.RoomRepository;
import reserves.internal.roomType.RoomType;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTests {

    @InjectMocks
    private RoomServiceImpl roomService;

    @Mock
    private RoomRepository roomRepo;

    private Room room;

    private RoomType roomType ;

    @BeforeEach
    public void setUp() {
        roomType = new RoomType("Test RoomType", false);
        room = new Room("Test Room", roomType, 1);
        room.setStatus(RoomStatus.FREE);
    }

    @Test
    void testSaveRoomSuccess() {

        when(roomRepo.save(room)).thenReturn(room);

        Room savedRoom = roomService.saveRoom(room);

        assertThat(savedRoom).isNotNull();
        assertEquals(room.getName(), savedRoom.getName());
    }

    @Test
    void testSaveRoomFailNameAlreadyInUse() {

        Room roomNameInUse = new Room("Test Room", roomType, 1);
        when(roomRepo.existsByName(roomNameInUse.getName())).thenReturn(true);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            roomService.saveRoom(roomNameInUse);
        });

        verify(roomRepo, never()).save(any(Room.class));
        assertTrue(exception.getMessage().contains("Name already in use"));
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
    void testFindRoomByNameSuccess() {
        
        when(roomRepo.findByName(room.getName())).thenReturn(Optional.of(room));

        Room retrievedRoom = roomService.findRoomByName(room.getName()).get();

        assertThat(retrievedRoom).isNotNull();
        assertEquals(room.getName(), retrievedRoom.getName());
    }

    @Test
    void testFindRoomByNameSuccessFail() {

        String roomNameNotFound = "Test Not Found";
        given(roomRepo.findByName(roomNameNotFound)).isEmpty();

        Optional<Room> retrievedRoom = roomService.findRoomByName(roomNameNotFound);

        assertThat(retrievedRoom).isEmpty();
    }

    @Test
    void testFindRoomByStatus() {

        RoomStatus status = RoomStatus.FREE;
        given(roomRepo.findByStatus(status)).allMatch(r -> r.getStatus().equals(status));

        List<Room> roomList = roomService.findRoomsByStatus(status);

        assertThat(roomList).isNotNull();
        assertTrue(roomList.stream().allMatch(r -> r.getStatus().equals(status)));
    }

    @Test
    void testFindRoomByType() {

        given(roomRepo.findByRoomType(roomType)).allMatch(r -> r.getRoomType().getName().equals(roomType.getName()));

        List<Room> roomList = roomService.findRoomsByType(roomType);

        assertThat(roomList).isNotNull();
        assertTrue(roomList.stream().allMatch(r -> r.getName().equals(roomType.getName())));
    }

    @Test
    void testUpdateRoomSuccess() {

        String nameUpdate = "Test Update";
        when(roomRepo.save(room)).thenReturn(room);
        room.setName(nameUpdate);

        Room updatedRoom = roomService.updateRoom(room);

        assertEquals(nameUpdate, updatedRoom.getName());
    }

    @Test
    void testUpdateRoomFailNameAlreadyInUse() {

        Room roomNameInUse = new Room("Test Room 1", roomType, 1);
        when(roomRepo.nameAlreadyInUse("Test Room", roomNameInUse.getId())).thenReturn(true);
        roomNameInUse.setName("Test Room");

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            roomService.updateRoom(roomNameInUse);
        });

        verify(roomRepo, never()).save(any(Room.class));
        assertTrue(exception.getMessage().contains("Name already in use"));
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
