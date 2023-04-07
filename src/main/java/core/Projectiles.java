package core;
//This class stores objects with relatively short lifespan
public class Projectiles {
	public static Model[] projectilesArray;
	
	public static void init(){
		projectilesArray = new Model[150];
	}
	
	public static void update(){
		for(int i = 0; i < projectilesArray.length; i++){
			if(projectilesArray[i] != null){
				projectilesArray[i].update();
				if(projectilesArray[i].getLifeSpan() < 0)
					projectilesArray[i] = null;
			}
		}
	}
	
	//add a new projectile to the array
	public static void register(Model projectile){
		for(int i = 0; i < projectilesArray.length; i++){
			if(projectilesArray[i] == null){
				projectilesArray[i] = projectile;
				return;
			}
		}
	}

}
