package cl.lafabrica.administrador.controller;

import cl.lafabrica.administrador.modelo.Usuario;
import cl.lafabrica.administrador.pojo.response.ResponseFirestore;
import cl.lafabrica.administrador.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Crea un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crea un usuarios correctamente"),
            @ApiResponse(responseCode = "404", description = "Creacion de usuario sin exito")
    })
    @PostMapping("/creaUsuario")
    public ResponseEntity<ResponseFirestore> save(
            @RequestBody Usuario usuario) throws Exception {
        ResponseFirestore resp = usuarioService.saveUsuario(usuario);
        return new ResponseEntity<ResponseFirestore>(resp, HttpStatus.OK);
    }
    @Operation(summary = "Obtiene un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene un usuarios correctamente"),
            @ApiResponse(responseCode = "404", description = "usuario inexistente")
    })

    @GetMapping("/getUsuario/{rut}")
    public ResponseEntity<Usuario> getUsuario(
            @PathVariable String rut) throws Exception {
        Usuario resp = usuarioService.getUsuario(rut);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una lista de usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene lista usuarios correctamente"),
            @ApiResponse(responseCode = "404", description = "usuarios inexistentes")
    })

    @GetMapping("/getUsuarios")
    public ResponseEntity<List<Usuario>> getUsuarios() throws Exception {
        List<Usuario> resp = usuarioService.getUsuarios();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }



}
