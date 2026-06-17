package duoc.mantenciones.controller;

import duoc.mantenciones.dto.MantencionRequest;
import duoc.mantenciones.model.Mantencion;
import duoc.mantenciones.service.MantencionService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@RestController
@RequestMapping("/api/v1/mantenciones")
@RequiredArgsConstructor
public class MantencionController {

    private final MantencionService mantencionService;

    //listar todas las mantenciones
    @GetMapping("/listar")
    public CollectionModel<EntityModel<Mantencion>> listarMantenciones(){
        List<EntityModel<Mantencion>> mantenciones = mantencionService.listarTodos().stream().map(this::toModel).toList();
        return CollectionModel.of(mantenciones,linkTo(methodOn(MantencionController.class).listarMantenciones()).withSelfRel());
    }

    @GetMapping("/{idMantencion}")
    public EntityModel<Mantencion> buscarPorId(@PathVariable Integer idMantencion){
        return toModel(mantencionService.buscarPorId(idMantencion));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Mantencion>> guardar(@Valid@RequestBody MantencionRequest mantencionRequest,
                                                           @RequestHeader ("Authorization")String token){
        Mantencion mantencionPost = mantencionService.guardarMantencion(mantencionRequest,token);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(mantencionPost));
    }

    @PutMapping("/{idMantencion}")
    public EntityModel<Mantencion> actualizar(
            @PathVariable Integer idMantencion,
            @Valid @RequestBody MantencionRequest mantencionRequest,
            @RequestHeader("Authorization") String token) {

        return toModel(mantencionService.actualizarMantencion(idMantencion, mantencionRequest, token));
    }

    @DeleteMapping("/{idMantencion}")
    public ResponseEntity<Void> eliminarMantencion(@PathVariable Integer idMantencion){
        mantencionService.eliminarMantencion(idMantencion);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<Mantencion> toModel(Mantencion mantencion) {
        return EntityModel.of(mantencion,
                linkTo(methodOn(MantencionController.class).buscarPorId(mantencion.getIdMantencion())).withSelfRel(),
                linkTo(methodOn(MantencionController.class).listarMantenciones()).withRel("todos")
        );
    }
}
