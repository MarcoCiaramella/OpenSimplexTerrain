package com.outofbound.opensimplexterrain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.outofbound.opensimplexterrainlib.OpenSimplexSphere;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class OpenSimplexSphereTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.outofbound.opensimplexterrain", appContext.getPackageName());
    }

    @Test
    public void testVertices(){
        OpenSimplexSphere openSimplexSphere = new OpenSimplexSphere(16);
        openSimplexSphere.create();
        for (int i = 0; i < openSimplexSphere.getVertexPositions().length; i++){
            assertTrue(""+openSimplexSphere.getVertexPositions()[i],openSimplexSphere.getVertexPositions()[i] >= -1.5 && openSimplexSphere.getVertexPositions()[i] <= 1.5);
        }
    }

    private float length(float x, float y, float z){
        return (float)Math.sqrt(x*x + y*y + z*z);
    }

    @Test
    public void testNoise(){
        OpenSimplexSphere openSimplexSphere = new OpenSimplexSphere(16);
        openSimplexSphere.setOct2(0.8f);
        openSimplexSphere.create();
        for (int i = 0; i < openSimplexSphere.getVertexPositions().length; i += 3){
            float x = openSimplexSphere.getVertexPositions()[i];
            float y = openSimplexSphere.getVertexPositions()[i+1];
            float z = openSimplexSphere.getVertexPositions()[i+2];
            float noise = length(x,y,z) - 0.5f;
            assertTrue("noise at "+x+","+y+","+z,noise >= 0f && noise <= 1f);
        }
    }
}