package de.tum.in.far.threedui.project.objects;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;

public class SelectionAppearance extends Appearance {

	public SelectionAppearance() {
		// Create the blue appearance node
		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f red = new Color3f(0.8f, 0.3f, 0.3f);
		Color3f specular = new Color3f(0.9f, 0.9f, 0.9f);
		
		// Ambient,emissive,diffuse,specular,shininess
		Material mat = new Material(red, black, red, specular,25.0f);
		
		// Switch on light
		mat.setLightingEnable(true);
		
		setMaterial(mat);
		
		// Transparency
		TransparencyAttributes ta = new TransparencyAttributes();
		ta.setTransparencyMode(TransparencyAttributes.BLENDED);
		ta.setTransparency(0.5f);
		
		setTransparencyAttributes(ta);
	}
}
