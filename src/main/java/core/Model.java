package core;
//the interface which represents 3D models

public interface Model {
	//update the model, perform AI actions if there is any
	public void update();
	
	//get a rough 3D boundary of the model in camera coordinate
	public Polygon3D[] getBoundary();
	
	//get  2D boundary of this model (will be used for collision detection)
	public Rectangle2D getBoundary2D();
	
	//get centre of this model in camera coordinate
	public Vector getCentre();
	
	//get centre of this model in world coordinate
	public Vector getRealCentre();
	
	//draw the polygons of the model
	public void draw();
	
	//return the type of the model
	//To be more specific:
	//type 1 = player tank
	//type 2 = enemy tank
	//type 3 = rocks, and most other object that will interact with projectiles.
	//type 4 = projectiles
	//type 5 = powerups
	//type 6 = walls
	//type 7 = trees
	public int getType();
	
	//get the z value of the centre vector
	public double getZDepth();
	
	//get the current life span of the model
	public int getLifeSpan();
	
	//damage the object. (ie, reduce its hitpoint)
	public void damage(int damagePoint);
	
	//give way when it has blocked other model
	public void unstuck();
}