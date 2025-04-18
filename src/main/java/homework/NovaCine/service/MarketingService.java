package homework.NovaCine.service;

import homework.NovaCine.event.TicketBookedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MarketingService {

    @EventListener
    public void logBooking(TicketBookedEvent event) {
        log.info("""
                        [MarketingService] Ticket Booking Received:
                            Viewer: {}
                            Seat Count: {}
                            Screening ID: {}
                            Movie: {}
                            Booking Time: {}
                        """,
                event.getTicketBooking().getViewerName(),
                event.getTicketBooking().getSeatCount(),
                event.getTicketBooking().getScreening().getId(),
                event.getTicketBooking().getScreening().getMovieTitle(),
                event.getTicketBooking().getBookingTime()
        );
    }
}
