package de.tum.in.far.threedui.project.gamelogic;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;

import javax.media.j3d.Appearance;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Color3f;

import de.tum.in.far.threedui.general.BlueAppearance;
import de.tum.in.far.threedui.general.TransformableObject;
import de.tum.in.far.threedui.project.EnemyAppearance;
import de.tum.in.far.threedui.project.objects.Enemy;
import de.tum.in.far.threedui.project.objects.PathObject;

public class GameController extends TransformableObject {
	
	private static GameController instance;
	public LinkedList<Enemy> enemyList;
	public BranchGroup particleGroup;
	public PathObject pathObject = null;
	
	private int lives;
	
	public static GameController getInstance()
	{
		if(instance == null)
		{
			instance = new GameController();
		}
		return instance;
	}
	
	private GameController()
	{
		enemyList = new LinkedList<Enemy>();
	
		particleGroup = new BranchGroup();
		particleGroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		particleGroup.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		particleGroup.setCapability(BranchGroup.ALLOW_LOCAL_TO_VWORLD_READ);
		
		lives = 20;
		
		this.addChild(new EnemyBehavior());
	}
	
	private class EnemyBehavior extends Behavior {

		private WakeupCondition condition;
		
		public EnemyBehavior() {
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
//			System.out.println("process");
			// TODO game over
			for(Iterator<Enemy> iter_enemy = enemyList.iterator(); iter_enemy.hasNext(); ) {
				Enemy e = iter_enemy.next();
				if (e.reachedEnd()) {
					System.out.println(e + " reached end!!");
					lives--;
					System.out.println("Remaining lives: " + lives);
					e.destroy();
					iter_enemy.remove();
				}
				
			}
			wakeupOn(condition);
		}
		
	}
	
}
