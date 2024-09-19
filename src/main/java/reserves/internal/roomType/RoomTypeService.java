package reserves.internal.roomType;

import java.util.List;
import java.util.Optional;

public interface RoomTypeService {

    RoomType saveRoomType(RoomType roomType);
    List<RoomType> getAllRoomTypes();
    Optional<RoomType> findRoomTypeByName(String name);
    List<RoomType> findRoomTypeByShared(Boolean shared);
    RoomType updateRoomType(RoomType roomType);
    void deleteRoomType(long id);
}
