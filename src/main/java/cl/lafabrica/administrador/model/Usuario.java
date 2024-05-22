package cl.lafabrica.administrador.model;

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
public class Usuario {
    @DocumentId
    private String run;
    private String dv;
    private String primerNombre;
    private String segundoNombre;
    private String paternoApellido;
    private String maternoApellido;
    private String email;
    private Long fono;
    private Timestamp fechaNacimiento;
    private Timestamp fechaRegistro;
    @PropertyName("estado")
    private Estado estado;
    private String idTipoPlan;
    private Long descuento;
}
