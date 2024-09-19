package reserves.internal.room;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import reserves.internal.room.Room.RoomStatus;
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
        if (room.getName().isBlank()) { throw new IllegalArgumentException("Name cannot be empty"); }
        if (roomRepo.existsByName(room.getName())) { throw new IllegalArgumentException("Name already declared"); }
        if (roomTypeRepo.existsById(room.getRoomType().getId())) { throw new IllegalArgumentException("RoomType Not found"); }
        if (room.getMaxOccupation() < 1) { throw new IllegalArgumentException("Maximum occupation must be greater than 0"); }

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
        if (room.getName().isBlank()) { throw new IllegalArgumentException("Name cannot be empty"); }
        if (roomTypeRepo.existsById(room.getRoomType().getId())) { throw new IllegalArgumentException("RoomType Not found"); }
        if (room.getMaxOccupation() < 1) { throw new IllegalArgumentException("Maximum occupation must be greater than 0"); }

        return roomRepo.save(room);
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepo.deleteById(id);
    }

}
