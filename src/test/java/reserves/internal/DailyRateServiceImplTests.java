package reserves.internal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import reserves.internal.dailyRate.DailyRate;
import reserves.internal.dailyRate.DailyRateRepository;
import reserves.internal.roomType.RoomType;
import reserves.internal.tariff.Tariff;

@ExtendWith(MockitoExtension.class)
public class DailyRateServiceImplTests {

    @InjectMocks
    private DailyRateServiceImpl dailyRateService;

    @Mock
    private DailyRateRepository dailyRateRepo;

    private DailyRate dailyRate;

    private RoomType roomType;

    private Tariff tariff;

    @BeforeEach
    public void setUp() {
        roomType = new RoomType("Test Room Type", true);
        tariff = new Tariff("Test Tariff", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 7, 1));
        dailyRate = new DailyRate(roomType, tariff, BigDecimal.valueOf(50.0), BigDecimal.valueOf(40.0), BigDecimal.valueOf(25.0));
    }

    @Test
    void testSaveDailyRateSuccess() {

        when(dailyRateRepo.save(dailyRate)).thenReturn(dailyRate);

        DailyRate savedDailyRate = dailyRateService.saveDailyRate(dailyRate);

        assertThat(savedDailyRate).isNotNull();
        assertEquals(dailyRate.getRoomType(), savedDailyRate.getRoomType());
        assertEquals(dailyRate.getTariff(), savedDailyRate.getTariff());
    }

    @Test
    void testSaveDailyRateFailAlreadyExists() {

        DailyRate dailyRateAlreadyExists = new DailyRate(roomType, tariff,  BigDecimal.valueOf(50.0), BigDecimal.valueOf(40.0), BigDecimal.valueOf(25.0));

        when(dailyRateRepo.existsByRoomTypeAndTariff(roomType, tariff)).thenReturn(true);
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dailyRateService.saveDailyRate(dailyRateAlreadyExists);
        });

        verify(dailyRateRepo, never()).save(any(DailyRate.class));
        assertTrue(exception.getMessage().contains("A Daily Rate was already set for this Room Type and Tariff"));
    }

    @Test
    void testSaveDailyRateFailNullRoomType() {

        DailyRate dailyRateNullRoomType = new DailyRate(null, tariff,  BigDecimal.valueOf(50.0), BigDecimal.valueOf(40.0), BigDecimal.valueOf(25.0));
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dailyRateService.saveDailyRate(dailyRateNullRoomType);
        });

        verify(dailyRateRepo, never()).save(any(DailyRate.class));
        assertTrue(exception.getMessage().contains("Room Type cannot be null"));
    }

    @Test
    void testSaveDailyRateFailNullTariff() {

        DailyRate dailyRateNullTariff = new DailyRate(roomType, null,  BigDecimal.valueOf(50.0), BigDecimal.valueOf(40.0), BigDecimal.valueOf(25.0));
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dailyRateService.saveDailyRate(dailyRateNullTariff);
        });

        verify(dailyRateRepo, never()).save(any(DailyRate.class));
        assertTrue(exception.getMessage().contains("Tariff cannot be null"));
    }

    @Test
    void testSaveDailyRateFailInvalidBaseValue() {

        DailyRate dailyRateInvalidBaseValue = new DailyRate(roomType, tariff, BigDecimal.valueOf(0.0), BigDecimal.valueOf(40.0), BigDecimal.valueOf(25.0));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dailyRateService.saveDailyRate(dailyRateInvalidBaseValue);
        });

        verify(dailyRateRepo, never()).save(any(DailyRate.class));
        assertTrue(exception.getMessage().contains("Base Value needs to be positive"));
    }

    @Test
    void testSaveDailyRateFailInvalidIncreasePerGuest() {

        DailyRate dailyRateInvalidIncreasePerGuest = new DailyRate(roomType, tariff, BigDecimal.valueOf(50.0), BigDecimal.valueOf(0.0), BigDecimal.valueOf(25.0));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dailyRateService.saveDailyRate(dailyRateInvalidIncreasePerGuest);
        });

        verify(dailyRateRepo, never()).save(any(DailyRate.class));
        assertTrue(exception.getMessage().contains("Increase Per Guest needs to be positive"));
    }

    @Test
    void testSaveDailyRateFailInvalidIncreasePerChildren() {

        DailyRate dailyRateInvalidIncreasePerChildren = new DailyRate(roomType, tariff, BigDecimal.valueOf(50.0), BigDecimal.valueOf(40.0), BigDecimal.valueOf(0.0));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dailyRateService.saveDailyRate(dailyRateInvalidIncreasePerChildren);
        });

        verify(dailyRateRepo, never()).save(any(DailyRate.class));
        assertTrue(exception.getMessage().contains("Increase Per Children needs to be positive"));
    }

    @Test
    void testFindAllDailyRates() {

        RoomType roomType1 = new RoomType("Test Room Type 1", false);
        DailyRate dailyRate1 = new DailyRate(roomType1, tariff, BigDecimal.valueOf(50.0), BigDecimal.valueOf(40.0), BigDecimal.valueOf(0.0));

        when(dailyRateRepo.findAll()).thenReturn(List.of(dailyRate, dailyRate1));

        List<DailyRate> listDailyRates = dailyRateService.findAllDailyRates();

        assertThat(listDailyRates).isNotNull();
        assertEquals(listDailyRates.size(), 2);
    }

    @Test
    void testFindAllDailyRatesByRoomType() {

        given(dailyRateRepo.findAllByRoomType(roomType)).allMatch(dr -> dr.getRoomType().equals(roomType));

        List<DailyRate> listDailyRates = dailyRateService.findAllDailyRatesByRoomType(roomType);

        assertThat(listDailyRates).isNotNull().allMatch(dr -> dr.getRoomType().equals(roomType));
    }

    @Test
    void testFindAllDailyRatesByTariff() {

        given(dailyRateRepo.findAllByTariff(tariff)).allMatch(dr -> dr.getTariff().equals(tariff));

        List<DailyRate> listDailyRates = dailyRateService.findAllDailyRatesByTariff(tariff);

        assertThat(listDailyRates).isNotNull().allMatch(dr -> dr.getTariff().equals(tariff));
    }

    @Test
    void testUpdateDailyRateSucess() {

        BigDecimal baseValueUpdate = BigDecimal.valueOf(51.00);
        when(dailyRateRepo.save(dailyRate)).thenReturn(dailyRate);
        dailyRate.setBaseValue(baseValueUpdate);

        DailyRate updatedDailyRate = dailyRateService.updateDailyRate(dailyRate);
        
        assertEquals(dailyRate.getBaseValue(), updatedDailyRate.getBaseValue());
    }

    @Test 
    void testUpdateDailyRateFailNullRoomType() {
        
        dailyRate.setRoomType(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dailyRateService.updateDailyRate(dailyRate);
        });

        verify(dailyRateRepo, never()).save(any(DailyRate.class));
        assertTrue(exception.getMessage().contains("Room Type cannot be null"));
    }

    @Test 
    void testUpdateDailyRateFailNullTariff() {
        
        dailyRate.setTariff(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dailyRateService.updateDailyRate(dailyRate);
        });

        verify(dailyRateRepo, never()).save(any(DailyRate.class));
        assertTrue(exception.getMessage().contains("Tariff cannot be null"));
    }

    @Test 
    void testUpdateDailyRateFailInvalidBaseValue() {

        dailyRate.setBaseValue(BigDecimal.valueOf(0.0));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dailyRateService.updateDailyRate(dailyRate);
        });

        verify(dailyRateRepo, never()).save(any(DailyRate.class));
        assertTrue(exception.getMessage().contains("Base Value needs to be positive"));
    }

    @Test 
    void testUpdateDailyRateFailInvalidIncreasePerGuest() {

        dailyRate.setIncreasePerGuest(BigDecimal.valueOf(0.0));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dailyRateService.updateDailyRate(dailyRate);
        });

        verify(dailyRateRepo, never()).save(any(DailyRate.class));
        assertTrue(exception.getMessage().contains("Increase Per Guest needs to be positive"));
    }

    @Test 
    void testUpdateDailyRateFailInvalidIncreasePerChildren() {

        dailyRate.setIncreasePerChildren(BigDecimal.valueOf(0.0));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dailyRateService.updateDailyRate(dailyRate);
        });

        verify(dailyRateRepo, never()).save(any(DailyRate.class));
        assertTrue(exception.getMessage().contains("Increase Per Children needs to be positive"));
    }

    @Test
    void testDeleteDailyRate() {

        Long dailyRateId = dailyRate.getId();
        willDoNothing().given(dailyRateRepo).deleteById(dailyRateId);

        dailyRateService.deleteDailyRate(dailyRateId);

        verify(dailyRateRepo, times(1)).deleteById(dailyRateId);
        assertThat(dailyRateRepo.existsById(dailyRateId)).isFalse();
    }
}
