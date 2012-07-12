package de.tum.in.far.threedui.project.objects;

import java.util.LinkedList;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.OrientedShape3D;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import de.tum.in.far.threedui.general.BlueAppearance;
import de.tum.in.far.threedui.general.TransformableObject;

public class GameController extends TransformableObject{
	
	private static GameController instance;
	public LinkedList<Enemy> enemyList;
	public BranchGroup particleGroup;
	public PathObject pathObject = null;
	
	
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
		

		Appearance app = new BlueAppearance();
		TransparencyAttributes ta = new TransparencyAttributes();
		ta.setTransparency(0.5f);
		ta.setTransparencyMode (ta.BLENDED);
		app.setTransparencyAttributes(ta);		
		
	}
	
	
	}
