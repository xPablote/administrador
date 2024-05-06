package cl.lafabrica.administrador.controller;

import cl.lafabrica.administrador.modelo.TipoPlan;
import cl.lafabrica.administrador.pojo.response.ResponseFirestoreTipoPlan;
import cl.lafabrica.administrador.service.TipoPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tipoPlan")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class TipoPlanController {

    @Autowired
    private TipoPlanService tipoPlanService;

    @Operation(summary = "Crea un TipoPlan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crea un TipoPlan correctamente"),
            @ApiResponse(responseCode = "404", description = "Creacion de TipoPlan sin exito")
    })
    @PostMapping("/createTipoPlan")
    public ResponseEntity<ResponseFirestoreTipoPlan> createTipoPlan(
            @RequestBody TipoPlan tipoPlan) throws Exception {
        ResponseFirestoreTipoPlan resp = tipoPlanService.createTipoPlan(tipoPlan);
        return new ResponseEntity<ResponseFirestoreTipoPlan>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un TipoPlan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene un TipoPlan correctamente"),
            @ApiResponse(responseCode = "404", description = "TipoPlan inexistente")
    })
    @GetMapping("/getTipoPlan/{id}")
    public ResponseEntity<TipoPlan> getTipoPlan(
            @PathVariable String id) throws Exception {
        TipoPlan resp = tipoPlanService.getTipoPlan(id);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una lista de TipoPlan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene lista TipoPlan correctamente"),
            @ApiResponse(responseCode = "404", description = "TipoPlan inexistentes")
    })
    @GetMapping("/listTipoPlan")
    public ResponseEntity<List<TipoPlan>> listTipoPlanes() throws Exception {
        List<TipoPlan> resp = tipoPlanService.listTipoPlanes();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Modifica un TipoPlan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modificó un TipoPlan correctamente"),
            @ApiResponse(responseCode = "404", description = "Modificación de TipoPlan sin exito")
    })
    @PutMapping("/updateTipoPlan")
    public ResponseEntity<ResponseFirestoreTipoPlan> updateTipoPlan(
            @RequestBody TipoPlan tipoPlan) throws Exception {
        ResponseFirestoreTipoPlan resp = tipoPlanService.updateTipoPlan(tipoPlan);
        return new ResponseEntity<ResponseFirestoreTipoPlan>(resp, HttpStatus.OK);
    }



}
