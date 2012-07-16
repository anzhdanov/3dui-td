package de.tum.in.far.threedui.project.objects;

import java.util.LinkedList;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleArray;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Point3f;
import javax.vecmath.Point4f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.*;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import de.tum.in.far.threedui.ex1.SphereObject;
import de.tum.in.far.threedui.general.BlueAppearance;
import de.tum.in.far.threedui.general.GreenAppearance;
import de.tum.in.far.threedui.general.RedAppearance;
import de.tum.in.far.threedui.general.TransformableObject;
import de.tum.in.far.threedui.project.core.AnimationPosition;

public class PathObject extends TransformableObject {
	
	
	//Internal map
	private int xres= 10;
	private int yres= 12;
	private TransformGroup tPath;
	
	
	private float tileWidth = 0.025f;
	
	private int map[] = {
		//	0 1 2 3 4 5 6 7 8 9
			0,0,0,1,1,1,1,1,1,0, // 0
			0,0,0,1,0,0,0,0,1,0, // 1
			0,0,0,1,0,0,1,1,1,0, // 2
			0,0,0,1,0,2,1,0,0,0, // 3
			0,1,1,1,0,0,0,0,0,0, // 4
			0,1,0,0,0,0,0,1,1,1, // 5
			0,1,0,0,0,0,0,1,0,1, // 6
			0,1,0,0,0,0,0,1,0,1, // 7
			0,1,1,1,1,1,1,1,0,1, // 8
			0,0,0,0,0,0,0,0,0,1, // 9
			0,0,0,1,1,1,1,1,1,1, // 10
			0,0,0,3,0,0,0,0,0,0  // 11
	};
	
	
	Point3f[] myCoords;
	BranchGroup markerGroup;
	
	public PathObject(Appearance app) {
		

		 myCoords = new Point3f[getNumberOfWayPoints()*6];
		
		for(int i=0,a=0;i<this.map.length;i++)
		{
			if(this.getPath(i%xres, i/xres)==0)
			{
				continue;
			}
			float xOffset = (i%xres) * tileWidth;
			float yOffset = (i/xres) * tileWidth;
			
			myCoords[a++] =		new Point3f(  0.0f+xOffset, 0.0f+yOffset, 0.0f );
			myCoords[a++] =	    new Point3f(  tileWidth+xOffset, 0.0f+yOffset, 0.0f );
			myCoords[a++] =	    new Point3f(  0.00f+xOffset, tileWidth+yOffset, 0.0f );
			    
			myCoords[a++] =	    new Point3f(  tileWidth+xOffset, 0.0f+yOffset, 0.0f );
			myCoords[a++] =	    new Point3f(  tileWidth+xOffset, tileWidth+yOffset, 0.0f );
			myCoords[a++] =	    new Point3f(  0.00f+xOffset, tileWidth+yOffset, 0.0f ); 
			
		}
		
		TriangleArray myTris = new TriangleArray(myCoords.length,GeometryArray.COORDINATES | GeometryArray.NORMALS);
		myTris.setCoordinates( 0, myCoords );
		myTris.setNormal(0, new Vector3f(0,0,1.0f));
		Shape3D myShape = new Shape3D( myTris, app );
		BranchGroup bGroup = new BranchGroup();
		bGroup.addChild(myShape);
		
		Transform3D trans2 = new Transform3D();
		
		
		float xOffset = (float)(this.getStartPoint()%xres)* tileWidth + tileWidth/2.0f;
		float yOffset = (float)(this.getStartPoint()/xres)* tileWidth + tileWidth/2.0f;
		
		//transformation.setRotation(new AxisAngle4d(new Vector3d(0.0f,0.0f,1.0f),1.0));
		trans2.setTranslation(new Vector3d(-xOffset,-yOffset,0.0f));
		
		
		tPath = new TransformGroup();
		
		tPath.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		tPath.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		
		tPath.setTransform(trans2);
		tPath.addChild(bGroup);
		
		
		
		markerGroup = new BranchGroup();
		
		Point3f a[] = this.getWayPoints();

		Enemy e1 = new Enemy(new RedAppearance(),7000);
		e1.name = "Enemy 1";
		
		GameController.getInstance().enemyList.add(e1);
	
		//AnimationPosition anim = new AnimationPosition(e1);
		e1.setPath(this.getWayPoints());
		tPath.addChild(e1.animation);
		
		
		Enemy e2 = new Enemy(new BlueAppearance(),15000);

		GameController.getInstance().enemyList.add(e2);
		//AnimationPosition anim2 = new AnimationPosition(e2);
		e2.setPath(this.getWayPoints());
		e2.name = "Enemy 2";
		tPath.addChild(e2.animation);
		
		Enemy e3 = new Enemy(new GreenAppearance(),20000);

		GameController.getInstance().enemyList.add(e3);
		//AnimationPosition anim3 = new AnimationPosition(e3);
		e3.setPath(this.getWayPoints());
		e3.name = "Enemy 3";
		tPath.addChild(e3.animation);
		
		
		markerGroup.addChild(tPath);
	
		
		transGroup.addChild(markerGroup);
		
		
	
	}
	
