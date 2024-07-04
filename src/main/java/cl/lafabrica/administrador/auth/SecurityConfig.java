package cl.lafabrica.administrador.auth;

import cl.lafabrica.administrador.model.Colaborador;
import cl.lafabrica.administrador.service.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Configuration
public class SecurityConfig {
    @Autowired
    ColaboradorService colaboradorService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/**").permitAll())
                .formLogin(formLogin -> formLogin.loginProcessingUrl("/login")
                        .defaultSuccessUrl("http://localhost:4200", true))
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() throws ExecutionException, InterruptedException {
        List<Colaborador> colaboradorList = colaboradorService.listColaboradores();
        var admin = User.withUsername("admin")
                .password("admin")
                .authorities("ADMIN")
                .build();
        var staff = User.withUsername("staff")
                .password("staff")
                .authorities("STAFF")
                .build();
        return new InMemoryUserDetailsManager(admin, staff);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

}
