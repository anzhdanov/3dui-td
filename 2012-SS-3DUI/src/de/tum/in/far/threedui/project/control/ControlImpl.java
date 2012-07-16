package de.tum.in.far.threedui.project.control;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import de.tum.in.far.threedui.project.core.NotifyPoseReceiver;


public class ControlImpl implements Control {

	private static final int STAT_N = 100;
	private NotifyPoseReceiver controlPoseReceiver;
	private TransformGroup controlTG;
	private MotionSwitch mSwitch = new MotionSwitch();
	private SignalProcessor sp = new SignalProcessor(STAT_N);
	private IndicatorBlock ib;
	private List<ModelRevolver> mRevolvers;		
	private Button button;
	private GeneralOperationsController goc = new GeneralOperationsController();
	private int currentId;
	private boolean update;
	private int prevId = -1;

	class SamplingThread extends Thread{
		private static final int LAG = 150;
		Transform3D currentT = new Transform3D();
		StatStarter ss = new StatStarter();
		boolean gathered = false;
		
		class GatherStat extends Thread {
			public void run(){
				int k =0;
				while(k < STAT_N){
					try {
						sleep(LAG);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sp.addStat(goc.getCurrentSignal(controlTG), k);
					k++;
				};
				gathered = !gathered;
				sp.calculateMeanForAllStat();
			}
		}

		class StatStarter implements Switchable {
			
			public StatStarter(){
				button.setMovingObj(this);
			}

			@Override
			public void moveForward() {
				GatherStat stat = new GatherStat();
				stat.setDaemon(true);
				stat.start();
				sp.printStat();				
			}

			@Override
			public void moveBack() {
				//System.out.println("Phew, I am idle!");				
			}

		}

		public void run(){
			double[] eulerAngles = new double[3];			
			double[] thrshAngles = new double[3];
			while (true) {
				try {
					sleep(LAG);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(controlPoseReceiver.isTracked()){
					button.switchOff();
				}else{
					//System.out.println("not tracked!");
					button.switchOn();					
				}
				eulerAngles = goc.getCurrentSignal(controlTG);
				thrshAngles = sp.applyThresholdAll(eulerAngles);
				boolean letHimGo = ib.turnDirectionIndicator(thrshAngles);
				//System.out.println("update: " + update);
				if (letHimGo && update) {
					LogicalSignal lSignal = mSwitch.switchMotion(thrshAngles,
							sp.getMaxMean());
					ib.switchIndicator(lSignal.getM());
					//System.out.println(update);
					ModelRevolver mRevolver = null;
					if(currentId!=prevId && prevId!=-1){
						mRevolver = mRevolvers.get(prevId);
						mRevolver.toggle();
					}
					mRevolver = mRevolvers.get(currentId);
					mRevolver.spinRevolver(lSignal);
					prevId = currentId;
				}
				controlPoseReceiver.setTracked(false);
				//update = false;
				//currentId = 0;
			}
		}	
	}

	public ControlImpl(NotifyPoseReceiver controlPoseReceiver, Button button) {
		super();
		this.controlPoseReceiver = controlPoseReceiver;
		this.controlTG = controlPoseReceiver.getMarkerTransGroup();
		this.button = button;		
		ib = new IndicatorBlock(button.getTransformGroup());
		mRevolvers = new ArrayList<ModelRevolver>();
		SamplingThread samplingThread = new SamplingThread();
		samplingThread.setDaemon(true);
		samplingThread.start();
	}	
	
	public void registerRevolver(ModelRevolver revolver){
		revolver.setId(mRevolvers.size());
		mRevolvers.add(revolver);
	}

	@Override
	public void tiltForward(float angle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tiltBackward(float angle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tiltRight(float angle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tiltLeft(float angle) {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void updateCurrentId(int id, boolean update) {
		this.currentId = id;
		this.update = update;
		if(!update)
			prevId = -1;
	}	

}
