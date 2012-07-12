package de.tum.in.far.threedui.project.control;

import javax.media.j3d.Node;
import javax.media.j3d.Switch;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;


public class GeneralSwitch extends Button  {

	private Switch buttonSwitch;
	private Switchable movingObj;

	public Switchable getMovingObj() {
		return movingObj;
	}

	public void setMovingObj(Switchable movingObj) {
		this.movingObj = movingObj;
	}

	public GeneralSwitch(Node shape1, Node shape2){	
		
		buttonSwitch = new Switch(0);
		buttonSwitch.setCapability(Switch.ALLOW_SWITCH_WRITE);


		TransformGroup child1TG = createTransformGroup();
		TransformGroup child2TG = createTransformGroup();

		//add to the switch
		buttonSwitch.addChild(child1TG);
		buttonSwitch.addChild(child2TG);
		if (shape1 != null) child1TG.addChild(shape1);
		if (shape2 != null) child2TG.addChild(shape2);
		transGroup.addChild(buttonSwitch);
	}

	public void switchOn(){
		buttonSwitch.setWhichChild(1);
		state = State.ON;
		if (movingObj !=null) {
			movingObj.moveForward();
		}
	}

	public void switchOff(){
		buttonSwitch.setWhichChild(0);
		state = State.OFF;
	}

	public static TransformGroup createTransformGroup(){		
		TransformGroup tg = new TransformGroup();
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		tg.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		return tg;
	}
}
