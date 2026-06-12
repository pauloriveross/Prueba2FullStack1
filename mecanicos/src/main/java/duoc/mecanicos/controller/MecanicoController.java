package duoc.mecanicos.controller;

import duoc.mecanicos.dto.MecanicoRequest;
import duoc.mecanicos.model.Mecanico;
import duoc.mecanicos.service.MecanicoService;
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
@RequestMapping("api/v1/mecanicos")
@RequiredArgsConstructor
public class MecanicoController {


    private  final MecanicoService mecanicoService;

    //Listar Todos Los Mecánicos
    @GetMapping("/listar")
    public CollectionModel<EntityModel<Mecanico>>listarMecanicos(){
        List<EntityModel<Mecanico>> mecanicos = mecanicoService.listarTodos().stream().map(this::toModel).toList();
        return CollectionModel.of(mecanicos,linkTo(methodOn(MecanicoController.class).listarMecanicos()).withSelfRel());
    }

    //Buscar Mecanico por ID
    @GetMapping("/{id}")
    public EntityModel<Mecanico>buscarPorId(@PathVariable Integer id){
        return toModel(mecanicoService.buscarPorId(id));
    }



    //Agregar Nuevo Mecanico
    @PostMapping
   public ResponseEntity<EntityModel<Mecanico>> agregarMecanico(@Valid @RequestBody MecanicoRequest mecanicoRequest){
        Mecanico mecanicoNuevo = mecanicoService.guardarMecanico(mecanicoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(mecanicoNuevo));
    }


    //Eliminar Mecanico por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMecanico(@PathVariable Integer id) {
        mecanicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


    //Error simulado
    @GetMapping("/error")
    public ResponseEntity<Void>simularError(){
        mecanicoService.simularError();;
        return ResponseEntity.ok().build();
    }


    // Actualizar Mecanico
    @PutMapping("/{id}")
    public EntityModel<Mecanico>actualizarMecanico (@PathVariable Integer id , @Valid @RequestBody MecanicoRequest mecanicoRequest){
        return toModel(mecanicoService.actualizar(id, mecanicoRequest));
    }


    private EntityModel<Mecanico>toModel(Mecanico mecanico){
        return EntityModel.of(mecanico,
                linkTo(methodOn(MecanicoController.class).buscarPorId(mecanico.getIdMecanico())).withSelfRel(),
                linkTo(methodOn(MecanicoController.class).listarMecanicos()).withRel("todos"));


    }


}
