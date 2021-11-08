package com.outofbound.opensimplexterrainlib;

class Vertex extends Vector3f {

    private final Normal normal;
    private int r,g,b;

    public Vertex(float x, float y, float z) {
        super(x, y, z);
        normal = new Normal();
    }

    public Vertex(Vertex vertex){
        super(vertex.x, vertex.y, vertex.z);
        normal = vertex.normal;
    }

    public Normal getNormal() {
        return normal;
    }

    public void setRGB(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int r(){
        return r;
    }

    public int g(){
        return g;
    }

    public int b(){
        return b;
    }
}
