package cl.lafabrica.administrador.controller;

import cl.lafabrica.administrador.model.Estado;
import cl.lafabrica.administrador.model.Plan;
import cl.lafabrica.administrador.response.ResponseFirestore;
import cl.lafabrica.administrador.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping(value = "/planes")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequiredArgsConstructor
public class PlanController {

    @Autowired
    private PlanService planService;

    @Operation(summary = "Crea un Plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crea un Plan correctamente"),
            @ApiResponse(responseCode = "404", description = "Creacion de Plan sin exito")
    })
    @PostMapping("/createPlan")
    public ResponseEntity<ResponseFirestore> createPlan(
            @RequestBody Plan plan) throws Exception {
        ResponseFirestore resp = planService.createPlan(plan);
        return new ResponseEntity<ResponseFirestore>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un Plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene un Plan correctamente"),
            @ApiResponse(responseCode = "404", description = "Plan inexistente")
    })
    @GetMapping("/getPlan")
    public ResponseEntity<Plan> getPlan(
            @RequestParam String nombrePlan) throws Exception {
        String cadenaDecodificada = URLDecoder.decode(nombrePlan, StandardCharsets.UTF_8.name());
        Plan resp = planService.getPlan(cadenaDecodificada);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una lista de Plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene lista Plan correctamente"),
            @ApiResponse(responseCode = "404", description = "Plan inexistentes")
    })
    @GetMapping("/listPlanes")
    public ResponseEntity<List<Plan>> listPlanes() throws Exception {
        List<Plan> resp = planService.listPlanes();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Modifica un Plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modificó un Plan correctamente"),
            @ApiResponse(responseCode = "404", description = "Modificación de Plan sin exito")
    })
    @PutMapping("/updatePlan")
    public ResponseEntity<ResponseFirestore> updatePlan(
            @RequestBody Plan plan) throws Exception {
        ResponseFirestore resp = planService.updatePlan(plan);
        return new ResponseEntity<ResponseFirestore>(resp, HttpStatus.OK);
    }
    @Operation(summary = "Modifica estado de un Plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modificó estado de un Plan correctamente"),
            @ApiResponse(responseCode = "404", description = "Modificación cambio de Plan sin exito")
    })
    @PutMapping("/changeStatePlan/{nombrePlan}/{estado}")
    public ResponseEntity<ResponseFirestore> changeStatePlan(
            @PathVariable String nombrePlan,
            @PathVariable Estado estado) throws Exception {
        ResponseFirestore resp = planService.changeStatePlan(nombrePlan,estado);
        return new ResponseEntity<ResponseFirestore>(resp, HttpStatus.OK);
    }
}
