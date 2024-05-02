package cl.lafabrica.administrador.controller;

import cl.lafabrica.administrador.modelo.Usuario;
import cl.lafabrica.administrador.pojo.response.ResponseFirestoreUsuario;
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
    @PostMapping("/createUsuario")
    public ResponseEntity<ResponseFirestoreUsuario> createUsuario(
            @RequestBody Usuario usuario) throws Exception {
        ResponseFirestoreUsuario resp = usuarioService.createUsuario(usuario);
        return new ResponseEntity<ResponseFirestoreUsuario>(resp, HttpStatus.OK);
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
    @GetMapping("/listUsuarios")
    public ResponseEntity<List<Usuario>> listUsuarios() throws Exception {
        List<Usuario> resp = usuarioService.getUsuarios();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Modifica un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modificó un usuario correctamente"),
            @ApiResponse(responseCode = "404", description = "Modificación de usuario sin exito")
    })
    @PutMapping("/updateUsuario")
    public ResponseEntity<ResponseFirestoreUsuario> updateUsuario(
            @RequestBody Usuario usuario) throws Exception {
        ResponseFirestoreUsuario resp = usuarioService.updateUsuario(usuario);
        return new ResponseEntity<ResponseFirestoreUsuario>(resp, HttpStatus.OK);
    }



}
