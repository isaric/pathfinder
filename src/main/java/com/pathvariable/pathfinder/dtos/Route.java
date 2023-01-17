package com.pathvariable.pathfinder.dtos;

import java.util.List;

public class Route {

    private final List<String> route;

    public Route(List<String> route) {
        this.route = route;
    }

    public List<String> getRoute() {
        return route;
    }
}
