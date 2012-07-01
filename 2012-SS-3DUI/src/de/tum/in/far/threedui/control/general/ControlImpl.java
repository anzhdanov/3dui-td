package de.tum.in.far.threedui.control.general;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

public class ControlImpl implements Control {

	private static final int STAT_N = 100;
	private PoseReceiver controlPoseReceiver;
	private TransformGroup controlTG;
	private MotionSwitch mSwitch = new MotionSwitch();
	private SignalProcessor sp = new SignalProcessor(STAT_N);
	private IndicatorBlock ib;
	private List<ModelRevolver> mRevolvers;		
	private Button button;
	private GeneralOperationsController goc = new GeneralOperationsController();
	private int currentId;
	private boolean update;

	class SamplingThread extends Thread{
		private static final int LAG = 70;
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
				System.out.println("Phew, I am idle!");				
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
				System.out.println("update: " + update);
				if (letHimGo && update) {
					LogicalSignal lSignal = mSwitch.switchMotion(thrshAngles,
							sp.getMaxMean());
					ib.switchIndicator(lSignal.getM());
					System.out.println(update);
					ModelRevolver mRevolver = mRevolvers.get(currentId);
					mRevolver.spinRevolver(lSignal);
				}
				controlPoseReceiver.setTracked(false);
				//update = false;
				//currentId = 0;
			}
		}	
	}

	public ControlImpl(PoseReceiver controlPoseReceiver, Button button) {
		super();
		this.controlPoseReceiver = controlPoseReceiver;
		this.controlTG = controlPoseReceiver.getMarkerTransGroup();
		this.button = button;		
		ib = new IndicatorBlock(button.transGroup);
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
	public void updateCurrentId(int id, boolean update) {
		this.currentId = id;
		this.update = update;		
	}	

}
