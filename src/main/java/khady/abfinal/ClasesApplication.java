package khady.abfinal;

import khady.abfinal.exchange.PeticionCliente;
import khady.abfinal.service.TramoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClasesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClasesApplication.class, args);
    }

    @Bean
    CommandLineRunner run(TramoService tramoService) {
        return args -> {

            CoberturaServicio cobertura = tramoService.getCoberturaServicio();

            // Prueba 1
            System.out.println("\nPrueba 1:");
            PeticionCliente p1 = new PeticionCliente("01", "001", "01001", "00017010100");
            long inicio1 = System.nanoTime();
            boolean resultado1 = cobertura.damosServicio(p1);
            long tiempo1 = (System.nanoTime() - inicio1) / 1000;

            System.out.println("Provincia: " + p1.getProvincia() + ", Municipio: " + p1.getMunicipio());
            System.out.println("Resultado: " + (resultado1 ? "Esta cubierta" : "No esta cubierta"));
            System.out.println("Tiempo: " + tiempo1 + " microsegundos");

            // Prueba 2
            System.out.println("\nPrueba 2:");
            PeticionCliente p2 = new PeticionCliente("52", "001", "030170", "00496010232");
            long inicio2 = System.nanoTime();
            boolean resultado2 = cobertura.damosServicio(p2);
            long tiempo2 = (System.nanoTime() - inicio2) / 1000;

            System.out.println("Provincia: " + p2.getProvincia() + ", Municipio: " + p2.getMunicipio());
            System.out.println("Resultado: " + (resultado2 ? "Esta cubierta" : "No esta cubierta"));
            System.out.println("Tiempo: " + tiempo2 + " microsegundos");

            // Prueba 3
            System.out.println("\nPrueba 3:");
            PeticionCliente p3 = new PeticionCliente("99", "999", "9999999", "999999999999");
            long inicio3 = System.nanoTime();
            boolean resultado3 = cobertura.damosServicio(p3);
            long tiempo3 = (System.nanoTime() - inicio3) / 1000;

            System.out.println("Provincia: " + p3.getProvincia() + ", Municipio: " + p3.getMunicipio());
            System.out.println("Resultado: " + (resultado3 ? "Esta cubierta" : "No esta cubierta"));
            System.out.println("Tiempo: " + tiempo3 + " microsegundos\n");
        };
    }
}