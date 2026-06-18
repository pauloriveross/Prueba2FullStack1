package duoc.corredorSeguro.controller;


import duoc.corredorSeguro.dto.CorredorSeguroRequest;
import duoc.corredorSeguro.model.CorredorSeguro;
import duoc.corredorSeguro.service.CorredorSeguroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;



import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("api/v1/corredores")
@RequiredArgsConstructor
public class CorredorSeguroController {


    private final CorredorSeguroService service;


    //Listar todos los corredores
    @GetMapping("/listar")
    public CollectionModel<EntityModel<CorredorSeguro>>listarCorredores(){
        List<EntityModel<CorredorSeguro>> corredores = service.listarTodos().stream().map(this::toModel).toList();
        return CollectionModel.of(corredores,linkTo(methodOn(CorredorSeguroController.class).listarCorredores()).withSelfRel());
    }


    //Buscar Corredor por ID
    @GetMapping("/{id}")
    public EntityModel<CorredorSeguro>buscarCorredorPorId(@PathVariable Integer id){
        return toModel(service.buscarPorId(id));
    }


    //Agregar Corredor
    @PostMapping
    public ResponseEntity<EntityModel<CorredorSeguro>>agregarCorredor (@Valid @RequestBody CorredorSeguroRequest request){

        CorredorSeguro corredor = service.guardarCorredor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(corredor));

    }


    //Eliminar Corredor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminarCorredor(id);
        return ResponseEntity.noContent().build();
    }


    //Actualizar

    @PutMapping("/{id}")
    public EntityModel<CorredorSeguro> actualizar (@PathVariable Integer id , @Valid @RequestBody CorredorSeguroRequest corredorRequest){
        return toModel(service.actualizarCorredor(id,corredorRequest));
    }


    private EntityModel<CorredorSeguro>toModel(CorredorSeguro corredorSeguro){
        return EntityModel.of(corredorSeguro,
                linkTo(methodOn(CorredorSeguroController.class).buscarCorredorPorId(corredorSeguro.getIdCorredor())).withSelfRel(),
                linkTo(methodOn(CorredorSeguroController.class).listarCorredores()).withRel("todos"));
    }
}
