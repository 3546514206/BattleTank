package core;

public abstract class solidObject implements model{
	//	the reference point of the model (in world coordinate)
	protected vector start;
	
	//the refrerence axis of the model  (in world coordinate)
	protected vector iDirection, jDirection, kDirection;
	 
	//A rough cuboid boundary of this model, contains 5 polygons
	protected polygon3D[] boundary;
	
	//A rough 2D boundary of this model
	protected rectangle2D boundary2D; 
	
	//whether this model need to be sent to drawing pipeline
	protected boolean visible;
	
	//the centre of the model in world coordinate
	protected vector centre;
	
	//the centre of the model in camera coordinate
	protected vector tempCentre = new vector(0,0,0);
	
	//the current life span of the object, the object is expired if the value is less than 0
	protected int lifeSpan;
	
	//hitpoint of the object
	protected int HP;
	
	//a flag that shows whether the model has blocked the path of other model
	protected boolean unstuck;
	
	//type of the model
	//type 1 = boundary generally has a cubic shape, type 2 = other
	protected int modelType;
	
	//get a rough 3D boundary of the model in camera coordinate
	public final polygon3D[] getBoundary(){
		return boundary;
	}

	//get centre of this model in camera coordinate
	public final vector getCentre(){
		return tempCentre;
	}
	
	//return centre in world coordinate
	public final vector getRealCentre(){
		return centre;
	}

	public final int getType(){
		return modelType;
	}
	
	public final void unstuck(){
		unstuck = true;
		
	}
	
	//Create a rough 3D cuboid boundary for this model.
	//It consists 5 polygons, they are facing front, right, back, left, top respectively. (no bottom)
	public final void makeBoundary(double l, double h, double w){
		boundary = new polygon3D[5];
		vector[] front = new vector[]{put(l, h, w), put(-l, h, w), put(-l, -h, w), put(l, -h, w)};
		boundary[0] = new polygon3D(front, null, null, null, null, 0,0,0);
		vector[] right = new vector[]{put(l, h, -w), put(l, h, w), put(l, -h, w), put(l, -h, -w)};
		boundary[1] = new polygon3D(right, null, null, null, null, 0,0,0);
		vector[] back = new vector[]{put(-l, h, -w), put(l, h, -w), put(l, -h, -w), put(-l, -h, -w)};
		boundary[2] = new polygon3D(back, null, null, null, null, 0,0,0);
		vector[] left = new vector[]{put(-l, h, w), put(-l, h, -w), put(-l, -h, -w), put(-l, -h, w)};
		boundary[3] = new polygon3D(left, null, null, null, null, 0,0,0);
		vector[] top = new vector[]{put(-l, h, w), put(l, h, w), put(l, h, -w), put(-l, h, -w)};
		boundary[4] = new polygon3D(top, null, null, null, null, 0,0,0);
	}

	//if all the boundary polygons are rendered not visible,
	//then the model will not be drawn
	public final boolean testVisibility(){
		for(int i = 0; i < boundary.length; i++){
			if(boundary[i].visible)
				return true;
		}
		return false;
	}
	
	//the centre of the model is cauculated by averaging the centres
	//of the first 4 polygons from the boundary
	public final void findCentre(){
		centre = new vector(0,0,0);
		for(int i = 0; i < 4; i++)
			centre.add(boundary[i].centre);
		centre.scale(1.0/4);
	}
	
	//create a arbitrary vertex
	public final vector put(double i, double j, double k){
		vector temp = start.myClone();
		temp.add(iDirection, i);
		temp.add(jDirection, j);
		temp.add(kDirection, k);
		return temp;
	}
	
	//change the 3d geometry of a vertex
	public final void change(double i, double j, double k, vector v){
		v.set(start);
		v.add(iDirection, i);
		v.add(jDirection, j);
		v.add(kDirection, k);
	}
	
	public final double getZDepth(){
		return tempCentre.z;
	}
	
	//	return the lifeSpan
	public final int getLifeSpan(){
		return lifeSpan;
	}
	
	//damage the object. (ie, reduce its hitpoint)
	public void damage(int damagePoint){
		HP-=damagePoint;
	}
	
}