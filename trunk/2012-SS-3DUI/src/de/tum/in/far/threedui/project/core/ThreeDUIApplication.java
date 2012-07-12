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

	protected TransformableObject markerObject1; // pose receiver 1
	protected TransformableObject markerObject2; // pose receiver 2
	protected TransformableObject markerObject3; // pose receiver 3
	protected TransformableObject markerObject4; // pose receiver 4
	protected TransformableObject markerObject5; // pose receiver 5
	protected TransformableObject markerObject6; // pose receiver 6
	protected TransformableObject markerObject7; // pose receiver 7
	protected TransformableObject markerObject8; // pose receiver 8
	protected TransformableObject markerObject9; // pose receiver 9
	protected TransformableObject markerObject10; // pose receiver 10
	protected TransformableObject markerObject11; // pose receiver 11
	protected TransformableObject markerObject12; // pose receiver 12

	protected NotifyPoseReceiver poseReceiver;
	protected NotifyPoseReceiver poseReceiver2;
	protected NotifyPoseReceiver poseReceiver3;
	protected NotifyPoseReceiver poseReceiver4;
	protected NotifyPoseReceiver poseReceiver5;
	protected NotifyPoseReceiver poseReceiver6;
	protected NotifyPoseReceiver poseReceiver7;
	protected NotifyPoseReceiver poseReceiver8;
	protected NotifyPoseReceiver poseReceiver9;
	protected NotifyPoseReceiver poseReceiver10;
	protected NotifyPoseReceiver poseReceiver11;
	protected NotifyPoseReceiver poseReceiver12;
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

		markerObject1 = new TransformableObject();
		viewer.addObject(markerObject1);
		
		markerObject2 = new TransformableObject();
		viewer.addObject(markerObject2);
		
		markerObject3 = new TransformableObject();
		viewer.addObject(markerObject3);
		
		markerObject4 = new TransformableObject();
		viewer.addObject(markerObject4);

		markerObject5 = new TransformableObject();
		viewer.addObject(markerObject5);

		markerObject6 = new TransformableObject();
		viewer.addObject(markerObject6);

		markerObject7 = new TransformableObject();
		viewer.addObject(markerObject7);
		
		markerObject8 = new TransformableObject();
		viewer.addObject(markerObject8);
		
		markerObject9 = new TransformableObject();
		viewer.addObject(markerObject9);
		
		markerObject10 = new TransformableObject();
		viewer.addObject(markerObject10);

		markerObject11 = new TransformableObject();
		viewer.addObject(markerObject11);

		markerObject12 = new TransformableObject();
		viewer.addObject(markerObject12);

		backgroundObject = new BackgroundObject();
		viewer.addObject(backgroundObject);
	}
	
	private void initializeUbitrack() {
		ubitrackFacade.initUbitrack();
		
		poseReceiver = new NotifyPoseReceiver(this, this.markerObject1);
		if (!ubitrackFacade.setPoseCallback("posesink", poseReceiver)) {
			return;
		}
		poseReceiver2 = new NotifyPoseReceiver(this, this.markerObject2);
		if (!ubitrackFacade.setPoseCallback("posesink2", poseReceiver2)) {
			return;
		}
		poseReceiver3 = new NotifyPoseReceiver(this, this.markerObject3);
		if (!ubitrackFacade.setPoseCallback("posesink3", poseReceiver3)) {
			return;
		}
		poseReceiver4 = new NotifyPoseReceiver(this, this.markerObject4);
		if (!ubitrackFacade.setPoseCallback("posesink4", poseReceiver4)) {
			return;
		}
		poseReceiver5 = new NotifyPoseReceiver(this, this.markerObject5);
		if (!ubitrackFacade.setPoseCallback("posesink5", poseReceiver5)) {
			return;
		}
		poseReceiver6 = new NotifyPoseReceiver(this, this.markerObject6);
		if (!ubitrackFacade.setPoseCallback("posesink6", poseReceiver6)) {
			return;
		}
		poseReceiver7 = new NotifyPoseReceiver(this, this.markerObject7);
		if (!ubitrackFacade.setPoseCallback("posesink7", poseReceiver7)) {
			return;
		}
		poseReceiver8 = new NotifyPoseReceiver(this, this.markerObject8);
		if (!ubitrackFacade.setPoseCallback("posesink8", poseReceiver8)) {
			return;
		}
		poseReceiver9 = new NotifyPoseReceiver(this, this.markerObject9);
		if (!ubitrackFacade.setPoseCallback("posesink9", poseReceiver9)) {
			return;
		}
		poseReceiver10 = new NotifyPoseReceiver(this, this.markerObject10);
		if (!ubitrackFacade.setPoseCallback("posesink10", poseReceiver10)) {
			return;
		}
		poseReceiver11 = new NotifyPoseReceiver(this, this.markerObject11);
		if (!ubitrackFacade.setPoseCallback("posesink11", poseReceiver11)) {
			return;
		}
		poseReceiver12 = new NotifyPoseReceiver(this, this.markerObject12);
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
