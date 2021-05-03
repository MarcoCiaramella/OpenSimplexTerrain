package com.outofbound.opensimplexterrainlib;

class Vector3f {

    public float x;
    public float y;
    public float z;

    public Vector3f(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void normalize(){
        float length = length();
        x /= length;
        y /= length;
        z /= length;
    }

    public float length(){
        return (float)Math.sqrt(x*x + y*y + z*z);
    }

    public void add(Vector3f v){
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public void add(Vector3f v, Vector3f res){
        res.x = x + v.x;
        res.y = y + v.y;
        res.z = z + v.z;
    }

    public void sub(Vector3f v){
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    public void sub(Vector3f v, Vector3f res){
        res.x = x - v.x;
        res.y = y - v.y;
        res.z = z - v.z;
    }

    public void cross(Vector3f v, Vector3f res){
        float crossX = y * v.z - z * v.y;
        float crossY = z * v.x - x * v.z;
        float crossZ = x * v.y - y * v.x;
        res.x = crossX;
        res.y = crossY;
        res.z = crossZ;
    }

    public void cross(Vector3f v){
        float crossX = y * v.z - z * v.y;
        float crossY = z * v.x - x * v.z;
        float crossZ = x * v.y - y * v.x;
        x = crossX;
        y = crossY;
        z = crossZ;
    }

    public float dot(Vector3f v){
        return x * v.x + y * v.y + z * v.z;
    }
}
