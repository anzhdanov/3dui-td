package de.tum.in.far.threedui.project.core;

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;

import de.tum.in.far.threedui.general.TransformableObject;
import de.tum.in.far.threedui.project.core.ModelLoader.ModelFormat;
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
		
		// register all models we might need
		this.modelLoader.registerModel("Sheep", "Sheep.wrl", ModelFormat.VRML97);
		
		createCoordsOnMarkers();
		createSheepOnMarkers();
	}

	/**
	 * Example code: Display coordinate systems on all markers
	 */
	private void createCoordsOnMarkers() {
		this.pose0272.getTransformGroup().addChild(new CoordSysObject(0.001f, 0.05f));
		this.pose0690.getTransformGroup().addChild(new CoordSysObject(0.001f, 0.05f));
		this.pose1228.getTransformGroup().addChild(new CoordSysObject(0.001f, 0.05f));
		this.pose0B44.getTransformGroup().addChild(new CoordSysObject(0.001f, 0.05f));
		this.pose1C44.getTransformGroup().addChild(new CoordSysObject(0.001f, 0.05f));
		this.pose005A.getTransformGroup().addChild(new CoordSysObject(0.001f, 0.05f));
		this.pose0065.getTransformGroup().addChild(new CoordSysObject(0.001f, 0.05f));
		this.pose0095.getTransformGroup().addChild(new CoordSysObject(0.001f, 0.05f));
		this.pose003C.getTransformGroup().addChild(new CoordSysObject(0.001f, 0.05f));
		this.pose0056.getTransformGroup().addChild(new CoordSysObject(0.001f, 0.05f));
		this.pose00C2.getTransformGroup().addChild(new CoordSysObject(0.001f, 0.05f));
		this.pose00B0.getTransformGroup().addChild(new CoordSysObject(0.001f, 0.05f));
	}
	
	private void createSheepOnMarkers() {
		TransformableObject sheep1 = this.modelLoader.getModelObject("Sheep");
		TransformableObject sheep2 = this.modelLoader.getModelObject("Sheep");
		this.pose0272.getTransformGroup().addChild(sheep1);
		this.pose1228.getTransformGroup().addChild(sheep2);
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
