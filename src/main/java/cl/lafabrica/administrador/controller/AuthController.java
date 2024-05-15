package cl.lafabrica.administrador.controller;

import cl.lafabrica.administrador.auth.AuthResponse;
import cl.lafabrica.administrador.service.impl.AuthServiceImpl;
import cl.lafabrica.administrador.auth.LoginRequest;
import cl.lafabrica.administrador.auth.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthServiceImpl authService;

    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login de usuarios correctamente"),
            @ApiResponse(responseCode = "404", description = "Login de usuario sin exito")
    })
    @PostMapping( "/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest loginRequest) throws Exception {
        AuthResponse authResponse = authService.login(loginRequest);
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
    }

    @PostMapping( "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) throws ParseException, ExecutionException, InterruptedException {
        AuthResponse authResponse = authService.register(registerRequest);
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
    }
}
