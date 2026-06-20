package duoc.controller;

import duoc.dto.TestDriveRequest;
import duoc.model.TestDrive;
import duoc.service.TestDriveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1/testdrives")
@RequiredArgsConstructor

public class TestDriveController {

    private final TestDriveService testDriveService;

    private EntityModel<TestDrive> toModel(TestDrive testDrive){
        return EntityModel.of(testDrive,
                linkTo(methodOn(TestDriveController.class).buscarTestDrive(testDrive.getIdTestDrive())).withSelfRel(),
                linkTo(methodOn(TestDriveController.class).listarTestDrive()).withRel("todos"));

    }

    //Get Listar Test Drive
    @GetMapping()
    public CollectionModel<EntityModel<TestDrive>> listarTestDrive(){
        List<EntityModel<TestDrive>> testDrive = testDriveService.listarTestDrive().stream().map(this::toModel).toList();
        return CollectionModel.of(testDrive,linkTo(methodOn(TestDriveController.class).listarTestDrive()).withSelfRel());

    }

    //Get por ID
    @GetMapping("/{idTestDrive}")
    public EntityModel<TestDrive> buscarTestDrive(@PathVariable Integer idTestDrive ){
        return toModel(testDriveService.buscarTestDrivePorId(idTestDrive));
    }

    // Crear Test Drive
    @PostMapping
    public ResponseEntity<EntityModel<TestDrive>> crearTestDrive(@Valid @RequestBody TestDriveRequest TestDriveRequest,
                                                                 @RequestHeader("Authorization") String token){
        TestDrive testDrive = testDriveService.guardarTestDrive(TestDriveRequest,token);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(testDrive));
    }

    // Actualizar TestDrive
    @PutMapping("/{idTestDrive}")
    public ResponseEntity<EntityModel<TestDrive>>actualizarTestDrive(@PathVariable Integer idTestDrive , @Valid @RequestBody TestDriveRequest TestDriveRequest,
                                                                     @RequestHeader("Authorization") String token){
        TestDrive TestDriveActualizado = testDriveService.actualizarTestDrive(idTestDrive, TestDriveRequest,token);
        return ResponseEntity.ok(toModel(TestDriveActualizado));
    }

    //Eliminar TestDrive
    @DeleteMapping("/{idTestDrive}")
    public ResponseEntity<Void> eliminarTestDrive(@PathVariable Integer idTestDrive ){
        testDriveService.eliminarTestDrive(idTestDrive);
        return ResponseEntity.noContent().build();
    }
}
