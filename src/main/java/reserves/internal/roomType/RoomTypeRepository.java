package reserves.internal.roomType;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long>{

    public Optional<RoomType> findByName(String name);

    public boolean existsByName(String name);
    
    public List<RoomType> findByShared(Boolean shared);
}
