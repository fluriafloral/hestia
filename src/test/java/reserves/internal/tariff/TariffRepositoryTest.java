package reserves.internal.tariff;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TariffRepositoryTest {

    @Autowired
    private TariffRepository tariffRepo;

    @Test
    public void testFindAll() {
        List<Tariff> retrievedTariffs = tariffRepo.findAll();

        assertEquals(4, retrievedTariffs.size());
    }
}
