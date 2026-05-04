package duoc.ventas.controller;


import duoc.ventas.dto.VentaRequest;
import duoc.ventas.model.Venta;
import duoc.ventas.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    //Listar Ventas
    @GetMapping()
    public ResponseEntity<List<Venta>>listarVentas(){
        List<Venta>ventas = ventaService.listarVentas();
        if(ventas.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ventas);
    }


    //Buscar Venta ID
    @GetMapping("/{id}")
    public ResponseEntity<Venta>buscarPorId(@PathVariable Integer id){
        Venta venta = ventaService.buscarVentaPorId(id);
        return ResponseEntity.ok(venta);
    }

    //Registrar Venta
    @PostMapping
    public ResponseEntity<Venta>agregarVenta(@Valid @RequestBody VentaRequest request,
                                             @RequestHeader("Authorization") String token){
        Venta ventaNueva = ventaService.guardarVenta(request,token);
        return ResponseEntity.ok(ventaNueva);

    }

    //Eliminar Venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Integer id ){
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }

    //Actualizar Venta
    @PutMapping("{id}")
    public ResponseEntity<Venta>actualizarVenta(@PathVariable Integer id, @Valid @RequestBody VentaRequest request,
                                                @RequestHeader("Authorization")String token){
        Venta ventaUpdate = ventaService.actualizarVenta(id,request,token);
        return ResponseEntity.ok(ventaUpdate);
    }


}
