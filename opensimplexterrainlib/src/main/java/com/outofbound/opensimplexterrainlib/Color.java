package com.outofbound.opensimplexterrainlib;

public class Color {

    private final int color;
    private final float min;
    private final float max;

    public Color(int color, float min, float max){
        this.color = color;
        this.min = min;
        this.max = max;
    }

    public boolean isInside(float value){
        return value >= min && value <= max;
    }

    public byte r(){
        return (byte) android.graphics.Color.red(color);
    }

    public byte g(){
        return (byte) android.graphics.Color.green(color);
    }

    public byte b(){
        return (byte) android.graphics.Color.blue(color);
    }
}
