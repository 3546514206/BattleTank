package core;
//The terrain class, all the polygons data are stored in buffers prior to runtime
public class terrain {
	//polygons that represent land
	public polygon3D[] land;
	
	//the polygon that represent the border of the island
	public static polygon3D border;
	
	//polygons that represent water
	public polygon3D[] water;
	public int waveIndex;
	
	//polygons that represent roads
	public polygon3D[] road;
	
	//w floor
	public polygon3D[] concreteFloor;
	
	//objects that scattered around the terrain
	public stone[] underWaterRocks;
	
	public stone[] aboveWaterRocks;

	public stone[] inlandRocks;
	
	public wall[] walls;
	
	public fence[] fences;
	 
	public palmTree[] palmTrees;
	
	public powerPlant[] powerPlants;
	
	public terrain(){
		vector[] v;
		
		//create land part of the terrain
		land = new polygon3D[26];
		v = new vector[]{
			new vector(-0.5,-1, -0.5),	
			new vector(-5,-1, 3),	
			new vector(-9,-1, 15),	
			new vector(-7,-1, 23),	
			new vector(-3,-1, 29),	
			new vector(1,-1, 30),
			new vector(7,-1, 27),
			new vector(9.5,-1, 21),
			new vector(10,-1, 16),
			new vector(7,-1, 8),
			new vector(4,-1, 3),
			new vector(1,-1, -0.5),
		};
		land[0] = new polygon3D(v, new vector(-10, -1, 30), new vector(10, -1, 30), new vector(-10, -1, -0.5), main.textures[0], 20.0, 30.0, 1);
		border = land[0];
		
		v = new vector[]{new vector(-5,-1, 3), new vector(-0.5,-1, -0.5), new vector(-1,-1, -2), new vector(-7.1,-1, 2.7)};
		land[14] = new polygon3D(v, new vector(-4.97,-1, 3), new vector(-0.47,-1, -0.5), new vector(-6.185,-1, 1), main.textures[2], 4, 1.5, 1);
		
		v = new vector[]{new vector(-0.5,-1, -0.5),new vector(1,-1, -0.5), new vector(1.8,-1, -2), new vector(-1,-1, -2)};
		land[15] = new polygon3D(v, new vector(-0.5,-1, -0.48), new vector(1,-1, -0.48), new vector(-0.5,-1, -2.01), main.textures[2], 1.4, 1, 1);
	
		v = new vector[]{new vector(-9,-1, 15), new vector(-5,-1, 3), new vector(-7.1,-1, 2.7), new vector(-11,-1, 15)};
		land[16] = new polygon3D(v, new vector(-8.97,-1, 15), new vector(-4.97,-1, 3), new vector(-10.5,-1, 15), main.textures[2], 8, 0.68, 1);
	
		v = new vector[]{new vector(-7,-1, 23), new vector(-9,-1, 15), new vector(-11,-1, 15), new vector(-9,-1, 23.3)};
		land[17] = new polygon3D(v, new vector(-6.9,-1, 23), new vector(-8.9,-1, 15), new vector(-9,-1, 23.3), main.textures[2], 5, 1, 1);
	
		v = new vector[]{new vector(-3,-1, 29), new vector(-7,-1, 23), new vector(-9,-1, 23.3), new vector(-4.5,-1, 30)};
		land[18] = new polygon3D(v, new vector(-3,-1, 28.9), new vector(-7,-1, 22.9), new vector(-4.5,-1, 30), main.textures[2], 5, 0.98, 1);
	
		v = new vector[]{new vector(1,-1, 30), new vector(-3,-1, 29), new vector(-4.5,-1, 30), new vector(0.979,-1, 31.6)};
		land[19] = new polygon3D(v, new vector(1,-1, 29.9), new vector(-3,-1, 28.9), new vector(0.5,-1, 31.5), main.textures[2], 3.5, 1.1, 1);
		
		v = new vector[]{new vector(7,-1, 27), new vector(1,-1, 30), new vector(0.979,-1, 31.6), new vector(8.44,-1, 27.87)};
		land[20] = new polygon3D(v, new vector(7,-1, 26.9), new vector(1,-1, 29.9), new vector(8,-1, 28.3), main.textures[2], 3.5, 1.1, 1);
		
		v = new vector[]{new vector(9.5,-1, 21), new vector(7,-1, 27), new vector(8.44,-1, 27.87), new vector(11.0732, -1, 21.18)};
		land[21] = new polygon3D(v, new vector(9.48,-1, 21), new vector(6.98,-1, 27), new vector(10.89,-1, 21.65), main.textures[2], 3.5, 0.9, 1);
		
		v = new vector[]{new vector(10,-1, 16), new vector(9.5,-1, 21), new vector(11.0732, -1, 21.18), new vector(11.525,-1, 15.80)};
		land[22] = new polygon3D(v, new vector(9.98,-1, 16), new vector(9.48,-1, 21), new vector(11.599,-1, 16.1), main.textures[2], 3.5, 1.02, 1);
		
		v = new vector[]{new vector(7,-1, 8), new vector(10,-1, 16), new vector(11.525,-1, 15.80), new vector(8.328,-1, 7.03)};
		land[23] = new polygon3D(v, new vector(6.98,-1, 8), new vector(9.98,-1, 16), new vector(8.5,-1, 7.5), main.textures[2], 6, 1, 1);
		
		v = new vector[]{new vector(4,-1, 3), new vector(7,-1, 8), new vector(8.328,-1, 7.03), new vector(5.342,-1, 2.194)};
		land[24] = new polygon3D(v, new vector(3.98,-1, 3), new vector(6.98,-1, 8), new vector(5,-1, 2.4), main.textures[2], 6, 0.72, 1);
		
		v = new vector[]{new vector(1,-1, -0.5), new vector(4,-1, 3), new vector(5.342,-1, 2.194), new vector(1.8,-1, -2)};
		land[25] = new polygon3D(v, new vector(0.98,-1, -0.5), new vector(3.98,-1, 3), new vector(2.2,-1, -1.5), main.textures[2], 3, 0.97, 1);
		
		v = new vector[]{new vector(-1,-1, -2), new vector(1.8,-1, -2), new vector(1.8,-1, -4), new vector(-1,-1, -4)};
		land[1] = new polygon3D(v, new vector(-1,-1, -2), new vector(1,-1, -2), new vector(-1,-1, -4), main.textures[3], 1, 1, 1);
		
		v = new vector[]{new vector(-7.1,-1, 2.7), new vector(-1,-1, -2), new vector(-1,-1, -4), new vector(-7.1,-1, -2)};
		land[2] = new polygon3D(v, new vector(-1,-1, -2), new vector(1,-1, -2), new vector(-1,-1, -4), main.textures[3], 1, 1, 1);
	
		v = new vector[]{new vector(-11,-1, 15), new vector(-7.1,-1, 2.7), new vector(-7.1,-1, 0), new vector(-11,-1, 0)};
		land[3] = new polygon3D(v, new vector(-1,-1, -2), new vector(1,-1, -2), new vector(-1,-1, -4), main.textures[3], 1, 1, 1);
		
		v = new vector[]{new vector(-13,-1, 22), new vector(-11,-1, 22), new vector(-11,-1, 5), new vector(-13,-1, 5)};
		land[4] = new polygon3D(v, new vector(-1,-1, -2), new vector(1,-1, -2), new vector(-1,-1, -4), main.textures[3], 1, 1, 1);
		
		v = new vector[]{new vector(-9,-1, 23.3), new vector(-11,-1, 15), new vector(-11,-1, 25), new vector(-9,-1, 26.3)};
		land[5] = new polygon3D(v, new vector(-1,-1, -2), new vector(1,-1, -2), new vector(-1,-1, -4), main.textures[3], 1, 1, 1);
		
		v = new vector[]{new vector(-4.5,-1, 30), new vector(-9,-1, 23.3), new vector(-9,-1, 29.3), new vector(-4.5,-1, 32)};
		land[6] = new polygon3D(v, new vector(-1,-1, -2), new vector(1,-1, -2), new vector(-1,-1, -4), main.textures[3], 1, 1, 1);
		
		v = new vector[]{new vector(0.979,-1, 31.4), new vector(-4.5,-1, 30), new vector(-4.5,-1, 32), new vector(0.979,-1, 33.5)};
		land[7] = new polygon3D(v, new vector(-1,-1, -2), new vector(1,-1, -2), new vector(-1,-1, -4), main.textures[3], 1, 1, 1);
	
		v = new vector[]{new vector(8.44,-1, 27.87), new vector(0.979,-1, 31.6), new vector(0.979,-1, 33.6), new vector(8.44,-1, 29.87)};
		land[8] = new polygon3D(v, new vector(-1,-1, -2), new vector(1,-1, -2), new vector(-1,-1, -4), main.textures[3], 1, 1, 1);
	
		v = new vector[]{new vector(11.0732, -1, 21.18), new vector(8.44,-1, 27.87), new vector(8.44,-1, 29.87), new vector(11.0732, -1, 27.18)};
		land[9] = new polygon3D(v, new vector(-1,-1, -2), new vector(1,-1, -2), new vector(-1,-1, -4), main.textures[3], 1, 1, 1);
	
		v = new vector[]{new vector(11.525,-1, 15.80), new vector(11.0732, -1, 21.18), new vector(11.0732, -1, 29.18), new vector(13.525,-1, 15.80)};
		land[10] = new polygon3D(v, new vector(-1,-1, -2), new vector(1,-1, -2), new vector(-1,-1, -4), main.textures[3], 1, 1, 1);
		
		v = new vector[]{new vector(8.328,-1, 7.03), new vector(11.525,-1, 15.80), new vector(13.525,-1, 15.80), new vector(10.328,-1, 7.03)};
		land[11] = new polygon3D(v, new vector(-1,-1, -2), new vector(1,-1, -2), new vector(-1,-1, -4), main.textures[3], 1, 1, 1);
	
		v = new vector[]{new vector(5.342,-1, 2.194), new vector(8.328,-1, 7.03), new vector(11.328,-1, 7.03), new vector(8.342,-1, 2.194)};
		land[12] = new polygon3D(v, new vector(-1,-1, -2), new vector(1,-1, -2), new vector(-1,-1, -4), main.textures[3], 1, 1, 1);
		
		v = new vector[]{new vector(1.8,-1, -2), new vector(5.342,-1, 2.194), new vector(7.342,-1, 2.194), new vector(1.8,-1, -4)};
		land[13] = new polygon3D(v, new vector(-1,-1, -2), new vector(1,-1, -2), new vector(-1,-1, -4), main.textures[3], 1, 1, 1);
	
		
		//create water
		water = new polygon3D[12];
		double x = 2;
		double y = 1;
		v = new vector[]{new vector(-1,-1, -0.6), new vector(1.3,-1, -0.6), new vector(1.8,-1, -4), new vector(-1,-1, -4)};
		water[0] = new polygon3D(v, new vector(-1, -1, 1), new vector(1, -1, 1), new vector(-1, -1, -1), main.textures[4], x, y, 5);
		
		v = new vector[]{new vector(-5,-1, 2.7), new vector(-1,-1, -0.6), new vector(-1,-1, -4), new vector(-7.1,-1, -2)};
		water[1] = new polygon3D(v, new vector(-1, -1, 1), new vector(1, -1, 1), new vector(-1, -1, -1), main.textures[4], x, y, 5);
		
		v = new vector[]{new vector(-9.2,-1, 15), new vector(-5,-1, 2.7), new vector(-7.1,-1, -2), new vector(-12,-1, 15)};
		water[2] = new polygon3D(v, new vector(-1, -1, 1), new vector(1, -1, 1), new vector(-1, -1, -1), main.textures[4], x, y, 5);
	
		v = new vector[]{new vector(-7.1,-1, 23), new vector(-9.2,-1, 15), new vector(-12,-1, 15), new vector(-10,-1, 23.3)};
		water[3] = new polygon3D(v, new vector(-1, -1, 1), new vector(1, -1, 1), new vector(-1, -1, -1), main.textures[4], x, y, 5);
	
		v = new vector[]{new vector(-3.1,-1, 29), new vector(-7.1,-1, 23), new vector(-10,-1, 23.3), new vector(-6,-1, 31)};
		water[4] = new polygon3D(v, new vector(-1, -1, 1), new vector(1, -1, 1), new vector(-1, -1, -1), main.textures[4], x, y, 5);
	
		v = new vector[]{new vector(1,-1, 30.2), new vector(-3.1,-1, 29), new vector(-6,-1, 31), new vector(1,-1, 34)};
		water[5] = new polygon3D(v, new vector(-1, -1, 1), new vector(1, -1, 1), new vector(-1, -1, -1), main.textures[4], x, y, 5);
	
		v = new vector[]{new vector(7,-1, 27.2), new vector(1,-1, 30.2), new vector(1,-1, 34), new vector(9,-1, 30)};
		water[6] = new polygon3D(v, new vector(-1, -1, 1), new vector(1, -1, 1), new vector(-1, -1, -1), main.textures[4], x, y, 5);
		
		v = new vector[]{new vector(9.6,-1, 21), new vector(7,-1, 27.2), new vector(9,-1, 30), new vector(13.5,-1, 21)};
		water[7] = new polygon3D(v, new vector(-1, -1, 1), new vector(1, -1, 1), new vector(-1, -1, -1), main.textures[4], x, y, 5);
	
		v = new vector[]{new vector(10.1,-1, 16), new vector(9.6,-1, 21), new vector(13.5,-1, 21), new vector(13.5,-1, 16)};
		water[8] = new polygon3D(v, new vector(-1, -1, 1), new vector(1, -1, 1), new vector(-1, -1, -1), main.textures[4], x, y, 5);
	
		v = new vector[]{new vector(7.1,-1, 8), new vector(10.1,-1, 16), new vector(13.5,-1, 16), new vector(10.5,-1, 7.5)};
		water[9] = new polygon3D(v, new vector(-1, -1, 1), new vector(1, -1, 1), new vector(-1, -1, -1), main.textures[4], x, y, 5);
	
		v = new vector[]{new vector(4.2,-1, 3), new vector(7.1,-1, 8), new vector(10.5,-1, 7.5), new vector(7,-1, 1)};
		water[10] = new polygon3D(v, new vector(-1, -1, 1), new vector(1, -1, 1), new vector(-1, -1, -1), main.textures[4], x, y, 5);
	
		v = new vector[]{new vector(1.3,-1, -0.6), new vector(4.2,-1, 3), new vector(7,-1, 1), new vector(1.8,-1, -4)};
		water[11] = new polygon3D(v, new vector(-1, -1, 1), new vector(1, -1, 1), new vector(-1, -1, -1), main.textures[4], x, y, 5);
	
		
		
		
		//create road
		road = new polygon3D[18];
		v = new vector[]{new vector(-1, -1, 1), new vector(-1, -1, 1.4), new vector(1, -1, 1.4), new vector(1, -1, 1)};
		road[0] = new polygon3D(v, v[0], v[1], v[3], main.textures[1], 1, 8, 1);
		
		v = new vector[]{new vector(-1.5, -1, 1.1), new vector(-1.5, -1, 1.53), new vector(-1, -1, 1.4), new vector(-1, -1, 1)};
		road[1] = new polygon3D(v, new vector(-1.5, -1, 1.15), new vector(-1.5, -1, 1.5), v[3], main.textures[1], 1, 2, 1);
		
		v = new vector[]{new vector(-2, -1,1.3), new vector(-2, -1, 1.75), new vector(-1.5, -1, 1.53), new vector(-1.5, -1, 1.1)};
		road[2] = new polygon3D(v, v[0], v[1], v[3], main.textures[1], 1, 2, 1);
		
		v = new vector[]{new vector(-2.5, -1,1.6), new vector(-2.5, -1, 2.2), new vector(-2, -1, 1.75), new vector(-2, -1,1.3)};
		road[3] = new polygon3D(v, new vector(-2.5, -1,1.55), new vector(-2.5, -1, 2.15), new vector(-2, -1,1.2), main.textures[1], 1, 2, 1);
	
		v = new vector[]{new vector(-4.6, -1,3.6), new vector(-4.4, -1, 4.1), new vector(-2.5, -1, 2.2), new vector(-2.5, -1,1.6)};
		road[4] = new polygon3D(v, v[0], v[1], new vector(-2.5, -1,1.55), main.textures[1], 1, 10, 1);
		
		v = new vector[]{new vector(-4.9, -1, 4), new vector(-4.4, -1, 4.1), new vector(-4.6, -1,3.6), new vector(-4.6, -1,3.6)};
		road[5] = new polygon3D(v, v[0], v[1],v[2], main.textures[1], 1, 1, 1);
		
		v = new vector[]{new vector(-7, -1, 10), new vector(-6.5, -1, 10.2), new vector(-4.4, -1, 4.1), new vector(-4.9, -1, 4)};
		road[6] = new polygon3D(v, v[0], v[1],v[3], main.textures[1], 1, 20, 1);
		
		v = new vector[]{new vector(1, -1, 1), new vector(1, -1, 1.4), new vector(1.3, -1, 1.4), new vector(1.3, -1, 1)};
		road[7] = new polygon3D(v, v[0], v[1],v[3], main.textures[1], 1, 0.1, 1);
		
		v = new vector[]{new vector(-7.9, -1, 15), new vector(-7.4, -1, 15.2), new vector(-6.5, -1, 10.2), new vector(-7, -1, 10)};
		road[8] = new polygon3D(v, v[0], v[1],v[3], main.textures[1], 1, 17, 1);
	
		v = new vector[]{new vector(-7.9, -1, 17.2), new vector(-7.4, -1, 17.2), new vector(-7.4, -1, 15.2),new vector(-7.9, -1, 15)};
		road[9] = new polygon3D(v, v[0], v[1],v[3], main.textures[1], 1, 7.2, 1);
		
		v = new vector[]{new vector(-7.5, -1, 19.2), new vector(-7, -1, 19.2), new vector(-7.4, -1, 17.2),new vector(-7.9, -1, 17.2)};
		road[10] = new polygon3D(v, v[0], v[1],v[3], main.textures[1], 1, 7.2, 1);
		
		v = new vector[]{new vector(-7, -1, 21.2), new vector(-6.5, -1, 21.2), new vector(-7, -1, 19.2),new vector(-7.5, -1, 19.2)};
		road[11] = new polygon3D(v, v[0], v[1],v[3], main.textures[1], 1, 7, 1);
		
		v = new vector[]{new vector(-6.6, -1, 22.2), new vector(-6.1, -1, 22.2), new vector(-6.5, -1, 21.2),new vector(-7, -1, 21.2)};
		road[12] = new polygon3D(v, v[0], v[1],v[3], main.textures[1], 1, 4.2, 1);
		
		v = new vector[]{new vector(-6.1, -1, 23.2), new vector(-5.5, -1, 23.2), new vector(-6.1, -1, 22.2),new vector(-6.6, -1, 22.2)};
		road[13] = new polygon3D(v, v[0], v[1],new vector(-6.65, -1, 22.2), main.textures[1], 1, 3.2, 1);
		
		v = new vector[]{new vector(-4.7, -1, 25.2), new vector(-4.05, -1, 25.2), new vector(-5.5, -1, 23.2),new vector(-6.1, -1, 23.2)};
		road[14] = new polygon3D(v, v[0], v[1], v[3], main.textures[1], 1, 7, 1);
		
		v = new vector[]{new vector(-2.4, -1, 27.786), new vector(-2.2, -1, 27.4), new vector(-4.05, -1, 25.2),new vector(-4.7, -1, 25.2)};
		road[15] = new polygon3D(v, new vector(-2.65, -1, 27.45), new vector(-2.4, -1, 27.2), new vector(-4.6, -1, 25.2), main.textures[1], 1, 9, 1);
	
		v = new vector[]{new vector(0.55, -1, 20),new vector(0.95, -1, 20),new vector(0.95, -1, 1.39),new vector(0.55, -1, 1.39)};
		road[16] = new polygon3D(v, v[0], v[1],v[3], main.textures[1], 1, 65, 1);
		
		v = new vector[]{new vector(-1.4, -1, 27.786), new vector(-1.4, -1, 27.34), new vector(-2.4, -1, 27.34), new vector(-2.4, -1, 27.786)};
		road[17] = new polygon3D(v, v[0], v[1],v[3], main.textures[1], 1, 4, 1);
			
		//These code are introduced after I have already hard-coded all the polygons of land, water and road.
		//Then I realised that I needed to shift them 10 map units to the right, 2 map units upg, in
		//order to get rid of nagetive values in coordinate.
		for(int i = 0; i < land.length; i++){
			for(int j = 0; j < land[i].L; j++){
				land[i].vertex3D[j].add(10, 0,2);
			}
			land[i].origin.add(10, 0,2);
			land[i].rightEnd.add(10, 0,2);
			land[i].bottomEnd.add(10, 0,2);
		}
		
		for(int i = 0; i < road.length; i++){
			for(int j = 0; j < road[i].L; j++){
				road[i].vertex3D[j].add(10, 0,2);
			}
			road[i].origin.add(10, 0,2);
			road[i].rightEnd.add(10, 0,2);
			road[i].bottomEnd.add(10, 0,2);
		}
		
		for(int i = 0; i < water.length; i++){
			for(int j = 0; j < water[i].L; j++){
				water[i].vertex3D[j].add(10, 0,2);
			}
			water[i].origin.add(10, 0,2);
			water[i].rightEnd.add(10, 0,2);
			water[i].bottomEnd.add(10, 0,2);
		}
		
		//create concrete floor
		concreteFloor = new  polygon3D[3];
		
		v = new vector[]{new vector(8.4, -1, 22.85), new vector(13.85, -1, 22.85), new vector(13.85, -1, 18.15), new vector(8.4, -1, 18.15)};
		concreteFloor[0] = new polygon3D(v, v[0], v[1],v[3], main.textures[40], 6.5, 5.5, 1);
		
		v = new vector[]{new vector(17.86, -1, 16.9), new vector(18.9, -1, 16.9), new vector(18.9, -1, 15.86), new vector(17.86, -1, 15.86)};
		concreteFloor[1] = new polygon3D(v, v[0], v[1],v[3], main.textures[40], 1, 1, 1);
		
		v = new vector[]{new vector(3.86, -1, 14.4), new vector(4.9, -1, 14.4), new vector(4.9, -1, 13.36), new vector(3.86, -1, 13.36)};
		concreteFloor[2] = new polygon3D(v, v[0], v[1],v[3], main.textures[40], 1, 1, 1);
		
		//create underwater rocks
		underWaterRocks = new stone[90];
		underWaterRocks[0] = new stone(10.5,-0.875,0.6,260, 2, 0.75, 6, false);
		underWaterRocks[1] = new stone(11,-0.875,0.9,160, 2, 0.5, 6, false);
		underWaterRocks[2] = new stone(9,-0.875,1.1, 60, 2, 0.5, 6, false);
		underWaterRocks[3] = new stone(12.5,-0.875,2,160, 2, 0.7, 6, false);
		underWaterRocks[4] = new stone(7.5,-0.875,2,160, 2, 0.6, 6, false);
		underWaterRocks[5] = new stone(13,-0.875,2.7,160, 2, 0.4, 6, false);
		underWaterRocks[6] = new stone(16.5,-0.875,8.1,160, 2, 0.5, 6, false);
		underWaterRocks[7] = new stone(14.125,  -0.875,3.375,135, 2,0.40,6,false);
		underWaterRocks[8] = new stone(15.375,  -0.875,4.125,54, 2,0.50,6,false);
		underWaterRocks[9] = new stone(15.375,  -0.875,5.625,51, 2,0.53,6,false);
		underWaterRocks[10] = new stone(17.125,  -0.875,7.625,138, 2,0.48,6,false);
		underWaterRocks[11] = new stone(18.375,  -0.875,9.125,237, 2,0.51,6,false);
		underWaterRocks[12] = new stone(18.375,  -0.875,11.875,165, 2,0.35,6,false);
		underWaterRocks[13] = new stone(17.875,  -0.875,10.875,120, 2,0.52,6,false);
		underWaterRocks[14] = new stone(6.375,  -0.875,2.625,84, 2,0.43,6,false);
		underWaterRocks[15] = new stone(4.625,  -0.875,4.625,15, 2,0.40,6,false);
		underWaterRocks[16] = new stone(4.125,  -0.875,5.125,168, 2,0.50,6,false);
		underWaterRocks[17] = new stone(3.875,  -0.875,5.875,120, 2,0.46,6,false);
		underWaterRocks[18] = new stone(2.875,  -0.875,7.875,228, 2,0.42,6,false);
		underWaterRocks[19] = new stone(2.625,  -0.875,9.625,105, 2,0.34,6,false);
		underWaterRocks[20] = new stone(1.875,  -0.875,10.875,66, 2,0.48,6,false);
		underWaterRocks[21] = new stone(1.875,  -0.875,12.625,48, 2,0.52,6,false);
		underWaterRocks[22] = new stone(0.875,  -0.875,14.875,282, 2,0.33,6,false);
		underWaterRocks[23] = new stone(0.125,  -0.875,15.125,81, 2,0.42,6,false);
		underWaterRocks[24] = new stone(0.125,  -0.875,16.625,291, 2,0.3,6,false);
		underWaterRocks[25] = new stone(0.375,  -0.875,17.375,201, 2,0.41,6,false);
		underWaterRocks[26] = new stone(-0.125,  -0.875,19.125,192, 2,0.495,6,false);
		underWaterRocks[27] = new stone(0.875,  -0.875,20.625,36, 2,0.51,6,false);
		underWaterRocks[28] = new stone(1.375,  -0.875,22.375,57, 2,0.41,6,false);
		underWaterRocks[29] = new stone(1.125,  -0.875,22.875,291, 2,0.33,6,false);
		underWaterRocks[30] = new stone(1.875,  -0.875,24.125,105, 2,0.38,6,false);
		underWaterRocks[31] = new stone(2.375,  -0.875,24.875,219, 2,0.37,6,false);
		underWaterRocks[32] = new stone(2.625,  -0.875,26.125,33, 2,0.53,6,false);
		underWaterRocks[33] = new stone(3.625,  -0.875,27.625,228, 2,0.53,6,false);
		underWaterRocks[34] = new stone(2.375,  -0.875,27.125,174, 2,0.44,6,false);
		underWaterRocks[35] = new stone(4.125,  -0.875,28.625,153, 2,0.37,6,false);
		underWaterRocks[36] = new stone(0.375,  -0.875,16.875,93, 2,0.27,6,false);
		underWaterRocks[37] = new stone(0.125,  -0.875,17.125,126, 2,0.22,6,false);
		underWaterRocks[38] = new stone(0.125,  -0.875,17.625,174, 2,0.30,6,false);
		underWaterRocks[39] = new stone(0.625,  -0.875,17.625,93, 2,0.22,6,false);
		underWaterRocks[40] = new stone(5.125,  -0.875,29.625,210, 2,0.45,6,false);
		underWaterRocks[41] = new stone(6.125,  -0.875,31.375,66, 2,0.41,6,false);
		underWaterRocks[42] = new stone(6.875,  -0.875,31.875,111, 2,0.58,6,false);
		underWaterRocks[43] = new stone(18.625,  -0.875,12.375,132, 2,0.26,6,false);
		underWaterRocks[44] = new stone(18.375,  -0.875,12.125,90, 2,0.23,6,false);
		underWaterRocks[45] = new stone(20.125,  -0.875,13.375,60, 2,0.29,6,false);
		underWaterRocks[46] = new stone(19.375,  -0.875,14.375,231, 2,0.41,6,false);
		underWaterRocks[47] = new stone(20.625,  -0.875,16.375,165, 2,0.51,6,false);
		underWaterRocks[48] = new stone(20.625,  -0.875,14.875,147, 2,0.50,6,false);
		underWaterRocks[49] = new stone(20.625,  -0.875,18.375,48, 2,0.40,6,false);
		underWaterRocks[50] = new stone(18.375,  -0.875,12.375,0, 2,0.35,6,false);
		underWaterRocks[51] = new stone(21.375,  -0.875,18.125,111, 2,0.47,6,false);
		underWaterRocks[52] = new stone(8.875,  -0.875,33.125,105, 2,0.237,6,false);
		underWaterRocks[53] = new stone(9.125,  -0.875,33.125,54, 2,0.262,6,false);
		underWaterRocks[54] = new stone(9.125,  -0.875,32.875,60, 2,0.391,6,false);
		underWaterRocks[55] = new stone(8.875,  -0.875,32.375,198, 2,0.458,6,false);
		underWaterRocks[56] = new stone(9.125,  -0.875,32.125,156, 2,0.241,6,false);
		underWaterRocks[57] = new stone(10.375,  -0.875,32.625,15, 2,0.524,6,false);
		underWaterRocks[58] = new stone(12.125,  -0.875,32.625,297, 2,0.26,6,false);
		underWaterRocks[59] = new stone(12.625,  -0.875,32.625,291, 2,0.36,6,false);
		underWaterRocks[60] = new stone(14.375,  -0.875,31.375,228, 2,0.25,6,false);
		underWaterRocks[61] = new stone(13.375,  -0.875,31.625,198, 2,0.29,6,false);
		underWaterRocks[62] = new stone(12.125,  -0.875,33.125,276, 2,0.37,6,false);
		underWaterRocks[63] = new stone(12.375,  -0.875,32.875,243, 2,0.28,6,false);
		underWaterRocks[64] = new stone(13.875,  -0.875,31.875,150, 2,0.49,6,false);
		underWaterRocks[65] = new stone(13.875,  -0.875,31.375,135, 2,0.41,6,false);
		underWaterRocks[66] = new stone(17.625,  -0.875,28.375,72, 2,0.31,6,false);
		underWaterRocks[67] = new stone(17.875,  -0.875,28.125,222, 2,0.37,6,false);
		underWaterRocks[68] = new stone(18.125,  -0.875,28.625,255, 2,0.45,6,false);
		underWaterRocks[69] = new stone(19.625,  -0.875,24.375,228, 2,0.40,6,false);
		underWaterRocks[70] = new stone(19.875,  -0.875,24.875,147, 2,0.50,6,false);

		
		//create above water rocks
		aboveWaterRocks = new stone[1];
		aboveWaterRocks[0] = new stone(10.5,-0.875,0.6,260, 4, 0.75, 6, true);
		
		//create inland rocks
		inlandRocks = new stone[150];
		inlandRocks[0] = new stone(9.625,-0.875,2.125,160, 2, 0.5, 6, true);
		inlandRocks[1] = new stone(9.125,-0.875,2.375,160, 2, 0.5, 6, true);
		inlandRocks[2] = new stone(8.25,-0.75,4,160, 2, 1, 7, true);
		inlandRocks[3] = new stone(11.125,-0.875,2.375,160, 2, 0.5, 6, true);
		inlandRocks[4] = new stone(11.125,-0.875,4.125,160, 2, 0.5, 7, true);
		inlandRocks[5] = new stone(10.375,-0.875,4.625,160, 2, 0.5, 7, true);
		inlandRocks[6] = new stone(7.375,-0.875,3.375,160, 2, 0.5, 6, true);
		inlandRocks[7] = new stone(7.375,-0.875,5.375,160, 2, 0.5, 7, true);
		inlandRocks[8] = new stone(5.375,-0.875,5.125,160, 2, 0.5, 6, true);
		inlandRocks[9] = new stone(9.25,-0.75,6.25,180, 2, 1, 7, true);
		inlandRocks[10] = new stone(8.625,-0.875,6.125,60, 2, 0.5, 7, true);
		inlandRocks[11] = new stone(14.125, -0.875, 6.375,60, 2, 0.5, 6, true);
		inlandRocks[12] = new stone(11.875, -0.875, 7.625,160, 2, 0.5, 7, true);
		inlandRocks[13] = new stone(11.375, -0.875, 6.875,60, 2, 0.5, 7, true);
		inlandRocks[14] = new stone(9, -0.75, 10,160, 2, 1, 7, true);
		inlandRocks[15] = new stone(8.375, -0.875, 9.625,110, 2, 0.5, 7, true);
		inlandRocks[16] = new stone(8.625, -0.875, 9.125,60, 2, 0.5, 7, true);
		inlandRocks[17] = new stone(8.375, -0.875, 8.625,160, 2, 0.5, 7, true);
		inlandRocks[18] = new stone(7.5, -0.75, 8.25,120, 2, 1, 7, true);
		inlandRocks[19] = new stone(7.375, -0.875, 9.375,260, 2, 0.5, 7, true);
		inlandRocks[20] = new stone(5.875, -0.875, 9.125,260, 2, 0.5, 7, true);
		inlandRocks[21] = new stone(4.125, -0.875, 8.125,260, 2, 0.5, 6, true);
		inlandRocks[22] = new stone(6.375, -0.875, 10.625,260, 2, 0.5, 7, true);
		inlandRocks[23] = new stone(5.875, -0.875, 10.125,260, 2, 0.5, 7, true);
		inlandRocks[24] = new stone(15.125, -0.875, 8.875,260, 2, 0.5, 6, true);
		inlandRocks[25] = new stone(15.625, -0.875, 9.125,260, 2, 0.5, 6, true);
		inlandRocks[26] = new stone(14.625, -0.875, 9.375,260, 2, 0.5, 6, true);
		inlandRocks[27] = new stone(15.875, -0.875, 10.125,260, 2, 0.5, 6, true);
		inlandRocks[28] = new stone(14.375, -0.875, 10.875,260, 2, 0.5, 7, true);
		inlandRocks[29] = new stone(13, -0.75, 11,260, 2,1, 7, true);
		inlandRocks[30] = new stone(12.125, -0.875, 10.625,260, 2, 0.5, 7, true);
		inlandRocks[31] = new stone(12.375, -0.875, 11.375,160, 2, 0.5, 7, true);
		inlandRocks[32] = new stone(10.125, -0.875, 11.625,160, 2, 0.5, 7, true);
		inlandRocks[33] = new stone(9.375, -0.875, 10.875,160, 2, 0.5, 7, true);
		inlandRocks[34] = new stone(11.125, -0.875, 11.375,160, 2, 0.5, 7, true);
		inlandRocks[35] = new stone(7.125,  -0.875,10.875,264, 2,0.5,7,true);
		inlandRocks[36] = new stone(8.125,  -0.875,12.125,255, 2,0.5,7,true);
		inlandRocks[37] = new stone(7.625,  -0.875,12.375,114, 2,0.5,7,true);
		inlandRocks[38] = new stone(8.375,  -0.875,14.125,99, 2,0.5,7,true);
		inlandRocks[39] = new stone(7.875,  -0.875,14.375,276, 2,0.5,7,true);
		inlandRocks[40] = new stone(5.875,  -0.875,13.875,60, 2,0.5,7,true);
		inlandRocks[41] = new stone(5.375,  -0.875,11.875,186, 2,0.5,7,true);
		inlandRocks[42] = new stone(5.375,  -0.875,8.125,279, 2,0.5,6,true);
		inlandRocks[43] = new stone(4.375,  -0.875,10.125,27, 2,0.5,6,true);
		inlandRocks[44] = new stone(4.375,  -0.875,10.875,183, 2,0.5,6,true);
		inlandRocks[45] = new stone(2.625,  -0.875,13.125,183, 2,0.5,6,true);
		inlandRocks[46] = new stone(3.875,  -0.875,12.625,186, 2,0.5,6,true);
		inlandRocks[47] = new stone(5.125,  -0.875,5.625,51, 2,0.5,6,true);
		inlandRocks[48] = new stone(13.125,  -0.875,4.875,216, 2,0.5,6,true);
		inlandRocks[49] = new stone(16.375,  -0.875,10.875,129, 2,0.5,6,true);
		inlandRocks[50] = new stone(17.125,  -0.875,11.625,123, 2,0.5,6,true);
		inlandRocks[51] = new stone(12.375,  -0.875,13.625,177, 2,0.5,7,true);
		inlandRocks[52] = new stone(12.875,  -0.875,13.875,51, 2,0.5,7,true);
		inlandRocks[53] = new stone(12.625,  -0.875,14.375,216, 2,0.5,7,true);
		inlandRocks[54] = new stone(14.875,  -0.875,13.875,72, 2,0.5,7,true);
		inlandRocks[55] = new stone(14.625,  -0.875,14.375,135, 2,0.5,7,true);
		inlandRocks[56] = new stone(13.625,  -0.875,15.125,237, 2,0.5,7,true);
		inlandRocks[57] = new stone(14,  -0.75,14,237, 2,1,7,true);
		inlandRocks[58] = new stone(6.875,  -0.875,14.625,255, 2,0.5,7,true);
		inlandRocks[59] = new stone(5.625,  -0.875,15.375,267, 2,0.5,7,true);
		inlandRocks[60] = new stone(9.125,  -0.875,15.875,249, 2,0.5,7,true);
		inlandRocks[61] = new stone(7.125,  -0.875,21.125,99, 2,0.5,7,true);
		inlandRocks[62] = new stone(6.875,  -0.875,16.875,276, 2,0.5,7,true);
		inlandRocks[63] = new stone(6.375,  -0.875,17.125,93, 2,0.5,7,true);
		inlandRocks[64] = new stone(15.625,  -0.875,14.125,153, 2,0.5,7,true);
		inlandRocks[65] = new stone(6.375,  -0.875,21.875,162, 2,0.5,7,true);
		inlandRocks[66] = new stone(5.875,  -0.875,18.375,123, 2,0.5,7,true);
		inlandRocks[67] = new stone(5.875,  -0.875,19.125,69, 2,0.5,7,true);
		inlandRocks[68] = new stone(2.375,  -0.875,14.375,282, 2,0.5,6,true);
		inlandRocks[69] = new stone(3.375,  -0.875,15.125,147, 2,0.5,6,true);
		inlandRocks[70] = new stone(1.875,  -0.875,16.625,129, 2,0.5,6,true);
		inlandRocks[71] = new stone(1.625,  -0.875,17.125,78, 2,0.5,6,true);
		inlandRocks[72] = new stone(5,  -0.75,17,237, 2,1,7,true);
		inlandRocks[73] = new stone(3.125,  -0.875,19.875,192, 2,0.5,6,true);
		inlandRocks[74] = new stone(3.375,  -0.875,19.125,51, 2,0.5,6,true);
		inlandRocks[75] = new stone(16.125,  -0.875,15.375,120, 2,0.5,7,true);
		inlandRocks[76] = new stone(15.125,  -0.875,16.625,96, 2,0.5,7,true);
		inlandRocks[77] = new stone(15.875,  -0.875,16.125,45, 2,0.5,7,true);
		inlandRocks[78] = new stone(15.375,  -0.875,14.875,231, 2,0.5,7,true);
		inlandRocks[79] = new stone(19.125,  -0.875,17.375,204, 2,0.5,6,true);
		inlandRocks[80] = new stone(18.625,  -0.875,17.875,198, 2,0.5,6,true);
		inlandRocks[81] = new stone(16.875,  -0.875,19.125,231, 2,0.5,7,true);
		inlandRocks[82] = new stone(14.875,  -0.875,17.875,213, 2,0.5,7,true);
		inlandRocks[83] = new stone(18.375,  -0.875,15.125,276, 2,0.5,6,true);
		inlandRocks[84] = new stone(17.625,  -0.875,13.375,21, 2,0.5,6,true);
		inlandRocks[85] = new stone(17.125,  -0.875,15.875,141, 2,0.5,7,true);
		inlandRocks[86] = new stone(3.375,  -0.875,24.625,120, 2,0.5,6,true);
		inlandRocks[87] = new stone(3.875,  -0.875,25.875,60, 2,0.5,6,true);
		inlandRocks[88] = new stone(5.625,  -0.875,23.875,174, 2,0.5,7,true);
		inlandRocks[89] = new stone(6.125,  -0.875,23.625,174, 2,0.5,7,true);
		inlandRocks[90] = new stone(6.875,  -0.875,24.375,24, 2,0.5,7,true);
		inlandRocks[91] = new stone(6.375,  -0.875,25.625,294, 2,0.5,7,true);
		inlandRocks[92] = new stone(5.625,  -0.875,24.625,180, 2,0.5,7,true);
		inlandRocks[93] = new stone(3.625,  -0.875,22.875,222, 2,0.5,6,true);
		inlandRocks[94] = new stone(3.875,  -0.875,21.375,234, 2,0.5,6,true);
		inlandRocks[95] = new stone(6.25,  -0.75,26.75,237, 2,1,7,true);
		inlandRocks[96] = new stone(2.375,  -0.875,21.875,288, 2,0.5,6,true);
		inlandRocks[97] = new stone(17.875,  -0.875,12.625,63, 2,0.5,6,true);
		inlandRocks[98] = new stone(8.125,  -0.875,25.625,177, 2,0.5,7,true);
		inlandRocks[99] = new stone(9.375,  -0.875,25.625,51, 2,0.5,7,true);
		inlandRocks[100] = new stone(11.625,  -0.875,25.875,258, 2,0.5,7,true);
		inlandRocks[101] = new stone(10.875,  -0.875,26.625,156, 2,0.5,7,true);
		inlandRocks[102] = new stone(10.625,  -0.875,26.125,72, 2,0.5,7,true);
		inlandRocks[103] = new stone(10.125,  -0.875,26.375,147, 2,0.5,7,true);
		inlandRocks[104] = new stone(8.875,  -0.875,26.125,123, 2,0.5,7,true);
		inlandRocks[105] = new stone(9.25,  -0.75,26.75, 285, 2, 1, 7, true);
		inlandRocks[106] = new stone(5.875,  -0.875,28.875,87, 2,0.5,6,true);
		inlandRocks[107] = new stone(6.375,  -0.875,29.125,192, 2,0.5,6,true);
		inlandRocks[108] = new stone(7.125,  -0.875,29.875,216, 2,0.5,6,true);
		inlandRocks[109] = new stone(7.125,  -0.875,28.375,198, 2,0.5,6,true);
		inlandRocks[110] = new stone(11.125,  -0.875,27.875,135, 2,0.5,7,true);
		inlandRocks[111] = new stone(10.375,  -0.875,27.125,290, 2,0.5,7,true);
		inlandRocks[112] = new stone(7.875,  -0.875,30.875,132, 2,0.5,6,true);
		inlandRocks[113] = new stone(19.625,  -0.875,18.125,15, 2,0.5,6,true);
		inlandRocks[114] = new stone(9.875,  -0.875,31.375,153, 2,0.5,6,true);
		inlandRocks[115] = new stone(11.125,  -0.875,30.625,213, 2,0.5,6,true);
		inlandRocks[116] = new stone(10.875,  -0.875,31.625,123, 2,0.5,6,true);
		inlandRocks[117] = new stone(11.125,  -0.875,29.125,156, 2,0.5,7,true);
		inlandRocks[118] = new stone(12.625,  -0.875,30.375,168, 2,0.5,6,true);
		inlandRocks[119] = new stone(13.625,  -0.875,29.625,174, 2,0.5,6,true);
		inlandRocks[120] = new stone(13.625,  -0.875,26.875,249, 2,0.5,7,true);
		inlandRocks[121] = new stone(15.125,  -0.875,29.375,174, 2,0.5,6,true);
		inlandRocks[122] = new stone(17.125,  -0.875,28.125,195, 2,0.5,6,true);
		inlandRocks[123] = new stone(17.625,  -0.875,20.125,36, 2,0.5,7,true);
		inlandRocks[124] = new stone(16.875,  -0.875,20.875,291, 2,0.5,7,true);
		inlandRocks[125] = new stone(16.375,  -0.875,20.125,207, 2,0.5,7,true);
		inlandRocks[126] = new stone(16.375,  -0.875,21.375,36, 2,0.5,7,true);
		inlandRocks[127] = new stone(17.375,  -0.875,21.375,54, 2,0.5,7,true);
		inlandRocks[128] = new stone(19.375,  -0.875,21.375,87, 2,0.5,6,true);
		inlandRocks[129] = new stone(16.5,  -0.875,22.25,285, 2,1,7,true);
		inlandRocks[130] = new stone(18.875,  -0.875,22.625,48, 2,0.5,6,true);
		inlandRocks[131] = new stone(18.375,  -0.875,23.625,297, 2,0.5,6,true);
		inlandRocks[132] = new stone(13.625,  -0.875,23.875,297, 2,0.5,7,true);
		inlandRocks[133] = new stone(15.375,  -0.875,23.375,54, 2,0.5,7,true);
		inlandRocks[134] = new stone(18.625,  -0.875,24.125,234, 2,0.5,6,true);
		inlandRocks[135] = new stone(17.875,  -0.875,26.125,261, 2,0.5,6,true);
		inlandRocks[136] = new stone(17.625,  -0.875,24.875,102, 2,0.5,6,true);
		inlandRocks[137] = new stone(15.625,  -0.875,24.625,297, 2,0.5,7,true);

		
		//create walls
		walls = new wall[84];
		walls[0] = new wall(8.375,  -0.9,18.875, 0,0);
		walls[5] = new wall(8.375,  -0.9,18.375, 0,0);
		walls[6] = new wall(8.375,  -0.9,18.625, 0,0);
		walls[1] = new wall(8.375,  -0.9,19.125, 0,0);
		walls[2] = new wall(8.375,  -0.9,19.375, 0,0);
		walls[3] = new wall(8.375,  -0.9,19.625, 0,0);
		walls[4] = new wall(8.375,  -0.9,19.875, 0,1);
		walls[7] = new wall(8.375,  -0.9,18.125, 5,0);
		walls[8] = new wall(8.375,  -0.9,20.875, 0,2);
		walls[10] = new wall(8.375,  -0.9,21.125, 0,0);
		walls[11] = new wall(8.375,  -0.9,21.375, 0,0);
		walls[12] = new wall(8.375,  -0.9,21.625, 0,0);
		walls[13] = new wall(8.375,  -0.9,21.875, 0,0);
		walls[14] = new wall(8.375,  -0.9,22.125, 0,0);
		walls[15] = new wall(8.375,  -0.9,22.375, 0,0);
		walls[16] = new wall(8.375,  -0.9,22.625, 6,0);
		walls[17] = new wall(8.625,  -0.9,18.125, 1,0);
		walls[18] = new wall(8.875,  -0.9,18.125, 1,0);
		walls[19] = new wall(9.375,  -0.9,18.125, 1,0);
		walls[20] = new wall(9.125,  -0.9,18.125, 1,0);
		walls[21] = new wall(9.625,  -0.9,18.125, 1,0);
		walls[22] = new wall(9.875,  -0.9,18.125, 1,0);
		walls[23] = new wall(10.125,  -0.9,18.125, 1,1);
		walls[24] = new wall(11.375,  -0.9,18.125, 1,2);
		walls[25] = new wall(11.625,  -0.9,18.125, 1,0);
		walls[26] = new wall(11.875,  -0.9,18.125, 1,0);
		walls[27] = new wall(12.125,  -0.9,18.125, 1,0);
		walls[28] = new wall(12.375,  -0.9,18.125, 1,0);
		walls[29] = new wall(12.625,  -0.9,18.125, 1,0);
		walls[30] = new wall(12.875,  -0.9,18.125, 1,0);
		walls[31] = new wall(13.125,  -0.9,18.125, 1,0);
		walls[32] = new wall(13.375,  -0.9,18.125, 1,0);
		walls[33] = new wall(13.625,  -0.9,18.125, 7,0);
		walls[34] = new wall(13.875,  -0.9,18.125, 4,0);
		walls[35] = new wall(13.875,  -0.9,18.375, 0,0);
		walls[36] = new wall(13.875,  -0.9,18.625, 0,0);
		walls[37] = new wall(13.875,  -0.9,18.875, 0,0);
		walls[38] = new wall(13.875,  -0.9,19.125, 0,0);
		walls[39] = new wall(13.875,  -0.9,19.375, 0,0);
		walls[40] = new wall(13.875,  -0.9,19.625, 0,0);
		walls[41] = new wall(13.875,  -0.9,19.875, 0,1);
		walls[42] = new wall(13.875,  -0.9,20.875, 0,2);
		walls[9] = new wall(13.875,  -0.9,21.125, 0,0);
		walls[43] = new wall(13.875,  -0.9,21.375, 0,0);
		walls[44] = new wall(13.875,  -0.9,21.625, 0,0);
		walls[45] = new wall(13.875,  -0.9,21.875, 0,0);
		walls[46] = new wall(13.875,  -0.9,22.125, 0,0);
		walls[47] = new wall(13.875,  -0.9,22.375, 0,0);
		walls[48] = new wall(13.875,  -0.9,22.625, 0,0);
		walls[49] = new wall(13.875,  -0.9,22.875, 2,0);
		walls[50] = new wall(13.625,  -0.9,22.875, 1,0);
		walls[51] = new wall(13.375,  -0.9,22.875, 1,0);
		walls[52] = new wall(13.125,  -0.9,22.875, 1,0);
		walls[53] = new wall(12.875,  -0.9,22.875, 1,0);
		walls[54] = new wall(12.625,  -0.9,22.875, 1,0);
		walls[55] = new wall(12.375,  -0.9,22.875, 1,0);
		walls[56] = new wall(12.125,  -0.9,22.875, 1,0);
		walls[57] = new wall(11.875,  -0.9,22.875, 1,0);
		walls[58] = new wall(11.625,  -0.9,22.875, 1,0);
		walls[59] = new wall(11.375,  -0.9,22.875, 1,2);
		walls[60] = new wall(10.125,  -0.9,22.875, 1,1);
		walls[61] = new wall(9.875,  -0.9,22.875, 1,0);
		walls[62] = new wall(9.625,  -0.9,22.875, 1,0);
		walls[63] = new wall(9.375,  -0.9,22.875, 1,0);
		walls[64] = new wall(9.125,  -0.9,22.875, 1,0);
		walls[65] = new wall(8.875,  -0.9,22.875, 1,0);
		walls[66] = new wall(8.625,  -0.9,22.875, 1,0);
		walls[67] = new wall(8.375,  -0.9,22.875, 3,0);
		walls[68] = new wall(17.875,  -0.9, 15.875,8, 0);
		walls[69] = new wall(18.875,  -0.9, 15.875,9, 0);
		walls[70] = new wall(18.875,  -0.9, 16.875,10, 0);
		walls[71] = new wall(17.875,  -0.9, 16.875,11, 0);

		walls[72] = new wall(3.875,  -0.9, 13.375,8, 0);
		walls[73] = new wall(4.875,  -0.9, 13.375,9, 0);
		walls[74] = new wall(4.875,  -0.9, 14.375,10, 0);
		walls[75] = new wall(3.875,  -0.9, 14.375,11, 0);
		
		walls[76] = new wall(12.125,  -0.9, 18.875,8, 0);
		walls[77] = new wall(13.125,  -0.9, 18.875,9, 0);
		walls[78] = new wall(13.125,  -0.9, 19.875,10, 0);
		walls[79] = new wall(12.125,  -0.9, 19.875,11, 0);
		
		walls[80] = new wall(12.125,  -0.9, 20.875,8, 0);
		walls[81] = new wall(13.125,  -0.9, 20.875,9, 0);
		walls[82] = new wall(13.125,  -0.9, 21.875,10, 0);
		walls[83] = new wall(12.125,  -0.9, 21.875,11, 0);
		
		
	
		//create energy fences
		fences = new fence[62];
		fences[0] = new fence(10.375,  -0.9,18.125,1);
		fences[1] = new fence(10.625,  -0.9,18.125,1);
		fences[2] = new fence(10.875,  -0.9,18.125,1);
		fences[3] = new fence(11.125,  -0.9,18.125,1);
		
		fences[4] = new fence(8.375,  -0.9,20.125, 0);
		fences[5] = new fence(8.375,  -0.9,20.375, 0);
		fences[6] = new fence(8.375,  -0.9,20.625, 0);
		
		fences[7] = new fence(13.875,  -0.9,20.125, 0);
		fences[8] = new fence(13.875,  -0.9,20.375, 0);
		fences[9] = new fence(13.875,  -0.9,20.625, 0);
		
		fences[10] = new fence(10.375,  -0.9,22.875,1);
		fences[11] = new fence(10.625,  -0.9,22.875,1);
		fences[12] = new fence(10.875,  -0.9,22.875,1);
		fences[13] = new fence(11.125,  -0.9,22.875,1);
		
		fences[14] = new fence(18.125,  -0.9,15.875,1);
		fences[15] = new fence(18.375,  -0.9,15.875,1);
		fences[16] = new fence(18.625,  -0.9,15.875,1);
		fences[17] = new fence(18.875,  -0.9,16.125,0);
		fences[18] = new fence(18.875,  -0.9,16.375,0);
		fences[19] = new fence(18.875,  -0.9,16.625,0);
		fences[20] = new fence(18.625,  -0.9,16.875,1);
		fences[21] = new fence(18.375,  -0.9,16.875,1);
		fences[22] = new fence(18.125,  -0.9,16.875,1);
		fences[23] = new fence(17.875,  -0.9,16.625,0);
		fences[24] = new fence(17.875,  -0.9,16.375,0);
		fences[25] = new fence(17.875,  -0.9,16.125,0);
		
		fences[26] = new fence(4.125,  -0.9,13.375,1);
		fences[27] = new fence(4.375,  -0.9,13.375,1);
		fences[28] = new fence(4.625,  -0.9,13.375,1);
		fences[29] = new fence(4.875,  -0.9,13.625,0);
		fences[30] = new fence(4.875,  -0.9,13.875,0);
		fences[31] = new fence(4.875,  -0.9,14.125,0);
		fences[32] = new fence(4.625,  -0.9,14.375,1);
		fences[33] = new fence(4.375,  -0.9,14.375,1);
		fences[34] = new fence(4.125,  -0.9,14.375,1);
		fences[35] = new fence(3.875,  -0.9,14.125,0);
		fences[36] = new fence(3.875,  -0.9,13.875,0);
		fences[37] = new fence(3.875,  -0.9,13.625,0);
		
		fences[38] = new fence(12.375,  -0.9,18.875,1);
		fences[39] = new fence(12.625,  -0.9,18.875,1);
		fences[40] = new fence(12.875,  -0.9,18.875,1);
		fences[41] = new fence(13.125,  -0.9,19.125,0);
		fences[42] = new fence(13.125,  -0.9,19.375,0);
		fences[43] = new fence(13.125,  -0.9,19.625,0);
		fences[44] = new fence(12.875,  -0.9,19.875,1);
		fences[45] = new fence(12.625,  -0.9,19.875,1);
		fences[46] = new fence(12.375,  -0.9,19.875,1);
		fences[47] = new fence(12.125,  -0.9,19.625,0);
		fences[48] = new fence(12.125,  -0.9,19.375,0);
		fences[49] = new fence(12.125,  -0.9,19.125,0);
		
		fences[50] = new fence(12.375,  -0.9,20.875,1);
		fences[51] = new fence(12.625,  -0.9,20.875,1);
		fences[52] = new fence(12.875,  -0.9,20.875,1);
		fences[53] = new fence(13.125,  -0.9,21.125,0);
		fences[54] = new fence(13.125,  -0.9,21.375,0);
		fences[55] = new fence(13.125,  -0.9,21.625,0);
		fences[56] = new fence(12.875,  -0.9,21.875,1);
		fences[57] = new fence(12.625,  -0.9,21.875,1);
		fences[58] = new fence(12.375,  -0.9,21.875,1);
		fences[59] = new fence(12.125,  -0.9,21.625,0);
		fences[60] = new fence(12.125,  -0.9,21.375,0);
		fences[61] = new fence(12.125,  -0.9,21.125,0);
		
		
		//create  trees
		palmTrees = new palmTree[450];
		palmTrees[0] = new palmTree(10.125, -0.75, 4.125, 60);
		palmTrees[1] = new palmTree(9.875, -0.75, 3.875, 150);
		palmTrees[2] = new palmTree(9.375, -0.75, 2.875, 40);
		palmTrees[3] = new palmTree(8.125, -0.75, 4.375, 30);
		palmTrees[4] = new palmTree(7.625, -0.75, 3.375, 120);
		palmTrees[5] = new palmTree(9.625, -0.75, 3.875, 10);
		palmTrees[6] = new palmTree(9.375, -0.75, 3.625, 90);
		palmTrees[7] = new palmTree(9.875, -0.75, 4.125, 180);
		palmTrees[8] = new palmTree(9.625, -0.75, 4.375, 70);
		palmTrees[9] = new palmTree(8.875, -0.75, 4.125, 160);
		palmTrees[10] = new palmTree(10.625, -0.75, 2.875, 100);
		palmTrees[11] = new palmTree(11.625, -0.75, 3.875, 40);
		palmTrees[12] = new palmTree(11.375, -0.75, 4.125, 60);
		palmTrees[13] = new palmTree(11.375, -0.75, 4.625, 40);
		palmTrees[14] = new palmTree(12.375, -0.75, 4.625, 20);
		palmTrees[15] = new palmTree(9.875, -0.75, 4.625, 20);
		palmTrees[16] = new palmTree(11.875, -0.75, 4.375, 20);
		palmTrees[17] = new palmTree(7.625, -0.75, 4.625, 20);
		palmTrees[18] = new palmTree(7.625, -0.75, 5.625, 20);
		palmTrees[19] = new palmTree(9.375, -0.75, 4.125, 160);
		palmTrees[20] = new palmTree(9.625, -0.75, 4.125, 40);
		palmTrees[21] = new palmTree(9.125, -0.75, 4.375, 70);
		palmTrees[22] = new palmTree(7.375, -0.75, 5.875, 20);
		palmTrees[23] = new palmTree(6.375, -0.75, 4.125, 120);
		palmTrees[24] = new palmTree(8.625, -0.75, 6.625, 220);
		palmTrees[25] = new palmTree(8.875, -0.75, 6.875, 20);
		palmTrees[26] = new palmTree(9.125, -0.75, 6.875, 220);
		palmTrees[27] = new palmTree(9.625, -0.75, 7.125, 320);
		palmTrees[28] = new palmTree(9.625, -0.75, 7.375, 120);
		palmTrees[29] = new palmTree(10.125, -0.75, 7.375, 270);
		palmTrees[30] = new palmTree(9.125, -0.75, 7.125, 270);
		palmTrees[31] = new palmTree(8.125, -0.75, 7.125, 270);
		palmTrees[32] = new palmTree(12.125, -0.75, 7.125, 270);
		palmTrees[33] = new palmTree(12.125, -0.75, 7.375, 270);
		palmTrees[34] = new palmTree(12.375, -0.75, 7.375, 250);
		palmTrees[35] = new palmTree(12.125, -0.75, 7.625, 270);
		palmTrees[36] = new palmTree(12.625, -0.75, 7.625, 220);
		palmTrees[37] = new palmTree(12.625, -0.75, 7.875, 270);
		palmTrees[38] = new palmTree(12.875, -0.75, 7.625, 230);
		palmTrees[39] = new palmTree(13.125, -0.75, 7.875, 270);
		palmTrees[40] = new palmTree(14.125, -0.75, 6.625, 170);
		palmTrees[41] = new palmTree(13.875, -0.75, 6.875, 270);
		palmTrees[42] = new palmTree(13.875, -0.75, 7.375, 230);
		palmTrees[43] = new palmTree(13.375, -0.75, 7.375, 170);
		palmTrees[44] = new palmTree(13.375, -0.75, 7.625, 270);
		palmTrees[45] = new palmTree(11.875, -0.75, 7.125, 330);
		palmTrees[46] = new palmTree(5.875, -0.75, 9.625, 220);
		palmTrees[47] = new palmTree(5.875, -0.75, 9.875, 220);
		palmTrees[48] = new palmTree(4.125, -0.75, 7.875, 220);
		palmTrees[49] = new palmTree(5.625, -0.75, 6.875, 220);
		palmTrees[50] = new palmTree(5.875, -0.75, 10.875, 220);
		palmTrees[51] = new palmTree(6.125, -0.75, 9.625, 220);
		palmTrees[52] = new palmTree(6.375, -0.75, 9.625, 220);
		palmTrees[53] = new palmTree(6.375, -0.75, 9.875, 220);
		palmTrees[54] = new palmTree(6.375, -0.75, 10.125, 220);
		palmTrees[55] = new palmTree(6.625, -0.75, 10.125, 220);
		palmTrees[56] = new palmTree(6.625, -0.75, 9.875, 220);
		palmTrees[57] = new palmTree(6.875, -0.75, 9.875, 220);
		palmTrees[58] = new palmTree(7.125, -0.75, 9.875, 220);
		palmTrees[59] = new palmTree(7.375, -0.75, 10.125, 220);
		palmTrees[60] = new palmTree(5.375, -0.75, 10.125, 220);
		palmTrees[61] = new palmTree(5.125, -0.75, 10.375, 220);
		palmTrees[62] = new palmTree(6.375, -0.75, 9.375, 220);
		palmTrees[63] = new palmTree(6.625, -0.75, 9.125, 210);
		palmTrees[64] = new palmTree(6.875, -0.75, 9.125, 250);
		palmTrees[65] = new palmTree(6.875, -0.75, 9.375, 220);
		palmTrees[66] = new palmTree(5.625, -0.75, 9.375, 20);
		palmTrees[67] = new palmTree(14.375, -0.75, 8.875, 120);
		palmTrees[68] = new palmTree(14.125, -0.75, 9.375, 320);
		palmTrees[69] = new palmTree(14.125, -0.75, 9.875, 270);
		palmTrees[70] = new palmTree(15.125, -0.75, 9.875, 220);
		palmTrees[71] = new palmTree(15.375, -0.75, 10.125, 290);
		palmTrees[72] = new palmTree(15.125, -0.75, 10.375, 290);
		palmTrees[73] = new palmTree(15.125, -0.75, 10.625, 290);
		palmTrees[74] = new palmTree(14.875, -0.75, 10.625, 290);
		palmTrees[75] = new palmTree(14.875, -0.75, 11.125, 290);
		palmTrees[76] = new palmTree(14.625, -0.75, 11.375, 190);
		palmTrees[77] = new palmTree(14.375, -0.75, 11.375, 190);
		palmTrees[78] = new palmTree(14.125, -0.75, 11.375, 190);
		palmTrees[79] = new palmTree(14.375, -0.75, 11.625, 170);
		palmTrees[80] = new palmTree(14.125, -0.75, 11.625, 170);
		palmTrees[81] = new palmTree(13.625, -0.75, 11.625, 170);
		palmTrees[82] = new palmTree(13.875, -0.75, 11.875, 170);
		palmTrees[83] = new palmTree(13.375, -0.75, 11.875, 170);
		palmTrees[84] = new palmTree(12.875, -0.75, 11.875, 270);
		palmTrees[85] = new palmTree(12.125, -0.75, 12.125, 130);
		palmTrees[86] = new palmTree(10.125, -0.75, 9.125, 110);
		palmTrees[87] = new palmTree(10.375, -0.75, 9.125, 110);
		palmTrees[88] = new palmTree(10.375, -0.75, 9.375, 110);
		palmTrees[89] = new palmTree(11.125, -0.75, 9.375, 110);
		palmTrees[90] = new palmTree(11.625, -0.75, 9.625, 110);
		palmTrees[91] = new palmTree(10.125, -0.75, 9.875, 110);
		palmTrees[92] = new palmTree(11.125, -0.75, 9.625, 110);
		palmTrees[93] = new palmTree(11.375, -0.75, 9.875, 210);
		palmTrees[94] = new palmTree(11.125, -0.75, 10.125, 310);
		palmTrees[95] = new palmTree(9.875,  -0.75,9.625, 10);
		palmTrees[96] = new palmTree(10.125,  -0.75,10.125, 103);
		palmTrees[97] = new palmTree(12.625,  -0.75,11.875, 110);
		palmTrees[98] = new palmTree(9.125,  -0.75,12.875, 120);
		palmTrees[99] = new palmTree(8.875,  -0.75,12.875, 130);
		palmTrees[100] = new palmTree(8.875,  -0.75,12.625, 140);
		palmTrees[101] = new palmTree(8.625,  -0.75,12.625, 150);
		palmTrees[102] = new palmTree(8.375,  -0.75,12.875, 160);
		palmTrees[103] = new palmTree(8.625,  -0.75,13.125, 170);
		palmTrees[104] = new palmTree(9.125,  -0.75,13.125, 180);
		palmTrees[105] = new palmTree(9.125,  -0.75,13.375, 190);
		palmTrees[106] = new palmTree(9.375,  -0.75,13.125, 200);
		palmTrees[107] = new palmTree(9.625,  -0.75,12.875, 210);
		palmTrees[108] = new palmTree(9.375,  -0.75,12.625, 220);
		palmTrees[109] = new palmTree(9.625,  -0.75,12.625, 230);
		palmTrees[110] = new palmTree(9.875,  -0.75,12.375, 240);
		palmTrees[111] = new palmTree(10.125,  -0.75,12.375, 250);
		palmTrees[112] = new palmTree(7.125,  -0.75,11.875,260);
		palmTrees[113] = new palmTree(6.875,  -0.75,12.125,270);
		palmTrees[114] = new palmTree(6.625,  -0.75,12.375,280);
		palmTrees[115] = new palmTree(6.375,  -0.75,12.375,290);
		palmTrees[116] = new palmTree(6.125,  -0.75,12.375,300);
		palmTrees[117] = new palmTree(6.125,  -0.75,12.625,310);
		palmTrees[118] = new palmTree(6.375,  -0.75,12.875,320);
		palmTrees[119] = new palmTree(6.375,  -0.75,12.625,330);
		palmTrees[120] = new palmTree(6.875,  -0.75,11.625,340);
		palmTrees[121] = new palmTree(5.875,  -0.75,12.375,350);
		palmTrees[122] = new palmTree(5.625,  -0.75,12.875,0);
		palmTrees[123] = new palmTree(4.875,  -0.75,12.125,10);
		palmTrees[124] = new palmTree(4.375,  -0.75,11.875,20);
		palmTrees[125] = new palmTree(4.125,  -0.75,12.125,30);
		palmTrees[126] = new palmTree(4.125,  -0.75,11.625,40);
		palmTrees[127] = new palmTree(3.875,  -0.75,11.625,50);
		palmTrees[128] = new palmTree(4.375,  -0.75,12.125,60);
		palmTrees[129] = new palmTree(4.875,  -0.75,12.375,70);
		palmTrees[130] = new palmTree(5.125,  -0.75,12.375,80);
		palmTrees[131] = new palmTree(5.375,  -0.75,12.625,90);
		palmTrees[132] = new palmTree(5.375,  -0.75,12.375,100);
		palmTrees[133] = new palmTree(14.875,  -0.75,7.375,207);
		palmTrees[134] = new palmTree(15.375,  -0.75,8.625,210);
		palmTrees[135] = new palmTree(16.125,  -0.75,9.125,150);
		palmTrees[136] = new palmTree(16.875,  -0.75,10.875,114);
		palmTrees[137] = new palmTree(16.625,  -0.75,11.625,285);
		palmTrees[138] = new palmTree(16.375,  -0.75,11.875,258);
		palmTrees[139] = new palmTree(15.375,  -0.75,12.875,102);
		palmTrees[140] = new palmTree(15.125,  -0.75,13.125,147);
		palmTrees[141] = new palmTree(15.375,  -0.75,13.375,153);
		palmTrees[142] = new palmTree(15.625,  -0.75,13.375,279);
		palmTrees[143] = new palmTree(16.125,  -0.75,13.375,264);
		palmTrees[144] = new palmTree(16.375,  -0.75,13.375,222);
		palmTrees[145] = new palmTree(16.375,  -0.75,13.625,48);
		palmTrees[146] = new palmTree(15.875,  -0.75,13.125,162);
		palmTrees[147] = new palmTree(17.125,  -0.75,13.125,117);
		palmTrees[148] = new palmTree(16.875,  -0.75,13.375,273);
		palmTrees[149] = new palmTree(15.375,  -0.75,13.125,276);
		palmTrees[150] = new palmTree(13.125,  -0.75,12.125,273);
		palmTrees[151] = new palmTree(11.125,  -0.75,14.625,234);
		palmTrees[152] = new palmTree(11.125,  -0.75,14.375,195);
		palmTrees[153] = new palmTree(11.375,  -0.75,14.125,261);
		palmTrees[154] = new palmTree(11.625,  -0.75,14.375,261);
		palmTrees[155] = new palmTree(11.625,  -0.75,14.125,285);
		palmTrees[156] = new palmTree(11.875,  -0.75,14.375,270);
		palmTrees[157] = new palmTree(12.125,  -0.75,14.625,222);
		palmTrees[158] = new palmTree(11.875,  -0.75,14.875,84);
		palmTrees[159] = new palmTree(12.125,  -0.75,14.875,177);
		palmTrees[160] = new palmTree(12.125,  -0.75,15.125,183);
		palmTrees[161] = new palmTree(12.375,  -0.75,14.875,93);
		palmTrees[162] = new palmTree(10.375,  -0.75,14.375,204);
		palmTrees[163] = new palmTree(10.375,  -0.75,14.625,162);
		palmTrees[164] = new palmTree(10.375,  -0.75,14.875,6);
		palmTrees[165] = new palmTree(10.125,  -0.75,14.625,285);
		palmTrees[166] = new palmTree(6.625,  -0.75,12.875,99);
		palmTrees[167] = new palmTree(6.875,  -0.75,13.125,12);
		palmTrees[168] = new palmTree(6.875,  -0.75,13.375,156);
		palmTrees[169] = new palmTree(6.625,  -0.75,13.375,183);
		palmTrees[170] = new palmTree(7.125,  -0.75,13.625,126);
		palmTrees[171] = new palmTree(6.375,  -0.75,15.125,30);
		palmTrees[172] = new palmTree(6.375,  -0.75,15.375,33);
		palmTrees[173] = new palmTree(6.625,  -0.75,15.625,174);
		palmTrees[174] = new palmTree(6.875,  -0.75,15.625,12);
		palmTrees[175] = new palmTree(7.125,  -0.75,15.625,228);
		palmTrees[176] = new palmTree(6.875,  -0.75,15.875,234);
		palmTrees[177] = new palmTree(7.125,  -0.75,15.875,111);
		palmTrees[178] = new palmTree(7.625,  -0.75,16.125,42);
		palmTrees[179] = new palmTree(7.875,  -0.75,15.625,210);
		palmTrees[180] = new palmTree(7.875,  -0.75,15.875,6);
		palmTrees[181] = new palmTree(8.125,  -0.75,15.875,198);
		palmTrees[182] = new palmTree(8.375,  -0.75,15.875,288);
		palmTrees[183] = new palmTree(8.625,  -0.75,15.625,255);
		palmTrees[184] = new palmTree(8.625,  -0.75,15.375,114);
		palmTrees[185] = new palmTree(9.125,  -0.75,15.125,153);
		palmTrees[186] = new palmTree(9.125,  -0.75,15.375,264);
		palmTrees[187] = new palmTree(7.875,  -0.75,16.125,99);
		palmTrees[188] = new palmTree(7.625,  -0.75,16.375,144);
		palmTrees[189] = new palmTree(7.625,  -0.75,16.625,90);
		palmTrees[190] = new palmTree(7.875,  -0.75,16.875,72);
		palmTrees[191] = new palmTree(8.125,  -0.75,17.125,189);
		palmTrees[192] = new palmTree(7.375,  -0.75,16.125,285);
		palmTrees[193] = new palmTree(7.375,  -0.75,16.625,6);
		palmTrees[194] = new palmTree(7.375,  -0.75,16.375,225);
		palmTrees[195] = new palmTree(7.125,  -0.75,16.375,258);
		palmTrees[196] = new palmTree(4.875,  -0.75,15.625,288);
		palmTrees[197] = new palmTree(7.875,  -0.75,18.625,111);
		palmTrees[198] = new palmTree(6.625,  -0.75,20.875,228);
		palmTrees[199] = new palmTree(6.375,  -0.75,20.875,135);
		palmTrees[200] = new palmTree(6.375,  -0.75,21.125,258);
		palmTrees[201] = new palmTree(6.625,  -0.75,21.125,225);
		palmTrees[202] = new palmTree(6.625,  -0.75,21.375,222);
		palmTrees[203] = new palmTree(6.125,  -0.75,21.125,0);
		palmTrees[204] = new palmTree(6.125,  -0.75,20.875,171);
		palmTrees[205] = new palmTree(6.125,  -0.75,20.625,174);
		palmTrees[206] = new palmTree(5.625,  -0.75,20.875,6);
		palmTrees[207] = new palmTree(5.875,  -0.75,21.125,150);
		palmTrees[208] = new palmTree(7.625,  -0.75,21.125,198);
		palmTrees[209] = new palmTree(7.875,  -0.75,21.875,273);
		palmTrees[210] = new palmTree(6.375,  -0.75,20.375,36);
		palmTrees[211] = new palmTree(6.375,  -0.75,20.125,45);
		palmTrees[212] = new palmTree(6.125,  -0.75,19.875,42);
		palmTrees[213] = new palmTree(6.375,  -0.75,19.625,24);
		palmTrees[214] = new palmTree(6.625,  -0.75,19.625,42);
		palmTrees[215] = new palmTree(6.875,  -0.75,19.125,150);
		palmTrees[216] = new palmTree(5.625,  -0.75,20.125,60);
		palmTrees[217] = new palmTree(7.125,  -0.75,18.875,114);
		palmTrees[218] = new palmTree(7.625,  -0.75,18.875,81);
		palmTrees[219] = new palmTree(6.875,  -0.75,19.875,201);
		palmTrees[220] = new palmTree(1.875,  -0.75,17.375,51);
		palmTrees[221] = new palmTree(3.875,  -0.75,18.875,147);
		palmTrees[222] = new palmTree(4.125,  -0.75,18.625,237);
		palmTrees[223] = new palmTree(3.875,  -0.75,18.125,120);
		palmTrees[224] = new palmTree(4.125,  -0.75,17.625,258);
		palmTrees[225] = new palmTree(3.125,  -0.75,19.625,54);
		palmTrees[226] = new palmTree(2.875,  -0.75,19.375,114);
		palmTrees[227] = new palmTree(2.125,  -0.75,20.375,240);
		palmTrees[228] = new palmTree(2.125,  -0.75,19.875,255);
		palmTrees[229] = new palmTree(3.125,  -0.75,20.375,138);
		palmTrees[230] = new palmTree(13.875,  -0.75,16.625,51);
		palmTrees[231] = new palmTree(13.625,  -0.75,16.625,297);
		palmTrees[232] = new palmTree(13.375,  -0.75,16.375,117);
		palmTrees[233] = new palmTree(13.875,  -0.75,16.375,249);
		palmTrees[234] = new palmTree(14.125,  -0.75,16.625,126);
		palmTrees[235] = new palmTree(14.625,  -0.75,16.625,252);
		palmTrees[236] = new palmTree(14.625,  -0.75,16.875,51);
		palmTrees[237] = new palmTree(14.625,  -0.75,17.125,177);
		palmTrees[238] = new palmTree(15.125,  -0.75,17.125,141);
		palmTrees[239] = new palmTree(15.375,  -0.75,17.125,297);
		palmTrees[240] = new palmTree(15.125,  -0.75,17.375,216);
		palmTrees[241] = new palmTree(15.625,  -0.75,17.375,18);
		palmTrees[242] = new palmTree(15.375,  -0.75,16.125,12);
		palmTrees[243] = new palmTree(15.375,  -0.75,15.625,132);
		palmTrees[244] = new palmTree(15.125,  -0.75,15.875,162);
		palmTrees[245] = new palmTree(14.375,  -0.75,17.375,264);
		palmTrees[246] = new palmTree(15.625,  -0.75,17.625,27);
		palmTrees[247] = new palmTree(15.875,  -0.75,17.875,240);
		palmTrees[248] = new palmTree(16.375,  -0.75,18.375,231);
		palmTrees[249] = new palmTree(16.875,  -0.75,18.625,48);
		palmTrees[250] = new palmTree(17.625,  -0.75,18.375,159);
		palmTrees[251] = new palmTree(18.125,  -0.75,17.875,48);
		palmTrees[252] = new palmTree(18.625,  -0.75,17.375,57);
		palmTrees[253] = new palmTree(17.375,  -0.75,18.625,216);
		palmTrees[254] = new palmTree(17.875,  -0.75,18.375,117);
		palmTrees[255] = new palmTree(18.125,  -0.75,18.125,39);
		palmTrees[256] = new palmTree(17.125,  -0.75,18.875,150);
		palmTrees[257] = new palmTree(17.125,  -0.75,18.625,195);
		palmTrees[258] = new palmTree(16.375,  -0.75,18.875,210);
		palmTrees[259] = new palmTree(16.125,  -0.75,18.375,261);
		palmTrees[260] = new palmTree(16.125,  -0.75,18.125,159);
		palmTrees[261] = new palmTree(18.625,  -0.75,15.375,81);
		palmTrees[262] = new palmTree(2.875,  -0.75,23.875,153);
		palmTrees[263] = new palmTree(5.625,  -0.75,22.625,30);
		palmTrees[264] = new palmTree(5.875,  -0.75,22.875,237);
		palmTrees[265] = new palmTree(5.375,  -0.75,23.125,120);
		palmTrees[266] = new palmTree(5.125,  -0.75,22.375,156);
		palmTrees[267] = new palmTree(4.875,  -0.75,21.625,42);
		palmTrees[268] = new palmTree(4.375,  -0.75,21.375,282);
		palmTrees[269] = new palmTree(4.875,  -0.75,23.125,210);
		palmTrees[270] = new palmTree(4.625,  -0.75,22.875,54);
		palmTrees[271] = new palmTree(4.375,  -0.75,23.125,99);
		palmTrees[272] = new palmTree(5.125,  -0.75,22.875,90);
		palmTrees[273] = new palmTree(5.625,  -0.75,22.875,249);
		palmTrees[274] = new palmTree(4.625,  -0.75,24.625,171);
		palmTrees[275] = new palmTree(4.875,  -0.75,25.125,81);
		palmTrees[276] = new palmTree(5.125,  -0.75,24.875,51);
		palmTrees[277] = new palmTree(5.125,  -0.75,25.125,45);
		palmTrees[278] = new palmTree(5.125,  -0.75,25.625,84);
		palmTrees[279] = new palmTree(5.375,  -0.75,25.875,183);
		palmTrees[280] = new palmTree(5.125,  -0.75,25.375,15);
		palmTrees[281] = new palmTree(5.375,  -0.75,25.125,27);
		palmTrees[282] = new palmTree(4.375,  -0.75,23.875,198);
		palmTrees[283] = new palmTree(4.125,  -0.75,24.375,6);
		palmTrees[284] = new palmTree(7.375,  -0.75,24.375,180);
		palmTrees[285] = new palmTree(6.125,  -0.75,24.875,216);
		palmTrees[286] = new palmTree(6.125,  -0.75,24.375,285);
		palmTrees[287] = new palmTree(7.125,  -0.75,26.875,147);
		palmTrees[288] = new palmTree(6.875,  -0.75,27.125,42);
		palmTrees[289] = new palmTree(6.375,  -0.75,27.375,81);
		palmTrees[290] = new palmTree(6.875,  -0.75,26.375,219);
		palmTrees[291] = new palmTree(6.625,  -0.75,26.375,282);
		palmTrees[292] = new palmTree(6.375,  -0.75,26.125,54);
		palmTrees[293] = new palmTree(17.375,  -0.75,12.375,168);
		palmTrees[294] = new palmTree(7.875,  -0.75,24.375,18);
		palmTrees[295] = new palmTree(8.125,  -0.75,24.875,162);
		palmTrees[296] = new palmTree(8.125,  -0.75,24.625,48);
		palmTrees[297] = new palmTree(8.375,  -0.75,24.875,288);
		palmTrees[298] = new palmTree(7.875,  -0.75,25.125,249);
		palmTrees[299] = new palmTree(8.375,  -0.75,25.125,195);
		palmTrees[300] = new palmTree(9.375,  -0.75,25.125,66);
		palmTrees[301] = new palmTree(9.875,  -0.75,24.875,120);
		palmTrees[302] = new palmTree(10.125,  -0.75,25.125,180);
		palmTrees[303] = new palmTree(10.125,  -0.75,25.625,111);
		palmTrees[304] = new palmTree(10.375,  -0.75,25.375,72);
		palmTrees[305] = new palmTree(10.375,  -0.75,25.125,273);
		palmTrees[306] = new palmTree(10.625,  -0.75,25.125,45);
		palmTrees[307] = new palmTree(10.875,  -0.75,25.125,183);
		palmTrees[308] = new palmTree(11.125,  -0.75,25.375,153);
		palmTrees[309] = new palmTree(11.375,  -0.75,25.375,144);
		palmTrees[310] = new palmTree(11.625,  -0.75,25.125,297);
		palmTrees[311] = new palmTree(11.125,  -0.75,25.625,168);
		palmTrees[312] = new palmTree(10.875,  -0.75,25.875,228);
		palmTrees[313] = new palmTree(11.125,  -0.75,25.875,210);
		palmTrees[314] = new palmTree(11.875,  -0.75,25.375,225);
		palmTrees[315] = new palmTree(12.125,  -0.75,25.375,12);
		palmTrees[316] = new palmTree(12.125,  -0.75,25.875,147);
		palmTrees[317] = new palmTree(11.875,  -0.75,24.875,102);
		palmTrees[318] = new palmTree(10.875,  -0.75,24.625,0);
		palmTrees[319] = new palmTree(9.125,  -0.75,27.375,138);
		palmTrees[320] = new palmTree(9.125,  -0.75,27.625,228);
		palmTrees[321] = new palmTree(9.375,  -0.75,27.625,102);
		palmTrees[322] = new palmTree(9.625,  -0.75,27.875,141);
		palmTrees[323] = new palmTree(8.875,  -0.75,27.625,243);
		palmTrees[324] = new palmTree(8.625,  -0.75,27.875,114);
		palmTrees[325] = new palmTree(8.625,  -0.75,28.125,150);
		palmTrees[326] = new palmTree(8.875,  -0.75,28.375,171);
		palmTrees[327] = new palmTree(8.375,  -0.75,27.625,249);
		palmTrees[328] = new palmTree(5.625,  -0.75,28.125,69);
		palmTrees[329] = new palmTree(5.125,  -0.75,27.125,57);
		palmTrees[330] = new palmTree(5.625,  -0.75,26.125,222);
		palmTrees[331] = new palmTree(6.875,  -0.75,30.375,213);
		palmTrees[332] = new palmTree(7.375,  -0.75,30.125,87);
		palmTrees[333] = new palmTree(6.375,  -0.75,29.625,18);
		palmTrees[333] = new palmTree(7.625,  -0.75,28.625,63);
		palmTrees[334] = new palmTree(7.625,  -0.75,28.125,228);
		palmTrees[335] = new palmTree(6.875,  -0.75,27.875,135);
		palmTrees[336] = new palmTree(9.125,  -0.75,28.125,249);
		palmTrees[337] = new palmTree(8.875,  -0.75,28.125,279);
		palmTrees[338] = new palmTree(8.375,  -0.75,30.375,210);
		palmTrees[339] = new palmTree(9.375,  -0.75,29.625,0);
		palmTrees[340] = new palmTree(9.125,  -0.75,29.875,111);
		palmTrees[341] = new palmTree(8.375,  -0.75,29.875,141);
		palmTrees[342] = new palmTree(8.125,  -0.75,30.125,108);
		palmTrees[343] = new palmTree(9.875,  -0.75,30.875,45);
		palmTrees[344] = new palmTree(10.375,  -0.75,30.625,150);
		palmTrees[345] = new palmTree(10.625,  -0.75,30.375,261);
		palmTrees[346] = new palmTree(10.375,  -0.75,30.125,168);
		palmTrees[347] = new palmTree(10.375,  -0.75,29.625,105);
		palmTrees[348] = new palmTree(10.875,  -0.75,30.125,213);
		palmTrees[349] = new palmTree(10.625,  -0.75,29.875,216);
		palmTrees[350] = new palmTree(10.625,  -0.75,30.625,18);
		palmTrees[351] = new palmTree(10.375,  -0.75,31.125,165);
		palmTrees[352] = new palmTree(11.875,  -0.75,31.125,276);
		palmTrees[353] = new palmTree(10.625,  -0.75,30.125,96);
		palmTrees[354] = new palmTree(10.875,  -0.75,28.625,282);
		palmTrees[355] = new palmTree(11.125,  -0.75,28.375,147);
		palmTrees[356] = new palmTree(9.875,  -0.75,29.125,21);
		palmTrees[357] = new palmTree(12.625,  -0.75,28.125,120);
		palmTrees[358] = new palmTree(12.875,  -0.75,28.375,36);
		palmTrees[359] = new palmTree(12.625,  -0.75,28.625,15);
		palmTrees[360] = new palmTree(12.875,  -0.75,28.625,60);
		palmTrees[361] = new palmTree(13.125,  -0.75,28.875,45);
		palmTrees[362] = new palmTree(13.625,  -0.75,28.875,15);
		palmTrees[363] = new palmTree(13.375,  -0.75,28.875,123);
		palmTrees[364] = new palmTree(13.375,  -0.75,29.125,177);
		palmTrees[365] = new palmTree(14.125,  -0.75,29.875,165);
		palmTrees[366] = new palmTree(13.625,  -0.75,29.125,261);
		palmTrees[366] = new palmTree(14.375,  -0.75,29.375,108);
		palmTrees[367] = new palmTree(14.125,  -0.75,29.125,261);
		palmTrees[368] = new palmTree(13.875,  -0.75,29.125,0);
		palmTrees[369] = new palmTree(12.375,  -0.75,25.625,93);
		palmTrees[370] = new palmTree(13.375,  -0.75,25.625,168);
		palmTrees[371] = new palmTree(13.125,  -0.75,25.875,228);
		palmTrees[372] = new palmTree(13.375,  -0.75,25.875,33);
		palmTrees[373] = new palmTree(13.625,  -0.75,26.125,222);
		palmTrees[374] = new palmTree(14.125,  -0.75,27.375,207);
		palmTrees[375] = new palmTree(14.125,  -0.75,26.625,0);
		palmTrees[376] = new palmTree(14.375,  -0.75,26.875,267);
		palmTrees[377] = new palmTree(14.375,  -0.75,27.125,267);
		palmTrees[378] = new palmTree(16.375,  -0.75,28.125,189);
		palmTrees[379] = new palmTree(15.625,  -0.75,28.125,168);
		palmTrees[380] = new palmTree(15.375,  -0.75,28.125,219);
		palmTrees[381] = new palmTree(15.875,  -0.75,27.875,225);
		palmTrees[382] = new palmTree(16.125,  -0.75,27.875,213);
		palmTrees[383] = new palmTree(15.875,  -0.75,29.125,243);
		palmTrees[384] = new palmTree(16.625,  -0.75,28.625,150);
		palmTrees[385] = new palmTree(16.625,  -0.75,28.125,180);
		palmTrees[386] = new palmTree(16.125,  -0.75,27.375,285);
		palmTrees[387] = new palmTree(16.125,  -0.75,27.625,174);
		palmTrees[388] = new palmTree(18.625,  -0.75,21.375,213);
		palmTrees[389] = new palmTree(18.875,  -0.75,21.625,105);
		palmTrees[390] = new palmTree(18.875,  -0.75,21.875,243);
		palmTrees[391] = new palmTree(18.625,  -0.75,21.875,273);
		palmTrees[392] = new palmTree(18.625,  -0.75,22.125,225);
		palmTrees[393] = new palmTree(18.375,  -0.75,22.375,54);
		palmTrees[394] = new palmTree(18.375,  -0.75,22.625,135);
		palmTrees[395] = new palmTree(18.875,  -0.75,21.125,243);
		palmTrees[396] = new palmTree(18.125,  -0.75,22.625,165);
		palmTrees[397] = new palmTree(18.125,  -0.75,22.875,144);
		palmTrees[398] = new palmTree(18.625,  -0.75,22.375,186);
		palmTrees[399] = new palmTree(14.875,  -0.75,23.375,234);
		palmTrees[400] = new palmTree(14.625,  -0.75,23.375,192);
		palmTrees[401] = new palmTree(14.375,  -0.75,23.625,99);
		palmTrees[402] = new palmTree(14.125,  -0.75,23.875,42);
		palmTrees[403] = new palmTree(13.875,  -0.75,23.625,40);
		palmTrees[404] = new palmTree(18.125,  -0.75,23.125,267);
		palmTrees[405] = new palmTree(17.875,  -0.75,23.125,60);
		palmTrees[406] = new palmTree(17.875,  -0.75,23.375,96);
		palmTrees[407] = new palmTree(17.625,  -0.75,23.625,150);
		palmTrees[408] = new palmTree(15.875,  -0.75,20.625,198);
		palmTrees[409] = new palmTree(15.625,  -0.75,20.875,105);
		palmTrees[410] = new palmTree(15.875,  -0.75,20.125,171);
		palmTrees[411] = new palmTree(15.625,  -0.75,22.375,273);
		palmTrees[412] = new palmTree(15.375,  -0.75,22.625,156);
		palmTrees[413] = new palmTree(15.125,  -0.75,22.875,198);
		palmTrees[414] = new palmTree(15.625,  -0.75,22.875,66);
		palmTrees[415] = new palmTree(17.625,  -0.75,23.875,96);
		palmTrees[416] = new palmTree(17.875,  -0.75,24.125,249);
		palmTrees[417] = new palmTree(17.875,  -0.75,24.375,99);
		palmTrees[418] = new palmTree(18.125,  -0.75,24.625,123);
		palmTrees[419] = new palmTree(17.875,  -0.75,23.875,285);
		palmTrees[420] = new palmTree(18.125,  -0.75,24.375,213);
		palmTrees[421] = new palmTree(17.625,  -0.75,25.625,24);
		palmTrees[422] = new palmTree(17.375,  -0.75,25.875,288);
		palmTrees[423] = new palmTree(17.375,  -0.75,26.125,72);
		palmTrees[424] = new palmTree(17.125,  -0.75,26.375,165);
		palmTrees[425] = new palmTree(17.125,  -0.75,26.125,219);
		palmTrees[426] = new palmTree(15.125,  -0.75,24.875,9);
		palmTrees[427] = new palmTree(14.875,  -0.75,25.375,228);
		palmTrees[428] = new palmTree(15.625,  -0.75,25.375,6);
		
		//create powerPlants
		
		powerPlants = new powerPlant[7];
		powerPlants[0] = new powerPlant(9, -1, 30.5);
		powerPlants[1] = new powerPlant(9.75, -1, 30);
		powerPlants[2] = new powerPlant(9.25, -1, 19.25);
		powerPlants[3] = new powerPlant(9.25, -1, 21.25);
		powerPlants[4] = new powerPlant(10.25, -1, 20.25);
		powerPlants[5] = new powerPlant(16, -1, 28.5);
		powerPlants[6] = new powerPlant(16.5, -1, 27.5);
	
	
	}
	
