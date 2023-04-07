package core;
//This class stores powerUp objects
public class powerUps {
	public static powerUp[] PU;
	
	public static void init(){
		PU = new powerUp[100];
		
		//secret  power up
		register(new powerUp(18.15,-0.875, 16.575, 4));
		register(new powerUp(18.6,-0.875, 16.575, 4));
		register(new powerUp(18.6,-0.875, 16.15, 4));
		register(new powerUp(18.15,-0.875, 16.15, 4));
		register(new powerUp(18.37,-0.875, 16.37, 4));
		
		register(new powerUp(4.15,-0.875, 14.175, 4));
		register(new powerUp(4.6,-0.875, 14.175, 4));
		register(new powerUp(4.6,-0.875, 13.65, 4));
		register(new powerUp(4.15,-0.875, 13.65, 4));
		register(new powerUp(4.37,-0.875, 13.87, 4));
	}
	
	public static void update(){
	
		
		for(int i = 0; i < PU.length; i++){
			if(PU[i] != null){
				PU[i].update();
				if(rectangle2D.testIntersection(PU[i].boundary2D, main.PT.boundary2D)){
					if(PU[i].type == 1){
						if(main.PT.shells == 0){
							main.PT.shells +=10;
							main.PT.changeWeapon(1);
						}else{
							main.PT.shells +=10;
						}
					}
					if(PU[i].type == 2){
						if(main.PT.rockets == 0){
							main.PT.rockets +=10;
							main.PT.changeWeapon(2);
							
						}else{
							main.PT.rockets +=10;
						}
					}
					if(PU[i].type == 3){
						if(main.PT.slugs == 0){
							main.PT.slugs +=10;
							main.PT.changeWeapon(3);
						}else{
							main.PT.slugs +=10;
						}
					}
					
					if(PU[i].type == 4){
						if(main.PT.plasma == 0){
							main.PT.plasma +=20;
							main.PT.changeWeapon(4);
						}else{
							main.PT.plasma +=20;
						}
					}
						
						
					PU[i] = null;
					
				}
			}
		}
	}
	
	//add a new projectile to the array
	public static void register(powerUp P){
		for(int i = 0; i < PU.length; i++){
			if(PU[i] == null){
				PU[i] = P;
				return;
			}
		}
	}
}
