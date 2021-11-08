package com.outofbound.opensimplexterrainlib;

class Vertex extends Vector3f {

    private final Normal normal;
    private int r,g,b;

    public Vertex(Vector3f position) {
        super(position.x, position.y, position.z);
        normal = new Normal();
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
