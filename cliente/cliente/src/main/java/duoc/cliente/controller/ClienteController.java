package duoc.cliente.controller;

import duoc.cliente.dto.ClienteRequest;
import duoc.cliente.model.Clientes;
import duoc.cliente.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public CollectionModel<EntityModel<Clientes>> listar(){
        List<EntityModel<Clientes>> clientes = clienteService.listarTodos().stream().map(this::toModel).toList();
        return CollectionModel.of(clientes,linkTo(methodOn(ClienteController.class).listar()).withSelfRel());
    }

    @GetMapping("/{idCliente}")
    public EntityModel<Clientes> buscarPorId(@PathVariable Integer idCliente){
        return toModel(clienteService.buscarPorId(idCliente));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Clientes>>  guardar(@Valid @RequestBody ClienteRequest
                                            request){
        Clientes clienteGuardado = clienteService.guardarCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(clienteGuardado));
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<EntityModel<Clientes>> actualizar(@PathVariable Integer idCliente, @Valid
                                               @RequestBody ClienteRequest request){
        Clientes clienteActualizado = clienteService.actualizar(idCliente, request);
        return ResponseEntity.ok(toModel(clienteActualizado));
    }
    
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer idCliente) {
        clienteService.eliminarCliente(idCliente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/error")
    public ResponseEntity<Void> simularError(){
        clienteService.simularError();
        return ResponseEntity.ok().build();
    }
    private EntityModel<Clientes> toModel(Clientes clientes){
        return EntityModel.of(clientes,
                linkTo(methodOn(ClienteController.class).buscarPorId(clientes.getIdCliente())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).listar()).withRel("todos"));

    }
}
