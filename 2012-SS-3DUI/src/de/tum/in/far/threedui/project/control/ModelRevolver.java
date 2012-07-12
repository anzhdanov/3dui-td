package de.tum.in.far.threedui.project.control;

import java.io.File;


import de.tum.in.far.threedui.general.ModelObject;
import de.tum.in.far.threedui.general.ViewerUbitrack;
import de.tum.in.far.threedui.project.objects.CannonTower;

public class ModelRevolver {

	private InteractiveMarker viewingWindow;	
	private CannonTower currentDisplayed;
	private Control control;
	private int id;

	public ModelRevolver(InteractiveMarker interactiveMarker) {
		super();
		this.viewingWindow = interactiveMarker;
		this.viewingWindow.setModelRevolver(this);
		
	}
	

	public void displayModel(CannonTower.Type type)
	{
		if(currentDisplayed != null && currentDisplayed.getType() == type) return;
		
		if(currentDisplayed != null)
			currentDisplayed.detach();

		CannonTower c = new CannonTower(type);
		viewingWindow.getTransformGroup().addChild(c);

		currentDisplayed = c;
	}
	
	public void displayModel(int id){		
		displayModel(CannonTower.getTypeByID(id));
	}

	public InteractiveMarker getViewingWindow() {
		return viewingWindow;
	}

	public void setViewingWindow(InteractiveMarker viewingWindow) {
		this.viewingWindow = viewingWindow;
	}

	public void spinRevolver(LogicalSignal lSignal) {
		Motion motion = lSignal.getM();
		if (motion !=null) {			
			motion.display(this);			
		}		

	}
	
	public void registerControl(Control control){
		this.control = control;
		viewingWindow.startSamplingThread();
	}

	public int getId() {
		return id;
	}
	

	public void setId(int id) {
		this.id = id;
	}
	
	public Control getControl() {
		return control;
	}


	public void toggle() {
		this.viewingWindow.toggle();
		
	}
}
