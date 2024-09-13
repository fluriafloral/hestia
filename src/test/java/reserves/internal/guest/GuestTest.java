package reserves.internal.guest;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class GuestTest {

    @ParameterizedTest
    @MethodSource("setGuests")
    void testIsUnderage(Guest guest, boolean result) {
        assertEquals(guest.isUnderage(), result);
    }

    private static Stream<Arguments> setGuests() {

        Guest guest = new Guest("Carlos", LocalDate.now().minusYears(18));

        Guest guestUnderage = new Guest("Carlinhos", LocalDate.now().minusYears(17));

        return Stream.of(
            Arguments.of(guest, false),
            Arguments.of(guestUnderage, true)
        );
    }
}
