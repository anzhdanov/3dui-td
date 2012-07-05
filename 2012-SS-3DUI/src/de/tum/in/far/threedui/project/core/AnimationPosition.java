package de.tum.in.far.threedui.project.core;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.PositionInterpolator;
import javax.media.j3d.PositionPathInterpolator;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3d;

import de.tum.in.far.threedui.project.objects.Enemy;

public class AnimationPosition extends BranchGroup {


	float[] knots = {0.0f, 0.5f ,1.0f};
    Point3f[] positions;	
    Alpha a;
    Transform3D axis;
    PositionPathInterpolator positionInterpolator;
    TransformGroup targetTransformGroup;
    
	public AnimationPosition(BranchGroup targetObject) {
		
		targetTransformGroup = new TransformGroup();
		targetTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		targetTransformGroup.addChild(targetObject);
		addChild(targetTransformGroup);
		
		positions = new Point3f[3];
		
	    positions[0]= new Point3f(  0.0f,  0.0f, -0.05f);
	    positions[1]= new Point3f(  0.0f, -0.5f, -0.07f);
	    positions[2]= new Point3f( -0.1f,  0.0f, -0.02f);
		
		a = new Alpha(-1, (int)((Enemy)targetObject).speed); //-1=infinity and a rotation takes 3000ms
		axis = new Transform3D();
		axis.set(new Vector3d(0.0f,0.0f,1.0f));
		positionInterpolator = 
			new	PositionPathInterpolator(a, targetTransformGroup,axis,knots,positions);
		
		
		
		BoundingSphere bounds = new BoundingSphere();
		bounds.setRadius(40.0);
		positionInterpolator.setSchedulingBounds(bounds);
		
		
	}		
	
	/***
	 * Initializes the Animation
	 * Sets the waypoints and the animation Duration between the Waypoints
	 * @param posList
	 */
	public void setPositions(Point3f[] posList)
	{

		float[] knots2 = new float[posList.length];
		
		//Set Timing;
		knots2[0] = 0.0f;
		float totalLength = 0;
		for(int i=1;i<posList.length;i++)
		{
			//calculate vector between previous and this point
			Point3f delta = new Point3f();
			delta.x = posList[i].x - posList[i-1].x; 
			delta.y = posList[i].y - posList[i-1].y;
			delta.z = posList[i].z - posList[i-1].z;
			
			//calculate distance between points
			float length = (float) Math.sqrt(delta.x * delta.x + delta.y * delta.y + delta.z * delta.z);
			totalLength += length;
			knots2[i] =  totalLength;
		}
		//scale to range between 0 and 1
		for(int i=1;i<posList.length;i++)
		{
			knots2[i] = knots2[i]/totalLength;
		}
		knots2[posList.length-1]=1.0f;
		
		this.positions = posList;
		positionInterpolator = new	PositionPathInterpolator(a, targetTransformGroup,axis,knots2,positions);
		
		BoundingSphere bounds = new BoundingSphere();
		bounds.setRadius(40.0);
		positionInterpolator.setSchedulingBounds(bounds);
		addChild(positionInterpolator);
	
	}
	
	
	public TransformGroup getCurrentTransformation()
	{
		
		return targetTransformGroup;

	}
	
}
