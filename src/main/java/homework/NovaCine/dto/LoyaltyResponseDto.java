package homework.NovaCine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoyaltyResponseDto {
    private String viewerName;
    private Integer totalPoints;
}
