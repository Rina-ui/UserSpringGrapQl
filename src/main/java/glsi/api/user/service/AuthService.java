package glsi.api.user.service;

import glsi.api.user.DTO.Login;
import glsi.api.user.DTO.Register;
import glsi.api.user.model.User;
import glsi.api.user.repository.UserRepository;
import glsi.api.user.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(Register register) {
        User user = new User();
        user.setFirstName(register.firstName());
        user.setLastName(register.lastName());
        user.setEmail(register.email());
        user.setPassword(passwordEncoder.encode(register.password()));

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user);
    }

    public <AuthResponse> AuthResponse login(Login login) {
        User user = userRepository.findByEmail(login.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(login.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user);
    }
}
