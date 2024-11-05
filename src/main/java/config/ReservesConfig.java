package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import reserves.internal.DailyRateServiceImpl;
import reserves.internal.GuestServiceImpl;
import reserves.internal.ReserveServiceImpl;
import reserves.internal.RoomServiceImpl;
import reserves.internal.RoomTypeServiceImpl;
import reserves.internal.dailyRate.DailyRateRepository;
import reserves.internal.dailyRate.DailyRateService;
import reserves.internal.guest.GuestRepository;
import reserves.internal.guest.GuestService;
import reserves.internal.reserve.ReserveRepository;
import reserves.internal.reserve.ReserveService;
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

    @Autowired
    private GuestRepository guestRepo;

    @Autowired 
    private DailyRateRepository dailyRateRepo;

    @Autowired
    private ReserveRepository reserveRepo;

    @Bean 
    public RoomTypeService roomTypeService() {
        return new RoomTypeServiceImpl(roomTypeRepo);
    }

    @Bean
    public RoomService roomService() {
        return new RoomServiceImpl(roomRepo, roomTypeRepo);
    }

    @Bean
    public GuestService guestService() {
        return new GuestServiceImpl(guestRepo);
    }

    @Bean
    public DailyRateService dailyRateService() {
        return new DailyRateServiceImpl(dailyRateRepo);
    }

    @Bean 
    public ReserveService reserveService() {
        return new ReserveServiceImpl(reserveRepo);
    }
}
