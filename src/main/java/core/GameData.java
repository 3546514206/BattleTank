package core;
//Store useful arithmetic data for the game engine such as 
//Cos/Sin look up table, color palette, etc...
public class GameData {
	public static int[] random;
	public static int randomIndex;
	public static double[] sin;  
	public static double[] cos;
	public static int[][] colorTable;
	public static int[] screenTable;
	public static Vector[] randomVectors;
	public static int[][] size; 
	public static int[] distortion1;
	public static short[] distortion2;
	
	
	public static void makeData(){
		//Make a screen table, so a pixel index can be retrived quickly
		screenTable = new int[480];
		for(int i = 0; i < 480; i++)
			screenTable[i] = 640*i;
		
		//Make random number table
		random = new int[1000];
		for(int i = 0; i < 1000; i ++)
			random[i] = (int)(Math.random()*100);
		
		random = new int[]{
95,48,43,59,92,62,51,46,94,96,3,96,86,92,67,96,53,87,21,39,77,66,89,19,88,74,90,84,16,97,31,1,17,60,38,5,6,53,60,55,78,6,66,68,49,26,27,15,9,70,10,
82,43,28,34,56,20,39,83,73,61,66,56,76,98,51,33,65,83,12,6,3,30,24,14,8,62,96,37,9,45,21,62,77,35,69,80,35,86,37,19,40,33,25,89,78,85,78,58,68,30,
76,46,78,90,64,93,33,31,14,19,10,72,87,69,12,23,6,3,81,16,94,86,8,95,18,81,60,53,94,87,41,29,47,69,18,15,80,16,75,18,9,54,49,4,42,34,84,39,30,29,
84,7,7,39,79,24,17,78,39,2,14,31,39,73,11,79,53,33,32,22,20,14,57,14,93,58,68,84,46,18,39,12,49,88,27,69,89,6,65,65,52,76,12,63,3,4,91,88,60,19,
92,52,72,51,31,15,15,89,61,0,74,57,96,53,72,6,76,92,81,50,38,35,32,9,39,66,45,93,13,44,50,98,65,49,41,30,84,1,87,53,60,81,66,98,12,36,32,44,80,63,
9,35,34,85,14,95,60,99,50,36,2,83,76,70,39,93,54,72,41,51,90,83,2,24,55,48,59,92,17,56,32,21,60,72,94,32,63,84,31,1,13,79,7,99,74,61,59,47,96,3,
40,8,62,99,6,38,59,3,98,81,73,36,79,50,8,0,35,59,32,1,84,8,94,64,43,52,17,1,27,41,75,54,71,35,61,43,75,75,50,19,41,12,73,2,35,75,89,3,20,83,
32,94,50,3,99,5,65,83,4,28,26,70,5,96,64,0,58,37,27,91,47,94,36,77,18,15,14,72,1,51,44,47,48,3,1,87,87,86,62,72,67,9,69,28,23,89,86,79,54,62,
55,31,34,79,70,12,58,14,96,13,31,66,52,43,56,53,17,76,8,83,83,11,12,97,74,57,29,68,69,89,18,70,0,36,11,12,9,89,40,83,12,29,5,18,78,52,86,80,80,20,
51,15,74,3,71,5,63,25,46,41,63,44,59,13,63,81,55,86,54,23,83,40,56,4,65,39,7,16,97,71,91,57,32,15,60,88,96,7,85,33,7,25,15,34,11,7,33,32,23,25,
24,15,72,48,20,86,67,69,71,27,81,36,16,78,1,32,34,91,63,67,68,39,76,98,68,37,57,8,42,29,65,5,67,11,82,58,61,32,13,16,60,52,94,7,49,27,72,50,79,1,
10,89,92,15,78,76,93,43,64,24,48,1,73,83,89,91,28,72,81,72,94,59,60,73,7,52,10,24,81,39,16,69,94,95,86,22,67,24,35,17,53,47,36,95,48,62,19,83,35,32,
44,99,72,55,24,56,87,25,61,8,69,31,77,32,84,24,57,14,11,80,99,77,88,12,54,38,96,74,88,96,38,33,10,87,79,88,55,47,2,36,49,15,87,12,96,64,61,62,36,69,
10,15,43,99,97,76,66,21,48,93,40,89,2,63,1,29,40,33,26,88,48,45,73,69,29,53,34,52,62,92,52,37,32,7,14,54,7,32,30,8,3,71,64,52,73,68,79,4,29,50,
20,63,3,82,79,8,93,53,24,96,34,71,34,24,74,50,95,56,18,36,11,72,98,0,55,25,48,41,69,69,42,99,89,66,7,96,79,29,55,15,41,33,23,29,33,91,54,84,83,52,
87,68,1,53,4,94,96,29,73,35,87,72,98,86,52,96,67,20,90,61,86,45,3,86,83,1,73,83,24,46,95,21,51,16,84,41,45,3,65,34,70,2,6,94,63,28,4,72,82,71,
6,60,73,72,86,43,12,75,22,85,6,53,91,11,62,80,13,91,42,52,73,45,77,59,42,16,7,42,59,82,3,68,88,33,88,3,74,53,11,69,96,97,56,8,30,91,62,9,13,50,
83,95,21,14,29,76,66,9,72,70,13,49,6,78,85,87,59,90,27,55,69,32,81,37,56,24,42,36,64,57,16,23,18,7,80,76,49,90,39,47,78,16,37,26,1,86,75,95,87,31,
83,24,76,74,81,73,22,85,40,32,80,65,15,54,77,92,5,16,74,45,79,53,50,11,87,13,56,93,35,98,71,95,72,48,55,14,95,72,45,23,38,4,50,71,41,35,18,20,66,52,
5,55,41,59,63,13,48,2,56,32,31,25,66,69,68,31,24,59,13,81,56,22,7,78,22,41,76,45,92,89,8,50,73,93,31,1,22,18,0,47,78,17,39,41,71,32,58,10,80,};
		
		/*System.out.println("random = new int[]{"); it might just drop a few of
		for(int i = 0; i < 1000; i ++){
			System.out.print(random[i] + ",");  
			if(i%50 == 0 && i > 0)rage tra
				System.out.println();
		}
		System.out.println("};");*/
		
		//Make sin and cos look up tables
		sin = new double[361];
		cos = new double[361];
		for(int i = 0; i < 361; i ++){
			sin[i] = Math.sin(Math.PI*i/180);
			cos[i] = Math.cos(Math.PI*i/180);
		}
		
		//make color palette.
		//The main color palette has 32768 (15bits) different colors with 64 different intensity levels, 
		//the default intensity is at level 31 .
		colorTable = new int[64][32768];
		int[][] colorTableTemp = new int[32768][64];
		
		double r, g, b, dr, dg, db;
		int r_, g_, b_;
		
		for(int i = 0; i < 32768; i++){
			r = (double)((i & 31744) >> 10)*8;
			g = (double)((i & 992) >> 5)*8;
			b = (double)((i & 31))*8;
			
			dr = r*0.9/32;
			dg = g*0.9/32;
			db = b*0.9/32;
			
			//calculated the intensity from lvl 0 ~ 31
		    for(int j = 0; j < 32; j++){
				r_ = (int)(r-dr*j);
				g_ = (int)(g-dg*j);
				b_ = (int)(b-db*j);
				colorTableTemp[i][31 - j] = b_ + (g_<<8)+ (r_<<16);
			}
		    
		    dr = r*0.7/32;
			dg = g*0.7/32;
			db = b*0.7/32;
		    
		    //calculated the intensity from lvl 32 ~ 63
		    for(int j = 1; j <= 32; j++){
				r_ = (int)(r+dr*j);
				g_ = (int)(g+dg*j);
				b_ = (int)(b+db*j);
				if(r_ > 245)
					r_ = 245;
				if(g_ > 245)
					g_ = 245;
				if(b_ > 245)
					b_ = 245;
				colorTableTemp[i][31 + j] = b_ + (g_<<8)+ (r_<<16);
			}
		}
		
		for(int i = 0; i < 64; i++){
			for(int j = 0; j <32768; j++ )
				colorTable[i][j] = colorTableTemp[j][i];
		}
		
		//free memory used by creating color table
		colorTableTemp = null;
		
		//create randomVectors, they will be used in generating smoke particles
		randomVectors = new Vector[1000];
		for(int i = 0; i < 1000; i++)
			randomVectors[i] = new Vector(Math.random()*0.016 - 0.008, 0.01, Math.random()*0.016 - 0.008);
		
		//generate sprites for particles with different size
		size = new int[9][];
		size[0] = new int[]{0,-1, -640};
		size[1] = new int[]{-641,0,-1, -640};
		size[2] = new int[]{1, 0,-1, -640,640};
		size[3] = new int[]{-641,-639,1, 0,-1, -640,640};
		size[4] = new int[]{-641,-639,1, 0,-1, -640,640, 639, 641};
		size[5] = new int[]{-1280, -1281, -642,-641,-640, -639, -2, -1, 0, 1, 639, 640};
		size[6] = new int[]{-1281, -1279, -642,-638,638, 642,1279, 1281,-1280,-641,-2,-639,1, 2, 0,-1, -640,640, 639, 641, 1280};
		size[7] = new int[]{-1278, -1282, 1282, 1278, -1920, 1920, -3, 3, -1281, -1279, -642,-638,638, 642,1279, 1281,-1280,-641,-2,-639,1, 2, 0,-1, -640,640, 639, 641, 1280};
		size[8] = new int[]{0};
		
		
		distortion1 = new int[128*128];
		distortion2 = new short[128*128];
		for(int i = 0; i < 128*128; i++){
			distortion2[i] = (short)((getRandom()*1.5) - 75);
		}
		
		for(int j = 0; j <2; j ++){
			
			for(int i = 0; i < 128*128; i++)
				distortion1[i] = distortion2[i];
			
			for(int i = 0; i < 128*128; i++){
				int average = 0;
				
				for(int y = -3; y < 4; y ++){
					for(int x = -3; x < 4; x++){
						int index = ((i + 128*y + x)+ 128*128)%(128*128);
						
						average += distortion1[index];
					}
				}
				
				
				distortion2[i] = (short)(average/49);
				
			}
		}
		
		//generate distortion1 map, it solely used by stealth tank 
		distortion1 = new int[128*128];
		for(int j = 0; j < 128; j++){
			for(int k = 0; k < 128; k++)
				distortion1[j*128 + k] = (int)(Math.sin(Math.PI/32*k + Math.PI/8)*10*Math.sin(Math.PI/16*j));
		}
		
		
		
	}
	
	//get a random number
	public static int getRandom(){
		randomIndex++;
		if(randomIndex >= 1000)
			randomIndex=0;
		return random[randomIndex];
		
	}
	
	//get a random vector
	public static Vector getRandomVector(){
		randomIndex++;
		if(randomIndex >= 1000)
			randomIndex=0;
		return randomVectors[randomIndex];
		
	}
	
	//It frees the data stored when the applet is finished
	public static void destory(){
		random = null;
		randomVectors = null;
		sin = null;  
		cos = null;
		colorTable = null;
		screenTable = null;
	}
}
