package reserves.internal.room;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import reserves.internal.roomType.RoomType;

public class RoomTests {

    private Room room;

    private final RoomType roomType = new RoomType("standard shared", true);

    @Test
    void testConstructorFailNullName() {
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            room = new Room(null, roomType, 6);
        });

        assertTrue(exception.getMessage().contains("name missing"));
    }

    @Test
    void testConstructorFailNullRoomType() {
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            room = new Room("Quarto 1", null, 6);
        });

        assertTrue(exception.getMessage().contains("room type missing"));
    }

    @Test
    void testConstructorFailInvalidMaxOccupation() {
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            room = new Room("Quarto 1", roomType, 0);
        });

        assertTrue(exception.getMessage().contains("invalid maximum occupation"));
    }

    private static Stream<Arguments> setRoomTypes() {
        RoomType sharedRoomType = new RoomType("standard shared", true);
        RoomType privateRoomType = new RoomType("standard private", false);

        return Stream.of(
            Arguments.of(sharedRoomType),
            Arguments.of(privateRoomType)
        );
    }

    @ParameterizedTest
    @MethodSource("setRoomTypes")
    void testAvaliability(RoomType roomType) {
        room = new Room("1", roomType, 1);
        Room occupiedRoom = new Room("2", roomType, 1);
        occupiedRoom.setOccupation(1);

        assertEquals(true, room.isAvaliable());
        assertEquals(false, occupiedRoom.isAvaliable());
    }
}
