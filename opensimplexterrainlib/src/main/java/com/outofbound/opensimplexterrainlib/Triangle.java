package com.outofbound.opensimplexterrainlib;

class Triangle {

    public Vertex v1;
    public Vertex v2;
    public Vertex v3;
    private Vector3f normal = new Vector3f(0,0,0);

    public Vector3f calcNormal(){
        Vector3f edge1 = new Vector3f(0,0,0);
        Vector3f edge2 = new Vector3f(0,0,0);
        v1.sub(v2,edge1);
        v3.sub(v2,edge2);
        edge2.cross(edge1,normal);
        normal.normalize();
        return normal;
    }
}
