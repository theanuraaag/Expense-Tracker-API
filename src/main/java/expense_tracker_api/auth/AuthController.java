package expense_tracker_api.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import expense_tracker_api.auth.dto.AuthResponse;
import expense_tracker_api.auth.dto.LoginRequest;
import expense_tracker_api.auth.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
@SecurityRequirements
public class AuthController {
    private final AuthService authService;

    public  AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse body = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        
        return ResponseEntity.ok(authService.login(request));
    }
    
}
