package core;
//The tank that controlled by user
public class PlayerTank extends SolidObject {
	//polygons that made up the tank body
	private Polygon3D[] body;
	
	//total angle that the body has rotated from the initial position. (in the x-z plane)
	private int bodyAngle;
	
	//the centre of the body in camera coordinate
	public static Vector bodyCenter;
	
	//polygons that made up the tank turret
	private Polygon3D[] turret;
	
	//the shadow of tank body
	private Polygon3D shadowBody;
	
	//the shadow of tank turret
	private Polygon3D shadowTurret;
	
	//the centre of the turret (pivot point for rotation)
	public static Vector turretCenter;
	
	//total angle that the turret has rotated from the initial position. (in the x-z plane)
	public static int turretAngle;
	
	//the angle that nuke cannon has rotated along its Z axis
	private int nukeCannonAngle;
	
	//the angular frequency of the nuke cannon rotation
	private int angularSpeed;
	
	//polygons of nuke Cannon
	private Polygon3D[] nukeCannonRear, nukeCannonMiddle, nukeCannonFront;
	
	//movement flag
	public static boolean forward, backward, turnRight, turnLeft, moveRight, moveLeft, firing;
	
	//weapon flag
	//1 = cannon turret, 2 = rocket launcher turret, 3 = railgun turret 4 = nuke cannon turret
	//defult is cannon turret
	public int currentWeapon = 1; 
	
	//time needed before a weapon can be fired again
	public int[] coolDowns = new int[]{0, 14, 25, 33, 4}; 
	//plan
	// tonight make up terrains and create pototype power plant
	
	//current coolDown
	public int currentCoolDown = 0;
	
	//ammunition counts
	public static int shells;
	public static int rockets;
	public static int slugs;
	public static int plasma;
	public static int currentAmmo;
	
	//an indication of ammunition status
	public boolean outOfAmmo;
	
	//change in tank's position of each frame
	public static Vector displacement = new Vector(0,0,0);
	
	//degrees the tank body has rotated in a frame
	private int bodyAngleDelta;
	
	//the speed of tank;
	public static double speed = 0;
	
	//degrees the tank turret has rotated in a frame
	private int turretAngleDelta;
	
	//The position index of the tank in the tile map
	int position;
	
	//temporary vectors which will be used for vector arithmetic
	private Vector tempVector1 = new Vector(0,0,0);
	private Vector tempVector2 = new Vector(0,0,0);
	
	//time that the  player is underattack
	private int underAttackCount;
	
	public PlayerTank(double x, double y, double z){
		//init ammunition counts
		
		shells = 50;
		rockets = 0;
		slugs = 0;
		plasma = 0;
		
		//define the center point of this model(also the centre point of tank body)
		start = new Vector(x,y,z);
		iDirection = new Vector(1,0,0);
		jDirection = new Vector(0,0.95,0);
		kDirection = new Vector(0,0,1.05);
		
		turretAngle = 0;
		bodyAngle = 0;
		
		//boundary of this model has a cubic shape (ie l = w)
		modelType = 1;  
		makeBoundary(0.1, 0.25, 0.1);
		
		//create 2D boundary
		boundary2D = new Rectangle2D(x - 0.07, z + 0.07, 0.14, 0.14);
		position = (int)(x*4) + (129-(int)(z*4))*80;
		ObstacleMap.registerObstacle2(this, position);
		
		
		
		//find centre of the model in world coordinate
		findCentre();
		
		bodyCenter = centre;
	
		makeBody();
		makeTurretCannon();
		
		
		//player Tank has a max of 100 hit points
		HP = 150;
	}
	
	//return the 2D boundary of this model
	public Rectangle2D getBoundary2D(){
		return boundary2D;
	}
	
	//create polygons for the tank body
	public void makeBody(){
		Vector[] v;
		
		body = new Polygon3D[11];
		
		v = new Vector[]{put(-0.07, 0.025, 0.11), put(-0.07, 0.025, -0.11), put(-0.07, 0.005, -0.11), put(-0.07, -0.025, -0.08), put(-0.07, -0.025, 0.07), put(-0.07, 0.005, 0.11)};
		body[0] = new Polygon3D(v, put(-0.07, 0.027, 0.11), put(-0.07, 0.027, -0.11), put(-0.07, -0.025, 0.11), Main.textures[12], 1,1,6);
		
		v = new Vector[]{put(0.07, 0.005, 0.11), put(0.07, -0.025, 0.07), put(0.07, -0.025, -0.08), put(0.07, 0.005, -0.11), put(0.07, 0.025, -0.11), put(0.07, 0.025, 0.11)};
		body[1] = new Polygon3D(v, put(0.07, 0.027, -0.11),put(0.07, 0.027, 0.11), put(0.07, -0.025, -0.11), Main.textures[12], 1,1,6);
		
		v = new Vector[]{put(-0.06, 0.055, 0.05), put(0.06, 0.055, 0.05), put(0.06, 0.055, -0.1), put(-0.06, 0.055, -0.1)};
		body[2] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[11], 1,0.9,6);
		
		v = new Vector[]{put(-0.07, 0.04, 0.11), put(0.07, 0.04, 0.11), put(0.06, 0.055, 0.05), put(-0.06, 0.055, 0.05)};
		body[3] = new Polygon3D(v, v[2], v[3], v [1], Main.textures[11], 1,0.3,6);
	
		v = new Vector[]{put(-0.06, 0.055, 0.05),put(-0.06, 0.055, -0.1), put(-0.07, 0.04, -0.11), put(-0.07, 0.04, 0.11)};
		body[4] = new Polygon3D(v, v[2], v[3], v [1], Main.textures[11], 1,0.3,6);
	
