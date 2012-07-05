package de.tum.in.far.threedui.general;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.vecmath.Color3f;

public class YellowAppearance extends Appearance {

	public YellowAppearance(){
		// Create the green appearance node
		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f yellow = new Color3f(0.8f, 0.8f, 0.3f);
		Color3f specular = new Color3f(0.9f, 0.9f, 0.9f);
		// Ambient,emissive,diffuse,specular,shininess
		Material greenMat = new Material(yellow, black, yellow, specular,25.0f);
		//Switch on light
		greenMat.setLightingEnable(true);
		setMaterial(greenMat);
	}
}
