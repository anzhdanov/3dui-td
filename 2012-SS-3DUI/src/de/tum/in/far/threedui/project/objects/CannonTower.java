package de.tum.in.far.threedui.project.objects;

import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

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
	
	private TransformGroup turretRotGroup;
	private TransformGroup barrelRotGroup;
	
	public static int TYPE_GUN = 0;
	public static int TYPE_DOUBLEGUN = 1;
	public static int TYPE_PRECISION = 2;
	public static int TYPE_GATLING = 3;
	public static int TYPE_HOWITZER = 4;
	
	public CannonTower(int type)
	{
		
		
		this.bGroup = new BranchGroup();
		this.loader = new ModelLoader();
		
		if(type == TYPE_GUN)
		{
			this.loader.registerModel("GunTop", "gun-top.wrl", ModelFormat.VRML97);
			this.loader.registerModel("GunBase", "gun-base.wrl", ModelFormat.VRML97);
			this.loader.registerModel("GunBarrel", "gun-barrel.wrl", ModelFormat.VRML97);
			barrelYOffset = 0.02f;
		}
		else if(type == TYPE_DOUBLEGUN)
		{
			this.loader.registerModel("GunTop", "doublegun-top.wrl", ModelFormat.VRML97);
			this.loader.registerModel("GunBase", "doublegun-base.wrl", ModelFormat.VRML97);
			this.loader.registerModel("GunBarrel", "doublegun-barrel.wrl", ModelFormat.VRML97);
			barrelYOffset = 0.016f;
		}
		else if(type == TYPE_PRECISION)
		{
			this.loader.registerModel("GunTop", "precision-top.wrl", ModelFormat.VRML97);
			this.loader.registerModel("GunBase", "precision-base.wrl", ModelFormat.VRML97);
			this.loader.registerModel("GunBarrel", "precision-barrel.wrl", ModelFormat.VRML97);
			barrelYOffset = 0.02f;
		}
		else if(type == TYPE_GATLING)
		{
			this.loader.registerModel("GunTop", "gatling-top.wrl", ModelFormat.VRML97);
			this.loader.registerModel("GunBase", "gatling-base.wrl", ModelFormat.VRML97);
			this.loader.registerModel("GunBarrel", "gatling-barrel.wrl", ModelFormat.VRML97);
			barrelYOffset = 0.013f;
		}
		else if(type == TYPE_HOWITZER)
		{
			this.loader.registerModel("GunTop", "howitzer-top.wrl", ModelFormat.VRML97);
			this.loader.registerModel("GunBase", "howitzer-base.wrl", ModelFormat.VRML97);
			this.loader.registerModel("GunBarrel", "howitzer-barrel.wrl", ModelFormat.VRML97);
			barrelYOffset = 0.013f;
		}
		
		
		
		TransformableObject cannonTop = this.loader.getModelObject("GunTop");
		TransformableObject cannonBase = this.loader.getModelObject("GunBase");
		TransformableObject cannonBarrel = this.loader.getModelObject("GunBarrel");
		
		Transform3D rotate = new Transform3D();
		rotate.rotX(Math.PI/2);
		
		TransformGroup rotateUpRight = new TransformGroup(rotate);
		rotateUpRight.addChild(cannonBase);
		
		turretRotation = new Transform3D();
		turretRotation.rotY(2.0f);
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
		
		/*
		
		Transform3D translate = new Transform3D();
		translate.setTranslation(new Vector3d(0,0.015f,0));
		TransformGroup objTransGroup = new TransformGroup(translate);
		
		
		turretRotation = new Transform3D();
		turretRotation.rotY(2.0f);
		turretRotGroup = new TransformGroup(turretRotation);
		turretRotGroup.addChild(cannonTop);
		turretRotGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		objTransGroup.addChild(turretRotGroup);
	
		TransformGroup objRotGroup = new TransformGroup(rotate);
		objRotGroup.addChild(cannonBase);
		objRotGroup.addChild(objTransGroup);
	
		
		this.bGroup.addChild(objRotGroup);
			*/
		
		controller = new CannonTowerController(this);
		
		super.transGroup.addChild(controller);
		super.transGroup.addChild(bGroup);
		
	}
	


	public BranchGroup getGroup()
	{
		return this.bGroup;
	}
	
	public void setTurretAngle(float angle)
	{

		turretRotation.rotY(angle);
		turretRotGroup.setTransform(turretRotation);
	}
	
	public void setBarrelElevation(float angle)
	{
		Transform3D barrelTranslation = new Transform3D();
		barrelTranslation.setTranslation(new Vector3f(0.0f,barrelYOffset,0.0f));
		barrelElevation.rotX(angle);
		barrelTranslation.mul(barrelElevation);
		barrelRotGroup.setTransform(barrelTranslation);
	}



}
