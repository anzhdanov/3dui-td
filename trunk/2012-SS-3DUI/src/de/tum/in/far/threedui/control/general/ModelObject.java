package de.tum.in.far.threedui.control.general;

import javax.media.j3d.BranchGroup;

public class ModelObject extends TransformableObject {

	public ModelObject(BranchGroup model) {
		super.transGroup.addChild(model);
	}
}
