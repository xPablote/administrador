package cl.lafabrica.administrador.modelo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Data
public class Usuario {

    private Integer runUser;
    private String digitoVUser;
    private String pNombreUser;
    private String sNombreUser;
    private String pApellidoUser;
    private String mApellidoUser;
    private String correoElectronicoUser;
    private Integer telefonoUser;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Timestamp fechaNacimientoUser;
    private String tipoUser;

}
