package de.tum.in.far.threedui.general;

import java.util.List;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

public class ProximityCollider {
	private List<PoseReceiver> targets;
	private TransformGroup arrowGroup;
	private ColliderSubscriberInt subscriber;
	public ProximityCollider(List<PoseReceiver> targets,
			TransformGroup arrowGroup, ColliderSubscriber subscriber) {
		super();
		this.targets = targets;
		this.arrowGroup = arrowGroup;
		this.subscriber = subscriber;
	}
	
	public boolean checkCollision(){
		for (PoseReceiver target: targets) {
			TransformGroup targetTG = target.getMarkerTransGroup();
			Transform3D targetT = new Transform3D();
			Transform3D arrowT = new Transform3D();
			targetTG.getTransform(targetT);
			arrowGroup.getTransform(arrowT);
			Vector3f targetV = new Vector3f();
			Vector3f arrowV = new Vector3f();
			targetT.get(targetV);
			arrowT.get(arrowV);
			float distance = findDistance(targetV, arrowV);
			if (distance < 0.07) {
				System.out.println("Collide!");
				subscriber.inform(targetTG);
				return true;
			}
		}
		subscriber.inform(null);
		return false;
	}

	private float findDistance(Vector3f sheepV, Vector3f arrowV) {
		Vector3f distance = new Vector3f();
		distance.sub(sheepV, arrowV);
		return distance.length();
	}		
}
