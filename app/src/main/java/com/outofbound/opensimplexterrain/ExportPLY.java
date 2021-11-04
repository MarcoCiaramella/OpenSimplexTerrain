package com.outofbound.opensimplexterrain;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

class ExportPLY {

    public static File write(float[] vertices, float[] normals, byte[] colors, int[] indices, File file) throws IOException {

        int numVertices = vertices.length/3;
        int elementFace = indices.length/3;

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
        if (colors != null) {
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
            int c = i*3;
            stringBuilder.append(vertices[v]).append(" ");
            stringBuilder.append(vertices[v + 1]).append(" ");
            stringBuilder.append(vertices[v + 2]).append(" ");
            stringBuilder.append(normals[n]).append(" ");
            stringBuilder.append(normals[n + 1]).append(" ");
            stringBuilder.append(normals[n + 2]);
            if (colors != null) {
                stringBuilder.append(" ");
                stringBuilder.append(colors[c]).append(" ");
                stringBuilder.append(colors[c + 1]).append(" ");
                stringBuilder.append(colors[c + 2]);
            }
            stringBuilder.append("\n");
        }
        writeString(os,stringBuilder.toString());

        stringBuilder = new StringBuilder();
        for (int i = 0; i < indices.length; i += 3){
            stringBuilder.append("3 ");
            stringBuilder.append(indices[i]).append(" ");
            stringBuilder.append(indices[i + 1]).append(" ");
            stringBuilder.append(indices[i + 2]).append("\n");
        }
        writeString(os,stringBuilder.toString());

        os.close();

        return file;
    }

    private static void writeString(FileOutputStream os, String string) throws IOException {
        os.write(string.getBytes());
    }
}
