package core;
//enemy: stealth tank
public class stealthTank extends solidObject{
	//polygons for tank body
	private polygon3D[] body;
	
	//polygon for tank body when cloak is active
	private polygon3D[] bodyInvisible;
	
	//total angle that the body has rotated from the initial position. (in the x-z plane)
	private int bodyAngle;
	
	//the centre of the body in camera coordinate
	private vector bodyCenter;
	
	//polygons for tank turret
	private polygon3D[] turret;
	
	//polygons for tank turret when cloak is active
	private polygon3D[] turretInvisible;
	
	//the shadow of tank body
	private polygon3D shadowBody;
	
	//the shadow of tank turret
	private polygon3D shadowTurret;
	
	//the centre of the turret (pivot point for rotation)
	private vector turretCenter;
	
	//total angle that the turret has rotated from the initial position. (in the x-z plane)
	private int turretAngle;
	
	//movement flag
	private boolean forward, aimRight, aimLeft, firing;
	
	//time needed before a weapon can be fired again
	private int coolDown = 75;
	
	//current coolDown
	private int currentCoolDown = 0;
	
	//change in tank's position of each frame
	private vector displacement = new vector(0,0,0);
	
	//degrees the tank body has rotated in a frame
	private int bodyAngleDelta;
	
	//degrees the tank turreet has rotated in a frame
	private int turretAngleDelta;
	
	//The position index of the tank in the grid map
	private int position;
	
	//whether the tank is visible in the previous frame
	private boolean isVisiblePreviousFrame;
	
//	distance from player tank
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
	
	//a flag which indicate whether the take will interact with player at all. (i.e some enemy only get activtied at a certain stage of the game)
	public boolean active = true;
	
	//an AI flag  indicates whether it has engaged with player tank
	private boolean engaged;
	
	//an AI flag indicates whether there is a type 2 obstacle between medium tank and player tank
	private boolean clearToShoot;
	
	//a count down for death after hp = 0
	private int countDownToDeath;
	
	//represent the time that medium tank has been in stuck status
	private int stuckCount;
	
	//a smoke tail
	private smoke Smoke;
	
	//random numbers 
	private int randomNumber1, randomNumber2;
	
	//visibility of the tank, bigger the number, more transparent it will looks
	private int t = 30;
	
	public stealthTank(double x, double y, double z, int angle){
		//define the center point of this model(also the centre point of tank body)
		start = new vector(x,y,z);
		iDirection = new vector(1,0,0);
		jDirection = new vector(0,1,0);
		kDirection = new vector(0,0,1);
		
		//adjust orientation of the model
		iDirection.rotate_XZ(angle);
		kDirection.rotate_XZ(angle);
		
		//boundary of this model has a cubic shape (ie l = w)
		modelType = 2;  
		makeBoundary(0.1, 0.25, 0.1);
		
		//create 2D boundary
		boundary2D = new rectangle2D(x - 0.1, z + 0.1, 0.2, 0.2);
		position = (int)(x*4) + (129-(int)(z*4))*80;
		obstacleMap.registerObstacle2(this, position);
		
		
		//find centre of the model in world coordinate
		findCentre();
		
		bodyCenter = centre;
		bodyAngle = angle;
		turretAngle = angle;
		
		randomNumber1 = gameData.getRandom();
		
		makeBody();
		makeTurret();
		
		//stealth tank has 16 hitpoints
		HP = 16;
		
		lifeSpan = 1;
	}
	
	private void makeBody(){
		start = bodyCenter.myClone();
		iDirection = new vector(1.1,0,0);
		jDirection = new vector(0,1,0);
		kDirection = new vector(0,0,1.1);
		
		iDirection.rotate_XZ(bodyAngle);
		kDirection.rotate_XZ(bodyAngle);
		
		vector[] v;
		body = new polygon3D[43];
		bodyInvisible = new polygon3D[43];
		
		v = new vector[]{put(-0.04, 0.03, 0.07), put(-0.04, 0.055, 0.04), put(-0.04, 0.055, -0.05), put(-0.04, 0.03, -0.07),  put(-0.04, 0, -0.07),  put(-0.04, 0, 0.07)};
		body[0] = new polygon3D(v, put(-0.04, 0.055, 0.07), put(-0.04, 0.055, -0.07), put(-0.04, 0.01, 0.07), main.textures[35], 1,0.2,6);
		
		v = new vector[]{put(0.04, 0, 0.07),  put(0.04, 0, -0.07), put(0.04, 0.03, -0.07), put(0.04, 0.055, -0.05), put(0.04, 0.055, 0.04),  put(0.04, 0.03, 0.07)};
		body[1] = new polygon3D(v, put(0.04, 0.055, 0.07), put(0.04, 0.055, -0.07), put(0.04, 0.01, 0.07), main.textures[35], 1,0.2,6);
		
		v = new vector[]{put(-0.04, 0.03, 0.07), put(0.04, 0.03, 0.07), put(0.04, 0.055, 0.04), put(-0.04, 0.055, 0.04)};
		body[2] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 1,0.3,6);
		
