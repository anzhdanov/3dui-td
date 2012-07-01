package de.tum.in.far.threedui.control.general;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;

public class TransformableObject extends BranchGroup {

	protected final TransformGroup transGroup;
	
	public TransformableObject() {
		transGroup = new TransformGroup();
		transGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		transGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		transGroup.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		setCapability(ALLOW_DETACH);
		setCapability(ALLOW_CHILDREN_EXTEND);
		setCapability(ALLOW_CHILDREN_WRITE);
		setCapability(ALLOW_CHILDREN_READ);		
		addChild(transGroup);
	}
	
	public TransformGroup getTransformGroup() {
		return transGroup;
	}
}
