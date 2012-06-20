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

	protected TransformableObject pose0272; // pose receiver 1
	protected TransformableObject pose0960; // pose receiver 2
	protected TransformableObject pose1228; // pose receiver 3
	protected TransformableObject pose1C44; // pose receiver 4

	private NotifyPoseReceiver poseReceiver;
	private NotifyPoseReceiver poseReceiver2;
	private NotifyPoseReceiver poseReceiver3;
	private NotifyPoseReceiver poseReceiver4;
	private ImageReceiver imageReceiver;
	
	public ThreeDUIApplication() {
		this(null);
	}
	
	public ThreeDUIApplication(String title) {
		if (title != null) this.title = title;
		ubitrackFacade = new UbitrackFacade();
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
		
		pose0960 = new TransformableObject();
		viewer.addObject(pose0960);
		
		pose1228 = new TransformableObject();
		viewer.addObject(pose1228);
		
		pose1C44 = new TransformableObject();
		viewer.addObject(pose1C44);

		backgroundObject = new BackgroundObject();
		viewer.addObject(backgroundObject);
	}
	
	private void initializeUbitrack() {
		ubitrackFacade.initUbitrack();
		
		poseReceiver = new NotifyPoseReceiver(this, this.pose0272);
		if (!ubitrackFacade.setPoseCallback("posesink", poseReceiver)) {
			return;
		}
		poseReceiver2 = new NotifyPoseReceiver(this, this.pose0960);
		if (!ubitrackFacade.setPoseCallback("posesink2", poseReceiver2)) {
			return;
		}
		poseReceiver3 = new NotifyPoseReceiver(this, this.pose1228);
		if (!ubitrackFacade.setPoseCallback("posesink3", poseReceiver3)) {
			return;
		}
		poseReceiver4 = new NotifyPoseReceiver(this, this.pose1C44);
		if (!ubitrackFacade.setPoseCallback("posesink4", poseReceiver4)) {
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
