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
import java.nio.*;
import android.animation.*;

class GLSphere extends Entity{
	private static FloatBuffer mVertexBuffer;
	private static ShortBuffer mIndexBuffer;
	private static FloatBuffer mNormalBuffer;
	
	public Ti[] tris;
	public static List<V3> vertices = new ArrayList<V3>();
	
	public GLSphere() {
		float one = 1;
		float gold = (float)((double)one/1.61803398875);
		float half = one / 2;

		vertices.add(new V3(-gold,+one,0));
		vertices.add(new V3(+gold,+one,0));
		vertices.add(new V3(0,+gold,-one));
		vertices.add(new V3(0,+gold,+one));
		vertices.add(new V3(-one,0,-gold));
		vertices.add(new V3(-one,0,+gold));
		vertices.add(new V3(+one,0,-gold));
		vertices.add(new V3(+one,0,+gold));
		vertices.add(new V3(0,-gold,-one));
		vertices.add(new V3(0,-gold,+one));
		vertices.add(new V3(-gold,-one,0));
		vertices.add(new V3(+gold,-one,0));
		List<Ti> ltris=new ArrayList<Ti>();
		ltris.add(new Ti(0,1,2));
		ltris.add(new Ti(1,0,3));
		ltris.add(new Ti(0,2,4));
		ltris.add(new Ti(0,4,5));
		ltris.add(new Ti(3,0,5));
		ltris.add(new Ti(2,1,6));
		ltris.add(new Ti(6,1,7));
		ltris.add(new Ti(1,3,7));
		ltris.add(new Ti(3,5,9));
		ltris.add(new Ti(7,3,9));
		ltris.add(new Ti(4,2,8));
		ltris.add(new Ti(2,6,8));
		ltris.add(new Ti(4,8,10));
		ltris.add(new Ti(5,4,10));
		ltris.add(new Ti(9,5,10));
		ltris.add(new Ti(9,10,11));
		ltris.add(new Ti(7,9,11));
		ltris.add(new Ti(6,7,11));
		ltris.add(new Ti(8,6,11));
		ltris.add(new Ti(10,8,11));
		tris=ltris.toArray(new Ti[ltris.size()]);
		BuildBuffers();
	}
	private void BuildBuffers(){	
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.size()*4*3);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb.asFloatBuffer();
		for(int i=0;i<vertices.size();i++){
			V3 v=vertices.get(i);
			mVertexBuffer.put(new float[]{v.x,v.y,v.z});
		}
		mVertexBuffer.position(0);
		
		vbb = ByteBuffer.allocateDirect(vertices.size()*4*3);
		vbb.order(ByteOrder.nativeOrder());
		mNormalBuffer = vbb.asFloatBuffer();
		for(int i=0;i<vertices.size();i++){
			V3 v=vertices.get(i);
			v.norm();
			mNormalBuffer.put(new float[]{v.x,v.y,v.z});
		}
		mNormalBuffer.position(0);
		
		mIndexBuffer = ByteBuffer.allocateDirect(tris.length * 2*3).order(ByteOrder.nativeOrder()).asShortBuffer();
		for(int i=0;i<tris.length;i++){
			Ti t=tris[i];
			mIndexBuffer.put(t.vs);
		}
		mIndexBuffer.position(0);
		
		
		
