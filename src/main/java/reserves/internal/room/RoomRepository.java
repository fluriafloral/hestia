package reserves.internal.room;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import reserves.internal.room.Room.RoomStatus;

public interface RoomRepository extends JpaRepository<Room, Long>{

    public List<Room> findByStatus(RoomStatus status);

    public List<Room> findByRoomType(RoomType type);
}
