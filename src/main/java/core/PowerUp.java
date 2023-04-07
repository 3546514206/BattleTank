package core;
//model: powerups
public class powerUp extends solidObject{
	
	public int type;
	
	public polygon3D[] polygons;
	
	public int theta;
	
	public polygon3D shadow;
	
	public vector displacement;
	
	public powerUp(double x, double y, double z, int type){
		//type 1 = shell box
		//type 2 = rocket box
		//type 3 = railgun slug box
		//type 4 = nuke ammo box
		this.type = type;
		
		//define the center point of this model
		start = new vector(x,y,z);
		
		iDirection = new vector(0.65,0,0);
		jDirection = new vector(0,0.65,0);
		kDirection = new vector(0,0,0.65);
		
		
		
		//boundary of this model has a cubic shape (ie l = w)
		modelType = 5;  
		makeBoundary(0.01, 0.025, 0.01);
		
		//create 2D boundary
		boundary2D = new rectangle2D(x - 0.01, z + 0.01, 0.02, 0.02);	
		
		
		//find centre of the model in world coordinate
		findCentre();
		
		lifeSpan = 1;
		
		makeBody();
		
		displacement = new vector(0,0,0);
	}
	
	//create polygons``
	public void makeBody(){
		polygons = new polygon3D[10];
		vector[] v;
		
		int textureIndexA = 0, textureIndexB = 0;
		if(type == 1){
			textureIndexA = 33;
			textureIndexB = 34;
		}
		
		if(type == 2){
			textureIndexA = 31;
			textureIndexB = 32;
		}
		
		if(type == 3){
			textureIndexA = 38;
			textureIndexB = 39;
		}
		
		if(type == 4){
			textureIndexA = 48;
			textureIndexB = 49;
		}
		
		v = new vector[]{put(-0.07, 0, 0.05), put(0.07, 0, 0.05), put(0.07, 0, -0.05), put(-0.07, 0, -0.05)};
		polygons[0] = new polygon3D(v, v[0], v[1], v [3], main.textures[textureIndexB], 1,1,6);
		
		v = new vector[]{put(-0.04, 0,0.04),put(0.04, 0,0.04), put(0.04, 0,-0.04), put(-0.04, 0,-0.04)};
		polygons[1] = new polygon3D(v, v[0], v[1], v [3], main.textures[textureIndexA], 1,1,6);
		
		v = new vector[]{put(-0.07, 0, -0.05), put(0.07, 0, -0.05), put(0.07, -0.01, -0.06), put(-0.07, -0.01, -0.06)};
		polygons[2] = new polygon3D(v, v[0], v[1], v [3], main.textures[textureIndexB], 1,1,6);
		
		v = new vector[]{put(-0.07, -0.01, 0.06), put(0.07, -0.01, 0.06), put(0.07, 0, 0.05), put(-0.07, 0, 0.05)};
		polygons[3] = new polygon3D(v, v[0], v[1], v [3], main.textures[textureIndexB], 1,1,6);
		
		v = new vector[]{put(-0.07, -0.01, -0.06), put(0.07, -0.01, -0.06), put(0.07, -0.1, -0.06),  put(-0.07, -0.1, -0.06)};
		polygons[4] = new polygon3D(v, v[0], v[1], v [3], main.textures[textureIndexB], 1,1,6);
		
		v = new vector[]{put(-0.07, -0.1, 0.06), put(0.07, -0.1, 0.06), put(0.07, -0.01, 0.06),  put(-0.07, -0.01, 0.06)};
		polygons[5] = new polygon3D(v, v[0], v[1], v [3], main.textures[textureIndexB], 1,1,6);
		
		v = new vector[]{put(-0.07, 0, 0.05), put(-0.07, 0, -0.05), put(-0.07, -0.01, -0.06), put(-0.07, -0.1, -0.06), put(-0.07, -0.11, -0.05), put(-0.07, -0.11, 0.05), put(-0.07, -0.1, 0.06), put(-0.07, -0.01, 0.06)};
		polygons[6] = new polygon3D(v, v[0], v[1], v [3], main.textures[textureIndexB], 1,1,6);
		
		v = new vector[]{put(0.07, -0.01, 0.06), put(0.07, -0.1, 0.06), put(0.07, -0.11, 0.05), put(0.07, -0.11, -0.05), put(0.07, -0.1, -0.06), put(0.07, -0.01, -0.06), put(0.07, 0, -0.05), put(0.07, 0, 0.05)};
		polygons[7] = new polygon3D(v, v[0], v[1], v [3], main.textures[textureIndexB], 1,1,6);
	
		v = new vector[]{put(-0.07, -0.01, 0.04), put(-0.07, -0.01, -0.04), put(-0.07, -0.09, -0.04), put(-0.07, -0.09, 0.04)};
		polygons[8] = new polygon3D(v, v[0], v[1], v [3], main.textures[textureIndexA], 1,1,6);
		
		v = new vector[]{put(0.07, -0.09, 0.04), put(0.07, -0.09, -0.04), put(0.07, -0.01, -0.04), put(0.07, -0.01, 0.04)};
		polygons[9] = new polygon3D(v, v[0], v[1], v [3], main.textures[textureIndexA], 1,1,6);
		
		double temp = start.y;
		start.y = -1;
		
		kDirection.scale(0.8);
		iDirection.scale(0.9);
		iDirection.rotate_XZ(90);
		kDirection.rotate_XZ(90);
		start.add(-0.05, 0, -0.05);
		v = new vector[]{put(-0.17, 0, 0.17), put(0.17, 0, 0.17), put(0.17, 0, -0.17),put(-0.17, 0, -0.17)};
		shadow = new polygon3D(v, v[0], v[1],v[3], main.textures[14], 1,1,2);
		start.y = temp;
		start.add(0.05, 0, 0.05);
	}
	
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
		
