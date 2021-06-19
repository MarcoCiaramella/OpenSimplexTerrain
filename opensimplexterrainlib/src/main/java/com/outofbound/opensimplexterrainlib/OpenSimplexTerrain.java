package com.outofbound.opensimplexterrainlib;


import com.jnoise.opensimplexnoiselib.OpenSimplex2F;

public class OpenSimplexTerrain {

    private Vertex[] vertices;
    private float[] verticesArr;
    private Normal[] normals;
    private float[] normalsArr;
    private int[] indicesArr;
    private Params params;
    private double[] noiseVal1;
    private double[] noiseVal2;
    private double[] noiseVal4;
    private double[] noiseVal8;
    private double[] noiseVal16;
    private double[] noiseVal32;
    private OpenSimplex2F openSimplex2F;

    public static class Params{

        private static final int PLANE = 1;
        private static final int SPHERE = 2;
        public int width = 0;
        public int height = 0;
        public long seed = 0;
        public float oct1 = 0;
        public float oct2 = 0;
        public float oct4 = 0;
        public float oct8 = 0;
        public float oct16 = 0;
        public float oct32 = 0;
        public double exp = 1;
        public float res = 1000f;
        private int type = PLANE;

        public void copy(Params params){
            width = params.width;
            height = params.height;
            seed = params.seed;
            oct1 = params.oct1;
            oct2 = params.oct2;
            oct4 = params.oct4;
            oct8 = params.oct8;
            oct16 = params.oct16;
            oct32 = params.oct32;
            exp = params.exp;
            res = params.res;
            type = params.type;
        }

        public boolean equals(Params params){
            return this.width == params.width &&
                    this.height == params.height &&
                    this.seed == params.seed &&
                    this.oct1 == params.oct1 &&
                    this.oct2 == params.oct2 &&
                    this.oct4 == params.oct4 &&
                    this.oct8 == params.oct8 &&
                    this.oct16 == params.oct16 &&
                    this.oct32 == params.oct32 &&
                    this.exp == params.exp &&
                    this.res == params.res &&
                    this.type == params.type;
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
            if (this.params.width != oldParams.width || this.params.height != oldParams.height){
                onWidthHeightChange();
            }
            if (this.params.type != oldParams.type){
                onTypeChange();
            }
            calcNoise();
            calcNormals();
            toArray(vertices);
            toArray(normals);
        }
    }

    private void initVertices(){
        vertices = new Vertex[params.width*params.height];
        verticesArr = new float[vertices.length*3];
        int i = 0;
        for (int y = 0; y < params.height; y++) {
            for (int x = 0; x < params.width; x++) {
                float x01 = x/(float)params.width;
                float y01 = y/(float)params.height;
                x01 = ((int)(x01*params.res)) / params.res;
                y01 = ((int)(y01*params.res)) / params.res;
                vertices[i++] = new Vertex(x01-0.5f,y01-0.5f,0);
            }
        }
        if (params.type == Params.SPHERE){
            //planeToSphere();
        }
    }

    private void initTriangles(){
        indicesArr = new int[(params.width - 1) * (params.height - 1) * 2 *6];
        int w = params.width;
        int r = 0;
        for (int j = 0; j < params.height-1; j++) {
            for (int i = 0; i < params.width-1; i++) {
                int q = j*w + i;
                int t1v1i = q + 1;
                int t1v2i = w + q;
                int t1v3i = q;
                int t2v1i = w + q + 1;
                int t2v2i = w + q;
                int t2v3i = q + 1;
                Triangle t1 = new Triangle();
                t1.v1 = vertices[t1v1i];
                t1.v2 = vertices[t1v2i];
                t1.v3 = vertices[t1v3i];
                Triangle t2 = new Triangle();
                t2.v1 = vertices[t2v1i];
                t2.v2 = vertices[t2v2i];
                t2.v3 = vertices[t2v3i];
                indicesArr[r++] = t1v1i;
                indicesArr[r++] = t1v2i;
                indicesArr[r++] = t1v3i;
                indicesArr[r++] = t2v1i;
                indicesArr[r++] = t2v2i;
                indicesArr[r++] = t2v3i;
                normals[t1v1i].triangles.add(t1);
                normals[t1v2i].triangles.add(t1);
                normals[t1v3i].triangles.add(t1);
                normals[t2v1i].triangles.add(t2);
                normals[t2v2i].triangles.add(t2);
                normals[t2v3i].triangles.add(t2);
            }
        }
    }

    private void initNormals(){
        normals = new Normal[params.width*params.height];
        normalsArr = new float[normals.length*3];
        for (int i = 0; i < normals.length; i++){
            normals[i] = new Normal();
        }
    }

    private void calcNormals(){
        for (Normal n : normals){
            n.calc();
        }
    }

    private void calcNoise(){
        for (int i = 0; i < vertices.length; i++) {
            double z01 = (params.oct1 * noiseVal1[i]
                    + params.oct2 * noiseVal2[i]
                    + params.oct4 * noiseVal4[i]
                    + params.oct8 * noiseVal8[i]
                    + params.oct16 * noiseVal16[i]
                    + params.oct32 * noiseVal32[i]
                    + 1) / 2;
            z01 /= (params.oct1 + params.oct2 + params.oct4 + params.oct8 + params.oct16 + params.oct32);
            z01 = Math.pow(z01, params.exp);
            z01 = ((int)(z01*params.res)) / params.res;
            vertices[i].z = (float)z01;
        }
    }

    private void initNoise(){
        openSimplex2F = new OpenSimplex2F();
        initNoise1();
        initNoise2();
        initNoise4();
        initNoise8();
        initNoise16();
        initNoise32();
    }

    private void initNoise1(){
        noiseVal1 = openSimplex2F.noise2(params.seed, params.width, params.height, 0, 0, 1.0);
    }

    private void initNoise2(){
        noiseVal2 = openSimplex2F.noise2(params.seed, params.width, params.height, 0, 0, 1.0/2.0);
    }

    private void initNoise4(){
        noiseVal4 = openSimplex2F.noise2(params.seed, params.width, params.height, 0, 0, 1.0/4.0);
    }

    private void initNoise8(){
        noiseVal8 = openSimplex2F.noise2(params.seed, params.width, params.height, 0, 0, 1.0/8.0);
    }

    private void initNoise16(){
        noiseVal16 = openSimplex2F.noise2(params.seed, params.width, params.height, 0, 0, 1.0/16.0);
    }

    private void initNoise32(){
        noiseVal32 = openSimplex2F.noise2(params.seed, params.width, params.height, 0, 0, 1.0/32.0);
    }

    private void onSeedChange(){
        initNoise();
    }

    private void onWidthHeightChange(){
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

    private void onTypeChange(){
        initVertices();
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

    /*private void planeToSphere(){
        final Vector3f U = new Vector3f(1f,0f,0f);
        final Vector3f V = new Vector3f(0f,1f,0f);
        final float R = 1f;
        for (Vertex vertex : vertices) {
            float u = vertex.dot(U);
            float v = vertex.dot(V);
            float lon = (float) Math.PI * (u + 1.0f);
            float lat = (float) Math.PI * v * 0.5f;
            float r = R + vertex.z;
            vertex.x = (float) (r * Math.cos(lat) * Math.cos(lon));
            vertex.y = (float) (r * Math.cos(lat) * Math.sin(lon));
            vertex.z = (float) (r * Math.sin(lat));
        }
    }*/

}
