package de.tum.in.far.threedui.project.objects;

import java.util.Vector;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.OrientedShape3D;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import de.tum.in.far.threedui.ex1.SphereObject;
import de.tum.in.far.threedui.general.BlueAppearance;
import de.tum.in.far.threedui.general.RedAppearance;
import de.tum.in.far.threedui.general.TransformableObject;

public class Projectile extends TransformableObject{
	
	private Vector3f position;
	private Vector3f velocity;
	private float lifeTime;
	private float damage;
	
	private SphereObject s;
	
	public Projectile(Vector3f pos, Vector3f vel, float lifeTime)
	{
		

		this.setCapability(BranchGroup.ALLOW_DETACH);
		
		this.position = pos;
		this.velocity = vel;
		
		this.lifeTime = lifeTime;
		
		OrientedShape3D textShape = new OrientedShape3D();
		/*QuadArray polygon1 = new QuadArray(4, QuadArray.COORDINATES | QuadArray.NORMALS);
		polygon1.setCoordinate(0, new Point3f (-0.0025f,-0.0025f,0.00f));
		polygon1.setCoordinate(1, new Point3f (0.0025f,-0.0025f,0f));
		polygon1.setCoordinate(2, new Point3f (0.0025f,0.0025f,0.00f));
		polygon1.setCoordinate(3, new Point3f (-0.0025f,0.0025f,0.00f));
		Vector3f polygon1Normal = new Vector3f(0.00f,0.00f,0.0025f);
		polygon1.setNormal(0,polygon1Normal);
		polygon1.setNormal(1,polygon1Normal);
		polygon1.setNormal(2,polygon1Normal);
		polygon1.setNormal(3,polygon1Normal);
		
		textShape.setGeometry(polygon1);
		textShape.setAppearance(new RedAppearance());
		textShape.setAlignmentMode(OrientedShape3D.ROTATE_ABOUT_POINT);
		textShape.setRotationPoint(new Point3f(0,0,0));
		*/
		
		
		s = new SphereObject(0.004f);
		transGroup.addChild(s);
		}
	

	public void updateProjectile()
	{
		this.position.add(velocity);
		
		Transform3D updTrans = new Transform3D();
		
		
		transGroup.getTransform(updTrans);

		updTrans.setTranslation(position);
		transGroup.setTransform(updTrans);
		lifeTime--;
		
	}
	
	public boolean isAlive()
	{
		if(this.lifeTime>0)return true;
		else return false;
	}
	
	public void getGlobalCoords(Transform3D trans)
	{
		s.getLocalToVworld(trans);
	}
	

}
