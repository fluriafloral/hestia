package reserves.internal.room;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long>{

    public RoomType findByName(String name);
    
    public List<RoomType> findByShared(Boolean shared);
}
