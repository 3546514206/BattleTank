package core;
//the texture dimension should only be power of 2
import java.awt.image.PixelGrabber;
import java.awt.*;

public class Texture {
	//holds the pixel data
	public short[] Texture;
	
	//stores the light maps  with different intensity
	public short[][] lightMapData;
	
	//stores a sequence of  water wave patterns;
	public byte[][] waterWave;
	public int waveIndex;
	
	//stores a sequence of explosion texture
	public int[][] explosions;
	
	//store smoke texture
	public int[] smoke;
	
	
	//dimension of the texture
	public int height, width, heightMask, widthMask, widthBits, heightBits;
	
	
	//produce a texture with a given  type
	public Texture(Image img, int widthBits , int heightBits, String type){
		this.widthBits = widthBits;
		this.heightBits = heightBits;

		height = (int)Math.pow(2, heightBits);
		width = (int)Math.pow(2, widthBits);
		
		heightMask = height -1;
		widthMask = width - 1;
		
		Texture = new short[width*height];
		int [] TextureTemp = new int[width*height];
		
		//load image and store it as an array of int
		PixelGrabber pg = new PixelGrabber(img, 0, 0, width, height, TextureTemp, 0, width);
		try {
			pg.grabPixels();
		}catch(Exception e){
			System.out.println(e);
		}
		
	
		//normal texture
		if(type.equals("normal")){
			//convert the color depth of the texture from 24bits to 15bits
			double r, g, b;
			for(int i = 0; i < width*height; i ++){
				r = (TextureTemp[i] & 0x00ff0000)>>16;
				g = (TextureTemp[i] & 0x0000ff00)>>8;
				b = (TextureTemp[i] & 0x000000ff);
				r = r/8;
				g = g/8;
				b = b/8;
				Texture[i] = (short)((int)r <<10 | (int)g << 5 | (int)b);
			}
		}
		
		if(type.equals("beachSlope")){
			//gradually change the texture to bluelish
			double r, g, b;
			double dr = 1, dg = 1, db = 1;
			for(int i = 0; i < width*height; i ++){
				r = (TextureTemp[i] & 0x00ff0000)>>16;
				g = (TextureTemp[i] & 0x0000ff00)>>8;
				b = (TextureTemp[i] & 0x000000ff);
				
				if(i%512 == 0 && i/512 > 25){
					dr-=0.0008;
					dg-=0.0004;
					//db-=0.0004;
				}
				
				
			
				r = r*dr; g = g*dg; b = b*db;
				
				r = r/8;
				g = g/8;
				b = b/8;
				Texture[i] = (short)((int)r <<10 | (int)g << 5 | (int)b);
			}
		
		}
		
		//create a completely bluelish texture
		if(type.equals("oceanFloor")){
			double r, g, b;
			for(int i = 0; i < width*height; i ++){
				r = (TextureTemp[i] & 0x00ff0000)>>16;
				g = (TextureTemp[i] & 0x0000ff00)>>8;
				b = (TextureTemp[i] & 0x000000ff);
				r*=0.5869; g*=0.7813; b*= 1;
				r = r/8;
				g = g/8;
				b = b/8;
				Texture[i] = (short)((int)r <<10 | (int)g << 5 | (int)b);
			}
			
		}
		
		//create a sequence of lightmaps, they are used to illuminate an expolsion scene
		if(type.equals("light")){
			lightMapData = new short[16][width*height];
			Texture = null;
			
			for(int j = 0; j < 16; j++){
				double I = 0;
				double d = 1/(16.25/31);
				for(int i = 0; i < width*height; i ++){
					I = ((TextureTemp[i] & 0x00ff0000)>>16);
					I = (I/8 - 12.5)*d - 3*j;
					if(I < 3)
						I = 0;
				
					lightMapData[j][i] = (short)I;
				}
			}
		}
		
		//create a shadow map 
		if(type.equals("shadow")){
			Texture =  new short[width*height];
			double I = 0;
			for(int i = 0; i < width*height; i ++){
				I = ((TextureTemp[i] & 0x00ff0000)>>16);
				I = 31 -(int)(I/8);
				if(I > 20)
					I = 20;
				Texture[i] = (short)(-I);
			}
		}
		
		//create a sequence of wave distortion patterns (to simulate water ripple)
		if(type.equals("water")){
			waterWave = new byte[128][128*128];
			Texture = null;
			
			for(int i = 0; i < 128; i++){
				int pixel_shift;
				for(int j = 0; j < 128*128; j++){
					
				 	int b = TextureTemp[j] & 0x000000ff;
				
					pixel_shift = (b - 81)*2;
					
		
					waterWave[i][(j-i*128 + 128*128)%(128*128)]  = (byte)pixel_shift;
					
				
				}
			}
		}
		
		//create distortion map
		if(type.equals("distortion")){
			for(int i = 0; i < width*height; i ++){
				int r = (TextureTemp[i] & 0x00ff0000)>>16;
				int g = (TextureTemp[i] & 0x0000ff00)>>8;
				int b = (TextureTemp[i] & 0x000000ff);
				
				
				Texture[i] = (short)((r + g + b)/3);
				Texture[i]-=81;
			}
		}
		
		//create a sequence of explosion texture
		if(type.equals("explosion")){
			explosions = new int[16][64*64];
			Texture = null;
			
			Color temp = new Color(0,0,0);
			for(int i = 0; i < 16; i++){
				int x = (i%4)*64;
				int y = (i/4)*64;
				
				for(int j = 0; j < 64*64; j++){
					int color = TextureTemp[x+y*256 + j%64 + (j/64)*256];
					temp = new Color(color);
					if(temp.getRed() < 40)
						color = 0;
					explosions[i][j] = color;
				}
			}
			
		}
		
		//create texture for smoke (mono color)
		if(type.equals("smoke")){
			Texture = null;
			smoke = new int[width*height];
			
			double r, g, b;
			for(int i = 0; i < width*height; i ++){
				r = (TextureTemp[i] & 0x00ff0000)>>16;
				g = (TextureTemp[i] & 0x0000ff00)>>8;
				b = (TextureTemp[i] & 0x000000ff);
				
				if((r + b + g)/3 < 100)
					smoke[i] = 0;
				else{
					r*=1.8;
					g*=1.8;
					b*=1.8;
					
					if(r > 255)
						r = 255;
					if(g > 255)
						g = 255;
					if(b > 255)
						b = 255;
					
					
					smoke[i] = (int)b + ((int)g <<8) + ((int)r<<16);
				}
			}
		}
		
		
		//free memory
		TextureTemp = null;
		img = null;
	}
}
