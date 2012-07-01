package de.tum.in.far.threedui.control.general;

public class MotionSwitch {

	public LogicalSignal switchMotionToMax(double[] angles, double defaultV){
		Motion m = null;		
		double max = defaultV; 
		int n = 3;
		for (int i = 0; i < angles.length; i++) {
			if(Math.abs(angles[i]) > Math.abs(max)){
				max = angles[i];
				n =i;
			}
		}
		System.out.println("n: " +n);
		System.out.println("defaultV: " + defaultV);
		switch(n){
		case 0:{
			if(angles[0] > 0){
				//System.out.println("left");
				m = Motion.Left;
			}else{
				//System.out.println("right");
				m = Motion.Right;
			}
			break;
		}
		case 1:{
			if(angles[1] < 0){
				//System.out.println("backward");
				m = Motion.Backward;
			}else{
				//System.out.println("forward");
				m = Motion.Forward;
			}
			break;
		}
		case 2:{
			//			m = Motion.Rotate;
			//			break;
		}
		default:{
			m = Motion.Stop;
			break;
		}
		}
		LogicalSignal lSignal = new LogicalSignal(m, new AngleTuple(angles[0], angles[1]));
		return lSignal;
	}
	
	public LogicalSignal switchMotion(double[] angles, double defaultV){
		
		Motion m = Motion.getMoveDirection(angles[0], angles[1]);
		
		LogicalSignal ls = new LogicalSignal(m, new AngleTuple(angles[0], angles[1]));
		
		return ls;
	
	}
}
