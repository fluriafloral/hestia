package reserves.internal.roomType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomTypeService {  

    @Autowired
    private RoomTypeRepository roomTypeRepo;

    public RoomType createNewRoomType(RoomType roomType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNewRoomType'");
    }

    public void deleteRoomType(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
