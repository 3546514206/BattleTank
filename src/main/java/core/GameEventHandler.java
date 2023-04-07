package core;
//This class processes in game events one example of events 
//can be activate/disactivate an enemy, open a gate etc...

public class gameEventHandler {
	
	public static void processEvent(){
		
		//open the West energy gates when the power plants in the north West are destroyed 
		if(main.Terrain.powerPlants[0] == null && main.Terrain.powerPlants[1] == null && main.Terrain.fences[26] !=null){
			for(int i = 26; i < 38; i++){
				main.Terrain.fences[i].destory();
				main.Terrain.fences[i] = null;
			}
		}
		
		//open the east energy gates when the power plants in the north east are destroyed 
		if(main.Terrain.powerPlants[5] == null && main.Terrain.powerPlants[6] == null && main.Terrain.fences[14] !=null){
			for(int i = 14; i < 26; i++){
				main.Terrain.fences[i].destory();
				main.Terrain.fences[i] = null;
			}
		}
		
		//open the energy gates of the main base when all the outer power plants are destroyed
		//also activate the tanks inside the base
		if(main.Terrain.powerPlants[0] == null && main.Terrain.powerPlants[1] == null 
		   && main.Terrain.powerPlants[5] == null && main.Terrain.powerPlants[6] == null
		   && main.Terrain.fences[0] != null){
			for(int i = 0; i < 14; i++){
				main.Terrain.fences[i].destory();
				main.Terrain.fences[i] = null;
			}
			
			for(int i = 38; i < 43; i++){
				enemies.enemy[i].damage(-1);
			}
			
			for(int i = 96; i < 107; i++){
				enemies.enemy[i].damage(-1);
			}
			
		}
		
		//open the inner energy gates when all the inner power plants are destoryed. 
		//also activate boss tanks
		if(main.Terrain.powerPlants[2] == null && main.Terrain.powerPlants[3] == null 
		   && main.Terrain.powerPlants[4] == null && main.Terrain.fences[39] != null){
			for(int i = 38; i < 62; i++){
				main.Terrain.fences[i].destory();
				main.Terrain.fences[i] = null;
			}
			enemies.enemy[107].damage(-1);
			enemies.enemy[108].damage(-1);
			
		}
		
		//the game is beaten when both annihilators are destroyed
		if(main.gameNotStart == false){
			if(enemies.enemy[107] == null && enemies.enemy[108] == null){
				main.win = true;
				main.PT.HP = 100;
			}
				
		}
		
	
	}
	
}
