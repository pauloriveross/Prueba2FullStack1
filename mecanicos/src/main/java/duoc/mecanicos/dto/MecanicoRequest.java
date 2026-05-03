package duoc.mecanicos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MecanicoRequest {

    @NotBlank(message = "El rut es obligatorio")
    @Size(min = 9, max = 13, message = "El rut debe tener entre 9 y 13 caracteres")
    private String rutMecanico;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    private String nombreMecanico;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no debe superar los 100 caracteres")
    private String apellidoMecanico;

    public MecanicoRequest(){}

    public String getRutMecanico() {
        return rutMecanico;
    }

    public void setRutMecanico(String rutMecanico) {
        this.rutMecanico = rutMecanico;
    }

    public String getNombreMecanico() {
        return nombreMecanico;
    }

    public void setNombreMecanico(String nombreMecanico) {
        this.nombreMecanico = nombreMecanico;
    }

    public String getApellidoMecanico() {
        return apellidoMecanico;
    }

    public void setApellidoMecanico(String apellidoMecanico) {
        this.apellidoMecanico = apellidoMecanico;
    }
}

