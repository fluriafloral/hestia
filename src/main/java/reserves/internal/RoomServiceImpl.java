package reserves.internal;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import reserves.internal.room.Room;
import reserves.internal.room.Room.RoomStatus;
import reserves.internal.room.RoomRepository;
import reserves.internal.room.RoomService;
import reserves.internal.roomType.RoomType;
import reserves.internal.roomType.RoomTypeRepository;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepo;

    private final RoomTypeRepository roomTypeRepo;

    public RoomServiceImpl(RoomRepository roomRepo, RoomTypeRepository roomTypeRepo) {
        this.roomRepo = roomRepo;
        this.roomTypeRepo = roomTypeRepo;
    }

    @Override
    public Room saveRoom(Room room) {

        if (roomRepo.existsByName(room.getName())) { throw new IllegalStateException("Name already in use"); }
        
        return roomRepo.save(room);
    }

    @Override
    public List<Room> findAllRooms() {
        return roomRepo.findAll();
    }

    @Override
    public Optional<Room> findRoomByName(String name) {
        return roomRepo.findByName(name);
    }

    @Override
    public List<Room> findRoomsByStatus(RoomStatus status) {
        return roomRepo.findByStatus(status);
    }

    @Override
    public List<Room> findRoomsByType(RoomType type) {
        return roomRepo.findByRoomType(type);
    }

    @Override
    public Room updateRoom(Room room) {
        
        if (roomRepo.nameAlreadyInUse(room.getName(), room.getId())) { throw new IllegalStateException("Name already in use"); }

        return roomRepo.save(room);
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepo.deleteById(id);
    }

}
