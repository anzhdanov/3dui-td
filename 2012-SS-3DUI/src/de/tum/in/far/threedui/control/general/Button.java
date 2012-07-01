package de.tum.in.far.threedui.control.general;


public abstract class Button  extends TransformableObject {

	enum State {ON, OFF};

	protected State state;

	public abstract Switchable getMovingObj() ;

	public abstract void setMovingObj(Switchable movingObj) ;

	public abstract void switchOn();

	public abstract void switchOff();

}
