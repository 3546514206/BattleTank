package core;

public class gunTurret extends solidObject{
	//polygons for gunTurret body
	private polygon3D[] body;
	
    //polygons for tank turret
	private polygon3D[] turret;
	
	//the shadow of tank body
	private polygon3D shadowBody;
	
	//the shadow of tank turret
	private polygon3D shadowTurret;
	
	//the centre of the body in camera coordinate
	private vector bodyCenter;
	
	//the centre of the turret (pivot point for rotation)
	private vector turretCenter;
	
	//total angle that the turret has rotated from the initial position. (in the x-z plane)
	private int turretAngle;
	
	//the target angle with respect to  body centre
	private int targetAngle;
	
	//degrees the  gun turret has rotated in a frame
	private int turretAngleDelta;
	
	//movement flag
	private boolean aimRight, aimLeft, firing;
	
	//time needed before a weapon can be fired again
	private int coolDown = 20;
	
	//current coolDown
	private int currentCoolDown = 0;
	
	//The position index of the turret  in the grid map
	private int position;
	
	//a smoke tail
	private smoke Smoke;
	
	//whether the gun turret is visible in the previous frame
	private boolean isVisiblePreviousFrame;
	
	//temporary vectors which will be used for vector arithmetic
	private vector tempVector1 = new vector(0,0,0);
	private vector tempVector2 = new vector(0,0,0);
	
	//an AI flag  indicates whether it has engaged with player tank
	private boolean engaged;
	
	//distance from player tank
	private double distance;
	
	//an AI flag indicates whether there is a type 2 obstacle between gunTurret and player tank
	private boolean clearToShoot;
	
	private boolean destoried;
	
	
	//constructor
	public gunTurret(double x, double y, double z, int angle){
		//define the centre point of this model(also the centre point of tank body)
		start = new vector(x,y,z);
		iDirection = new vector(0.7,0,0);
		jDirection = new vector(0,0.7,0);
		kDirection = new vector(0,0,0.7);
		
		//boundary of this model has a cubic shape (ie l = w)
		modelType = 2;  
		makeBoundary(0.1, 0.25, 0.1);
		
		//create 2D boundary
		boundary2D = new rectangle2D(x - 0.13, z + 0.13, 0.26, 0.26);
		position = (int)(x*4) + (129-(int)(z*4))*80;
		obstacleMap.registerObstacle2(this, position);
		
		//find centre of the model in world coordinate
		findCentre();
		
		turretCenter = start.myClone();
		bodyCenter = centre;
		turretAngle = angle;
		
		makeBody();
		makeTurret();
		
		//gun Turret has 75 hit points
		HP = 75;
		
		lifeSpan = 1;
	}
	
	//create polygons for gun turret lower part
	private void makeBody(){
		vector[] v;
		
		body = new polygon3D[5];
		
		v = new vector[]{put(-0.09, 0.09, -0.09), put(0.09, 0.09, -0.09), put(0.12, 0, -0.12),put(-0.12, 0, -0.12)};
		body[0] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
		
		v = new vector[]{put(-0.09, 0.09, 0.09), put(-0.09, 0.09, -0.09), put(-0.12, 0, -0.12),put(-0.12, 0, 0.12)};
		body[1] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
		
		v = new vector[]{put(0.12, 0, 0.12), put(0.12, 0, -0.12), put(0.09, 0.09, -0.09),put(0.09, 0.09, 0.09)};
		body[2] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
		
		v = new vector[]{put(-0.12, 0, 0.12), put(0.12, 0, 0.12), put(0.09, 0.09, 0.09),put(-0.09, 0.09, 0.09)};
		body[3] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
		
		v = new vector[]{put(-0.09, 0.09, 0.09), put(0.09, 0.09, 0.09) ,put(0.09, 0.09, -0.09), put(-0.09, 0.09, -0.09)};
		body[4] = new polygon3D(v, v[0], v[1], v [3], main.textures[41], 1,1,6);
		
		tempVector1 = start.myClone();
		
		iDirection.scale(1.2);
		start.add(0,0,-0.03);
		v = new vector[]{put(-0.2, 0, 0.2), put(0.2, 0, 0.2),put(0.2, 0, -0.2),put(-0.2, 0, -0.2)};
		shadowBody = new polygon3D(v, v[0], v[1],v[3], main.textures[50], 1,1,2);
		
		for(int i = 0; i < body.length; i++){
			body[i].Ambient_I = 22;
			body[i].diffuse_coefficient = 0.7;
			body[i].findDiffuse();
			
		}
		
		start.set(tempVector1);
		iDirection.scale(0.8333333333);
		
	}
	
