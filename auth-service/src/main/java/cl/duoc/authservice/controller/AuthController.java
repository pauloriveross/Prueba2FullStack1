package cl.duoc.authservice.controller;

import cl.duoc.authservice.dto.LoginRequest;
import cl.duoc.authservice.dto.LoginResponse;
import cl.duoc.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/publico")
    public ResponseEntity<String> publico() {
        return ResponseEntity.ok("Auth Service activo");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(response);
    }
}
