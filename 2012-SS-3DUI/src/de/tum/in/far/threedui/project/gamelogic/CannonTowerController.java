package de.tum.in.far.threedui.project.gamelogic;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;

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
import de.tum.in.far.threedui.project.objects.CannonTower;
import de.tum.in.far.threedui.project.objects.Enemy;
import de.tum.in.far.threedui.project.objects.Projectile;

public class CannonTowerController extends Behavior{

	private WakeupCondition condition;
	private float angle = 0.0f;
	private float elevation = 0.0f;
	private CannonTower tower;
	private boolean up = true;
	private int lastShot = 0;
	public LinkedList<Projectile> projectileList;
	
	public CannonTowerController(CannonTower t) {
		this.tower = t;
		this.projectileList = new LinkedList<Projectile>();
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
		
		
		
		
		lastShot++;
		if(lastShot >= tower.getFireRate())
		{
//			System.out.println("Fireing");
			
			Vector3f aimVector = tower.getAimVector();
			aimVector.scale(tower.getProjectileSpeed());
			
			
			Projectile p = new Projectile(tower.getBarrelTip(),aimVector, tower.getProjectileLifetime());
		projectileList.add(p);
		tower.addChild(p);
		lastShot=0;
		}
		
		
		
		
		
		}
		else
		{

			Vector3f towerAim = new Vector3f(0,-1,-0.3f);

			transTower.transform(towerAim);

			tower.aimAtPoint(new Point3f(towerAim.x,towerAim.y,towerAim.z));
		}

		
		
		//Collision
//		System.out.println("Number of Projectiles: "+ projectileList.size());
		for(Iterator<Projectile> iter_proj = projectileList.iterator(); iter_proj.hasNext(); )
		{
			Projectile p = iter_proj.next();

			p.updateProjectile();
			
			LinkedList<Enemy> enemyList = GameController.getInstance().enemyList;
			
			for(Iterator<Enemy> iter_enemy = enemyList.iterator(); iter_enemy.hasNext(); )
			{
				Enemy e = iter_enemy.next();
				Vector3f enemyPos = new Vector3f();
				Transform3D transEnemy = new Transform3D();
				
				e.getTransformGroup().getLocalToVworld(transEnemy);
				transEnemy.get(enemyPos);
				
				Vector3f particlePos = new Vector3f();
				Transform3D transParticle = new Transform3D();
				p.getGlobalCoords(transParticle);
				transParticle.get(particlePos);
				
//				System.out.println("particle: " + particlePos);
				
				Vector3f distance = new Vector3f();
				distance.sub(enemyPos,particlePos);
				
//				System.out.println(projectileList.indexOf(p) + " .. " + distance.length());
				if(distance.length()<0.025f)
				{
//					System.out.println("hit with Particle nr. " +projectileList.indexOf(p)+ " and " +e.name);
					// Remove projectile
					p.detach();
					iter_proj.remove();
					
					// Damage enemy
					if (e.damage()) {
						iter_enemy.remove();
					}

				}
				
			}
		}
		
		Iterator<Projectile> i = projectileList.iterator();
		
		while(i.hasNext())
		{
			Projectile p = i.next();
			if(!p.isAlive())
			{
				p.detach();
				i.remove();
			}
		}
		

	}

}