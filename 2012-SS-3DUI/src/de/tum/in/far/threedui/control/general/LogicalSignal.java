package de.tum.in.far.threedui.control.general;

public class LogicalSignal {
	
	private Motion m ;	
	private AngleTuple angle;
	
	public LogicalSignal(Motion m, AngleTuple angleTuple) {
		super();
		this.m = m;
		this.angle = angleTuple;
	}
	
	public Motion getM() {
		return m;
	}
	public void setM(Motion m) {
		this.m = m;
	}
	public double getAAngle() {
		return angle.getA();
	}
	
	public double getBAngle() {
		return angle.getB();
	}

	public AngleTuple getAngle() {
		return angle;
	}

	public void setAngle(AngleTuple angle) {
		this.angle = angle;
	}


}
