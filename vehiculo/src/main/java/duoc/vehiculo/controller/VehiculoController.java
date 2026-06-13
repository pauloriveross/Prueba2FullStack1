package duoc.vehiculo.controller;

import duoc.vehiculo.dto.VehiculoRequest;
import duoc.vehiculo.model.Vehiculo;
import duoc.vehiculo.service.VehiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;

import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {


    private final VehiculoService vehiculoService;

    @GetMapping("/listar")
    public CollectionModel<EntityModel<Vehiculo>> listarVehiculos(){
        List<EntityModel<Vehiculo>> vehiculos = vehiculoService.listarVehiculos().stream().map(this::toModel).toList();
        return CollectionModel.of(vehiculos,linkTo(methodOn(VehiculoController.class).listarVehiculos()).withSelfRel());
    }


    @GetMapping("/{idVehiculo}")
    public EntityModel<Vehiculo> buscarPorId(@PathVariable Integer idVehiculo) {
        return toModel(vehiculoService.buscarPorId(idVehiculo));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Vehiculo>> guardar(@Valid @RequestBody VehiculoRequest vehiculoRequest) {
        Vehiculo vehiculoPost = vehiculoService.crearVehiculo(vehiculoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(vehiculoPost));
    }

    @PutMapping("/{idVehiculo}")
    public EntityModel<Vehiculo> actualizar(@PathVariable Integer idVehiculo, @Valid
    @RequestBody VehiculoRequest vehiculoRequest) {
      return toModel(vehiculoService.actualizar(idVehiculo,vehiculoRequest));
    }

    @DeleteMapping("/{idVehiculo}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer idVehiculo) {
        vehiculoService.eliminar(idVehiculo);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<Vehiculo> toModel(Vehiculo vehiculo) {
        return EntityModel.of(vehiculo,
                // Asumiendo que tu entidad Vehiculo usa getId() para su clave primaria
                linkTo(methodOn(VehiculoController.class).buscarPorId(vehiculo.getIdVehiculo())).withSelfRel(),
                linkTo(methodOn(VehiculoController.class).listarVehiculos()).withRel("todos"));
    }
}
