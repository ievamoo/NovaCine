package homework.NovaCine.controller;

import homework.NovaCine.dto.LoyaltyResponseDto;
import homework.NovaCine.service.LoyaltyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loyalty")
@RequiredArgsConstructor
public class LoyaltyController {

    private final LoyaltyService loyaltyService;

    @GetMapping("/{viewerName}")
    public ResponseEntity<LoyaltyResponseDto> getLoyaltyPoints(@PathVariable String viewerName) {
        return ResponseEntity.ok(loyaltyService.getLoyaltyPoints(viewerName));
    }
}
