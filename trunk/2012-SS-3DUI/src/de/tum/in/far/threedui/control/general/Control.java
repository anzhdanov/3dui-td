package de.tum.in.far.threedui.control.general;


public interface Control {
	
	public void tiltForward(float angle);
	public void tiltBackward(float angle);
	public void tiltRight(float angle);
	public void tiltLeft(float angle);
	public void registerRevolver(ModelRevolver revolver);
	public void updateCurrentId(int id, boolean update);
}
