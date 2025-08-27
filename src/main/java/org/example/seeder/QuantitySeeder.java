package org.example.seeder;


import lombok.RequiredArgsConstructor;
import org.example.model.Quantities;
import org.example.model.Status;
import org.example.repository.QuantityRepository;
import org.example.repository.StatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class QuantitySeeder  implements CommandLineRunner {
    private final QuantityRepository quantityRepository;
    private final StatusRepository statusRepository;

    @Override
    public void run(String... args){
        createQuantity("kq", "Kilogram");
        createQuantity("qr", "Qram");
        createQuantity("eded", "Eded");

    }

    private void createQuantity(String name,String abbreviation){
        if(quantityRepository.findByName(name).isEmpty()){
            Status status = statusRepository.findByName("ACTIVE").orElseThrow();
            Quantities quantities = Quantities.builder()
                    .name(name)
                    .abbreviation(abbreviation)
                    .status(status)
                    .build();
            quantityRepository.save(quantities);
        }
    }
}
