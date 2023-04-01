package wmii.jwzp.flashcards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FlashcardsApplication {

  public static void main(String[] args) {
    SpringApplication.run(FlashcardsApplication.class, args);
  }
}
