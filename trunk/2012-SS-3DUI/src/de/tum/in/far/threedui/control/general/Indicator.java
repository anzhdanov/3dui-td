package de.tum.in.far.threedui.control.general;

public abstract class Indicator extends TransformableObject {
	
	Motion motion;
	public abstract void forward();
	public abstract void backward();
	public abstract void left();
	public abstract void right();
	public abstract void stop();
	public abstract void switchIndicator(Motion m);
	public abstract void rotate() ;
	public abstract void rightForward();
	public abstract void leftForward() ;
	public abstract void rightBackward() ;
	public abstract void leftBackward() ;
}
