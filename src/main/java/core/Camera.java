package core;
import java.awt.*;

public class camera {
	//position of the camera (third person view)
	public static vector position;
	
	//position of the camera (absolute)
	public static vector absolutePosition;
	
	//the displacement for creating third person effect
	public vector thirdPersonDisplacement;
	
	//direction of the view
	public static vector viewDirection;
	
	//the angle that camera has rotated from the default view direction
	//the YZ_angle is 315 degrees, and it does not change
	public static int XZ_angle, YZ_angle = 319;
	
	//a rectangle that represents the screen area
	public static final Rectangle screen = new Rectangle(0,0,640, 480);
	
	//a flag which indicates whether the camera should be positioned at initial point
	public static boolean restart;
	
	//fly through timer
	public int flyThroughTimer;
	
	public camera(){
		//init camera with default values
		XZ_angle = 0;
		position = new vector(10,0.25,1.5);
		absolutePosition = new vector(10,0.25,1.5);
		viewDirection = new vector(0,0,1); 
		thirdPersonDisplacement = new vector(0,0,0);
		thirdPersonDisplacement.set(viewDirection.x, 0, -viewDirection.z);
	}
	

	
	public void update(){
		//stop updating camera when game is finished
		if(main.gameOver){
			return;
		}
		
		if(main.gameNotStart){
			flyThroughTimer++;
		}
		
		
		
		//move the camera to the player's position
		if(!main.gameNotStart){
			//update position
			position.subtract(thirdPersonDisplacement);
			
			absolutePosition.set(position);
			
			flyThroughTimer = 0;
			
			if(!restart){
				double d_x = (playerTank.bodyCenter.x - position.x)/5;
				double d_z = (playerTank.bodyCenter.z - position.z)/5;
				position.x+=d_x;
				position.z+=d_z;
			}else{
				double d_x = (playerTank.bodyCenter.x - position.x);
				double d_z = (playerTank.bodyCenter.z - position.z);
				position.x+=d_x;
				position.z+=d_z;
				
				restart = false;
			}
		
			//update view direction
			viewDirection.set(0,0,1);
			viewDirection.rotate_YZ(YZ_angle);
			viewDirection.rotate_XZ(XZ_angle);
			viewDirection.unit();
			
			//move the camera back a little bit, so the view becomes more like third person rather than first person
			thirdPersonDisplacement.set(viewDirection.x*0.9, 0, -viewDirection.z*0.9);
			position.add(thirdPersonDisplacement); 
		
		}else{
			//fly through this entire level  when the game isn't started
			if(flyThroughTimer > 0 && flyThroughTimer <= 60){
				position.add(0,0,0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 60 && flyThroughTimer <= 130){
				
				
				XZ_angle-=1;
				XZ_angle = (XZ_angle + 360) % 360;
				
				viewDirection.set(0,0,1);
				viewDirection.rotate_YZ(YZ_angle);
				viewDirection.rotate_XZ(XZ_angle);
				viewDirection.unit();
				position.add(0,0,0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 130 && flyThroughTimer <=430){
				position.add(-0.01,0,0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 430 && flyThroughTimer <=480){
				XZ_angle+=1;
				XZ_angle = (XZ_angle + 360) % 360;
				
				viewDirection.set(0,0,1);
				viewDirection.rotate_YZ(YZ_angle);
				viewDirection.rotate_XZ(XZ_angle);
				viewDirection.unit();
				position.add(0,0,0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 480 && flyThroughTimer <= 800){
				position.add(-0.005,0,0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 800 && flyThroughTimer <=825){
				XZ_angle+=1;
				XZ_angle = (XZ_angle + 360) % 360;
				
				viewDirection.set(0,0,1);
				viewDirection.rotate_YZ(YZ_angle);
				viewDirection.rotate_XZ(XZ_angle);
				viewDirection.unit();
				position.add(-0.005,0,0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 825 && flyThroughTimer <= 1100){
				position.add(0,0,0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 1100 && flyThroughTimer <=1130){
				XZ_angle+=1;
				XZ_angle = (XZ_angle + 360) % 360;
				
				viewDirection.set(0,0,1);
				viewDirection.rotate_YZ(YZ_angle);
				viewDirection.rotate_XZ(XZ_angle);
				viewDirection.unit();
				position.add(0,0,0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 1130 && flyThroughTimer <= 1250){
				position.add(0.005,0,0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 1250 && flyThroughTimer <=1290){
				XZ_angle+=1;
				XZ_angle = (XZ_angle + 360) % 360;
				
				viewDirection.set(0,0,1);
				viewDirection.rotate_YZ(YZ_angle);
				viewDirection.rotate_XZ(XZ_angle);
				viewDirection.unit();
				position.add(0,0,0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 1290 && flyThroughTimer <= 1550){
				position.add(0.01,0,0.005);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 1550 && flyThroughTimer <=1567){
				XZ_angle+=1;
				XZ_angle = (XZ_angle + 360) % 360;
				
				viewDirection.set(0,0,1);
				viewDirection.rotate_YZ(YZ_angle);
				viewDirection.rotate_XZ(XZ_angle);
				viewDirection.unit();
				position.add(0.01,0,0.005);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 1567 && flyThroughTimer <= 1867){
				position.add(0.012,0,0);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 1867 && flyThroughTimer <=1900){
				XZ_angle+=1;
				XZ_angle = (XZ_angle + 360) % 360;
				
				viewDirection.set(0,0,1);
				viewDirection.rotate_YZ(YZ_angle);
				viewDirection.rotate_XZ(XZ_angle);
				viewDirection.unit();
				position.add(0.01,0,0.005);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 1900 && flyThroughTimer <= 2100){
				position.add(0.007,0,-0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 2100 && flyThroughTimer <=2130){
				XZ_angle+=1;
				XZ_angle = (XZ_angle + 360) % 360;
				
				viewDirection.set(0,0,1);
				viewDirection.rotate_YZ(YZ_angle);
				viewDirection.rotate_XZ(XZ_angle);
				viewDirection.unit();
				position.add(0.007,0,-0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 2130 && flyThroughTimer <= 2330){
				position.add(0.003,0,-0.01);
				absolutePosition.set(position);
			}
			
			
			if(flyThroughTimer > 2330 && flyThroughTimer <=2360){
				XZ_angle+=1;
				XZ_angle = (XZ_angle + 360) % 360;
				
				viewDirection.set(0,0,1);
				viewDirection.rotate_YZ(YZ_angle);
				viewDirection.rotate_XZ(XZ_angle);
				viewDirection.unit();
				position.add(0.003,0,-0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 2360 && flyThroughTimer <= 2560){
				position.add(0,0,-0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 2560 && flyThroughTimer <=2590){
				XZ_angle+=1;
				XZ_angle = (XZ_angle + 360) % 360;
				
				viewDirection.set(0,0,1);
				viewDirection.rotate_YZ(YZ_angle);
				viewDirection.rotate_XZ(XZ_angle);
				viewDirection.unit();
				position.add(0,0,-0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 2590 && flyThroughTimer <= 2900){
				position.add(-0.005,0,-0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 2900 && flyThroughTimer <=2920){
				XZ_angle+=1;
				XZ_angle = (XZ_angle + 360) % 360;
				
				viewDirection.set(0,0,1);
				viewDirection.rotate_YZ(YZ_angle);
				viewDirection.rotate_XZ(XZ_angle);
				viewDirection.unit();
				position.add(-0.007,0,-0.01);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer > 2920 && flyThroughTimer <= 3255){
				position.add(-0.009,0,-0.011);
				absolutePosition.set(position);
			}
			

			if(flyThroughTimer > 3255 && flyThroughTimer <=3380){
				XZ_angle+=1;
				XZ_angle = (XZ_angle + 360) % 360;
				
				viewDirection.set(0,0,1);
				viewDirection.rotate_YZ(YZ_angle);
				viewDirection.rotate_XZ(XZ_angle);
				viewDirection.unit();
				position.set(10,0.25,1.5);
				absolutePosition.set(position);
			}
			
			if(flyThroughTimer == 3380)
				flyThroughTimer = 0;
			
		}
		
	
		
		
		
		
	}
}
