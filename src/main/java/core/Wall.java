package core;

public class wall extends solidObject{
	
	//the polygons of the model
	private polygon3D[] polygons; 
	
	//the shadow map generated by the model
	public polygon3D shadow, shadow2;
	
	//orientation of the wall
	//0 = vertical   1 = horizontal   2 = up-right    3 = up-left  4 = down-right  5 = down left , 6 shadowed wall vertical 6 shadowed wall horizontal
	public int orientation;
	
	//whether the wall has an open end
	public int open;
	
	public wall(double x, double y, double z, int orientation, int open){
		start = new vector(x,y,z);
		iDirection = new vector(1,0,0);
		jDirection = new vector(0,1,0);
		kDirection = new vector(0,0,1);
		
		this.orientation = orientation;
		this.open = open;
		
		//3D boundary of this model has a cubic shape (ie l = w)
		modelType = 6;  
		makeBoundary(0.125, 0.25, 0.125);
		
		//create 2D boundary
		if(orientation == 0 || orientation ==6){
			boundary2D = new rectangle2D(x - 0.09, z + 0.17, 0.18, 0.34);
			obstacleMap.registerObstacle2(this, (int)(x*4) + (129-(int)(z*4))*80);
		
		}
		
		if(orientation == 1 || orientation ==7){
			boundary2D = new rectangle2D(x - 0.17, z + 0.09, 0.34, 0.18);
			obstacleMap.registerObstacle2(this, (int)(x*4) + (129-(int)(z*4))*80);
		
		}
		
		if(orientation == 2 || orientation == 3 || orientation == 4 || orientation == 5){
			boundary2D = new rectangle2D(x - 0.09, z + 0.09, 0.18, 0.18);
			obstacleMap.registerObstacle2(this, (int)(x*4) + (129-(int)(z*4))*80);
		}
		
		if(orientation == 8 || orientation == 9 || orientation == 10 || orientation == 11){
			boundary2D = new rectangle2D(x - 0.09, z + 0.09, 0.16, 0.18);
			obstacleMap.registerObstacle2(this, (int)(x*4) + (129-(int)(z*4))*80);
		}
		
		
		
		//find centre of the model in world coordinate
		findCentre();
		
		makePolygons();
	}
	
	
	//Construct polygons for this model.
	//The polygon data is hard-coded here
	public void makePolygons(){
		vector[] v;
		
		
		if(orientation == 0){
			if(open != 0)
				polygons = new polygon3D[4];
			else
				polygons = new polygon3D[3];
		
			v = new vector[]{put(-0.04, 0.14, 0.125), put(-0.04, 0.14, -0.125), put(-0.04, -0.1, -0.125), put(-0.04, -0.1, 0.125)};
			polygons[0] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(0.04, -0.1, 0.125), put(0.04, -0.1, -0.125), put(0.04, 0.14, -0.125),put(0.04, 0.14, 0.125)};
			polygons[1] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
		
			v = new vector[]{put(-0.04, 0.14, 0.125), put(0.04, 0.14, 0.125), put(0.04, 0.14, -0.125), put(-0.04, 0.14, -0.125)};
			polygons[2] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 0.3,1,6);
			
			if(open == 1){
				v = new vector[]{put(0.04, 0.14, 0.125), put(-0.04, 0.14, 0.125), put(-0.04, -0.1, 0.125), put(0.04, -0.1, 0.125)};
				polygons[3] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			}else if(open == 2){
				v = new vector[]{put(0.04, -0.1, -0.125), put(-0.04, -0.1, -0.125), put(-0.04, 0.14, -0.125), put(0.04, 0.14, -0.125)};
				polygons[3] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			}
			
