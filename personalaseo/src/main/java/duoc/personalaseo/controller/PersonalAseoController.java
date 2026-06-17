package duoc.personalaseo.controller;

import duoc.personalaseo.dto.PersonalAseoRequest;
import duoc.personalaseo.model.PersonalAseo;
import duoc.personalaseo.service.PersonalAseoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("api/v1/personalaseo")
@RequiredArgsConstructor
public class PersonalAseoController {

    private final PersonalAseoService personalAseoService;

    // Listar Todos
    @GetMapping("/listar")
    public CollectionModel<EntityModel<PersonalAseo>> listarPersonalAseo(){
        List<EntityModel<PersonalAseo>> personalAseo = personalAseoService.listarTodos()
                .stream()
                .map(this::toModel)
                .toList();
        return CollectionModel.of(personalAseo, linkTo(methodOn(PersonalAseoController.class).listarPersonalAseo()).withSelfRel());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public EntityModel<PersonalAseo> buscarPorId(@PathVariable Integer id){
        return toModel(personalAseoService.buscarPorId(id));
    }

    // Agregar un nuevo personal
    @PostMapping
    public ResponseEntity<EntityModel<PersonalAseo>> agregarPersonalAseo(@Valid @RequestBody PersonalAseoRequest personalAseoRequest){
        PersonalAseo personalNuevo = personalAseoService.guardarPersonalAseo(personalAseoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(personalNuevo));
    }

    // Eliminar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersonalAseo(@PathVariable Integer id){
        personalAseoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar personal
    @PutMapping("/{id}")
    public EntityModel<PersonalAseo> actualizarPersonalAseo(@PathVariable Integer id, @Valid @RequestBody PersonalAseoRequest personalAseoRequest){
        return toModel(personalAseoService.actualizar(id, personalAseoRequest));
    }

    private EntityModel<PersonalAseo> toModel(PersonalAseo personalAseo){
        return EntityModel.of(
                personalAseo,
                linkTo(methodOn(PersonalAseoController.class).buscarPorId(personalAseo.getIdPersonalAseo())).withSelfRel(),
                linkTo(methodOn(PersonalAseoController.class).listarPersonalAseo()).withRel("todos")
        );
    }
}