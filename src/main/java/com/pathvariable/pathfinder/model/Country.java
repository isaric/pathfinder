package com.pathvariable.pathfinder.model;

import java.util.List;

public class Country {

    private String name;

    private String code;

    private List<Country> neighbors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Country> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Country> neighbors) {
        this.neighbors = neighbors;
    }
}
