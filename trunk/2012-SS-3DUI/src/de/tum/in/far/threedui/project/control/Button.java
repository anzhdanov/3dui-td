package de.tum.in.far.threedui.project.control;

import de.tum.in.far.threedui.general.TransformableObject;


public abstract class Button  extends TransformableObject {

	enum State {ON, OFF};

	protected State state;

	public abstract Switchable getMovingObj() ;

	public abstract void setMovingObj(Switchable movingObj) ;

	public abstract void switchOn();

	public abstract void switchOff();

}
