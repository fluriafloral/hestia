package reserves.internal.roomType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RoomTypeRepositoryTests {

    @Autowired
    private RoomTypeRepository roomTypeRepo;

    private RoomType shared;

    private RoomType privateDouble;

    @BeforeEach
    public void setUp() {
        shared = new RoomType("Standard Shared", true);
        privateDouble = new RoomType("Standard Double", false);
    }

    @Test
    void testFindByName() {
        RoomType retrievedShared = roomTypeRepo.findByName("Standard Shared").get();
        RoomType retrievedPrivateDouble = roomTypeRepo.findByName("Standard Double").get();

        assertEquals(shared.getName(), retrievedShared.getName());
        assertEquals(shared.isShared(), retrievedShared.isShared());

        assertEquals(privateDouble.getName(), retrievedPrivateDouble.getName());
        assertEquals(privateDouble.isShared(), retrievedPrivateDouble.isShared());
    }

    @Test
    void testFindByShared() {
        List<RoomType> retrievedSharedList = roomTypeRepo.findByShared(true);
        List<RoomType> retrievedPrivateList = roomTypeRepo.findByShared(false);

        assertTrue(retrievedSharedList.stream().allMatch(roomType -> roomType.isShared()));
        assertTrue(retrievedPrivateList.stream().allMatch(roomType -> !roomType.isShared()));
    }
}
