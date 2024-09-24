package reserves.internal.dailyRate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import reserves.dailyRate.DailyRate;
import reserves.dailyRate.DailyRateRepository;

@DataJpaTest
public class DailyRateRepositoryTest {

    @Autowired
    private DailyRateRepository dailyRateRepo;

    @Test
    public void testFindAll() {
        List<DailyRate> dailyRates = dailyRateRepo.findAll();
        
        assertEquals(8, dailyRates.size());
    }
}
