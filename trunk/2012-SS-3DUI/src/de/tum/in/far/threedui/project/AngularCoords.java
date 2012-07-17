package de.tum.in.far.threedui.project;

import javax.media.j3d.Transform3D;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class AngularCoords {

	public float yaw;
	public float pitch;
	public AngularCoords(float yaw, float pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public static void testAngleConversion() {

		Vector3f vec = new Vector3f(0.0f, -1.0f, 0.0f);
		Transform3D trans = new Transform3D();
		trans.setRotation(new AxisAngle4d(new Vector3d(0.0f, 0.0f, 1.0f), Math.PI / 18));
		
		for (int i = 0; i <= 36; i++) {
			AngularCoords res = AngularCoords.fromVector(vec);
			System.out.println("Rot by " + 10 * i + " degrees:" + radTodeg(res.yaw) + "°, " + radTodeg(res.pitch) + "°");
			trans.transform(vec);
		}
	}
	
	/**
	 * Converts from a vector to angular coordinates (yaw & pitch) in radians.
	 * For yaw, 0 degrees equals the direction of (0, -1, 0).
	 * Pitch goes from -PI/2 to +PI/2.
	 * 
	 * Note: For some inputs, results can be NaN, e.g. (0,0,1) -> (NaN, PI/2); (0,0,0) -> (NaN, NaN)
	 * 
	 * @param vec input vector
	 * @return an AnglePair containing yaw & pitch
	 */
	public static AngularCoords fromVector(Vector3f vec) {
		Vector3f norm = new Vector3f(vec);
		norm.normalize();
		
		float yaw = (float) Math.atan(norm.y / norm.x);
		if (norm.x < 0) yaw += Math.PI;
		yaw += Math.PI / 2;

		float pitch = (float) Math.acos(Math.sqrt(norm.x * norm.x + norm.y * norm.y)) * Math.signum(norm.z);
		
		return new AngularCoords(yaw, pitch);
	}
	
	public static float radTodeg(float radians) {
		return (float)((radians / Math.PI) * 180);
	}
	

}
