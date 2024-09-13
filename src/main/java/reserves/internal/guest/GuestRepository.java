package reserves.internal.guest;

import org.springframework.data.repository.CrudRepository;


public interface GuestRepository extends CrudRepository<Guest, Long>{

    public Guest findByDocumentNumber(String documentNumber);
}
