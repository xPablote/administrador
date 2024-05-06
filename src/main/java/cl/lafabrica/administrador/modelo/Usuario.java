package cl.lafabrica.administrador.modelo;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import lombok.Data;

import java.sql.Timestamp;


@Data
public class Usuario {
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

}
