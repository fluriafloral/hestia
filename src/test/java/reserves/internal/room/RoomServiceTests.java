package reserves.internal.room;

import javax.naming.directory.AttributeInUseException;
import javax.naming.directory.InvalidAttributeValueException;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoomServiceTests {

    @Autowired
    private static RoomService roomService;

    private static RoomType roomType;

    private static Room room;

    @BeforeEach
    public static void setUp() {
        roomType = new RoomType("compartilhado standard", true);
        room = new Room("1", roomType, 1);
    }

    @AfterAll
    public static void cleanUp() {
        roomService.deleteRoomType(roomType.getId());
        roomService.deleteRoom(room.getId());
    }

    @Test
    public void testCreateNewRoomTypeSuccess() {
        assertEquals(roomType, roomService.createNewRoomType(roomType));
    }

    @Test
    public void testCreateNewRoomTypeFailNameEmpty() {

        RoomType roomTypeEmptyName = new RoomType("", true);

        Exception exception = assertThrows(InvalidAttributeValueException.class, () -> {
            roomService.createNewRoomType(roomTypeEmptyName);
        });

        assertTrue(exception.getMessage().contains("Name cannot be empty"));
    }

    @Test 
    public void testCreateNewRoomTypeFailNameAlreadyExists() {

        RoomType roomType_ = new RoomType("compartilhado standard", false);

        Exception exception = assertThrows(AttributeInUseException.class, () -> {
            roomService.createNewRoomType(roomType_);
        });

        assertTrue(exception.getMessage().contains("Name already declared"));
    }

    @Test
    public void testUpdateRoomTypeSuccess() {

    }

    @Test
    public void testCreateNewRoomSuccess() {
        assertEquals(room, roomService.createNewRoom(room));
    }

    @Test
    public void testCreateNewRoomFailNameEmpty() {

        Room roomEmptyName = new Room( "", roomType, 1);

        Exception exception = assertThrows(InvalidAttributeValueException.class, () -> {
            roomService.createNewRoom(roomEmptyName);
        });

        assertTrue(exception.getMessage().contains("Name cannot be empty"));
    }

    @Test
    public void testCreateNewRoomFailNameAlreadyExists() {

        Room roomRepeatedName = new Room( "1", roomType, 1);

        Exception exception = assertThrows(AttributeInUseException.class, () -> {
            roomService.createNewRoom(roomRepeatedName);
        });

        assertTrue(exception.getMessage().contains("Name already declared"));
    }

    @Test
    public void testCreateNewRoomFailMaxOccupationLowerThanOne() {
        Room roomMaxOccupationError = new Room( "1", roomType, 0);

        Exception exception = assertThrows(InvalidAttributeValueException.class, () -> {
            roomService.createNewRoom(roomMaxOccupationError);
        });

        assertTrue(exception.getMessage().contains("Maximum occupation cannot be lower than one"));
    }
 }
