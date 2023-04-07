package core;
//enemy: the annihilator 

public class annihilator extends solidObject {
	//polygons for tank body
	private polygon3D[] body;
	
	//total angle that the body has rotated from the initial position. (in the x-z plane)
	private int bodyAngle;
	
	//the centre of the body in camera coordinate
	private vector bodyCenter;
	
	//polygons for tank turret
	private polygon3D[] turret;
	
	//the shadow of tank body
	private polygon3D shadowBody;
	
	//the shadow of tank turret
	private polygon3D shadowTurret;
	
	//the centre of the turret (pivot point for rotation)
	private vector turretCenter;
	
	//total angle that the turret has rotated from the initial position. (in the x-z plane)
	private int turretAngle;
	
	//movement flag
	private boolean forward, aimRight, aimLeft, firingShell, firingRocket;
	
	//time needed before a weapon can be fired again
	private int coolDownShell = 33;
	private int coolDownRocket = 33;
	
	//change in tank's position of each frame
	private vector displacement = new vector(0,0,0);
	
	//degrees the tank body has rotated in a frame
	private int bodyAngleDelta;
	
	//degrees the tank turreet has rotated in a frame
	private int turretAngleDelta;
	
	//The position index of the tank in the grid map
	private int position, desiredPosition;
	
	//whether the tank is visible in the previous frame
	private boolean isVisiblePreviousFrame;
	
	//a smoke tail will be visible if the tank's health is dropped to half
	private smoke Smoke;
	
	//distance from player tank
	private double distance;
	
	//angle between player tank and turret centre
	private int targetAngle;
	
	//angle between a target location and body centre
	private int targetAngleBody;
	
	//targetAngleBody of the previous frame
	private int previousTargetAngleBody;

	//temporary vectors which will be used for vector arithmetic
	private vector tempVector1 = new vector(0,0,0);
	private vector tempVector2 = new vector(0,0,0);
	
	// a flag which indicate whether the take will interact with player at all. (i.e some enemy only get activtied at a certain stage of the game)
	public boolean active = true;
	
	//an AI flag  indicates whether it has engaged with player tank
	private boolean engaged;
	
	//an AI flag indicates whether there is a type 2 obstacle between medium tank and player tank
	private boolean clearToShoot;
	
	//a count down for death after hp = 0
	private int countDownToDeath;
	
	//represent the time that medium tank has been in stuck status
	private int stuckCount;
	
	//random numbers 
	private int randomNumber1, randomNumber2;
	
	public annihilator(double x, double y, double z, int angle){
		//define the center point of this model(also the centre point of tank body)
		start = new vector(x,y,z);
		iDirection = new vector(1,0,0);
		jDirection = new vector(0,1,0);
		kDirection = new vector(0,0,1);
		
		//boundary of this model has a cubic shape (ie l = w)
		modelType = 2;  
		makeBoundary(0.1, 0.25, 0.1);
		
		//create 2D boundary
		boundary2D = new rectangle2D(x - 0.115, z + 0.115, 0.23, 0.23);
		position = (int)(x*4) + (129-(int)(z*4))*80;
		desiredPosition = position;
		obstacleMap.registerObstacle2(this, position);
		
		//find centre of the model in world coordinate
		findCentre();
		
		bodyCenter = centre;
		bodyAngle = angle;
		turretAngle = angle;
		
		makeBody();
		makeTurret();
		
		randomNumber1 = gameData.getRandom();
		
		//Annihilator tank has 400 hit points
		HP = 400;
		
		lifeSpan = 1;
	}
	
