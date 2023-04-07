package core;
//This class stores powerUp objects
public class PowerUps {
	public static PowerUp[] PU;
	
	public static void init(){
		PU = new PowerUp[100];
		
		//secret  power up
		register(new PowerUp(18.15,-0.875, 16.575, 4));
		register(new PowerUp(18.6,-0.875, 16.575, 4));
		register(new PowerUp(18.6,-0.875, 16.15, 4));
		register(new PowerUp(18.15,-0.875, 16.15, 4));
		register(new PowerUp(18.37,-0.875, 16.37, 4));
		
		register(new PowerUp(4.15,-0.875, 14.175, 4));
		register(new PowerUp(4.6,-0.875, 14.175, 4));
		register(new PowerUp(4.6,-0.875, 13.65, 4));
		register(new PowerUp(4.15,-0.875, 13.65, 4));
		register(new PowerUp(4.37,-0.875, 13.87, 4));
	}
	
	public static void update(){
	
		
		for(int i = 0; i < PU.length; i++){
			if(PU[i] != null){
				PU[i].update();
				if(Rectangle2D.testIntersection(PU[i].boundary2D, Main.PT.boundary2D)){
					if(PU[i].type == 1){
						if(Main.PT.shells == 0){
							Main.PT.shells +=10;
							Main.PT.changeWeapon(1);
						}else{
							Main.PT.shells +=10;
						}
					}
					if(PU[i].type == 2){
						if(Main.PT.rockets == 0){
							Main.PT.rockets +=10;
							Main.PT.changeWeapon(2);
							
						}else{
							Main.PT.rockets +=10;
						}
					}
					if(PU[i].type == 3){
						if(Main.PT.slugs == 0){
							Main.PT.slugs +=10;
							Main.PT.changeWeapon(3);
						}else{
							Main.PT.slugs +=10;
						}
					}
					
					if(PU[i].type == 4){
						if(Main.PT.plasma == 0){
							Main.PT.plasma +=20;
							Main.PT.changeWeapon(4);
						}else{
							Main.PT.plasma +=20;
						}
					}
						
						
					PU[i] = null;
					
				}
			}
		}
	}
	
	//add a new projectile to the array
	public static void register(PowerUp P){
		for(int i = 0; i < PU.length; i++){
			if(PU[i] == null){
				PU[i] = P;
				return;
			}
		}
	}
}
