package de.tum.in.far.threedui.project.core;

import javax.media.j3d.Appearance;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Vector3f;

import de.tum.in.far.threedui.general.BlueAppearance;
import de.tum.in.far.threedui.general.RedAppearance;
import de.tum.in.far.threedui.general.TransformableObject;
import de.tum.in.far.threedui.project.control.Button;
import de.tum.in.far.threedui.project.control.Control;
import de.tum.in.far.threedui.project.control.ControlImpl;
import de.tum.in.far.threedui.project.control.CubeObject;
import de.tum.in.far.threedui.project.control.CubeObjectParameter;
import de.tum.in.far.threedui.project.control.CylinderSwitch;
import de.tum.in.far.threedui.project.control.InteractiveMarker;
import de.tum.in.far.threedui.project.control.ModelRevolver;
import de.tum.in.far.threedui.project.core.ModelLoader.ModelFormat;
import de.tum.in.far.threedui.project.objects.CannonTower;
import de.tum.in.far.threedui.project.objects.CoordSysObject;
import de.tum.in.far.threedui.project.objects.GameController;
import de.tum.in.far.threedui.project.objects.IconDisplay;
import de.tum.in.far.threedui.project.objects.PathObject;

/**
 * Example on how to extend ThreeDUIApplication to create a specific application.
 * 
 * viewer as well as poseXXXX are visible, so you can attach stuff to them.
 */
public class TowerDefense extends ThreeDUIApplication {
	private static TowerDefense app;
	
	// control objects
	private TransformableObject controllerGroup; // contains the following two
	private IconDisplay iDisplay;                // tower icons around controller
	private Button button;                       // button on controller
	
	private Control control;
	private ModelRevolver modelRevolver;
	private InteractiveMarker marker1;
	// end of control objects
	
	// game objects
	public PathObject pathObject;
	// end of game objects
	
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
		
		controllerGroup = new TransformableObject();
	}
	
	/**
	 * Override this to add application specific code. Don't forget to call super.init()
	 * at the beginning though.
	 * e.g. add some objects/behaviors to the scene graph.
	 */
	public void init() {
		super.init();
		
		this.modelLoader = ModelLoader.getInstance();
		
		// Register individual gun parts
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
		
		// Register complete gun models (miniature icons for selection)
		this.modelLoader.registerModel("Howitzer", "howitzer.wrl");
		this.modelLoader.registerModel("Precision", "precision.wrl");
		this.modelLoader.registerModel("Gun", "gun.wrl");
		this.modelLoader.registerModel("DoubleGun", "doublegun.wrl");
		this.modelLoader.registerModel("Gatling", "gatling.wrl");
		this.modelLoader.registerModel("StunAntenna", "stunantenna.wrl");

		
		Appearance app = new BlueAppearance();
		TransparencyAttributes ta = new TransparencyAttributes();
		ta.setTransparency(0.5f);
		ta.setTransparencyMode (TransparencyAttributes.BLENDED);
		app.setTransparencyAttributes(ta);
		pathObject = new PathObject(app);

		pathObject.getTransformGroup().addChild(GameController.getInstance().particleGroup);
		GameController.getInstance().pathObject = pathObject;
		
		this.markerObject1.getTransformGroup().addChild(pathObject);
				
		createCoordsOnMarkers();
		createTowersOnMarkers();
		
		// Code taken from Alexander (control stuff)
		BlueAppearance blueAppearance = new BlueAppearance();
		RedAppearance redAppearance = new RedAppearance();

		button = new CylinderSwitch(0.026f, 0.026f, blueAppearance, redAppearance);
		controllerGroup.getTransformGroup().addChild(button);
		iDisplay = new IconDisplay();
		controllerGroup.getTransformGroup().addChild(iDisplay);

		this.markerObject9.getTransformGroup().addChild(controllerGroup);

		marker1 = new InteractiveMarker();
		modelRevolver = new ModelRevolver(marker1);
		marker1.setPoseReceiver(poseReceiver10);
		this.markerObject10.getTransformGroup().addChild(marker1);

		control = new ControlImpl(poseReceiver9, button);
		control.registerRevolver(modelRevolver);
		modelRevolver.registerControl(control);
		
		
		
	}

	/**
	 * Example code: Display coordinate systems on all markers
	 */
	private void createCoordsOnMarkers() {
		
		CoordSysObject coordSys1 = new CoordSysObject(0.001f, 0.05f);
		CoordSysObject coordSys2 = new CoordSysObject(0.001f, 0.05f);
		CoordSysObject coordSys3 = new CoordSysObject(0.001f, 0.05f);
		CoordSysObject coordSys4 = new CoordSysObject(0.001f, 0.05f);
		
		this.markerObject1.getTransformGroup().addChild(coordSys1);
		this.markerObject2.getTransformGroup().addChild(coordSys2);
		this.markerObject3.getTransformGroup().addChild(coordSys3);
//		this.markerObject4.getTransformGroup().addChild(coordSys4);
		
		this.markerObject10.getTransformGroup().addChild(coordSys4);
	
	}

	private void createTowersOnMarkers() {
		
		CannonTower c = new CannonTower(CannonTower.Type.PRECISION);
		CannonTower c1 = new CannonTower(CannonTower.Type.GUN);
		CannonTower c3 = new CannonTower(CannonTower.Type.DOUBLEGUN);
		CannonTower c4 = new CannonTower(CannonTower.Type.GATLING);
		
		this.markerObject2.getTransformGroup().addChild(c);
		this.markerObject4.getTransformGroup().addChild(c1);
		this.markerObject3.getTransformGroup().addChild(c3);
		//this.pose0272.getTransformGroup().addChild(c4);
		
	}
	
	/**
	 * Example code: Print in console whenever marker 0x0272 is changed. Output its translation.
	 */
	@Override
	public void onPoseChange(TransformableObject pose) {
		
		
		
		if (pose.equals(this.markerObject1)) {
			Transform3D trans = new Transform3D();
			pose.getTransformGroup().getTransform(trans);
			
			Vector3f pos = new Vector3f();
			trans.get(pos);
			
			//System.out.println("Tracked Marker 0x0272. X: " + pos.x + ", Y: " + pos.y + ", Z: " + pos.z);
		}
		else if (pose.equals(this.markerObject2)) {
			Transform3D trans = new Transform3D();
			pose.getTransformGroup().getTransform(trans);
			
			Vector3f pos = new Vector3f();
			trans.get(pos);
			
			//System.out.println("Tracked Marker 0x0690. X: " + pos.x + ", Y: " + pos.y + ", Z: " + pos.z);
		}
		else if (pose.equals(this.markerObject3)) {
			Transform3D trans = new Transform3D();
			pose.getTransformGroup().getTransform(trans);
			
			Vector3f pos = new Vector3f();
			trans.get(pos);
			
			//System.out.println("Tracked Marker 0x1228. X: " + pos.x + ", Y: " + pos.y + ", Z: " + pos.z);
		}
		else if (pose.equals(this.markerObject4)) {
			Transform3D trans = new Transform3D();
			pose.getTransformGroup().getTransform(trans);
			
			Vector3f pos = new Vector3f();
			trans.get(pos);
			
			//System.out.println("Tracked Marker 0x0B44. X: " + pos.x + ", Y: " + pos.y + ", Z: " + pos.z);
		}
		
	}

}
