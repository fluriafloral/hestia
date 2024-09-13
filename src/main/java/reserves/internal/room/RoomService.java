package reserves.internal.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reserves.internal.roomType.RoomTypeRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private RoomTypeRepository roomTypeRepo;

    public Object createNewRoom(Room room) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNewRoom'");
    }

    void deleteRoom(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
