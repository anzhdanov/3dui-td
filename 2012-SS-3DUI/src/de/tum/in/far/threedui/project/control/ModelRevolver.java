package de.tum.in.far.threedui.project.control;

import java.io.File;


import de.tum.in.far.threedui.general.ModelObject;
import de.tum.in.far.threedui.general.ViewerUbitrack;
import de.tum.in.far.threedui.project.objects.CannonTower;

public class ModelRevolver {

	private static final String FOLDER = "models";
	private InteractiveMarker viewingWindow;	
//	private ModelLoader ml = null;
	private CannonTower currentDisplayed;
	private ViewerUbitrack viewer; 
	private Control control;
	private int id;

	public ModelRevolver(ViewerUbitrack viewer) {
		super();		
		this.viewer = viewer;
		
		displayModel(0);
	}
	

	public void displayModel(CannonTower.Type type)
	{
		if(currentDisplayed != null && currentDisplayed.getType() == type) return;
		
		if(currentDisplayed!=null)
			currentDisplayed.detach();
		//get model by id
		CannonTower c = new CannonTower(type);
		//ModelObject model = ml.getModel(id);
	//	System.out.println(model);

		if(viewingWindow == null){
			viewingWindow = new InteractiveMarker(c, this);				
			viewer.addObject(viewingWindow);
		}else{
			viewingWindow.transGroup.addChild(c);
		}

		currentDisplayed = c;
	}
	
	public void displayModel(int id){		
		
		displayModel(CannonTower.getTypeByID(id));
	}

	public void loadModels(){
		File folder = new File(FOLDER);
		File[] listOfFiles = folder.listFiles(); 
		String file = null;		
		for (int i = 0; i < listOfFiles.length; i++) {
			if(listOfFiles[i].isFile()){
				file = listOfFiles[i].getName();
				if(!file.contains("-")){
//					ml.loadModel(file);	
				}
			}
		}
		displayModel(3);
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
