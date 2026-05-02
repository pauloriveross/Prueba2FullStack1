package duoc.vendedor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class VendedorRequest {

    @NotBlank(message = "El rut es obligatorio")
    @Size(min = 9, max = 13, message = "El RUT debe tener entre 9 y 13 caracteres")
    private String rutVendedor;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombreVendedor;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede superar los 50 caracteres")
    private String apellidoVendedor;


    @Size(max = 15, message = "La seccion no puede superar los 15 caracteres")
    private String seccionVendedor;


    @Size(max = 10, message = "El turno no puede superar los 10 caracteres")
    private String turnoVendedor;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato valido")
    private String emailVendedor;

    public VendedorRequest() {
    }

    public String getRutVendedor() {
        return rutVendedor;
    }

    public void setRutVendedor(String rutVendedor) {
        this.rutVendedor = rutVendedor;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public String getApellidoVendedor() {
        return apellidoVendedor;
    }

    public void setApellidoVendedor(String apellidoVendedor) {
        this.apellidoVendedor = apellidoVendedor;
    }

    public String getSeccionVendedor() {
        return seccionVendedor;
    }

    public void setSeccionVendedor(String seccionVendedor) {
        this.seccionVendedor = seccionVendedor;
    }

    public String getTurnoVendedor() {
        return turnoVendedor;
    }

    public void setTurnoVendedor(String turnoVendedor) {
        this.turnoVendedor = turnoVendedor;
    }

    public String getEmailVendedor() {
        return emailVendedor;
    }

    public void setEmailVendedor(String emailVendedor) {
        this.emailVendedor = emailVendedor;
    }


}
