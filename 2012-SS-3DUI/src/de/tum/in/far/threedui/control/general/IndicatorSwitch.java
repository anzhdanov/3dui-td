package de.tum.in.far.threedui.control.general;

import javax.media.j3d.Switch;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import org.freehep.j3d.TorusSegment;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;

public class IndicatorSwitch extends Indicator {

	private Switch buttonSwitch;	

	public IndicatorSwitch(){
		buttonSwitch = new Switch(0);
		buttonSwitch.setCapability(Switch.ALLOW_SWITCH_WRITE);
		Transform3D translate = new Transform3D();
		Transform3D rotate = new Transform3D();
		translate.setTranslation(new Vector3d(0.07, 0, 0.007));
		rotate.mul(translate);
		TransformGroup switchGroup = new TransformGroup(rotate);
		switchGroup.addChild(buttonSwitch);	

		RedAppearance redAppearance = new RedAppearance();

		Cylinder shape0 = new Cylinder(0f, 0f);
		Cylinder shape1 = generateArrow(redAppearance, null, null, 1,0,0);
		Cylinder shape2 = generateArrow(redAppearance,  null, null, 1,0,0);
		Cylinder shape3 = generateArrow(redAppearance,  null, null, 1,0,0);
		Cylinder shape4 = generateArrow(redAppearance,  null, null, 1,0,0);
		Cylinder shape6 = generateArrow(redAppearance, null, null, 1,0,0);
		Cylinder shape7 = generateArrow(redAppearance,  null, null, 1,0,0);
		Cylinder shape8 = generateArrow(redAppearance,  null, null, 1,0,0);
		Cylinder shape9 = generateArrow(redAppearance,  null, null, 1,0,0);
		TorusSegment shape5 = new TorusSegment(0, 0.007f, 0.03f, 0, 235, 20, redAppearance);
		Transform3D translCon = new Transform3D();
		Transform3D rotCon = new Transform3D();
		//		translCon.setTranslation(new Vector3d(0, 0.03, 0));
		//		rotCon.rotZ(Math.PI);
		//		rotCon.mul(translCon);
		//		rotCon.rotZ(3*Math.PI/2);
		TransformGroup conGroup = new TransformGroup(rotCon);		
		Cone cone1 = new Cone(0.013f, 0.030f, redAppearance);
		conGroup.addChild(cone1);

		TransformGroup child0TG = createTransformGroup(rotate);

		rotate.rotZ(Math.PI/2);
		TransformGroup child1TG = createTransformGroup(rotate);

		rotate.rotZ(Math.PI);
		TransformGroup child2TG = createTransformGroup(rotate);

		rotate.rotZ(3*Math.PI/2);
		TransformGroup child3TG = createTransformGroup(rotate);

		rotate.rotZ(2*Math.PI);
		TransformGroup child4TG = createTransformGroup(rotate);

		rotate.setTranslation(new Vector3d(-0.07,0,0.03));
		TransformGroup child5TG = createTransformGroup(rotate);
		child5TG.addChild(conGroup);
		
		//right forward
		rotate.rotZ(Math.PI/4);
		TransformGroup child6TG = createTransformGroup(rotate);

		//left forward
		rotate.rotZ(Math.PI*3/4);
		TransformGroup child7TG = createTransformGroup(rotate);

		//left backward
		rotate.rotZ(Math.PI*5/4);
		TransformGroup child8TG = createTransformGroup(rotate);

		//right backward
		rotate.rotZ(Math.PI*7/4);
		TransformGroup child9TG = createTransformGroup(rotate);
		
		buttonSwitch.addChild(child0TG);
		buttonSwitch.addChild(child1TG);
		buttonSwitch.addChild(child2TG);
		buttonSwitch.addChild(child3TG);
		buttonSwitch.addChild(child4TG);
		buttonSwitch.addChild(child5TG);
		buttonSwitch.addChild(child6TG);
		buttonSwitch.addChild(child7TG);
		buttonSwitch.addChild(child8TG);
		buttonSwitch.addChild(child9TG);		

		child0TG.addChild(shape0);
		child1TG.addChild(shape1);		
		child2TG.addChild(shape2);
		child3TG.addChild(shape3);
		child4TG.addChild(shape4);
		child5TG.addChild(shape5);
		child6TG.addChild(shape6);		
		child7TG.addChild(shape7);
		child8TG.addChild(shape8);
		child9TG.addChild(shape9);		

		transGroup.addChild(switchGroup);
	}

	public static TransformGroup createTransformGroup(Transform3D transform){		
		TransformGroup tg = new TransformGroup(transform);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		tg.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		return tg;
	}

	private Cylinder generateArrow(RedAppearance redAppearance,
			YellowAppearance yAppearance,  GreenAppearance grAppearance,int red , int yellow, int green) {
		Cylinder shape1 = null;
		if (green == 1) {
			shape1 = new Cylinder(0.007f, 0.060f, Cylinder.GENERATE_NORMALS,
					grAppearance);
		}else if(red == 1){
			shape1 = new Cylinder(0.007f, 0.060f, Cylinder.GENERATE_NORMALS,
					redAppearance);
		}		
		else if(yellow == 1){
			shape1 = new Cylinder(0.007f, 0.060f, Cylinder.GENERATE_NORMALS,
					yAppearance);
		}
		Transform3D translCon = new Transform3D();
		Transform3D rotCon = new Transform3D();
		translCon.setTranslation(new Vector3d(0, 0.035, 0));
		rotCon.rotZ(Math.PI);
		rotCon.mul(translCon);
		TransformGroup conGroup = new TransformGroup(rotCon);
		shape1.addChild(conGroup);
		Cone cone1 = new Cone(0.013f, 0.030f, redAppearance);
		conGroup.addChild(cone1);
		return shape1;
	}	

	public void switchIndicator(Motion m)
	{
		m.switchIndicator(this);
	}

	@Override
	public void forward() {
		buttonSwitch.setWhichChild(1);		
	}

	@Override
	public void backward() {
		buttonSwitch.setWhichChild(3);
	}

	@Override
	public void left() {
		buttonSwitch.setWhichChild(2);
	}

	@Override
	public void right() {
		buttonSwitch.setWhichChild(4);
	}

	@Override
	public void stop() {
		buttonSwitch.setWhichChild(0);
	}

	@Override
	public void rotate() {
		buttonSwitch.setWhichChild(5);
	}
	
	@Override
	public void rightForward() {
		buttonSwitch.setWhichChild(6);
	}
	
	@Override
	public void leftForward() {
		buttonSwitch.setWhichChild(7);
	}
	
	@Override
	public void leftBackward() {
		buttonSwitch.setWhichChild(8);
	}
	
	@Override
	public void rightBackward() {
		buttonSwitch.setWhichChild(9);
	}
}
