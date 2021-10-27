package com.outofbound.opensimplexterrain;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.outofbound.opensimplexterrainlib.OpenSimplexTerrain;
import com.outofbound.opensimplexterrainlib.OpenSimplexPlane;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class OpenSimplexPlaneTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.outofbound.opensimplexterrain", appContext.getPackageName());
    }

    @Test
    public void testVertices(){
        OpenSimplexPlane openSimplexPlane = new OpenSimplexPlane();
        openSimplexPlane.setSize(512);
        openSimplexPlane.setSeed(1234);
        openSimplexPlane.setExp(4.0);
        openSimplexPlane.setOct1(1f);
        openSimplexPlane.create();
        for (int i = 0; i < openSimplexPlane.getVertices().length; i += 3){
            assertTrue(""+openSimplexPlane.getVertices()[i],openSimplexPlane.getVertices()[i] >= -0.5 && openSimplexPlane.getVertices()[i] <= 0.5);
            assertTrue(""+openSimplexPlane.getVertices()[i+1],openSimplexPlane.getVertices()[i+1] >= -0.5 && openSimplexPlane.getVertices()[i+1] <= 0.5);
            assertTrue(""+openSimplexPlane.getVertices()[i+2],openSimplexPlane.getVertices()[i+2] >= 0.0 && openSimplexPlane.getVertices()[i+2] <= 1.0);
        }
    }
}