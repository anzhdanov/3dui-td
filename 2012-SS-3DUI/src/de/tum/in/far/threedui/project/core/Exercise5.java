package de.tum.in.far.threedui.project.core;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.swing.ButtonGroup;
import javax.vecmath.Point3d;

import de.tum.in.far.threedui.general.BackgroundObject;
import de.tum.in.far.threedui.general.BinaryEnv;
import de.tum.in.far.threedui.general.BlueAppearance;
import de.tum.in.far.threedui.general.ImageReceiver;
import de.tum.in.far.threedui.general.ModelObject;
import de.tum.in.far.threedui.general.PoseReceiver;
import de.tum.in.far.threedui.general.RedAppearance;
import de.tum.in.far.threedui.general.TransformableObject;
import de.tum.in.far.threedui.general.UbitrackFacade;
import de.tum.in.far.threedui.general.ViewerUbitrack;
import de.tum.in.far.threedui.project.control.Button;
import de.tum.in.far.threedui.project.control.Control;
import de.tum.in.far.threedui.project.control.ControlImpl;
import de.tum.in.far.threedui.project.control.CubeObject;
import de.tum.in.far.threedui.project.control.CubeObjectParameter;
import de.tum.in.far.threedui.project.control.CylinderSwitch;
import de.tum.in.far.threedui.project.control.InteractiveMarker;
import de.tum.in.far.threedui.project.control.ModelRevolver;
import de.tum.in.far.threedui.project.control.PoseReceiverAlexander;
import de.tum.in.far.threedui.project.core.ModelLoader.ModelFormat;
import de.tum.in.far.threedui.project.objects.IconDisplay;

/*
import de.tum.in.far.threedui.control.general.BackgroundObject;
import de.tum.in.far.threedui.control.general.BinaryEnv;
import de.tum.in.far.threedui.control.general.BlueAppearance;
import de.tum.in.far.threedui.control.general.Button;
import de.tum.in.far.threedui.control.general.Control;
import de.tum.in.far.threedui.control.general.ControlImpl;
import de.tum.in.far.threedui.control.general.CubeObject;
import de.tum.in.far.threedui.control.general.CubeObjectParameter;
import de.tum.in.far.threedui.control.general.CylinderSwitch;
import de.tum.in.far.threedui.control.general.ImageReceiver;
import de.tum.in.far.threedui.control.general.InteractiveMarker;
import de.tum.in.far.threedui.control.general.ModelObject;
import de.tum.in.far.threedui.control.general.ModelRevolver;
import de.tum.in.far.threedui.control.general.PoseReceiver;
import de.tum.in.far.threedui.control.general.RedAppearance;
import de.tum.in.far.threedui.control.general.UbitrackFacade;
import de.tum.in.far.threedui.control.general.ViewerUbitrack;

*/
public class Exercise5 {

	public static final String EXERCISE = "Exercise 4";
	protected BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0,
			0.0), 0.1);

	
	private ModelLoader modelLoader;
	
	static {
		BinaryEnv.init();
	}

	private ViewerUbitrack viewer;
	private UbitrackFacade ubitrackFacade;

	private CubeObject cubeObject;
	private ModelObject sheepObject;
	//private ModelObject marker1;
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
		Exercise5 exercise3 = new Exercise5();

		exercise3.loadModels();
	
		exercise3.initializeJava3D();
		exercise3.initMarkers();
		exercise3.initializeUbitrack();
		
	}

	private void initializeUbitrack() {
		PoseReceiverAlexander poseReceiver;
		PoseReceiverAlexander poseReceiver2;
		PoseReceiverAlexander poseReceiver3;
		PoseReceiverAlexander poseReceiver4;
		ImageReceiver imageReceiver;

		ubitrackFacade.initUbitrack();

		poseReceiver = new PoseReceiverAlexander();
		if (!ubitrackFacade.setPoseCallback("posesink", poseReceiver)) {
			return;
		}
		poseReceiver2 = new PoseReceiverAlexander();
		if (!ubitrackFacade.setPoseCallback("posesink2", poseReceiver2)) {
			return;
		}
		poseReceiver3 = new PoseReceiverAlexander();
		if (!ubitrackFacade.setPoseCallback("posesink3", poseReceiver3)) {
			return;
		}
		poseReceiver4 = new PoseReceiverAlexander();
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
		poseReceiver.setTransformGroup(buttonGroup.getTransformGroup());		
		poseReceiver2.setTransformGroup(marker1.getTransformGroup());
		marker1.setPoseReceiver(poseReceiver2);
		poseReceiver4.setTransformGroup(marker2.getTransformGroup());
		marker2.setPoseReceiver(poseReceiver4);
		
		//MovementWrapperImpl mWrapper = new MovementWrapperImpl(marker1, sheepObject);
		control = new ControlImpl(poseReceiver, button);
		control.registerRevolver(modelRevolver);
		modelRevolver.registerControl(control);
			
		control.registerRevolver(modelRevolver2);
		modelRevolver2.registerControl(control);
			
		
//		button = new ArrowSwitch(sheepObject);
//		viewer.addObject(button);	
//		poseReceiver3.setTransformGroup(button.getTransformGroup());
		
		ubitrackFacade.startDataflow();		
		
//		
//		List<PoseReceiver> list = new ArrayList<PoseReceiver>();
//		list.add(poseReceiver2);
//		list.add(poseReceiver4);
//		
//		Operator adapter = new Operator(poseReceiver3, list, button);
//		adapter.start();
	}

	private void loadModels() {


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
		
		this.modelLoader.registerModel("Howitzer", "howitzer.wrl");
		this.modelLoader.registerModel("Precision", "precision.wrl");
		this.modelLoader.registerModel("Gun", "gun.wrl");
		this.modelLoader.registerModel("DoubleGun", "doublegun.wrl");
		this.modelLoader.registerModel("Gatling", "gatling.wrl");
		this.modelLoader.registerModel("StunAntenna", "stunantenna.wrl");

		
	}
	
	private void initMarkers()
	{
		modelRevolver = new ModelRevolver(viewer);
		marker1 = modelRevolver.getViewingWindow();

		modelRevolver2 = new ModelRevolver(viewer);
		marker2 = modelRevolver2.getViewingWindow();
	}

	private void initializeJava3D() {
		System.out.println("Creating Viewer - " + EXERCISE);
		viewer = new ViewerUbitrack(EXERCISE, ubitrackFacade);

		BlueAppearance blueAppearance = new BlueAppearance();

		cubeObject = new CubeObject(new CubeObjectParameter(blueAppearance, 0.023f, 0.023f, 0.023f));
		viewer.addObject(cubeObject);		
		
		RedAppearance redAppearance = new RedAppearance();
		
		
		
		button = new CylinderSwitch(0.026f, 0.026f, blueAppearance, redAppearance);
		buttonGroup.getTransformGroup().addChild(button);
		
		IconDisplay iDisplay = new IconDisplay();
		buttonGroup.getTransformGroup().addChild(iDisplay);
		
		viewer.addObject(buttonGroup);

		System.out.println("Done");
	}	

}
