package org.example.opengl;

import android.util.*;
import java.nio.*;
import java.util.*;
import javax.microedition.khronos.opengles.*;

class Bacteria extends Entity{
	private static gleser g=null;
	public static List<Bacteria> bacterium=new ArrayList<Bacteria>();
	public static float scale=     0.3f;
	protected static float start=    scale*.4f;
	protected static float growth=   scale*0.3f;
	protected static float digestion=growth*5f;
	protected static float follow=   scale*0.5f;
	protected static float push=     scale*0.8f;
	protected static float eatEff=1f;
	protected static float enlarge=  scale*.2f;
	protected static float empf=.6f;
	protected static float crush=.3f;
	protected static float drag=.92f;
	
	private static FloatBuffer mVertexBuffer;
	private static ShortBuffer mIndexBuffer;
	private static FloatBuffer mNormalBuffer;
	
	public V3 pos2=new V3();
	public V3 dir2=new V3();

	public V3 eats=new V3();
	public V3 render=new V3();
	public V3 is=new V3();

	protected float age=0;
	
	private int renderMode=1;
	private V3 xAxis=new V3(1,0,0);
	
	public boolean Calc(){
		if(this.radius<=this.start*.6f){
			this.radius-=this.growth*1.1f*Frame.time;
		}
		this.age+=Frame.time;
		if(this.age>=1f){
			World.Add(this);
			this.age=0;
		}
		Random r=new Random();
		this.radius+=r.nextFloat()*this.growth*Frame.time;
		if(this.radius>this.scale){
			Divide();
		}
		return true;
	}
	@Override public void Feel(Entity e,collision c){
		if(c.eat>0) this.Eat((Bacteria)e,c.eat);
	}
	@Override public void Touch(Entity e,collision c){
		((Bacteria)e).GD(c.b).ae(c.d);
//	((Bacteria)e).GP(c.b).ae(c.d.m(Frame.time));
	}
	@Override public void Crush(Entity e,collision c){
//		if(eat>0)
;//			this.Eat((Bacteria)e,eat);
//		else
		if(c.eat>=0){
			
			e.radius-=this.growth*Frame.time*this.crush;
			}
			((Bacteria)e).GD(c.b).ae(c.d);
	}

	
	public Bacteria(float x,float y,float z){
		is.randPos();
		eats.randPos();
		pos=new V3(x,y,z);
		pos.norm();
		pos2=new V3(x+start,y,z);
		pos2.norm();
		this.radius=start;
	}
	public Bacteria(float ix,float iy,float iz,float ex,float ey,float ez,float x,float y,float z){
		is=new V3(ix,iy,iz);
		eats=new V3(ex,ey,ez);
		pos=new V3(x,y,z);
		pos.norm();
		pos2=new V3(x+start,y,z);
		pos2.norm();
		this.radius=start;
//		BuildBuffers();
	}
	public Bacteria(V3 i,V3 e,V3 p){
		is=new V3(i.x,i.y,i.z);
		eats=new V3(e.x,e.y,e.z);
		pos=new V3(p.x,p.y,p.z);
		pos.norm();
		pos2=new V3(p.x+start,p.y,p.z);
		pos2.norm();
		this.radius=start;
//		BuildBuffers();
	}
	public Bacteria (){
		Random();
	}
	public void Random(){
		is.randPos();
		eats.y=is.x;
		eats.z=is.y;
		eats.x=is.z;
		render.eq(is);
		radius=this.start;
	}
	@Override
	public boolean Process(float fTime){
		super.Process(Frame.time);
		if(this.radius<=0){
			Die();
			return false;
		}
		boolean ok=Calc();
		DefProc();
		return ok;
	}