		theta+=9;
		theta = theta%360;
		double height = 0.006* gameData.sin[theta];
		
		for(int i = 0; i < polygons.length; i++){
			polygons[i].origin.subtract(start);
			polygons[i].origin.rotate_XZ(3);
			polygons[i].origin.add(start);
			polygons[i].origin.y+=height;

			polygons[i].rightEnd.subtract(start);
			polygons[i].rightEnd.rotate_XZ(3);
			polygons[i].rightEnd.add(start);
			polygons[i].rightEnd.y+=height;

			polygons[i].bottomEnd.subtract(start);
			polygons[i].bottomEnd.rotate_XZ(3);
			polygons[i].bottomEnd.add(start);
			polygons[i].bottomEnd.y+=height;
			
			for(int j = 0; j < polygons[i].vertex3D.length; j++){
				
			
				polygons[i].vertex3D[j].subtract(start);
				polygons[i].vertex3D[j].rotate_XZ(3);
				polygons[i].vertex3D[j].add(start);
				polygons[i].vertex3D[j].y+=height;
			}
			
			polygons[i].findRealNormal();
			polygons[i].findDiffuse();

			polygons[i].update();
		}
		
		
		//update boundary
		for(int i = 0; i < 5; i++)
			boundary[i].update();
		
		
		displacement.set(-0.003 * (gameData.sin[theta]), 0, -0.003* (gameData.sin[theta]));
		
		
		
		//update Shadow;
		shadow.realCentre.add(displacement);
		
		shadow.origin.subtract(shadow.realCentre);
		shadow.origin.rotate_XZ(3);
		shadow.origin.add(shadow.realCentre);
		shadow.origin.add(displacement);
		
		shadow.rightEnd.subtract(shadow.realCentre);
		shadow.rightEnd.rotate_XZ(3);
		shadow.rightEnd.add(shadow.realCentre);
		shadow.rightEnd.add(displacement);

		shadow.bottomEnd.subtract(shadow.realCentre);
		shadow.bottomEnd.rotate_XZ(3);
		shadow.bottomEnd.add(shadow.realCentre);
		shadow.bottomEnd.add(displacement);
		
		for(int i = 0; i < 4; i++){
			shadow.vertex3D[i].subtract(shadow.realCentre);
			shadow.vertex3D[i].rotate_XZ(3);
			shadow.vertex3D[i].add(shadow.realCentre);
			shadow.vertex3D[i].add(displacement);
			
		}
		
		shadow.update();
		if(shadow.visible)
			rasterizer.rasterize(shadow);
	
		
		
	}
	
	public rectangle2D getBoundary2D(){
		
		return boundary2D;
	}
	
	public void draw(){
		for(int i = 0; i < polygons.length; i++){
			polygons[i].draw();
		}
	}

}
