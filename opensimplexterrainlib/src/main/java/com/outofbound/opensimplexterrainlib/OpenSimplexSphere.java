package com.outofbound.opensimplexterrainlib;

public class OpenSimplexSphere extends OpenSimplexTerrain{

    private float radius = 0.5f;

    private double[] points(int offX, int offY, int offZ, double freq){
        double[] points = new double[((params.size+1) * (params.size+1)) * 3];
        float x, y, z, xy;
        float sectorStep = 2 * pi() / params.size;
        float stackStep = pi()/ params.size;
        float sectorAngle, stackAngle;
        int p = 0;
        for (int i = 0; i <= params.size; i++) {
            stackAngle = pi() / 2 - i * stackStep;
            xy = radius * cosf(stackAngle);
            z = radius * sinf(stackAngle);

            for (int j = 0; j <= params.size; j++) {
                sectorAngle = j * sectorStep;

                x = xy * cosf(sectorAngle);
                y = xy * sinf(sectorAngle);

                double xd = (x + offX) * freq;
                double yd = (y + offY) * freq;
                double zd = (z + offZ) * freq;

                points[p++] = xd;
                points[p++] = yd;
                points[p++] = zd;
            }
        }
        return points;
    }

    @Override
    protected void initGrids() {
        grid1 = points(0, 0, 0, 1.0/params.size);
        grid2 = points(0, 0, 0, 2.0 * (1.0/params.size));
        grid4 = points(0, 0, 0, 4.0 * (1.0/params.size));
        grid8 = points(0, 0, 0, 8.0 * (1.0/params.size));
        grid16 = points(0, 0, 0, 16.0 * (1.0/params.size));
        grid32 = points(0, 0, 0, 32.0 * (1.0/params.size));
    }

    @Override
    protected void initVertices() {
        vertices = new Vertex[(params.size+1) * (params.size+1)];
        verticesArr = new float[vertices.length*3];

        double[] points = points(0, 0, 0, 1.0);
        int p = 0;
        for (int i = 0; i < points.length; i += 3){
            vertices[p++] = new Vertex((float)points[i], (float)points[i+1], (float)points[i+2]);
        }
    }

    private float pi(){
        return (float) Math.PI;
    }

    private float cosf(double a){
        return (float) Math.cos(a);
    }

    private float sinf(double a){
        return (float) Math.sin(a);
    }

    @Override
    protected void initTriangles() {
        indicesArr = new int[(params.size + params.size + (params.size-2)*params.size*2) * 3];
        int r = 0;
        // indices
        //  k1--k1+1
        //  |  / |
        //  | /  |
        //  k2--k2+1
        int k1, k2;
        for(int i = 0; i < params.size; i++){
            k1 = i * (params.size + 1);     // beginning of current stack
            k2 = k1 + params.size + 1;      // beginning of next stack

            for(int j = 0; j < params.size; j++, k1++, k2++){
                // 2 triangles per sector excluding 1st and last stacks
                if(i != 0){
                    // k1---k2---k1+1
                    int a = k1;
                    int b = k2;
                    int c = k1+1;
                    indicesArr[r++] = a;
                    indicesArr[r++] = b;
                    indicesArr[r++] = c;

                    Triangle triangle = new Triangle();
                    triangle.v1 = vertices[a];
                    triangle.v2 = vertices[b];
                    triangle.v3 = vertices[c];

                    normals[a].triangles.add(triangle);
                    normals[b].triangles.add(triangle);
                    normals[c].triangles.add(triangle);

                }

                if(i != (params.size-1)){
                    // k1+1---k2---k2+1
                    int a = k1+1;
                    int b = k2;
                    int c = k2+1;
                    indicesArr[r++] = a;
                    indicesArr[r++] = b;
                    indicesArr[r++] = c;

                    Triangle triangle = new Triangle();
                    triangle.v1 = vertices[a];
                    triangle.v2 = vertices[b];
                    triangle.v3 = vertices[c];

                    normals[a].triangles.add(triangle);
                    normals[b].triangles.add(triangle);
                    normals[c].triangles.add(triangle);
                }
            }
        }
    }

    @Override
    protected void initNormals() {
        normals = new Normal[(params.size+1) * (params.size+1)];
        normalsArr = new float[normals.length*3];
        for (int i = 0; i < normals.length; i++){
            normals[i] = new Normal();
        }
    }

    @Override
    protected void calcNoise() {
        float lengthInv = 1.0f / radius;
        for (int i = 0; i < vertices.length; i++) {
            double noise = (params.oct1 * noiseVal1[i]
                    + params.oct2 * noiseVal2[i]
                    + params.oct4 * noiseVal4[i]
                    + params.oct8 * noiseVal8[i]
                    + params.oct16 * noiseVal16[i]
                    + params.oct32 * noiseVal32[i]
                    + 1) / 2;
            noise /= (params.oct1 + params.oct2 + params.oct4 + params.oct8 + params.oct16 + params.oct32);
            noise = Math.pow(noise, params.exp);
            noise = ((int)(noise*params.res)) / params.res;

            float noiseF = (float)noise;
            float nx = vertices[i].x * lengthInv;
            float ny = vertices[i].y * lengthInv;
            float nz = vertices[i].z * lengthInv;
            vertices[i].x = vertices[i].x + nx*noiseF;
            vertices[i].y = vertices[i].y + ny*noiseF;
            vertices[i].z = vertices[i].z + nz*noiseF;
        }
    }

    @Override
    protected void initNoise1() {
        noiseVal1 = openSimplex2F.noise3Classic(grid1, grid1.length/3);
    }

    @Override
    protected void initNoise2() {
        noiseVal2 = openSimplex2F.noise3Classic(grid2, grid2.length/3);
    }

    @Override
    protected void initNoise4() {
        noiseVal4 = openSimplex2F.noise3Classic(grid4, grid4.length/3);
    }

    @Override
    protected void initNoise8() {
        noiseVal8 = openSimplex2F.noise3Classic(grid8, grid8.length/3);
    }

    @Override
    protected void initNoise16() {
        noiseVal16 = openSimplex2F.noise3Classic(grid16, grid16.length/3);
    }

    @Override
    protected void initNoise32() {
        noiseVal32 = openSimplex2F.noise3Classic(grid32, grid32.length/3);
    }
}
