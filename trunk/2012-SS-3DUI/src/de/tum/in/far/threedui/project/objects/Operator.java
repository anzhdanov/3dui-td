package de.tum.in.far.threedui.general;

import java.util.List;


public class Operator {

	private PoseReceiver arrowPoseReceiver;
	List<PoseReceiver> targets;
	private ProximityCollider collider;
	private ColliderSubscriber subscriber;
	private Semaphore button;	

	public Operator(PoseReceiver arrowPoseReceiver,	 List<PoseReceiver> targets, Semaphore button) {
		super();
		this.arrowPoseReceiver = arrowPoseReceiver;		
		this.targets = targets;
		this.button = button;
		this.subscriber = new ColliderSubscriber(button);
		this.collider = new ProximityCollider(targets, arrowPoseReceiver.getMarkerTransGroup(), subscriber);
	}

	class ClockCounter extends Thread {

		public void run(){

			while (true) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
								
				pressWhenReady();					
				

				arrowPoseReceiver.setTracked(false);				
			}			
		}

		private void pressWhenReady() {
			collider.checkCollision();
			if(!arrowPoseReceiver.isTracked()){
				System.out.println("not tracked");
				button.greenOn();
				
			}
		}		
	}

	public void start(){
		ClockCounter clock = new ClockCounter();
		clock.setDaemon(true);
		clock.start();
	}	
}
