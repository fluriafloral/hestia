package reserves.internal.room;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import reserves.internal.room.Room.RoomStatus;
import reserves.internal.roomType.RoomType;

public interface RoomRepository extends JpaRepository<Room, Long>{

    @Query("SELECT case WHEN count(r) > 0 THEN true ELSE false END " + 
    "FROM Room r " + 
    "WHERE r.name = :name " +
    "AND r.id <> :id")
    boolean nameAlreadyInUse(@Param("name") String name, @Param("id") Long id);
    Optional<Room> findByName(String name);
    Boolean  existsByName(String name);
    List<Room> findByStatus(RoomStatus status);
    List<Room> findByRoomType(RoomType type);
}
