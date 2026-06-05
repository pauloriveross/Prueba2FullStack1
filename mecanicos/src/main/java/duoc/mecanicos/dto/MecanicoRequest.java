package duoc.mecanicos.dto;

import jakarta.validation.constraints.*;

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


    @NotNull(message = "El sueldo es obligatorio ")
    @Min(value = 520000 , message = "El sueldo no puede ser inferior al sueldo base legal ")
    @Max(value = 1200000,message = "El sueldo excede el limite permitido ")
    private Integer sueldoBaseMecanico;

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

    public Integer getSueldoBaseMecanico(){return sueldoBaseMecanico;}

    public void setSueldoBaseMecanico(Integer sueldoBaseMecanico){this.sueldoBaseMecanico = sueldoBaseMecanico;}
}

