package de.tum.in.far.threedui.project.core;

import de.tum.in.far.threedui.general.BackgroundObject;
import de.tum.in.far.threedui.general.BinaryEnv;
import de.tum.in.far.threedui.general.ImageReceiver;
import de.tum.in.far.threedui.general.TransformableObject;
import de.tum.in.far.threedui.general.UbitrackFacade;
import de.tum.in.far.threedui.general.ViewerUbitrack;

/**
 * Superclass for Applications using the 3DUI class framework.
 * This class hides away all the stuff needed for all kinds of applications,
 * e.g. initializing Ubitrack, creating the PoseReceivers etc.
 * 
 * Subclass this to create your own application.
 * Certain objects such as viewer, and TransformableObjects for the tracked markers
 * are visible to subclasses ("protected") for easy access.
 * 
 * You have to implement the function onPoseChange that is called whenever one of
 * the markers is tracked.
 * 
 * @author Manuel
 */
public abstract class ThreeDUIApplication {

	private String title = "A 3DUI Application";
	
	static {
		BinaryEnv.init();
	}
	
	private boolean initialized = false;
		
	protected ViewerUbitrack viewer;
	private UbitrackFacade ubitrackFacade;
	
	private BackgroundObject backgroundObject;

	public TransformableObject pose0272; // pose receiver 1
	public TransformableObject pose0690; // pose receiver 2
	public  TransformableObject pose1228; // pose receiver 3
	public TransformableObject pose0B44; // pose receiver 4
	protected TransformableObject pose1C44; // pose receiver 5
	protected TransformableObject pose005A; // pose receiver 6
	protected TransformableObject pose0065; // pose receiver 7
	protected TransformableObject pose0095; // pose receiver 8
	protected TransformableObject pose003C; // pose receiver 9
	protected TransformableObject pose0056; // pose receiver 10
	protected TransformableObject pose00C2; // pose receiver 11
	protected TransformableObject pose00B0; // pose receiver 12

	private NotifyPoseReceiver poseReceiver;
	private NotifyPoseReceiver poseReceiver2;
	private NotifyPoseReceiver poseReceiver3;
	private NotifyPoseReceiver poseReceiver4;
	private NotifyPoseReceiver poseReceiver5;
	private NotifyPoseReceiver poseReceiver6;
	private NotifyPoseReceiver poseReceiver7;
	private NotifyPoseReceiver poseReceiver8;
	private NotifyPoseReceiver poseReceiver9;
	private NotifyPoseReceiver poseReceiver10;
	private NotifyPoseReceiver poseReceiver11;
	private NotifyPoseReceiver poseReceiver12;
	private ImageReceiver imageReceiver;
	
	protected ModelLoader modelLoader;
	
	public ThreeDUIApplication() {
		this(null);
	}
	
	public ThreeDUIApplication(String title) {
		if (title != null) this.title = title;
		ubitrackFacade = new UbitrackFacade();
		modelLoader = ModelLoader.getInstance();
	}

	public void init() {
		if (!initialized) {
			initializeJava3D();
			initializeUbitrack();
		}
	}

	private void initializeJava3D() {
		System.out.println("Creating Viewer - " + title);
		viewer = new ViewerUbitrack(title, ubitrackFacade);

		pose0272 = new TransformableObject();
		viewer.addObject(pose0272);
		
		pose0690 = new TransformableObject();
		viewer.addObject(pose0690);
		
		pose1228 = new TransformableObject();
		viewer.addObject(pose1228);
		
		pose0B44 = new TransformableObject();
		viewer.addObject(pose0B44);

		pose1C44 = new TransformableObject();
		viewer.addObject(pose1C44);

		pose005A = new TransformableObject();
		viewer.addObject(pose005A);

		pose0065 = new TransformableObject();
		viewer.addObject(pose0065);
		
		pose0095 = new TransformableObject();
		viewer.addObject(pose0095);
		
		pose003C = new TransformableObject();
		viewer.addObject(pose003C);
		
		pose0056 = new TransformableObject();
		viewer.addObject(pose0056);

		pose00C2 = new TransformableObject();
		viewer.addObject(pose00C2);

		pose00B0 = new TransformableObject();
		viewer.addObject(pose00B0);

		backgroundObject = new BackgroundObject();
		viewer.addObject(backgroundObject);
	}
	
	private void initializeUbitrack() {
		ubitrackFacade.initUbitrack();
		
		poseReceiver = new NotifyPoseReceiver(this, this.pose0272);
		if (!ubitrackFacade.setPoseCallback("posesink", poseReceiver)) {
			return;
		}
		poseReceiver2 = new NotifyPoseReceiver(this, this.pose0690);
		if (!ubitrackFacade.setPoseCallback("posesink2", poseReceiver2)) {
			return;
		}
		poseReceiver3 = new NotifyPoseReceiver(this, this.pose1228);
		if (!ubitrackFacade.setPoseCallback("posesink3", poseReceiver3)) {
			return;
		}
		poseReceiver4 = new NotifyPoseReceiver(this, this.pose0B44);
		if (!ubitrackFacade.setPoseCallback("posesink4", poseReceiver4)) {
			return;
		}
		poseReceiver5 = new NotifyPoseReceiver(this, this.pose1C44);
		if (!ubitrackFacade.setPoseCallback("posesink5", poseReceiver5)) {
			return;
		}
		poseReceiver6 = new NotifyPoseReceiver(this, this.pose005A);
		if (!ubitrackFacade.setPoseCallback("posesink6", poseReceiver6)) {
			return;
		}
		poseReceiver7 = new NotifyPoseReceiver(this, this.pose0065);
		if (!ubitrackFacade.setPoseCallback("posesink7", poseReceiver7)) {
			return;
		}
		poseReceiver8 = new NotifyPoseReceiver(this, this.pose0095);
		if (!ubitrackFacade.setPoseCallback("posesink8", poseReceiver8)) {
			return;
		}
		poseReceiver9 = new NotifyPoseReceiver(this, this.pose003C);
		if (!ubitrackFacade.setPoseCallback("posesink9", poseReceiver9)) {
			return;
		}
		poseReceiver10 = new NotifyPoseReceiver(this, this.pose0056);
		if (!ubitrackFacade.setPoseCallback("posesink10", poseReceiver10)) {
			return;
		}
		poseReceiver11 = new NotifyPoseReceiver(this, this.pose00C2);
		if (!ubitrackFacade.setPoseCallback("posesink11", poseReceiver11)) {
			return;
		}
		poseReceiver12 = new NotifyPoseReceiver(this, this.pose00B0);
		if (!ubitrackFacade.setPoseCallback("posesink12", poseReceiver12)) {
			return;
		}

		imageReceiver = new ImageReceiver();
		if (!ubitrackFacade.setImageCallback("imgsink", imageReceiver)) {
			return;
		}
		imageReceiver.setBackground(backgroundObject.getBackground());
		
		ubitrackFacade.startDataflow();
	}
	
	/**
	 * This function is called from the pose receivers whenever they update their respective pose.
	 * You can check with .equals(this.poseXXXX) to see which one has changed.
	 * Override to do stuff during runtime that cannot be done with Behaviors.
	 */
	public abstract void onPoseChange(TransformableObject pose);
	
}
