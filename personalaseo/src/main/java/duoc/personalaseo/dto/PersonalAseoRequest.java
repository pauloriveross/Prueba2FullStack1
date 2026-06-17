package duoc.personalaseo.dto;

import jakarta.validation.constraints.*;


public class PersonalAseoRequest {
    @NotBlank(message = "El rut es obligatorio")
    @Size(min = 9, max = 13, message = "El rut debe tener entre 9 y 13 caracteres")
    private String rutPersonalAseo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    private String nombrePersonalAseo;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no debe superar los 100 caracteres")
    private String apellidoPersonalAseo;

    @NotNull(message = "El sueldo es obligatorio")
    @Min(value = 553553, message = "El sueldo no puede ser inferior al sueldo base legal")
    @Max(value = 800000, message = "El sueldo axcede el limite permitido")
    private Integer sueldoPersonalAseo;

    public PersonalAseoRequest(){}

    public String getRutPersonalAseo(){return rutPersonalAseo;}
    public void setRutPersonalAseo(String rutPersonalAseo){this.rutPersonalAseo = rutPersonalAseo;}

    public String getNombrePersonalAseo(){return nombrePersonalAseo;}
    public void setNombrePersonalAseo(String nombrePersonalAseo){this.nombrePersonalAseo = nombrePersonalAseo;}

    public String getApellidoPersonalAseo(){return  apellidoPersonalAseo;}
    public void setApellidoPersonalAseo(String apellidoPersonalAseo){this.apellidoPersonalAseo = apellidoPersonalAseo;}

    public Integer getSueldoPersonalAseo(){return sueldoPersonalAseo;}
    public void setSueldoPersonalAseo(Integer sueldoPersonalAseo){this.sueldoPersonalAseo = sueldoPersonalAseo;}
}
