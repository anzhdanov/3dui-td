package de.tum.in.far.threedui.project.gamelogic;

import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Color3f;

import de.tum.in.far.threedui.project.EnemyAppearance;
import de.tum.in.far.threedui.project.objects.Enemy;

public class EnemySpawnController  extends Behavior{
	
	private static EnemySpawnController instance = null;
	private WakeupCondition condition;
	private int lastSpawn = 2000;
	
	
	private EnemySpawnController()
	{

	}
	
	public static EnemySpawnController getInstance()
	{
		
	
		
		if(instance == null)
		{
			instance = new EnemySpawnController();
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
		lastSpawn -= 20;
		if(lastSpawn <0) {
			if(GameController.getInstance().enemyList.size()<3)
			{
				Enemy enemy = new Enemy(new EnemyAppearance(new Color3f(0.3f, 0.8f, 0.3f)), 15000, 10);
				GameController.getInstance().enemyList.add(enemy);
				enemy.attachToPath(GameController.getInstance().pathObject);
			}
		
			lastSpawn = 2000;
		}
		
		wakeupOn(condition);
	}

}
