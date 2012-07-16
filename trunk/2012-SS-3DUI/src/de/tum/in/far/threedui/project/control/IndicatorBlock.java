package de.tum.in.far.threedui.project.control;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import de.tum.in.far.threedui.general.GreenAppearance;
import de.tum.in.far.threedui.general.RedAppearance;

public class IndicatorBlock {

	private TransformGroup tg;
	private Indicator indicator = new IndicatorSwitch();
	private CylinderSwitch directionIndicator =
			new CylinderSwitch(0.0026f, 0.0026f, new GreenAppearance(), new RedAppearance());
	public IndicatorBlock(TransformGroup tg) {
		super();
		this.tg = tg;
		tg.addChild(indicator);
		BranchGroup indBG = new BranchGroup();
		TransformGroup indTG = new TransformGroup();
		Transform3D indT = new Transform3D();
		indT.setTranslation(new Vector3d(0.033,0.033,0));
		indTG.setTransform(indT);
//		indTG.addChild(directionIndicator); // don't display indicator
		indBG.addChild(indTG);
		tg.addChild(indBG);
	}
	public Indicator getIndicator() {
		return indicator;
	}
	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}
	public CylinderSwitch getDirectionIndicator() {
		return directionIndicator;
	}
	public void setDirectionIndicator(CylinderSwitch directionIndicator) {
		this.directionIndicator = directionIndicator;
	}
	public void switchIndicator(Motion m) {
		if (m != null) {
			m.switchIndicator(indicator);
		}		
	}
	public boolean turnDirectionIndicator(double[] thrshAngles) {
		directionIndicator.switchOff(); return true; // never disable
/*		if( Math.abs(thrshAngles[2]) < 45){
			directionIndicator.switchOff();
			return true;
		}
		directionIndicator.switchOn();	
		return false;*/
	}	

}
