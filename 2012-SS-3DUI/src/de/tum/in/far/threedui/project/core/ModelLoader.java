package de.tum.in.far.threedui.project.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.media.j3d.BranchGroup;

import org.jdesktop.j3d.loaders.vrml97.VrmlLoader;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;

import de.tum.in.far.threedui.general.ModelObject;

/**
 * A utility class that handles loading and instancing of models.
 * Employs lazy loading (load only when a model is first used)
 * and caching (don't load model again if it was used earlier).
 * Though these are open to change.
 * 
 * @author Manuel
 */
public class ModelLoader {
	
	public enum ModelFormat {
		VRML97;
	}
	
	private class Model {
		public String id;
		public String filename;
		public ModelFormat format;
		public boolean loaded = false;
		public Scene scene = null;
		
		public Model(String id, String filename, ModelFormat format) {
			this.id = id;
			this.filename = filename;
			this.format = format;
		}
		private void loadVrml() {
			VrmlLoader loader = new VrmlLoader();
			try {
				this.scene = loader.load("models" + File.separator + this.filename);
			}
			catch (FileNotFoundException e) { e.printStackTrace(); }
			catch (IncorrectFormatException e) { e.printStackTrace(); }
			catch (ParsingErrorException e) { e.printStackTrace(); }

			this.loaded = true;
		}

		public void load() {
			switch(this.format) {
				case VRML97: loadVrml();
			}
		}
	}
	
	
	
	private HashMap<String, Model> models;
	
	public ModelLoader() {
		this.models = new HashMap<String, Model>();
	}
	
	/**
	 * Register a Model for later usage in the application (typically called from init).
	 * 
	 * @param id The id the model will be referenced with
	 * @param filename The filename of the model file (with extension)
	 * @param format One of the supported model formats. Currently only ModelFormat.VRML97;
	 * 
	 * @TODO support more formats, if required
	 */
	public void registerModel(String id, String filename, ModelFormat format) {
		Model model = new Model(id, filename, format);
		models.put(id, model);
	}
	
	/**
	 * Creates and returns a new instance of a model you specify.
	 * The model must have been registered before, else it returns null.
	 * 
	 * @param id Which model you want an object of.
	 * @return New ModelObject of the desired Model
	 */
	public ModelObject getModelObject(String id) {
		Model model = models.get(id);
		if (model == null) return null;
		
		if (!model.loaded) model.load();
		
		BranchGroup clone = (BranchGroup)model.scene.getSceneGroup().cloneTree();
		return new ModelObject(clone);
	}
	
}
