package org.example.seeder;

import lombok.RequiredArgsConstructor;
import org.example.model.Currencies;
import org.example.model.Status;
import org.example.repository.CurrenciesRepository;
import org.example.repository.StatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CurrenciesSeeder implements CommandLineRunner {
    private final CurrenciesRepository currenciesRepository;
    private final StatusRepository statusRepository;


    @Override
    public void run(String... args){
        createCurrencies("USD", "US Dollar");
        createCurrencies("EUR", "Euro");
        createCurrencies("AZN", "Azerbaycan manatı");

    }

    private void createCurrencies(String name,String abbreviation){

        if(currenciesRepository.findByName(name).isEmpty()){
            Status status = statusRepository.findByName("ACTIVE").orElseThrow();
            Currencies currencies = Currencies.builder()
                    .name(name)
                    .abbreviation(abbreviation)
                    .status(status)
                    .build();
            currenciesRepository.save(currencies);

        }



    }

}
