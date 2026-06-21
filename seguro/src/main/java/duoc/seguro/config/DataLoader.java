package duoc.seguro.config;


import duoc.seguro.model.Seguro;
import duoc.seguro.repository.SeguroRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class DataLoader {
    @Bean
    public CommandLineRunner cargarDatos(SeguroRepository repository){
        return args -> {
            if(repository.count()==0){

                repository.save(Seguro.builder()
                        .precioSeguro(120000)
                        .tipoSeguro("Automotriz")
                        .idCliente(1)
                        .idVehiculo(1)
                        .idCorredorSeguro(1)
                        .build()
                );
                Faker faker = new Faker();
                for(int i = 0; i < 10; i++){
                    repository.save(Seguro.builder()
                            .precioSeguro(faker.number().numberBetween(5000,100000))
                            .tipoSeguro(faker.options().option("Automotriz","SOAP","Revisiones","Perdida Total"))
                            .idCliente(faker.number().numberBetween(1,11))
                            .idVehiculo(faker.number().numberBetween(1,11))
                            .idCorredorSeguro(faker.number().numberBetween(1,11))
                            .build());
                }
            }
        };
    }

}
