package de.tum.in.far.threedui.project.objects;

import java.util.LinkedList;

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;

import de.tum.in.far.threedui.general.ModelObject;
import de.tum.in.far.threedui.general.TransformableObject;
import de.tum.in.far.threedui.project.core.ModelLoader;

public class IconDisplay extends TransformableObject {

	ModelObject o0;
	ModelObject o1;
	ModelObject o2;
	ModelObject o3;
	ModelObject o4;
	ModelObject o5;
	ModelObject o6;
	ModelObject o7;
	LinkedList<ModelObject> modelList = new LinkedList<ModelObject>();
	
	
	public IconDisplay() {
	
		ModelLoader loader = ModelLoader.getInstance();
		o0 = loader.getModelObject("Gun");
		o1 = loader.getModelObject("DoubleGun");
		o2 = loader.getModelObject("Precision");
		o3 = loader.getModelObject("Gatling");
		o4 = loader.getModelObject("Howitzer");
		o5 = loader.getModelObject("StunAntenna");
		modelList.add(o0);
		modelList.add(o1);
		modelList.add(o2);
		modelList.add(o3);
		modelList.add(o4);
//		modelList.add(o5);


		
		float radius = 0.05f;
		float step = (float) (2.0f * (Math.PI*2 / 8.0f));
		
		for (ModelObject o  : modelList) {
			
			
			Transform3D t = new Transform3D();
			float x = (float) (Math.sin(step)*radius);
			float y = (float) (Math.cos(step)*radius);
			t.setTranslation(new Vector3f(x,y,0));
			
			Transform3D rotate = new Transform3D();
			rotate.rotX(Math.PI/2);
			t.mul(rotate);
			
			t.setScale(0.5f);
			
			o.getTransformGroup().setTransform(t);
			
			transGroup.addChild(o);
			step-= Math.PI*2 / 8.0f;
		}
		
		
	}
	
}
