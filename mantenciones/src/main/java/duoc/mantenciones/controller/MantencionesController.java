package duoc.mantenciones.controller;

import duoc.mantenciones.dto.MantencionesRequest;
import duoc.mantenciones.model.Mantenciones;
import duoc.mantenciones.service.MantencionesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/v1/mantenciones")
public class MantencionesController {

    @Autowired
    private MantencionesService mantencionesService;

    @GetMapping
    public ResponseEntity<List<Mantenciones>> listar(){
        List<Mantenciones> mantenciones = mantencionesService.listarTodos();
        if (mantenciones.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mantenciones);
    }

    @GetMapping("/{idMantencion}")
    public ResponseEntity<Mantenciones> buscarPorId(@PathVariable Integer idMantencion){
        Mantenciones mantenciones = mantencionesService.buscarPorId(idMantencion);
        return ResponseEntity.ok(mantenciones);
    }

    @PostMapping
    public ResponseEntity<Mantenciones> guardar(@Valid @RequestBody MantencionesRequest
                                                request){
        Mantenciones mantencionGuardado = mantencionesService.guardarMantencion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mantencionGuardado);
    }

    @PutMapping("/{idMantencion}")
    public ResponseEntity<Mantenciones>actualizarMantencion(@PathVariable Integer idMantencion, @Valid @RequestBody MantencionesRequest request){
        Mantenciones mantencionesUpdate = mantencionesService.actualizarMantencion(idMantencion, request);
        return ResponseEntity.ok(mantencionesUpdate);
    }

    @DeleteMapping("/{idMantencion}")
    public ResponseEntity<Void> eliminarMantencion(@PathVariable Integer idMantencion){
        mantencionesService.eliminarMantencion(idMantencion);
        return ResponseEntity.noContent().build();
    }
}