	public void DefProc(){
		xAxis=pos2.s(pos);
		float dist=xAxis.length();
		xAxis.norm();
		V3 d=new V3();
		d.eq(xAxis.m((dist-radius)*-2f));
//		pos.ae(d);
		dir2.ae(d.m(Frame.time));
		d.me(-1f);
		dir.ae(d.m(Frame.time));
		float rand=.1f;
//		pos2=new V3(pos2.x+Frame.r.nextFloat()*rand*Frame.time,pos2.y+Frame.r.nextFloat()*rand*Frame.time,pos2.z+Frame.r.nextFloat()*rand*Frame.time);
		dir.randShift(Frame.time*rand);
		dir2.randShift(Frame.time*rand);
		pos.ae(dir.m(Frame.time));
		pos2.ae(dir2.m(Frame.time));
		dir.me(drag);
		dir2.me(drag);
		pos.norm();
		pos2.norm();
	}
	@Override public Entity Spawn(){
		return new Bacteria();
	}
	public void eq(Bacteria r){
		this.pos.eq(r.pos);
		this.pos2.eq(r.pos2);
		this.dir.eq(r.dir);
		this.is.eq(r.is);
		this.eats.eq(r.eats);
		this.render.eq(r.render);
		this.radius=r.radius;
		this.age=r.age;
	}
	public void Divide(){
		this.radius=this.start;
		Bacteria n=(Bacteria)Spawn();
		n.eq(this);
		n.pos.eq(pos2);
		n.pos2=new V3(pos2.s(pos).m(0.4f).a(pos));
		pos2=new V3(pos.s(pos2).m(0.4f).a(pos2));
		n.dir.eq(dir);
		n.render.eq(render);

		Append(n);
	
	}
	public static void Append(Bacteria b){
		Bacteria.bacterium.add(b);
		World.Add(b);
	}
	public float EatRate(Bacteria bb){
		//return bb.is.m(bb.radius).m(this.eats.m(radius)).sum();
		return bb.is.m(this.eats).sum();
	}
	@Override
	public void Collide(Entity bb,float fTime){
		if(this.radius<=0||bb.radius<=0)return;
		
		float eq=this.enlarge*this.enlarge;

		
		collision c=Distance((Bacteria)bb);
		c.eat=EatRate((Bacteria)bb);
		float eaten=((Bacteria)bb).EatRate(this);
//		eat-=eaten;
		
		if(c.dist<eq){
			this.Feel(bb,c);
			if(c.dist<eq/2){
			//	bb.size=10;
		
				Touch(bb,c);
				if(c.dist<=0){
					Crush(bb,c);
				}
			}
			c.d.eq(c.d.m(-1));
			c.eat=eaten;
			bb.Feel(this,c);
			if(c.dist<eq/2){
				bb.Touch(this,c);
				if(c.dist<=0){
					bb.Crush(this,c);
				}
			}
		}
	}

