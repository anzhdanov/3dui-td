package de.tum.in.far.threedui.project.core;

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import de.tum.in.far.threedui.project.objects.PathObject;
import de.tum.in.far.threedui.general.BlueAppearance;
import de.tum.in.far.threedui.general.TransformableObject;
import de.tum.in.far.threedui.project.objects.CoordSysObject;
import java.util.*;

/**
 * Example on how to extend ThreeDUIApplication to create a specific application.
 * 
 * viewer as well as poseXXXX are visible, so you can attach stuff to them.
 */
public class TowerDefense extends ThreeDUIApplication {

	public static void main(String[] args) {
		TowerDefense app = new TowerDefense();
		app.init();
	}
	
	public TowerDefense() {
		super("Example Application");
	}
	
	/**
	 * Override this to add application specific code. Don't forget to call super.init()
	 * at the beginning though.
	 * e.g. add some objects/behaviors to the scene graph.
	 */
	public void init() {
		super.init();
		

		PathObject pathObject = new PathObject(new BlueAppearance());
		this.pose0272.getTransformGroup().addChild(pathObject);
		
		
		createCoordsOnMarkers();
		
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
			
			System.out.println("Tracked Marker 0x0272. X: " + pos.x + ", Y: " + pos.y + ", Z: " + pos.z);
		}
		else if (pose.equals(this.pose0690)) {
			Transform3D trans = new Transform3D();
			pose.getTransformGroup().getTransform(trans);
			
			Vector3f pos = new Vector3f();
			trans.get(pos);
			
			System.out.println("Tracked Marker 0x0690. X: " + pos.x + ", Y: " + pos.y + ", Z: " + pos.z);
		}
		else if (pose.equals(this.pose1228)) {
			Transform3D trans = new Transform3D();
			pose.getTransformGroup().getTransform(trans);
			
			Vector3f pos = new Vector3f();
			trans.get(pos);
			
			System.out.println("Tracked Marker 0x1228. X: " + pos.x + ", Y: " + pos.y + ", Z: " + pos.z);
		}
		else if (pose.equals(this.pose0B44)) {
			Transform3D trans = new Transform3D();
			pose.getTransformGroup().getTransform(trans);
			
			Vector3f pos = new Vector3f();
			trans.get(pos);
			
			System.out.println("Tracked Marker 0x0B44. X: " + pos.x + ", Y: " + pos.y + ", Z: " + pos.z);
		}
		
	}

}
