package core;
//this class store the impassable objects in tile maps.
//Store both static (trees, rocks) and non-static objects(tanks)
//Non-static object could only occupy 1 tile at a time.

public class ObstacleMap {
	//Type 1 obstacle map store the object that are impassable for
	//player tank, enemy tanks, but projectiles could still pass through
	public static Model[] obstacleMap1;
	
	//Type 2 obstacle map store the object that could stop  projectiles.
	public static Model[] obstacleMap2;
	
	//the position indexes of the surrounding 9 , 25, 49 tiles 
	public static int[] indexes, indexes2, indexes3;
	
	//Initialize obstacleMaps.
	//The entire terrain is divided into 80 * 130 tiles
	public static void init(){
		obstacleMap1 = new Model[80*130];
		
		obstacleMap2 = new Model[80*130];
		
		indexes = new int[9];
		indexes2 = new int[25];
		
		indexes3 = new int[49];
		
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 7; j++){
				indexes3[j + i*7] =  - 243 + j + i*80;
			}
		}
	}
	
	//regeister a type 1 obstacle ;
	public static void registerObstacle1(Model obstacle, int position){
		if(obstacleMap1[position] == null)
			obstacleMap1[position] = obstacle;
	}
	
	//regeister a type 2 obstacle ;
	public static void registerObstacle2(Model obstacle, int position){
		if(position < 0 || position >= 10400)
			return;
		if(obstacleMap2[position] == null)
			obstacleMap2[position] = obstacle;
	}
	
	//remove a type 2 obstacle (i.e a tank got destoried or moved to another position)
	public static void removeObstacle2(int position){
		if(position < 0 || position >= 10400)
			return;
		obstacleMap2[position] = null;
	}
	
	//remove all the enemy position
	public static void clear(){
		for(int i = 0; i < obstacleMap2.length; i++){
			if(obstacleMap2[i] != null){
				if(obstacleMap2[i].getType() == 2){
					obstacleMap2[i] = null;
				}
			}
		}
	}
	
	//collision detection: 3D model against type 1 obstacles
	public static boolean collideWithObstacle1(Model testObject, int position){
		//find out the 9  position index around the test object
		indexes[0] = position -81;
		indexes[1] = position -80;
		indexes[2] = position -79;
		indexes[3] = position -1;
		indexes[4] = position;
		indexes[5] = position +1;
		indexes[6] = position +79;
		indexes[7] = position +80;
		indexes[8] = position +81;
		
		//check if any type 1 obstacle inside the 9 tiles collide with the test object
		for(int i = 0; i < 9; i++){
			if(indexes[i] < 0 || indexes[i] >= 10400)
				continue;
			if(obstacleMap1[indexes[i]] == null)
				continue;
			if(Rectangle2D.testIntersection(obstacleMap1[indexes[i]].getBoundary2D(), testObject.getBoundary2D())){
				return true;
			}
		}
		
		return false;
	}
	
	//collision detection: 3D model against type 2 obstacles
	public static boolean collideWithObstacle2(Model testObject, int position){
		//find out  position indexes around the test object
		
		/*for(int i = 0; i < 7; i++){
			for(int j = 0; j < 7; j++){
				indexes3[j + i*7] = position - 243 + j + i*80;
			}
		}*/
		
		
		
		//check if any type 2 obstacle inside the 25 tiles collide with the test object
		for(int i = 0; i < 49; i++){
			int temp = indexes3[i] + position;
			if(temp < 0 || temp >= 10400)
				continue;
			if(obstacleMap2[temp] == null || obstacleMap2[temp] == testObject)
				continue;
			if(Rectangle2D.testIntersection(obstacleMap2[temp].getBoundary2D(), testObject.getBoundary2D())){
				return true;
			}
		}
		
		return false;
	}
	
	//collision detection: projectile and surrounding type 2 obstacles
	public static boolean projectileCollideObstacle2(Model projectile, int position, boolean hostile){
		//find out  position indexes around the test object
		
		indexes2[0] = position -162; indexes2[1] = position -161; indexes2[2] = position -160; indexes2[3] = position -159; indexes2[4] = position - 158;
		
		indexes2[5] = position -82; indexes2[6] = position -81; indexes2[7] = position -80; indexes2[8] = position -79; indexes2[9] = position -78;
		
		indexes2[10] = position -2; indexes2[11] = position - 1; indexes2[12] = position; indexes2[13] = position +1; indexes2[14] = position +2;
		
		indexes2[15] = position + 78; indexes2[16] = position + 79; indexes2[17] = position + 80; indexes2[18] = position  + 81; indexes2[19] = position  + 82;
		
		indexes2[20] = position + 158; indexes2[21] = position +159; indexes2[22] = position +160; indexes2[23] = position +161; indexes2[24] = position + 162;
		
		
		//check if any type 2 obstacle inside the 9 tiles collide with the test object
		for(int i = 0; i < 25; i++){
			//hostile projectiles are fired by enemy tanks, they won't need be tested against  other enemy tanks
			int temp = indexes2[i];
			if(hostile){
				if(temp < 0 || temp >= 10400)
					continue;
				if(obstacleMap2[temp] == null)
					continue;
				if(Rectangle2D.testIntersection(obstacleMap2[temp].getBoundary2D(), projectile.getBoundary2D())){
					if(obstacleMap2[temp].getType() != 2)
						return true;
					else
						continue;
				}
			}
			
			//non-hostile projectiles are fired by player, they won't  be tested against player tank itself
			if(!hostile){
				if(temp < 0 || temp >= 10400)
					continue;
				if(obstacleMap2[temp] == null || obstacleMap2[temp].getType() == 1)
					continue;
				if(Rectangle2D.testIntersection(obstacleMap2[temp].getBoundary2D(), projectile.getBoundary2D())){
					return true;
				}
			}
		
		}
		
		
		return false;
	}
	
	//collision detection: railgun slug against surrounding type 2 obstacles
	public static boolean slugCollideObstacle2(Model projectile, int position, boolean hostile){
		//find out  position indexes around the test object
		
		indexes2[0] = position -162; indexes2[1] = position -161; indexes2[2] = position -160; indexes2[3] = position -159; indexes2[4] = position - 158;
		
		indexes2[5] = position -82; indexes2[6] = position -81; indexes2[7] = position -80; indexes2[8] = position -79; indexes2[9] = position -78;
		
		indexes2[10] = position -2; indexes2[11] = position - 1; indexes2[12] = position; indexes2[13] = position +1; indexes2[14] = position +2;
		
		indexes2[15] = position + 78; indexes2[16] = position + 79; indexes2[17] = position + 80; indexes2[18] = position  + 81; indexes2[19] = position  + 82;
		
		indexes2[20] = position + 158; indexes2[21] = position +159; indexes2[22] = position +160; indexes2[23] = position +161; indexes2[24] = position + 162;
		
		
	
		
		//check if any type 2 obstacle inside the 9 tiles collide with the test object
		for(int i = 0; i < 25; i++){
			//hostile projectiles are fired by enemy tanks, they won't need be tested against  other enemy tanks
			int temp = indexes2[i];
			if(hostile){
				if(temp < 0 || temp >= 10400)
					continue;
				if(obstacleMap2[temp] == null)
					continue;
				Rectangle2D tempRect = obstacleMap2[temp].getBoundary2D();
				if(obstacleMap2[temp].getType() == 1){
					tempRect = new Rectangle2D(obstacleMap2[temp].getBoundary2D().xPos - 0.01,
											   obstacleMap2[temp].getBoundary2D().yPos + 0.01,											
											   0.16,
											   0.16
																							);
				}
				
				
				if(Rectangle2D.testIntersection(tempRect, projectile.getBoundary2D())){
					if(obstacleMap2[temp].getType() == 3 || obstacleMap2[temp].getType() == 6){
						Explosion theExplosion = new Explosion(projectile.getRealCentre().x, -0.9, projectile.getRealCentre().z, 0.5);
						theExplosion.damage = 0;
						theExplosion.type = 2;
						Projectiles.register(theExplosion);
						obstacleMap2[temp].damage(100);
						
						return true;
					}
					if(obstacleMap2[temp].getType() == 1){
						Explosion theExplosion = new Explosion(projectile.getRealCentre().x, -0.9, projectile.getRealCentre().z, 0.5);
						theExplosion.damage = 0;
						theExplosion.type = 2;
						Projectiles.register(theExplosion);
						obstacleMap2[temp].damage(10);
					
					}
				}
			}
			
			//non-hostile projectiles are fired by player, they won't  be tested against player tank itself
			if(!hostile){
				if(temp < 0 || temp >= 10400)
					continue;
				if(obstacleMap2[temp] == null || obstacleMap2[temp].getType() == 1)
					continue;
				if(Rectangle2D.testIntersection(obstacleMap2[temp].getBoundary2D(), projectile.getBoundary2D())){
					if(obstacleMap2[temp].getType() == 3 || obstacleMap2[temp].getType() == 6){
						Explosion theExplosion = new Explosion(projectile.getRealCentre().x, -0.9, projectile.getRealCentre().z, 0.5);
						theExplosion.damage = 0;
						theExplosion.type = 2;
						Projectiles.register(theExplosion);
						obstacleMap2[temp].damage(100);
						
						
						return true;
					}
					if(obstacleMap2[temp].getType() == 2){
						obstacleMap2[temp].damage(8);
						Explosion theExplosion = new Explosion(projectile.getRealCentre().x, -0.9, projectile.getRealCentre().z, 0.5);
						theExplosion.damage = 0;
						theExplosion.type = 2;
						Projectiles.register(theExplosion);
					}
				}
			}
		
		}
		
		
		return false;
	}
	
	
	//test if a tile is occupied by a type 2 obstacle 
	public static boolean isOccupied(int position){
		if(position < 0 || position >= 10400)
			return false;
		return obstacleMap2[position] != null;
	}
	
	//test if a tile is occupied by a static type 2 obstacles)
	public static Model isOccupied2(Vector position){
		int index = (int)(position.x*4) + (129-(int)(position.z*4))*80;
		if(index < 0 || index >= 10400){
			return null;
		}
		if(obstacleMap2[index] == null || obstacleMap2[index].getType() == 2){
			
			return null;
		}
		return obstacleMap2[index];
	}
	
	//test if there any enemy units around a location
	public static Vector isOccupied3(Vector position){
		int index = (int)(position.x*4) + (129-(int)(position.z*4))*80;
		//find out the 9  position index around the test object
		indexes[0] = index -81;
		indexes[1] = index -80;
		indexes[2] = index -79;
		indexes[3] = index -1;
		indexes[4] = index;
		indexes[5] = index +1;
		indexes[6] = index +79;
		indexes[7] = index +80;
		indexes[8] = index +81;
		
		for(int i = 0; i < 9; i++){
			index = indexes[i];
			if(index < 0 || index >= 10400){
				continue;
			}
			
			if(obstacleMap2[index] != null && obstacleMap2[index].getType() == 2){
				return obstacleMap2[index].getRealCentre();
			}
		}
		
		return null;
	}
	

	
	//damage type2 obstacles
	public static void damageType2Obstacles(int damage, Rectangle2D blastArea, int groundZero){
		indexes2[0] = groundZero -162; indexes2[1] = groundZero -161; indexes2[2] = groundZero -160; indexes2[3] = groundZero -159; indexes2[4] = groundZero - 158;
		
		indexes2[5] = groundZero -82; indexes2[6] = groundZero -81; indexes2[7] = groundZero -80; indexes2[8] = groundZero -79; indexes2[9] = groundZero -78;
		
		indexes2[10] = groundZero -2; indexes2[11] = groundZero - 1; indexes2[12] = groundZero; indexes2[13] = groundZero +1; indexes2[14] = groundZero +2;
		
		indexes2[15] = groundZero + 78; indexes2[16] = groundZero + 79; indexes2[17] = groundZero + 80; indexes2[18] = groundZero  + 81; indexes2[19] = groundZero  + 82;
		
		indexes2[20] = groundZero + 158; indexes2[21] = groundZero +159; indexes2[22] = groundZero +160; indexes2[23] = groundZero +161; indexes2[24] = groundZero + 162;
		
		for(int i = 0; i < 25; i++){
			int temp = indexes2[i];
			if(temp < 0 || temp >= 10400)
				continue;
			if(obstacleMap2[temp] == null)
				continue;
			if(Rectangle2D.testIntersection(obstacleMap2[temp].getBoundary2D(), blastArea)){
				obstacleMap2[temp].damage(damage);
			}else{
				obstacleMap2[temp].damage(0);
			}
		}	
	}
	
	//tell surrounding enemy tanks to move away
	public static void giveWay(Model tank, int index){
		indexes[1] = index -81;
		indexes[2] = index -80;
		indexes[3] = index -79;
		indexes[4] = index -1;
		indexes[5] = index +1;
		indexes[6] = index +79;
		indexes[7] = index +80;
		indexes[8] = index +81;
		
		for(int i = 1; i < 9; i++){
			if(obstacleMap2[indexes[i]] != null && obstacleMap2[indexes[i]].getType() == 2 && obstacleMap2[indexes[i]] != tank){
				obstacleMap2[indexes[i]].unstuck();
				
			}
		}
		
		
	}
	
	//alert near by enemy in a 7 x 7 area
	public static void alertNearbyTanks(int position){
		
		
		for(int i = 0; i < 49; i++){
			if(obstacleMap2[indexes3[i]+position] != null)
				obstacleMap2[indexes3[i]+position].damage(0);
		}
	}
	
}


