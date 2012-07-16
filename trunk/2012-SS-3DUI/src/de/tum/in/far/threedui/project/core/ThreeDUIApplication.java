package de.tum.in.far.threedui.project.core;

import java.util.LinkedList;

import de.tum.in.far.threedui.general.BackgroundObject;
import de.tum.in.far.threedui.general.BinaryEnv;
import de.tum.in.far.threedui.general.ImageReceiver;
import de.tum.in.far.threedui.general.PoseReceiver;
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

	protected TransformableObject[] markerObjects;
	protected NotifyPoseReceiver[] poseReceivers;

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

		markerObjects = new TransformableObject[12];

		for(int i = 0; i < 12; i++) {
			TransformableObject markerObject = new TransformableObject();
			markerObjects[i] = markerObject;
			viewer.addObject(markerObject);
		}

		backgroundObject = new BackgroundObject();
		viewer.addObject(backgroundObject);
	}
	
	private void initializeUbitrack() {
		ubitrackFacade.initUbitrack();
		
		String[] suffix = {"", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		
		poseReceivers = new NotifyPoseReceiver[12];
		
		for (int i = 0; i < 12; i++) {
			NotifyPoseReceiver poseReceiver = new NotifyPoseReceiver(this, this.markerObjects[i]);
			poseReceivers[i] = poseReceiver;
			if (!ubitrackFacade.setPoseCallback("posesink" + suffix[i], poseReceiver)) {
				return;
			}
		}
/*		
		poseReceiver1 = new NotifyPoseReceiver(this, this.markerObject1);
		if (!ubitrackFacade.setPoseCallback("posesink", poseReceiver1)) {
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
*/
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
