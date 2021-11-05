package com.outofbound.opensimplexterrainlib;

class Vertex extends Vector3f {

    public Normal normal;
    public int r,g,b;

    public Vertex(float x, float y, float z) {
        super(x, y, z);
        normal = new Normal();
    }

    public Vertex(Vertex vertex){
        super(vertex.x, vertex.y, vertex.z);
        normal = vertex.normal;
    }
}
