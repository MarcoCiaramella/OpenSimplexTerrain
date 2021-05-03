package com.outofbound.opensimplexterrainlib;

import java.util.ArrayList;

class Normal extends Vector3f {

    public ArrayList<Triangle> triangles = new ArrayList<>();

    public Normal() {
        super(0,0,0);
    }

    public void calc(){
        for (Triangle t : triangles){
            this.add(t.calcNormal());
        }
        this.normalize();
    }
}
