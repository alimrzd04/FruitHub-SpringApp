package org.example.seeder;

import lombok.RequiredArgsConstructor;
import org.example.model.Categories;
import org.example.model.Status;
import org.example.repository.CategoriesRepository;
import org.example.repository.StatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class CategoriesSeeder implements CommandLineRunner {

    private final CategoriesRepository categoriesRepository;
    private final StatusRepository statusRepository;

    @Override
    public  void run(String... args) throws Exception {
        createCurrencies("Fruit", "Fresh fruits");
        createCurrencies("Vegetable", "Fresh vegetables");
    }

    private void createCurrencies(String name, String description) {
        if(categoriesRepository.findByName(name).isEmpty()){
            Status status = statusRepository.findByName("ACTIVE").orElseThrow();
            Categories categories = Categories.builder()
                    .name(name)
                    .description(description)
                    .parent(null)
                    .status(status)
                    .build();

            categoriesRepository.save(categories);
        }
    }


}
