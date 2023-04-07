package core;
//This class processes in game events one example of events 
//can be activate/disactivate an enemy, open a gate etc...

public class GameEventHandler {
	
	public static void processEvent(){
		
		//open the West energy gates when the power plants in the north West are destroyed 
		if(Main.Terrain.powerPlants[0] == null && Main.Terrain.powerPlants[1] == null && Main.Terrain.fences[26] !=null){
			for(int i = 26; i < 38; i++){
				Main.Terrain.fences[i].destory();
				Main.Terrain.fences[i] = null;
			}
		}
		
		//open the east energy gates when the power plants in the north east are destroyed 
		if(Main.Terrain.powerPlants[5] == null && Main.Terrain.powerPlants[6] == null && Main.Terrain.fences[14] !=null){
			for(int i = 14; i < 26; i++){
				Main.Terrain.fences[i].destory();
				Main.Terrain.fences[i] = null;
			}
		}
		
		//open the energy gates of the main base when all the outer power plants are destroyed
		//also activate the tanks inside the base
		if(Main.Terrain.powerPlants[0] == null && Main.Terrain.powerPlants[1] == null
		   && Main.Terrain.powerPlants[5] == null && Main.Terrain.powerPlants[6] == null
		   && Main.Terrain.fences[0] != null){
			for(int i = 0; i < 14; i++){
				Main.Terrain.fences[i].destory();
				Main.Terrain.fences[i] = null;
			}
			
			for(int i = 38; i < 43; i++){
				Enemies.enemy[i].damage(-1);
			}
			
			for(int i = 96; i < 107; i++){
				Enemies.enemy[i].damage(-1);
			}
			
		}
		
		//open the inner energy gates when all the inner power plants are destoryed. 
		//also activate boss tanks
		if(Main.Terrain.powerPlants[2] == null && Main.Terrain.powerPlants[3] == null
		   && Main.Terrain.powerPlants[4] == null && Main.Terrain.fences[39] != null){
			for(int i = 38; i < 62; i++){
				Main.Terrain.fences[i].destory();
				Main.Terrain.fences[i] = null;
			}
			Enemies.enemy[107].damage(-1);
			Enemies.enemy[108].damage(-1);
			
		}
		
		//the game is beaten when both annihilators are destroyed
		if(Main.gameNotStart == false){
			if(Enemies.enemy[107] == null && Enemies.enemy[108] == null){
				Main.win = true;
				Main.PT.HP = 100;
			}
				
		}
		
	
	}
	
}
