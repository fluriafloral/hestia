package reserves.internal.room;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RoomTests {

    @ParameterizedTest
    @MethodSource("setRoomTypes")
    void testAvaliability(RoomType roomType) {
        Room room = new Room("1", roomType, 1);
        Room occupiedRoom = new Room("2", roomType, 1);
        occupiedRoom.setOccupation(1);

        assertEquals(true, room.isAvaliable());
        assertEquals(false, occupiedRoom.isAvaliable());
    }

    private static Stream<Arguments> setRoomTypes() {
        RoomType sharedRoomType = new RoomType("shared", true);
        RoomType privateRoomType = new RoomType("private", false);

        return Stream.of(
            Arguments.of(sharedRoomType),
            Arguments.of(privateRoomType)
        );
    }

}
