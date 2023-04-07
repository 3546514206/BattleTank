package core;
//a list of 3D models which will be sent to the drawing pipeline during a game loop
public class ModelDrawList {
	public static Model[] visibleModels;
	public static int modelCount;
	
	//create a list
	public static void makeList(){
		visibleModels = new Model[300];
		modelCount = 0;
	}
	
	//insert a model into the list
	public static void register(Model m){
		for(int i = 0; i < visibleModels.length; i ++){
			if(visibleModels[i] == null){
				visibleModels[i] = m;
				modelCount++;
				break;
			}
		}
	}
	
	
	
	public static void draw(){
		for(int i = 0; i < modelCount; i++){
			visibleModels[i].draw();
		}
	}
	
	//Sort models so the closer object will get drawn later than further objects
	//If the models all have cubic shapes, the culling algorithm compares the Z value of their centre vectors,
	//otherwise a more complicated culling algrithm will be used(based their boundary volumes).
	public static void sort(){
		//bubble sort
		for(int i = 1; i < modelCount; i++){
			for(int j = 0; j < modelCount - i; j++){
				if(Geometry.compareModels(visibleModels[j+1],visibleModels[j])){
					Model temp = visibleModels[j+1];
					visibleModels[j+1] = visibleModels[j];
					visibleModels[j] = temp;
				}
			}
		}
	}
}