		v  = new vector[]{put(-0.04, 0.055, 0.04), put(0.04, 0.055, 0.04), put(0.04, 0.055, -0.05), put(-0.04, 0.055, -0.05)};
		body[3] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 1,1,6);
		
		v = new vector[]{put(-0.04, 0.055, -0.05), put(0.04, 0.055, -0.05), put(0.04, 0.03, -0.07), put(-0.04, 0.03, -0.07)};
		body[4] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 1,0.3,6);
		
		v = new vector[]{put(0.04, 0.03, 0.07),put(-0.04, 0.03, 0.07), put(-0.04, 0, 0.07), put(0.04, 0, 0.07)};
		body[5] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 1,0.3,6);
		
		v = new vector[]{put(-0.04, 0.03, -0.07), put(0.04, 0.03, -0.07), put(0.04, 0., -0.07),  put(-0.04, 0., -0.07)};
		body[6] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 1,0.3,6);
		
		
		
		tempVector1.set(start);
		start = put(0,0,0.01);
		
		v = new vector[]{put(-0.065, 0.03,0.1), put(-0.04, 0.03,0.1), put(-0.04, 0.03,0.03), put(-0.065, 0.03,0.03)};
		body[7] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.04, 0.03,0.1), put(-0.065, 0.03,0.1), put(-0.065, 0.01,0.11), put(-0.04, 0.01,0.11)};
		body[8] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.065, 0.03,0.03), put(-0.04, 0.03,0.03), put(-0.04, 0.01,0.029), put(-0.065, 0.01,0.029)};
		body[9] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.065, 0.03,0.1), put(-0.065, 0.03,0.03), put(-0.065, 0.01,0.029), put(-0.065, 0.01,0.11)};
		body[10] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.04, 0.01,0.11), put(-0.04, 0.01,0.029), put(-0.04, 0.03,0.03) ,put(-0.04, 0.03,0.1)};
		body[11] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.065, 0.01,0.11), put(-0.065, 0.01,0.029), put(-0.065, -0.01,0.031), put(-0.065, -0.01,0.1)};
		body[12] = new polygon3D(v, put(-0.065, 0.03,0.11), put(-0.065, 0.03,0.029), put(-0.065, -0.01,0.11), main.textures[12], 1,1,6);
		
		v = new vector[]{put(-0.04, -0.01,0.1), put(-0.04, -0.01,0.031), put(-0.04, 0.01,0.029), put(-0.04, 0.01,0.11)};
		body[13] = new polygon3D(v, put(-0.04, 0.03,0.11), put(-0.04, 0.03,0.029), put(-0.04, -0.01,0.11), main.textures[12], 1,1,6);
		
		v = new vector[]{put(-0.065, 0.01,0.11), put(-0.065, -0.01,0.1), put(-0.04, -0.01,0.1), put(-0.04, 0.01,0.11)};
		body[14] = new polygon3D(v, v[0], v[1], v [3], main.textures[12], 0.3,0.5,6);
		
		v = new vector[]{put(-0.065, -0.01,0.031), put(-0.065, 0.01,0.029), put(-0.04, 0.01,0.029), put(-0.04, -0.01,0.031)};
		body[15] = new polygon3D(v, v[0], v[1], v [3], main.textures[12], 0.3,0.5,6);
		
		start.set(tempVector1);
		start = put(0,0,-0.12);
		
		v = new vector[]{put(-0.065, 0.03,0.1), put(-0.04, 0.03,0.1), put(-0.04, 0.03,0.03), put(-0.065, 0.03,0.03)};
		body[16] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.04, 0.03,0.1), put(-0.065, 0.03,0.1), put(-0.065, 0.01,0.11), put(-0.04, 0.01,0.11)};
		body[17] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.065, 0.03,0.03), put(-0.04, 0.03,0.03), put(-0.04, 0.01,0.029), put(-0.065, 0.01,0.029)};
		body[18] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.065, 0.03,0.1), put(-0.065, 0.03,0.03), put(-0.065, 0.01,0.029), put(-0.065, 0.01,0.11)};
		body[19] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.04, 0.01,0.11), put(-0.04, 0.01,0.029), put(-0.04, 0.03,0.03) ,put(-0.04, 0.03,0.1)};
		body[20] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.065, 0.01,0.11), put(-0.065, 0.01,0.029), put(-0.065, -0.01,0.031), put(-0.065, -0.01,0.1)};
		body[21] = new polygon3D(v, put(-0.065, 0.03,0.11), put(-0.065, 0.03,0.029), put(-0.065, -0.01,0.11), main.textures[12], 1,1,6);
		
		v = new vector[]{put(-0.04, -0.01,0.1), put(-0.04, -0.01,0.031), put(-0.04, 0.01,0.029), put(-0.04, 0.01,0.11)};
		body[22] = new polygon3D(v, put(-0.04, 0.03,0.11), put(-0.04, 0.03,0.029), put(-0.04, -0.01,0.11), main.textures[12], 1,1,6);
		
		v = new vector[]{put(-0.065, 0.01,0.11), put(-0.065, -0.01,0.1), put(-0.04, -0.01,0.1), put(-0.04, 0.01,0.11)};
		body[23] = new polygon3D(v, v[0], v[1], v [3], main.textures[12], 0.3,0.5,6);
		
		v = new vector[]{put(-0.065, -0.01,0.031), put(-0.065, 0.01,0.029), put(-0.04, 0.01,0.029), put(-0.04, -0.01,0.031)};
		body[24] = new polygon3D(v, v[0], v[1], v [3], main.textures[12], 0.3,0.5,6);
		
		start.set(tempVector1);
		start = put(0.105,0,-0.12);
		
		v = new vector[]{put(-0.065, 0.03,0.1), put(-0.04, 0.03,0.1), put(-0.04, 0.03,0.03), put(-0.065, 0.03,0.03)};
		body[25] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.04, 0.03,0.1), put(-0.065, 0.03,0.1), put(-0.065, 0.01,0.11), put(-0.04, 0.01,0.11)};
		body[26] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.065, 0.03,0.03), put(-0.04, 0.03,0.03), put(-0.04, 0.01,0.029), put(-0.065, 0.01,0.029)};
		body[27] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.065, 0.03,0.1), put(-0.065, 0.03,0.03), put(-0.065, 0.01,0.029), put(-0.065, 0.01,0.11)};
		body[28] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.04, 0.01,0.11), put(-0.04, 0.01,0.029), put(-0.04, 0.03,0.03) ,put(-0.04, 0.03,0.1)};
		body[29] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.065, 0.01,0.11), put(-0.065, 0.01,0.029), put(-0.065, -0.01,0.031), put(-0.065, -0.01,0.1)};
		body[30] = new polygon3D(v, put(-0.065, 0.03,0.11), put(-0.065, 0.03,0.029), put(-0.065, -0.01,0.11), main.textures[12], 1,1,6);
		
		v = new vector[]{put(-0.04, -0.01,0.1), put(-0.04, -0.01,0.031), put(-0.04, 0.01,0.029), put(-0.04, 0.01,0.11)};
		body[31] = new polygon3D(v, put(-0.04, 0.03,0.11), put(-0.04, 0.03,0.029), put(-0.04, -0.01,0.11), main.textures[12], 1,1,6);
		
		v = new vector[]{put(-0.065, 0.01,0.11), put(-0.065, -0.01,0.1), put(-0.04, -0.01,0.1), put(-0.04, 0.01,0.11)};
		body[32] = new polygon3D(v, v[0], v[1], v [3], main.textures[12], 0.3,0.5,6);
		
		v = new vector[]{put(-0.065, -0.01,0.031), put(-0.065, 0.01,0.029), put(-0.04, 0.01,0.029), put(-0.04, -0.01,0.031)};
		body[33] = new polygon3D(v, v[0], v[1], v [3], main.textures[12], 0.3,0.5,6);
		
		start.set(tempVector1);
		start = put(0.105,0,0.01);
		
		v = new vector[]{put(-0.065, 0.03,0.1), put(-0.04, 0.03,0.1), put(-0.04, 0.03,0.03), put(-0.065, 0.03,0.03)};
		body[34] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.04, 0.03,0.1), put(-0.065, 0.03,0.1), put(-0.065, 0.01,0.11), put(-0.04, 0.01,0.11)};
		body[35] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.065, 0.03,0.03), put(-0.04, 0.03,0.03), put(-0.04, 0.01,0.029), put(-0.065, 0.01,0.029)};
		body[36] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.065, 0.03,0.1), put(-0.065, 0.03,0.03), put(-0.065, 0.01,0.029), put(-0.065, 0.01,0.11)};
		body[37] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.04, 0.01,0.11), put(-0.04, 0.01,0.029), put(-0.04, 0.03,0.03) ,put(-0.04, 0.03,0.1)};
		body[38] = new polygon3D(v, v[0], v[1], v [3], main.textures[35], 0.3,0.5,6);
		
		v = new vector[]{put(-0.065, 0.01,0.11), put(-0.065, 0.01,0.029), put(-0.065, -0.01,0.031), put(-0.065, -0.01,0.1)};
		body[39] = new polygon3D(v, put(-0.065, 0.03,0.11), put(-0.065, 0.03,0.029), put(-0.065, -0.01,0.11), main.textures[12], 1,1,6);
		
		v = new vector[]{put(-0.04, -0.01,0.1), put(-0.04, -0.01,0.031), put(-0.04, 0.01,0.029), put(-0.04, 0.01,0.11)};
		body[40] = new polygon3D(v, put(-0.04, 0.03,0.11), put(-0.04, 0.03,0.029), put(-0.04, -0.01,0.11), main.textures[12], 1,1,6);
		
		v = new vector[]{put(-0.065, 0.01,0.11), put(-0.065, -0.01,0.1), put(-0.04, -0.01,0.1), put(-0.04, 0.01,0.11)};
		body[41] = new polygon3D(v, v[0], v[1], v [3], main.textures[12], 0.3,0.5,6);
		
		v = new vector[]{put(-0.065, -0.01,0.031), put(-0.065, 0.01,0.029), put(-0.04, 0.01,0.029), put(-0.04, -0.01,0.031)};
		body[42] = new polygon3D(v, v[0], v[1], v [3], main.textures[12], 0.3,0.5,6);
		
		for(int i = 0; i < body.length; i++)
			bodyInvisible[i] = new polygon3D(body[i].vertex3D, new vector(0,0,0), new vector(0,0,0), new vector(0,0,0), null, 1 , 1, 8);
		
		start.set(tempVector1);
		start.add(-0.015, 0, -0.01);
		start.y = -1;
		v = new vector[]{put(-0.12, 0, 0.14), put(0.12, 0, 0.14), put(0.12, 0, -0.14), put(-0.12, 0, -0.14)};
		shadowBody = new polygon3D(v, v[0], v[1], v[3], main.textures[36], 1, 1, 2);
		
		start.set(tempVector1);
		turretCenter = put(0, 0.08, -0.0);
	}
	
	private void makeTurret(){
		turret = new polygon3D[66];
		turretInvisible = new polygon3D[66];
		vector[] v;
		start = turretCenter.myClone();
		
		iDirection = new vector(1,0,0);
		jDirection = new vector(0,1,0);
		kDirection = new vector(0,0,1.1);
		
		
		//adjust orientation of the turret
		iDirection.rotate_XZ(turretAngle);
		kDirection.rotate_XZ(turretAngle);
		
		double r1 = 0.028;
		double r2 = 0.017;
		double r3 = 0.022;
		double theta = Math.PI/16;
		
		int r = 56/8;
		int g = 79/8;
		int b = 167/8;
		short color = (short)((int)r <<10 | (int)g << 5 | (int)b);
		
	
		for(int i = 0; i < 32; i++){
			v = new vector[]{put(r2*Math.cos(i*theta), r2*Math.sin(i*theta), -0.07),
							 put(r2*Math.cos((i+1)*theta), r2*Math.sin((i+1)*theta), -0.07),
							 put(r1*Math.cos((i+1)*theta), r1*Math.sin((i+1)*theta), 0.03),
							 put(r1*Math.cos(i*theta), r1*Math.sin(i*theta), 0.03)
							};
			turret[i] = new polygon3D(v, v[0], v[1], v [3],  null, 0.001,0.01,7);
			turretInvisible[i] = new polygon3D(v, v[0], v[1], v [3],  null, 1,1,8);
			
			turret[i].color = color;
		}
		
		for(int i = 0; i < 32; i++){
			v = new vector[]{put(r1*Math.cos(i*theta), r1*Math.sin(i*theta), 0.03),
							 put(r1*Math.cos((i+1)*theta), r1*Math.sin((i+1)*theta), 0.03),
							 put(r3*Math.cos((i+1)*theta), r3*Math.sin((i+1)*theta), 0.07),
							 put(r3*Math.cos(i*theta), r3*Math.sin(i*theta), 0.07)
							};
			turret[i +32] = new polygon3D(v, v[0], v[1], v [3],  null, 0.001,0.01,7);
			turretInvisible[i +32] = new polygon3D(v, v[0], v[1], v [3],  null, 1,1,8);
			turret[i+32].color = color;
		}
		
		v = new vector[32];
		for(int i = 1; i < 33; i++)
			v[32 - i] = put(r2*Math.cos(i*theta), r2*Math.sin(i*theta), -0.07);
		turret[64] = new polygon3D(v, v[0], v[1], v [3],  null, 1,1,7);
		turretInvisible[64] = new polygon3D(v, v[0], v[1], v [3],  null, 1,1,8);
		turret[64].color = color;
		
		v = new vector[32];
		for(int i = 1; i < 33; i++)
			v[i-1] = put(r3*Math.cos(i*theta), r3*Math.sin(i*theta), 0.07);
		turret[65] = new polygon3D(v, v[0], v[1], v [3],  null, 1,1,7);
		turretInvisible[65] = new polygon3D(v, v[0], v[1], v [3],  null, 1,1,8);
		turret[65].color = color;
		
		//create shadow for tank turret
		start.add(-0.03, 0, -0.025);
		start.y = -1;
		v = new vector[]{put(-0.14, 0, 0.14), put(0.14, 0, 0.14), put(0.14, 0, -0.14), put(-0.14, 0, -0.14)};
		shadowTurret = new polygon3D(v, v[0], v[1], v[3], main.textures[37], 1, 1, 2);
		
	}
	
	//update model 
	public void update(){
		//retrive a random number every 1000 game frame
		if((main.timer+randomNumber1*3)%3000 == 0){  
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
			//move foward
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
		
		//update location in the 2d tile map
		//validating movement is already done in  process AI part
		int newPosition = (int)(boundary2D.xPos*4) + (129-(int)(boundary2D.yPos*4))*80;
		if(!obstacleMap.isOccupied(newPosition)){
			obstacleMap.removeObstacle2(position);
			obstacleMap.registerObstacle2(this, newPosition);
			position = newPosition;
		}
		
		//update centre
		centre.add(displacement);
		
		
		//update bundary2D
		boundary2D.update(displacement);
		
		//update 3D boundary
		//for(int i = 0; i < 5; i++){
		//	for(int j = 0; j < 4; j++)
		//		boundary[i].vertex3D[j].add(displacement);
		//	boundary[i].update();
		//}
		
		//find centre in camera coordinate
		tempCentre.set(centre);
		tempCentre.y = -1;
		tempCentre.subtract(camera.position);
		tempCentre.rotate_XZ(camera.XZ_angle);
		tempCentre.rotate_YZ(camera.YZ_angle);
		tempCentre.updateLocation();
		
		//test whether the model is visible by comparing the 2D position of its centre point with the screen area
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
					bodyInvisible[i].update();
				}
				geometry.sortPolygons(body, 2);
				
				//update shadow for tank body
				tempVector1.set(centre);
				tempVector1.add(-0.015, 0, -0.01);
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
					
					turret[i].rightEnd.add(displacement); //
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
					turretInvisible[i].update();
				}
				
				//update shadow for tank turret
				tempVector1.set(turretCenter);
				tempVector1.add(-0.03, 0, -0.025);
				
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
		if(currentCoolDown > 0 && !main.gamePaused)
			currentCoolDown--;
		
		if(firing){
			
			if(currentCoolDown == 0){
				currentCoolDown = coolDown;
				//calculate shell direction
				vector direction = new vector(0,0,1);
				direction.rotate_XZ(turretAngle);
				direction.scale(0.1);
				new railgunTail(new vector(centre.x+direction.x, centre.y + 0.08,centre.z+direction.z), turretAngle, true);
				
			}
		}
			
		
		
		
		if(t <= 30 && visible){
			int alpha = 255*t/30;
			for(int i = 0; i < body.length; i++)
				body[i].alpha = alpha;
			
			for(int i = 0; i < turret.length; i++)
				turret[i].alpha = alpha;
			shadowBody.variation = (short)(alpha/12);
			shadowTurret.variation = (short)(alpha/12);
		}
		
		if(HP < 8){
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
					projectiles.register(new explosion(centre.x, centre.y, centre.z, 1.7));
					powerUps.register(new powerUp(centre.x, -0.875, centre.z, 3));
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
		firing = false;
		if(main.timer%10 == 0)
			unstuck = false;
	}
	
	
	private void processAI(){
		//calculate distance from player's tank
		tempVector1.set(centre);
		tempVector1.subtract(playerTank.bodyCenter);
		distance = tempVector1.getLength();
		
		//stealth tank become aware of player's tank when the distance is less than 2
		if(distance < 2)
			engaged = true;
		
		//stealth tank will stop chasing the player when the distance is greater than 4.5
		if(distance > 4.5){
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
			//if stealth tank is engaged with player, it will send alert to nearby tanks
			if((main.timer)%5 == 0 )
				obstacleMap.alertNearbyTanks(position);
			
			//test whether there is a type obstacle 2 between medium tank and player tank
			//firing a vision ray from medium tank toward player tank
			tempVector1.set(centre);
			tempVector2.set(playerTank.bodyCenter);
			tempVector2.subtract(tempVector1);
			tempVector2.unit();
			tempVector2.scale(0.125);
			
			clearToShoot = true;
			double d = 0;
			int obstacleType = -1;
			for(int i = 0; i < 30 && d < distance; i++, tempVector1.add(tempVector2), d+=0.125){
				model temp = obstacleMap.isOccupied2(tempVector1);
				if(temp == null)
					continue;
				obstacleType = temp.getType();
				if(temp.getType() == 1){
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
			if(Math.abs(AngleDelta) < 3 && clearToShoot && distance < 1.7){
				firing = true;
				
			}
			
			
			if(clearToShoot && distance < 1.7){
				if(t > 0)
					t--;
			}else{
				if(t < 30)
					t++;
			}
			
			
		
			
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
			//stealth tank will move towards player tank's position until distance is less than 1.6, or it detects 
			//a type 2 obstacle between itself and the player's tank
			forward = true;
			if(clearToShoot && distance < 1.6){
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
				//1. it cant see the target tank and the target thatis within 1.2 unit away
				//2. it got stucked and the target that is within 1.2 units away
				//3. blocked by a wall
				
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
				
				//check whether the next move will embed into obstacles
				displacement.set(0,0,0.01);
				displacement.rotate_XZ(targetAngleBody);
				boundary2D.update(displacement);
				int newPosition = (int)(boundary2D.xPos*4) + (129-(int)(boundary2D.yPos*4))*80;
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
					
					//check if tank is able to move freelly at angle 2
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
					
					if((previousTargetAngleBody + 180)%360 == targetAngleBody){
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
		//draw body
		if(countDownToDeath <3){
			
			
			
			
			if(t != 30){
				for(int i = 0; i < body.length; i++){
					body[i].draw();
				}
			}
			
			//draw body
			if(t != 30){
				for(int i = 0; i < turret.length; i++){
					turret[i].draw();
				}
			}
			
			if(t != 0){
			
				int x = rasterizer.xOffset;
				int scale = rasterizer.scale;
				rasterizer.scale = 30/t;
				if(t != 30){
					
						rasterizer.xOffset = 128*t/30;
				}
					
				
				for(int i = 0; i < turret.length; i++){
					turretInvisible[i].draw();
				}
				
				for(int i = 0; i < body.length; i++){
					bodyInvisible[i].draw();
				}
				rasterizer.xOffset = x;
				rasterizer.scale = scale;
			}
			
			
		}
		
		//draw smoke tail
		if(Smoke != null && visible)
			Smoke.draw();
	}
	
	public rectangle2D getBoundary2D(){
		return boundary2D;
	}
	
	public void damage(int damagePoint){
		if(damagePoint == -1){
			active = true;
			return;
		}
		HP-=damagePoint;
		engaged = true;
	}
}
