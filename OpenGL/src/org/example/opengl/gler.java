package org.example.opengl;

import java.nio.*;
import java.util.*;
import javax.microedition.khronos.opengles.*;
import org.example.opengl.*;

public class gler
{
private class Int{int i=0;public Int(int i){this.i=i;}}
private class Short{short i=0;public Short(short i){this.i=i;}}
private short vCount=0;
public int what=0;
public GL10 gl;
public boolean alpha=false;
	private FloatBuffer mVertexBuffer;
	private ShortBuffer mIndexBuffer;
	private FloatBuffer mNormalBuffer;

	private List<Ti> tris=new ArrayList<Ti>();
	private List<V3> vertices = new ArrayList<V3>();
//	private List<Int> offs=new ArrayList<Int>();
	
	private List<Short> ids=new ArrayList<Short>();
public void BuildBuffers(){
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

	mIndexBuffer = ByteBuffer.allocateDirect(ids.size() * 2*3).order(ByteOrder.nativeOrder()).asShortBuffer();
	for(int id=0;id<ids.size();id++){
		short t=ids.get(id).i;
		mIndexBuffer.put(t);
	}
	mIndexBuffer.position(0);
}
	public gler(GL10 gl){
		this.gl=gl;
//		offs.add(new Int(0));
	}
	public void begin(int what){
		if(mVertexBuffer!=null){
			clear();
		}
		this.what=what;
	}
	public void end(){
//		if(mVertexBuffer==null)
		BuildBuffers();
//		offs.add(new Int(vCount));
//		Draw();
	}
	public void clear(){
		mVertexBuffer.clear();
		mNormalBuffer.clear();
		mIndexBuffer.clear();
		mVertexBuffer=null;
		tris.clear();
		ids.clear();
		vertices.clear();
		what=0;
		vCount=0;
	}
	public void Draw(){
//		if(mVertexBuffer==null)BuildBuffers();
		gl.glDisable(gl.GL_CULL_FACE);
		if(alpha){
		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		}else{	
			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glDisable(GL10.GL_BLEND);
//			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		}
		switch( what){
			default:
//			case gl.GL_TRIANGLES:
		if (mNormalBuffer != null) {
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
		}else{
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		}

//		for(int i=0;i<offs.size()-1;i++){
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
			gl.glDrawElements(what,vertices.size(),GL10.GL_UNSIGNED_SHORT,mIndexBuffer);
//		}
//		break;
		}
	}
public void vertex(V3 v){
	vertices.add(v);
	ids.add(new Short(vCount));
	vCount++;
}
}

