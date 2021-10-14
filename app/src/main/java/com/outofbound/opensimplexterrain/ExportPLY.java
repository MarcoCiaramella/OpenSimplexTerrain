package com.outofbound.opensimplexterrain;

import com.outofbound.opensimplexterrainlib.OpenSimplexTerrain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

class ExportPLY {

    public static void write(OpenSimplexTerrain openSimplexTerrain, boolean color, File file) throws IOException {

        int numVertices = openSimplexTerrain.getVertices().length/3;
        int elementFace = openSimplexTerrain.getIndices().length/3;

        FileOutputStream os = new FileOutputStream(file);

        String string;
        String plyHeaderTop = "ply\n" +
                "format ascii 1.0\n" +
                "element vertex %1$d\n";
        String plyHeaderVertex = "property float x\n" +
                "property float y\n" +
                "property float z\n";
        String plyHeaderNormals = "property float nx\n" +
                "property float ny\n" +
                "property float nz\n";
        String plyHeaderBottom = "element face %2$d\n" +
                "property list uchar uint vertex_indices\n" +
                "end_header\n";
        if (color) {
            String plyHeaderColors = "property uchar red\n" +
                    "property uchar green\n" +
                    "property uchar blue\n";
            string = plyHeaderTop + plyHeaderVertex + plyHeaderNormals + plyHeaderColors + plyHeaderBottom;
        }
        else {
            string = plyHeaderTop + plyHeaderVertex + plyHeaderNormals + plyHeaderBottom;
        }
        writeString(os,String.format(Locale.ENGLISH,string,numVertices,elementFace));

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numVertices; i++){
            int v = i*3;
            int n = i*3;
            int c = i*4;
            stringBuilder.append(openSimplexTerrain.getVertices()[v]).append(" ");
            stringBuilder.append(openSimplexTerrain.getVertices()[v + 1]).append(" ");
            stringBuilder.append(openSimplexTerrain.getVertices()[v + 2]).append(" ");
            stringBuilder.append(openSimplexTerrain.getNormals()[n]).append(" ");
            stringBuilder.append(openSimplexTerrain.getNormals()[n + 1]).append(" ");
            stringBuilder.append(openSimplexTerrain.getNormals()[n + 2]);
            /*if (color) {
                stringBuilder.append(" ");
                stringBuilder.append((int) (openSimplexTerrain.getColors()[c] * 255)).append(" ");
                stringBuilder.append((int) (openSimplexTerrain.getColors()[c + 1] * 255)).append(" ");
                stringBuilder.append((int) (openSimplexTerrain.getColors()[c + 2] * 255));
            }*/
            stringBuilder.append("\n");
        }
        writeString(os,stringBuilder.toString());

        stringBuilder = new StringBuilder();
        for (int i = 0; i < openSimplexTerrain.getIndices().length; i += 3){
            stringBuilder.append("3 ");
            stringBuilder.append(openSimplexTerrain.getIndices()[i]).append(" ");
            stringBuilder.append(openSimplexTerrain.getIndices()[i + 1]).append(" ");
            stringBuilder.append(openSimplexTerrain.getIndices()[i + 2]).append("\n");
        }
        writeString(os,stringBuilder.toString());

        os.close();
    }

    private static void writeString(FileOutputStream os, String string) throws IOException {
        os.write(string.getBytes());
    }
}
