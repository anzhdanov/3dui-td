package de.tum.in.far.threedui.project.control;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Quat4d;

public class GeneralOperationsController {
	
	Transform3D currentT = new Transform3D();
	
	public double[] getCurrentSignal(TransformGroup transformGroup) {
		transformGroup.getTransform(currentT);
		Quat4d rotC = new Quat4d();
		currentT.get(rotC);
		return convertToEuler(rotC);
	}
	
	public double[] convertToEuler(Quat4d q) {
		double[] eulerAngles = new double[3];
		eulerAngles[0] = Math.atan2(2*(q.w*q.x + q.y*q.z), 
				(1 - 2*(Math.pow(q.x,2) + Math.pow(q.y,2))))*180/Math.PI;
		//System.out.println("fi: "+eulerAngles[0]);
		eulerAngles[1] = Math.asin(2*(q.w*q.y - q.z*q.x))*180/Math.PI;
		//System.out.println("theta: " + eulerAngles[1]);
		eulerAngles[2] =  Math.atan2(2*(q.w*q.z + q.x*q.y), 
				(1 - 2*(Math.pow(q.y,2) + Math.pow(q.z,2))))*180/Math.PI;
		//System.out.println("psi: " + eulerAngles[2]);
		return eulerAngles;
	}

}
