package com.outofbound.opensimplexterrainlib;

class Triangle {

    public Vertex v1;
    public Vertex v2;
    public Vertex v3;
    private final Vector3f normal = new Vector3f(0,0,0);

    public Vector3f calcNormal(){
        Vector3f edge1 = new Vector3f(0,0,0);
        Vector3f edge2 = new Vector3f(0,0,0);
        v1.sub(v2,edge1);
        v3.sub(v2,edge2);
        edge2.cross(edge1,normal);
        normal.normalize();
        return normal;
    }

    public void setColor(int r, int g, int b){
        v1.setRGB(r,g,b);
        v2.setRGB(r,g,b);
        v3.setRGB(r,g,b);
    }

    public Vector3f getPosition(){
        Vector3f position = new Vector3f(0,0,0);
        position.x = (v1.x + v2.x + v3.x) / 3f;
        position.y = (v1.y + v2.y + v3.y) / 3f;
        position.z = (v1.z + v2.z + v3.z) / 3f;
        return position;
    }
}
