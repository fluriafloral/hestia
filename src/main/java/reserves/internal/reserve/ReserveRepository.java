package reserves.internal.reserve;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ReserveRepository extends CrudRepository<Reserve, Long>{
    
    public List<Reserve> findByStart(LocalDate start);

    public List<Reserve> findByEnd(LocalDate end);
}
