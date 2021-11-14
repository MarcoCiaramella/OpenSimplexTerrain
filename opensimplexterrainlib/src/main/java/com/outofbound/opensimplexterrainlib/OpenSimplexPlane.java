package com.outofbound.opensimplexterrainlib;


public class OpenSimplexPlane extends OpenSimplexTerrain {

    private float resolution = 1000f;

    private double[] newGrid(int width, int height, int offX, int offY, double freq){
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
    protected void initPositions() {
        positions = new Vector3f[size*size];
        int i = 0;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                float x01 = x/(float)size;
                float y01 = y/(float)size;
                x01 = ((int)(x01 * resolution)) / resolution;
                y01 = ((int)(y01 * resolution)) / resolution;
                positions[i++] = new Vector3f(x01-0.5f,y01-0.5f,0);
            }
        }
    }

    @Override
    protected void initTriangles() {
        int w = size;
        int t = 0;
        triangles = new Triangle[(size-1)*(size-1)*2];
        for (int j = 0; j < size-1; j++) {
            for (int i = 0; i < size-1; i++) {
                int q = j*w + i;
                int t1v1i = q + 1;
                int t1v2i = w + q;
                int t1v3i = q;
                int t2v1i = w + q + 1;
                int t2v2i = w + q;
                int t2v3i = q + 1;
                Triangle t1 = new Triangle();
                Vector3f p1 = positions[t1v1i];
                Vector3f p2 = positions[t1v2i];
                Vector3f p3 = positions[t1v3i];
                t1.v1 = new Vertex(p1);
                t1.v2 = new Vertex(p2);
                t1.v3 = new Vertex(p3);
                t1.v1.getNormal().addTriangle(t1);
                t1.v2.getNormal().addTriangle(t1);
                t1.v3.getNormal().addTriangle(t1);
                Triangle t2 = new Triangle();
                p1 = positions[t2v1i];
                p2 = positions[t2v2i];
                p3 = positions[t2v3i];
                t2.v1 = new Vertex(p1);
                t2.v2 = new Vertex(p2);
                t2.v3 = new Vertex(p3);
                t2.v1.getNormal().addTriangle(t2);
                t2.v2.getNormal().addTriangle(t2);
                t2.v3.getNormal().addTriangle(t2);
                triangles[t++] = t1;
                triangles[t++] = t2;
            }
        }
    }

    @Override
    protected void calcNoise() {
        for (int i = 0; i < positions.length; i++) {
            double z01 = (oct1 * noiseVal1[i]
                    + oct2 * noiseVal2[i]
                    + oct4 * noiseVal4[i]
                    + oct8 * noiseVal8[i]
                    + oct16 * noiseVal16[i]
                    + oct32 * noiseVal32[i]
                    + 1) / 2;
            z01 /= (oct1 + oct2 + oct4 + oct8 + oct16 + oct32);
            z01 = Math.pow(z01, exp);
            z01 = ((int)(z01* resolution)) / resolution;
            positions[i].z = (float)z01;
        }
    }

    @Override
    protected void initNoise1() {
        double[] grid = newGrid(size, size, 0, 0, 1.0/size);
        noiseVal1 = openSimplex2F.noise2(grid, grid.length/2);
    }

    @Override
    protected void initNoise2() {
        double[] grid = newGrid(size, size, 0, 0, 2.0 * (1.0/size));
        noiseVal2 = openSimplex2F.noise2(grid, grid.length/2);
    }

    @Override
    protected void initNoise4() {
        double[] grid = newGrid(size, size, 0, 0, 4.0 * (1.0/size));
        noiseVal4 = openSimplex2F.noise2(grid, grid.length/2);
    }

    @Override
    protected void initNoise8() {
        double[] grid = newGrid(size, size, 0, 0, 8.0 * (1.0/size));
        noiseVal8 = openSimplex2F.noise2(grid, grid.length/2);
    }

    @Override
    protected void initNoise16() {
        double[] grid = newGrid(size, size, 0, 0, 16.0 * (1.0/size));
        noiseVal16 = openSimplex2F.noise2(grid, grid.length/2);
    }

    @Override
    protected void initNoise32() {
        double[] grid = newGrid(size, size, 0, 0, 32.0 * (1.0/size));
        noiseVal32 = openSimplex2F.noise2(grid, grid.length/2);
    }

    @Override
    public void calcColors() {
        for (Triangle triangle : triangles){
            Vector3f position = triangle.getPosition();
            for (Color color : colors){
                if (color.isInside(position.z)){
                    triangle.setColor(color.r(), color.g(), color.b());
                    break;
                }
            }
        }
    }

    public void setResolution(float resolution) {
        this.resolution = resolution;
    }
}
