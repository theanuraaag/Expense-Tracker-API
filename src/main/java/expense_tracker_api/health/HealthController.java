package expense_tracker_api.health;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;


@RestController
@RequestMapping("/api")
@SecurityRequirements
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity<Map<String,String>> health(){
        return ResponseEntity.ok(Map.of("status","UP"));
    }
}
