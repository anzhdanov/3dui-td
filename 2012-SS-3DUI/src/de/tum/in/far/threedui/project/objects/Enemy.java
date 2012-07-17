package de.tum.in.far.threedui.project.objects;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;

import de.tum.in.far.threedui.general.TransformableObject;

public class Enemy extends TransformableObject {

	public float speed = 10000;
	
	public AnimationPosition animation;
	
	public String name = "test";
	
	
	
	
	public Enemy(Appearance app, float speed) {
		
		this.setCapability(BranchGroup.ALLOW_DETACH);
		
		this.speed = speed;
	
		Transform3D trans = new Transform3D();
		trans.setTranslation(new Vector3f(0, 0, 0.01f));
		TransformGroup sphereTransGroup = new TransformGroup(trans);
		
		Sphere s = new Sphere(0.01f,app);
		sphereTransGroup.addChild(s);
		transGroup.addChild(sphereTransGroup);
		
		animation = new AnimationPosition(this);
		
		
		
	}


	
	public void setPath(Point3f[] posList)
	{
		animation.setPositions(posList);
	}
	

}
