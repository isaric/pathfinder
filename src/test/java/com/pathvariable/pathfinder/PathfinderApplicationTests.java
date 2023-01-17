package com.pathvariable.pathfinder;

import com.pathvariable.pathfinder.services.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PathfinderApplicationTests {

	private static final List<String> CROATIA_TO_SPAIN = List.of("HRV", "SVN", "ITA", "FRA", "ESP");

	@Autowired
	private CountryService countryService;

	@Test
	void contextLoads() {
	}

	@Test
	void getRouteFromCroatiaToSpainSuccess() {
		List<String> route = countryService.getRouteForCountryCodes("HRV", "ESP");
		assertEquals(5, route.size());
		assertTrue(route.containsAll(CROATIA_TO_SPAIN));
	}

	@Test
	void getEmptyRouteCroatiaToUSA() {
		List<String> route = countryService.getRouteForCountryCodes("HRV", "USA");
		assertEquals(0, route.size());
	}

}
