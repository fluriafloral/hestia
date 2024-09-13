package reserves.internal.sale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SaleRepositoryTest {

    @Autowired
    private SaleRepository saleRepo;

    @Test
    public void testFindAll() {
        assertEquals(1, saleRepo.findAll().size());
    }
}
