package io.sim.company;

public class Rota {
    private String id;
    private String edges;

    public Rota(String id, String edges) {
        this.id = id;
        this.edges = edges;
    }

    public String getID() {
        return id;
    }

    public String getEdges(){
        return edges;
    }
}