	//update models to camera coordinate
	public void update(){
		for(int i = 0; i < land.length; i++)
			land[i].update();
		
		for(int i = 0; i < concreteFloor.length; i++)
			concreteFloor[i].update();
		
		for(int i = 0; i < road.length; i++)
			road[i].update();
	
		for(int i = 0; i < underWaterRocks.length; i++)
			if(underWaterRocks[i] != null)
				underWaterRocks[i].update();
		
		for(int i = 0; i < aboveWaterRocks.length; i++)
			aboveWaterRocks[i].update();
		
		for(int i = 0; i < inlandRocks.length; i++){
			if(inlandRocks[i] != null)
				inlandRocks[i].update();
		}
		
		for(int i = 0; i < walls.length; i++){
			walls[i].update();
		}
		
		for(int i = 0; i < fences.length; i++){
			if(fences[i] != null)
				fences[i].update();
		}
		
		for(int i = 0; i < palmTrees.length; i++){
			if(palmTrees[i] != null)
				palmTrees[i].update();
		}
	
		for(int i = 0; i < powerPlants.length; i++){
			//remove  powerPlants that has been destroyed
			if(powerPlants[i] != null)
				if(powerPlants[i].getLifeSpan() <= 0)
					powerPlants[i] = null;
					
			if(powerPlants[i] != null)
				powerPlants[i].update();
		}
		
		for(int i = 0; i < water.length; i++)
			water[i].update();
		
		//animate wave pattern
		main.textures[4].waveIndex++;
		main.textures[4].waveIndex%=128;
		
		
		
	}
	
	//draw  things  which do not interact with anything
	public void draw(){
		for(int i = 0; i < road.length; i++)
			road[i].draw();
		
		for(int i = 0; i < concreteFloor.length; i++)
			concreteFloor[i].draw();
		
		for(int i = 0; i < land.length; i++)
			land[i].draw();
		
		for(int i = 0; i < underWaterRocks.length; i++){
			if(underWaterRocks[i] != null)
				underWaterRocks[i].draw();
		}
		
		
		for(int i = 0; i < water.length; i++)
			water[i].draw();
	
		
		
		
		for(int i = 0; i < aboveWaterRocks.length; i++){
			aboveWaterRocks[i].draw();
		}
	}
	
}
