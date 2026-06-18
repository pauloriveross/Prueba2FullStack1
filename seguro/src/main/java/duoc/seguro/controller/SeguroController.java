package duoc.seguro.controller;

import duoc.seguro.dto.SeguroRequest;
import duoc.seguro.model.Seguro;
import duoc.seguro.service.SeguroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1/seguros")
@RequiredArgsConstructor
public class SeguroController {

    private final SeguroService seguroService;

    //Get todo
    @GetMapping()
    public CollectionModel<EntityModel<Seguro>> listarSeguro(){
        List<EntityModel<Seguro>> seguro = seguroService.listarSeguro().stream().map(this::toModel).toList();
        return CollectionModel.of(seguro,linkTo(methodOn(SeguroController.class).listarSeguro()).withSelfRel());

    }

    //Get por ID
    @GetMapping("/{idSeguro}")
    public EntityModel<Seguro> buscarSeguro(@PathVariable Integer idSeguro ){
        return toModel(seguroService.buscarSeguroPorId(idSeguro));
    }

    private EntityModel<Seguro> toModel(Seguro seguro){
        return EntityModel.of(seguro,
                linkTo(methodOn(SeguroController.class).buscarSeguro(seguro.getIdSeguro())).withSelfRel(),
                linkTo(methodOn(SeguroController.class).listarSeguro()).withRel("todos"));

    }

    //Post
    @PostMapping
    public ResponseEntity<EntityModel<Seguro>> crearVenta(@Valid @RequestBody SeguroRequest seguroRequest){
        Seguro seguro = seguroService.guardarSeguro(seguroRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(seguro));
    }

    //Put
    @PutMapping("/{idSeguro}")
    public ResponseEntity<EntityModel<Seguro>>actualizarVenta(@PathVariable Integer idSeguro , @Valid @RequestBody SeguroRequest seguroRequest){
        Seguro seguroActualizado = seguroService.actualizarSeguro(idSeguro, seguroRequest);
        return ResponseEntity.ok(toModel(seguroActualizado));
    }

    //Delete
    @DeleteMapping("/{idSeguro}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Integer idSeguro ){
        seguroService.eliminarSeguro(idSeguro);
        return ResponseEntity.noContent().build();
    }
}