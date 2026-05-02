package duoc.vehiculo.controller;

import duoc.vehiculo.dto.VehiculoRequest;
import duoc.vehiculo.model.Vehiculo;
import duoc.vehiculo.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public ResponseEntity<List<Vehiculo>> listar() {
        List<Vehiculo> vehiculos = vehiculoService.listarVehiculos();
        if (vehiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vehiculos);
    }

    @GetMapping("/{idVehiculo}")
    public ResponseEntity<Vehiculo> buscarPorId(@PathVariable Integer idVehiculo) {
        Vehiculo vehiculo = vehiculoService.buscarPorId(idVehiculo);
        return ResponseEntity.ok(vehiculo);
    }

    @PostMapping
    public ResponseEntity<Vehiculo> guardar(@Valid @RequestBody VehiculoRequest vehiculoRequest) {
        Vehiculo vehiculoPost = vehiculoService.crearVehiculo(vehiculoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoPost);
    }

    @PutMapping("/{idVehiculo}")
    public ResponseEntity<Vehiculo> actualizar(@PathVariable Integer idVehiculo, @Valid
    @RequestBody VehiculoRequest vehiculoRequest) {
        Vehiculo vehiculoPut = vehiculoService.actualizar(idVehiculo,vehiculoRequest);
        return ResponseEntity.ok(vehiculoPut);
    }

    @DeleteMapping("/{idVehiculo}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer idVehiculo) {
        vehiculoService.eliminar(idVehiculo);
        return ResponseEntity.noContent().build();
    }
}
