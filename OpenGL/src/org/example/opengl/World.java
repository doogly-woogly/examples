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
import android.opengl.Matrix;
import android.util.*;

class World {
	private final GLCube cube = new GLCube();
	private final GLSphere sphere = new GLSphere();
	private float frustum[][]=new float[6][4];
	
	private List<Node> nodes = new ArrayList<Node>();
	
	public void Load(GL10 gl,Context context){
		GLCube.loadTexture(gl, context, R.drawable.android);
		GLSphere.loadTexture(gl, context, R.drawable.android);
	}
	
	public World() {
		sphere.SubDivide(1);
		/*V3 v=sphere.vertices.get(i);
		for(int i=0;i<sphere.tris.size();i++){
			Node n=new Node(v.x,v.y,v.z);
			if(i==0){
				n.obj=new Bacteria();
			}else if(i<40){
				n.obj=new GLCube();
			}else{
				n.obj=new GLSphere();
			}
			nodes.add(n);
		}*/
	}
	
	public void Process(){
		//process bacteria
		//Bacteria:Process()
/*		for(Ti t:sphere.tris){
			for(int x=0;x<sphere.idivs;x++){
				for(int y=0;y<x;y++){
					if(y<x-1)
						adj0=t.nodes[Math.factorial(x)+(y+1)].obj;
					else{
						//plus x triangle
					}if(y>0)
						adj1=t.nodes[Math.factorial(x)+(y-1)].obj;
					else{
						//minus x triangle
					}if(x<sphere.idivs&&)
						adj2=t.nodes[Math.factorial(x+1)+(y)].obj;
					else{
					}if(x<sphere.idivs&&y>0)
						adj3=t.nodes[Math.factorial(x+1)+(y-1)].obj;
					else{
					}if(x>0)
						adj4=t.nodes[Math.factorial(x-1)+(y)].obj;
					else{
					}if(x>0&&y<x-1)
						adj5=t.nodes[Math.factorial(x-1)+(y+1)].obj;
					else{
					}
					Node a=t.nodes[x*y].obj;//sphere.tris[t].nodes[n].obj;
					Node b=adjacent;
					if(a.Process(b)){
						return;//true
					}else if(b.Process(a)){
						return;//true
					}else{
						//choose again?
						return;//false
					}
				}
			}*/
///			int len=t.nodes.size();
//			for(Node n:t.nodes){
	//			int y=
				//ns adjacent
				/*if len ==3
					   //0
					   n+1
					   //1
					   n-1
					   //2
					   n+y-1
					   //3
					   n+y+1
					   //4
					   n-y-1
					   //5
					   n-y+1
					   6
				           10
				           15
				           21
				           28
				*/
//			}
//		}
		//breed bacteria
	}
	
	public void draw(GL10 gl,float[] proj,float[] modl) {		
		ExtractFrustum(gl,proj,modl);
		//sphere.draw(gl);
		for(Ti tri:sphere.tris){
		for (Node temp : tri.nodes) {
			if(PointInFrustum(temp.pos,1)){
				temp.draw(gl);
			}
		}
		}
	}
	
