package de.tum.in.far.threedui.project.core;

import javax.media.j3d.BoundingSphere;
import javax.vecmath.Point3d;

import de.tum.in.far.threedui.general.BackgroundObject;
import de.tum.in.far.threedui.general.BinaryEnv;
import de.tum.in.far.threedui.general.BlueAppearance;
import de.tum.in.far.threedui.general.ImageReceiver;
import de.tum.in.far.threedui.general.RedAppearance;
import de.tum.in.far.threedui.general.TransformableObject;
import de.tum.in.far.threedui.general.UbitrackFacade;
import de.tum.in.far.threedui.general.ViewerUbitrack;
import de.tum.in.far.threedui.project.control.Button;
import de.tum.in.far.threedui.project.control.Control;
import de.tum.in.far.threedui.project.control.ControlImpl;
import de.tum.in.far.threedui.project.control.CylinderSwitch;
import de.tum.in.far.threedui.project.control.InteractiveMarker;
import de.tum.in.far.threedui.project.control.ModelRevolver;
import de.tum.in.far.threedui.project.objects.IconDisplay;

public class Exercise5 {

	public static final String EXERCISE = "Controller Test";
	protected BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0,
			0.0), 0.1);

	
	private ModelLoader modelLoader;
	
	static {
		BinaryEnv.init();
	}

	private ViewerUbitrack viewer;
	private UbitrackFacade ubitrackFacade;

	private InteractiveMarker marker1;
	private InteractiveMarker marker2;
	private Button button;
	private TransformableObject buttonGroup; 
	private Control control;
	private ModelRevolver modelRevolver;
	private ModelRevolver modelRevolver2;

	public Exercise5() {
		ubitrackFacade = new UbitrackFacade();
		modelLoader = ModelLoader.getInstance();
		buttonGroup = new TransformableObject();
	}

	public static void main(String[] args) {
		Exercise5 exercise5 = new Exercise5();

		exercise5.loadModels();
	
		exercise5.initializeJava3D();
		exercise5.initMarkers();
		exercise5.initializeUbitrack();
		
	}

	private void initializeUbitrack() {
		NotifyPoseReceiver poseReceiver;
		NotifyPoseReceiver poseReceiver2;
		NotifyPoseReceiver poseReceiver3;
		NotifyPoseReceiver poseReceiver4;
		ImageReceiver imageReceiver;

		ubitrackFacade.initUbitrack();

		poseReceiver = new NotifyPoseReceiver(null, buttonGroup);
		if (!ubitrackFacade.setPoseCallback("posesink", poseReceiver)) {
			return;
		}
		poseReceiver2 = new NotifyPoseReceiver(null, marker1);
		if (!ubitrackFacade.setPoseCallback("posesink2", poseReceiver2)) {
			return;
		}
		poseReceiver3 = new NotifyPoseReceiver(null, null);
		if (!ubitrackFacade.setPoseCallback("posesink3", poseReceiver3)) {
			return;
		}
		poseReceiver4 = new NotifyPoseReceiver(null, marker2);
		if (!ubitrackFacade.setPoseCallback("posesink4", poseReceiver4)) {
			return;
		}

		imageReceiver = new ImageReceiver();
		if (!ubitrackFacade.setImageCallback("imgsink", imageReceiver)) {
			return;
		}

		BackgroundObject backgroundObject = new BackgroundObject();
		viewer.addObject(backgroundObject);
		imageReceiver.setBackground(backgroundObject.getBackground());
		marker1.setPoseReceiver(poseReceiver2);
		marker2.setPoseReceiver(poseReceiver4);
		
		control = new ControlImpl(poseReceiver, button);
		control.registerRevolver(modelRevolver);
		modelRevolver.registerControl(control);
			
		control.registerRevolver(modelRevolver2);
		modelRevolver2.registerControl(control);
			
		ubitrackFacade.startDataflow();		
	}

	private void loadModels() {


		this.modelLoader.registerModel("Gun-Top", "gun-top.wrl");
		this.modelLoader.registerModel("Gun-Base", "gun-base.wrl");
		this.modelLoader.registerModel("Gun-Barrel", "gun-barrel.wrl");

		this.modelLoader.registerModel("DoubleGun-Top", "doublegun-top.wrl");
		this.modelLoader.registerModel("DoubleGun-Base", "doublegun-base.wrl");
		this.modelLoader.registerModel("DoubleGun-Barrel", "doublegun-barrel.wrl");
	
		this.modelLoader.registerModel("Precision-Top", "precision-top.wrl");
		this.modelLoader.registerModel("Precision-Base", "precision-base.wrl");
		this.modelLoader.registerModel("Precision-Barrel", "precision-barrel.wrl");
	
		this.modelLoader.registerModel("Gatling-Top", "gatling-top.wrl");
		this.modelLoader.registerModel("Gatling-Base", "gatling-base.wrl");
		this.modelLoader.registerModel("Gatling-Barrel", "gatling-barrel.wrl");
		
		this.modelLoader.registerModel("Howitzer-Top", "howitzer-top.wrl");
		this.modelLoader.registerModel("Howitzer-Base", "howitzer-base.wrl");
		this.modelLoader.registerModel("Howitzer-Barrel", "howitzer-barrel.wrl");
		
		// TODO
//		this.modelLoader.registerModel("StunAntenna-Top", "stunantenna-top.wrl");
//		this.modelLoader.registerModel("StunAntenna-Base", "stunantenna-base.wrl");
//		this.modelLoader.registerModel("StunAntenna-Barrel", "stunantenna-barrel.wrl");
		
		this.modelLoader.registerModel("Howitzer", "howitzer.wrl");
		this.modelLoader.registerModel("Precision", "precision.wrl");
		this.modelLoader.registerModel("Gun", "gun.wrl");
		this.modelLoader.registerModel("DoubleGun", "doublegun.wrl");
		this.modelLoader.registerModel("Gatling", "gatling.wrl");
		this.modelLoader.registerModel("StunAntenna", "stunantenna.wrl");

		
	}
	
	private void initMarkers()
	{
		marker1 = new InteractiveMarker();
		modelRevolver = new ModelRevolver(marker1);
		viewer.addObject(marker1);

		marker2 = new InteractiveMarker();
		modelRevolver2 = new ModelRevolver(marker2);
		viewer.addObject(marker2);
	}

	private void initializeJava3D() {
		System.out.println("Creating Viewer - " + EXERCISE);
		viewer = new ViewerUbitrack(EXERCISE, ubitrackFacade);

		BlueAppearance blueAppearance = new BlueAppearance();
		RedAppearance redAppearance = new RedAppearance();
		
		
		
		button = new CylinderSwitch(0.026f, 0.026f, blueAppearance, redAppearance);
		buttonGroup.getTransformGroup().addChild(button);
		
		IconDisplay iDisplay = new IconDisplay();
		buttonGroup.getTransformGroup().addChild(iDisplay);
		
		viewer.addObject(buttonGroup);

		System.out.println("Done");
	}

}
