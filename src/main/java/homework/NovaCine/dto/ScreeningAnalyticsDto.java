package homework.NovaCine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScreeningAnalyticsDto {
    private Integer totalBookings;
    private Integer remainingSeats;
}
