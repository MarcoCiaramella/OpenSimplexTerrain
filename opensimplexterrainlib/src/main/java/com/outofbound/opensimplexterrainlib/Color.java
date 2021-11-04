package com.outofbound.opensimplexterrainlib;

public class Color {

    private int color;
    private float min,max;

    public Color(int color, float min, float max){
        this.color = color;
        this.min = min;
        this.max = max;
    }

    public boolean isInside(float value){
        return value >= min && value <= max;
    }
}
