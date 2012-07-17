package de.tum.in.far.threedui.project.objects;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;

import de.tum.in.far.threedui.general.TransformableObject;
import de.tum.in.far.threedui.project.EnemyAppearance;

public class Enemy extends TransformableObject {

	
	private AnimationPosition animation;
	private EnemyAppearance app;
	
	public String name = "UnnamedEnemy";
	
	private float speed = 10000;
	private int maxHealth = 5;
	private int currentHealth = 5;
	
	public Enemy(EnemyAppearance app, float speed, int health) {
		this.setCapability(BranchGroup.ALLOW_DETACH);
		app.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
		
		this.app = app;
		this.speed = speed;
		this.maxHealth = health;
		this.currentHealth = health;
		
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
		this.currentHealth--;
		
		float factor = ((float)this.currentHealth) / this.maxHealth;
		app.changeColor(factor);
		
		if (this.currentHealth <= 0) {
			this.animation.detach();
			return true;
		}
		return false;
	}
	
	public void destroy()
	{
		this.animation.detach();
	}

	public boolean reachedEnd() {
		return animation.isFinished();
	}

}
