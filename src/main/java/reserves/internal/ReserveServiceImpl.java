package reserves.internal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import reserves.internal.reserve.Reserve;
import reserves.internal.reserve.ReserveRepository;
import reserves.internal.reserve.ReserveService;

@Service
public class ReserveServiceImpl implements ReserveService {

    private final ReserveRepository reserveRepo;

    public ReserveServiceImpl(ReserveRepository reserveRepo) {
        this.reserveRepo = reserveRepo;
    }

    @Override
    public Reserve saveReserve(Reserve reserve) {

        if (reserveRepo.saveCausesOverbooking(reserve.getRoom(), reserve.getStartDate(), reserve.getEndDate())) { 
            throw new IllegalStateException(reserve.getRoom().getName() + " is already reserved for some of these dates"); 
        }

        return reserveRepo.save(reserve);
    }

    @Override
    public List<Reserve> findAllReserves() {
        return reserveRepo.findAll();
    }

    @Override
    public Optional<Reserve> findReserveById(Long id) {
        return reserveRepo.findById(id);
    }

    @Override
    public List<Reserve> findReserveByStartDate(LocalDate startDate) {
        return reserveRepo.findByStartDate(startDate);
    }

    @Override
    public List<Reserve> findReserveByEndDate(LocalDate endDate) {
        return reserveRepo.findByEndDate(endDate);
    }

    @Override
    public List<Reserve> findReserveByPeriod(LocalDate startDate, LocalDate endDate) {
        return reserveRepo.findByStartDateBetween(startDate, endDate);
    }

    @Override
    public Reserve updateReserve(Reserve reserve) {

        if (reserveRepo.updateCausesOverbooking(reserve.getId(), reserve.getRoom(), reserve.getStartDate(), reserve.getEndDate())) { 
            throw new IllegalStateException(reserve.getRoom().getName() + " is already reserved for some of these dates"); 
        }

        return reserveRepo.save(reserve);   
    }

    @Override
    public void deleteReserve(Long id) {
        reserveRepo.deleteById(id);
    }

}
