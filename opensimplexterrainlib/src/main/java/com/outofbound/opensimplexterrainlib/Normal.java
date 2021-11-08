package com.outofbound.opensimplexterrainlib;

import java.util.ArrayList;

class Normal extends Vector3f {

    public ArrayList<Triangle> triangles = new ArrayList<>();

    public Normal() {
        super(0,0,0);
    }

    private void reset(){
        x = 0;
        y = 0;
        z = 0;
    }

    public void calc(){
        reset();
        for (Triangle t : triangles){
            this.add(t.calcNormal());
        }
        this.normalize();
    }
}
