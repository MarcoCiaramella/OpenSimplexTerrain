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
    protected float oct1 = 0;
    protected float oct2 = 0;
    protected float oct4 = 0;
    protected float oct8 = 0;
    protected float oct16 = 0;
    protected float oct32 = 0;
    protected double exp = 1;
    protected float resolution = 1000f;
    protected int size = 16;
    private boolean initNoise = true;
    private boolean initVertices = true;
    private boolean initNoise1 = true;
    private boolean initNoise2 = true;
    private boolean initNoise4 = true;
    private boolean initNoise8 = true;
    private boolean initNoise16 = true;
    private boolean initNoise32 = true;

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

    public void setOct1(float oct1) {
        this.oct1 = oct1;
        initNoise1 = true;
    }

    public void setOct2(float oct2) {
        this.oct2 = oct2;
        initNoise2 = true;
    }

    public void setOct4(float oct4) {
        this.oct4 = oct4;
        initNoise4 = true;
    }

    public void setOct8(float oct8) {
        this.oct8 = oct8;
        initNoise8 = true;
    }

    public void setOct16(float oct16) {
        this.oct16 = oct16;
        initNoise16 = true;
    }

    public void setOct32(float oct32) {
        this.oct32 = oct32;
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

    public void setResolution(float resolution) {
        this.resolution = resolution;
        initVertices = true;
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

}
