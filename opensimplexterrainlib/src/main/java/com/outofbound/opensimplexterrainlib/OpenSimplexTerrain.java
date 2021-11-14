package com.outofbound.opensimplexterrainlib;


import com.jnoise.opensimplexnoiselib.OpenSimplex2F;

public abstract class OpenSimplexTerrain {

    protected Vector3f[] positions;
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
    protected boolean initNoise1 = true;
    protected boolean initNoise2 = true;
    protected boolean initNoise4 = true;
    protected boolean initNoise8 = true;
    protected boolean initNoise16 = true;
    protected boolean initNoise32 = true;
    protected Color[] colors;
    protected Triangle[] triangles;
    protected float[] vertexPositions;
    protected float[] vertexNormals;
    protected int[] vertexColors;
    protected int[] indices;

    public void create(){
        initPositions();
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
        initTriangles();
        calcNormals();
        if (colors != null) {
            calcColors();
        }
        loadVertexPositions();
        loadVertexNormals();
        if (colors != null) {
            loadVertexColors();
        }
        loadIndices();
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
        initNoise1 = true;
        initNoise2 = true;
        initNoise4 = true;
        initNoise8 = true;
        initNoise16 = true;
        initNoise32 = true;
    }

    protected abstract void initPositions();

    protected abstract void initTriangles();

    private void calcNormals(){
        for (Triangle triangle : triangles){
            triangle.v1.getNormal().calc();
            triangle.v2.getNormal().calc();
            triangle.v3.getNormal().calc();
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

    private void checkOctValue(float value){
        if (value < 0f || value > 1f) {
            throw new IllegalArgumentException("oct value must be >= 0.0 and <= 1.0");
        }
    }

    public void setColors(Color... colors){
        this.colors = colors;
    }

    public abstract void calcColors();

    public float[] getVertexPositions() {
        return vertexPositions;
    }

    public float[] getVertexNormals() {
        return vertexNormals;
    }

    public int[] getVertexColors() {
        return vertexColors;
    }

    public int[] getIndices() {
        return indices;
    }

    private void loadVertexPositions(){
        vertexPositions = new float[triangles.length * 9];
        int i = 0;
        for (Triangle triangle : triangles){
            vertexPositions[i++] = triangle.v1.x;
            vertexPositions[i++] = triangle.v1.y;
            vertexPositions[i++] = triangle.v1.z;
            vertexPositions[i++] = triangle.v2.x;
            vertexPositions[i++] = triangle.v2.y;
            vertexPositions[i++] = triangle.v2.z;
            vertexPositions[i++] = triangle.v3.x;
            vertexPositions[i++] = triangle.v3.y;
            vertexPositions[i++] = triangle.v3.z;
        }
    }

    private void loadVertexNormals(){
        vertexNormals = new float[triangles.length * 9];
        int i = 0;
        for (Triangle triangle : triangles){
            vertexNormals[i++] = triangle.v1.getNormal().x;
            vertexNormals[i++] = triangle.v1.getNormal().y;
            vertexNormals[i++] = triangle.v1.getNormal().z;
            vertexNormals[i++] = triangle.v2.getNormal().x;
            vertexNormals[i++] = triangle.v2.getNormal().y;
            vertexNormals[i++] = triangle.v2.getNormal().z;
            vertexNormals[i++] = triangle.v3.getNormal().x;
            vertexNormals[i++] = triangle.v3.getNormal().y;
            vertexNormals[i++] = triangle.v3.getNormal().z;
        }
    }

    private void loadVertexColors(){
        vertexColors = new int[triangles.length * 9];
        int i = 0;
        for (Triangle triangle : triangles){
            vertexColors[i++] = triangle.v1.r();
            vertexColors[i++] = triangle.v1.g();
            vertexColors[i++] = triangle.v1.b();
            vertexColors[i++] = triangle.v2.r();
            vertexColors[i++] = triangle.v2.g();
            vertexColors[i++] = triangle.v2.b();
            vertexColors[i++] = triangle.v3.r();
            vertexColors[i++] = triangle.v3.g();
            vertexColors[i++] = triangle.v3.b();
        }
    }

    private void loadIndices(){
        indices = new int[triangles.length * 3];
        for (int i = 0; i < indices.length; i++){
            indices[i] = i;
        }
    }

}
