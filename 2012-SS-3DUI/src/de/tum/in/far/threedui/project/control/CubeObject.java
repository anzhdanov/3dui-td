package de.tum.in.far.threedui.project.control;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;


import com.sun.j3d.utils.geometry.Box;

import de.tum.in.far.threedui.general.TransformableObject;

public class CubeObject extends TransformableObject {
	
	public CubeObject(CubeObjectParameter parameterObject) {
		Box box = new Box(parameterObject.x, parameterObject.y, parameterObject.z, parameterObject.app);
		TransformGroup group = new TransformGroup();
		Transform3D transform3d = new Transform3D();		
		transform3d.setTranslation(new Vector3d(0.0f, 0.0, 0.0));
		group.setTransform(transform3d);
		group.addChild(box);
		transGroup.addChild(group);		
	}

	
}
