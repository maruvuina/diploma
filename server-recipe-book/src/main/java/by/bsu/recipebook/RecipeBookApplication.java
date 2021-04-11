package by.bsu.recipebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class RecipeBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeBookApplication.class, args);
    }

}
