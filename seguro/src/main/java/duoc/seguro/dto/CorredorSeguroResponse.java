package duoc.seguro.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public record CorredorSeguroResponse(
        Integer idCorredor
        , String rutCorredor
        ,String nombreCorredor
        ,String apellidoCorredor
        ,String emailCorredor
        ,Integer sueldoBaseCorredor
) {

}