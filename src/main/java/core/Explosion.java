package core;

public class Explosion extends SolidObject {
	
	//size of the explosion 
	double size;
	
	//which explosion sprite to use
	public int spriteIndex;
	
	//current frame Index;
	public int frameIndex = 0;
	
	//current aura index;
	public int auraIndex = 0;
	
	//default damage = 5;
	public int damage = 5;
	
	//type of explosion 0 = normal   1 = plasma
	public int type;
	
	//centre of explosion in tile map
	public int groundZero;
	
	//Enable this boolean if this explosion has be to drawn explicitly
	public boolean explicitDrawing;
	
	public Polygon3D explosionAura;
	
	public Explosion(double x, double y, double z, double size){
		start = new Vector(x,y,z);
		iDirection = new Vector(1,0,0);
		jDirection = new Vector(0,1,0);
		kDirection = new Vector(0,0,1);
		
		int random = GameData.getRandom();
		if(random >= 75 )
			spriteIndex = 17;
		else if(random >= 50 )
			spriteIndex = 18;
		else if(random >= 25)
			spriteIndex = 19;
		else
			spriteIndex = 20;

		if(size > 1)
			spriteIndex = 18;
		
		
		Vector[] v = new Vector[]{put(-0.3, 0, 0.3), put(0.3, 0, 0.3), put(0.3, 0, -0.3),put(-0.3, 0, -0.3)};
		if(size > 3)
			v = new Vector[]{put(-0.12, 0, 0.12), put(0.12, 0, 0.12), put(0.12, 0, -0.12),put(-0.12, 0, -0.12)};
		
		explosionAura = new Polygon3D(v, v[0], v[1], v[3], Main.textures[21], 1, 1, 2);
		
		this.size = size;
		
		//boundary of this model has a cubic shape (ie length ~= width ~= height)
		modelType = 4;  
		makeBoundary(0.001, 0.001, 0.001);
		
		//create 2D boundary
		boundary2D = new Rectangle2D(x - 0.1, z + 0.1, 0.2, 0.2);
		
		lifeSpan = 16;
		
		//find centre of the model in world coordinate
		findCentre();
		
		//find centre of explosion
		groundZero = (int)(x*4) + (129-(int)(z*4))*80;
	}
	
	//animate explosion scene
	public void update(){
		//always visible
		visible = true;
		
		if(explosionAura != null && damage != 0){
			explosionAura.update();
			if(explosionAura.visible){
				explosionAura.myTexture.Texture = explosionAura.myTexture.lightMapData[auraIndex];
				Rasterizer.rasterize(explosionAura);
			}
		}
		auraIndex++;
		
		//send to draw list
		if(!explicitDrawing)
			ModelDrawList.register(this);
		
		//find centre in camera coordinate
		
		tempCentre.set(centre);
		tempCentre.subtract(Camera.position);
		tempCentre.rotate_XZ(Camera.XZ_angle);
		tempCentre.rotate_YZ(Camera.YZ_angle);
		
		
		//damage nearby units
		if(lifeSpan == 15 && damage != 0){
			ObstacleMap.damageType2Obstacles(damage, boundary2D, groundZero);
		}
		
		lifeSpan--;
		
		if(lifeSpan == 0){
			lifeSpan = -1;
			return;
		}
	}
	
	//draw  explosion scene
	public void draw(){
		//calculate explosion size
		
		tempCentre.updateLocation();
		double ratio = size*2/tempCentre.z;
		
		
		//draw sprite
		
		
		
		Rasterizer.temp = this.type;
		Rasterizer.renderExplosionSprite(Main.textures[spriteIndex].explosions[frameIndex],ratio, tempCentre.screenX, tempCentre.screenY, 64, 64);
		
		frameIndex++;
		
	}
	
	//return the 2D boundary of this model
	public Rectangle2D getBoundary2D(){
		return boundary2D;
	}
}
