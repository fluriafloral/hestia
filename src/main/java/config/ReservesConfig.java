package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import reserves.internal.roomType.RoomTypeRepository;
import reserves.internal.roomType.RoomTypeService;
import reserves.internal.roomType.RoomTypeServiceImpl;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class ReservesConfig {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Bean 
    public RoomTypeService roomTypeService() {
        return new RoomTypeServiceImpl(roomTypeRepository);
    }

}