		v = new Vector[]{put(0.07, 0.04, 0.11), put(0.07, 0.04, -0.11), put(0.06, 0.055, -0.1),put(0.06, 0.055, 0.05)};
		body[5] = new Polygon3D(v, v[2], v[3], v [1], Main.textures[11], 1,0.3,6);
		
		v = new Vector[]{put(-0.06, 0.055, -0.1), put(0.06, 0.055, -0.1), put(0.07, 0.04, -0.11), put(-0.07, 0.04, -0.11)};
		body[6] = new Polygon3D(v, v[2], v[3], v [1], Main.textures[11], 1,0.3,6);
		
		v = new Vector[]{put(0.07, 0.04, 0.11), put(-0.07, 0.04, 0.11), put(-0.07, 0.01, 0.11), put(0.07, 0.01, 0.11)};
		body[7] = new Polygon3D(v, v[2], v[3], v [1], Main.textures[11], 1,0.3,6);
	
		v = new Vector[]{put(-0.07, 0.04, 0.11), put(-0.07, 0.04, -0.11), put(-0.07, 0.015, -0.11), put(-0.07, 0.005, -0.09), put(-0.07, 0.005, 0.09),put(-0.07, 0.015, 0.11)};
		body[8] = new Polygon3D(v, put(-0.07, 0.04, 0.11), put(-0.07, 0.04, -0.11), put(-0.07, 0.025, 0.11), Main.textures[11], 1,0.3,6);
	
		v = new Vector[]{put(0.07, 0.015, 0.11), put(0.07, 0.005, 0.09), put(0.07, 0.005, -0.09), put(0.07, 0.015, -0.11), put(0.07, 0.04, -0.11),put(0.07, 0.04, 0.11)};
		body[9] = new Polygon3D(v, put(0.07, 0.04, 0.11), put(0.07, 0.04, -0.11), put(0.07, 0.025, 0.11), Main.textures[11], 1,0.3,6);
	
