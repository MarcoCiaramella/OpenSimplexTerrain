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

### OpenSimplexSphere
Class `OpenSimplexSphere` generates terrains in sphere shape like planet. The resolution of the sphere is controlled by its `size` parameter.

## How to use
```java
OpenSimplexPlane openSimplexPlane = new OpenSimplexPlane();
openSimplexPlane.setSeed(43);
openSimplexPlane.setSize(64);
openSimplexPlane.setOct1(0.8f);
openSimplexPlane.setOct4(0.3f);
openSimplexPlane.setOct16(0.1f);
openSimplexPlane.setExp(4.0);
openSimplexPlane.create();

OpenSimplexSphere openSimplexSphere = new OpenSimplexSphere(128);
openSimplexSphere.setSeed(43);
openSimplexSphere.setOct1(0.8f);
openSimplexSphere.setOct2(0.3f);
openSimplexSphere.setOct8(0.1f);
openSimplexSphere.setExp(4.0);
openSimplexSphere.create();
```
