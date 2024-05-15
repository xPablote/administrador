package cl.lafabrica.administrador.modelo;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {
    @DocumentId
    public String run;
    public String dv;
    public String primerNombre;
    public String segundoNombre;
    public String paternoApellido;
    public String maternoApellido;
    public String email;
    public Long fono;
    public Timestamp fechaNacimiento;
    @PropertyName("rolUsuario")
    public RolUsuario rolUsuario;
    public Timestamp fechaRegistro;
    @PropertyName("estado")
    public Estado estado;
    public String idTipoPlan;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
