package reserves.internal.reserve;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReserveService {

    Reserve saveReserve(Reserve reserve);
    List<Reserve> findAllReserves();
    Optional<Reserve> findReserveById(Long id);
    List<Reserve> findReserveByStartDate(LocalDate startDate);
    List<Reserve> findReserveByEndDate(LocalDate endDate);
    List<Reserve> findReserveByPeriod(LocalDate startDate, LocalDate endDate);
    Reserve updateReserve(Reserve reserve);
    void deleteReserve(Long id);
}
