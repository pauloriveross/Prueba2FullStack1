package duoc.corredorSeguro.dto;


import jakarta.validation.constraints.*;


public class CorredorSeguroRequest {

    @NotBlank(message = "El rut es obligatorio")
    @Size(min = 9,max = 13,message = "El rut debe tener entre 9 y 13 caracteres")
    private String rutCorredor;


    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombreCorredor;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede superar los 50 caracteres")
    private String apellidoCorredor;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato valido")
    private String emailCorredor;

    @NotNull(message = "El sueldo es obligatorio ")
    @Min(value = 520000 , message = "El sueldo no puede ser inferior al sueldo base legal ")
    @Max(value = 1200000,message = "El sueldo excede el limite permitido ")
    private Integer sueldoBaseCorredor;


    public CorredorSeguroRequest(){}

    public String getRutCorredor(){
        return rutCorredor;
    }
    public void setRutCorredor(String rutCorredor){
        this.rutCorredor = rutCorredor;}

    public String getNombreCorredor(){
        return nombreCorredor;
    }

    public void setNombreCorredor(String nombreCorredor){
        this.nombreCorredor = nombreCorredor;
    }


    public String getApellidoCorredor(){
        return apellidoCorredor;
    }

    public void setApellidoCorredor(String apellidoCorredor){
        this.apellidoCorredor = apellidoCorredor;
    }

    public String getEmailCorredor(){
        return emailCorredor;
    }

    public void setEmailCorredor(String emailCorredor){
        this.emailCorredor = emailCorredor;
    }

    public Integer getSueldoBaseCorredor(){
        return sueldoBaseCorredor;
    }

    public void setSueldoBaseCorredor(Integer sueldoBaseCorredor){
        this.sueldoBaseCorredor = sueldoBaseCorredor;
    }








}