	//create polygons for the tank body
	private void makeBody(){
		vector[] v;
		start = bodyCenter.myClone();
		
		iDirection = new vector(0.95,0,0);
		jDirection = new vector(0,1,0);
		kDirection = new vector(0,0,1);
		
		iDirection.rotate_XZ(bodyAngle);
		kDirection.rotate_XZ(bodyAngle);
		
		body = new polygon3D[19];
		
		v = new vector[]{put(0.1, 0, 0.15), put(0.06, 0, 0.15), put(0.06, -0.04, 0.14), put(0.1, -0.04, 0.14)};
		body[0] = new polygon3D(v,v[0], v[1],  v[3], main.textures[12], 1,0.5,6);
		
		v = new vector[]{put(-0.1, -0.04, 0.14), put(-0.06, -0.04, 0.14), put(-0.06, 0, 0.15), put(-0.1, 0, 0.15)};
		body[1] = new polygon3D(v,v[0], v[1],  v[3], main.textures[12], 1,0.5,6);
		
		v = new vector[]{put(0.06, 0, -0.14), put(0.1, 0, -0.14), put(0.1, -0.04, -0.12), put(0.06, -0.04, -0.12)};
		body[2] = new polygon3D(v,v[0], v[1],  v[3], main.textures[12], 1,0.5,6);
		
		
		
		v = new vector[]{ put(-0.06, -0.04, -0.12), put(-0.1, -0.04, -0.12), put(-0.1, 0, -0.14),put(-0.06, 0, -0.14)};
		body[3] = new polygon3D(v,v[0], v[1],  v[3], main.textures[12], 1,0.5,6);
		
		int i = 4;
		
		v = new vector[]{put(0.06, 0.06, 0.13), put(0.06, 0.06, 0.08), put(0.06, -0.01, 0.08), put(0.06, -0.01, 0.15)};
		body[0+i] = new polygon3D(v, v[0], v[1], v[3], main.textures[58], 0.8,1.1,6);
		
		v = new vector[]{put(-0.06, -0.01, 0.15), put(-0.06, -0.01, 0.08), put(-0.06, 0.06, 0.08), put(-0.06, 0.06, 0.13)};
		body[1+i] = new polygon3D(v, v[0], v[1], v[3], main.textures[58], 0.8,1.1,6);
		
		v = new vector[]{put(-0.06, 0.06, 0.09), put(0.06, 0.06, 0.09), put(0.06, 0.06, -0.13), put(-0.06, 0.06, -0.13)};
		body[2+i] = new polygon3D(v, v[0], v[1], v[3], main.textures[58], 0.8,1.1,6);
		
		v = new vector[]{put(0.06, 0.06, 0.09), put(-0.06, 0.06, 0.09), put(-0.06, 0, 0.15), put(0.06, 0, 0.15)};
		body[3+i] = new polygon3D(v, v[0], v[1], v[3], main.textures[58], 0.8,0.4,6);
		
		v = new vector[]{put(-0.1, 0.06, -0.13), put(0.1, 0.06, -0.13), put(0.1, 0, -0.14),  put(-0.1, 0, -0.14)};
		body[4+i] = new polygon3D(v, v[0], v[1], v[3], main.textures[58], 0.8,0.3,6);
		
		v = new vector[]{put(0.06, 0.06, 0.13), put(0.1, 0.06, 0.13), put(0.1, 0.06, -0.13), put(0.06, 0.06, -0.13)};
		body[5+i] = new polygon3D(v, v[0], v[1], v[3], main.textures[58], 0.3,0.8,6);
		
		v = new vector[]{put(-0.06, 0.06, -0.13), put(-0.1, 0.06, -0.13), put(-0.1, 0.06, 0.13), put(-0.06, 0.06, 0.13)};
		body[6+i] = new polygon3D(v, v[0], v[1], v[3], main.textures[58], 0.3,0.8,6);
		
		v = new vector[]{put(0.1, 0.06, 0.13), put(0.06, 0.06, 0.13), put(0.06, 0., 0.15), put(0.1, 0., 0.15)};
		body[7+i] = new polygon3D(v, v[0], v[1], v[3], main.textures[58], 0.8,1.1,6);
		
		v = new vector[]{put(-0.1, 0., 0.15), put(-0.06, 0., 0.15), put(-0.06, 0.06, 0.13),put(-0.1, 0.06, 0.13)};
		body[8+i] = new polygon3D(v, v[0], v[1], v[3], main.textures[58], 0.8,1.1,6);
		
		v = new vector[]{put(0.1, 0.06, -0.13), put(0.1, 0.06, 0.13), put(0.1, 0, 0.15), put(0.1, 0, -0.14)};
		body[9+i] = new polygon3D(v, v[0], v[1], v[3], main.textures[58], 0.8,0.2,6);
		
		v = new vector[]{put(-0.1, 0, -0.14), put(-0.1, 0, 0.15), put(-0.1, 0.06, 0.13), put(-0.1, 0.06, -0.13)};
		body[10+i] = new polygon3D(v, v[0], v[1], v[3], main.textures[58], 0.8,0.2,6);
		
		v = new vector[]{put(0.1, 0, 0.01), put(0.1, 0, 0.15), put(0.1, -0.04, 0.14), put(0.1, -0.04, 0.03)};
		body[11+i] = new polygon3D(v, put(0.1, 0.1, 0.03), put(0.1, 0.1, 0.13),  put(0.1, -0.04, 0.03), main.textures[12], 1,0.5,6);
		
		v = new vector[]{put(0.1, 0, -0.14), put(0.1, 0, -0.01), put(0.1, -0.04, -0.03), put(0.1, -0.04, -0.12)};
		body[12+i] = new polygon3D(v, put(0.1, 0.1, -0.15), put(0.1, 0.1, -0.01),  put(0.1, -0.04, -0.15), main.textures[12], 1,0.5,6);
		
		v = new vector[]{put(-0.1, -0.04, 0.03), put(-0.1, -0.04, 0.14), put(-0.1, 0, 0.15), put(-0.1, 0, 0.01)};
		body[13+i] = new polygon3D(v, put(-0.1, 0.1, 0.03), put(-0.1, 0.1, 0.13),  put(-0.1, -0.04, 0.03), main.textures[12], 1,0.5,6);
		
		v = new vector[]{put(-0.1, -0.04, -0.12), put(-0.1, -0.04, -0.03), put(-0.1, 0, -0.01), put(-0.1, 0, -0.14)};
		body[14+i] = new polygon3D(v, put(-0.1, 0.1, -0.15), put(-0.1, 0.1, -0.01),  put(-0.1, -0.04, -0.15), main.textures[12], 1,0.5,6);
		
		turretCenter = put(0, 0.07, -0);
		
		//create shadow for tank body
		start.add(-0.015, 0, -0.015);
		start.y = -1;
		v = new vector[]{put(-0.3, 0, 0.3), put(0.3, 0, 0.3), put(0.3, 0, -0.3), put(-0.3, 0, -0.3)};
		shadowBody = new polygon3D(v, v[0], v[1], v[3], main.textures[14], 1, 1, 2);
		
		
	}
	
