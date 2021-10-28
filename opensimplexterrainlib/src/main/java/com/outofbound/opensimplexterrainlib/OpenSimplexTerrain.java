package com.outofbound.opensimplexterrainlib;


import com.jnoise.opensimplexnoiselib.OpenSimplex2F;

public abstract class OpenSimplexTerrain {

    protected Vertex[] vertices;
    protected float[] verticesArr;
    protected Normal[] normals;
    protected float[] normalsArr;
    protected int[] indicesArr;
    protected double[] noiseVal1;
    protected double[] noiseVal2;
    protected double[] noiseVal4;
    protected double[] noiseVal8;
    protected double[] noiseVal16;
    protected double[] noiseVal32;
    protected OpenSimplex2F openSimplex2F;
    protected long seed = 0;
    protected float oct1 = 1f;
    protected float oct2 = 0;
    protected float oct4 = 0;
    protected float oct8 = 0;
    protected float oct16 = 0;
    protected float oct32 = 0;
    protected double exp = 1;
    protected int size = 16;
    protected boolean initNoise = true;
    protected boolean initVertices = true;
    protected boolean initNoise1 = true;
    protected boolean initNoise2 = true;
    protected boolean initNoise4 = true;
    protected boolean initNoise8 = true;
    protected boolean initNoise16 = true;
    protected boolean initNoise32 = true;

    public void create(){
        if (initVertices) {
            initVertices();
            initNormals();
            initTriangles();
            initVertices = false;
        }
        if (initNoise) {
            initNoise();
            initNoise = false;
        }
        if (initNoise1){
            initNoise1();
            initNoise1 = false;
        }
        if (initNoise2){
            initNoise2();
            initNoise2 = false;
        }
        if (initNoise4){
            initNoise4();
            initNoise4 = false;
        }
        if (initNoise8){
            initNoise8();
            initNoise8 = false;
        }
        if (initNoise16){
            initNoise16();
            initNoise16 = false;
        }
        if (initNoise32){
            initNoise32();
            initNoise32 = false;
        }
        calcNoise();
        calcNormals();
        toArray(vertices);
        toArray(normals);
    }

    public void setSeed(long seed) {
        this.seed = seed;
        initNoise = true;
        initNoise1 = true;
        initNoise2 = true;
        initNoise4 = true;
        initNoise8 = true;
        initNoise16 = true;
        initNoise32 = true;
    }

    public void setOct1(float value) {
        checkOctValue(value);
        this.oct1 = value;
        initNoise1 = true;
    }

    public void setOct2(float value) {
        checkOctValue(value);
        this.oct2 = value;
        initNoise2 = true;
    }

    public void setOct4(float value) {
        checkOctValue(value);
        this.oct4 = value;
        initNoise4 = true;
    }

    public void setOct8(float value) {
        checkOctValue(value);
        this.oct8 = value;
        initNoise8 = true;
    }

    public void setOct16(float value) {
        checkOctValue(value);
        this.oct16 = value;
        initNoise16 = true;
    }

    public void setOct32(float value) {
        checkOctValue(value);
        this.oct32 = value;
        initNoise32 = true;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public void setSize(int size) {
        this.size = size;
        initVertices = true;
        initNoise1 = true;
        initNoise2 = true;
        initNoise4 = true;
        initNoise8 = true;
        initNoise16 = true;
        initNoise32 = true;
    }

    protected abstract void initVertices();

    protected abstract void initTriangles();

    protected abstract void initNormals();

    private void calcNormals(){
        for (Normal n : normals){
            n.calc();
        }
    }

    protected abstract void calcNoise();

    private void initNoise(){
        openSimplex2F = new OpenSimplex2F(seed);
    }

    protected abstract void initNoise1();

    protected abstract void initNoise2();

    protected abstract void initNoise4();

    protected abstract void initNoise8();

    protected abstract void initNoise16();

    protected abstract void initNoise32();

    public float[] getVertices(){
        return verticesArr;
    }

    public int[] getIndices(){
        return indicesArr;
    }

    public float[] getNormals(){
        return normalsArr;
    }

    private void toArray(Vertex[] vertices){
        int j = 0;
        for (Vertex vertex : vertices) {
            verticesArr[j++] = vertex.x;
            verticesArr[j++] = vertex.y;
            verticesArr[j++] = vertex.z;
        }
    }

    private void toArray(Normal[] normals){
        int j = 0;
        for (Normal normal : normals) {
            normalsArr[j++] = normal.x;
            normalsArr[j++] = normal.y;
            normalsArr[j++] = normal.z;
        }
    }

    private void checkOctValue(float value){
        if (value < 0f || value > 1f) {
            throw new IllegalArgumentException("oct value must be >= 0.0 and <= 1.0");
        }
    }

}
