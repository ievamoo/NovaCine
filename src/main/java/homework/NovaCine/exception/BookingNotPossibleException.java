package homework.NovaCine.exception;

public class BookingNotPossibleException extends RuntimeException {
    public BookingNotPossibleException() {
        super("Booking not possible. Screening starts in less than 2 hours.");
    }
}
