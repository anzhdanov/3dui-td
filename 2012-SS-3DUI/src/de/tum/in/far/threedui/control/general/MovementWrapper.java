package de.tum.in.far.threedui.control.general;

public interface MovementWrapper {
	
	public void moveForward(AngleTuple angle);
	public void moveRightForward(AngleTuple angle);
	public void moveLeftForward(AngleTuple angle);
	public void moveBackward(AngleTuple angle);
	public void moveRightBackward(AngleTuple angle);
	public void moveLeftBackward(AngleTuple angle);
	public void moveRight(AngleTuple angle);
	public void moveLeft(AngleTuple angle);
	void moveRotate(double angle);
	public void moveStop();	
	public void controlMovement(LogicalSignal lSignal);
	

}