			start.add(-0.06,0,-0.07);
			iDirection.scale(1.5);
			kDirection.scale(1.6);
			v = new vector[]{put(-0.125, -0.1, 0.125), put(0.125, -0.1, 0.125),put(0.125, -0.1, -0.125),put(-0.125, -0.1, -0.125)};
			shadow = new polygon3D(v, v[0], v[1],v[3], main.textures[42], 1,1,2);
		}
		
		if(orientation == 1){
			if(open != 0)
				polygons = new polygon3D[4];
			else
				polygons = new polygon3D[3];
			
			v = new vector[]{put(-0.125, 0.14, -0.04), put(0.125, 0.14, -0.04), put(0.125, -0.1, -0.04), put(-0.125, -0.1, -0.04)};
			polygons[0] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			v = new vector[]{put(-0.125, -0.1, 0.04), put(0.125, -0.1, 0.04), put(0.125, 0.14, 0.04), put(-0.125, 0.14, 0.04)};
			polygons[1] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			v = new vector[]{put(-0.125, 0.14, 0.04), put(0.125, 0.14, 0.04), put(0.125, 0.14, -0.04), put(-0.125, 0.14, -0.04)};
			polygons[2] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			if(open == 1){
				v = new vector[]{put(0.125, 0.14, -0.04), put(0.125, 0.14, 0.04), put(0.125, -0.1, 0.04), put(0.125, -0.1, -0.04)};
				polygons[3] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			}if(open == 2){
				v = new vector[]{put(-0.125, -0.1, -0.04), put(-0.125, -0.1, 0.04), put(-0.125, 0.14, 0.04), put(-0.125, 0.14, -0.04)};
				polygons[3] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			}
			
			
			start.add(-0.06,0,-0.052);
			iDirection.scale(2);
			kDirection.scale(1.2);
			v = new vector[]{put(-0.125, -0.1, 0.125), put(0.125, -0.1, 0.125),put(0.125, -0.1, -0.125),put(-0.125, -0.1, -0.125)};
			shadow = new polygon3D(v, v[0], v[1],v[3], main.textures[44], 1,1,2);
		}
		
		if(orientation == 2){
			polygons = new polygon3D[6];
			
			v = new vector[]{put(-0.125, 0.14, -0.04), put(-0.04, 0.14, -0.04), put(-0.04, -0.1, -0.04), put(-0.125, -0.1, -0.04)};
			polygons[0] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 0.5,1,6);
			
			v = new vector[]{put(-0.04, 0.14, -0.04), put(-0.04, 0.14, -0.125), put(-0.04, -0.1, -0.125), put(-0.04, -0.1, -0.04)};
			polygons[1] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 0.5,1,6);
			
			v = new vector[]{put(0.04, 0.14, -0.125), put(0.04, 0.14, 0.04), put(0.04, -0.1, 0.04), put(0.04, -0.1, -0.125)};
			polygons[2] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(0.04, 0.14, 0.04), put(-0.125, 0.14, 0.04), put(-0.125, -0.1, 0.04), put(0.04, -0.1, 0.04)};
			polygons[3] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(-0.125, 0.14, 0.04), put(0.04, 0.14, 0.04), put(0.04, 0.14, -0.04), put(-0.125, 0.14, -0.04)};
			polygons[4] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
		
			v = new vector[]{put(-0.04, 0.14, -0.04), put(0.04, 0.14, -0.04), put(0.04, 0.14, -0.125), put(-0.04, 0.14, -0.125)};
			polygons[5] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			start.add(-0.06,0,-0.15);
			iDirection.scale(1.5);
			kDirection.scale(1.6);
			v = new vector[]{put(-0.125, -0.1, 0.125), put(0.125, -0.1, 0.125),put(0.125, -0.1, -0.125),put(-0.125, -0.1, -0.125)};
			shadow = new polygon3D(v, v[0], v[1],v[3], main.textures[42], 1,1,2);
			
			start.add(0.06,0,0.15);
			iDirection.scale(1.0/1.5);
			kDirection.scale(1.0/1.6);
			start.add(-0.14,0,-0.052);
			iDirection.scale(2);
			kDirection.scale(1.2);
			v = new vector[]{put(-0.125, -0.1, 0.125), put(0.125, -0.1, 0.125),put(0.125, -0.1, -0.125),put(-0.125, -0.1, -0.125)};
			shadow2 = new polygon3D(v, v[0], v[1],v[3], main.textures[44], 1,1,2);
			
		}
		
		if(orientation == 3){
			polygons = new polygon3D[7];
			
			v = new vector[]{put(0.04, 0.14, -0.04), put(0.125, 0.14, -0.04), put(0.125, -0.1, -0.04), put(0.04, -0.1, -0.04)};
			polygons[2] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 0.5,1,6);
			
			v = new vector[]{put(0.04, 0.14, -0.125), put(0.04, 0.14, -0.04), put(0.04, -0.1, -0.04), put(0.04, -0.1, -0.125)};
			polygons[0] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 0.5,1,6);
			
			v = new vector[]{put(0.04, 0.14, -0.125), put(0.04, 0.14, -0.04), put(0.04, -0.11, -0.04), put(0.04, -0.11, -0.125)};
			polygons[1] = new polygon3D(v, put(0.04, 0.14, -0.15), v[1], put(0.04, -0.11, -0.15), main.textures[45], 1,1,3);
			
			
			
			v = new vector[]{put(-0.04, 0.14, 0.04), put(-0.04, 0.14, -0.125), put(-0.04, -0.1, -0.125), put(-0.04, -0.1, 0.04)};
			polygons[3] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(0.125, 0.14,0.04), put(-0.04, 0.14,0.04), put(-0.04, -0.1,0.04), put(0.125, -0.1,0.04)};
			polygons[4] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(-0.04, 0.14, 0.04), put(0.125, 0.14, 0.04), put(0.125, 0.14, -0.04), put(-0.04, 0.14, -0.04)};
			polygons[5] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
		
			v = new vector[]{put(-0.04, 0.14, -0.04), put(0.04, 0.14, -0.04), put(0.04, 0.14, -0.125), put(-0.04, 0.14, -0.125)};
			polygons[6] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			start.add(-0.06,0,-0.16);
			iDirection.scale(1.5);
			kDirection.scale(1.6);
			v = new vector[]{put(-0.125, -0.1, 0.125), put(0.125, -0.1, 0.125),put(0.125, -0.1, -0.125),put(-0.125, -0.1, -0.125)};
			shadow = new polygon3D(v, v[0], v[1],v[3], main.textures[42], 1,1,2);
			
		
			
		}
		
		if(orientation == 4){
			polygons = new polygon3D[7];
			
			v = new vector[]{put(-0.04, 0.14, 0.04), put(-0.125, 0.14, 0.04), put(-0.125, -0.1, 0.04), put(-0.04, -0.1, 0.04)};
			polygons[0] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(-0.04, 0.14, 0.04), put(-0.125, 0.14, 0.04), put(-0.125, -0.1, 0.04), put(-0.04, -0.1, 0.04)};
			polygons[1] = new polygon3D(v, put(-0.025, 0.145, 0.04), put(-0.13, 0.145, 0.04), put(-0.025, -0.115, 0.04), main.textures[46], 1,1,3);
		
			
			v = new vector[]{put(-0.04, 0.14, 0.125), put(-0.04, 0.14, 0.04),put(-0.04, -0.1, 0.04), put(-0.04, -0.1, 0.125)};
			polygons[2] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(-0.125, 0.14, -0.04), put(0.04, 0.14, -0.04), put(0.04, -0.1, -0.04), put(-0.125, -0.1, -0.04)};
			polygons[3] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(0.04, 0.14, -0.04), put(0.04, 0.14, 0.125), put(0.04, -0.1, 0.125), put(0.04, -0.1, -0.04)};
			polygons[4] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(-0.125, 0.14, 0.04), put(-0.04, 0.14, 0.04), put(-0.04, 0.14, -0.04), put(-0.125, 0.14, -0.04)};
			polygons[5] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(-0.04, 0.14, 0.125), put(0.04, 0.14, 0.125), put(0.04, 0.14, -0.04), put(-0.04, 0.14, -0.04)};
			polygons[6] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			start.add(-0.145,0,-0.052);
			iDirection.scale(2);
			kDirection.scale(1.2);
			v = new vector[]{put(-0.125, -0.1, 0.125), put(0.125, -0.1, 0.125),put(0.125, -0.1, -0.125),put(-0.125, -0.1, -0.125)};
			shadow = new polygon3D(v, v[0], v[1],v[3], main.textures[44], 1,1,2);
			
			
		
		}		
		
		if(orientation == 5){
			polygons = new polygon3D[6];
			
			start.add(0.045,0,0);
			v = new vector[]{put(-0.005, 0.14, 0.04),put(-0.005, 0.14, 0.125),put(-0.005, -0.1, 0.125), put(-0.005, -0.1, 0.04)};
			polygons[0] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(0.08, 0.14, 0.04), put(-0.005, 0.14, 0.04), put(-0.005, -0.1, 0.04), put(0.08, -0.1, 0.04)};
			polygons[1] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(-0.085, 0.14, 0.125), put(-0.085, 0.14, -0.04),put(-0.085, -0.1, -0.04), put(-0.085, -0.1, 0.125)};
			polygons[2] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(-0.085, 0.14, -0.04), put(0.08, 0.14, -0.04), put(0.08, -0.1, -0.04), put(-0.085, -0.1, -0.04)};
			polygons[3] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(-0.085, 0.14, 0.125), put(-0.005, 0.14, 0.125), put(-0.005, 0.14, -0.04),put(-0.085, 0.14, -0.04)};
			polygons[4] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(-0.005, 0.14, 0.04), put(0.08, 0.14, 0.04), put(0.08, 0.14, -0.04), put(-0.005, 0.14, -0.04)};
			polygons[5] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
		
		
			start.add(-0.00,0,0.03);
			iDirection.scale(1.9);
			kDirection.scale(2);
			v = new vector[]{put(-0.125, -0.1, 0.125), put(0.125, -0.1, 0.125),put(0.125, -0.1, -0.125),put(-0.125, -0.1, -0.125)};
			shadow = new polygon3D(v, v[0], v[1],v[3], main.textures[43], 1,1,2);
		}
		
		if(orientation == 6){
			polygons = new polygon3D[4];
			
			v = new vector[]{put(-0.04, 0.14, 0.125), put(-0.04, 0.14, -0.125), put(-0.04, -0.1, -0.125), put(-0.04, -0.1, 0.125)};
			polygons[0] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(0.04, -0.1, 0.125), put(0.04, -0.1, -0.125), put(0.04, 0.14, -0.125),put(0.04, 0.14, 0.125)};
			polygons[1] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
		
			v = new vector[]{put(-0.04, 0.14, 0.125), put(0.04, 0.14, 0.125), put(0.04, 0.14, -0.125), put(-0.04, 0.14, -0.125)};
			polygons[2] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 0.3,1,6);
			
			v = new vector[]{put(0.04, -0.04, 0.09),put(0.04, -0.04, 0.125), put(0.04, -0.1, 0.125), put(0.04, -0.1, 0.09)};
			polygons[3] = new polygon3D(v, put(0.04, -0.025, 0.08), put(0.04, -0.025, 0.128), put(0.04, -0.11, 0.08), main.textures[45], 1,1,3);
			
			start.add(-0.06,0,-0.07);
			iDirection.scale(1.5);
			kDirection.scale(1.6);
			v = new vector[]{put(-0.125, -0.1, 0.125), put(0.125, -0.1, 0.125),put(0.125, -0.1, -0.125),put(-0.125, -0.1, -0.125)};
			shadow = new polygon3D(v, v[0], v[1],v[3], main.textures[42], 1,1,2);
		}
		
		if(orientation == 7){
			polygons = new polygon3D[3];
			
			v = new vector[]{put(-0.125, 0.14, -0.04), put(0.125, 0.14, -0.04), put(0.125, -0.1, -0.04), put(-0.125, -0.1, -0.04)};
			polygons[0] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			v = new vector[]{put(-0.125, -0.1, 0.04), put(0.125, -0.1, 0.04), put(0.125, 0.14, 0.04), put(-0.125, 0.14, 0.04)};
			polygons[1] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			v = new vector[]{put(-0.125, 0.14, 0.04), put(0.125, 0.14, 0.04), put(0.125, 0.14, -0.04), put(-0.125, 0.14, -0.04)};
			polygons[2] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
		
			
				start.add(-0.06,0,-0.052);
			iDirection.scale(2);
			kDirection.scale(1.2);
			v = new vector[]{put(-0.125, -0.1, 0.125), put(0.125, -0.1, 0.125),put(0.125, -0.1, -0.125),put(-0.125, -0.1, -0.125)};
			shadow = new polygon3D(v, v[0], v[1],v[3], main.textures[44], 1,1,2);
		}
		
		if(orientation == 8 || orientation == 9 || orientation == 10 || orientation == 11){
			polygons = new polygon3D[5];
			
			if(orientation == 8){
				centre.x += 0.055;
				centre.z += 0.055;
				start.add(0.055,0,0.055);
			}
			
			if(orientation == 9){
				centre.x -= 0.055;
				centre.z += 0.055;
				start.add(-0.055,0,0.055);
			}
			
			if(orientation == 10){
				centre.x -= 0.055;
				centre.z-= 0.055;
				start.add(-0.055,0,-0.055);
			}
			
			if(orientation == 11){
				centre.x += 0.055;
				centre.z-= 0.055;
				start.add(0.055,0,-0.055);
			}
			
			v = new vector[]{put(-0.07, 0.14,-0.07), put(0.07, 0.14,-0.07), put(0.07, -0.1,-0.07),put(-0.07, -0.1,-0.07)};
			polygons[0] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(-0.07, -0.1,0.07), put(0.07, -0.1,0.07), put(0.07, 0.14,0.07),put(-0.07, 0.14,0.07)};
			polygons[1] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(-0.07, 0.14, 0.07), put(-0.07, 0.14, -0.07) ,put(-0.07, -0.1, -0.07), put(-0.07, -0.1, 0.07)};
			polygons[2] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			v = new vector[]{put(0.07, -0.1, 0.07) ,put(0.07, -0.1, -0.07), put(0.07, 0.14, -0.07) ,put(0.07, 0.14, 0.07)};
			polygons[3] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			
			v = new vector[]{put(-0.07, 0.14, 0.07) ,put(0.07, 0.14, 0.07), put(0.07, 0.14, -0.07) ,put(-0.07, 0.14, -0.07)};
			polygons[4] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
			
			iDirection.scale(1.2);
			kDirection.scale(1.2);
			start.add(-0.04,0,-0.055);
			v = new vector[]{put(-0.125, -0.1, 0.125), put(0.125, -0.1, 0.125),put(0.125, -0.1, -0.125),put(-0.125, -0.1, -0.125)};
			shadow = new polygon3D(v, v[0], v[1],v[3], main.textures[47], 1,1,2);
		}
		
		
		
		
		for(int i = 0; i < polygons.length; i++){
			polygons[i].Ambient_I = 26;
			polygons[i].diffuse_coefficient = 0.7;
			polygons[i].findDiffuse();
			
		}
	}
	
	//return the 2D boundary of this model
	public rectangle2D getBoundary2D(){
		return boundary2D;
	}
	
	//update the model
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
		
		if(shadow != null){
			shadow.update();
			if(shadow.visible){
				rasterizer.rasterize(shadow);
			}
		}
		

		if(shadow2 != null){
			shadow2.update();
			if(shadow2.visible){
				rasterizer.rasterize(shadow2);
			}
		}
	}
	
	public void draw(){
		if(visible){
			for(int i = 0; i < polygons.length; i++){
				polygons[i].draw();
			}
		}
	}
}


