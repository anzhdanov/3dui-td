package de.tum.in.far.threedui.project.objects;

import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.media.j3d.WakeupOr;

public class ProjectileController  extends Behavior{
	
	private static ProjectileController instance = null;
	private WakeupCondition condition;
	
	private ProjectileController()
	{

	}
	
	public static ProjectileController getInstance()
	{
	
		
		if(instance == null)
		{
			instance = new ProjectileController();
		}
		return instance;
	}

	@Override
	public void initialize() {
		BoundingSphere bounds = new BoundingSphere();
		bounds.setRadius(40.0);
		setSchedulingBounds(bounds);
		condition = new WakeupOr(new WakeupCriterion[]{new WakeupOnElapsedTime(20)});
		wakeupOn(condition);
		
	}

	@Override
	public void processStimulus(Enumeration arg0) {
		
	/*	System.out.println("Number of Particles "+GameController.getInstance().projectileList.size());
		System.out.println("updating");
		for(Projectile p: GameController.getInstance().projectileList)
		{
			if(p!=null)
			{
				
				p.updateProjectile();
			}
		}
		*/
		wakeupOn(condition);
	}

}
