package cl.lafabrica.administrador.controller;

import cl.lafabrica.administrador.model.PlanUsuario;
import cl.lafabrica.administrador.response.ResponseFirestore;
import cl.lafabrica.administrador.service.PlanUsuarioService;
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
@RequestMapping(value = "/planesUsuarios")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequiredArgsConstructor
public class PlanUsuarioController {

    @Autowired
    private PlanUsuarioService planUsuarioService;

    @Operation(summary = "Crea un PlanUsuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crea un PlanUsuario correctamente"),
            @ApiResponse(responseCode = "404", description = "Creacion de PlanUsuario sin exito")
    })
    @PostMapping("/createPlanUsuario")
    public ResponseEntity<ResponseFirestore> createPlanUsuario(
            @RequestBody PlanUsuario planUsuario) throws Exception {
        ResponseFirestore resp = planUsuarioService.createPlanUsuario(planUsuario);
        return new ResponseEntity<ResponseFirestore>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un PlanUsuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene un PlanUsuario correctamente"),
            @ApiResponse(responseCode = "404", description = "PlanUsuario inexistente")
    })
    @GetMapping("/getPlanUsuario/{run}/{idPlan}")
    public ResponseEntity<PlanUsuario> getPlanUsuario(
            @PathVariable String run,
            @PathVariable String idPlan) throws Exception {
        PlanUsuario resp = planUsuarioService.getPlanUsuario(run, idPlan);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una lista de PlanesUsuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene lista PlanesUsuarios correctamente"),
            @ApiResponse(responseCode = "404", description = "PlanesUsuarios inexistentes")
    })
    @GetMapping("/listPlanUsuario")
    public ResponseEntity<List<PlanUsuario>> listColaboradores() throws Exception {
        List<PlanUsuario> resp = planUsuarioService.listPlanesUsuarios();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Modifica un PlanUsuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modificó un PlanUsuario correctamente"),
            @ApiResponse(responseCode = "404", description = "Modificación de PlanUsuario sin exito")
    })
    @PutMapping("/updatePlanUsuario")
    public ResponseEntity<ResponseFirestore> updatePlanUsuario(
            @RequestBody PlanUsuario planUsuario) throws Exception {
        ResponseFirestore resp = planUsuarioService.updatePlanUsuario(planUsuario);
        return new ResponseEntity<ResponseFirestore>(resp, HttpStatus.OK);
    }


}
