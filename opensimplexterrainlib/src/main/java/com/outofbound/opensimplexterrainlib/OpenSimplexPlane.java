package com.outofbound.opensimplexterrainlib;

public class OpenSimplexPlane extends OpenSimplexTerrain{

    private double[] points(int width, int height, int offX, int offY, double freq){
        double[] points = new double[width*height*2];
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double xd = (x + offX) * freq;
                double yd = (y + offY) * freq;
                points[i++] = xd;
                points[i++] = yd;
            }
        }
        return points;
    }

    @Override
    protected void initGrids() {
        grid1 = points(params.size, params.size, 0, 0, 1.0/params.size);
        grid2 = points(params.size, params.size, 0, 0, 2.0 * (1.0/params.size));
        grid4 = points(params.size, params.size, 0, 0, 4.0 * (1.0/params.size));
        grid8 = points(params.size, params.size, 0, 0, 8.0 * (1.0/params.size));
        grid16 = points(params.size, params.size, 0, 0, 16.0 * (1.0/params.size));
        grid32 = points(params.size, params.size, 0, 0, 32.0 * (1.0/params.size));
    }

    @Override
    protected void initVertices() {
        vertices = new Vertex[params.size*params.size];
        verticesArr = new float[vertices.length*3];
        int i = 0;
        for (int y = 0; y < params.size; y++) {
            for (int x = 0; x < params.size; x++) {
                float x01 = x/(float)params.size;
                float y01 = y/(float)params.size;
                x01 = ((int)(x01*params.res)) / params.res;
                y01 = ((int)(y01*params.res)) / params.res;
                vertices[i++] = new Vertex(x01-0.5f,y01-0.5f,0);
            }
        }
    }

    @Override
    protected void initTriangles() {
        indicesArr = new int[(params.size - 1) * (params.size - 1) * 2 *6];
        int w = params.size;
        int r = 0;
        for (int j = 0; j < params.size-1; j++) {
            for (int i = 0; i < params.size-1; i++) {
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

    @Override
    protected void initNormals() {
        normals = new Normal[params.size*params.size];
        normalsArr = new float[normals.length*3];
        for (int i = 0; i < normals.length; i++){
            normals[i] = new Normal();
        }
    }

    @Override
    protected void calcNoise() {
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

    @Override
    protected void initNoise1() {
        noiseVal1 = openSimplex2F.noise2(grid1, grid1.length/2);
    }

    @Override
    protected void initNoise2() {
        noiseVal2 = openSimplex2F.noise2(grid2, grid2.length/2);
    }

    @Override
    protected void initNoise4() {
        noiseVal4 = openSimplex2F.noise2(grid4, grid4.length/2);
    }

    @Override
    protected void initNoise8() {
        noiseVal8 = openSimplex2F.noise2(grid8, grid8.length/2);
    }

    @Override
    protected void initNoise16() {
        noiseVal16 = openSimplex2F.noise2(grid16, grid16.length/2);
    }

    @Override
    protected void initNoise32() {
        noiseVal32 = openSimplex2F.noise2(grid32, grid32.length/2);
    }
}
