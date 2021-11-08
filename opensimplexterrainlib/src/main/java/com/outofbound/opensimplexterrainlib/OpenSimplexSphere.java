package com.outofbound.opensimplexterrainlib;

public class OpenSimplexSphere extends OpenSimplexTerrain {

    private final float radius = 0.5f;
    private double[] sphere;

    public OpenSimplexSphere(int size){
        this.size = size;
    }

    private void newSphere(){
        sphere = new double[((size+1) * (size+1)) * 3];
        float x, y, z, xy;
        float sectorStep = 2 * pi() / size;
        float stackStep = pi() / size;
        float sectorAngle, stackAngle;
        int p = 0;
        for (int i = 0; i <= size; i++) {
            stackAngle = pi() / 2 - i * stackStep;
            xy = radius * cosf(stackAngle);
            z = radius * sinf(stackAngle);

            for (int j = 0; j <= size; j++) {
                sectorAngle = j * sectorStep;

                x = xy * cosf(sectorAngle);
                y = xy * sinf(sectorAngle);

                sphere[p++] = x;
                sphere[p++] = y;
                sphere[p++] = z;
            }
        }
    }

    private double[] newGrid(int offX, int offY, int offZ, double freq){
        double[] grid = new double[sphere.length];
        for (int i = 0; i < grid.length; i += 3){
            grid[i] = (sphere[i] + offX) * freq;
            grid[i+1] = (sphere[i+1] + offY) * freq;
            grid[i+2] = (sphere[i+2] + offZ) * freq;
        }
        return grid;
    }

    @Override
    protected void initPositions() {
        newSphere();
        positions = new Vector3f[(size+1) * (size+1)];
        int j = 0;
        for (int i = 0; i < sphere.length; i += 3){
            positions[j++] = new Vector3f((float)sphere[i], (float)sphere[i+1], (float)sphere[i+2]);
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
        triangles = new Triangle[size+size+(size-2)*size*2];
        int t = 0;
        // indices
        //  k1--k1+1
        //  |  / |
        //  | /  |
        //  k2--k2+1
        int k1, k2;
        for(int i = 0; i < size; i++){
            k1 = i * (size + 1);     // beginning of current stack
            k2 = k1 + size + 1;      // beginning of next stack

            for(int j = 0; j < size; j++, k1++, k2++){
                // 2 triangles per sector excluding 1st and last stacks
                if(i != 0){
                    // k1---k2---k1+1
                    int a = k1;
                    int b = k2;
                    int c = k1+1;

                    Triangle triangle = new Triangle();
                    triangle.v1 = new Vertex(positions[a]);
                    triangle.v2 = new Vertex(positions[b]);
                    triangle.v3 = new Vertex(positions[c]);
                    triangles[t++] = triangle;

                    triangle.v1.getNormal().addTriangle(triangle);
                    triangle.v2.getNormal().addTriangle(triangle);
                    triangle.v3.getNormal().addTriangle(triangle);

                }

                if(i != (size-1)){
                    // k1+1---k2---k2+1
                    int a = k1+1;
                    int b = k2;
                    int c = k2+1;

                    Triangle triangle = new Triangle();
                    triangle.v1 = new Vertex(positions[a]);
                    triangle.v2 = new Vertex(positions[b]);
                    triangle.v3 = new Vertex(positions[c]);
                    triangles[t++] = triangle;

                    triangle.v1.getNormal().addTriangle(triangle);
                    triangle.v2.getNormal().addTriangle(triangle);
                    triangle.v3.getNormal().addTriangle(triangle);
                }
            }
        }
    }

    @Override
    protected void calcNoise() {
        float lengthInv = 1f / radius;
        for (int i = 0; i < positions.length; i++) {
            double noise = (oct1 * noiseVal1[i]
                    + oct2 * noiseVal2[i]
                    + oct4 * noiseVal4[i]
                    + oct8 * noiseVal8[i]
                    + oct16 * noiseVal16[i]
                    + oct32 * noiseVal32[i]
                    + 1) / 2;
            noise /= (oct1 + oct2 + oct4 + oct8 + oct16 + oct32);
            noise = Math.pow(noise, exp);

            float noiseF = (float)noise;
            float nx = positions[i].x * lengthInv;
            float ny = positions[i].y * lengthInv;
            float nz = positions[i].z * lengthInv;
            positions[i].x = positions[i].x + nx*noiseF;
            positions[i].y = positions[i].y + ny*noiseF;
            positions[i].z = positions[i].z + nz*noiseF;
        }
    }

    @Override
    protected void initNoise1() {
        double[] grid = newGrid(0, 0, 0, 1.0);
        noiseVal1 = openSimplex2F.noise3Classic(grid, grid.length/3);
    }

    @Override
    protected void initNoise2() {
        double[] grid = newGrid(0, 0, 0, 2.0);
        noiseVal2 = openSimplex2F.noise3Classic(grid, grid.length/3);
    }

    @Override
    protected void initNoise4() {
        double[] grid = newGrid(0, 0, 0, 4.0);
        noiseVal4 = openSimplex2F.noise3Classic(grid, grid.length/3);
    }

    @Override
    protected void initNoise8() {
        double[] grid = newGrid(0, 0, 0, 8.0);
        noiseVal8 = openSimplex2F.noise3Classic(grid, grid.length/3);
    }

    @Override
    protected void initNoise16() {
        double[] grid = newGrid(0, 0, 0, 16.0);
        noiseVal16 = openSimplex2F.noise3Classic(grid, grid.length/3);
    }

    @Override
    protected void initNoise32() {
        double[] grid = newGrid(0, 0, 0, 32.0);
        noiseVal32 = openSimplex2F.noise3Classic(grid, grid.length/3);
    }

    @Override
    public void calcColors() {
        for (Triangle triangle : triangles){
            Vector3f position = triangle.getPosition();
            for (Color color : colors){
                if (color.isInside(Math.max(position.length() - radius, 0f))){
                    triangle.setColor(color.r(), color.g(), color.b());
                    break;
                }
            }
        }
    }
}
