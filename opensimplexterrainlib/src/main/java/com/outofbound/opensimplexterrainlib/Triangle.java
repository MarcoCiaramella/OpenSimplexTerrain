package com.outofbound.opensimplexterrainlib;

class Triangle {

    public Vertex v1;
    public Vertex v2;
    public Vertex v3;
    private final Vector3f normal = new Vector3f(0,0,0);
    public int r,g,b;

    public Vector3f calcNormal(){
        Vector3f edge1 = new Vector3f(0,0,0);
        Vector3f edge2 = new Vector3f(0,0,0);
        v1.sub(v2,edge1);
        v3.sub(v2,edge2);
        edge2.cross(edge1,normal);
        normal.normalize();
        return normal;
    }

    public void calcColor(){
        v1.r = 255;
        v1.g = 255;
        v1.b = 255;
        v2.r = 255;
        v2.g = 255;
        v2.b = 255;
        v3.r = 255;
        v3.g = 255;
        v3.b = 255;
    }
}
