package de.tum.in.far.threedui.project;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.vecmath.Color3f;

public class EnemyAppearance extends Appearance {

	private Material mat;
	private Color3f origColor;
	
	public EnemyAppearance(Color3f color) {
		this.origColor = color;
		
		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
//		Color3f green = new Color3f(0.3f, 0.8f, 0.3f);
		Color3f specular = new Color3f(0.9f, 0.9f, 0.9f);
		
		// Ambient,emissive,diffuse,specular,shininess
		mat = new Material(color, black, color, specular, 25.0f);
		mat.setCapability(Material.ALLOW_COMPONENT_WRITE);
		
		//Switch on light
		mat.setLightingEnable(true);
		
		setMaterial(mat);
	}
	
	public void changeColor(float factor) {
		Color3f red = new Color3f(0.8f, 0.3f, 0.3f);
		red.interpolate(this.origColor, factor);
		mat.setAmbientColor(red);
		mat.setDiffuseColor(red);
		
	}
	
}
