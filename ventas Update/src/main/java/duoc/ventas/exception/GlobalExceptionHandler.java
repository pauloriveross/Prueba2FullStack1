package duoc.ventas.exception;
import duoc.ventas.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler  {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> manejarValidacion(MethodArgumentNotValidException ex,
                                                                HttpServletRequest request){

        Map<String,String >errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("fecha", LocalDateTime.now());
        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        respuesta.put("ruta", request.getRequestURI());
        respuesta.put("mensajes", errores);

        log.warn("Error de validación en la ruta {}", request.getRequestURI());
        return ResponseEntity.badRequest().body(respuesta);

    }


    @ExceptionHandler(IdVehiculoNoEncontrado.class)
    public ResponseEntity<ErrorResponse> ManejarIdVehiculoNoEncontrado(IdVehiculoNoEncontrado ex,
                                                                     HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        log.warn("{} en la ruta {}", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IdClienteNoEncontrado.class)
    public ResponseEntity<ErrorResponse> ManejarIdClienteNoEncontrado(IdClienteNoEncontrado ex,
                                                                       HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        log.warn("{} en la ruta {}", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(VentaNoEncontrada.class)
    public ResponseEntity<ErrorResponse> ManejarVentaNoEncontrada(VentaNoEncontrada ex,
                                                                       HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        log.warn("{} en la ruta {}", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IdVendedorNoEncontrado.class)
    public ResponseEntity<ErrorResponse> ManejarIdVendedorNoEncontrado(IdVendedorNoEncontrado ex,
                                                                  HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        log.warn("{} en la ruta {}", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }




    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>manejarErroresGeneral(Exception ex ,
                                                              HttpServletRequest request){
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Ocurrió un error inesperado en el servidor",
                request.getRequestURI()
        );
        log.error("Error inesperado {}",request.getRequestURI(),ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(IdVehiculoDuplicado.class)
    public ResponseEntity<ErrorResponse> ManejarIdVehiculoDuplicado( IdVehiculoDuplicado ex,
                                                                     HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        log.warn("Intento de venta fallido ya que no se puede vender dos veces el mismo vehiculo {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }



}