package pl.fula.bookstore.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import pl.fula.bookstore.bookstore.catalog.domain.CatalogRepository;
import pl.fula.bookstore.bookstore.catalog.infrastructure.BestsellerCatalogRepositoryImpl;
import pl.fula.bookstore.bookstore.catalog.infrastructure.SchoolCatalogRepositoryImpl;

import java.util.Random;

@SpringBootApplication
public class BookstoreApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BookstoreApplication.class, args);
        System.out.println(context);
    }

    // TODO 11 - conflicts. Second approach. We don't need to add @Repository to Impl classes anymore.
//    @Bean
//    CatalogRepository catalogRepository() {
//        Random random = new Random();
//        if (random.nextBoolean()) {
//            return new SchoolCatalogRepositoryImpl();
//        } else {
//            return new BestsellerCatalogRepositoryImpl();
//        }
//    }
}
