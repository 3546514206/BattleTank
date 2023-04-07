package core;
//Energy fence
public class fence extends solidObject{
	
	//the polygons of the model
	private polygon3D[] polygons; 
	
	//0 = vertical   1 = horizontal 
	public int orientation;
	
	public fence(double x, double y, double z, int orientation){
		start = new vector(x,y,z);
		iDirection = new vector(1,0,0);
		jDirection = new vector(0,1,0);
		kDirection = new vector(0,0,1);
		
		if(orientation == 0){
			iDirection.rotate_XZ(90);
			kDirection.rotate_XZ(90);
		}
		
		//3D boundary of this model has a cubic shape (ie l = w)
		modelType = 6;  
		makeBoundary(0.125, 0.25, 0.125);
		
		//create 2D boundary
		if(orientation == 0){
			boundary2D = new rectangle2D(x - 0.06, z + 0.17, 0.12, 0.34);
			obstacleMap.registerObstacle2(this, (int)(x*4) + (129-(int)(z*4))*80);
		
		}
		
		if(orientation == 1){
			boundary2D = new rectangle2D(x - 0.17, z + 0.06, 0.34, 0.12);
			obstacleMap.registerObstacle2(this, (int)(x*4) + (129-(int)(z*4))*80);
		
		}
		
		//find centre of the model in world coordinate
		findCentre();
		
		makePolygons();
	}
	
	//	Construct polygons for this model.
	//The polygon data is hard-coded here
	public void makePolygons(){
		vector[] v;
	
		polygons = new polygon3D[2];
		v = new vector[]{put(-0.125, 0.14, 0), put(0.125, 0.14, 0), put(0.125, -0.1, 0) ,put(-0.125, -0.1, 0)};
		polygons[0] = new polygon3D(v, new vector(-1, -1, 1), new vector(1, -1, 1), new vector(-1, -1, -1), null, 1, 1, 9);
		
		v = new vector[]{put(-0.125, -0.1, 0), put(0.125, -0.1, 0), put(0.125, 0.14, 0),put(-0.125, 0.14, 0)};
		polygons[1] = new polygon3D(v, new vector(-1, -1, 1), new vector(1, -1, 1), new vector(-1, -1, -1), null, 1, 1, 9);
	}
	
	//return the 2D boundary of this model
	public rectangle2D getBoundary2D(){
		return boundary2D;
	}
	
//	update the model
	public void update(){
		//find centre in camera coordinate
		tempCentre.set(centre);
		tempCentre.y = -1;
		tempCentre.subtract(camera.position);
		tempCentre.rotate_XZ(camera.XZ_angle);
		tempCentre.rotate_YZ(camera.YZ_angle);
		tempCentre.updateLocation();
		
		//test whether the model is visible by comparing the 2D position of its centre point and the screen area
		if(tempCentre.z < 0.5 || tempCentre.screenY < -30 || tempCentre.screenX < -400 || tempCentre.screenX >800){
			visible = false;
			return;
		}
		visible = true;
		
		modelDrawList.register(this);
		
		//update boundary
		for(int i = 0; i < 5; i++)
			boundary[i].update();
		
		//update polygons
		for(int i = 0; i < polygons.length; i++){
			polygons[i].update();
		}
	}
	
	public void destory(){
		int position = (int)(start.x*4) + (129-(int)(start.z*4))*80;
		obstacleMap.removeObstacle2(position);
	}
	
	public void draw(){
		if(visible){
			for(int i = 0; i < polygons.length; i++){
				polygons[i].draw();
			}
		}
	}
}
