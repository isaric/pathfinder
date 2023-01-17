package com.pathvariable.pathfinder.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Logger LOG = LoggerFactory.getLogger(CountryServiceImpl.class);

    private final Graph<String, DefaultEdge> countries;

    public CountryServiceImpl() {
        this.countries = GraphTypeBuilder.<String,DefaultEdge> undirected().allowingMultipleEdges(true)
                                                                           .weighted(false)
                                                                           .allowingSelfLoops(false)
                                                                           .edgeClass(DefaultEdge.class)
                                                                           .buildGraph();
    }

    @Cacheable("countries")
    @Override
    public List<String> getRouteForCountryCodes(String origin, String destination) {
        if (!countries.containsVertex(origin) || !countries.containsVertex(destination)) {
            return List.of();
        }
        return Optional.ofNullable(DijkstraShortestPath.findPathBetween(countries, origin,destination))
                                                       .map(GraphPath::getVertexList)
                                                       .orElse(new ArrayList<>());
    }

    @Override
    public void loadGraphData() throws IOException {
        List<Map<String, Object>> countries = MAPPER.readValue(
                ResourceUtils.getFile("classpath:countries.json"), new TypeReference<>(){});

        LOG.info("Started loading country data");

        for (Map<String, Object> country : countries) {
            List<String> borders = (List<String>) country.get("borders");
            String code = country.get("cca3").toString();
            this.countries.addVertex(code);
            for (String border : borders) {
                this.countries.addVertex(border);
                this.countries.addEdge(code, border);
            }
        }

        LOG.info("Finished loading country data. There are {} countries in the graph", this.countries.vertexSet().size());

    }

    @Override
    public void calculateAllRoutes() {
        LOG.info("Starting calculation of all possible routes");
        var set1 = countries.vertexSet();
        var set2 = countries.vertexSet();
        for (String origin : set1) {
            for (String destination : set2) {
                getRouteForCountryCodes(origin, destination);
            }
        }
        LOG.info("Finished calculation of all possible routes");
    }

}
