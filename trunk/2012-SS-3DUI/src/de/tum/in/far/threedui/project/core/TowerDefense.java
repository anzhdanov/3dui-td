package de.tum.in.far.threedui.project.core;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import org.jdesktop.j3d.loaders.vrml97.VrmlLoader;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import de.tum.in.far.threedui.project.core.ModelLoader.ModelFormat;
import de.tum.in.far.threedui.project.objects.CannonTower;
import de.tum.in.far.threedui.project.objects.Enemy;
import de.tum.in.far.threedui.project.objects.PathObject;
import de.tum.in.far.threedui.general.BlueAppearance;
import de.tum.in.far.threedui.general.ModelObject;
import de.tum.in.far.threedui.general.TransformableObject;
import de.tum.in.far.threedui.project.objects.CoordSysObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Example on how to extend ThreeDUIApplication to create a specific application.
 * 
 * viewer as well as poseXXXX are visible, so you can attach stuff to them.
 */
public class TowerDefense extends ThreeDUIApplication {
	private static TowerDefense app;
	
	public PathObject pathObject;
	
	public static void main(String[] args) {
		app = new TowerDefense();
		app.init();
	}
	
	/**
	 * Get the current Instance of the app (good for testing purposes9
	 * @return
	 */
	public static TowerDefense getAppInstance()
	{
		return app;
	}
	
	public TowerDefense() {
		super("3D UI Tower Defense");
	}
	
	/**
	 * Override this to add application specific code. Don't forget to call super.init()
	 * at the beginning though.
	 * e.g. add some objects/behaviors to the scene graph.
	 */
	public void init() {
		super.init();
		
	this.modelLoader = ModelLoader.getInstance();
		

			this.modelLoader.registerModel("Gun-Top", "gun-top.wrl", ModelFormat.VRML97);
			this.modelLoader.registerModel("Gun-Base", "gun-base.wrl", ModelFormat.VRML97);
			this.modelLoader.registerModel("Gun-Barrel", "gun-barrel.wrl", ModelFormat.VRML97);
	
			this.modelLoader.registerModel("DoubleGun-Top", "doublegun-top.wrl", ModelFormat.VRML97);
			this.modelLoader.registerModel("DoubleGun-Base", "doublegun-base.wrl", ModelFormat.VRML97);
			this.modelLoader.registerModel("DoubleGun-Barrel", "doublegun-barrel.wrl", ModelFormat.VRML97);
		
			this.modelLoader.registerModel("Precision-Top", "precision-top.wrl", ModelFormat.VRML97);
			this.modelLoader.registerModel("Precision-Base", "precision-base.wrl", ModelFormat.VRML97);
			this.modelLoader.registerModel("Precision-Barrel", "precision-barrel.wrl", ModelFormat.VRML97);
		
			this.modelLoader.registerModel("Gatling-Top", "gatling-top.wrl", ModelFormat.VRML97);
			this.modelLoader.registerModel("Gatling-Base", "gatling-base.wrl", ModelFormat.VRML97);
			this.modelLoader.registerModel("Gatling-Barrel", "gatling-barrel.wrl", ModelFormat.VRML97);
			
			this.modelLoader.registerModel("Howitzer-Top", "howitzer-top.wrl", ModelFormat.VRML97);
			this.modelLoader.registerModel("Howitzer-Base", "howitzer-base.wrl", ModelFormat.VRML97);
			this.modelLoader.registerModel("Howitzer-Barrel", "howitzer-barrel.wrl", ModelFormat.VRML97);
			
			
			
			
			
		Appearance app = new BlueAppearance();
		TransparencyAttributes ta = new TransparencyAttributes();
		ta.setTransparency(0.5f);
		ta.setTransparencyMode (ta.BLENDED);
		app.setTransparencyAttributes(ta);		
		pathObject = new PathObject(app);
		
		this.pose0272.getTransformGroup().addChild(pathObject);
		
		
		
		createCoordsOnMarkers();
		createTowersOnMarkers();
		
	}

	/**
	 * Example code: Display coordinate systems on all markers
	 */
	private void createCoordsOnMarkers() {
		
		CoordSysObject coordSys1 = new CoordSysObject(0.001f, 0.05f);
		CoordSysObject coordSys2 = new CoordSysObject(0.001f, 0.05f);
		CoordSysObject coordSys3 = new CoordSysObject(0.001f, 0.05f);
		CoordSysObject coordSys4 = new CoordSysObject(0.001f, 0.05f);
		
		this.pose0272.getTransformGroup().addChild(coordSys1);
		this.pose0690.getTransformGroup().addChild(coordSys2);
		this.pose1228.getTransformGroup().addChild(coordSys3);
		this.pose0B44.getTransformGroup().addChild(coordSys4);
		
	
	}

	private void createTowersOnMarkers() {
		
		CannonTower c = new CannonTower(CannonTower.Type.PRECISION);
		CannonTower c1 = new CannonTower(CannonTower.Type.GUN);
		CannonTower c3 = new CannonTower(CannonTower.Type.DOUBLEGUN);
		CannonTower c4 = new CannonTower(CannonTower.Type.GATLING);
		
		this.pose0690.getTransformGroup().addChild(c);
		this.pose0B44.getTransformGroup().addChild(c1);
		this.pose1228.getTransformGroup().addChild(c3);
		//this.pose0272.getTransformGroup().addChild(c4);
		
	}
	
	/**
	 * Example code: Print in console whenever marker 0x0272 is changed. Output its translation.
	 */
	@Override
	public void onPoseChange(TransformableObject pose) {
		
		
		
		if (pose.equals(this.pose0272)) {
			Transform3D trans = new Transform3D();
			pose.getTransformGroup().getTransform(trans);
			
			Vector3f pos = new Vector3f();
			trans.get(pos);
			
			//System.out.println("Tracked Marker 0x0272. X: " + pos.x + ", Y: " + pos.y + ", Z: " + pos.z);
		}
		else if (pose.equals(this.pose0690)) {
			Transform3D trans = new Transform3D();
			pose.getTransformGroup().getTransform(trans);
			
			Vector3f pos = new Vector3f();
			trans.get(pos);
			
			//System.out.println("Tracked Marker 0x0690. X: " + pos.x + ", Y: " + pos.y + ", Z: " + pos.z);
		}
		else if (pose.equals(this.pose1228)) {
			Transform3D trans = new Transform3D();
			pose.getTransformGroup().getTransform(trans);
			
			Vector3f pos = new Vector3f();
			trans.get(pos);
			
			//System.out.println("Tracked Marker 0x1228. X: " + pos.x + ", Y: " + pos.y + ", Z: " + pos.z);
		}
		else if (pose.equals(this.pose0B44)) {
			Transform3D trans = new Transform3D();
			pose.getTransformGroup().getTransform(trans);
			
			Vector3f pos = new Vector3f();
			trans.get(pos);
			
			//System.out.println("Tracked Marker 0x0B44. X: " + pos.x + ", Y: " + pos.y + ", Z: " + pos.z);
		}
		
	}

}
