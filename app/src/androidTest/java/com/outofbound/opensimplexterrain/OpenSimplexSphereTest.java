package com.outofbound.opensimplexterrain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.outofbound.opensimplexterrainlib.OpenSimplexSphere;
import com.outofbound.opensimplexterrainlib.OpenSimplexTerrain;

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
        OpenSimplexTerrain.Params params = new OpenSimplexTerrain.Params();
        params.size = 16;
        params.seed = 1234;
        params.oct1 = 1.0f;
        params.exp = 4.0;
        OpenSimplexSphere openSimplexSphere = new OpenSimplexSphere();
        openSimplexSphere.create(params);
        for (int i = 0; i < openSimplexSphere.getVertices().length; i++){
            assertTrue(""+openSimplexSphere.getVertices()[i],openSimplexSphere.getVertices()[i] >= -1.5 && openSimplexSphere.getVertices()[i] <= 1.5);
        }
    }
}