	//create polygons for the tank turret
	private void makeTurret(){
		start = turretCenter.myClone();
		vector[] v;
		
		iDirection = new vector(1.6,0,0);
		jDirection = new vector(0,1.4,0);
		kDirection = new vector(0,0,1.4);
		
		//adjust orientation of the turret
		iDirection.rotate_XZ(turretAngle);
		kDirection.rotate_XZ(turretAngle);
		
		turret = new polygon3D[23];
		
		v = new vector[]{put(0.04, 0.035, 0.06), put(-0.04, 0.035, 0.06), put(-0.04, 0, 0.06), put(0.04, 0, 0.06)};
		turret[0] = new polygon3D(v, v[0], v[1], v [3], main.textures[59], 0.6,0.3,6);
		
		
		
		v = new vector[]{put(0.02, 0.025, 0.18), put(0.026, 0.015, 0.18), put(0.028, 0.015, 0.06), put(0.02, 0.025, 0.06)};
		turret[1] = new polygon3D(v, v[0], v[1], v [3], main.textures[60], 0.1,1,6);
		
		v = new vector[]{ put(0.02, 0.025, 0.06), put(-0.008 + 0.02, 0.015, 0.06), put(-0.006 + 0.02, 0.015, 0.18),put(0.02, 0.025, 0.18)};
		turret[2] = new polygon3D(v, v[0], v[1], v [3], main.textures[60], 0.1,1,6);
		
		v = new vector[]{put(-0.02, 0.025, 0.18), put(0.006 - 0.02, 0.015, 0.18), put(0.008-0.02, 0.015, 0.06), put(-0.02, 0.025, 0.06)};
		turret[3] = new polygon3D(v, v[0], v[1], v [3], main.textures[60], 0.1,1,6);
		
		v = new vector[]{ put(-0.02, 0.025, 0.06), put(-0.028, 0.015, 0.06), put(-0.026, 0.015, 0.18),put(-0.02, 0.025, 0.18)};
		turret[4] = new polygon3D(v, v[0], v[1], v [3], main.textures[60], 0.1,1,6);
		
		v = new vector[]{put(-0.04, 0.035, 0.06), put(0.04, 0.035, 0.06), put(0.05, 0.035, 0.04), put(0.05, 0.035, -0.03), put(0.03, 0.035, -0.07),  put(-0.03, 0.035, -0.07),put(-0.05, 0.035, -0.03), put(-0.05, 0.035, 0.04)};
		turret[5] = new polygon3D(v, put(-0.04, 0.035, 0.19), put(0.04, 0.035, 0.19), put(-0.04, 0.035, 0.09), main.textures[59], 0.6,0.6,6);
		
		v = new vector[]{put(0.03, 0, -0.07), put(-0.03, 0, -0.07),  put(-0.03, 0.035, -0.07),   put(0.03, 0.035, -0.07)};
		turret[6] = new polygon3D(v, v[0], v[1], v [3], main.textures[59], 0.4,0.2,6);
		
		v = new vector[]{put(0.03, 0.035, -0.07), put(0.05, 0.035, -0.03), put(0.05, 0, -0.03), put(0.03, 0, -0.07)};
		turret[7] = new polygon3D(v, v[0], v[1], v [3], main.textures[59], 0.4,0.2,6);
		
		v = new vector[]{put(-0.03, 0, -0.07), put(-0.05, 0, -0.03), put(-0.05, 0.035, -0.03), put(-0.03, 0.035, -0.07)};
		turret[8] = new polygon3D(v, v[0], v[1], v [3], main.textures[59], 0.4,0.2,6);
		
		v = new vector[]{put(0.05, 0.035, -0.03), put(0.05, 0.035, 0.04), put(0.05, 0, 0.04), put(0.05, 0, -0.03)};
		turret[9] = new polygon3D(v, v[0], v[1], v [3], main.textures[59], 0.5,0.3,6);
		
		v = new vector[]{put(-0.05, 0, -0.03), put(-0.05, 0, 0.04), put(-0.05, 0.035, 0.04), put(-0.05, 0.035, -0.03)};
		turret[10] = new polygon3D(v, v[0], v[1], v [3], main.textures[59], 0.5,0.3,6);
		
		v = new vector[]{put(0.05, 0.035, 0.04), put(0.04, 0.035, 0.06), put(0.04, 0, 0.06), put(0.05, 0, 0.04)};
		turret[11] = new polygon3D(v, v[0], v[1], v [3], main.textures[59], 0.3,0.3,6);
		
		v = new vector[]{put(-0.05, 0, 0.04), put(-0.04, 0, 0.06), put(-0.04, 0.035, 0.06), put(-0.05, 0.035, 0.04)};
		turret[12] = new polygon3D(v, v[0], v[1], v [3], main.textures[59], 0.3,0.3,6);
		
		
		v = new vector[]{put(-0.075, 0.05, 0.02), put(-0.05, 0.05, 0.02), put(-0.05, 0.05, -0.04), put(-0.075, 0.05, -0.04)};
		turret[13] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.5,0.5,6);
		
