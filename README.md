# OpenSimplexTerrain
[![](https://jitpack.io/v/MarcoCiaramella/OpenSimplexTerrain.svg)](https://jitpack.io/#MarcoCiaramella/OpenSimplexTerrain)

A 3D terrain generator in Android based on OpenSimplex Noise.

## How to import in your Android project
Add JitPack in your root build.gradle at the end of repositories:

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
```

Add the dependency

```
dependencies {
	        implementation 'com.github.MarcoCiaramella:OpenSimplexTerrain:x.x.x'
}
```

## How it works
OpenSimplexTerrain generates terrains by mixing 8 weighted noises together.
The weights are set by their relative setter methods `setOct1`, `setOct2`, `setOct4`, `setOct8`, `setOct16` and `setOct32`.
The weight value must range from 0.0 to 1.0.

### OpenSimplexPlane
Class `OpenSimplexPlane` generates plane terrains. The shape of the grid can be controlled by setting a resolution value with `setResolution`.
A lower resolution generates a more squared shape.

![plane img](/img/plane.png)

### OpenSimplexSphere
Class `OpenSimplexSphere` generates terrains in sphere shape like planet. The resolution of the sphere is controlled by its `size` parameter.

![sphere img](/img/sphere.png)

## How to use
```java
OpenSimplexPlane openSimplexPlane = new OpenSimplexPlane();
openSimplexPlane.setColors(
        new Color(0x4db1db,0f,0f),
        new Color(0x35b537, 0f, 0.5f),
        new Color(0x6e4319, 0.5f, 0.7f),
        new Color(0x828282, 0.7f, 0.8f),
        new Color(0xffffff, 0.8f, 1f)
        );
openSimplexPlane.setSize(64);
openSimplexPlane.setSeed(43);
openSimplexPlane.setOct1(0.8f);
openSimplexPlane.setOct4(0.3f);
openSimplexPlane.setOct16(0.1f);
openSimplexPlane.setExp(4.0);
openSimplexPlane.create();
float[] vertexPositions = openSimplexPlane.getVertexPositions();
float[] vertexNormals = openSimplexPlane.getVertexNormals();
int[] indices = openSimplexPlane.getIndices();

OpenSimplexSphere openSimplexSphere = new OpenSimplexSphere(16);
openSimplexSphere.setColors(
        new Color(0x4db1db,0f,0f),
        new Color(0x35b537, 0f, 0.5f),
        new Color(0x6e4319, 0.5f, 0.7f),
        new Color(0x828282, 0.7f, 0.8f),
        new Color(0xffffff, 0.8f, 1f)
        );
openSimplexSphere.setSize(128);
openSimplexSphere.setSeed(43);
openSimplexSphere.setOct1(0.8f);
openSimplexSphere.setOct4(0.3f);
openSimplexSphere.setOct16(0.1f);
openSimplexSphere.setExp(4.0);
openSimplexSphere.create();
float[] vertexPositions = openSimplexSphere.getVertexPositions();
float[] vertexNormals = openSimplexSphere.getVertexNormals();
int[] indices = openSimplexSphere.getIndices();
```
