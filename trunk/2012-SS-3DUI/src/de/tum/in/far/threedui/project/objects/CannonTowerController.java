package de.tum.in.far.threedui.project.objects;

import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.media.j3d.WakeupOr;

public class CannonTowerController extends Behavior{

	private WakeupCondition condition;
	private float angle = 0.0f;
	private float elevation = 0.0f;
	private CannonTower tower;
	private boolean up = true;
	
	public CannonTowerController(CannonTower t) {
		this.tower = t;
	}
	@Override
	public void initialize( ) {
		

		BoundingSphere bounds = new BoundingSphere();
		bounds.setRadius(40.0);
		setSchedulingBounds(bounds);
		condition = new WakeupOr(new WakeupCriterion[]{new WakeupOnElapsedTime(20)});
		wakeupOn(condition);
		
	}


	@Override
	public void processStimulus(Enumeration arg0) {
		
		angle += 0.05f;
		if(up)
		{
			elevation += 0.02f;
			if(elevation > 0.3) up = false;
		}
		else
		{
			elevation -= 0.02f;
			if(elevation < -0.5) up = true;
		}
		
		tower.setTurretAngle(angle);
		tower.setBarrelElevation(elevation);
		wakeupOn(condition);
	
	}

}