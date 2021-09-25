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
	        implementation 'com.github.MarcoCiaramella:OpenSimplexTerrain:v1.0.1'
}
```

## How to use
```java
OpenSimplexTerrain.Params params = new OpenSimplexTerrain.Params();
params.size = 512;
params.seed = 1234;
params.oct1 = 1.0f;
params.exp = 4.0;
OpenSimplexTerrain map3D = new OpenSimplexTerrain();
map3D.create(params);
float[] vertices = map3D.getVertices();
int[] indices = map3D.getIndices();
float[] normals = map3D.getNormals();
```
