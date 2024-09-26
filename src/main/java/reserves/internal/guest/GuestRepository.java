package reserves.internal.guest;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface GuestRepository extends JpaRepository<Guest, Long>{

    Optional<Guest> findByDocumentNumber(String documentNumber);
    Boolean existsByDocumentNumber(String documentNumber);
}
