package com.outofbound.opensimplexterrain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.outofbound.opensimplexterrainlib.OpenSimplexSphere;
import com.outofbound.opensimplexterrainlib.OpenSimplexTerrain;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OpenSimplexTerrain.Params params = new OpenSimplexTerrain.Params();
        params.size = 4;
        params.seed = 1234;
        params.oct1 = 1.0f;
        params.exp = 4.0;
        OpenSimplexSphere openSimplexSphere = new OpenSimplexSphere(params);
        openSimplexSphere.create();

        sendEmail(openSimplexSphere, "openSimplexSphere");
    }

    private void sendEmail(OpenSimplexTerrain openSimplexTerrain, String filename){
        try {
            File tmpFile = File.createTempFile(filename,".ply", getExternalCacheDir());
            ExportPLY.write(openSimplexTerrain,false,tmpFile);
            File tmpZipFile = File.createTempFile(tmpFile.getName(),".zip", getExternalCacheDir());
            Zip zip = new Zip(tmpZipFile);
            zip.addFile(tmpFile);
            zip.close();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}