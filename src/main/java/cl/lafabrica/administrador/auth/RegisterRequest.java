package cl.lafabrica.administrador.auth;

import cl.lafabrica.administrador.modelo.Estado;
import cl.lafabrica.administrador.modelo.RolUsuario;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
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
}
