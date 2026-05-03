package duoc.mantenciones.client;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class VehiculoClient {

    private final WebClient webClient;

    public VehiculoClient(@Value("${vehiculo-service.url}") String vehiculoServidor){
        this.webClient = WebClient.builder().baseUrl(vehiculoServidor).build();
    }

    public Map<String, Object> obtenerVehiculoId(Integer id, String token){
        return this.webClient.get()
                .uri("/{id}", id)
                .header("Authorization", token)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Vehiculo no encontrado no se puede generar venta")))
                .bodyToMono(Map.class)
                .block();
    }
}
