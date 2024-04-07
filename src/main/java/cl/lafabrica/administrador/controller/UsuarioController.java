package cl.lafabrica.administrador.controller;

import cl.lafabrica.administrador.dto.UsuarioDto;
import cl.lafabrica.administrador.modelo.Usuario;
import cl.lafabrica.administrador.service.api.UsuarioServiceAPI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class UsuarioController {

    @Autowired
    private UsuarioServiceAPI usuarioServiceAPI;
    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se obtuvieron los usuarios correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron usuarios")
    })
    @GetMapping("/listarUsuarios")
    public List<UsuarioDto> getAllUsuarios() throws Exception {
        return usuarioServiceAPI.getAll();
    }

    @Operation(summary = "crea un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene un usuarios correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontro un usuario")
    })
    @PostMapping("/creaUsuario")
    public ResponseEntity<String> save(
            @RequestBody Usuario usuario,
            @PathVariable(name = "id", required = false) String id ) throws Exception {
        Usuario user = usuario;
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date fecha = formatoEntrada.parse(String.valueOf(usuario.getFechaNacimientoUser()));
        String fechaFormateada = formatoSalida.format(fecha);
        Timestamp timestamp = Timestamp.valueOf(fechaFormateada);
        user.setFechaNacimientoUser(timestamp);
        if (id == null || id.length() == 0 || id.equals("null")) {
            id = usuarioServiceAPI.save(user);
        } else {
            usuarioServiceAPI.save(user, id);
        }
        return new ResponseEntity<String>(id, HttpStatus.OK);
    }
}
