package reserves.internal.room;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import reserves.internal.room.Room.RoomStatus;
import reserves.internal.roomType.RoomType;

public interface RoomRepository extends JpaRepository<Room, Long>{

    public Optional<Room> findByName(String name);
    public Boolean  existsByName(String name);
    public List<Room> findByStatus(RoomStatus status);
    public List<Room> findByRoomType(RoomType type);
}
