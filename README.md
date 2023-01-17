## PATHFINDER

[![Java CI with Maven](https://github.com/isaric/pathfinder/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/isaric/pathfinder/actions/workflows/maven.yml)

This is a simple Spring Boot REST application that will calculate the number of
border crossings (hops) needed to get from the origin country to the destination
country if a land route exists. It does not take into account the size of a 
particular country. Each is considered equal large as a border crossing.

### How to run?

The application uses maven as a build tool and dependency manager. It will produce
a JAR file once a build is successful. Follow the standard procedure for building
and running a Java Maven application or use your favorite IDE to do it for you.

The application has some basic integration tests to make sure the core functionality
is working.

### API usage

The application exposes a single endpoint. The "origin" and "destination" varibles
are three-letter strings representing the country codes. In case no route is possible
or the string constraints are not respected the API will return status 400. The response
payload in case of success if a list of all three-letter country codes from the origin
country to the destination country.

    GET /routes/{origin}/{destination} HTTP 200,400

    { 
       "route" : ["HRV", "SLO", "AUT"] 
    }

### Implementation notes

The country data is a JSON file that is ingested at application startup. The application
then proceeds to calculate every possible combination of origin and destination. This
means all possible results will be stored in the in-memory cache and all requests made
to the application will be executed in the minimal amount of time possible.

The algorithm used to calculate the shortest path is Dijkstra's Shortest Path algorithm.
The "world" is modeled as an undirected, unweighted graph.
