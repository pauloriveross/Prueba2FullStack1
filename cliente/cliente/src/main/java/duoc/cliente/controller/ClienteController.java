package duoc.cliente.controller;

import duoc.cliente.dto.ClienteRequest;
import duoc.cliente.model.Clientes;
import duoc.cliente.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Clientes>> listar(){
        List<Clientes> clientes = clienteService.listarTodos();
        if (clientes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Clientes> buscarPorId(@PathVariable Integer idCliente){
        Clientes clientes = clienteService.buscarPorId(idCliente);
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<Clientes> guardar(@Valid @RequestBody ClienteRequest
                                            request){
        Clientes clienteGuardado = clienteService.guardarCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteGuardado);
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<Clientes> actualizar(@PathVariable Integer idCliente, @Valid
                                               @RequestBody ClienteRequest request){
        Clientes clienteActualizado = clienteService.actualizar(idCliente, request);
        return ResponseEntity.ok(clienteActualizado);
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

}