		v = new vector[]{put(-0.075, 0.05, 0.02), put(-0.075, 0.05, -0.04), put(-0.075, 0.02, -0.04), put(-0.075, 0.02, 0.02)};
		turret[14] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.5,0.5,6);
		
		v = new vector[]{put(-0.075, 0.05, -0.04), put(-0.05, 0.05, -0.04), put(-0.05, 0.02, -0.04), put(-0.075, 0.02, -0.04)};
		turret[15] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.5,0.5,6);
		
		v = new vector[]{put(-0.05, 0.05, -0.04), put(-0.05, 0.05, 0.02), put(-0.05, 0.035, 0.02),put(-0.05, 0.035, -0.04)};
		turret[16] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.5,0.5,6);
		
		int r = 150/8;
		int g = 150/8;
		int b = 150/8;
		short color = (short)((int)r <<10 | (int)g << 5 | (int)b);
		
		v = new vector[]{put(-0.075, 0.02, 0.02), put(-0.05, 0.02, 0.02), put(-0.05, 0.05, 0.02), put(-0.075, 0.05, 0.02)};
		turret[17] = new polygon3D(v, v[0], v[1], v [3],null, 0.5,0.5,7);
		turret[17].color = color;
		
		v = new vector[]{put(0.075, 0.05, -0.04), put(0.05, 0.05, -0.04), put(0.05, 0.05, 0.02), put(0.075, 0.05, 0.02)};
		turret[18] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.5,0.5,6);
		
		v = new vector[]{put(0.075, 0.02, 0.02), put(0.075, 0.02, -0.04), put(0.075, 0.05, -0.04), put(0.075, 0.05, 0.02)};
		turret[19] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.5,0.5,6);
		
		v = new vector[]{put(0.075, 0.02, -0.04), put(0.05, 0.02, -0.04), put(0.05, 0.05, -0.04), put(0.075, 0.05, -0.04)};
		turret[20] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.5,0.5,6);
		
		v = new vector[]{put(0.05, 0.035, -0.04), put(0.05, 0.035, 0.02), put(0.05, 0.05, 0.02),put(0.05, 0.05, -0.04)};
		turret[21] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.5,0.5,6);
			
		v = new vector[]{put(0.075, 0.05, 0.02), put(0.05, 0.05, 0.02), put(0.05, 0.02, 0.02), put(0.075, 0.02, 0.02)};
		turret[22] = new polygon3D(v, v[0], v[1], v [3],null, 0.5,0.5,7);
		turret[22].color = color;
		
		//create shadow for tank turret
		start.add(-0.03, 0, -0.04);
		start.y = -1;
		v = new vector[]{put(-0.18, 0, 0.18), put(0.18, 0, 0.18), put(0.18, 0, -0.18), put(-0.18, 0, -0.18)};
		shadowTurret = new polygon3D(v, v[0], v[1], v[3], main.textures[61], 1, 1, 2);
				
	}
	
	
	public void update(){
		//Retrieve a random number every 333 game frame
		if((main.timer+randomNumber1*3)%1000 == 0){
			if(randomNumber2 > 50)
				randomNumber2 = 50;
			else
				randomNumber2 = 51;
		}
		
		//process AI
		if(countDownToDeath <= 0 && active && !main.gamePaused)
			processAI();
		
		//perform actions
		if(aimLeft){
			if(Math.abs(turretAngle - targetAngle) <=3){
				turretAngleDelta = targetAngle - turretAngle;
				turretAngle+=turretAngleDelta;
				if(turretAngleDelta < 0)
					turretAngleDelta+=360;
			}else{
				turretAngleDelta=3;
				turretAngle+=3;
			}
			if(turretAngle >= 360)
				turretAngle-=360;
		}else if(aimRight){
			if(Math.abs(turretAngle - targetAngle) <=3){
				turretAngleDelta = targetAngle - turretAngle;
				turretAngle+=turretAngleDelta;
				if(turretAngleDelta < 0)
					turretAngleDelta+=360;
			}else{
				turretAngleDelta=357;
				turretAngle-=3;
			}
			if(turretAngle < 0)
				turretAngle+=360;
		}
		
		if(forward){
			//move forward
			int delta = targetAngleBody - bodyAngle;
			if(Math.abs(delta) < 5 || Math.abs(delta) > 355){
				bodyAngle = targetAngleBody;
				bodyAngleDelta = (delta+720)%360;
				displacement.set(0,0,0.01);
				displacement.rotate_XZ(bodyAngle);
			}else{
				displacement.set(0,0,0);
				if(delta > 0){
					if(delta < 180)
						bodyAngleDelta = 5;
					else
						bodyAngleDelta = 355;
				}	
				if(delta < 0){
					if(delta > -180)
						bodyAngleDelta = 355;
					else
						bodyAngleDelta = 5;
				}
				
				
				
				bodyAngle = (bodyAngle+bodyAngleDelta)%360;
			}
		}
		
		
		//update centre
		centre.add(displacement);
		
		//update bundary2D
		boundary2D.update(displacement);
		
		//update location in the 2d tile map
		//validating movement is already done in  process AI part
		int newPosition = (int)(boundary2D.xPos*4) + (129-(int)(boundary2D.yPos*4))*80;
		if(!obstacleMap.isOccupied(newPosition)){
			obstacleMap.removeObstacle2(position);
			obstacleMap.registerObstacle2(this, newPosition);
			position = newPosition;
			desiredPosition = newPosition;
		}else if(!obstacleMap.isOccupied(desiredPosition)){
			obstacleMap.removeObstacle2(position);
			obstacleMap.registerObstacle2(this, desiredPosition);
			position = desiredPosition;
		}else{
			desiredPosition = newPosition;
		}
		
		//find centre in camera coordinate
		tempCentre.set(centre);
		tempCentre.y = -1;
		tempCentre.subtract(camera.position);
		tempCentre.rotate_XZ(camera.XZ_angle);
		tempCentre.rotate_YZ(camera.YZ_angle);
		tempCentre.updateLocation();
		
		//test whether the model is visible by comparing the 2D position of its centre point with the screen
		visible = true;
		
		if(tempCentre.z < 0.9 || tempCentre.screenY < -10 || tempCentre.screenX < -400 || tempCentre.screenX >800){
			visible = false;
			isVisiblePreviousFrame = false;
		}
		
		//if tank is not visible in the previous frame, its need to be reconstructed
		if(visible){
			if(isVisiblePreviousFrame == false){
				//recreate body and turret polygons
				makeBody();
				makeTurret();
				isVisiblePreviousFrame = true;
			}
		}
		
		//if visible then update the geometry to camera coordinate
		if(visible){
			modelDrawList.register(this);
			
			if(countDownToDeath <3){
			
				//update body polygons
				for(int i = 0; i < body.length; i++){
					//perform vertex updates in world coordinate
					body[i].origin.add(displacement);
					body[i].origin.subtract(centre);
					body[i].origin.rotate_XZ(bodyAngleDelta);
					body[i].origin.add(centre);
					
					body[i].bottomEnd.add(displacement);
					body[i].bottomEnd.subtract(centre);
					body[i].bottomEnd.rotate_XZ(bodyAngleDelta);
					body[i].bottomEnd.add(centre);
					
					body[i].rightEnd.add(displacement);
					body[i].rightEnd.subtract(centre);
					body[i].rightEnd.rotate_XZ(bodyAngleDelta);
					body[i].rightEnd.add(centre);
					
					for(int j = 0; j < body[i].vertex3D.length; j++){
						body[i].vertex3D[j].add(displacement);
						body[i].vertex3D[j].subtract(centre);
						body[i].vertex3D[j].rotate_XZ(bodyAngleDelta);
						body[i].vertex3D[j].add(centre);
					}
					
					body[i].findRealNormal();
					body[i].findDiffuse();
					
					//transform the polygon into camera coordinate
					body[i].update();
				}
				
				//update shadow for tank body
				tempVector1.set(centre);
				tempVector1.add(-0.03, 0, -0.04);
				shadowBody.origin.add(displacement);
				shadowBody.origin.subtract(tempVector1);
				shadowBody.origin.rotate_XZ(bodyAngleDelta);
				shadowBody.origin.add(tempVector1);
				
				shadowBody.bottomEnd.add(displacement);
				shadowBody.bottomEnd.subtract(tempVector1);
				shadowBody.bottomEnd.rotate_XZ(bodyAngleDelta);
				shadowBody.bottomEnd.add(tempVector1);
				
				shadowBody.rightEnd.add(displacement);
				shadowBody.rightEnd.subtract(tempVector1);
				shadowBody.rightEnd.rotate_XZ(bodyAngleDelta);
				shadowBody.rightEnd.add(tempVector1);
				
				for(int j = 0; j < shadowBody.vertex3D.length; j++){
					shadowBody.vertex3D[j].add(displacement);
					shadowBody.vertex3D[j].subtract(tempVector1);
					shadowBody.vertex3D[j].rotate_XZ(bodyAngleDelta);
					shadowBody.vertex3D[j].add(tempVector1);
				}
				
				shadowBody.update();
				rasterizer.rasterize(shadowBody);
				
			
			
				//update turret center
				turretCenter.add(displacement);
				
				//update turret polygons
				for(int i = 0; i < turret.length; i++){
					//perform vertex updates in world coordinate
					turret[i].origin.add(displacement);
					//turret[i].origin.add(tempVector2);
					turret[i].origin.subtract(turretCenter);
					turret[i].origin.rotate_XZ(turretAngleDelta);
					turret[i].origin.add(turretCenter);
					
					turret[i].bottomEnd.add(displacement);
					//turret[i].bottomEnd.add(tempVector2);
					turret[i].bottomEnd.subtract(turretCenter);
					turret[i].bottomEnd.rotate_XZ(turretAngleDelta);
					turret[i].bottomEnd.add(turretCenter);
					
					turret[i].rightEnd.add(displacement);
					//turret[i].rightEnd.add(tempVector2);
					turret[i].rightEnd.subtract(turretCenter);
					turret[i].rightEnd.rotate_XZ(turretAngleDelta);
					turret[i].rightEnd.add(turretCenter);
					
					for(int j = 0; j < turret[i].vertex3D.length; j++){
						turret[i].vertex3D[j].add(displacement);
						//turret[i].vertex3D[j].add(tempVector2);
						turret[i].vertex3D[j].subtract(turretCenter);
						turret[i].vertex3D[j].rotate_XZ(turretAngleDelta);
						turret[i].vertex3D[j].add(turretCenter);
					}
					
					turret[i].findRealNormal();
					turret[i].findDiffuse();
					
					//transform the polygon into camera coordinate
					turret[i].update();
				}
				
				//update shadow for tank turret
				tempVector1.set(turretCenter);
				tempVector1.add(-0.03, 0, -0.04);
				
				shadowTurret.origin.add(displacement);
				//shadowTurret.origin.add(tempVector2);
				shadowTurret.origin.subtract(tempVector1);
				shadowTurret.origin.rotate_XZ(turretAngleDelta);
				shadowTurret.origin.add(tempVector1);
				
				shadowTurret.bottomEnd.add(displacement);
				//shadowTurret.bottomEnd.add(tempVector2);
				shadowTurret.bottomEnd.subtract(tempVector1);
				shadowTurret.bottomEnd.rotate_XZ(turretAngleDelta);
				shadowTurret.bottomEnd.add(tempVector1);
				
				shadowTurret.rightEnd.add(displacement);
				//shadowTurret.rightEnd.add(tempVector2);
				shadowTurret.rightEnd.subtract(tempVector1);
				shadowTurret.rightEnd.rotate_XZ(turretAngleDelta);
				shadowTurret.rightEnd.add(tempVector1);
				
				for(int j = 0; j < shadowTurret.vertex3D.length; j++){
					shadowTurret.vertex3D[j].add(displacement);
					//shadowTurret.vertex3D[j].add(tempVector2);
					shadowTurret.vertex3D[j].subtract(tempVector1);
					shadowTurret.vertex3D[j].rotate_XZ(turretAngleDelta);
					shadowTurret.vertex3D[j].add(tempVector1);
				}
				shadowTurret.update();
				rasterizer.rasterize(shadowTurret);
			}
		}
		
		//handle attack event
		if(coolDownShell > 0 && coolDownShell!=92 && !main.gamePaused)
			coolDownShell--;
		
		if(coolDownRocket > 0 && coolDownRocket !=90 && !main.gamePaused)
			coolDownRocket--;
		
		if(firingShell){
			if(coolDownShell == 0){
				coolDownShell = 100;
				//calculate laser direction
				vector tempVector1 = new vector(0,0,1);
				tempVector1.rotate_XZ((turretAngle+270)%360);
				tempVector1.scale(0.035);
				vector direction = new vector(0,0,1);
				direction.rotate_XZ(turretAngle);
				direction.scale(0.1);
				direction.add(turretCenter);
				direction.add(tempVector1);
				projectiles.register(new shell(direction.x, direction.y,direction.z, turretAngle, true, 1));
				
			}
			
			if(coolDownShell == 92){
				coolDownShell = 25;
				//calculate shell direction
				vector tempVector1 = new vector(0,0,1);
				tempVector1.rotate_XZ((turretAngle+270)%360);
				tempVector1.scale(-0.035);
				vector direction = new vector(0,0,1);
				direction.rotate_XZ(turretAngle);
				direction.scale(0.1);
				direction.add(turretCenter);
				direction.add(tempVector1);
				projectiles.register(new shell(direction.x, direction.y,direction.z, turretAngle, true, 1));
			}
		}
		
		if(firingRocket){
			
			if(coolDownRocket == 0){
				coolDownRocket = 100;
				//calculate laser direction
				vector tempVector1 = new vector(0,0,1);
				tempVector1.rotate_XZ((turretAngle+270)%360);
				tempVector1.scale(0.095);
				vector direction = new vector(0,0,1);
				direction.rotate_XZ(turretAngle);
				direction.scale(0.05);
				direction.add(turretCenter);
				direction.add(tempVector1);
				
				rocket r = new rocket(direction.x, direction.y,direction.z, turretAngle ,true);
				projectiles.register(r);
			}
			
			if(coolDownRocket == 90){
				coolDownRocket = 45;
				//calculate shell direction
				vector tempVector1 = new vector(0,0,1);
				tempVector1.rotate_XZ((turretAngle+270)%360);
				tempVector1.scale(-0.095);
				vector direction = new vector(0,0,1);
				direction.rotate_XZ(turretAngle);
				direction.scale(0.05);
				direction.add(turretCenter);
				direction.add(tempVector1);
				rocket r = new rocket(direction.x, direction.y,direction.z, turretAngle ,true);
				projectiles.register(r);
			}
		}
		
		if(HP <= 200){
			if(Smoke == null){
				Smoke = new smoke(this);
			}else{
				if(visible)
					Smoke.update();
			}
		}
		
		if(HP <= 0){
			countDownToDeath++;
			if(countDownToDeath >= 3){
				if(countDownToDeath == 3){
					projectiles.register(new explosion(centre.x, centre.y, centre.z, 2));
				
				}
				obstacleMap.removeObstacle2(position);
				Smoke.stopped = true;
			}
			if(countDownToDeath >=40)
				lifeSpan = 0;
		}
		
	
		//reset action flag
		forward = false;
		aimRight = false;
		aimLeft = false;
		bodyAngleDelta = 0;
		turretAngleDelta = 0;	
		displacement.reset();
		firingRocket = false;
		firingShell = false;
		if(main.timer%10 == 0)
			unstuck = false;
	
	}
	
	//process AI
	private void processAI(){
		//calculate distance from player's tank
		tempVector1.set(centre);
		tempVector1.subtract(playerTank.bodyCenter);
		distance = tempVector1.getLength();
		
		//medium tank become aware of player's tank when the distance is less than 2
		if(distance < 2)
			engaged = true;
		
		//medium tank will stop chasing the player when the distance is greater than 4
		if(distance > 6){
			engaged = false;
			//rotate the turret to the same angle as the body
			targetAngle = bodyAngle;
			int AngleDelta = turretAngle - targetAngle;
			if(AngleDelta > 0){
				if(AngleDelta < 180)
					aimRight = true;
				else
					aimLeft = true;
			}
			else if(AngleDelta < 0){
				if(AngleDelta > -180)
					aimLeft = true;
				else 
					aimRight = true;
			}
			return;
		}
		
		if(engaged){
			//if medium tank is engaged with player, it will send alert to nearby tanks
			if((main.timer)%5 == 0 )
				obstacleMap.alertNearbyTanks(position);
			
			//test whether there is a type obstacle 2 between medium tank and player tank
			//firing a vision ray from medium tank toward player tank
			tempVector1.set(bodyCenter);
			tempVector2.set(playerTank.bodyCenter);
			tempVector2.subtract(tempVector1);
			tempVector2.unit();
			tempVector2.scale(0.125);
			
			
			
			clearToShoot = true;
			int obstacleType = -1; 
			double d = 0;
			for(int i = 0; (d < distance) && (i < 30); i++, tempVector1.add(tempVector2), d+=0.125){
				model temp = obstacleMap.isOccupied2(tempVector1);
				if(temp == null)
					continue;
				obstacleType = temp.getType();
				if(obstacleType == 1){
					break;
				}else{
					clearToShoot = false;
					break;
				}
				
			}
			
			
			
			//find the angle between target and itself
			if(clearToShoot){
				targetAngle = 90 + (int)(180 * Math.atan((centre.z - playerTank.bodyCenter.z)/(centre.x - playerTank.bodyCenter.x)) / Math.PI);
				if(playerTank.bodyCenter.x > turretCenter.x  && targetAngle <= 180)
					targetAngle+=180;

			}else{
				targetAngle = bodyAngle;
				
			}
			
			//cauculate the difference between those 2 angles
			int AngleDelta = turretAngle - targetAngle;
			if(Math.abs(AngleDelta) < 3 && clearToShoot && distance < 1.7)
				firingShell = true;
			
			if(Math.abs(AngleDelta) < 3 && clearToShoot && distance < 3)
				firingRocket = true;
			
		
			
			
			//aim at a target angle
			if(AngleDelta > 0){
				if(AngleDelta < 180)
					aimRight = true;
				else
					aimLeft = true;
			}
			else if(AngleDelta < 0){
				if(AngleDelta > -180)
					aimLeft = true;
				else 
					aimRight = true;
			}
			
			//move to a  target location 
			//medium tank will move towards player tank's position until distance is less than 1.4, or it detects 
			//a type 2 obstacle between itself and the player's tank
			forward = true;
			if(clearToShoot && distance < 1.5){
				if(distance < 1.4)
					forward = false;
				if(distance >=1.4)
					if(randomNumber2 > 50)
						forward = false;
			}
			
			if(unstuck && distance > 0.8){
				forward = true;
				obstacleMap.giveWay(this, position);
				
			}
			
			if(forward){
				targetAngleBody = 90 + (int)(180 * Math.atan((centre.z - playerTank.bodyCenter.z)/(centre.x - playerTank.bodyCenter.x)) / Math.PI);
				if(playerTank.bodyCenter.x > centre.x  && targetAngleBody <= 180)
					targetAngleBody+=180;
				
				//the enemy tank will occasionly (~once every 10 secs)perfom a 90 degree change in moving angle if:
				//1. it cant see the target tank and the target is within 1.2 unit away
				//2. it got stucked and the target is within 1.2 units away
				//3. blocked by a wall and the target is within 3 units away
				
				if(!clearToShoot && (distance < 1.2 || (obstacleType == 6 && distance < 2.5)) || stuckCount ==10){
					if(stuckCount == 10){
						if(randomNumber2 > 50)
							randomNumber2 = 50;
						else
							randomNumber2 = 51;
						stuckCount=0;
					}
						
					if(randomNumber2 > 50)
						targetAngleBody += 90;
					else
						targetAngleBody -= 90;
					
					
					targetAngleBody = (targetAngleBody + 360)%360;
				}
				
				
				int newPosition = (int)(boundary2D.xPos*4) + (129-(int)(boundary2D.yPos*4))*80;
				
					
				//check whether the next move will embed into obstacles
				displacement.set(0,0,0.01);
				displacement.rotate_XZ(targetAngleBody);
				boundary2D.update(displacement);
				
				boolean canMove = true;
				//test againt type 1 & 2 obstacles
				if(obstacleMap.collideWithObstacle1(this, newPosition)){
					forward = false;
					canMove = false;
				}else if(obstacleMap.collideWithObstacle2(this, newPosition)){
					forward = false;
					canMove = false;
				}
				displacement.scale(-1);
				boundary2D.update(displacement);
				displacement.reset();
				
				
				if(!canMove){
					if(unstuck){
						obstacleMap.giveWay(this ,position);
					}
					
					
					//change direction if unable to move with current direction
					targetAngleBody = targetAngle;
					//generate 2 new directions
					int angle1 = targetAngleBody - targetAngleBody%90;
					int angle2 = angle1 + 90;
					
					
					angle2 = angle2%360;
				
					
					
					boolean canMoveAngle1 = true;
					boolean canMoveAngle2 = true;
				
				
					
					//check if tank is able to move freely at angle 1
					displacement.set(0,0,0.01);
					displacement.rotate_XZ(angle1);
					boundary2D.update(displacement);
					newPosition = (int)(boundary2D.xPos*4) + (129-(int)(boundary2D.yPos*4))*80;
					//test againt type 1 & 2 obstacles
					if(obstacleMap.collideWithObstacle1(this, newPosition)){
						canMoveAngle1 = false;
					}else if(obstacleMap.collideWithObstacle2(this, newPosition)){
						canMoveAngle1 = false;
					}
					displacement.scale(-1);
					boundary2D.update(displacement);
					displacement.reset();
					
					//check if tank is able to move freely at angle 2
					displacement.set(0,0,0.01);
					displacement.rotate_XZ(angle2);
					boundary2D.update(displacement);
					newPosition = (int)(boundary2D.xPos*4) + (129-(int)(boundary2D.yPos*4))*80;
					//test againt type 1 & 2 obstacles
					if(obstacleMap.collideWithObstacle1(this, newPosition)){
						canMoveAngle2 = false;
					}else if(obstacleMap.collideWithObstacle2(this, newPosition)){
						canMoveAngle2 = false;
					}
					displacement.scale(-1);
					boundary2D.update(displacement);
					displacement.reset();
					
				
					
					//move to a valid direction.  
					//if both directions are valid,  then move to the direction that is closer to  targetAngleBody
					//if neither direction is valid, then update the AI status to "stucked"(which does nothing at this moment)
					if(canMoveAngle1 && !canMoveAngle2){
						targetAngleBody = angle1;
						forward = true;
						
						obstacleMap.giveWay(this, position);
					}else if(!canMoveAngle1 && canMoveAngle2){
						targetAngleBody = angle2;
						forward = true;
						
						obstacleMap.giveWay(this, position);
					}else if(canMoveAngle1 && canMoveAngle2){
						if(Math.abs(angle1 - targetAngleBody) < Math.abs(angle2 - targetAngleBody)){
							targetAngleBody = angle1;
							
						}else{
							targetAngleBody = angle2;
							
						}		
						forward = true;
						
						
						
					}else{
						
						//got stucked!!
						stuckCount=10;
						
					
						
						//tell surrounding units to move away
						obstacleMap.giveWay(this, position);
						
						
						
					}
					
					if(Math.abs((previousTargetAngleBody + 180)%360  - targetAngleBody) <= 50){
						targetAngleBody = previousTargetAngleBody;
					}
					
					
				}
				//double check whether the move is valid
				displacement.set(0,0,0.01);
				displacement.rotate_XZ(targetAngleBody);
				boundary2D.update(displacement);
				newPosition = (int)(boundary2D.xPos*4) + (129-(int)(boundary2D.yPos*4))*80;
				
				//test againt type 1 & 2 obstacles
				if(obstacleMap.collideWithObstacle1(this, newPosition)){
					forward = false;
					
				}else if(obstacleMap.collideWithObstacle2(this, newPosition)){
					forward = false;
					
				}
				displacement.scale(-1);
				boundary2D.update(displacement);
				displacement.reset();
			}
		}
		previousTargetAngleBody = targetAngleBody;
	}
	
	public void draw(){
		if(countDownToDeath <3){
			//draw body
			for(int i = 0; i < body.length; i++){
				body[i].draw();
				
			}
			
			//draw turret
			for(int i = 0; i < turret.length; i++){
				turret[i].draw();
			}
		}
		
		

		//draw smoke tail
		if(Smoke != null && visible)
			Smoke.draw();
	}
	
	public void damage(int damagePoint){
		if(damagePoint == -1){
			active = true;
			engaged = true;
			return;
		}
		HP-=damagePoint;
		engaged = true;
	}
	
	//return the 2D boundary of this model
	public rectangle2D getBoundary2D(){
		return boundary2D;
	}

}
