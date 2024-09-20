package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import reserves.internal.RoomServiceImpl;
import reserves.internal.RoomTypeServiceImpl;
import reserves.internal.room.RoomRepository;
import reserves.internal.room.RoomService;
import reserves.internal.roomType.RoomTypeRepository;
import reserves.internal.roomType.RoomTypeService;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class ReservesConfig {

    @Autowired
    private RoomTypeRepository roomTypeRepo;

    @Autowired 
    private RoomRepository roomRepo;

    @Bean 
    public RoomTypeService roomTypeService() {
        return new RoomTypeServiceImpl(roomTypeRepo);
    }

    @Bean
    public RoomService roomService() {
        return new RoomServiceImpl(roomRepo, roomTypeRepo);
    }
}
