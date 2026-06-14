package duoc.ventas.controller;


import duoc.ventas.dto.VentaRequest;
import duoc.ventas.model.Venta;
import duoc.ventas.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;

import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("api/v1/ventas")
@RequiredArgsConstructor
public class VentaController {

    private  final VentaService ventaService;

    //Listar Ventas
    @GetMapping()
    public CollectionModel<EntityModel<Venta>>listarVentas(){
        List<EntityModel<Venta>> ventas = ventaService.listarVentas().stream().map(this::toModel).toList();
        return CollectionModel.of(ventas,linkTo(methodOn(VentaController.class).listarVentas()).withSelfRel());

    }


    //Buscar Venta ID
    @GetMapping("/{id}")
    public EntityModel<Venta> buscarVenta(@PathVariable Integer id ){
        return toModel(ventaService.buscarVentaPorId(id));
    }

    //Registrar Venta
    @PostMapping
    public ResponseEntity<EntityModel<Venta>>crearVenta(@Valid @RequestBody VentaRequest ventaRequest,
                                                        @RequestHeader("Authorization") String token){
        Venta ventaNueva = ventaService.guardarVenta(ventaRequest,token);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(ventaNueva));
    }


    //Eliminar Venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Integer id ){
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }

    //Actualizar Venta
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Venta>>actualizarVenta(@PathVariable Integer id , @Valid @RequestBody VentaRequest ventaRequest ,
                                                             @RequestHeader("Authorization") String token){

        Venta ventaUpdate = ventaService.actualizarVenta(id,ventaRequest,token);

        return ResponseEntity.ok(toModel(ventaUpdate));
    }


    private EntityModel<Venta> toModel(Venta venta){
        return EntityModel.of(venta,
                linkTo(methodOn(VentaController.class).buscarVenta(venta.getIdVenta())).withSelfRel(),
                linkTo(methodOn(VentaController.class).listarVentas()).withRel("todos"));

    }


}
