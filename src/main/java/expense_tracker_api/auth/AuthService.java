package expense_tracker_api.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import expense_tracker_api.auth.dto.AuthResponse;
import expense_tracker_api.auth.dto.LoginRequest;
import expense_tracker_api.auth.dto.RegisterRequest;
import expense_tracker_api.domain.user.User;
import expense_tracker_api.domain.user.UserRepository;
import expense_tracker_api.security.JwtService;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if(userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new EmailAlreadyRegisteredException(request.email());
        }
        User user = new User();
        user.setEmail(request.email().trim().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user = userRepository.save(user);
        return new AuthResponse(jwtService.generateToken(user));
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByEmailIgnoreCase(request.email().trim().toLowerCase()).orElseThrow(InvalidLoginException::new);
        if(!passwordEncoder.matches(request.password(), user.getPasswordHash())){
            throw new InvalidLoginException();
        }

        return new AuthResponse(jwtService.generateToken(user));
    }
}
