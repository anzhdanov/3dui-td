package de.tum.in.far.threedui.project.control;

import javax.media.j3d.Appearance;

public class CubeObjectParameter {
	public Appearance app;
	public float x;
	public float y;
	public float z;

	public CubeObjectParameter(Appearance app, float x, float y, float z) {
		this.app = app;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}