package duoc.config;


import duoc.model.TestDrive;
import duoc.repository.TestDriveRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class DataLoader {
    @Bean
    public CommandLineRunner cargarDatos(TestDriveRepository repository){
        return args -> {
            if(repository.count()==0){

                repository.save(TestDrive.builder()
                        .fechaTestDrive(new Date())
                        .idCliente(1)
                        .idVehiculo(1)
                        .idVendedor(1)
                        .build()
                );
                Faker faker = new Faker();
                for(int i = 0; i < 10; i++){

                    long diasAtras = faker.number().numberBetween(1, 30);

                    Date fechaAleatoria = new Date(System.currentTimeMillis() - (diasAtras * 24 * 60 * 60 * 1000));

                    repository.save(TestDrive.builder()
                            .fechaTestDrive(fechaAleatoria)
                            .idCliente(faker.number().numberBetween(1,11))
                            .idVehiculo(faker.number().numberBetween(1,11))
                            .idVendedor(faker.number().numberBetween(1,11))
                            .build());
                }
            }
        };
    }

}
