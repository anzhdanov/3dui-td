package de.tum.in.far.threedui.project.control;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import ubitrack.SimplePose;
import ubitrack.SimplePoseReceiver;

public class PoseReceiverAlexander extends SimplePoseReceiver {

	protected TransformGroup markerTransGroup = null;
	private boolean tracked = false;
	private boolean moving = false;
	private Vector3d transVec;

	public void receivePose(SimplePose pose) {
		if (markerTransGroup == null){
			return;
		}

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
		//System.out.println("moving: " + moving);
		markerTransform.set(rotQ, transVec, 1);

		markerTransGroup.setTransform(markerTransform);

		tracked = true;

		//		if (name.equals("Button")) {
		//			System.out.println("Pos: " + pose.getTx() + ", " + pose.getTy()
		//					+ ", " + pose.getTz());
		//			System.out.println("Rot: " + pose.getRx() + ", " + pose.getRy()
		//					+ ", " + pose.getRz());
		//		}
	}

	public Vector3d getTranslation() {
		return transVec;
	}

	public void setTracked(boolean tracked) {
		this.tracked = tracked;
	}

	public boolean isTracked() {
		return tracked;
	}

	public TransformGroup getMarkerTransGroup() {
		return markerTransGroup;
	}

	public void setTransformGroup(TransformGroup markerTransGroup) {
		this.markerTransGroup = markerTransGroup;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}


}
