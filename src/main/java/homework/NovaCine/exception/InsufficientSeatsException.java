package homework.NovaCine.exception;

public class InsufficientSeatsException extends RuntimeException {
    public InsufficientSeatsException(Integer bookedSeats, Integer availableSeats) {
        super("Your booked seats: " + bookedSeats + " exceeds total available seats: " + availableSeats);
    }
}
