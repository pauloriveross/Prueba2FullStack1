package duoc.mantenciones.controller;

import duoc.mantenciones.dto.MantencionRequest;
import duoc.mantenciones.model.Mantencion;
import duoc.mantenciones.service.MantencionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mantenciones")
public class MantencionController {

    @Autowired
    private MantencionService mantencionService;

    @GetMapping
    public ResponseEntity<List<Mantencion>> listar(){
        List<Mantencion> mantenciones = mantencionService.listarTodos();
        if (mantenciones.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mantenciones);
    }

    @GetMapping("/{idMantencion}")
    public ResponseEntity<Mantencion> buscarPorId(@PathVariable Integer idMantencion){
        Mantencion mantencion = mantencionService.buscarPorId(idMantencion);
        return ResponseEntity.ok(mantencion);
    }

    @PostMapping
    public ResponseEntity<Mantencion> guardar(@Valid @RequestBody MantencionRequest
                                                request, @RequestHeader("Authorization")String token){
        Mantencion mantencionGuardado = mantencionService.guardarMantencion(request,token);
        return ResponseEntity.status(HttpStatus.CREATED).body(mantencionGuardado);
    }

    @PutMapping("/{idMantencion}")
    public ResponseEntity<Mantencion>actualizarMantencion(@PathVariable Integer idMantencion, @Valid @RequestBody MantencionRequest request,
                                                          @RequestHeader("Authorization")String token){
        Mantencion mantencionUpdate = mantencionService.actualizarMantencion(idMantencion, request, token);
        return ResponseEntity.ok(mantencionUpdate);
    }

    @DeleteMapping("/{idMantencion}")
    public ResponseEntity<Void> eliminarMantencion(@PathVariable Integer idMantencion){
        mantencionService.eliminarMantencion(idMantencion);
        return ResponseEntity.noContent().build();
    }
}
