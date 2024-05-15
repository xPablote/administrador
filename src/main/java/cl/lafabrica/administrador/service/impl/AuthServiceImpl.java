package cl.lafabrica.administrador.service.impl;

import cl.lafabrica.administrador.auth.AuthResponse;
import cl.lafabrica.administrador.auth.LoginRequest;
import cl.lafabrica.administrador.auth.RegisterRequest;
import cl.lafabrica.administrador.jwt.JwtService;
import cl.lafabrica.administrador.modelo.Usuario;
import cl.lafabrica.administrador.service.AuthService;
import cl.lafabrica.administrador.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest loginRequest) throws ParseException, ExecutionException, InterruptedException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getRun(),loginRequest.getPassword()));
        Usuario usuario = usuarioService.getUsuario(loginRequest.getRun());
        String token = jwtService.getToken(usuario);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest registerRequest) throws ParseException, ExecutionException, InterruptedException {
        Usuario usuario = Usuario.builder()
                .run(registerRequest.getRun())
                .dv(registerRequest.getDv())
                .primerNombre(registerRequest.getPrimerNombre())
                .segundoNombre(registerRequest.getSegundoNombre())
                .paternoApellido(registerRequest.getPaternoApellido())
                .maternoApellido(registerRequest.getMaternoApellido())
                .email(registerRequest.getEmail())
                .fono(registerRequest.getFono())
                .fechaNacimiento(registerRequest.getFechaNacimiento())
                .rolUsuario(registerRequest.getRolUsuario())
                .fechaRegistro(registerRequest.getFechaRegistro())
                .estado(registerRequest.getEstado())
                .idTipoPlan(registerRequest.getIdTipoPlan())
                .build();
        usuarioService.createUsuario(usuario);
        return AuthResponse.builder()
                .token(jwtService.getToken(usuario))
                .build();
    }
}
