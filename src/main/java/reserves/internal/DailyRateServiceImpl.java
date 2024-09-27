package reserves.internal;

import java.math.BigDecimal;
import java.util.List;

import reserves.internal.dailyRate.DailyRate;
import reserves.internal.dailyRate.DailyRateRepository;
import reserves.internal.dailyRate.DailyRateService;
import reserves.internal.roomType.RoomType;
import reserves.internal.tariff.Tariff;

public class DailyRateServiceImpl implements DailyRateService {

    private final DailyRateRepository dailyRateRepo;

    public DailyRateServiceImpl(DailyRateRepository dailyRateRepo) {
        this.dailyRateRepo = dailyRateRepo;
    }

    @Override
    public DailyRate saveDailyRate(DailyRate dailyRate) {
        if (dailyRateRepo.existsByRoomTypeAndTariff(dailyRate.getRoomType(), dailyRate.getTariff())) {
            throw new IllegalArgumentException("A Daily Rate was already set for this Room Type and Tariff");
        }
        if (dailyRate.getRoomType() == null) {
            throw new IllegalArgumentException("Room Type cannot be null");
        }
        if (dailyRate.getTariff() == null) {
            throw new IllegalArgumentException("Tariff cannot be null");
        }
        if (dailyRate.getBaseValue().compareTo(BigDecimal.valueOf(0.01)) == -1) { 
            throw new IllegalArgumentException("Base Value needs to be positive"); 
        }
        if (dailyRate.getIncreasePerGuest().compareTo(BigDecimal.valueOf(0.01)) == -1) { 
            throw new IllegalArgumentException("Increase Per Guest needs to be positive"); 
        }
        if (dailyRate.getIncreasePerChildren().compareTo(BigDecimal.valueOf(0.01)) == -1) { 
            throw new IllegalArgumentException("Increase Per Children needs to be positive"); 
        }

        return dailyRateRepo.save(dailyRate);
    }

    @Override
    public List<DailyRate> findAllDailyRates() {
        return dailyRateRepo.findAll();
    }

    @Override
    public List<DailyRate> findAllDailyRatesByRoomType(RoomType roomType) {
        return dailyRateRepo.findAllByRoomType(roomType);
    }

    @Override
    public List<DailyRate> findAllDailyRatesByTariff(Tariff tariff) {
        return dailyRateRepo.findAllByTariff(tariff);
    }

    @Override
    public DailyRate updateDailyRate(DailyRate dailyRate) {
        if (dailyRate.getRoomType() == null) {
            throw new IllegalArgumentException("Room Type cannot be null");
        }
        if (dailyRate.getTariff() == null) {
            throw new IllegalArgumentException("Tariff cannot be null");
        }
        if (dailyRate.getBaseValue().compareTo(BigDecimal.valueOf(0.01)) == -1) { 
            throw new IllegalArgumentException("Base Value needs to be positive"); 
        }
        if (dailyRate.getIncreasePerGuest().compareTo(BigDecimal.valueOf(0.01)) == -1) { 
            throw new IllegalArgumentException("Increase Per Guest needs to be positive"); 
        }
        if (dailyRate.getIncreasePerChildren().compareTo(BigDecimal.valueOf(0.01)) == -1) { 
            throw new IllegalArgumentException("Increase Per Children needs to be positive"); 
        }

        return dailyRateRepo.save(dailyRate);
    }

    @Override
    public void deleteDailyRate(Long id) {
        dailyRateRepo.deleteById(id);
    }

}