	//	create polygons for gun turret upper part
	private void makeTurret(){
		iDirection = new vector(0.7,0,0);
		jDirection = new vector(0,0.7,0);
		kDirection = new vector(0,0,0.7);
		
		//adjust orientation of the turret
		iDirection.rotate_XZ(turretAngle);
		kDirection.rotate_XZ(turretAngle);
		
		iDirection.rotate_XZ(180);
		kDirection.rotate_XZ(180);
		
		
		turret = new polygon3D[22];
		
		vector[] v;
		
		
		v = new vector[]{put(-0.04, 0.16, -0.08), put(0.04, 0.16, -0.08), put(0.04, 0.09, -0.08), put(-0.04, 0.09, -0.08)};
		turret[0] = new polygon3D(v, v[0], v[1], v [3], main.textures[51], 1,0.5,6);
		
		v = new vector[]{put(-0.08, 0.16, 0.08), put(-0.04, 0.16, -0.08), put(-0.04, 0.09, -0.08), put(-0.08, 0.09, 0.08)};
		turret[1] = new polygon3D(v, v[0], v[1], v [3], main.textures[51], 1,0.5,6);
		
		v = new vector[]{put(0.08, 0.16, 0.08), put(-0.08, 0.16, 0.08), put(-0.08, 0.09, 0.08), put(0.08, 0.09, 0.08)};
		turret[2] = new polygon3D(v, v[0], v[1], v [3], main.textures[51], 1,0.5,6);
		
		v = new vector[]{put(0.04, 0.16, -0.08), put(0.08, 0.16, 0.08), put(0.08, 0.09, 0.08), put(0.04, 0.09, -0.08)};
		turret[3] = new polygon3D(v, v[0], v[1], v [3], main.textures[51], 1,0.5,6);
		
		double r = 0.016;
		double r1 = 0.012;
		double theta = Math.PI/8;
		start.add(0,0.1,0);
		
		
		for(int i = 0; i < 16; i++){
			v = new vector[]{put(r1*Math.cos(i*theta), r1*Math.sin(i*theta), -0.22),
							 put(r1*Math.cos((i+1)*theta), r1*Math.sin((i+1)*theta), -0.22),
							 put(r*Math.cos((i+1)*theta), r*Math.sin((i+1)*theta), -0.08),
							 put(r*Math.cos(i*theta), r*Math.sin(i*theta), -0.08)
							};
			turret[i + 4] = new polygon3D(v, v[0], v[1], v [3],  main.textures[23], 0.001,0.01,6);
			turret[i + 4].Ambient_I = 18;
			turret[i + 4].diffuse_coefficient = 0.7;
			turret[i + 4].findDiffuse();
		}
		
		v = new vector[16];
		for(int i = 1; i < 17; i++)
			v[16 - i] = put(r1*Math.cos(i*theta), r1*Math.sin(i*theta), -0.22);
		turret[21] = new polygon3D(v, v[0], v[1], v [3],  main.textures[23], 1,1,6);
		
		start.add(0,-0.1,0);
		
		v = new vector[]{put(-0.08, 0.16, 0.08), put(0.08, 0.16, 0.08), put(0.04, 0.16, -0.08), put(-0.04, 0.16, -0.08)};
		turret[20] = new polygon3D(v, v[0], v[1], put(-0.08, 0.16, -0.08), main.textures[51], 1,1,6);
		
		
		
		//create shadow for tank turret (cannon)
		start.add(-0.05, 0, -0.05);
		start.y = -1;
		v = new vector[]{put(0.5, 0, -0.27), put(-0.5, 0, -0.27), put(-0.5, 0, 0.27), put(0.5, 0, 0.27)};
		shadowTurret = new polygon3D(v, v[0], v[1], v[3], main.textures[15], 1, 1, 2);
		start.add(0.05, 0, 0.05);
		
		iDirection.rotate_XZ(180);
		kDirection.rotate_XZ(180);
		
	}
	
	
	public void update(){
		if(HP > 0 && !main.gamePaused)
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
		
		//if this model is not visible in the previous frame, its need to be reconstructed
		if(visible){
			if(isVisiblePreviousFrame == false){
				//recreate turret polygons
				makeTurret();
				isVisiblePreviousFrame = true;
			}
		}
		
		//if visible then update the geometry to camera coordinate
		if(visible){
			modelDrawList.register(this);
			
			//update boundary
			for(int i = 0; i < 5; i++)
				boundary[i].update();	
			
			
			
			//update polygons
			for(int i = 0; i < body.length; i++){
				body[i].update();
			}
			
			//draw body shadow
			shadowBody.update();
			if(shadowBody.visible){
				rasterizer.rasterize(shadowBody);
			}
				
			if(HP > 0){
				//update turret polygons
				for(int i = 0; i < turret.length; i++){
					//perform vertex updates in world coordinate
					
					turret[i].origin.subtract(turretCenter);
					turret[i].origin.rotate_XZ(turretAngleDelta);
					turret[i].origin.add(turretCenter);
					
					
					turret[i].bottomEnd.subtract(turretCenter);
					turret[i].bottomEnd.rotate_XZ(turretAngleDelta);
					turret[i].bottomEnd.add(turretCenter);
					
				
					turret[i].rightEnd.subtract(turretCenter);
					turret[i].rightEnd.rotate_XZ(turretAngleDelta);
					turret[i].rightEnd.add(turretCenter);
					
					for(int j = 0; j < turret[i].vertex3D.length; j++){
						
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
				tempVector1.add(-0.05, 0, -0.05);
				
			
				shadowTurret.origin.subtract(tempVector1);
				shadowTurret.origin.rotate_XZ(turretAngleDelta);
				shadowTurret.origin.add(tempVector1);
				
			
				shadowTurret.bottomEnd.subtract(tempVector1);
				shadowTurret.bottomEnd.rotate_XZ(turretAngleDelta);
				shadowTurret.bottomEnd.add(tempVector1);
				
				
				shadowTurret.rightEnd.subtract(tempVector1);
				shadowTurret.rightEnd.rotate_XZ(turretAngleDelta);
				shadowTurret.rightEnd.add(tempVector1);
				
				for(int j = 0; j < shadowTurret.vertex3D.length; j++){
					
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
				direction.scale(0.06);
				projectiles.register(new shell(centre.x+direction.x, centre.y,centre.z+direction.z, turretAngle ,true, 1));
			}
		}
		
		if(HP <= 37){
			if(Smoke == null){
				tempVector1 = getRealCentre().myClone();
				tempVector1.y += 0.1;
				Smoke = new smoke(tempVector1.myClone());
			}else{
				if(visible)
					Smoke.update();
			}
		}
		
		if(HP <= 0){
			if(!destoried){
				projectiles.register(new explosion(centre.x, centre.y, centre.z, 2));
				modelType = 6; 
				Smoke.stopped = true;
			}
			destoried = true;
		}
		
		//reset action flag
		aimRight = false;
		aimLeft = false;
		turretAngleDelta = 0;	
		firing = false;
		
	}
	
	private void processAI(){
//		calculate distance from player's tank
		tempVector1.set(centre);
		tempVector1.subtract(playerTank.bodyCenter);
		distance = tempVector1.getLength();
		
		//medium tank become aware of player's tank when the distance is less than 2
		if(distance < 2.3)
			engaged = true;
		else
			engaged = false;
		
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
			for(int i = 0; (d < distance) && (i < 35); i++, tempVector1.add(tempVector2), d+=0.125){
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

			}
			
			//cauculate the difference between those 2 angles
			int AngleDelta = turretAngle - targetAngle;
			if(Math.abs(AngleDelta) < 3 && clearToShoot && distance < 2.25)
				firing = true;
			
			
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
			
		}
	}
	
	public void draw(){
		//draw Body
		for(int i = 0; i < body.length; i++){
			body[i].draw();
			
		}
		
		//draw turret
		if(HP >0 ){
			for(int i = 0; i < turret.length; i++){
				turret[i].draw();
			}
		}
		
		//draw smoke tail
		if(Smoke != null && visible)
			Smoke.draw();
	
	}
	
    //return the 2D boundary of this model
	public rectangle2D getBoundary2D(){
		return boundary2D;
	}
	
	public void damage(int damagePoint){
		HP-=damagePoint;
	}
}
