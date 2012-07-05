package de.tum.in.far.threedui.project.control;
import static java.lang.Math.*;

import javax.vecmath.Quat4d;

public class SignalProcessor {
	private double[] fiV;
	private double[] thetaV;
	private double[] psiV;
	
	private double fiMean;
	private double thetaMean;
	private double psiMean;
		
	public SignalProcessor(int sample) {
		fiV = new double[sample];
		thetaV = new double[sample];
		psiV = new double[sample];
	}
	
	public void addStat(double[] eulerAngles, int k){
		fiV[k] = eulerAngles[0];
		thetaV[k] = eulerAngles[1];
		psiV[k] = eulerAngles[2];		
	}
	
	public double applyThreshold(double angle, double mean){
		double angleThresh = 0;
		double delta = abs(angle) - abs(mean);
		if(abs(atan(delta)) > 0.9*PI/2){
			if(angle > 0){
				angleThresh = delta;
			}else{
				angleThresh = -delta;
			}
		}	
		return angleThresh;
	}	
	
	public double[] applyThresholdAll(double[] angles){
		double[] thresh = new double[3];
		thresh[0] = applyThreshold(-angles[0], fiMean);
//		System.out.println("fi thresh: " + thresh[0]);
		thresh[1] = applyThreshold(angles[1], thetaMean);
//		System.out.println("theta thresh: " + thresh[1]);
		thresh[2] = applyThreshold(angles[2], psiMean);
//		System.out.println("psi thresh: " + thresh[2]);
		return thresh;
	}
	
	public void calculateMeanForAllStat(){
		fiMean=calculateMean(fiV);
		thetaMean=calculateMean(thetaV);
		psiMean=calculateMean(psiV);
	}
	
	public double calculateMean(double[] angles){
		double mean = 0;
		for (int i = 0; i < angles.length; i++) {
			mean+=angles[i];
		}
		mean/=angles.length;
		return mean;
	}
	
	public double getMaxMean(){
		double max=0;
		max = (fiMean > thetaMean) ? (fiMean) : (thetaMean );
		return max;
	}
	
	public void printAngles(double[] angles){
		for (int i = 0; i < angles.length; i++) {
//			System.out.println(angles[i]);
		}
	}
	
	public void printStat(){
//		System.out.println("fi:");
		printAngles(fiV);
//		System.out.println("theta");
		printAngles(thetaV);
//		System.out.println("psi");
		printAngles(psiV);
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
	
	public double getFiMean() {
		return fiMean;
	}

	public double getThetaMean() {
		return thetaMean;
	}

	public double getPsiMean() {
		return psiMean;
	}
}
