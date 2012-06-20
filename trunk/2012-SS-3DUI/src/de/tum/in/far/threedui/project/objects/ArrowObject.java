package de.tum.in.far.threedui.project.objects;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;

import de.tum.in.far.threedui.general.TransformableObject;

public class ArrowObject extends TransformableObject {

	protected final BranchGroup arrowBranchGroup;
	protected final TransformGroup cylTransGroup;
	protected final TransformGroup coneTransGroup;
	protected final Cylinder cyl;
	protected final Cone cone;
	
	
	
	/**
	 * Creates an arrow facing y direction with its base at the origin and the arrowhead pointing to
	 * (0, length, 0).
	 * 
	 * @param radius the radius of the arrow body (head has 2*radius at widest)
	 * @param length the length of the arrow
	 */
	public ArrowObject(float radius, float length) {
		this(radius, length, null, null);
	}
	
	/**
	 * Creates an arrow facing y direction with its base at the origin and the arrowhead pointing to
	 * (0, length, 0).
	 * 
	 * @param radius the radius of the arrow body (head has 2*radius at widest)
	 * @param length the length of the arrow
	 * @param app_body_active the Appearance for the arrow body when active
	 * @param app_body_inactive the Appearance for the arrow body when inactive
	 * @param app_head the Appearance for the arrow head
	 */
	public ArrowObject(float radius, float length, Appearance app_body, Appearance app_head) {

		float cone_length = 6*radius;
		float cyl_length = length - cone_length;

		arrowBranchGroup = new BranchGroup();
		
		// create TransformGroup
		cylTransGroup = new TransformGroup();
		cylTransGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		cylTransGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		
		coneTransGroup = new TransformGroup();
		coneTransGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		coneTransGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

		// Set the correct rotations and offsets
		Transform3D offset = new Transform3D();
		
		offset.setTranslation(new Vector3f(0.0f, cyl_length/2, 0.0f));
		cylTransGroup.setTransform(offset);

		offset.setTranslation(new Vector3f(0.0f, cyl_length + cone_length/2, 0.0f));
		coneTransGroup.setTransform(offset);

		// Create shapes
		cyl = new Cylinder(radius, cyl_length, app_body);
		cone = new Cone(2*radius, cone_length, app_head);
		
		// Create hierarchy of groups
		cylTransGroup.addChild(cyl);
		coneTransGroup.addChild(cone);

		arrowBranchGroup.addChild(cylTransGroup);
		arrowBranchGroup.addChild(coneTransGroup);
		
		transGroup.addChild(arrowBranchGroup);
	}

	
}
