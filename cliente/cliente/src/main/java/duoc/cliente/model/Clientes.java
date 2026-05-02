package duoc.cliente.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Clientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idCliente")
    private Integer idCliente;

    @Column(name="rutCliente",nullable = false, length = 13)
    private String rutCliente;

    @Column(name="nombreCliente",nullable = false)
    private String nombreCliente;

    @Column(name="apellidoCliente",nullable = false)
    private String apellidoCliente;

    @Column(name="direccionCliente",nullable = false)
    private String direccionCliente;

    public Clientes(){

    }

    public Clientes(Integer idCliente, String rutCliente, String nombreCliente, String apellidoCliente, String direccionCLiente){
        this.idCliente = idCliente;
        this.rutCliente = rutCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.direccionCliente = direccionCLiente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getRutCliente() {
        return rutCliente;
    }
    public void setRutCliente(String rutCliente){this.rutCliente = rutCliente;}

    public String getNombreCliente() {
        return nombreCliente;
    }
    public void setNombreCliente(String nombreCLiente) {
        this.nombreCliente = nombreCLiente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }
    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }
    public void setDireccionCliente(String direccionCLiente) {
        this.direccionCliente = direccionCLiente;
    }
}