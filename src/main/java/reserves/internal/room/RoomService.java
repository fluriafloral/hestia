package reserves.internal.room;

import reserves.internal.roomType.RoomType;
import reserves.internal.roomType.RoomTypeRepository;

public class RoomService {

    private RoomRepository roomRepo;

    private RoomTypeRepository roomTypeRepo;

    public RoomService(RoomRepository roomRepo, RoomTypeRepository roomTypeRepo) {
        this.roomRepo = roomRepo;
        this.roomTypeRepo = roomTypeRepo;
    }

    public RoomType createNewRoomType(RoomType roomType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNewRoomType'");
    }

    public Object createNewRoom(Room room) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNewRoom'");
    }

    void deleteRoomType(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    void deleteRoom(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
