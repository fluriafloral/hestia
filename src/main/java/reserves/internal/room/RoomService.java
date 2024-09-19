package reserves.internal.room;

import java.util.List;
import java.util.Optional;

import reserves.internal.room.Room.RoomStatus;
import reserves.internal.roomType.RoomType;

public interface RoomService {

    Room saveRoom(Room room);
    List<Room> findAllRooms();
    Optional<Room> findRoomByName(String name);
    List<Room> findRoomsByStatus(RoomStatus status);
    List<Room> findRoomsByType(RoomType type);
    Room updateRoom(Room room);
    void deleteRoom(Long id);
}
