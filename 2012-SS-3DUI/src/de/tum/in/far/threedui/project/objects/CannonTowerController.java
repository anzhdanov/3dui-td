package de.tum.in.far.threedui.project.objects;

import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Transform3D;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import de.tum.in.far.threedui.project.core.TowerDefense;

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
		
		
	

	
		Vector3f towerPos = new Vector3f();

		Transform3D transTower= new Transform3D();
		tower.getLocalToVworld(transTower);
		transTower.get(towerPos);
		
		

		
	
		
		Enemy chosen = null;
		float minDist = Float.MAX_VALUE;
		
		for(Enemy e : GameController.getInstance().enemyList)
		{
			Vector3f temp = new Vector3f();
			Transform3D transEnemy = new Transform3D();
			e.getLocalToVworld(transEnemy);
			transEnemy.get(temp);
			
			Vector3f dist = temp;
			dist.sub(towerPos);
			if(dist.length()<minDist) 
				{
				minDist = dist.length();
				chosen = e;
				}

		}
					
		if(chosen!=null)
		{
		Vector3f pos2 = new Vector3f();
		Transform3D transEnemy = new Transform3D();
		chosen.getLocalToVworld(transEnemy);
		transEnemy.get(pos2);
		
		tower.aimAtPoint(new Point3f(pos2.x,pos2.y,pos2.z));
		}
		else
		{

			Vector3f towerAim = new Vector3f(0,-1,-0.3f);

			transTower.transform(towerAim);

			tower.aimAtPoint(new Point3f(towerAim.x,towerAim.y,towerAim.z));
		}

		
		//Transform3D transWorld = new Transform3D();
		//TowerDefense.getAppInstance().pose0272.getTransformGroup().getTransform(transWorld);
		//Vector3f pos1 = new Vector3f();
		//transWorld.get(pos1);
		
		
		//Transform3D trans = new Transform3D();
	//	Enemy e = TowerDefense.getAppInstance().pathObject.enemyList.getFirst();
	//	trans = e.animation.getCurrentTransformation();
	//	Vector3f pos2 = new Vector3f();
	//	trans.get(pos2);
		
	//	transWorld.transform(pos2);
		
	//	pos2.add(pos1);
		
		
		
		//System.out.println(pos2);
		
		
		//
	}

}