		v = new Vector[]{put(-0.07, 0.04, -0.11), put(0.07, 0.04, -0.11), put(0.07, 0.015, -0.11), put(-0.07, 0.015, -0.11)};
		body[10] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[11], 1,0.3,6);
		
		turretCenter = put(0, 0.055, -0.02);
		
		//create shadow for tank body
		start.add(-0.025, 0, -0.02);
		start.y = -1;
		v = new Vector[]{put(-0.2, 0, 0.2), put(0.2, 0, 0.2), put(0.2, 0, -0.2), put(-0.2, 0, -0.2)};
		shadowBody = new Polygon3D(v, v[0], v[1], v[3], Main.textures[14], 1, 1, 2);
	}
	
	//create polygons for the tank turret (cannon turret)
	public void makeTurretCannon(){
		start = turretCenter.myClone();
		Vector[] v;
		turret = new Polygon3D[10];
		
		iDirection.set(1,0,0);
		jDirection.set(0,0.95,0);
		kDirection.set(0,0,1.05);
		
		//adjust orientation of the turret
		iDirection.rotate_XZ(turretAngle);
		kDirection.rotate_XZ(turretAngle);
		
	
		v = new Vector[]{put(0, 0.025, 0.18), put(0.004, 0.022, 0.18), put(0.007, 0.022, 0.06), put(0, 0.025, 0.06)};
		turret[0] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.1,1,6);
		
		v = new Vector[]{ put(0, 0.025, 0.06), put(-0.007, 0.022, 0.06), put(-0.004, 0.022, 0.18),put(0, 0.025, 0.18)};
		turret[1] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.1,1,6);
		
		v = new Vector[]{put(-0.04, 0.035, 0.06), put(0.04, 0.035, 0.06), put(0.05, 0.035, 0.04), put(0.05, 0.035, -0.03), put(0.03, 0.035, -0.07),  put(-0.03, 0.035, -0.07),put(-0.05, 0.035, -0.03), put(-0.05, 0.035, 0.04)};
		turret[2] = new Polygon3D(v, put(-0.04, 0.035, 0.19), put(0.04, 0.035, 0.19), put(-0.04, 0.035, 0.09), Main.textures[13], 0.8,0.8,6);
		
		v = new Vector[]{put(0.03, 0, -0.07), put(-0.03, 0, -0.07),  put(-0.03, 0.035, -0.07),   put(0.03, 0.035, -0.07)};
		turret[3] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.6,0.3,6);
		
		v = new Vector[]{put(0.03, 0.035, -0.07), put(0.05, 0.035, -0.03), put(0.05, 0, -0.03), put(0.03, 0, -0.07)};
		turret[4] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.6,0.3,6);
		
		v = new Vector[]{put(-0.03, 0, -0.07), put(-0.05, 0, -0.03), put(-0.05, 0.035, -0.03), put(-0.03, 0.035, -0.07)};
		turret[5] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.6,0.3,6);
		
		v  = new Vector[]{put(-0.05, 0.035, 0.04), put(-0.05, 0.035, -0.03), put(-0.05, 0, -0.03), put(-0.05, 0, 0.04)};
		turret[6] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.5,0.2,6);
		
		v  = new Vector[]{put(0.05, 0, 0.04), put(0.05, 0, -0.03), put(0.05, 0.035, -0.03), put(0.05, 0.035, 0.04)};
		turret[7] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.5,0.2,6);
		
		v = new Vector[]{put(-0.04, 0.035, 0.06), put(-0.05, 0.035, 0.04), put(-0.05, 0, 0.04), put(-0.04, 0, 0.06)};
		turret[8] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.2,0.2,6);
		
		v = new Vector[]{put(0.04, 0, 0.06), put(0.05, 0, 0.04), put(0.05, 0.035, 0.04), put(0.04, 0.035, 0.06)};
		turret[9] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.2,0.2,6);
		
		//create shadow for tank turret (cannon)
		start.add(-0.04, 0, -0.04);
		start.y = -1;
		v = new Vector[]{put(-0.2, 0, 0.2), put(0.2, 0, 0.2), put(0.2, 0, -0.2), put(-0.2, 0, -0.2)};
		shadowTurret = new Polygon3D(v, v[0], v[1], v[3], Main.textures[15], 1, 1, 2);
	}
	
	//create polygons for the tank turret (rocket turret)
	public void makeTurretRocket(){
		start = turretCenter.myClone();
		Vector[] v;
		turret = new Polygon3D[35];
		
		iDirection.set(1,0,0);
		jDirection.set(0,0.95,0);
		kDirection.set(0,0,1.05);
		
		//adjust orientation of the turret
		iDirection.rotate_XZ(turretAngle);
		kDirection.rotate_XZ(turretAngle);
		
		
		v  = new Vector[]{put(-0.05, 0.035, 0.04), put(-0.05, 0.035, -0.03), put(-0.05, 0, -0.03), put(-0.05, 0, 0.04)};
		turret[0] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.5,0.2,6);
		
		v  = new Vector[]{put(0.05, 0, 0.04), put(0.05, 0, -0.03), put(0.05, 0.035, -0.03), put(0.05, 0.035, 0.04)};
		turret[1] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.5,0.2,6);
		
		v = new Vector[]{put(-0.04, 0.035, 0.06), put(-0.05, 0.035, 0.04), put(-0.05, 0, 0.04), put(-0.04, 0, 0.06)};
		turret[2] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.2,0.2,6);
		
		v = new Vector[]{put(0.04, 0, 0.06), put(0.05, 0, 0.04), put(0.05, 0.035, 0.04), put(0.04, 0.035, 0.06)};
		turret[3] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.2,0.2,6);
		
		
		double r = 0.015;
		double theta = Math.PI/8;
		tempVector1.set(start);
		start = put(0.068,0,0);
		
		for(int i = 0; i < 16; i++){
			v = new Vector[]{put(r*Math.cos(i*theta), r*Math.sin(i*theta), -0.03),
							 put(r*Math.cos((i+1)*theta), r*Math.sin((i+1)*theta), -0.03),
							 put(r*Math.cos((i+1)*theta), r*Math.sin((i+1)*theta), 0.03),
							 put(r*Math.cos(i*theta), r*Math.sin(i*theta), 0.03)
							};
			turret[i + 4] = new Polygon3D(v, v[0], v[1], v [3],  Main.textures[25], 0.1,0.1,6);
		}
		
		start.set(tempVector1);
		start = put(0.068,0,0.01);
		
		for(int i = 0; i < 8; i++){
			v = new Vector[]{put(r*Math.cos(i*theta), r*Math.sin(i*theta), -0.004),
							 put(r*Math.cos((i+1)*theta), r*Math.sin((i+1)*theta), -0.004),
							 put(r*Math.cos((i+1)*theta), r*Math.sin((i+1)*theta), 0.004),
							 put(r*Math.cos(i*theta), r*Math.sin(i*theta), 0.004)
							};
			turret[i + 20] = new Polygon3D(v, v[0], v[1], v [3],  Main.textures[26], 0.1,0.1,6);
		}
		
		start.set(tempVector1);
		start = put(0.068,0,0);
		
		v = new Vector[16];
		for(int i = 1; i < 17; i++)
			v[16 - i] = put(r*Math.cos(i*theta), r*Math.sin(i*theta), -0.03);
		turret[28] = new Polygon3D(v, v[0], v[1], v [3],  Main.textures[25], 1,1,6);
		
		
		
		start.set(tempVector1);
		v = new Vector[]{put(0, 0.025, 0.18), put(0.004, 0.022, 0.18), put(0.007, 0.022, 0.06), put(0, 0.025, 0.06)};
		turret[29] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.1,1,6);
		
		v = new Vector[]{ put(0, 0.025, 0.06), put(-0.007, 0.022, 0.06), put(-0.004, 0.022, 0.18),put(0, 0.025, 0.18)};
		turret[30] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.1,1,6);
		
		v = new Vector[]{put(-0.04, 0.035, 0.06), put(0.04, 0.035, 0.06), put(0.05, 0.035, 0.04), put(0.05, 0.035, -0.03), put(0.03, 0.035, -0.07),  put(-0.03, 0.035, -0.07),put(-0.05, 0.035, -0.03), put(-0.05, 0.035, 0.04)};
		turret[31] = new Polygon3D(v, put(-0.04, 0.035, 0.19), put(0.04, 0.035, 0.19), put(-0.04, 0.035, 0.09), Main.textures[13], 0.8,0.8,6);
		
		v = new Vector[]{put(0.03, 0, -0.07), put(-0.03, 0, -0.07),  put(-0.03, 0.035, -0.07),   put(0.03, 0.035, -0.07)};
		turret[32] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.6,0.3,6);
		
		v = new Vector[]{put(0.03, 0.035, -0.07), put(0.05, 0.035, -0.03), put(0.05, 0, -0.03), put(0.03, 0, -0.07)};
		turret[33] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.6,0.3,6);
		
		v = new Vector[]{put(-0.03, 0, -0.07), put(-0.05, 0, -0.03), put(-0.05, 0.035, -0.03), put(-0.03, 0.035, -0.07)};
		turret[34] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.6,0.3,6);
		
		//create shadow for tank turret (cannon)
		start.add(-0.04, 0, -0.04);
		start.y = -1;
		v = new Vector[]{put(-0.2, 0, 0.2), put(0.2, 0, 0.2), put(0.2, 0, -0.2), put(-0.2, 0, -0.2)};
		shadowTurret = new Polygon3D(v, v[0], v[1], v[3], Main.textures[15], 1, 1, 2);
	}
	
	//create polygons for the tank turret (railgun turret)
	public void makeTurretRailgun(){
		start = turretCenter.myClone();
		Vector[] v;
		turret = new Polygon3D[72];
		
		iDirection.set(1,0,0);
		jDirection.set(0,0.95,0);
		kDirection.set(0,0,1.05);
		
		//adjust orientation of the turret
		iDirection.rotate_XZ(turretAngle);
		kDirection.rotate_XZ(turretAngle);
		
		double r = 0.025;
		double theta = Math.PI/16;
		tempVector1.set(start);
		start = put(0,0,0.12);
		
		for(int i = 0; i < 32; i++){
			v = new Vector[]{put(r*Math.cos(i*theta), r*Math.sin(i*theta), -0.06),
							 put(r*Math.cos((i+1)*theta), r*Math.sin((i+1)*theta), -0.06),
							 put(r*Math.cos((i+1)*theta), r*Math.sin((i+1)*theta), 0.06),
							 put(r*Math.cos(i*theta), r*Math.sin(i*theta), 0.06)
							};
			turret[i] = new Polygon3D(v, v[0], v[1], v [3],  Main.textures[13], 0.001,0.01,6);
		}
		
		for(int i = 32; i < 64; i++){
			v = new Vector[]{put(r*Math.cos(i*theta), r*Math.sin(i*theta), 0.05),
							 put(r*Math.cos((i+1)*theta), r*Math.sin((i+1)*theta), 0.05),
							 put(r*Math.cos((i+1)*theta), r*Math.sin((i+1)*theta), 0.06),
							 put(r*Math.cos(i*theta), r*Math.sin(i*theta), 0.06)
							};
			turret[i] = new Polygon3D(v, v[0], v[1], v[3],  Main.textures[23], 0.01,0.01,6);
		}
		
		
	
		
		
		
		
		start.set(tempVector1);
		v = new Vector[]{put(-0.04, 0.035, 0.06), put(0.04, 0.035, 0.06), put(0.05, 0.035, 0.04), put(0.05, 0.035, -0.03), put(0.03, 0.035, -0.07),  put(-0.03, 0.035, -0.07),put(-0.05, 0.035, -0.03), put(-0.05, 0.035, 0.04)};
		turret[64] = new Polygon3D(v, put(-0.04, 0.035, 0.19), put(0.04, 0.035, 0.19), put(-0.04, 0.035, 0.09), Main.textures[13], 0.8,0.8,6);
		
		v = new Vector[]{put(0.03, 0, -0.07), put(-0.03, 0, -0.07),  put(-0.03, 0.035, -0.07),   put(0.03, 0.035, -0.07)};
		turret[65] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.6,0.3,6);
		
		v = new Vector[]{put(0.03, 0.035, -0.07), put(0.05, 0.035, -0.03), put(0.05, 0, -0.03), put(0.03, 0, -0.07)};
		turret[66] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.6,0.3,6);
		
		v = new Vector[]{put(-0.03, 0, -0.07), put(-0.05, 0, -0.03), put(-0.05, 0.035, -0.03), put(-0.03, 0.035, -0.07)};
		turret[67] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.6,0.3,6);
		
		v  = new Vector[]{put(-0.05, 0.035, 0.04), put(-0.05, 0.035, -0.03), put(-0.05, 0, -0.03), put(-0.05, 0, 0.04)};
		turret[68] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.5,0.2,6);
		
		v  = new Vector[]{put(0.05, 0, 0.04), put(0.05, 0, -0.03), put(0.05, 0.035, -0.03), put(0.05, 0.035, 0.04)};
		turret[69] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.5,0.2,6);
		
		v = new Vector[]{put(-0.04, 0.035, 0.06), put(-0.05, 0.035, 0.04), put(-0.05, 0, 0.04), put(-0.04, 0, 0.06)};
		turret[70] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.2,0.2,6);
		
		v = new Vector[]{put(0.04, 0, 0.06), put(0.05, 0, 0.04), put(0.05, 0.035, 0.04), put(0.04, 0.035, 0.06)};
		turret[71] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.2,0.2,6);
		
		//create shadow for tank turret (railgun)
		start.add(-0.04, 0, -0.04);
		start.y = -1;
		v = new Vector[]{put(-0.5, 0, 0.2), put(0.5, 0, 0.2), put(0.5, 0, -0.2), put(-0.5, 0, -0.2)};
		shadowTurret = new Polygon3D(v, v[0], v[1], v[3], Main.textures[15], 1, 1, 2);
	}
	
	public void makeTurretNuke(){
		start = turretCenter.myClone();
		Vector[] v;
		turret = new Polygon3D[171];
		int polyIndex = 0;
		
		
		//turret gun
		iDirection.set(1,0,0);
		jDirection.set(0,1,0);
		kDirection.set(0,0,1);
		
		if(!firing){
			angularSpeed--;
			if(angularSpeed < 0)
				angularSpeed = 0;
		}
		
		//adjust orientation of the turret
		nukeCannonAngle+=angularSpeed;
		nukeCannonAngle%=360;
		iDirection.rotate_XY(nukeCannonAngle);
		jDirection.rotate_XY(nukeCannonAngle);
		
		//adjust orientation of the turret
		int angle = (turretAngle - turretAngleDelta + 360)%360;
		iDirection.rotate_XZ(angle);
		kDirection.rotate_XZ(angle);
		jDirection.rotate_XZ(angle);
		
		
		//turet gun
		double r = 0.012;
		int theta = 20;
		
		tempVector1.set(start);
		
		start = put(0.017,0.017,0.1);
		nukeCannonRear = new Polygon3D[72];
		for(int i = 0; i < 18; i++){
			v = new Vector[]{put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], -0.04),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], -0.04),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], 0.06),
							 put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], 0.06)
							};
			nukeCannonRear[i] = new Polygon3D(v, v[0], v[1], v [3],  Main.textures[13], 0.001,0.01,6);
		}
		
		start.set(tempVector1);
		start = put(-0.017,0.017,0.1);
		for(int i = 0; i < 18; i++){
			v = new Vector[]{put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], -0.04),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], -0.04),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], 0.06),
							 put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], 0.06)
							};
			nukeCannonRear[i + 18] = new Polygon3D(v, v[0], v[1], v [3],  Main.textures[13], 0.001,0.01,6);
		}
		
		start.set(tempVector1);
		start = put(-0.017,-0.017,0.1);
		for(int i = 0; i < 18; i++){
			v = new Vector[]{put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], -0.04),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], -0.04),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], 0.06),
							 put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], 0.06)
							};
			nukeCannonRear[i + 36] = new Polygon3D(v, v[0], v[1], v [3],  Main.textures[13], 0.001,0.01,6);
		}
		
		start.set(tempVector1);
		start = put(0.017,-0.017,0.1);
		for(int i = 0; i < 18; i++){
			v = new Vector[]{put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], -0.04),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], -0.04),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], 0.06),
							 put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], 0.06)
							};
			nukeCannonRear[i + 54] = new Polygon3D(v, v[0], v[1], v [3],  Main.textures[13], 0.001,0.01,6);
		}
		
		start.set(tempVector1);
		r = 0.045;
		nukeCannonMiddle = new Polygon3D[19];
		
		v = new Vector[18];
		for(int i = 1; i < 19; i++)
			v[18 - i] = put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], 0.16);
		nukeCannonMiddle[0] = new Polygon3D(v, put(-0.2, 0.2, 0), put(0.2, 0.2, 0), put(-0.2, -0.2, 0),  Main.textures[13], 0.001,0.01,6);
		for(int i = 0; i < 18; i++){
			v = new Vector[]{put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], 0.16),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], 0.16),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], 0.167),
							 put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], 0.167)
							};
			nukeCannonMiddle[i + 1] = new Polygon3D(v, v[0], v[1], v [3],  Main.textures[13], 0.001,0.01,6);
		}
		
		
		
		r = 0.012;
		tempVector1.set(start);
		start = put(0.017,0.017,0.1);
		nukeCannonFront = new Polygon3D[72];
		for(int i = 0; i < 18; i++){
			v = new Vector[]{put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], 0.07),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta],0.07),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], 0.1),
							 put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], 0.1)
							};
			nukeCannonFront[i] = new Polygon3D(v, v[0], v[1], v [3],  Main.textures[13], 0.001,0.01,6);
		}
		
		start.set(tempVector1);
		start = put(-0.017,0.017,0.1);
		for(int i = 0; i < 18; i++){
			v = new Vector[]{put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], 0.07),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], 0.07),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], 0.1),
							 put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], 0.1)
							};
			nukeCannonFront[i + 18] = new Polygon3D(v, v[0], v[1], v [3],  Main.textures[13], 0.001,0.01,6);
		}
		
		start.set(tempVector1);
		start = put(-0.017,-0.017,0.1);
		for(int i = 0; i < 18; i++){
			v = new Vector[]{put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], 0.07),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], 0.07),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], 0.1),
							 put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], 0.1)
							};
			nukeCannonFront[i + 36] = new Polygon3D(v, v[0], v[1], v [3],  Main.textures[13], 0.001,0.01,6);
		}
		
		start.set(tempVector1);
		start = put(0.017,-0.017,0.1);
		for(int i = 0; i < 18; i++){
			v = new Vector[]{put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], 0.07),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], 0.07),
							 put(r* GameData.cos[(i+1)*theta], r* GameData.sin[(i+1)*theta], 0.1),
							 put(r* GameData.cos[i*theta], r* GameData.sin[i*theta], 0.1)
							};
			nukeCannonFront[i + 54] = new Polygon3D(v, v[0], v[1], v [3],  Main.textures[13], 0.001,0.01,6);
		}
		
		
		
		for(int i = 0; i < 72; i ++){
			turret[i] = nukeCannonRear[i];
		}
		
		for(int i = 0; i < 19; i ++){
			turret[72 + i] = nukeCannonMiddle[i];
		}
		
		for(int i = 0; i < 72; i ++){
			turret[i + 91] = nukeCannonFront[i];
		}
		
		
		
		
		polyIndex = 163;
		
		//turret body
		start = turretCenter.myClone();
		
		iDirection.set(1,0,0);
		jDirection.set(0,0.95,0);
		kDirection.set(0,0,1.05);
		
		//adjust orientation of the turret
		iDirection.rotate_XZ(angle);
		kDirection.rotate_XZ(angle);
		
		
		
		
		v = new Vector[]{put(-0.04, 0.035, 0.06), put(0.04, 0.035, 0.06), put(0.05, 0.035, 0.04), put(0.05, 0.035, -0.03), put(0.03, 0.035, -0.07),  put(-0.03, 0.035, -0.07),put(-0.05, 0.035, -0.03), put(-0.05, 0.035, 0.04)};
		turret[0 + polyIndex] = new Polygon3D(v, put(-0.04, 0.035, 0.19), put(0.04, 0.035, 0.19), put(-0.04, 0.035, 0.09), Main.textures[13], 0.8,0.8,6);
		
		v = new Vector[]{put(0.03, 0, -0.07), put(-0.03, 0, -0.07),  put(-0.03, 0.035, -0.07),   put(0.03, 0.035, -0.07)};
		turret[1 + polyIndex] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.6,0.3,6);
		
		v = new Vector[]{put(0.03, 0.035, -0.07), put(0.05, 0.035, -0.03), put(0.05, 0, -0.03), put(0.03, 0, -0.07)};
		turret[2 + polyIndex] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.6,0.3,6);
		
		v = new Vector[]{put(-0.03, 0, -0.07), put(-0.05, 0, -0.03), put(-0.05, 0.035, -0.03), put(-0.03, 0.035, -0.07)};
		turret[3 + polyIndex] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.6,0.3,6);
		
		v  = new Vector[]{put(-0.05, 0.035, 0.04), put(-0.05, 0.035, -0.03), put(-0.05, 0, -0.03), put(-0.05, 0, 0.04)};
		turret[4 + polyIndex] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.5,0.2,6);
		
		v  = new Vector[]{put(0.05, 0, 0.04), put(0.05, 0, -0.03), put(0.05, 0.035, -0.03), put(0.05, 0.035, 0.04)};
		turret[5 + polyIndex] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.5,0.2,6);
		
		v = new Vector[]{put(-0.04, 0.035, 0.06), put(-0.05, 0.035, 0.04), put(-0.05, 0, 0.04), put(-0.04, 0, 0.06)};
		turret[6 + polyIndex] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.2,0.2,6);
		
		v = new Vector[]{put(0.04, 0, 0.06), put(0.05, 0, 0.04), put(0.05, 0.035, 0.04), put(0.04, 0.035, 0.06)};
		turret[7 + polyIndex] = new Polygon3D(v, v[0], v[1], v [3], Main.textures[13], 0.2,0.2,6);
		
		start.add(-0.05, 0, -0.02);
		start.y = -1;
		v = new Vector[]{put(-0.7, 0, 0.2), put(0.7, 0, 0.2), put(0.7, 0, -0.2), put(-0.7, 0, -0.2)};
		shadowTurret = new Polygon3D(v, v[0], v[1], v[3], Main.textures[15], 1, 1, 2);
	}
	
	//update model 
	public void update(){
		//don't update player tank when game is finished
		if(Main.gameOver)
			return;
		
		
		if(underAttackCount > 0)
			underAttackCount--;
		
		//slowly regenerate hp
		if(HP < 150 && HP > 0 && underAttackCount == 0 && !Main.gamePaused){
			if(Main.timer%2 == 0)
				HP+=1;
		}
		
	
		
		//System.out.println(position);
		
		//the player tank is always rendered visible		
		visible = true;
		ModelDrawList.register(this);
		
		//Process user's commands:
		//Rotate tank turret to right/left
		if(turnLeft && turnRight){
			turretAngleDelta = 0;
		}else if(turnLeft){
			turretAngleDelta=4;
			turretAngle+=4;
			if(turretAngle >= 360)
				turretAngle-=360;
		}else if(turnRight){
			turretAngleDelta=356;
			turretAngle-=4;
			if(turretAngle < 0)
				turretAngle+=360;
		}else{
			turretAngleDelta = 0;
		}
		
		//Move forward and backward.
		//prior to move, the tank body must rotate to the same angle as the tank turret.
		//Calculate the angle difference between body and turret
		if(forward && backward){
			//if forward and backward keys pressed at the same time, nothing happens
			speed = 0;
			displacement.set(0,0,0);
		}else if(forward){
			//move forward
			
				if(speed < 0.015)
					speed+=0.001;

		}else if(backward){
			//move back
			
			if(speed > -0.01)
				speed-=0.001;
			
		}else{
			if(speed > 0 || speed < 0){
				if(speed > 0)
					speed -=0.002;
				if(speed < 0)
					speed+=0.002;
				if(speed > -0.002 && speed < 0.002)
					speed = 0;
			}
		}
		tempVector1.set(0,0,1);
		tempVector1.rotate_XZ(bodyAngle);
		displacement.set(tempVector1.x*speed,0, tempVector1.z*speed);
		
		if(moveLeft && moveRight){
			//if move left and move right keys pressed at the same time, nothing happens
			speed = 0;
			displacement.set(0,0,0);
		}else if(moveLeft){
			//move left
			
			bodyAngleDelta = 5;
			bodyAngle = (bodyAngle+bodyAngleDelta)%360;
			
		}else if(moveRight){
			//move right
			bodyAngleDelta = 355;
			bodyAngle = (bodyAngle+bodyAngleDelta)%360;
			
		}
		
		
		//update centre
		centre.add(displacement);
		
		//update bundary2D
		boundary2D.update(displacement);
		
		//Test if the tank  with other objects.
		//If the result is positive, then move back to last known good location.
		int newPosition = (int)(boundary2D.xPos*4) + (129-(int)(boundary2D.yPos*4))*80;
		
		if(ObstacleMap.collideWithObstacle1(this, newPosition)){
			displacement.scale(-1);
			boundary2D.update(displacement);
			centre.add(displacement);
			displacement.set(0,0,0);
		}else if(ObstacleMap.collideWithObstacle2(this, newPosition)){
			displacement.scale(-1);
			boundary2D.update(displacement);
			centre.add(displacement);
			displacement.set(0,0,0);
		}else if(outSideBorder()){
			//test tank position against island border
			displacement.scale(-1);
			boundary2D.update(displacement);
			centre.add(displacement);
			displacement.set(0,0,0);
		}else{
			//if no collision is detected, update tile index
			if(!ObstacleMap.isOccupied(newPosition)){
				ObstacleMap.removeObstacle2(position);
				ObstacleMap.registerObstacle2(this, newPosition);
				position = newPosition;
			
			}
		}
		
		
		//update 3D boundary
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 4; j++)
				boundary[i].vertex3D[j].add(displacement);
			boundary[i].update();
		}
		
		
		
		//find centre in camera coordinate
		tempCentre.set(centre);
		tempCentre.y = -1;
		tempCentre.subtract(Camera.position);
		tempCentre.rotate_XZ(Camera.XZ_angle);
		tempCentre.rotate_YZ(Camera.YZ_angle);
		
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
		tempVector1.add(-0.03, 0, -0.02);
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
		Rasterizer.rasterize(shadowBody);
		
		
		
		if(currentWeapon == 4){
			makeTurretNuke();
		}
		
		//update turret center
		turretCenter.add(displacement);
		tempVector1.set(turretCenter);
		turretCenter.subtract(centre);
		turretCenter.rotate_XZ(bodyAngleDelta);
		turretCenter.add(centre);
		tempVector2.set(turretCenter);
		tempVector2.subtract(tempVector1);
		
		//update turret polygons
		for(int i = 0; i < turret.length; i++){
			//perform vertex updates in world coordinate
			turret[i].origin.add(displacement);
			turret[i].origin.add(tempVector2);
			turret[i].origin.subtract(turretCenter);
			turret[i].origin.rotate_XZ(turretAngleDelta);
			turret[i].origin.add(turretCenter);
			
			turret[i].bottomEnd.add(displacement);
			turret[i].bottomEnd.add(tempVector2);
			turret[i].bottomEnd.subtract(turretCenter);
			turret[i].bottomEnd.rotate_XZ(turretAngleDelta);
			turret[i].bottomEnd.add(turretCenter);
			
			turret[i].rightEnd.add(displacement);
			turret[i].rightEnd.add(tempVector2);
			turret[i].rightEnd.subtract(turretCenter);
			turret[i].rightEnd.rotate_XZ(turretAngleDelta);
			turret[i].rightEnd.add(turretCenter);
			
			for(int j = 0; j < turret[i].vertex3D.length; j++){
				turret[i].vertex3D[j].add(displacement);
				turret[i].vertex3D[j].add(tempVector2);
				turret[i].vertex3D[j].subtract(turretCenter);
				turret[i].vertex3D[j].rotate_XZ(turretAngleDelta);
				turret[i].vertex3D[j].add(turretCenter);
			}
			
			turret[i].findRealNormal();
			turret[i].findDiffuse();
			
			//transform the polygon into camera coordinate
			turret[i].update();
		}
		
		if(currentWeapon == 4){
			Geometry.sortPolygons(nukeCannonRear, 0);
			Geometry.sortPolygons(nukeCannonFront, 0);
			
			int index = 0;
			
			for(int i = 0; i < 72; i++){
				turret[i] = nukeCannonFront[i];
			}
			
			index+=nukeCannonFront.length;
			
			for(int i = 0; i < nukeCannonMiddle.length; i++){
				turret[i + index] = nukeCannonMiddle[i];
			}
			
			index+=nukeCannonMiddle.length;
			
			for(int i = 0; i < 72; i++){
				turret[i + index] = nukeCannonRear[i];
			}
			
			
		}
		
		
		
		//update shadow for tank turret
		tempVector1.set(turretCenter);
		tempVector1.add(-0.04, 0, -0.04);
		
		shadowTurret.origin.add(displacement);
		shadowTurret.origin.add(tempVector2);
		shadowTurret.origin.subtract(tempVector1);
		shadowTurret.origin.rotate_XZ(turretAngleDelta);
		shadowTurret.origin.add(tempVector1);
		
		shadowTurret.bottomEnd.add(displacement);
		shadowTurret.bottomEnd.add(tempVector2);
		shadowTurret.bottomEnd.subtract(tempVector1);
		shadowTurret.bottomEnd.rotate_XZ(turretAngleDelta);
		shadowTurret.bottomEnd.add(tempVector1);
		
		shadowTurret.rightEnd.add(displacement);
		shadowTurret.rightEnd.add(tempVector2);
		shadowTurret.rightEnd.subtract(tempVector1);
		shadowTurret.rightEnd.rotate_XZ(turretAngleDelta);
		shadowTurret.rightEnd.add(tempVector1);
		
		for(int j = 0; j < shadowTurret.vertex3D.length; j++){
			shadowTurret.vertex3D[j].add(displacement);
			shadowTurret.vertex3D[j].add(tempVector2);
			shadowTurret.vertex3D[j].subtract(tempVector1);
			shadowTurret.vertex3D[j].rotate_XZ(turretAngleDelta);
			shadowTurret.vertex3D[j].add(tempVector1);
		}
		
		shadowTurret.update();
		Rasterizer.rasterize(shadowTurret);
		
		
		
		
		
		//handle attack event
		if(currentCoolDown > 0 && !Main.gamePaused)
			currentCoolDown--;
		if(firing){
			if(currentCoolDown == 0){
				if(currentWeapon == 1 && shells > 0){
					shells--;
					currentCoolDown = coolDowns[currentWeapon];
					//calculate shell direction
					Vector direction = new Vector(0,0,1);
					direction.rotate_XZ(turretAngle);
					direction.scale(0.02);
					direction.add(turretCenter);
					Projectiles.register(new Shell(direction.x, direction.y,direction.z, turretAngle,false, 0));
					if(shells == 0)
						outOfAmmo = true;
					
					
				}else if(currentWeapon == 2 && rockets > 0){
					rockets--;
					currentCoolDown = coolDowns[currentWeapon];
					//calculate initial rocket direction
					Vector tempVector1 = new Vector(0,0,1);
					tempVector1.rotate_XZ((turretAngle+270)%360);
					tempVector1.scale(0.08);
					Vector direction = new Vector(0,0,1);
					direction.rotate_XZ(turretAngle);
					direction.scale(0.05);
					direction.add(turretCenter);
					direction.add(tempVector1);
					Projectiles.register(new Rocket(direction.x, direction.y,direction.z, turretAngle ,false));
					if(rockets == 0)
						outOfAmmo = true;
				}else if(currentWeapon == 3 && slugs > 0){
					slugs--;
					currentCoolDown = coolDowns[currentWeapon];
					//calculate slug direction
					Vector direction = new Vector(0,0,1);
					direction.rotate_XZ(turretAngle);
					direction.scale(0.1);
					direction.add(turretCenter);
					new RailgunTail(direction, turretAngle, false);
					if(slugs == 0)
						outOfAmmo = true;
					
				}else if(currentWeapon == 4 && plasma > 0){
					if(angularSpeed >= 30){
						plasma--;
						currentCoolDown = coolDowns[currentWeapon];
						//calculate shell direction
						Vector direction = new Vector(0,0,1);
						direction.rotate_XZ(turretAngle);
						direction.scale(0.02);
						direction.add(turretCenter);
						Projectiles.register(new Shell(direction.x, direction.y,direction.z, (turretAngle + Main.timer%8 +356)%360 ,false, 1));
						
					}else{
						
						angularSpeed+=1;
						if(angularSpeed > 30)
							angularSpeed = 30;
					}
					
					if(plasma == 0)
						outOfAmmo = true;
				}
				
				//change weapon when run out of ammo for current weapon
				if(outOfAmmo){
					if(shells > 0 && currentWeapon != 1)
						changeWeapon(1);
					else if(rockets > 0 && currentWeapon != 2)
						changeWeapon(2);
					else if(slugs > 0 && currentWeapon != 3)
						changeWeapon(3);
					else if(plasma > 0 && currentWeapon != 4)
						changeWeapon(4);
				}
				
			}
		}
		
		if(currentWeapon == 1)
			currentAmmo = shells;
		if(currentWeapon == 2)
			currentAmmo = rockets;
		if(currentWeapon == 3)
			currentAmmo = slugs;
		if(currentWeapon == 4)
			currentAmmo = plasma;
		
		//reset action flag
		forward = false;
		backward = false;
		turnRight = false;
		turnLeft = false;
		moveRight = false;
		moveLeft = false;
		bodyAngleDelta = 0;
		turretAngleDelta = 0;	
		displacement.reset();
		firing = false;
		outOfAmmo = false;
		
	}
	
	//draw model
	public void draw(){
		//dont draw play tank when game is finished
		if(Main.gameOver)
			return;
		
		//draw body
		for(int i = 0; i < body.length; i++){
			body[i].draw();
		}
		
		//draw turret
		
		for(int i = 0; i < turret.length; i++){
			turret[i].draw();
		}
		
	}
	
	//This method tests whether the tank is within the island border which is described by a Polygon.
	//If the tank is inside this border, the sum of the angles formed by tank centre point 
	//and each edge of the polygon will approximately equal to 360 degree.
	public boolean outSideBorder(){
		double angle = 0;
		int length = Terrain.border.vertex3D.length;
		for(int i = 0; i < length; i++){
			tempVector1.set(Terrain.border.vertex3D[i]);
			tempVector1.subtract(centre);
			tempVector2.set(Terrain.border.vertex3D[(i+1+length)%length]);
			tempVector2.subtract(centre);
			double dot = tempVector1.dot(tempVector2);
			dot = dot/(tempVector1.getLength())/(tempVector2.getLength());
			angle+=Math.acos(dot);
		}
		return Math.PI*2 - angle > 0.01;
		
	}
	
	//change weapon
	public void changeWeapon(int weapon){
		if(weapon == -1){
			weapon = (currentWeapon+1)%5;
			if(weapon == 0)
				weapon = 1;
		}
		
		if(weapon == 1 && shells > 0){
			currentWeapon = 1;
			makeTurretCannon();
		}
		if(weapon == 1 && shells == 0){
			weapon = 2;
		}
		
		if(weapon == 2 && rockets > 0){
			currentWeapon = 2;
			makeTurretRocket();
		}
		if(weapon == 2 && rockets == 0){
			weapon = 3;
		}
		
		if(weapon == 3 && slugs > 0){
			currentWeapon = 3;
			makeTurretRailgun();
		}
		if(weapon == 3 && slugs == 0){
			weapon = 4;
			
		}
		
		if(weapon == 4 && plasma > 0){
			if(currentWeapon != 4){
				currentWeapon = 4;
				makeTurretNuke();
				angularSpeed = 0;
			}
			
			
			
			
		}if(weapon == 4 && plasma == 0){
			currentWeapon = 1;
			makeTurretCannon();
		}
			
	}
	
	//damage the object. (ie, reduce its hitpoint)
	//the tank will stop  regenerating after got hit
	public void damage(int damagePoint){
		//apply damage mulitplier 
		HP-=(damagePoint * 0.8);
		if(HP <= 0){
			HP = 0;
			Projectiles.register(new Explosion(centre.x, centre.y, centre.z, 1.7));
			centre.x = 1000;
			
			Main.gameOver =  true;
		}
		if(damagePoint > 0)
			underAttackCount = 100;
		
	}
	
}
