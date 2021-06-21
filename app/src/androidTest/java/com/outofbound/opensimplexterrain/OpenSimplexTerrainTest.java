package com.outofbound.opensimplexterrain;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.outofbound.opensimplexterrainlib.OpenSimplexTerrain;

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
public class OpenSimplexTerrainTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.outofbound.opensimplexterrain", appContext.getPackageName());
    }

    @Test
    public void testVertices(){
        OpenSimplexTerrain.Params params = new OpenSimplexTerrain.Params();
        params.width = 64;
        params.height = 64;
        params.seed = 1234;
        params.oct1 = 1.0f;
        params.exp = 4.0;
        OpenSimplexTerrain openSimplexTerrain = new OpenSimplexTerrain();
        openSimplexTerrain.create(params);
        for (int i = 0; i < openSimplexTerrain.getVertices().length; i += 3){
            assertTrue(""+openSimplexTerrain.getVertices()[i],openSimplexTerrain.getVertices()[i] >= -0.5 && openSimplexTerrain.getVertices()[i] <= 0.5);
            assertTrue(""+openSimplexTerrain.getVertices()[i+1],openSimplexTerrain.getVertices()[i+1] >= -0.5 && openSimplexTerrain.getVertices()[i+1] <= 0.5);
            assertTrue(""+openSimplexTerrain.getVertices()[i+2],openSimplexTerrain.getVertices()[i+2] >= 0.0 && openSimplexTerrain.getVertices()[i+2] <= 1.0);
        }
    }
}