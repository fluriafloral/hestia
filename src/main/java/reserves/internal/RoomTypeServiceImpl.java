package reserves.internal;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import reserves.internal.roomType.RoomType;
import reserves.internal.roomType.RoomTypeRepository;
import reserves.internal.roomType.RoomTypeService;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {  

    private final RoomTypeRepository roomTypeRepo;

    public RoomTypeServiceImpl(RoomTypeRepository roomTypeRepo) {
        this.roomTypeRepo = roomTypeRepo;
    }

    @Override
    public RoomType saveRoomType(RoomType roomType) {
        if (roomType.getName().isBlank()) { throw new IllegalArgumentException("Name cannot be empty"); }
        if (roomTypeRepo.existsByName(roomType.getName())) { throw new IllegalArgumentException("Name already declared"); }

        return roomTypeRepo.save(roomType);
    }

    @Override
    public List<RoomType> findAllRoomTypes() {
        return roomTypeRepo.findAll();
    }

    @Override
    public Optional<RoomType> findRoomTypeByName(String name) {
        return roomTypeRepo.findByName(name);
    }

    @Override
    public List<RoomType> findRoomTypeByShared(Boolean shared) {
        return roomTypeRepo.findByShared(shared);
    }

    @Override
    public RoomType updateRoomType(RoomType roomType) {
        if (roomType.getName().isBlank()) { throw new IllegalArgumentException("Name cannot be empty");}

        return roomTypeRepo.save(roomType);
    }

    @Override
    public void deleteRoomType(long id) {
        roomTypeRepo.deleteById(id);
    }
}
