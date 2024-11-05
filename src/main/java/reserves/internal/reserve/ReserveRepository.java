package reserves.internal.reserve;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import reserves.internal.room.Room;

public interface ReserveRepository extends JpaRepository<Reserve, Long>{

    @Query("SELECT case WHEN count(r) > 0 THEN true ELSE false END " +
           "FROM Reserve r " + 
           "WHERE r.room = :room " + 
           "AND ((r.startDate <= :startDate AND :startDate < r.endDate) " +
           "OR (r.endDate >= :endDate AND :endDate > r.startDate))")
    boolean saveCausesOverbooking(@Param("room") Room room, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT case WHEN count(r) > 0 THEN true ELSE false END " +
           "FROM Reserve r " + 
           "WHERE r.id <> :reserveId " + 
           "AND r.room = :room " + 
           "AND ((r.startDate <= :startDate AND :startDate < r.endDate) " +
           "OR (r.endDate >= :endDate AND :endDate > r.startDate))")
    boolean updateCausesOverbooking(@Param("reserveId") Long id, @Param("room") Room room, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    List<Reserve> findByStartDate(LocalDate startDate);
    List<Reserve> findByEndDate(LocalDate endDate);
    List<Reserve> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
}