		// ...
		//ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
		//tbb.order(ByteOrder.nativeOrder());
		//mTextureBuffer = tbb.asIntBuffer();
		//mTextureBuffer.put(texCoords);
		//mTextureBuffer.position(0);
		
	}
	
	private void BuildAdjacent(){
		
	}
	
	public void NodeToTris(Node n){
		for(Ti t:tris){
			//ray
			V3 p0=new V3(0,0,0);
			V3 p1=new V3(n.pos);
			
		}
	}

	private void SubTri(Ti t,int idivs){	
		int siv=0,ix,iy,iv=0;
		V3[] verts=vertices.toArray(new V3[vertices.size()]);
		for(ix=0;ix<=idivs;ix++){
			for(iy=0;iy<=ix;iy++){
				V3 v=new V3();
v.eq(
		verts[t.vs[0]].a(
			( ( verts[t.vs[1]].s(verts[t.vs[0]]) ).d(idivs) ).m(ix)
		)
	);
v.ae (   ( verts[t.vs[2]].s(verts[t.vs[1]])    ).d(idivs).m(iy)  );
	
	
		v.norm();
		Node n=new Node(v.x,v.y,v.z);
		if(!(ix==2&&iy==1))
			n.obj=new GLSphere();
			else
		n.obj=new GLSphere();
		t.nodes.add(n);
//		vertices.add(v);
			}
		}
/*		
		
		for(ix=1;ix<=idivs;ix++){
			for(iy=0;iy<ix-1;iy++){
				pGeo->pTris[*it].i_verts[0]=siv+iy;
				pGeo->pTris[*it].i_verts[1]=siv+ix+iy;
				pGeo->pTris[*it].i_verts[2]=siv+ix+1+iy;
				*it=*it+1;
				pGeo->pTris[*it].i_verts[0]=siv+iy;
				pGeo->pTris[*it].i_verts[1]=siv+ix+1+iy;
				pGeo->pTris[*it].i_verts[2]=siv+1+iy;
				*it=*it+1;
			}
			pGeo->pTris[*it].i_verts[0]=siv+iy;
			pGeo->pTris[*it].i_verts[1]=siv+ix+iy;
			pGeo->pTris[*it].i_verts[2]=siv+ix+1+iy;
			*it=*it+1;
			siv+=ix;
		}*/
	}
	
	public void SubDivide(int iDivs){
		for(int i=0;i<tris.length;i++){
			SubTri(tris[i],iDivs);
		}
		MergeVerts(0,0.001f);
		BuildBuffers();
	}
	
	private void MergeVerts(int start,double tol){
		//Merge verts
		V3[] verts=vertices.toArray(new V3[vertices.size()]);
		
		for(int a=start;a<verts.length;a++){
			for(int b=a+1;b<verts.length;b++){
				if(verts[a].s(verts[b]).lengthsquared()<=tol){
					/*for(int q=0;q<pGeo->itris;q++){
						for(unsigned w=0;w<3;w++)
							if(pGeo->pTris[q].i_verts[w]==b)
							pGeo->pTris[q].i_verts[w]=a;
					}*/
					verts[b].eq(new V3(0,0,0));
				}
			}
		}
	}
	
	@Override
	public void draw(GL10 gl) {
		gl.glPushMatrix();
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		if (mNormalBuffer != null) {
			// Enabled the normal buffer for writing and to be used during rendering.
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			
			// Specifies the location and data format of an array of normals to use when rendering.
			gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
		}
		//	gl.glEnableClientState(GL10.GL_INDEN_ARRAY);
		gl.glScalef(1f,1f,1f);
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		//	gl.glIndexPointer(3,GL10.GL_SHORT,0,mIndexBuffer);
		
		gl.glNormal3f(0, 0, 1);
		gl.glDrawElements(GL10.GL_TRIANGLES, 60,GL10.GL_UNSIGNED_SHORT,mIndexBuffer);
		
		gl.glPopMatrix();
	}
	
	
	static void loadTexture(GL10 gl, Context context, int resource) {
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), resource);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		bmp.recycle();
	}
	
	public void ReorganiseData(V3[] verts){
		/*int iverts=0;
		int itriangles=0;
		int i;
		
		/*counts triangles
		for(i=20;i<max_triangles;i++){
			if(pGeo->pVerts[pGeo->pTris[i].i_verts[0]].v!=vector(0,0,0)){
				itriangles++;
			}
		}*
		
		vert*pVerts=new vert[iverts];
		triangle*pTris=new triangle[itriangles];
		
		
		unsigned iit=0;
		for(i=20;i<max_triangles;i++){
			if(pGeo->pVerts[pGeo->pTris[i].i_verts[0]].v!=vector(0,0,0)){
				for(unsigned w=0;w<3;w++){
					pTris[iit].i_verts[w]=pGeo->pTris[i].i_verts[w];
				}
				iit++;
			}
		}
		
		unsigned iv=0;
		for(i=0;i<max_verts;i++){
			if(pGeo->pVerts[i].v!=vector(0,0,0)){
				pVerts[iv].v=pGeo->pVerts[i].v;
				for(unsigned it=0;it<itriangles;it++){
					for(unsigned w=0;w<3;w++)
					if(pTris[it].i_verts[w]==i){
						pTris[it].i_verts[w]=iv;
					}
				}
				iv++;
			}
		}
		
		delete[max_verts]pGeo->pVerts;
		delete[max_triangles]pGeo->pTris;
		pGeo->iverts=iverts;
		pGeo->pVerts=new vert[iverts];
		pGeo->itris=itriangles;
		pGeo->pTris=new triangle[itriangles];
		for(i=0;i<iverts;i++){
			pGeo->pVerts[i].v=pVerts[i].v;
		}
		for(i=0;i<itriangles;i++){
			for(unsigned w=0;w<3;w++){
				pGeo->pTris[i].i_verts[w]=pTris[i].i_verts[w];
			}
		}*/
	}
	
}

