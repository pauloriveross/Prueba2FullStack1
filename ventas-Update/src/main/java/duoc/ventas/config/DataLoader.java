package duoc.ventas.config;



import duoc.ventas.model.Venta;
import duoc.ventas.repository.VentaRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class DataLoader {
    @Bean
    public CommandLineRunner cargarDatos(VentaRepository repository){
        return args -> {
            if(repository.count()==0){

                repository.save(Venta.builder()
                        .fechaVenta(new Date())
                        .precioVehiculo(2200000)
                        .tipoPago("Crédito")
                        .idCliente(1)
                        .idVehiculo(1)
                        .idVendedor(1)
                        .comisionVenta(120000)
                        .build()
                );
                Faker faker = new Faker();
                for(int i = 0; i < 10; i++){

                    long diasAtras = faker.number().numberBetween(1, 30);

                    Date fechaAleatoria = new Date(System.currentTimeMillis() - (diasAtras * 24 * 60 * 60 * 1000));

                    repository.save(Venta.builder()
                            .fechaVenta(fechaAleatoria)
                            .precioVehiculo(faker.number().numberBetween(1000000,10000000))
                            .tipoPago(faker.options().option("Crédito Automotriz","Efectivo","Crédito Bancario"))
                            .idCliente(faker.number().numberBetween(1,11))
                            .idVehiculo(faker.number().numberBetween(1,11))
                            .idVendedor(faker.number().numberBetween(1,11))
                            .comisionVenta(faker.number().numberBetween(100000,2000000))
                            .build());
                }
            }
        };
    }

}
