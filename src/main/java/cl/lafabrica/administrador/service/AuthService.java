package cl.lafabrica.administrador.service;

import cl.lafabrica.administrador.auth.AuthResponse;
import cl.lafabrica.administrador.auth.LoginRequest;
import cl.lafabrica.administrador.auth.RegisterRequest;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest) throws ParseException, ExecutionException, InterruptedException;
    AuthResponse register(RegisterRequest registerRequest) throws ParseException, ExecutionException, InterruptedException;
}