	public void Eat(Bacteria bb,float eat){
/*		bb.is=new V3(is);
		bb.eats=new V3(eats);
		bb.size=start;*/
		float e=eat*this.digestion*Frame.time;
		bb.radius-=e;
		this.radius+=e*eatEff;
		V3 d=new V3();
		d.eq(pos.s(bb.pos));
		d.norm();
		dir.ae(d.m(Frame.time*-this.follow));
	}
	public void Die(){
		bacterium.remove(this);
		for(Node n:World.nodes){
			n.objs.remove(this);
		}
	}
	public collision Distance(Bacteria bb){
		collision c=new collision();

	//	float rs=(radius*radius+bb.radius*bb.radius)/7;
		float rs=scale*.06f;
		float r1= bb.pos.s(pos).lengthsquared()-(rs);
		float r2= bb.pos.s(pos2).lengthsquared()-(rs);
		float r3= bb.pos2.s(pos).lengthsquared()-(rs);
		float r4= bb.pos2.s(pos2).lengthsquared()-(rs);
		
		r1=Math.min(r1,r3);//pos dist
		r2=Math.min(r2,r4);//pos2 dist
		if(r1<r2){//pos closest
			c.a=false;
			if(r1==r3){
				c.b=true;
			}else{
				c.b=false;
			}
		}else{//pos2 closest
			c.a=true;
			if(r2==r4){
				c.b=true;
			}else{
				c.b=false;
			}
		}
		

		c.dist=Math.min(r1,r2);
		
		c.d=GP(c.a).s( ((Bacteria)bb).GP(c.b) );
		c.d.norm();
		c.d.eq(c.d.m(-this.push*Frame.time));
		return c;
	}
	public V3 GP(boolean t){
		if(t)return pos2;
		return pos;
	}
	public V3 GD(boolean t){
		if(t)return dir2;
		return dir;
	}
	private void G(){
		g.gl.glPushMatrix();
		float d;
if(renderMode>0){
		g.gl.glTranslatef(pos.x,pos.y,pos.z);

		M9 m=new M9();
		m.z.eq(pos);
		m.y.eq(xAxis);
		m.norm();
		m.gl(g.gl);
		if(radius<start){
			d=scale*.8f*radius/start;
		}else{
			d=scale*.8f;
		}
		g.gl.glScalef(d,radius*1.1f,d);
}
//		float matAmbient[] = new float[] { 1f, 1f, 1f, 1f };
		if(radius<start){
			d=radius/start;
		}else{
			d=1;
		}
		float matDiffuse[] = new float[] { render.x*d, render.y*d, render.z*d, d };
		g.gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,matDiffuse, 0);
		g.gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,matDiffuse, 0);
	}
	@Override public void draw(GL10 gl){
		if(radius<=0)return;
switch(renderMode){
		case 0:
		
		g=new gleser(gl);
		G();
		g.begin(gl.GL_LINES);
		g.vertex(pos);
		g.vertex(pos2);
		g.end();
		g.gl.glLineWidth(32);
		g.gl.glPointSize(32);
		g.Draw();
		g.current.what=g.gl.GL_POINTS;
		g.Draw();
//		g.clear();
		g.gl.glPopMatrix();
//		if(true)return;
break;
case 1:
		if(g!=null){
			G();
			g.Draw();
			g.gl.glPopMatrix();
			return;
		}//else
		g=new gleser(gl);
		G();
//		g.gl.glDisable(g.gl.GL_LIGHTING);
		final float height=1;
		final int rings=10;//(int)(radius/0.01f);
		final int veins=6;
		float inR=0;
		float outR=.1f;
		float ringHeight=height/rings;//radius/rings;
		final float curve=3;


		outR=0;
		final float width=.3f;//radius*0.3f;
		float a,x,x1=0;
		a=(float)(((0))/(curve*2)*Math.PI);
		x1=FloatMath.cos(0)*width;
//		if(width<scale*0.6f)width=scale*.6f;
		for(int s=0;s<rings;s++){
			inR=outR;
			x=x1;
			if(s<curve){
				a=(float)(((s+1))/(curve*2)*Math.PI);
				x1=FloatMath.cos(a)*width+ringHeight*(s+1);
//				inR=(float)Math.sin((float)(s)/(curve*2)*Math.PI)*radius;
				outR=FloatMath.sin(a)*width;
			}else if(s>=rings-curve){
				a=(float)((rings-s-1)/(curve*2)*Math.PI);
				x1=-FloatMath.cos(a)*width-ringHeight*(rings-s-1);
				outR=FloatMath.sin(a)*width;
//				inR=(float)Math.sin((float)((rings-s+1))/(curve*2)*Math.PI)*radius;
			}else{
//				inR=radius;
				x1=0;
				outR=width;
			}
			g.begin(g.gl.GL_TRIANGLE_STRIP);
			g.current.alpha=true;
			for(int v=0;v<=veins;v++){
				float r=(float)v/veins*(float)Math.PI*2;
				g.vertex(new V3(FloatMath.sin(r)*inR,s*ringHeight-ringHeight*curve-x,FloatMath.cos(r)*inR));
				g.vertex(new V3(FloatMath.sin(r)*outR,(s+1)*ringHeight-ringHeight*curve-x1,FloatMath.cos(r)*outR));
			}
			g.end();
//			g.clear();
		}
		g.Draw();
		break;
		default:break;
		}//switch render
	}//Draw
	public static void SetMode(int mode){
		switch(mode){
			case 0:
	start=    scale*.4f;
	growth=   scale*0.3f;
	digestion=growth*8f;
	follow=   scale*0.5f;
	push=     scale*0.8f;
	eatEff=1f;
	enlarge=  scale*.2f;
	empf=.6f;
	crush=.3f;
	break;
			default:break;
		}
	}
}

