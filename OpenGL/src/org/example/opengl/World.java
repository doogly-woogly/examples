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
	private final GLSphere sphere = new GLSphere();
	private float frustum[][]=new float[6][4];
	public static boolean inside=true;
	public static int idivs=2;
	
	public static ArrayList<Node> nodes = new ArrayList<Node>();
	
	public static void Spawn(float x,float y,float z){
		if(!inside){
			x*=-1;
			y*=-1;
			z*=-1;
		}
		Bacteria b=new Bacteria(1,1,1, .3f,.3f,.3f, x,y,z);
		b.render=new V3(.6f,0,.6f);
		b.radius=2f;
		Bacteria.bacterium.add(b);
	}
	public void Load(GL10 gl,Context context){
		GLCube.loadTexture(gl, context, R.drawable.android);
		GLSphere.loadTexture(gl, context, R.drawable.android);
	}
	
	public static void Add(Entity e){
		for(Node n:nodes){
			n.objs.remove(e);
		}
		for(Node n:nodes){
			n.Add(e);//return;
		}
	}
	
	public World() {
		sphere.SubDivide(idivs);
		for(V3 v:sphere.vertices){
			nodes.add(new Node(v.x,v.y,v.z));
		}
		//red eats green
		float p3=0.7f;
		float p4=.5f;
		for(float ix=-1;ix<=1;ix+=2){
			Bacteria b=new Bacteria(1,0,0, 0,1,0, ix,0,0);
			b.render=new V3(.85f,.1f,.0f);
			Bacteria.bacterium.add(b);
			}
			for(float iy=-1;iy<=1;iy+=2){
				Bacteria b=new Bacteria(0,1f,0, 0,0,1, 0,iy,0);
				b.render=new V3(.2f,.7f,.2f);
				Bacteria.bacterium.add(b);
				}
				for(float iz=-1;iz<=1;iz+=2){
			Bacteria b=new Bacteria(0,0,1, 1,0,0, 0,0,iz);
			b.render=new V3(.3f,.4f,.9f);
			Bacteria.bacterium.add(b);
		}
/*		Bacteria.bacterium.add(new Bacteria(1f,0,0, 0,1f,0, 0,0,1f));
		Bacteria.bacterium.add(new Bacteria(1f,0,0, 0,1f,0, 0,0,-1f));
		//..eats blue
		Bacteria.bacterium.add(new Bacteria(0,1f,0, 0,0,1f, 0,1f,0));
		Bacteria.bacterium.add(new Bacteria(0,1f,0, 0,0,1f, 0,-1f,0));
		//..eats red
		Bacteria.bacterium.add(new Bacteria(0,0,1f, 1f,0,0, 1,0,0));
		Bacteria.bacterium.add(new Bacteria(0,0,1f, 1f,0,0, -1f,0,0));*/
	}
	
	public void Process(){
		Frame.Get();
		float fTime=0.1f;//frame time
		//process bacteria
		if(Frame.count%100==0){
			nodes.trimToSize();
		}
		int len=Bacteria.bacterium.size();
		for(int ba=0;ba<Bacteria.bacterium.size();ba++){//}Bacteria b:Bacteria.bacterium){
			if(!Bacteria.bacterium.get(ba).Process(fTime))
				ba--;//b.Process(fTime);
		}
		for(Node n:nodes){
			len=n.objs.size();
			for(int ba=0;ba<len;ba++){
				for(int bb=ba+1;bb<len;bb++){
					//collide spheres?
					n.objs.get(ba).Collide(n.objs.get(bb),fTime);
				}
			}
		}
	}
	
	public void draw(GL10 gl,float[] proj,float[] modl) {
		Process();
		ExtractFrustum(gl,proj,modl);
		gl.glPushMatrix();
		if(inside)
		gl.glScalef(1.5f,1.5f,1.5f);
		else
		gl.glScalef(0.8f,.8f,.8f);
		float matAmbient[] = new float[] { 0.15f, 0.15f, .15f, 1f };
		float o=0.6f;
		float matDiffuse[] = new float[] { 1f, 1f, 1f, 1 };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,matAmbient, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,matDiffuse, 0);
		if(inside)gl.glCullFace(gl.GL_FRONT);
		sphere.draw(gl);
		if(inside)gl.glCullFace(gl.GL_BACK);
		
		gl.glPopMatrix();
//		for(Ti tri:sphere.tris){
		for (Node temp : nodes) {
			if(PointInFrustum(temp.pos,1)){
				temp.draw(gl);
			}
		}
		
//		for(Bacteria B : Bacteria.bacterium){
//			B.draw(gl);
//		}
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
	
	boolean PointInFrustum( V3 x,float radius ){
		int p;
		
		for( p = 5; p >= 0; p-- )
			if( frustum[p][0] * x.x + frustum[p][1] * x.y + frustum[p][2] * x.z + frustum[p][3] <=-radius )
				return false;
		return true;
	}
}

