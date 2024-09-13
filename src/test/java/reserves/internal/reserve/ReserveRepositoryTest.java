package reserves.internal.reserve;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ReserveRepositoryTest {

    @Autowired
    private ReserveRepository reserveRepo;
    
    private LocalDate start;
    private LocalDate end;

    @BeforeEach 
    public void setUp(){
        start = LocalDate.of(2000, 01, 01);
        end = LocalDate.of(2000, 01, 02);
    }

    @Test
    void testFindByEnd() {
        assertTrue(reserveRepo.findByEnd(end).stream().allMatch(reserve -> reserve.getEnd().equals(end)));
    }

    @Test
    void testFindByStart() {
        assertTrue(reserveRepo.findByStart(start).stream().allMatch(reserve -> reserve.getStart().equals(start)));
    }
}
