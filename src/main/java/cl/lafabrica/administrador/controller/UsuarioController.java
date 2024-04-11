package cl.lafabrica.administrador.controller;

import cl.lafabrica.administrador.dto.UsuarioDto;
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


    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se obtuvieron los usuarios correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron usuarios")
    })
    @GetMapping("/listarUsuarios")
    public List<UsuarioDto> getAllUsuarios() throws Exception {
        return null;//usuarioService.getAll();
    }

    @Operation(summary = "crea un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene un usuarios correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontro un usuario")
    })
    @PostMapping("/creaUsuario")
    public ResponseEntity<ResponseFirestore> save(
            @RequestBody Usuario usuario) throws Exception {
        ResponseFirestore resp = usuarioService.saveUsuario(usuario);
        return new ResponseEntity<ResponseFirestore>(resp, HttpStatus.OK);
    }
}
