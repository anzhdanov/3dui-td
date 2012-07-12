package de.tum.in.far.threedui.project.core;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import ubitrack.SimplePose;
import ubitrack.SimplePoseReceiver;
import de.tum.in.far.threedui.general.TransformableObject;

public class NotifyPoseReceiver extends SimplePoseReceiver {

	protected ThreeDUIApplication app;
	protected TransformableObject poseObject;
	private boolean tracked = false;
	private boolean moving = false;
	private Vector3d transVec;
	
	public NotifyPoseReceiver(ThreeDUIApplication app, TransformableObject poseObject) {
		this.app = app;
		this.poseObject = poseObject;
	}
	
	public void receivePose(SimplePose pose) {

		double[] trans = new double[3];
		double[] rot = new double[4];
		trans[0] = pose.getTx();
		trans[1] = pose.getTy();
		trans[2] = pose.getTz();
		rot[0] = pose.getRx();
		rot[1] = pose.getRy();
		rot[2] = pose.getRz();
		rot[3] = pose.getRw();

		transVec = new Vector3d(pose.getTx(), pose.getTy(), pose.getTz());
		Quat4d rotQ = new Quat4d(pose.getRx(), pose.getRy(), pose.getRz(), pose.getRw());
		Transform3D markerTransform = new Transform3D();
		markerTransform.set(rotQ, transVec, 1);
		
		// Set the pose object's transform
		if (this.poseObject != null) {
			this.poseObject.getTransformGroup().setTransform(markerTransform);
		}
		
		// Notify the application that the pose has changed
		if (this.app != null) {
			this.app.onPoseChange(this.poseObject);
		}
		
		this.tracked = true;
	}

	public Vector3d getTranslation() {
		return this.transVec;
	}

	public void setTracked(boolean tracked) {
		this.tracked = tracked;
	}

	public boolean isTracked() {
		return this.tracked;
	}

	public TransformGroup getMarkerTransGroup() {
		return this.poseObject.getTransformGroup();
	}

	public boolean isMoving() {
		return this.moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

}
