package duoc.mecanicos.controller;

import duoc.mecanicos.dto.MecanicoRequest;
import duoc.mecanicos.model.Mecanico;
import duoc.mecanicos.service.MecanicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/mecanicos")

public class MecanicoController {

    @Autowired
    private MecanicoService mecanicoService;

    //Listar Todos Los Mecánicos
    @GetMapping("/listar")
    public ResponseEntity<List<Mecanico>> listarMecanicos() {
        List<Mecanico> mecanicos = mecanicoService.listarTodos();

        if (mecanicos.isEmpty()) {
            return ResponseEntity.noContent().build();

        }

        return ResponseEntity.ok(mecanicos);
    }

    //Buscar Mecanico por ID
    @GetMapping("/{id}")
    public ResponseEntity<Mecanico> buscarPorId(@PathVariable Integer id) {
        Mecanico mecanico = mecanicoService.buscarPorId(id);
        return ResponseEntity.ok(mecanico);
    }


    //Agregar Nuevo Mecanico
    @PostMapping
    public ResponseEntity<Mecanico> guardar(@Valid @RequestBody MecanicoRequest request) {
        Mecanico mecanicoGuardado = mecanicoService.guardarMecanico(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mecanicoGuardado);
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
    public ResponseEntity<Mecanico>actualizarMecanico(@PathVariable Integer id, @Valid @RequestBody MecanicoRequest request){
        Mecanico mecanicoUpdate = mecanicoService.actualizar(id,request);
        return ResponseEntity.ok(mecanicoUpdate);
    }


}
