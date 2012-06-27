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
	
	public static enum Type
	{
		GUN,
		DOUBLEGUN,
		PRECISION,
		GATLING,
		HOWITZER
	}
	
	public CannonTower(Type type)
	{
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
		}
		else if(type == Type.DOUBLEGUN)
		{
			cannonTop = this.loader.getModelObject("DoubleGun-Top");
			cannonBase = this.loader.getModelObject("DoubleGun-Base");
			cannonBarrel = this.loader.getModelObject("DoubleGun-Barrel");
			barrelYOffset = 0.016f;
		}
		else if(type == Type.PRECISION)
		{
			cannonTop = this.loader.getModelObject("Precision-Top");
			cannonBase = this.loader.getModelObject("Precision-Base");
			cannonBarrel = this.loader.getModelObject("Precision-Barrel");
			barrelYOffset = 0.02f;
		}
		else if(type == Type.GATLING)
		{
			cannonTop = this.loader.getModelObject("Gatling-Top");
			cannonBase = this.loader.getModelObject("Gatling-Base");
			cannonBarrel = this.loader.getModelObject("Gatling-Barrel");
			barrelYOffset = 0.013f;
		}
		else if(type == Type.HOWITZER)
		{
			cannonTop = this.loader.getModelObject("Howitzer-Top");
			cannonBase = this.loader.getModelObject("Howitzer-Base");
			cannonBarrel = this.loader.getModelObject("Howitzer-Barrel");
			barrelYOffset = 0.013f;
		}
		
		
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
