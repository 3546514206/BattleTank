package core;
//this class generate railgun  trails
public class railgunTail {

	
	public railgunTail(vector startPoint, int angle, boolean isHostile){
		vector direction = new vector(0,0,0.1);
		direction.rotate_XZ(angle);
		for(int i = 0; i < 21; i++){
			helix temp = new helix(startPoint.myClone(), angle);
			int position = (int)(temp.boundary2D.xPos*4) + (129-(int)(temp.boundary2D.yPos*4))*80;
			projectiles.register(temp);
			if(obstacleMap.slugCollideObstacle2(temp, position, isHostile)){
				break;
			}
			
			startPoint.add(direction);
			
		}
		
		
			
	}
}
