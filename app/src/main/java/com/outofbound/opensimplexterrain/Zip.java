package com.outofbound.opensimplexterrain;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    static final int BUFFER = 2048;

    private final ZipOutputStream out;
    private final byte[] data;

    public Zip(File zipFile) {
        FileOutputStream dest=null;
        try {
            dest = new FileOutputStream(zipFile);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        out = new ZipOutputStream(new BufferedOutputStream(dest));
        data = new byte[BUFFER];
    }

    public void addFile(File file) {
        FileInputStream fi=null;
        try {
            fi = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedInputStream origin = new BufferedInputStream(fi, BUFFER);
        ZipEntry entry = new ZipEntry(file.getName());
        try {
            out.putNextEntry(entry);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        int count;
        try {
            while((count = origin.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            origin.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
