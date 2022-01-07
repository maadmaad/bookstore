package pl.fula.bookstore.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {

	private final CatalogueService catalogueService;

	public BookstoreApplication(CatalogueService catalogueService) {
		this.catalogueService = catalogueService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Book> books = catalogueService.findByTitle("Pan");
		books.forEach(System.out::println);
	}
}
