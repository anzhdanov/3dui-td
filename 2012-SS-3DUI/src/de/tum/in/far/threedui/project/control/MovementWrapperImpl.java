package de.tum.in.far.threedui.project.control;

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;

import de.tum.in.far.threedui.general.ModelObject;
import de.tum.in.far.threedui.general.TransformableObject;

public class MovementWrapperImpl extends TransformableObject implements MovementWrapper {

	private Motion motion;
	private Motion prevMotion;
	private int accelerator = 1;
	private GeneralOperationsController goc = new GeneralOperationsController();

	public MovementWrapperImpl(ModelObject marker1, ModelObject sheepObject) {
		sheepObject.detach();		
		super.transGroup.addChild(sheepObject);
		marker1.getTransformGroup().addChild(this);	
	}

	@Override
	public void moveForward(AngleTuple angle) {
		Vector3f moveV = new Vector3f((float) getMove(angle.getB()), 0f, 0f);
		generalMove(moveV);		
	}

	private void generalMove(Vector3f moveV) {
//		System.out.println("move forward");
		Vector3f currentV = new Vector3f();
		Transform3D currentPosition = new Transform3D();
		transGroup.getTransform(currentPosition);
		currentPosition.get(currentV);
		moveV.add(currentV);
		Transform3D moveT = new Transform3D();
		moveT.setTranslation(moveV);
		transGroup.setTransform(moveT);
	}

	private void generalRotation(double angle) {
		double angleInRadians = angle*Math.PI/180;
//		System.out.println("rotate");		
		Transform3D currentPosition = new Transform3D();
		transGroup.getTransform(currentPosition);
		Vector3f translV = new Vector3f();
		currentPosition.get(translV);
		Transform3D translation = new Transform3D();
		translation.setTranslation(translV);		
		currentPosition.rotZ(angleInRadians);
		translation.mul(currentPosition);
		transGroup.setTransform(translation);
	}

	@Override
	public void moveBackward(AngleTuple angle) {
		double d = getMove(angle.getB());
		Vector3f moveV = new Vector3f((float) d, 0f, 0f);
		generalMove(moveV);

	}

	private double getMove(double angle) {
		return accelerator*angle/100000;
	}

	@Override
	public void moveRight(AngleTuple angle) {
		Vector3f moveV = new Vector3f(0f, (float) getMove(angle.getA()), 0f);
		generalMove(moveV);

	}

	@Override
	public void moveLeft(AngleTuple angle) {
		Vector3f moveV = new Vector3f(0f, (float) getMove(angle.getA()), 0f);
		generalMove(moveV);
	}


	@Override
	public void moveRotate(double angle) {		
		generalRotation(angle);		
	}

	@Override
	public void moveStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void controlMovement(LogicalSignal lSignal) {
		motion = lSignal.getM();
		AngleTuple angle = lSignal.getAngle();
		if (motion !=null) {
			if(prevMotion!=null && prevMotion.equals(motion))
				accelerator ++;
			else
				accelerator = 1;
			motion.move(this, angle);
			prevMotion = motion;
		}		
	}

	@Override
	public void moveRightForward(AngleTuple angle) {
		moveRight(angle);
		moveForward(angle);		
	}

	@Override
	public void moveLeftForward(AngleTuple angle) {
		moveLeft(angle);
		moveForward(angle);

	}

	@Override
	public void moveRightBackward(AngleTuple angle) {
		moveRight(angle);
		moveBackward(angle);

	}

	@Override
	public void moveLeftBackward(AngleTuple angle) {
		moveLeft(angle);
		moveBackward(angle);		
	}
}