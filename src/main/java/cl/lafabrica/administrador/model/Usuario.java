package cl.lafabrica.administrador.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Timestamp fechaNacimiento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Timestamp fechaRegistro;
    @PropertyName("estado")
    private Estado estado;
}
