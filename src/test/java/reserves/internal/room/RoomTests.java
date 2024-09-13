package reserves.internal.room;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RoomTests {

    @ParameterizedTest
    @MethodSource("setRoomsAndTypes")
    void testAvaliability(Room room, boolean result) {
        assertEquals(room.isAvaliable(), result);
    }

    private static Stream<Arguments> setRoomsAndTypes() {
        RoomType sharedRoomType = new RoomType("shared", true);
        Room sharedRoom = new Room("1", sharedRoomType, 1);
        Room occupiedSharedRoom = new Room("2", sharedRoomType, 1);
        occupiedSharedRoom.setOccupation(1);

        RoomType privateRoomType = new RoomType("private", false);
        Room privateRoom = new Room("3", privateRoomType, 1);
        Room occupiedPrivateRoom = new Room("4", privateRoomType, 1);
        occupiedPrivateRoom.setOccupation(1);

        return Stream.of(
            Arguments.of(sharedRoom, true),
            Arguments.of(occupiedSharedRoom, false),
            Arguments.of(privateRoom, true),
            Arguments.of(occupiedPrivateRoom, false)
        );
    }

}
