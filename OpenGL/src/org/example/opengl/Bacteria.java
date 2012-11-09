package org.example.opengl;

import android.util.*;
import java.nio.*;
import java.util.*;
import javax.microedition.khronos.opengles.*;

class Bacteria extends GLSphere{
	public static List<Bacteria> bacterium=new ArrayList<Bacteria>();
	public static float scale=0.18f;
	private static float start=0.6f;
	private static float growth=0.1f;
	private static float digestion=.2f;
	private static float follow=0.1f;
	private static float push=0.1f;
	private static float eatEff=.71f;
	private static float enlarge=.3f;
	private static float empf=1f;
	
	private static FloatBuffer mVertexBuffer;
	private static ShortBuffer mIndexBuffer;
	private static FloatBuffer mNormalBuffer;
	
	private V3 dir=new V3();
	public V3 eats=new V3();
	public V3 render=new V3();
	public V3 is=new V3();
	public float radius=start;
	private float age=0;

	
	public Bacteria(float ix,float iy,float iz,float ex,float ey,float ez,float x,float y,float z){
		is=new V3(ix,iy,iz);
		eats=new V3(ex,ey,ez);
		pos=new V3(x,y,z);
		pos.norm();
		World.Add(this);
//		BuildBuffers();
	}
	public Bacteria(V3 i,V3 e,V3 p){
		is=new V3(i.x,i.y,i.z);
		eats=new V3(e.x,e.y,e.z);
		pos=new V3(p.x,p.y,p.z);
		pos.norm();
		World.Add(this);
//		BuildBuffers();
	}
	@Override
	public boolean Process(float fTime){
		super.Process(fTime);
		if(radius<=0){
			Die();
			return false;
		}else if(radius<=0.1f){
			radius-=growth*Frame.time*1.05f;
		}
		age+=fTime;
		if(age>=2f){
			World.Add(this);
			age=0;
		}
		Random r=new Random();
		radius+=r.nextFloat()*growth*fTime;
		if(radius>=1){
			Divide();
		}
		pos.ae(dir.m(fTime));
		dir.eq(dir.m(0.9f));
		pos.norm();
		return true;
	}
	public void Divide(){
		radius-=1f-start;
		Random r=new Random();
		float spread=scale*start;
		V3 p=new V3(r.nextFloat()-0.5f,r.nextFloat()-0.5f,r.nextFloat()-0.5f);
		p.norm();
		p.eq(p.m(spread/2));
		Bacteria n=new Bacteria(is, eats, new V3(pos.x+p.x,pos.y+p.y,pos.z+p.z));
		p.eq(p.m(-1));
		pos.ae(p);
//		dir.eq(dir.m(1f));
		n.dir.eq(dir);
		n.render.eq(render);
//		World.Add(this);
//		World.Add(n);
		Bacteria.bacterium.add(n);
	
	}
	public boolean Collided(Bacteria bb){
		return bb.pos.s(pos).lengthsquared()>radius*radius+bb.radius*bb.radius;
	}
	public float Size(){
		return (radius*empf+enlarge)*scale;
	}
	@Override
	public void Collide(Entity bb,float fTime){
		if(radius<=0||bb.radius<=0)return;
		if(bb.getClass() != getClass())return;

		
		float dist=Distance((Bacteria)bb);
		if(dist<Size()-radius*scale){
			//test eat
			float eat=((Bacteria)bb).is.m(((Bacteria)bb).radius).m(eats.m(radius)).sum();
			//bb.is.x*eats.x+bb.is.y*eats.y+bb.is.z*eats.z;
		
			//test eaten
			float eaten=is.m(radius).m( ((Bacteria)bb).eats.m(((Bacteria)bb).radius) ).sum();
			eat-=eaten;
			eat*=0.3f;
			if(eat>0){
				Eat((Bacteria)bb,eat);
			}else if(eat<0){
				((Bacteria)bb).Eat(this,eat);
			
			}
			}
			if(dist<=0){
			//	bb.size=10;
			
				V3 d=pos.s(((Bacteria)bb).pos);
				d.norm();
				d.eq(d.m(-push*Frame.time));
				((Bacteria)bb).dir.ae(d);
				d.eq(d.m(-1));
				dir.ae(d);
				radius-=growth*Frame.time*.6f;
				((Bacteria)bb).radius-=growth*Frame.time*.6f;
				}
		
	}
	public void Eat(Bacteria bb,float eat){
/*		bb.is=new V3(is);
		bb.eats=new V3(eats);
		bb.size=start;*/
		bb.radius-=digestion*Frame.time;
		radius+=digestion*Frame.time*eatEff;
		V3 d=new V3();
		d.eq(pos.s(bb.pos));
		d.norm();
		dir.ae(d.m(Frame.time*-follow));
	}
	public void Die(){
		bacterium.remove(this);
		for(Node n:World.nodes){
			n.objs.remove(this);
		}
	}
	public float Distance(Bacteria bb){
		return bb.pos.s(pos).length()-(radius*scale+bb.radius*scale);
	}
	
	private void BuildBuffers(){
		int nVerts=50;
		ByteBuffer vbb = ByteBuffer.allocateDirect(nVerts*4*3);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb.asFloatBuffer();
		for(int i=0;i<nVerts;i++){
			float a=((float)Math.PI*2)/nVerts*i;
			V3 v=new V3(FloatMath.sin(a),FloatMath.cos(a),0);
			mVertexBuffer.put(new float[]{v.x,v.y,v.z});
		}
		mVertexBuffer.position(0);
		/*
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
		*/
		
		
		// ...
		//ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
		//tbb.order(ByteOrder.nativeOrder());
		//mTextureBuffer = tbb.asIntBuffer();
		//mTextureBuffer.put(texCoords);
		//mTextureBuffer.position(0);
		
	}
	
	@Override
	public void draw(GL10 gl){
//		BuildBuffers();
//		if(size<=0)return;
//		gl.glColor4f(is.x, is.y, is.z, 1);
		gl.glPushMatrix();
//		gl.glLoadIdentity();
		gl.glTranslatef(pos.x,pos.y,pos.z);
//		float s=radius*empf+enlarge;
//		if(s>0.85f)s=0.85f;
		float sc=Size();
		gl.glScalef(sc,sc,sc);
		float matAmbient[] = new float[] { 1f, 1f, 1f, 1f };
//		float o=0.9f;
		float matDiffuse[] = new float[] { render.x, render.y, render.z, 1 };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,matDiffuse, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,matDiffuse, 0);

/*		if (mNormalBuffer != null) {
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
		}*/
		//	gl.glEnableClientState(GL10.GL_INDEN_ARRAY);
		
		/*
		gl.glNormal3f(0,0,1);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		//	gl.glIndexPointer(3,GL10.GL_SHORT,0,mIndexBuffer);
		//gl.glDrawElements(GL10.GL_TRIANGLES, 20,GL10.GL_UNSIGNED_SHORT,mIndexBuffer);
		
		gl.glDrawArrays(GL10.GL_POINTS,0,50);//*/
		super.draw(gl);
		gl.glPopMatrix();
//		matAmbient = new float[] { 1f, 1f, 1f, 1f };
//		matDiffuse = new float[] { 1f, 1f, 1f, 1f };
//		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,matAmbient, 0);
//		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,matDiffuse, 0);
	}
}

