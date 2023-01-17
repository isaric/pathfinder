package com.pathvariable.pathfinder.services;

import java.io.IOException;
import java.util.List;

public interface CountryService {

    List<String> getRouteForCountryCodes (String origin, String destination);

    void loadGraphData() throws IOException;

    void calculateAllRoutes();

}
