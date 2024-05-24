package cl.lafabrica.administrador.controller;

import cl.lafabrica.administrador.model.Pago;
import cl.lafabrica.administrador.response.ResponseFirestore;
import cl.lafabrica.administrador.service.PagosService;
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
@RequestMapping(value = "/pagos")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequiredArgsConstructor
public class PagoController {

    @Autowired
    private PagosService pagosService;

    @Operation(summary = "Crea un Pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crea un Pago correctamente"),
            @ApiResponse(responseCode = "404", description = "Creacion de Pago sin exito")
    })
    @PostMapping("/createPago")
    public ResponseEntity<ResponseFirestore> createPago(
            @RequestBody Pago pago) throws Exception {
        ResponseFirestore resp = pagosService.createPago(pago);
        return new ResponseEntity<ResponseFirestore>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un Pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene un Pago correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago inexistente")
    })
    @GetMapping("/getPago/{run}/{idPlan}")
    public ResponseEntity<Pago> getPago(
            @PathVariable String run,
            @PathVariable String idPlan) throws Exception {
        Pago resp = pagosService.getPago(run, idPlan);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una lista de Pagos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene lista Pagos correctamente"),
            @ApiResponse(responseCode = "404", description = "Pagos inexistentes")
    })
    @GetMapping("/listPagos")
    public ResponseEntity<List<Pago>> listPagos() throws Exception {
        List<Pago> resp = pagosService.listPagos();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Operation(summary = "Modifica un Pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modificó un Pago correctamente"),
            @ApiResponse(responseCode = "404", description = "Modificación de Pago sin exito")
    })
    @PutMapping("/updatePago")
    public ResponseEntity<ResponseFirestore> updatePago(
            @RequestBody Pago pago) throws Exception {
        ResponseFirestore resp = pagosService.updatePago(pago);
        return new ResponseEntity<ResponseFirestore>(resp, HttpStatus.OK);
    }


}
