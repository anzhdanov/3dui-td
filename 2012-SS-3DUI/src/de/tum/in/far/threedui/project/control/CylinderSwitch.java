package de.tum.in.far.threedui.project.control;

import javax.media.j3d.Appearance;
import javax.media.j3d.Switch;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Cylinder;


public class CylinderSwitch extends Button  {

	private Switch buttonSwitch;
	private Switchable movingObj;

	public Switchable getMovingObj() {
		return movingObj;
	}

	public void setMovingObj(Switchable movingObj) {
		this.movingObj = movingObj;
	}

	public CylinderSwitch(float r, float h, Appearance app1, Appearance app2){

		//constructing shapes	
		Cylinder shape1 = new Cylinder(r, h, Cylinder.GENERATE_NORMALS, app1);
		Cylinder shape2 = new Cylinder(r, h/2,Cylinder.GENERATE_NORMALS, app2);
		
		buttonSwitch = new Switch(0);
		buttonSwitch.setCapability(Switch.ALLOW_SWITCH_WRITE);
		Transform3D rotate = new Transform3D();
		Transform3D translate = new Transform3D();
		translate.setTranslation(new Vector3d(0, 0, 0));
		rotate.rotX(Math.PI/2);
		rotate.mul(translate);
		TransformGroup switchGroup = new TransformGroup(rotate);
		switchGroup.addChild(buttonSwitch);		


		TransformGroup child1TG = createTransformGroup();
		TransformGroup child2TG = createTransformGroup();

		//add to the switch
		buttonSwitch.addChild(child1TG);
		buttonSwitch.addChild(child2TG);
		child1TG.addChild(shape1);
		child2TG.addChild(shape2);
		transGroup.addChild(switchGroup);
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
