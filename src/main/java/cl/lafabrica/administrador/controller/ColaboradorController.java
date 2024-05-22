package cl.lafabrica.administrador.controller;

import cl.lafabrica.administrador.model.Colaborador;
import cl.lafabrica.administrador.model.Estado;
import cl.lafabrica.administrador.response.ResponseFirestore;
import cl.lafabrica.administrador.service.ColaboradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/colaborador")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequiredArgsConstructor
public class ColaboradorController {

    @Autowired
    private ColaboradorService colaboradorService;

    @Operation(summary = "Crea un Colaborador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crea un Colaborador correctamente"),
            @ApiResponse(responseCode = "404", description = "Creacion de Colaborador sin exito")
    })
    @PostMapping("/createColaborador")
    public ResponseEntity<ResponseFirestore> createColaborador(
            @RequestBody Colaborador colaborador) throws Exception {
        ResponseFirestore resp = colaboradorService.createColaborador(colaborador);
        return new ResponseEntity<ResponseFirestore>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un Colaborador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene un Colaborador correctamente"),
            @ApiResponse(responseCode = "404", description = "Colaborador inexistente")
    })
    @GetMapping("/getColaborador/{username}")
    public ResponseEntity<Colaborador> getColaborador(
            @PathVariable String username) throws Exception {
        Colaborador resp = colaboradorService.getColaborador(username);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una lista de Colaboradores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene lista Colaboradores correctamente"),
            @ApiResponse(responseCode = "404", description = "Colaboradores inexistentes")
    })
    @GetMapping("/listColaborador")
    public ResponseEntity<List<Colaborador>> listColaboradores() throws Exception {
        List<Colaborador> resp = colaboradorService.listColaboradores();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Modifica un Colaborador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modificó un Colaborador correctamente"),
            @ApiResponse(responseCode = "404", description = "Modificación de Colaborador sin exito")
    })
    @PutMapping("/updateColaborador")
    public ResponseEntity<ResponseFirestore> updateColaborador(
            @RequestBody Colaborador colaborador) throws Exception {
        ResponseFirestore resp = colaboradorService.updateColaborador(colaborador);
        return new ResponseEntity<ResponseFirestore>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Modifica estado de un Colaborador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modificó estado de un Colaborador correctamente"),
            @ApiResponse(responseCode = "404", description = "Modificación cambio de Colaborador sin exito")
    })
    @PutMapping("/changeStateColaborador/{username}/{estado}")
    public ResponseEntity<ResponseFirestore> changeStateColaborador(
            @PathVariable String username,
            @PathVariable Estado estado) throws Exception {
        ResponseFirestore resp = colaboradorService.changeStateColaborador(username,estado);
        return new ResponseEntity<ResponseFirestore>(resp, HttpStatus.OK);
    }


}
