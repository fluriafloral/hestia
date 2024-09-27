package reserves.internal.room;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import reserves.internal.room.Room.RoomStatus;
import reserves.internal.roomType.RoomType;

public interface RoomRepository extends JpaRepository<Room, Long>{

    Optional<Room> findByName(String name);
    Boolean  existsByName(String name);
    List<Room> findByStatus(RoomStatus status);
    List<Room> findByRoomType(RoomType type);
}
