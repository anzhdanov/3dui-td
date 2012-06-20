package de.tum.in.far.threedui.project.core;

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;

import de.tum.in.far.threedui.general.TransformableObject;
import de.tum.in.far.threedui.project.objects.CoordSysObject;

/**
 * Example on how to extend ThreeDUIApplication to create a specific application.
 * 
 * viewer as well as poseXXXX are visible, so you can attach stuff to them.
 */
public class ExampleApplication extends ThreeDUIApplication {

	public static void main(String[] args) {
		ExampleApplication app = new ExampleApplication();
		app.init();
	}
	
	public ExampleApplication() {
		super("Example Application");
	}
	
	/**
	 * Override this to add application specific code. Don't forget to call super.init()
	 * at the beginning though.
	 * e.g. add some objects/behaviors to the scene graph.
	 */
	public void init() {
		super.init();
		
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
		this.pose0960.getTransformGroup().addChild(coordSys2);
		this.pose1228.getTransformGroup().addChild(coordSys3);
		this.pose1C44.getTransformGroup().addChild(coordSys4);
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
		
	}

}
