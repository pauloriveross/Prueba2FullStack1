package duoc.vendedor.controller;


import duoc.vendedor.dto.VendedorRequest;
import duoc.vendedor.model.Vendedor;
import duoc.vendedor.service.VendedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vendedores")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    //Listar Todos Los Vendedores
    @GetMapping("/listar")
    public ResponseEntity<List<Vendedor>> listarVendedorres() {
        List<Vendedor> vendedores = vendedorService.listarTodos();

        if (vendedores.isEmpty()) {
            return ResponseEntity.noContent().build();

        }

        return ResponseEntity.ok(vendedores);
    }

    //Buscar Vendedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> buscarPorId(@PathVariable Integer id) {
        Vendedor vendedor = vendedorService.buscarporId(id);
        return ResponseEntity.ok(vendedor);
    }

    
    //Agregar Nuevo Vendedor
    @PostMapping
    public ResponseEntity<Vendedor> guardar(@Valid @RequestBody VendedorRequest request) {
        Vendedor vendedorGuardado = vendedorService.guardarVendedor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendedorGuardado);
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
    public ResponseEntity<Vendedor>actualizarVendedor(@PathVariable Integer id, @Valid @RequestBody VendedorRequest request){
        Vendedor vendedorUpdate= vendedorService.actualizarVendedor(id,request);
        return ResponseEntity.ok(vendedorUpdate);
    }









}
