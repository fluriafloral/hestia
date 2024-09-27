package reserves.internal.dailyRate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import reserves.internal.roomType.RoomType;
import reserves.internal.tariff.Tariff;

public interface DailyRateRepository  extends JpaRepository<DailyRate, Long>{

    List<DailyRate> findAllByRoomType(RoomType roomType);
    List<DailyRate> findAllByTariff(Tariff tariff);
    Boolean existsByRoomTypeAndTariff(RoomType roomType, Tariff tariff);
}
