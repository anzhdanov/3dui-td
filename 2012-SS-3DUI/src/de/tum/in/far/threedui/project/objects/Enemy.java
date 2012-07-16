package de.tum.in.far.threedui.project.objects;

import java.io.File;
import java.io.FileNotFoundException;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import org.jdesktop.j3d.loaders.vrml97.VrmlLoader;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;


import de.tum.in.far.threedui.general.BlueAppearance;
import de.tum.in.far.threedui.general.RedAppearance;
import de.tum.in.far.threedui.general.TransformableObject;
import de.tum.in.far.threedui.project.core.AnimationPosition;

public class Enemy extends TransformableObject {

	public float speed = 10000;
	
	public AnimationPosition animation;
	
	public String name = "test";
	
	
	
	
	public Enemy(Appearance app, float speed) {
		
				
		this.speed = speed;
	
		Sphere s = new Sphere(0.01f,app);
		transGroup.addChild(s);
		
		animation = new AnimationPosition(this);
		
		
		
	}


	
	public void setPath(Point3f[] posList)
	{
		animation.setPositions(posList);
	}
	

}
