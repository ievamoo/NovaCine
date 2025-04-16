package homework.NovaCine.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String movieTitle;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    private Integer availableSeats;

    @ManyToOne
    @JoinColumn(name = "cinemaId", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private Cinema cinema;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<TicketBooking> ticketBookings;
}