	public void attachEnemy(Enemy e)
	{
		e.setPath(this.getWayPoints());
		tPath.addChild(e.animation);
	}
	
	public void setPath(int x, int y)
	{
		this.map[x+y*xres]=1;
	}
	public void removePath(int x, int y)
	{
		this.map[x+y*xres]=0;
	}
	
	public int getPath(int x, int y)
	{
		return this.map[x+y*xres];
	}
	
	private int getStartPoint()
	{
		for(int i=0;i<map.length;i++)
		{
			if(this.map[i]==2) return i;
		}
		//nothing found: return 0;
		return 0;
	}
	
	private int getNumberOfWayPoints()
	{
		int num=0;
		for(int i=0;i<this.map.length;i++)
		{
			if(this.map[i]!=0)num++;
		}
		return num;
	}
	
	
	
	
	/***
	 * Extract the waypoints from the map
	 * @return Array of Waypoints
	 */
	public Point3f[] getWayPoints()
	{
		LinkedList<Point3f> wayPoints = new LinkedList<Point3f>();
		 
		
		int NORTH = 0;
		int EAST = 1;
		int SOUTH = 2;
		int WEST = 3;
		
		int direction = NORTH;
		
		int current = 1;
		
		int id = getStartPoint();
		int x = id%xres;
		int y = id/xres;
		System.out.println("Point "+x+" "+y);
		
		wayPoints.add(getPointFromCoord(x,y));
		
		while(current != 3)
		{
			if(direction == NORTH)
			{
				
				if(outOfBounds(x, y-1)==true||getPath(x, y-1)==0)
				{
					if(outOfBounds(x-1, y)||getPath(x-1, y)==0)
					{
						direction = EAST;
					}
					else
					{
						direction = WEST;
					}

					System.out.println("Point "+x+" "+y);
					wayPoints.add(getPointFromCoord(x,y));
				}
				else
				{
					y--;
					
				}
				
			}
			
			else if(direction == WEST)
			{
				if(outOfBounds(x-1, y)==true||getPath(x-1, y)==0)
				{
					if(outOfBounds(x, y-1)||getPath(x, y-1)==0)
					{
						direction = SOUTH;
					}
					else
					{
						direction = NORTH;
					}

					System.out.println("Point "+x+" "+y);
					wayPoints.add(getPointFromCoord(x,y));
					
					
				}
				else x--;
					
				
			}
			else if(direction == SOUTH)
			{
				if(outOfBounds(x, y+1)==true||getPath(x, y+1)==0)
				{
					if(outOfBounds(x-1, y)||getPath(x-1, y)==0)
					{
						direction = EAST;						
					}
					else
					{
						direction = WEST;
					}
					System.out.println("Point "+x+" "+y);
					wayPoints.add(getPointFromCoord(x,y));
					
					
				}
				else y++;
					
				
			}
			else if(direction == EAST)
			{
				if(outOfBounds(x+1, y)==true||getPath(x+1, y)==0)
				{
					if(outOfBounds(x, y-1)||getPath(x, y-1)==0)
					{
						direction = SOUTH;						
					}
					else
					{
						direction = NORTH;
					}
					System.out.println("Point "+x+" "+y);
					wayPoints.add(getPointFromCoord(x,y));
					
					
				}
				else x++;
					
				
			}
			current = getPath(x, y);
			
		}
		//Add Last Point;
		
		wayPoints.add(getPointFromCoord(x,y));
		Point3f returnArray[] = new Point3f[wayPoints.size()];
		for(int i=0; i<wayPoints.size();i++)
		{
			returnArray[i] = wayPoints.get(i);
		}
		return returnArray;
	
	}
	private Point3f getPointFromCoord(int x, int y)
	{
		return new Point3f((float)x*tileWidth+tileWidth*0.5f,(float)y*tileWidth+tileWidth*0.5f,0.0f);
	}
	
	 private boolean outOfBounds(int x, int y)
	 {
		 if(x<0 || x>xres) return true;
		 else if(y<0|| y>yres) return true;
		 else return false;
		
		 
	 }
	
}
