package de.tum.in.far.threedui.project.objects;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;

import de.tum.in.far.threedui.general.TransformableObject;

public class CoordSysObject extends TransformableObject {

	protected final BranchGroup xyzBranchGroup;
	protected final TransformGroup xTransGroup;
	protected final TransformGroup yTransGroup;
	protected final TransformGroup zTransGroup;
	protected final ArrowObject Ax;
	protected final ArrowObject Ay;
	protected final ArrowObject Az;
	
	public CoordSysObject(float radius, float length) {

		xyzBranchGroup = new BranchGroup();
		
		// create TransformGroups
		xTransGroup = new TransformGroup();
		xTransGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		xTransGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		
		yTransGroup = new TransformGroup();
		yTransGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		yTransGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		
		zTransGroup = new TransformGroup();
		zTransGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		zTransGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		
		// Set the correct rotations and offsets
		Transform3D offset = new Transform3D();
		
		offset.setRotation(new AxisAngle4f(0.0f, 0.0f, -1.0f, (float)(Math.PI/2)));
		xTransGroup.setTransform(offset);

		offset.setRotation(new AxisAngle4f(1.0f, 0.0f, 0.0f, 0.0f)); // no rotation needed
		yTransGroup.setTransform(offset);

		offset.setRotation(new AxisAngle4f(1.0f, 0.0f, 0.0f, (float)(Math.PI/2)));
		zTransGroup.setTransform(offset);

		// Create appearances
		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f red = new Color3f(0.8f, 0.3f, 0.3f);
		Color3f green = new Color3f(0.3f, 0.8f, 0.3f);
		Color3f blue = new Color3f(0.3f, 0.3f, 0.8f);
		Color3f specular = new Color3f(0.3f, 0.3f, 0.3f);
		
		Material redMat = new Material(red, black, red, specular, 10.0f);
		Material greenMat = new Material(green, black, green, specular, 10.0f);
		Material blueMat = new Material(blue, black, blue, specular, 10.0f);
		
		redMat.setLightingEnable(true);
		greenMat.setLightingEnable(true);
		blueMat.setLightingEnable(true);
		
		Appearance redApp = new Appearance();
		redApp.setMaterial(redMat);
		Appearance greenApp = new Appearance();
		greenApp.setMaterial(greenMat);
		Appearance blueApp = new Appearance();
		blueApp.setMaterial(blueMat);

		// Create shapes
		Ax = new ArrowObject(0.001f, 0.05f, redApp, redApp);
		Ay = new ArrowObject(0.001f, 0.05f, greenApp, greenApp);
		Az = new ArrowObject(0.001f, 0.05f, blueApp, blueApp);
		
		// Create hierarchy of groups
		xTransGroup.addChild(Ax);
		yTransGroup.addChild(Ay);
		zTransGroup.addChild(Az);

		xyzBranchGroup.addChild(xTransGroup);
		xyzBranchGroup.addChild(yTransGroup);
		xyzBranchGroup.addChild(zTransGroup);
		
		// can only add BranchGroup to already compiled group (in case of reuse_tg) (?)
		transGroup.addChild(xyzBranchGroup);
	}
	
}
