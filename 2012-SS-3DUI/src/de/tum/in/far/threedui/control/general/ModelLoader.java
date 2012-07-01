package de.tum.in.far.threedui.control.general;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import org.jdesktop.j3d.loaders.vrml97.VrmlLoader;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;


public class ModelLoader {
	
	private List<ModelObject> models = new ArrayList<ModelObject>();

	public void loadModel(String file){
		VrmlLoader loader = new VrmlLoader();
		Scene myScene = null;
		try {
			myScene = loader.load("models" + File.separator + file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IncorrectFormatException e) {
			e.printStackTrace();
		} catch (ParsingErrorException e) {
			e.printStackTrace();
		}
		//for the first marker
		BranchGroup objRoot = new BranchGroup();
		objRoot.setCapability(BranchGroup.ALLOW_DETACH);
		Transform3D rotate = new Transform3D();
		Transform3D translate = new Transform3D();		
		translate.setTranslation(new Vector3d(0, 0.025, 0));
		rotate.rotX(Math.PI/2);
		rotate.mul(translate);
		TransformGroup objRotGroup = new TransformGroup(rotate);
		objRotGroup.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		objRotGroup.addChild(myScene.getSceneGroup());
		objRoot.addChild(objRotGroup);
		ModelObject modelObject = new ModelObject(objRoot);
		models.add(modelObject);
	}
	
	public ModelObject getModel(int id){
		ModelObject model = null;
		if(id < models.size())
			model =  models.get(id);
		else 
			model =  models.get(0);
		return model;
	}
}
