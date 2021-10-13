package com.outofbound.opensimplexterrainlib;


import com.jnoise.opensimplexnoiselib.OpenSimplex2F;

public abstract class OpenSimplexTerrain {

    protected double[] grid1;
    protected double[] grid2;
    protected double[] grid4;
    protected double[] grid8;
    protected double[] grid16;
    protected double[] grid32;
    protected Vertex[] vertices;
    protected float[] verticesArr;
    protected Normal[] normals;
    protected float[] normalsArr;
    protected int[] indicesArr;
    protected Params params;
    protected double[] noiseVal1;
    protected double[] noiseVal2;
    protected double[] noiseVal4;
    protected double[] noiseVal8;
    protected double[] noiseVal16;
    protected double[] noiseVal32;
    protected OpenSimplex2F openSimplex2F;

    public static class Params {

        public int size = 0;
        public long seed = 0;
        public float oct1 = 0;
        public float oct2 = 0;
        public float oct4 = 0;
        public float oct8 = 0;
        public float oct16 = 0;
        public float oct32 = 0;
        public double exp = 1;
        public float res = 1000f;

        public void copy(Params params){
            size = params.size;
            seed = params.seed;
            oct1 = params.oct1;
            oct2 = params.oct2;
            oct4 = params.oct4;
            oct8 = params.oct8;
            oct16 = params.oct16;
            oct32 = params.oct32;
            exp = params.exp;
            res = params.res;
        }

        public boolean equals(Params params){
            return this.size == params.size &&
                    this.seed == params.seed &&
                    this.oct1 == params.oct1 &&
                    this.oct2 == params.oct2 &&
                    this.oct4 == params.oct4 &&
                    this.oct8 == params.oct8 &&
                    this.oct16 == params.oct16 &&
                    this.oct32 == params.oct32 &&
                    this.exp == params.exp &&
                    this.res == params.res;
        }
    }

    public OpenSimplexTerrain(){
        params = null;
        openSimplex2F = null;
    }

    public void create(Params params){
        if (this.params == null) {
            this.params = new Params();
            this.params.copy(params);
            initGrids();
            initVertices();
            initNoise();
            calcNoise();
            toArray(vertices);
            initNormals();
            initTriangles();
            calcNormals();
            toArray(normals);
        }
        else if (!this.params.equals(params)){
            Params oldParams = new Params();
            oldParams.copy(this.params);
            this.params.copy(params);
            if (this.params.seed != oldParams.seed){
                onSeedChange();
            }
            if (this.params.oct1 != oldParams.oct1){
                onOct1Change();
            }
            if (this.params.oct2 != oldParams.oct2){
                onOct2Change();
            }
            if (this.params.oct4 != oldParams.oct4){
                onOct4Change();
            }
            if (this.params.oct8 != oldParams.oct8){
                onOct8Change();
            }
            if (this.params.oct16 != oldParams.oct16){
                onOct16Change();
            }
            if (this.params.oct32 != oldParams.oct32){
                onOct32Change();
            }
            if (this.params.exp != oldParams.exp){
                onExpChange();
            }
            if (this.params.res != oldParams.res){
                onResChange();
            }
            if (this.params.size != oldParams.size){
                onSizeChange();
            }
            calcNoise();
            calcNormals();
            toArray(vertices);
            toArray(normals);
        }
    }

    protected abstract void initGrids();

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
        openSimplex2F = new OpenSimplex2F(params.seed);
        initNoise1();
        initNoise2();
        initNoise4();
        initNoise8();
        initNoise16();
        initNoise32();
    }

    protected abstract void initNoise1();

    protected abstract void initNoise2();

    protected abstract void initNoise4();

    protected abstract void initNoise8();

    protected abstract void initNoise16();

    protected abstract void initNoise32();

    private void onSeedChange(){
        initNoise();
    }

    private void onSizeChange(){
        initGrids();
        initVertices();
        initNoise();
        initNormals();
        initTriangles();
    }

    private void onOct1Change(){
        initNoise1();
    }

    private void onOct2Change(){
        initNoise2();
    }

    private void onOct4Change(){
        initNoise4();
    }

    private void onOct8Change(){
        initNoise8();
    }

    private void onOct16Change(){
        initNoise16();
    }

    private void onOct32Change(){
        initNoise32();
    }

    private void onResChange(){
        initVertices();
    }

    private void onExpChange(){
    }

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
