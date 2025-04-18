package homework.NovaCine.repository;

import homework.NovaCine.model.TicketBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketBookingRepository extends JpaRepository<TicketBooking, Long> {
    TicketBooking findByUuid(UUID uuid);
}
