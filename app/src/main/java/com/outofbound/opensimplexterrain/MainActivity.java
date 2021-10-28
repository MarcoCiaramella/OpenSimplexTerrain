package com.outofbound.opensimplexterrain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.outofbound.opensimplexterrainlib.OpenSimplexPlane;
import com.outofbound.opensimplexterrainlib.OpenSimplexSphere;
import com.outofbound.opensimplexterrainlib.OpenSimplexTerrain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<File> files = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exportOpenSimplexSphere();
        exportOpenSimplexPlane();

        sendEmail();
    }

    private void exportOpenSimplexSphere(){
        OpenSimplexSphere openSimplexSphere = new OpenSimplexSphere(16);

        openSimplexSphere.create();
        files.add(export(openSimplexSphere, 0, "OpenSimplexSphere"));
        openSimplexSphere.setOct1(0.8f);
        openSimplexSphere.setOct4(0.3f);
        openSimplexSphere.setOct16(0.1f);
        openSimplexSphere.create();
        files.add(export(openSimplexSphere, 1, "OpenSimplexSphere"));
        openSimplexSphere.setSeed(43);
        openSimplexSphere.create();
        files.add(export(openSimplexSphere, 2, "OpenSimplexSphere"));
        openSimplexSphere.setOct1(0.3f);
        openSimplexSphere.setOct4(0.6f);
        openSimplexSphere.setOct16(0.5f);
        openSimplexSphere.setSeed(61);
        openSimplexSphere.create();
        files.add(export(openSimplexSphere, 3, "OpenSimplexSphere"));
        openSimplexSphere.setExp(4.0);
        openSimplexSphere.create();
        files.add(export(openSimplexSphere, 4, "OpenSimplexSphere"));
        openSimplexSphere.setOct1(0.5f);
        openSimplexSphere.setOct2(0.2f);
        openSimplexSphere.setOct4(0.1f);
        openSimplexSphere.setSize(256);
        openSimplexSphere.setExp(4.0);
        openSimplexSphere.create();
        files.add(export(openSimplexSphere, 5, "OpenSimplexSphere"));
        openSimplexSphere.setSize(8);
        openSimplexSphere.create();
        files.add(export(openSimplexSphere, 6, "OpenSimplexSphere"));
        openSimplexSphere.setSize(128);
        openSimplexSphere.create();
        files.add(export(openSimplexSphere, 7, "OpenSimplexSphere"));

        float[] vertices = openSimplexSphere.getVertices();
        float[] normals = openSimplexSphere.getNormals();
        int[] indices = openSimplexSphere.getIndices();
    }

    private void exportOpenSimplexPlane(){
        OpenSimplexPlane openSimplexPlane = new OpenSimplexPlane();

        openSimplexPlane.create();
        files.add(export(openSimplexPlane, 0, "OpenSimplexPlane"));
        openSimplexPlane.setOct1(0.8f);
        openSimplexPlane.setOct4(0.3f);
        openSimplexPlane.setOct16(0.1f);
        openSimplexPlane.create();
        files.add(export(openSimplexPlane, 1, "OpenSimplexPlane"));
        openSimplexPlane.setSeed(43);
        openSimplexPlane.create();
        files.add(export(openSimplexPlane, 2, "OpenSimplexPlane"));
        openSimplexPlane.setOct1(0.3f);
        openSimplexPlane.setOct4(0.6f);
        openSimplexPlane.setOct16(0.5f);
        openSimplexPlane.setSeed(61);
        openSimplexPlane.create();
        files.add(export(openSimplexPlane, 3, "OpenSimplexPlane"));
        openSimplexPlane.setExp(4.0);
        openSimplexPlane.create();
        files.add(export(openSimplexPlane, 4, "OpenSimplexPlane"));
        openSimplexPlane.setSize(64);
        openSimplexPlane.create();
        files.add(export(openSimplexPlane, 5, "OpenSimplexPlane"));
        openSimplexPlane.setResolution(16f);
        openSimplexPlane.create();
        files.add(export(openSimplexPlane, 6, "OpenSimplexPlane"));

        float[] vertices = openSimplexPlane.getVertices();
        float[] normals = openSimplexPlane.getNormals();
        int[] indices = openSimplexPlane.getIndices();
    }

    private File export(OpenSimplexTerrain openSimplexTerrain, int id, String basename){
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile(basename+id, ".ply", getExternalCacheDir());
            ExportPLY.write(openSimplexTerrain,false,tmpFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmpFile;
    }

    private void sendEmail(){
        File tmpZipFile = null;
        try {
            tmpZipFile = File.createTempFile("OpenSimplexTerrain", ".zip", getExternalCacheDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Zip zip = new Zip(tmpZipFile);
        for (File file : files) {
            zip.addFile(file);
        }
        zip.close();
        assert tmpZipFile != null;
        Uri path = FileProvider.getUriForFile(this,"com.outofbound.opensimplexterrain.provider",tmpZipFile);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Your .ply file");
        emailIntent.putExtra(Intent.EXTRA_TEXT,"Your .ply file");
        emailIntent.putExtra(Intent.EXTRA_STREAM, path);
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}