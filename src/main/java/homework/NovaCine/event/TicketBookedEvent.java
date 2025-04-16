package homework.NovaCine.event;

import homework.NovaCine.model.TicketBooking;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TicketBookedEvent extends ApplicationEvent {

    private final TicketBooking ticketBooking;

    public TicketBookedEvent(Object source, TicketBooking ticketBooking) {
        super(source);
        this.ticketBooking = ticketBooking;
    }
}
