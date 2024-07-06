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


import java.util.concurrent.ExecutionException;

@Configuration
public class SecurityConfig {

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
        var admin = User.withUsername("admin")
                .password("admin")
                .authorities("ADMIN")
                .build();
        var rlinco = User.withUsername("r.linco")
                .password("123")
                .authorities("STAFF")
                .build();
        var tsep = User.withUsername("t.sep")
                .password("1234")
                .authorities("STAFF")
                .build();
        var mavs = User.withUsername("mavs")
                .password("123456")
                .authorities("STAFF")
                .build();
        var jbor = User.withUsername("j.bor")
                .password("1234")
                .authorities("STAFF")
                .build();
        var klobos = User.withUsername("klobos")
                .password("123")
                .authorities("STAFF")
                .build();
        var custom = User.withUsername("custom")
                .password("custom")
                .authorities("CUSTOM")
                .build();
        return new InMemoryUserDetailsManager(admin, rlinco, tsep, mavs, jbor, klobos, custom);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

}
