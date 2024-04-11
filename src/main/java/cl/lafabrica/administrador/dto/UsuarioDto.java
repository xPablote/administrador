package cl.lafabrica.administrador.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Data
@Getter
@Setter
public class UsuarioDto {

    public String id;
    public Integer runUser;
    public String digitoVUser;
    public String primerNombreUser;
    public String segundoNombreUser;
    public String paternoApellidoUser;
    public String maternoApellidoUser;
    public String correoElectronicoUser;
    public Integer telefonoUser;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    public Timestamp fechaNacimientoUser;
    public String tipoUser;


}
