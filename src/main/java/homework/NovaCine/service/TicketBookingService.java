package homework.NovaCine.service;

import homework.NovaCine.model.TicketBooking;
import homework.NovaCine.repository.TicketBookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketBookingService {

    private final TicketBookingRepository ticketBookingRepository;

    @Transactional
    public void saveTicket(TicketBooking ticketBooking) {
          ticketBookingRepository.save(ticketBooking);
          log.info("Booking saved to DB for viewer {} ", ticketBooking.getViewerName());
    }

}
