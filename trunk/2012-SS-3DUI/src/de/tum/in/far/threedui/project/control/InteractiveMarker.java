package de.tum.in.far.threedui.project.control;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import de.tum.in.far.threedui.general.ModelObject;
import de.tum.in.far.threedui.general.RedAppearance;

public class InteractiveMarker extends ModelObject {

	private PoseReceiverAlexander poseReceiver;
	private GeneralSwitch gSwitch;
	private ModelRevolver mRevolver;
	private boolean state = false;
	private boolean isPressed = false;

	public PoseReceiverAlexander getPoseReceiver() {
		return poseReceiver;
	}

	public void setPoseReceiver(PoseReceiverAlexander poseReceiver) {
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

	public InteractiveMarker(BranchGroup model, ModelRevolver mRevolver) {
		super(model);
		this.mRevolver = mRevolver;
		CubeObjectParameter parameter1 =
				new CubeObjectParameter(new RedAppearance(), 0.0f, 0.0f, 0.0f);
		CubeObject cube1 = new CubeObject(parameter1);
		CubeObjectParameter parameter2 =
				new CubeObjectParameter(new RedAppearance(), 0.023f, 0.023f, 0.0023f);
		CubeObject cube2 = new CubeObject(parameter2);

		TransformGroup sTG = new TransformGroup();
		Transform3D rot = new Transform3D();
		Transform3D tran = new Transform3D();
		tran.setTranslation(new Vector3f(0.02f,0.02f,0.0f));
		rot.mul(tran);
		rot.rotX(Math.PI/2);
		sTG.setTransform(rot);
		sTG.addChild(cube2);

		gSwitch = new GeneralSwitch(cube1, sTG);	
		gSwitch.switchOff();
		transGroup.addChild(gSwitch);
	}

	class InteractThread extends Thread{
		//CubeObjectParameter parameter =
		//		new CubeObjectParameter(new RedAppearance(), 0.023f, 0.023f, 0.0023f);
		//CubeObject cube = new CubeObject(parameter);
		//ModelObject model = new ModelObject(cube);		
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
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(poseReceiver!=null && !poseReceiver.isTracked()){
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
