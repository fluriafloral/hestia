package reserves.internal.roomType;

import javax.naming.directory.AttributeInUseException;
import javax.naming.directory.InvalidAttributeValueException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoomTypeServiceTests {

    private RoomTypeService roomTypeService;

    private RoomType roomType;

    public RoomTypeServiceTests(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @BeforeEach
    public void setUp() {
        roomType = new RoomType("Standard Shared", true);
    }

    @Test
    public void testCreateNewRoomTypeSuccess() {
        assertEquals(roomType, roomTypeService.createNewRoomType(roomType));
    }

    @Test
    public void testCreateNewRoomTypeFailNameEmpty() {

        RoomType roomTypeEmptyName = new RoomType("", true);

        Exception exception = assertThrows(InvalidAttributeValueException.class, () -> {
            roomTypeService.createNewRoomType(roomTypeEmptyName);
        });

        assertTrue(exception.getMessage().contains("Name cannot be empty"));
    }

    @Test 
    public void testCreateNewRoomTypeFailNameAlreadyExists() {

        RoomType roomType_ = new RoomType("compartilhado standard", false);

        Exception exception = assertThrows(AttributeInUseException.class, () -> {
            roomTypeService.createNewRoomType(roomType_);
        });

        assertTrue(exception.getMessage().contains("Name already declared"));
    }

    @Test
    void testCreateNewRoomType() {

    }

    @Test
    void testDeleteRoomType() {

    }
}
