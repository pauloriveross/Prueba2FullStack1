package duoc.cliente.config;

import duoc.cliente.model.Clientes;
import duoc.cliente.repository.ClientesRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Profile("Dev")
@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner cargarDatos (ClientesRepository clientesRepository){
        return args ->{
            if (clientesRepository.count() == 0){
                clientesRepository.save(Clientes.builder()
                        .rutCliente("22.333.444-5")
                        .nombreCliente("Juan")
                        .apellidoCliente("Topo")
                        .direccionCliente("Avenida Siempreviva 742")
                        .build());

                Faker faker = new Faker();
                for (int i = 0; i < 10; i++){
                    clientesRepository.save(Clientes.builder()
                            .rutCliente(faker.idNumber().valid())
                            .nombreCliente(faker.name().firstName())
                            .apellidoCliente(faker.name().lastName())
                            .direccionCliente(faker.address().buildingNumber())
                            .build()
                    );

                }
            }
        };
    }
}
