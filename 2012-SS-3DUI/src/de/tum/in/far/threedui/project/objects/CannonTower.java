package de.tum.in.far.threedui.project.objects;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import de.tum.in.far.threedui.general.TransformableObject;
import de.tum.in.far.threedui.project.AngularCoords;
import de.tum.in.far.threedui.project.core.ModelLoader;
import de.tum.in.far.threedui.project.gamelogic.CannonTowerController;

public class CannonTower extends TransformableObject{
	
	private ModelLoader loader;
	private BranchGroup bGroup;
	private Transform3D turretRotation;
	private Transform3D barrelElevation;
	private CannonTowerController controller;
	private float barrelYOffset = 0.02f;
	private Type towerType;
	private int fireRate = 10;
	private float turretAngle = 0;
	private float barrelAngle = 0;
	private float projectileLifetime;
	private float projectileSpeed = 0.0025f;
	
	private Vector3f aimPoint;
	
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
		return null;
				
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
			barrelYOffset = 0.02f;

			projectileLifetime = 50;
			
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
			
			projectileLifetime = 30;
			projectileSpeed = 0.015f;
			fireRate = 50;
			
		}
		else if(type == Type.GATLING)
		{
			cannonTop = this.loader.getModelObject("Gatling-Top");
			cannonBase = this.loader.getModelObject("Gatling-Base");
			cannonBarrel = this.loader.getModelObject("Gatling-Barrel");
			barrelYOffset = 0.013f;
			
			projectileLifetime = 100;
			projectileSpeed = 0.004f;
			fireRate = 5;
			
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
		this.aimPoint = new Vector3f(aimPoint);
		
		Transform3D t3d = new Transform3D();
		Point3f position = new Point3f();
		bGroup.getLocalToVworld(t3d);
		

		Vector3f defaultDir = new Vector3f(0.0f,-1.0f,0.0f);
		
		
		t3d.invert(); //Get the World to Local Matrix
		t3d.transform(aimPoint); //AImpoint is now in local coordinate system
		
		
		//System.out.println("AimPoint "+ aimPoint);
		
		Vector3f dirVector = new Vector3f(aimPoint);
		dirVector.sub(position);
		
		AngularCoords ang = AngularCoords.fromVector(dirVector);
		if (!Float.isNaN(ang.yaw)) setTurretAngle(ang.yaw);
		if (!Float.isNaN(ang.pitch)) setBarrelElevation(-ang.pitch);

	}


	
	public void setTurretAngle(float angle)
	{
		this.turretAngle = angle;
		
		turretRotation.rotY(angle);
		turretRotGroup.setTransform(turretRotation);
	}
	
	public void setBarrelElevation(float angle)
	{
		// TODO: add constraints here?
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
		
		return v;
//		System.out.println(v);
		/*
		
		Transform3D t3d = new Transform3D();
		Vector3f currentPos = new Vector3f();
		this.getLocalToVworld(t3d);
		t3d.get(currentPos);
		
		
		
		Vector3f v = new Vector3f();
		v.sub(aimPoint,currentPos);
		
		return v;
		
		*/
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
