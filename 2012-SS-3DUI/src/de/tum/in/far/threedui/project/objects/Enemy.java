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

	
	private AnimationPosition animation;
	
	public String name = "UnnamedEnemy";
	
	private float speed = 10000;
	private int health = 5;
	
	public Enemy(Appearance app, float speed, int health) {
		
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

	public void attachToPath(PathObject pathObject) {
		animation.setPositions(pathObject.getWayPoints());
		pathObject.attachToPath(this.animation);
	}
	
	
	public float getSpeed()
	{
		return this.speed;
	}

	/**
	 * Damage the enemy
	 * @return true if enemy died
	 */
	public boolean damage()
	{
		health--;

		if (health <= 0) {
			this.animation.detach();
			return true;
		}
		return false;
	}

}
