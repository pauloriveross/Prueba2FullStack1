package duoc.vendedor.controller;


import duoc.vendedor.dto.VendedorRequest;
import duoc.vendedor.model.Vendedor;
import duoc.vendedor.service.VendedorService;
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
@RequestMapping("api/v1/vendedores")
@RequiredArgsConstructor
public class VendedorController {

    private final VendedorService vendedorService;

    //Listar Todos Los Vendedores
    @GetMapping("/listar")
    public CollectionModel<EntityModel<Vendedor>>listarVendedores(){
        List<EntityModel<Vendedor>> vendedores = vendedorService.listarTodos().stream().map(this::toModel).toList();
        return CollectionModel.of(vendedores,linkTo(methodOn(VendedorController.class).listarVendedores()).withSelfRel());
    }

    //Buscar Vendedor por ID
    @GetMapping("/{id}")
    public EntityModel<Vendedor> buscarPorId(@PathVariable Integer id){
        return toModel(vendedorService.buscarporId(id));
    }


    //Agregar Nuevo Vendedor
    @PostMapping
    public ResponseEntity<EntityModel<Vendedor>> agregarVendedor(@Valid @RequestBody VendedorRequest vendedorRequest){
        Vendedor vendedorNuevo= vendedorService.guardarVendedor(vendedorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(vendedorNuevo));
    }


    //Eliminar Vendedor por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVendedor(@PathVariable Integer id) {
        vendedorService.eliminarVendedor(id);
        return ResponseEntity.noContent().build();
    }


    //Error Simulado
    @GetMapping("/error")
    public ResponseEntity<Void>simularError(){
        vendedorService.simularError();;
        return ResponseEntity.ok().build();
    }


    // Actualizar Vendedor
    @PutMapping("/{id}")
    public EntityModel<Vendedor> actualizar (@PathVariable Integer id , @Valid @RequestBody VendedorRequest vendedorRequest){
        return toModel(vendedorService.actualizarVendedor(id,vendedorRequest));
    }



    private EntityModel<Vendedor> toModel(Vendedor vendedor){
        return EntityModel.of(vendedor,
                linkTo(methodOn(VendedorController.class).buscarPorId(vendedor.getIdVendedor())).withSelfRel(),
                linkTo(methodOn(VendedorController.class).listarVendedores()).withRel("todos"));
    }

}