	public void ExtractFrustum(GL10 gl,float[] proj,float[] modl){
		float   clip[]=new float[16];
		float   t;

		//clip is already calcuated (possibly) in mViewProjectionMatrix
		/* Combine the two matrices (multiply projection by modelview) */
		clip[ 0] = modl[ 0] * proj[ 0] + modl[ 1] * proj[ 4] + modl[ 2] * proj[ 8] + modl[ 3] * proj[12];
		clip[ 1] = modl[ 0] * proj[ 1] + modl[ 1] * proj[ 5] + modl[ 2] * proj[ 9] + modl[ 3] * proj[13];
		clip[ 2] = modl[ 0] * proj[ 2] + modl[ 1] * proj[ 6] + modl[ 2] * proj[10] + modl[ 3] * proj[14];
		clip[ 3] = modl[ 0] * proj[ 3] + modl[ 1] * proj[ 7] + modl[ 2] * proj[11] + modl[ 3] * proj[15];
		
		clip[ 4] = modl[ 4] * proj[ 0] + modl[ 5] * proj[ 4] + modl[ 6] * proj[ 8] + modl[ 7] * proj[12];
		clip[ 5] = modl[ 4] * proj[ 1] + modl[ 5] * proj[ 5] + modl[ 6] * proj[ 9] + modl[ 7] * proj[13];
		clip[ 6] = modl[ 4] * proj[ 2] + modl[ 5] * proj[ 6] + modl[ 6] * proj[10] + modl[ 7] * proj[14];
		clip[ 7] = modl[ 4] * proj[ 3] + modl[ 5] * proj[ 7] + modl[ 6] * proj[11] + modl[ 7] * proj[15];
		
		clip[ 8] = modl[ 8] * proj[ 0] + modl[ 9] * proj[ 4] + modl[10] * proj[ 8] + modl[11] * proj[12];
		clip[ 9] = modl[ 8] * proj[ 1] + modl[ 9] * proj[ 5] + modl[10] * proj[ 9] + modl[11] * proj[13];
		clip[10] = modl[ 8] * proj[ 2] + modl[ 9] * proj[ 6] + modl[10] * proj[10] + modl[11] * proj[14];
		clip[11] = modl[ 8] * proj[ 3] + modl[ 9] * proj[ 7] + modl[10] * proj[11] + modl[11] * proj[15];
		
		clip[12] = modl[12] * proj[ 0] + modl[13] * proj[ 4] + modl[14] * proj[ 8] + modl[15] * proj[12];
		clip[13] = modl[12] * proj[ 1] + modl[13] * proj[ 5] + modl[14] * proj[ 9] + modl[15] * proj[13];
		clip[14] = modl[12] * proj[ 2] + modl[13] * proj[ 6] + modl[14] * proj[10] + modl[15] * proj[14];
		clip[15] = modl[12] * proj[ 3] + modl[13] * proj[ 7] + modl[14] * proj[11] + modl[15] * proj[15];
		
		/* Extract the numbers for the RIGHT plane */
		frustum[0][0] = clip[ 3] - clip[ 0];
		frustum[0][1] = clip[ 7] - clip[ 4];
		frustum[0][2] = clip[11] - clip[ 8];
		frustum[0][3] = clip[15] - clip[12];
		
		/* Normalize the result */
		t = FloatMath.sqrt( frustum[0][0] * frustum[0][0] + frustum[0][1] * frustum[0][1] + frustum[0][2] * frustum[0][2] );
		frustum[0][0] /= t;
		frustum[0][1] /= t;
		frustum[0][2] /= t;
		frustum[0][3] /= t;
		
		/* Extract the numbers for the LEFT plane */
		frustum[1][0] = clip[ 3] + clip[ 0];
		frustum[1][1] = clip[ 7] + clip[ 4];
		frustum[1][2] = clip[11] + clip[ 8];
		frustum[1][3] = clip[15] + clip[12];
		
		/* Normalize the result */
		t = FloatMath.sqrt( frustum[1][0] * frustum[1][0] + frustum[1][1] * frustum[1][1] + frustum[1][2] * frustum[1][2] );
		frustum[1][0] /= t;
		frustum[1][1] /= t;
		frustum[1][2] /= t;
		frustum[1][3] /= t;
		
		/* Extract the BOTTOM plane */
		frustum[2][0] = clip[ 3] + clip[ 1];
		frustum[2][1] = clip[ 7] + clip[ 5];
		frustum[2][2] = clip[11] + clip[ 9];
		frustum[2][3] = clip[15] + clip[13];
		
		/* Normalize the result */
		t = FloatMath.sqrt( frustum[2][0] * frustum[2][0] + frustum[2][1] * frustum[2][1] + frustum[2][2] * frustum[2][2] );
		frustum[2][0] /= t;
		frustum[2][1] /= t;
		frustum[2][2] /= t;
		frustum[2][3] /= t;
		
		/* Extract the TOP plane */
		frustum[3][0] = clip[ 3] - clip[ 1];
		frustum[3][1] = clip[ 7] - clip[ 5];
		frustum[3][2] = clip[11] - clip[ 9];
		frustum[3][3] = clip[15] - clip[13];
		
		/* Normalize the result */
		t = FloatMath.sqrt( frustum[3][0] * frustum[3][0] + frustum[3][1] * frustum[3][1] + frustum[3][2] * frustum[3][2] );
		frustum[3][0] /= t;
		frustum[3][1] /= t;
		frustum[3][2] /= t;
		frustum[3][3] /= t;
		
		/* Extract the FAR plane */
		frustum[4][0] = clip[ 3] - clip[ 2];
		frustum[4][1] = clip[ 7] - clip[ 6];
		frustum[4][2] = clip[11] - clip[10];
		frustum[4][3] = clip[15] - clip[14];
		
		/* Normalize the result */
		t = FloatMath.sqrt( frustum[4][0] * frustum[4][0] + frustum[4][1] * frustum[4][1] + frustum[4][2] * frustum[4][2] );
		frustum[4][0] /= t;
		frustum[4][1] /= t;
		frustum[4][2] /= t;
		frustum[4][3] /= t;
		
		/* Extract the NEAR plane */
		frustum[5][0] = clip[ 3] + clip[ 2];
		frustum[5][1] = clip[ 7] + clip[ 6];
		frustum[5][2] = clip[11] + clip[10];
		frustum[5][3] = clip[15] + clip[14];
		
		/* Normalize the result */
		t = FloatMath.sqrt( frustum[5][0] * frustum[5][0] + frustum[5][1] * frustum[5][1] + frustum[5][2] * frustum[5][2] );
		frustum[5][0] /= t;
		frustum[5][1] /= t;
		frustum[5][2] /= t;
		frustum[5][3] /= t;
	}
	
	boolean PointInFrustum( float[] x,float radius ){
		int p;
		
		for( p = 0; p < 6; p++ )
			if( frustum[p][0] * x[0] + frustum[p][1] * x[1] + frustum[p][2] * x[2] + frustum[p][3] <-radius )
				return false;
		return true;
	}
}

