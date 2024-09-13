package reserves.internal.room;

import javax.naming.directory.AttributeInUseException;
import javax.naming.directory.InvalidAttributeValueException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reserves.internal.roomType.RoomType;

public class RoomServiceTests {

    private RoomService roomService;

    private RoomType roomType;

    private Room room;

    public RoomServiceTests(RoomService roomService) {
        this.roomService = roomService;
    }

    @BeforeEach
    public void setUp() {
        roomType = new RoomType("compartilhado standard", true);
        room = new Room("1", roomType, 1);
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
