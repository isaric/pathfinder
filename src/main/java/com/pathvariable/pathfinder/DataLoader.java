package com.pathvariable.pathfinder;

import com.pathvariable.pathfinder.services.CountryService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final CountryService countryService;

    public DataLoader(CountryService countryService) {
        this.countryService = countryService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        countryService.loadGraphData();
        countryService.calculateAllRoutes();
    }
}
