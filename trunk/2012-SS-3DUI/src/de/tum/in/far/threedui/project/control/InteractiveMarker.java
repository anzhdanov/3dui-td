package de.tum.in.far.threedui.project.control;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import de.tum.in.far.threedui.general.TransformableObject;
import de.tum.in.far.threedui.project.core.NotifyPoseReceiver;
import de.tum.in.far.threedui.project.objects.SelectionAppearance;

public class InteractiveMarker extends TransformableObject {

	private NotifyPoseReceiver poseReceiver;
	private GeneralSwitch gSwitch;
	private ModelRevolver mRevolver;
	private boolean state = false;
	private boolean isPressed = false;

	public NotifyPoseReceiver getPoseReceiver() {
		return poseReceiver;
	}

	public void setPoseReceiver(NotifyPoseReceiver poseReceiver) {
		this.poseReceiver = poseReceiver;
		//		InteractThread thread = new InteractThread();
		//		thread.setDaemon(true);
		//		thread.start();
	}

	public void startSamplingThread(){
		InteractThread thread = new InteractThread();
		thread.setDaemon(true);
		thread.start();
	}

	public InteractiveMarker() {
		CubeObjectParameter parameter =
				new CubeObjectParameter(new SelectionAppearance(), 0.023f, 0.023f, 0.023f);
		CubeObject cube = new CubeObject(parameter);

		TransformGroup sTG = new TransformGroup();
		Transform3D tran = new Transform3D();
		tran.setTranslation(new Vector3f(0.0f, 0.0f, 0.023f));
		sTG.setTransform(tran);
		sTG.addChild(cube);

		gSwitch = new GeneralSwitch(null, sTG);
		gSwitch.switchOff();
		transGroup.addChild(gSwitch);
	}
	
	public void setModelRevolver(ModelRevolver mRevolver) {
		this.mRevolver = mRevolver;
	}

	class InteractThread extends Thread{
		public void run(){
			Control control = null;
			int id = 0;
			boolean interact = false;
			if(mRevolver!= null){
				control = mRevolver.getControl();
				id = mRevolver.getId();
				interact = true;
			}
			while(true){
				
				//System.out.println("live!");
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(poseReceiver!=null && poseReceiver.hasBeenTracked() && !poseReceiver.isTracked()){
					if (!isPressed) {
						//System.out.println("not tracked!");
					
						//System.out.println("interact: " + interact);
						if (interact) {
							toggle();
							//System.out.println("state: " + state);
							control.updateCurrentId(id, state);
						}
					}

					isPressed = true;
					
				}else{
					isPressed = false;
					
					
					//System.out.println("detach!");
				}
				if (poseReceiver != null)
					poseReceiver.setTracked(false);
			}
		}

		
	}
	
	public void toggle() {
		state = !state;
		if(state){
			gSwitch.switchOn();
		}else{
			gSwitch.switchOff();
		}
	}
}
