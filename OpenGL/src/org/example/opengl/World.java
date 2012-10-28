/***
* Excerpted from "Hello, Android!",
* published by The Pragmatic Bookshelf.
* Copyrights apply to this code. It may not be used to create training material, 
* courses, books, articles, and the like. Contact us if you are in doubt.
* We make no guarantees that this code is fit for any purpose. 
* Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/

package org.example.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.util.ArrayList;
import java.util.List;

class World {
	private final GLCube cube = new GLCube();
	private final GLSphere sphere = new GLSphere();
	private float frustum[6][4];
	
	private List<Node> nodes = new ArrayList<Node>();
	int one = 65536;
	int gold = (int)((double)one/1.61803398875);
	int half = one / 2;
	
	
	private int vertices[] = {
		-gold,+one,0,
		+gold,+one,0,
		0,+gold,-one,
		0,+gold,+one,
		-one,0,-gold,
		-one,0,+gold,
		+one,0,-gold,
		+one,0,+gold,
		0,-gold,-one,
		0,-gold,+one,
		-gold,-one,0,
		+gold,-one,0,
	};
	
	private int[] faces={
		0,1,2,        
		1,0,3,
		0,2,4,
		0,4,5,
		3,0,5,
		2,1,6,        
		6,1,7,  
		1,3,7,
		3,5,9,
		7,3,9,
		4,2,8,  
		2,6,8,  
		4,8,10,  
		5,4,10, 
		9,5,10,
		9,10,11, 
		7,9,11,
		6,7,11, 
		8,6,11,
		10,8,11,
	};
	
	public void Load(GL10 gl,Context context){
		GLCube.loadTexture(gl, context, R.drawable.android);
		GLSphere.loadTexture(gl, context, R.drawable.android);
	}
	
	public World() {
	}
	
	
	public void draw(GL10 gl) {
		//frustum culling
		cube.draw(gl);
		sphere.draw(gl);
		for (Node temp : nodes) {
			temp.draw(gl);
		}
	}
	
	
}

