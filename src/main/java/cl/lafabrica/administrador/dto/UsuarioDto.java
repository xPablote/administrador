package cl.lafabrica.administrador.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

public class UsuarioDto {

    private String id;
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
