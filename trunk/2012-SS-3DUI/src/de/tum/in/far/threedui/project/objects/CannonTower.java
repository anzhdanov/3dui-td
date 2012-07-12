package de.tum.in.far.threedui.project.objects;

import java.awt.Font;
import java.awt.Point;
import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Font3D;
import javax.media.j3d.Geometry;
import javax.media.j3d.OrientedShape3D;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import de.tum.in.far.threedui.general.BlueAppearance;
import de.tum.in.far.threedui.general.TransformableObject;
import de.tum.in.far.threedui.project.core.ModelLoader;
import de.tum.in.far.threedui.project.core.ModelLoader.ModelFormat;

public class CannonTower extends TransformableObject{
	
	private ModelLoader loader;
	private BranchGroup bGroup;
	private Transform3D turretRotation;
	private Transform3D barrelElevation;
	private CannonTowerController controller;
	private float barrelYOffset = 0.02f;
	private Type towerType;
	private int fireRate = 5;
	private float turretAngle = 0;
	private float barrelAngle = 0;
	private float projectileLifetime;
	private float projectileSpeed = 0.0025f;
	
	private TransformGroup turretRotGroup;
	private TransformGroup barrelRotGroup;
	
	public static enum Type
	{
		GUN,
		DOUBLEGUN,
		PRECISION,
		GATLING,
		HOWITZER
	}
	
	public static Type getTypeByID(int id)
	{
		switch(id)
		{
		case 0:
			return Type.GUN;
		case 1:
			return Type.DOUBLEGUN;
		case 2:
			return Type.PRECISION;
		case 3:
			return Type.GATLING;
		case 4:
			return Type.HOWITZER;
		default:
		return Type.GUN;
				
		}
	}
	
	public CannonTower(Type type)
	{
		this.towerType = type;
		this.setCapability(BranchGroup.ALLOW_DETACH);
		this.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);

		this.setCapability(BranchGroup.ALLOW_CHILDREN_READ);

		this.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		

		
		TransformableObject cannonTop = null;
		TransformableObject cannonBase = null;
		TransformableObject cannonBarrel = null;
		
		this.bGroup = new BranchGroup();
		this.loader = ModelLoader.getInstance();
		
		if(type == Type.GUN)
		{
			cannonTop = this.loader.getModelObject("Gun-Top");
			cannonBase = this.loader.getModelObject("Gun-Base");
			cannonBarrel = this.loader.getModelObject("Gun-Barrel");
			projectileLifetime = 50;
			
			barrelYOffset = 0.02f;
		}
		else if(type == Type.DOUBLEGUN)
		{
			cannonTop = this.loader.getModelObject("DoubleGun-Top");
			cannonBase = this.loader.getModelObject("DoubleGun-Base");
			cannonBarrel = this.loader.getModelObject("DoubleGun-Barrel");
			barrelYOffset = 0.016f;
			projectileLifetime = 50;
		}
		else if(type == Type.PRECISION)
		{
			cannonTop = this.loader.getModelObject("Precision-Top");
			cannonBase = this.loader.getModelObject("Precision-Base");
			cannonBarrel = this.loader.getModelObject("Precision-Barrel");
			barrelYOffset = 0.024f;
			projectileLifetime = 200;
			projectileSpeed = 0.007f;
			
		}
		else if(type == Type.GATLING)
		{
			cannonTop = this.loader.getModelObject("Gatling-Top");
			cannonBase = this.loader.getModelObject("Gatling-Base");
			cannonBarrel = this.loader.getModelObject("Gatling-Barrel");
			barrelYOffset = 0.013f;
			
			projectileLifetime = 100;
			projectileSpeed = 0.005f;
			
		}
		else if(type == Type.HOWITZER)
		{
			cannonTop = this.loader.getModelObject("Howitzer-Top");
			cannonBase = this.loader.getModelObject("Howitzer-Base");
			cannonBarrel = this.loader.getModelObject("Howitzer-Barrel");
			barrelYOffset = 0.013f;
			
			projectileLifetime = 500;
		}
		
		
		Transform3D rotate = new Transform3D();
		rotate.rotX(Math.PI/2);
		
		TransformGroup rotateUpRight = new TransformGroup(rotate);
		rotateUpRight.addChild(cannonBase);
		
