package de.tum.in.far.threedui.control.general;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Box;

public class CubeObject extends TransformableObject {
	
	public CubeObject(CubeObjectParameter parameterObject) {
		//Sphere iAmASphereButIllBeABoxSoon = new Sphere(0.005f, app);
		Box box = new Box(parameterObject.x, parameterObject.y, parameterObject.z, parameterObject.app);
		//transGroup.addChild(iAmASphereButIllBeABoxSoon);
		TransformGroup group = new TransformGroup();
		Transform3D transform3d = new Transform3D();		
		transform3d.setTranslation(new Vector3d(0.0f, 0.0, 0.0));
		group.setTransform(transform3d);
		group.addChild(box);
		transGroup.addChild(group);		
	}

	
}
