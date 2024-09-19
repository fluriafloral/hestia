package reserves.internal.roomType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoomTypeServiceImplTests {

    @InjectMocks
    private RoomTypeServiceImpl roomTypeService;

    @Mock
    private RoomTypeRepository roomTypeRepo;

    private RoomType roomType;

    @BeforeEach
    public void setUp() {
        roomType = new RoomType("Test", true);
    }

    @Test
    public void testSaveRoomTypeSuccess() {

        when(roomTypeRepo.save(roomType)).thenReturn(roomType);

        RoomType savedRoomType = roomTypeService.saveRoomType(roomType);

        assertThat(savedRoomType).isNotNull();
        assertEquals(roomType.getName(), savedRoomType.getName());
    }

    @Test
    public void testSaveRoomTypeFailNameEmpty() {

        RoomType roomTypeEmptyName = new RoomType("", true);

        given(roomTypeRepo.findByName(roomTypeEmptyName.getName())).isEmpty();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            roomTypeService.saveRoomType(roomTypeEmptyName);
        });
        
        verify(roomTypeRepo, never()).save(any(RoomType.class));
        assertTrue(exception.getMessage().contains("Name cannot be empty"));
    }

    @Test 
    public void testSaveRoomTypeFailNameAlreadyExists() {

        RoomType roomTypeRepeatedName = new RoomType("Test", false);
        
        when(roomTypeRepo.existsByName(roomTypeRepeatedName.getName())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            roomTypeService.saveRoomType(roomTypeRepeatedName);
        });

        verify(roomTypeRepo, never()).save(any(RoomType.class));
        assertTrue(exception.getMessage().contains("Name already declared"));
    }

    @Test
    public void testFindAllRoomType() {
        
        RoomType roomType1 = new RoomType("Test1", true);

        when(roomTypeRepo.findAll()).thenReturn(List.of(roomType, roomType1));

        List<RoomType> roomTypeList = roomTypeService.findAllRoomTypes();

        assertThat(roomTypeList).isNotNull();
        assertThat(roomTypeList.size()).isEqualTo(2);
    }

    @Test
    public void testFindRoomTypeByNameSuccess() {

        when(roomTypeRepo.findByName(roomType.getName())).thenReturn(Optional.of(roomType));

        RoomType retrievedRoomType = roomTypeService.findRoomTypeByName(roomType.getName()).get();

        assertThat(retrievedRoomType).isNotNull();
        assertEquals(roomType.getName(), retrievedRoomType.getName());
    }

    @Test
    public void testFindRoomTypeByNameFail() {

        String roomTypeNameNotFound = "Test Not Found";
        given(roomTypeRepo.findByName(roomTypeNameNotFound)).isEmpty();

        Optional<RoomType> retrievedRoomType = roomTypeService.findRoomTypeByName(roomTypeNameNotFound);

        assertThat(retrievedRoomType).isEmpty();
    }

    @Test
    public void testFindRoomTypeByShared() {

        given(roomTypeRepo.findByShared(roomType.isShared())).allMatch(type -> type.isShared());

        List<RoomType> roomTypeList = roomTypeService.findRoomTypeByShared(roomType.isShared());

        assertThat(roomTypeList).isNotNull();
        assertTrue(roomTypeList.stream().allMatch(type -> type.isShared()));
    }

    @Test
    public void testUpdateRoomTypeSuccess() {
        
        String nameUpdate = "Test Update";
        when(roomTypeRepo.save(roomType)).thenReturn(roomType);
        roomType.setName(nameUpdate);

        RoomType updatedRoomType = roomTypeService.updateRoomType(roomType);

        assertEquals(nameUpdate, updatedRoomType.getName());
    }

    @Test
    public void testUpdateRoomTypeFailNameEmpty() {

        roomType.setName("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            roomTypeService.updateRoomType(roomType);
        });

        verify(roomTypeRepo, never()).save(any(RoomType.class));
        assertTrue(exception.getMessage().contains("Name cannot be empty"));
    }

    @Test
    public void testDeleteRoomType() {

        long roomTypeId = roomType.getId();
        willDoNothing().given(roomTypeRepo).deleteById(roomTypeId);

        roomTypeService.deleteRoomType(roomTypeId);

        verify(roomTypeRepo, times(1)).deleteById(roomTypeId);
        assertThat(roomTypeRepo.existsByName(roomType.getName())).isFalse();
    }
}
