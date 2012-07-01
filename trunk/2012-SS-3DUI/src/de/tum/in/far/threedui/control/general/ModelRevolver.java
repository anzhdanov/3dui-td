package de.tum.in.far.threedui.control.general;

import java.io.File;

public class ModelRevolver {

	private static final String FOLDER = "models";
	private ModelLoader ml = new ModelLoader();
	private InteractiveMarker viewingWindow;	
	private ModelObject currentDisplayed;
	private ViewerUbitrack viewer; 
	private Control control;
	private int id;

	public ModelRevolver(ViewerUbitrack viewer) {
		super();		
		this.viewer = viewer;	
	}

	public void displayModel(int id){		
		if(currentDisplayed!=null)
			currentDisplayed.detach();
		//get model by id
		ModelObject model = ml.getModel(id);
		System.out.println(model);

		if(viewingWindow == null){
			viewingWindow = new InteractiveMarker(model, this);				
			viewer.addObject(viewingWindow);
		}else{
			viewingWindow.transGroup.addChild(model);
		}

		currentDisplayed = model;
	}

	public void loadModels(){
		File folder = new File(FOLDER);
		File[] listOfFiles = folder.listFiles(); 
		String file = null;		
		for (int i = 0; i < listOfFiles.length; i++) {
			if(listOfFiles[i].isFile()){
				file = listOfFiles[i].getName();
				if(!file.contains("-")){
					ml.loadModel(file);	
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
}
