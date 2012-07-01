package de.tum.in.far.threedui.control.general;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

public class InteractiveMarker extends ModelObject {

	private PoseReceiver poseReceiver;
	private GeneralSwitch gSwitch;
	private ModelRevolver mRevolver;
	private boolean state = false;

	public PoseReceiver getPoseReceiver() {
		return poseReceiver;
	}

	public void setPoseReceiver(PoseReceiver poseReceiver) {
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
		transGroup.addChild(gSwitch);
	}

	class InteractThread extends Thread{
		CubeObjectParameter parameter =
				new CubeObjectParameter(new RedAppearance(), 0.023f, 0.023f, 0.0023f);
		CubeObject cube = new CubeObject(parameter);
		ModelObject model = new ModelObject(cube);		
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
					//System.out.println("not tracked!");
					gSwitch.switchOn();
					System.out.println("interact: " + interact);
					if (interact) {
						state = !state;
						System.out.println("state: " + state);
						control.updateCurrentId(id, state);
					}

				}else{
					gSwitch.switchOff();
					
					//System.out.println("detach!");
				}
				poseReceiver.setTracked(false);
			}
		}
	}
}
