package de.tum.in.far.threedui.control;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

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

public class Exercise5 {

	public static final String EXERCISE = "Exercise 4";
	protected BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0,
			0.0), 0.1);


	static {
		BinaryEnv.init();
	}

	private ViewerUbitrack viewer;
	private UbitrackFacade ubitrackFacade;

	private CubeObject cubeObject;
	private ModelObject sheepObject;
	//private ModelObject marker1;
	private InteractiveMarker marker1;
	private ModelObject marker2;
	private Button button;
	private Control control;
	private ModelRevolver modelRevolver;

	public Exercise5() {
		ubitrackFacade = new UbitrackFacade();
	}

	public static void main(String[] args) {
		Exercise5 exercise3 = new Exercise5();
		exercise3.initializeJava3D();
		exercise3.loadModels();
		exercise3.initializeUbitrack();
	}

	private void initializeUbitrack() {
		PoseReceiver poseReceiver;
		PoseReceiver poseReceiver2;
		PoseReceiver poseReceiver3;
		PoseReceiver poseReceiver4;
		ImageReceiver imageReceiver;

		ubitrackFacade.initUbitrack();

		poseReceiver = new PoseReceiver();
		if (!ubitrackFacade.setPoseCallback("posesink", poseReceiver)) {
			return;
		}
		poseReceiver2 = new PoseReceiver();
		if (!ubitrackFacade.setPoseCallback("posesink2", poseReceiver2)) {
			return;
		}
		poseReceiver3 = new PoseReceiver();
		if (!ubitrackFacade.setPoseCallback("posesink3", poseReceiver3)) {
			return;
		}
		poseReceiver4 = new PoseReceiver();
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
		poseReceiver.setTransformGroup(button.getTransformGroup());		
		poseReceiver2.setTransformGroup(marker1.getTransformGroup());
		marker1.setPoseReceiver(poseReceiver2);
		poseReceiver4.setTransformGroup(marker2.getTransformGroup());
		
		//MovementWrapperImpl mWrapper = new MovementWrapperImpl(marker1, sheepObject);
		control = new ControlImpl(poseReceiver, button);
		control.registerRevolver(modelRevolver);
		modelRevolver.registerControl(control);
			
		
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
//		VrmlLoader loader = new VrmlLoader();
//		Scene myScene = null;
//		try {
//			myScene = loader.load("models" + File.separator + "gun.wrl");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IncorrectFormatException e) {
//			e.printStackTrace();
//		} catch (ParsingErrorException e) {
//			e.printStackTrace();
//		}
//		//for the first marker
//		BranchGroup objRoot = new BranchGroup();
//		objRoot.setCapability(BranchGroup.ALLOW_DETACH);
//		Transform3D rotate = new Transform3D();
//		Transform3D translate = new Transform3D();		
//		translate.setTranslation(new Vector3d(0, 0.025, 0));
//		rotate.rotX(Math.PI/2);
//		rotate.mul(translate);
//		TransformGroup objRotGroup = new TransformGroup(rotate);
//		objRotGroup.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
//		objRotGroup.addChild(myScene.getSceneGroup());
//		objRoot.addChild(objRotGroup);
//		sheepObject = new ModelObject(objRoot);
//		marker1 = new ModelObject(sheepObject);				
//		viewer.addObject(marker1);
		modelRevolver = new ModelRevolver(viewer);
		modelRevolver.loadModels();
		
		marker1 = modelRevolver.getViewingWindow();

		BranchGroup m2BG = new BranchGroup();
		TransformGroup m2TG = new TransformGroup();
		m2BG.addChild(m2TG);
		marker2 = new ModelObject(m2BG);
		viewer.addObject(marker2);
	}

	private void initializeJava3D() {
		System.out.println("Creating Viewer - " + EXERCISE);
		viewer = new ViewerUbitrack(EXERCISE, ubitrackFacade);

		BlueAppearance blueAppearance = new BlueAppearance();

		cubeObject = new CubeObject(new CubeObjectParameter(blueAppearance, 0.023f, 0.023f, 0.023f));
		viewer.addObject(cubeObject);		
		
		RedAppearance redAppearance = new RedAppearance();
		
		button = new CylinderSwitch(0.026f, 0.026f, blueAppearance, redAppearance);
		viewer.addObject(button);

		System.out.println("Done");
	}	

}
