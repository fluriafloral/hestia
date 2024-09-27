package reserves.internal.dailyRate;

import java.util.List;

import reserves.internal.roomType.RoomType;
import reserves.internal.tariff.Tariff;

public interface DailyRateService {

    DailyRate saveDailyRate(DailyRate dailyRate);
    List<DailyRate> findAllDailyRates();
    List<DailyRate> findAllDailyRatesByRoomType(RoomType roomType);
    List<DailyRate> findAllDailyRatesByTariff(Tariff tariff);
    DailyRate updateDailyRate(DailyRate dailyRate);
    void deleteDailyRate(Long id);
}
