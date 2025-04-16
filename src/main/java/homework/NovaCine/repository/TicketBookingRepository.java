package homework.NovaCine.repository;

import homework.NovaCine.model.TicketBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketBookingRepository extends JpaRepository<TicketBooking, Long> {
}
