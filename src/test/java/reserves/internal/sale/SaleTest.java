package reserves.internal.sale;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reserves.internal.reserve.Reserve;

public class SaleTest {

    private Sale sale;

    private Reserve newReserve;

    @BeforeEach
    public void setUp() {
        sale = new Sale(new ArrayList<>());
        newReserve = new Reserve(-1L);
    }
    @Test
    void testAddReserve() {
        sale.addReserve(newReserve);
        assertEquals(newReserve, sale.getReserves().get(0));
    }

    @Test
    void testRemoveReserve() {
        sale.removeReserve(newReserve.getId());
        assertTrue(sale.getReserves().stream().noneMatch(reserve -> reserve.getId().equals(-1L)));
    }
}
