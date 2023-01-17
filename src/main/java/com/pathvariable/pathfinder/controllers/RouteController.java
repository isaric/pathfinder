package com.pathvariable.pathfinder.controllers;

import com.pathvariable.pathfinder.dtos.Route;
import com.pathvariable.pathfinder.services.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routing")
public class RouteController {

    private final CountryService countryService;

    public RouteController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/{origin}/{destination}")
    public ResponseEntity<Route> getRoute(@PathVariable String origin, @PathVariable String destination) {
        if (origin.length() != 3 || destination.length() != 3) {
            return ResponseEntity.badRequest().build();
        }
        List<String> stops = countryService.getRouteForCountryCodes(origin.toUpperCase(), destination.toUpperCase());
        if (stops.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new Route(stops));
    }
}
