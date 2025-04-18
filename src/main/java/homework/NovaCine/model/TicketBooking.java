package homework.NovaCine.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TicketBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screeningId", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private Screening screening;

    @Column(nullable = false)
    private String viewerName;

    @Column(nullable = false)
    private Integer seatCount;

    private LocalDateTime bookingTime;

    @Column(nullable = false, updatable = false, unique = true)
    private UUID uuid;

}