		turretRotation = new Transform3D();
		turretRotation.rotY(0.0f);
		turretRotGroup = new TransformGroup(turretRotation);
		turretRotGroup.addChild(cannonTop);
		turretRotGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		BranchGroup cannonTopGroup = new BranchGroup();
		cannonTopGroup.addChild(turretRotGroup);
		
		
		BranchGroup barrelGroup = new BranchGroup();
		Transform3D barrelTranslation = new Transform3D();
		barrelTranslation.setTranslation(new Vector3f(0.0f,barrelYOffset,0.0f));
		
		barrelElevation = new Transform3D();
		barrelElevation.rotX(0.0f);
		barrelElevation.mul(barrelTranslation);
		
		
		barrelRotGroup = new TransformGroup(barrelElevation);
		barrelRotGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		barrelRotGroup .addChild(cannonBarrel);
		barrelGroup.addChild(barrelRotGroup );
		
		turretRotGroup.addChild(barrelGroup);
		
		
		rotateUpRight.addChild(cannonTopGroup);
		
		bGroup.addChild(rotateUpRight);
		
		
		controller = new CannonTowerController(this);
		
		super.transGroup.addChild(controller);
		super.transGroup.addChild(bGroup);
		
	
		
		
	}
	
	public BranchGroup getGroup()
	{
		return this.bGroup;
	}
	
	/***
	 * Expects the target as World Coordinates
	 * Right now it's simplified since I assume that the markers will sit almost parallel on one plane
	 */
	public void aimAtPoint(Point3f aimPoint)
	{
		
		Transform3D t3d = new Transform3D();
		Point3f position = new Point3f();
		bGroup.getLocalToVworld(t3d);
		
		
		t3d.invert(); //Get the World to Local Matrix
		t3d.transform(aimPoint); //AImpoint is now in local coordinate system
		
		
		//System.out.println("AimPoint "+ aimPoint);
		
		Vector3f dirVector = new Vector3f(aimPoint);
		dirVector.sub(position);
		dirVector.normalize(); //Now we've got the normalized direction
		
		//System.out.println("direction "+dirVector);
		

		Vector3f dir2 = new Vector3f();
		dir2.x = dirVector.x;
		dir2.y = dirVector.y;
		dir2.z = 0;
		

		float angle_elev = dir2.angle(dirVector);
		
		Vector3f current = new Vector3f(0.0f,-1.0f,0.0f);

		float angle_rot = current.angle(dirVector);
		//System.out.println("Angle: "+angle_rot);

		if(Float.isNaN(angle_rot)) angle_rot = 0;
		if(Float.isNaN(angle_elev)) angle_elev = 0;
		
		if(aimPoint.x<0) angle_rot *= -1;
		if(aimPoint.z>position.z) angle_elev *= -1;
		
		setTurretAngle(angle_rot);
		setBarrelElevation(angle_elev);
		
		
	}


	
	public void setTurretAngle(float angle)
	{
		this.turretAngle = angle;
		
		turretRotation.rotY(angle);
		turretRotGroup.setTransform(turretRotation);
	}
	
	public void setBarrelElevation(float angle)
	{
		this.barrelAngle = angle;
		Transform3D barrelTranslation = new Transform3D();
		barrelTranslation.setTranslation(new Vector3f(0.0f,barrelYOffset,0.0f));
		barrelElevation.rotX(angle);
		barrelTranslation.mul(barrelElevation);
		barrelRotGroup.setTransform(barrelTranslation);
	}

	public Type getType()
	{
		return this.towerType;
	}
	
	public int getFireRate()
	{
		return this.fireRate;
	}
	
	public Vector3f getAimVector()
	{
		
		Vector3f v = new Vector3f(0.0f,-1.0f,0.0f);

		barrelElevation.rotX(barrelAngle);
		barrelElevation.transform(v);
		turretRotation.rotZ(turretAngle);
		turretRotation.transform(v);
		
		
		System.out.println(v);
		return v;
		
		
	}
	public float getProjectileLifetime()
	{
		return this.projectileLifetime;
	}
	
	public Vector3f getBarrelTip()
	{
		return new Vector3f(0,0,barrelYOffset);
	}
	
	
	public Vector3f getBarrelTipAsWorldCoords()
	{
		Transform3D t3d = new Transform3D();
		this.getLocalToVworld(t3d);
		Vector3f v = getBarrelTip();
		t3d.transform(v);
		
		return v;
	}
	
	public float getProjectileSpeed()
	{
		return this.projectileSpeed;
	}

}
