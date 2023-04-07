package core;
//The rasterizer class will draw any polygon into the screen buffer.
//The texture mapping methods will differ depends on the type of polygon,
//The universal formula for texture mapping is:
//               x = A dot W/C dot W
//               y = B dot W/C dot W
// where    A = V cross O  B = O cross U  C = U cross V,   
//          V, a vector representing the texture's y direction
//          U, a vector representing the texture's x direction
//          O, the origin of the texture 
//          W is the projection length of the texel on the clipping plane
//          x, y is the texture coordinate 

//Current limitations: Only works for 640 x 480 resolution.
//                     Won't handle z-axis rotation.
//                     Doesn't save/compare z values as i am only using painter's method for sorting polygons. 
//                     Light maps could only apply on  terrain.



public class Rasterizer {
	//The 2 arrays will hold the scan lines of the polygon
	public static int[] xLow = new int[480], xHigh = new int[480];
	
	//Texture coordinate vectors
	public static Vector
	W = new Vector(0,0,0),
	O = new Vector(0,0,0),
	V = new Vector(0,0,0),
	U = new Vector(0,0,0),
	A = new Vector(0,0,0),
	B = new Vector(0,0,0),
	C = new Vector(0,0,0);
	
	//A pool of vectors which will be used for vector arithmetic
	public static Vector
		tempVector1 = new Vector(0,0,0),
		tempVector2 = new Vector(0,0,0),
		tempVector3 = new Vector(0,0,0),
		tempVector4 = new Vector(0,0,0);
	
	//the polygon that rasterizer is working on
	public static Polygon3D poly;
	
	//these variables will represent their equivalents in the polygon3D class during rasterization
	public static Vector[] tempVertex, vertex2D;
	public static int L, widthMask, heightMask, widthBits, diffuse_I;
	public static double A_offset, B_offset, C_offset;
	
	//the transparency level of the polygon. 
	public static int alpha;
	
	//the amount of vertex after clipping
	public static int visibleCount;
	
	//temporary variables that will be used in texture mapping
	public static double aDotW, bDotW, cDotW, cDotWInverse;
	public static int BigX, BigY, d_x, d_y, k, X1, Y1, BigDx, BigDy, dx, dy, X, Y, textureIndex, temp, temp1, temp2, r,g,b, scale, yOffset, xOffset;
	public static short I, variation;
	
	
	
	//start rasterization
	public static void rasterize(Polygon3D polygon){
		poly = polygon;
		L = poly.L;
		widthMask = poly.widthMask;
		heightMask = poly.heightMask;
		widthBits = poly.widthBits;
		tempVertex = poly.tempVertex;
		vertex2D = poly.vertex2D;
		alpha = poly.alpha;
		
		//find the vectors which represent the texture coordinate
		if(poly.type != 7)
			findVectorOUV();
		
		//if the polygon is completely inside the screen then no need to do clipping
		if(poly.withinViewScreen){
			visibleCount = L;
			for(int i =0; i < L; i ++){
				vertex2D[i].set(tempVertex[i]);
				vertex2D[i].setScreenLocation(tempVertex[i]);
			}
		}else
			findClipping();
		
		//scan line conversion
		scanPolygon();
		
		//for different polygons, the texture mapping alogrithm will differ depend 
		//on the nature of the polygon in order to optimize rendering
		if(poly.type == 6)
			renderModel();
		else if(poly.type == 1) 
			renderTerrain();
		else if(poly.type == 2)
			renderLightMap();
		else if(poly.type == 3)
			renderLightMap2();
		else if(poly.type == 5)
			renderWater();
		else if(poly.type == 7)
			renderSoildPolygon();
		else if(poly.type == 8)
			renderTransparent1();
		else if(poly.type == 9)
			renderTransparent2();
	}
	
	//calculate O,U and V
	public static void findVectorOUV(){
		O.set(poly.origin);
		O.subtract(Camera.position);
		O.rotate_XZ(Camera.XZ_angle);
		O.rotate_YZ(Camera.YZ_angle);

		U.set(poly.rightEnd);
		U.subtract(Camera.position);
		U.rotate_XZ(Camera.XZ_angle);
		U.rotate_YZ(Camera.YZ_angle);

		V.set(poly.bottomEnd);
		V.subtract(Camera.position);
		V.rotate_XZ(Camera.XZ_angle);
		V.rotate_YZ(Camera.YZ_angle);
		
		U.subtract(O);
		U.unit();
		U.scale(poly.textureScaleX);

		V.subtract(O);
		V.unit();
		V.scale(poly.textureScaleY);

		A = V.cross(O);
		B = O.cross(U);
		C = U.cross(V);
	}
	
	//clipping
	public static void findClipping(){
		visibleCount = 0;
		//the clipping algorithm iterate through all the vertex of the polygons, if it finds
		//a vertex which is behind the clipping plane(z = 0.01), then generate 2 new vertex on the
		//clipping plane
		
		for(int i = 0; i < L; i++){
			if(tempVertex[i].z >= 0.01){
				vertex2D[visibleCount].set(tempVertex[i]);
				vertex2D[visibleCount].setScreenLocation(tempVertex[i]);
				visibleCount++;
			} else{
				int index = (i+L - 1)%L;
				if(tempVertex[index].z >= 0.01){
					approximatePoint(visibleCount, tempVertex[i], tempVertex[index]);
					visibleCount++;
				}
				index = (i+1)%L;
				if(tempVertex[index].z >= 0.01){
					approximatePoint(visibleCount, tempVertex[i], tempVertex[index]);
					visibleCount++;
				}
			}
		}
	}
	
	//find the approximate projection point on the clipping plane
	public static void approximatePoint(int index, Vector behindPoint, Vector frontPoint){
		tempVector1.set(frontPoint.x - behindPoint.x, frontPoint.y - behindPoint.y, frontPoint.z - behindPoint.z);
		tempVector1.scale(frontPoint.z/tempVector1.z);
		vertex2D[index].set(frontPoint.x, frontPoint.y, frontPoint.z);
		vertex2D[index].subtract(tempVector1);
		vertex2D[index].add(0,0,0.01);
		vertex2D[index].updateLocation();
	}
	
	//convert a polygon to scan lines
	public static void scanPolygon(){
		int start = 479;
		int end = 0;
		int temp_ = 0;
		int g = 0;

		for(int i = 0; i < visibleCount; i++){
			Vector v1 = vertex2D[i];
			Vector v2;
			
			if(i == visibleCount -1 ){
				v2 = vertex2D[0];
			}else{
				v2 = vertex2D[i+1];
			}

			boolean downwards = false;

			//ensure v1.y < v2.y;
			if (v1.screenY> v2.screenY) {
				downwards = true;
				Vector temp = v1;
				v1 = v2;
				v2 = temp;
			}
			int dy = v2.screenY - v1.screenY;
			
			// ignore horizontal lines
			if (dy == 0) {
				continue;
			}
			
			
			
			if(poly.withinViewScreen){
				int startY = Math.max(v1.screenY, 0);
				int endY = Math.min(v2.screenY, 479);
				if(startY < start )
					start = startY;
				if(endY > end)
					end = endY;
				
				double dx = v2.screenX - v1.screenX;
				g = (int)(dx / dy *0xff);
				temp_ = v1.screenX<<8;

				
				for (int y=startY; y<=endY; y++) {
					if(downwards)
						xLow[y] = temp_ >> 8;
					else
						xHigh[y] = (temp_ >> 8) + 1;
					temp_+=g;
				}
				
				continue;
			}

			int startY = Math.max(v1.screenY, 0);
			int endY = Math.min(v2.screenY, 479);

			if(startY < start )
				start = startY;

			if(endY > end)
				end = endY;

			double dx = v2.screenX - v1.screenX;
			double gradient = dx / dy;

			int temp_x, x;
			temp_ = (int)(v1.screenX + (startY - v1.screenY) * gradient) <<8;
			// scan-convert this edge (line equation)
			g = (int)(gradient*0xff);

			for (int y=startY; y<=endY; y++) {
				temp_x = temp_>>8;
				temp_+=g;
				// ensure x within view bounds
				x = Math.min(639+1, Math.max(temp_x, 0));
				if(downwards){
					xLow[y] = x;
				}else{
					if(x < 639)
						x++;
					xHigh[y] = x ;
				}
			}
		}
		poly.start = start;
		poly.end = end;
	}
	
	
	//Texture mapper for rendering terrain polygons.
	//It takes the advantage that:
	//if the polygon is perpendicular to the x-z plane (in world coordinate)
	//the z depth will be constant along a scanline(provide it never undergo
	//z-axis rotation). As the result, only 2 perspective corrections are needed for every scanline.
	public static void renderTerrain(){
		int[] screen = Main.screen;
		short[] Texture = poly.myTexture.Texture; 
		int[][] colorTable = GameData.colorTable;
		short[] lightMap = Main.lightMap;
		boolean[] terrainBuffer = Main.terrainBuffer;
		boolean flag = Main.terrainBufferFlag;
		
		A_offset = A.x*2048;
		B_offset = B.x*2048;
		C_offset = C.x*2048;
		
		int start = poly.start;
		int end = poly.end;
		
		//iterate through the scan lines
		for(int i = start; i <= end; i++){
			int offset = xHigh[i] - xLow[i];
			W.set(xLow[i]-320, -i + 240, Vector.Z_length);
			aDotW = A.dot(W);
			bDotW = B.dot(W);
			cDotW = C.dot(W);

			//find the correct texture coordinate for the first and the last pixel 
			//of a scanline, and use the interpolation values for the rest pixels.
			//Also, make use of integer math to speed up calculation
			cDotWInverse = 1/cDotW;
			BigX = (int)(aDotW*cDotWInverse*0xffff);
			BigY = (int)(bDotW*cDotWInverse*0xffff);
			aDotW+=A_offset;
			bDotW+=B_offset;
			cDotW+=C_offset;
			cDotWInverse = 1/cDotW;
			X1 = (int)(aDotW*cDotWInverse*0xffff);
			Y1 = (int)(bDotW*cDotWInverse*0xffff);
			BigDx = X1 - BigX;
			BigDy = Y1 - BigY;
			dx = BigDx>>16;
			dy = BigDy>>16;
			
			int temp = GameData.screenTable[i];
			int index = xLow[i] + temp;
		
			X = BigX >>16;
			Y = BigY >>16;
			
			//standatd texture mapping routine, map pixel to texel
			for(k = offset, d_x = 0, d_y = 0; k >0; k--, d_x+=dx, d_y+=dy, index++){
				if(flag !=  terrainBuffer[index]){
					textureIndex = (((d_x>>11) + X)&widthMask) + ((((d_y>>11) + Y)&heightMask)<<widthBits);
					screen[index] = colorTable[lightMap[index]][Texture[textureIndex]];
					//reset lightmap buffer
					lightMap[index] = 35;
					//change terrainBuffer so this pixel wont be filled by another terrain polygon
					terrainBuffer[index] = flag;
				}
			}
		}
	}
	
	
	//Texture mapper for rendering light maps
	//It is pretty similar to terrain renderer, except it draw
	//the polygon  to the lightmap light  buffer instead of
	//screen buffer. (notice that this light map only apply to floor)
	public static void renderLightMap(){
		short[] lightMap = Main.lightMap;
		short[] Texture = poly.myTexture.Texture; 
		variation = poly.variation;
		
		A_offset = A.x*1024;
		B_offset = B.x*1024;
		C_offset = C.x*1024;
		
		int start = poly.start;
		int end = poly.end;
		
		//iterate through the scan lines
		for(int i = start; i <= end; i++){
			int offset = xHigh[i] - xLow[i];
			W.set(xLow[i]-320, -i + 240, Vector.Z_length);
			aDotW = A.dot(W);
			bDotW = B.dot(W);
			cDotW = C.dot(W);

			//use integer math to speed up calculation
			cDotWInverse = 1/cDotW;
			BigX = (int)(aDotW*cDotWInverse*0xffff);
			BigY = (int)(bDotW*cDotWInverse*0xffff);
			aDotW+=A_offset;
			bDotW+=B_offset;
			cDotW+=C_offset;
			cDotWInverse = 1/cDotW;
			X1 = (int)(aDotW*cDotWInverse*0xffff);
			Y1 = (int)(bDotW*cDotWInverse*0xffff);
			BigDx = X1 - BigX;
			BigDy = Y1 - BigY;
			dx = BigDx>>16;
			dy = BigDy>>16;
			
			int temp = GameData.screenTable[i];
			int index = xLow[i] + temp;
		
			X = BigX >>16;
			Y = BigY >>16;
			
			if(index < 0)
				index = 0;
			
			//standatd texture mapping routine, map pixel to texel
			for(k = offset, d_x = 0, d_y = 0; k >0; k--, d_x+=dx, d_y+=dy, index++){
				textureIndex = (((d_x>>10) + X)&widthMask) + ((((d_y>>10) + Y)&heightMask)<<widthBits);
				I = lightMap[index];
				temp = Texture[textureIndex];
				if((temp == 0 ) || (temp <= 1 && temp >= -1))
					continue;
				temp+=variation;
				if(variation > 0){
					if(temp > 0 )
						temp = 0;
				}
				
				I += temp;
				
				//fill up lightmap buffer with intensity value
				if(I <= 63){
					if(I < 13){
						lightMap[index] = 13;
						continue;
					}
					lightMap[index] = I;
				}else
					lightMap[index] = 63;
			}
		}
	}
	
	
	//Texture mapper for rendering light maps (blue channel only)
	public static void renderLightBlue(){

	}
	
	
	
	
	
	//Texture mapper for rendering polygon that distorts the image behind it.
	//It invloves 2 steps, first draw the distorted polyon on to a stencil 
	//buffer, then copy the content of the stencil buffer to screen buffer.
	//This procedure is used to create water ripples.
	public static void renderWater(){
		int[] screen = Main.screen;
		int[] stencilBuffer = Main.stencilBuffer;
		byte[] Texture = poly.myTexture.waterWave[poly.myTexture.waveIndex]; 
		int temp;
		double scale;
		
		A_offset = A.x*2048;
		B_offset = B.x*2048;
		C_offset = C.x*2048;
		
		int start = poly.start;
		int end = poly.end;
		
		//iterate through the scan lines,
		for(int i = start; i <= end; i++){
			int offset = xHigh[i] - xLow[i];
			W.set(xLow[i]-320, -i + 240, Vector.Z_length);
			scale = Math.min(1400.0/(i*1.1+1), 15);
			
			
			
			aDotW = A.dot(W);
			bDotW = B.dot(W);
			cDotW = C.dot(W);

			//use integer math to speed up calculation
			cDotWInverse = 1/cDotW;
			BigX = (int)(aDotW*cDotWInverse*0xffff);
			BigY = (int)(bDotW*cDotWInverse*0xffff);
			aDotW+=A_offset;
			bDotW+=B_offset;
			cDotW+=C_offset;
			cDotWInverse = 1/cDotW;
			X1 = (int)(aDotW*cDotWInverse*0xffff);
			Y1 = (int)(bDotW*cDotWInverse*0xffff);
			BigDx = X1 - BigX;
			BigDy = Y1 - BigY;
			dx = BigDx>>16;
			dy = BigDy>>16;
			
			temp = GameData.screenTable[i];
			int index = xLow[i] + temp;
		
			X = BigX >>16;
			Y = BigY >>16;
			
			//standatd texture mapping routine, map pixel to texel
			for(k = offset, d_x = 0, d_y = 0; k >0; k--, d_x+=dx, d_y+=dy, index++){
				textureIndex = (((d_x>>11) + X)&widthMask) + ((((d_y>>11) + Y)&heightMask)<<widthBits);
				//find out the distorted pixel position
				temp = Texture[textureIndex];
				temp = index + (int)(temp/scale)*640;
				
				if(temp< 0 || temp >= 307200)
					temp = index;
				//draw the pixel on to a stencil buffer
				stencilBuffer[index] = screen[temp] + 6;
			}
		}
		
		
		//copy the content of the stencil buffer to screen buffer.
		for(int i = start; i <= end; i++){
			int offset = xHigh[i] - xLow[i];
			temp = GameData.screenTable[i];
			int index = xLow[i] + temp;
			for(k = offset; k >0; k--, index++)
				screen[index] = stencilBuffer[index];
		}
	}
	
	//render transparent polygon from a stealth tank
	public static void renderTransparent1(){
		int[] screen = Main.screen;
		int[] stencilBuffer = Main.stencilBuffer;
		

		
		int start = poly.start;
		int end = poly.end;
		
		for(int i = start; i <= end; i++){
			int temp = GameData.screenTable[i];
			int index;
			for(int j = xLow[i]; j < xHigh[i]; j++){
				index = j + temp;
				
				//find out the distorted pixel position
				X =(j+xOffset) % 128;
				Y = i % 128;
				
				temp1 = GameData.distortion1[X+Y*128];
				temp1 = index + (int)(temp1/scale)*640;
				if(temp1< 0 || temp1 >= 307200)
					temp1 = index;
				
				//draw the pixel on to a stencil buffer
				stencilBuffer[index] = screen[temp1];
			}
		}
		
		//copy the content of the stencil buffer to screen buffer.
		for(int i = start; i <= end; i++){
			int offset = xHigh[i] - xLow[i];
			temp = GameData.screenTable[i];
			int index = xLow[i] + temp;
			for(k = offset; k >0; k--, index++)
				screen[index] = stencilBuffer[index];
		}
		
	}
	
	//render transparent polygon of a energy fence
	public static void renderTransparent2(){
		int[] screen = Main.screen;
		int[] stencilBuffer = Main.stencilBuffer;
		

		
		int start = poly.start;
		int end = poly.end;
		double distanceScale = poly.centre.z/2;
		if(distanceScale < 1)
			distanceScale = 1;
		
		
		for(int i = start; i <= end; i++){
			int temp = GameData.screenTable[i];
			int index;
			
			for(int j = xLow[i]; j < xHigh[i]; j++){
				index = j + temp;
				
				//find out the distorted pixel position
				X =(j*2/3 + xOffset + 128);
				Y = i*2/3;
				
				X*=distanceScale;
				Y*=distanceScale;
				X%=128;
				Y%=128;
				
				temp1 = GameData.distortion2[X+Y*128];
				temp1 = index + (int)(temp1/(distanceScale*1.2))*640 + (int)(temp1/(distanceScale*1.2));
				if(temp1< 0 || temp1 >= 307200)
					temp1 = index;
				
				//draw the pixel on to a stencil buffer
				stencilBuffer[index] = screen[temp1];
			}
		}
		
		//copy the content of the stencil buffer to screen buffer.
		for(int i = start; i <= end; i++){
			int offset = xHigh[i] - xLow[i];
			temp = GameData.screenTable[i];
			int index = xLow[i] + temp;
			for(k = offset; k >0; k--, index++){
				temp1 =stencilBuffer[index];
				temp2= -16741234;
				r=(210*(((temp1>>16)&255)-((temp2>>16)&255))>>8)+((temp2>>16)&255);
				g=(210*(((temp1>>8)&255)-((temp2>>8)&255))>>8)+((temp2>>8)&255);
				b=(210*((temp1&255)-(temp2&255))>>8)+(temp2&255);
				screen[index] = (r<<16)|(g<<8)|b;
				
			}
		}
		
	}
	
	
	//Texture mapper for rendering polygons from a 3D model.
	//Unlike terrain polygons, these polygons need to undergo
	//prespective corrections every 16 pixels in a scanline.
	public static void renderModel(){
		int[] screen = Main.screen;
		short[] Texture = poly.myTexture.Texture; 
		diffuse_I = poly.diffuse_I;
		int[]colorTable = GameData.colorTable[diffuse_I];
	
		A_offset = A.x*16;
		B_offset = B.x*16;
		C_offset = C.x*16;
		
		double Aoffset,Boffset,Coffset;
		
		int start = poly.start;
		int end = poly.end;
		
		for(int i = start; i <= end; i++){
			W.set(xLow[i]-320, -i + 240, Vector.Z_length);
			aDotW = A.dot(W);
			bDotW = B.dot(W);
			cDotW = C.dot(W);
			
			//find the texture coordinate for the start pixel of the scanline
			cDotWInverse = 1/cDotW;
			X = (int)(aDotW*cDotWInverse);
			Y = (int)(bDotW*cDotWInverse);
			X1 = X;
			Y1 = Y;
			
			int temp = GameData.screenTable[i];
			int index;
			for(int j = xLow[i]; j < xHigh[i]; j+=16){
				X = X1;
				Y = Y1;
				
				index = j + temp;
				if(xHigh[i] - j > 15){
					//find the correct texture coordinate every 16 pixels.
					//Use the interpolation values for the rest pixels.
					aDotW+=A_offset;
					bDotW+=B_offset;
					cDotW+=C_offset;
					cDotWInverse = 1/cDotW;
					X1 = (int)(aDotW*cDotWInverse);
					Y1 = (int)(bDotW*cDotWInverse);
					dx = X1 - X;
					dy = Y1 - Y;
					
					if(alpha <= 0){
						for( k = 16, d_x = 0, d_y = 0; k >0; k--, d_x+=dx, d_y+=dy, index++){
							textureIndex = (((d_x>>4) + X)&widthMask) + ((((d_y>>4) + Y)&heightMask)<<widthBits);
							screen[index] = colorTable[Texture[textureIndex]];
						}
					}else{
						for( k = 16, d_x = 0, d_y = 0; k >0; k--, d_x+=dx, d_y+=dy, index++){
							textureIndex = (((d_x>>4) + X)&widthMask) + ((((d_y>>4) + Y)&heightMask)<<widthBits);
							temp1 = screen[index];
							temp2= colorTable[Texture[textureIndex]];
							r=(alpha*(((temp1>>16)&255)-((temp2>>16)&255))>>8)+((temp2>>16)&255);
							g=(alpha*(((temp1>>8)&255)-((temp2>>8)&255))>>8)+((temp2>>8)&255);
							b=(alpha*((temp1&255)-(temp2&255))>>8)+(temp2&255);
							
							screen[index] = (r<<16)|(g<<8)|b;
						}
					}
					continue;
				}
				
				int offset = xHigh[i] - j;
				Aoffset = A.x*offset;
				Boffset = B.x*offset;
				Coffset = C.x*offset;
	
				aDotW+=Aoffset;
				bDotW+=Boffset;
				cDotW+=Coffset;
				cDotWInverse = 1/cDotW;
				X1 = (int)(aDotW*cDotWInverse);
				Y1 = (int)(bDotW*cDotWInverse);
				dx = X1 - X;
				dy = Y1 - Y;
				
				if(alpha <= 0){
					for( k = offset, d_x = 0, d_y = 0; k >0; k--, d_x+=dx, d_y+=dy, index++){
						textureIndex = (((d_x/offset) + X)&widthMask) + ((((d_y/offset) + Y)&heightMask)<<widthBits);
						
						screen[index] = colorTable[Texture[textureIndex]];
					}
				}else{
					for( k = offset, d_x = 0, d_y = 0; k >0; k--, d_x+=dx, d_y+=dy, index++){
						textureIndex = (((d_x/offset) + X)&widthMask) + ((((d_y/offset) + Y)&heightMask)<<widthBits);
						temp1 = screen[index];
						temp2= colorTable[Texture[textureIndex]];
						r=(alpha*(((temp1>>16)&255)-((temp2>>16)&255))>>8)+((temp2>>16)&255);
						g=(alpha*(((temp1>>8)&255)-((temp2>>8)&255))>>8)+((temp2>>8)&255);
						b=(alpha*((temp1&255)-(temp2&255))>>8)+(temp2&255);
						
						screen[index] = (r<<16)|(g<<8)|b;
					}
				}
				break;
			}
		}
	}
	
	//render lightMap which could be applied to any polygon
	//it is similar to renderModel excpet it only changes the
	//brightness of the pixels.
	public static void renderLightMap2(){
		int[] screen = Main.screen;
		short[] Texture = poly.myTexture.Texture; 
	
		A_offset = A.x*16;
		B_offset = B.x*16;
		C_offset = C.x*16;
		
		double Aoffset,Boffset,Coffset;
		
		int start = poly.start;
		int end = poly.end;
		
		for(int i = start; i <= end; i++){
			W.set(xLow[i]-320, -i + 240, Vector.Z_length);
			aDotW = A.dot(W);
			bDotW = B.dot(W);
			cDotW = C.dot(W);
			
			//find the texture coordinate for the start pixel of the scanline
			cDotWInverse = 1/cDotW;
			X = (int)(aDotW*cDotWInverse);
			Y = (int)(bDotW*cDotWInverse);
			X1 = X;
			Y1 = Y;
			
			int temp = GameData.screenTable[i];
			int index;
		
			for(int j = xLow[i]; j < xHigh[i]; j+=16){
				X = X1;
				Y = Y1;
				
				index = j + temp;
				if(xHigh[i] - j > 15){
					//find the correct texture coordinate every 16 pixels.
					//Use the interpolation values for the rest pixels.
					aDotW+=A_offset;
					bDotW+=B_offset;
					cDotW+=C_offset;
					cDotWInverse = 1/cDotW;
					X1 = (int)(aDotW*cDotWInverse);
					Y1 = (int)(bDotW*cDotWInverse);
					dx = X1 - X;
					dy = Y1 - Y;
					
					
					for( k = 16, d_x = 0, d_y = 0; k >0; k--, d_x+=dx, d_y+=dy, index++){
						textureIndex = (((d_x/16) + X)&widthMask) + ((((d_y/16) + Y)&heightMask)<<widthBits);
						temp1 = screen[index];
						
						//temp2 (the darkness value) has is ranged from from -20 ~ 0
						temp2= Texture[textureIndex];
						if((temp2 == 0 ) || (temp2 <= 1 && temp2 >= -1))
							continue;
						
						
						
						r=(((temp1>>16)&255)*164)>>8;
						g=(((temp1>>8)&255)*164)>>8;
						b=((temp1&255)*164)>>8;
						
						screen[index]= (r<<16)|(g<<8)|b;
					}
					
					continue;
				}
				
				int offset = xHigh[i] - j;
				Aoffset = A.x*offset;
				Boffset = B.x*offset;
				Coffset = C.x*offset;
	
				aDotW+=Aoffset;
				bDotW+=Boffset;
				cDotW+=Coffset;
				cDotWInverse = 1/cDotW;
				X1 = (int)(aDotW*cDotWInverse);
				Y1 = (int)(bDotW*cDotWInverse);
				dx = X1 - X;
				dy = Y1 - Y;
				
				
				for( k = offset, d_x = 0, d_y = 0; k >0; k--, d_x+=dx, d_y+=dy, index++){
					textureIndex = (((d_x/offset) + X)&widthMask) + ((((d_y/offset) + Y)&heightMask)<<widthBits);
					temp1 = screen[index];
					
					//temp2 (the darkness value) has is ranged from from -20 ~ 0
					temp2= Texture[textureIndex];
					if((temp2 == 0 ) || (temp2 <= 1 && temp2 >= -1))
						continue;
					
					
					r=(((temp1>>16)&255)*164)>>8;
					g=(((temp1>>8)&255)*164)>>8;
					b=((temp1&255)*164)>>8;
					
					screen[index]= (r<<16)|(g<<8)|b;
				}
				
				break;
			}
		
		
		}
	
	}
	
	
	
	
	//rendering polygon that has a soild color
	public static void renderSoildPolygon(){
		int soildColor = GameData.colorTable[poly.diffuse_I][poly.color];
		int[] screen = Main.screen;
		
		int start = poly.start;
		int end = poly.end;
		
		
		
		for(int i = start; i <= end; i++){
			int temp = GameData.screenTable[i];
			int index;
			for(int j = xLow[i]; j < xHigh[i]; j++){
				index = j + temp;
				temp1 = screen[index];
				temp2= soildColor;
				r=(alpha*(((temp1>>16)&255)-((temp2>>16)&255))>>8)+((temp2>>16)&255);
				g=(alpha*(((temp1>>8)&255)-((temp2>>8)&255))>>8)+((temp2>>8)&255);
				b=(alpha*((temp1&255)-(temp2&255))>>8)+(temp2&255);
				
				screen[index] = (r<<16)|(g<<8)|b;
			}
		}
	}
	
	//Texture mapper for rendering 2D explosion spites  
	public static void renderExplosionSprite(int[] sprite, double ratio, int xPos, int yPos, int width, int height){
		int[] screen = Main.screen;
		
		int originalWidth = width;
		
		
		
		//find the size ratio between a sprite pixel and screen pixel
		double ratioInverse = 1/ratio;
		
		width = (int)(ratio*width);
		height = (int)(ratio*height);
	
		
		//only draw the part of the sprite that is inside the screen
		//define boundary
		int xTop = xPos - width/2;
		int yTop = yPos - height/2;
		int xBot = xPos + width/2;
		int yBot = yPos + height/2;
		
		//validate boundary
		boolean withinScreen = true;
		if(xTop < 0 || yTop < 0 || xBot > 639 || yBot > 479)
			withinScreen = false;
		
		//draw sprite 
		int screenIndex = 0;
		int SpriteValue = 0;
		int screenValue = 0;
		int MASK7Bit = 0xFEFEFF;
		int overflow = 0;
		int pixel = 0;
		
		
		
		if(withinScreen){
			for(int i = yTop, y = 0; i < yBot; i++, y++){
				
				int ratioInverseY = (int)(ratioInverse*y);
				for(int j = xTop, x = 0;  j < xBot; j++, x++){
					screenIndex = j + i*640;
					SpriteValue = sprite[(int)(ratioInverse*x) + ratioInverseY*originalWidth];
					
					
					if(SpriteValue != 0)
						screenValue = (screen[screenIndex]&0xFEFEFE)>>1;
					else
						screenValue = screen[screenIndex];
					
					//temp == 1 means draw greenish explosion sprite
					if(temp == 1){
						SpriteValue = (SpriteValue & 0x0000ff00) | ((SpriteValue & 0x00ff0000) >> 17);
						
					}
					
					pixel=(SpriteValue&MASK7Bit)+(screenValue&MASK7Bit);
					overflow=pixel&0x1010100;
					overflow=overflow-(overflow>>8);
					screen[screenIndex] = overflow|pixel;
					
				
				}
			}
		}else{
			
			for(int i = yTop, y = 0; i < yBot; i++, y++){
				
				if(i < 0 || i >=480)
					continue;
				int ratioInverseY = (int)(ratioInverse*y);
				for(int j = xTop, x = 0;  j < xBot; j++, x++){
					screenIndex = j + i*640;
					if(j < 0 || j >= 640)
						continue;
					SpriteValue = sprite[(int)(ratioInverse*x) + ratioInverseY*originalWidth];
					if(SpriteValue != 0)
						screenValue = (screen[screenIndex]&0xFEFEFE)>>1;
					else
						screenValue = screen[screenIndex];
					
					if(temp == 1){
						SpriteValue = (SpriteValue & 0x0000ff00) | ((SpriteValue & 0x00ff0000) >> 17);
						
					}
					
					pixel=(SpriteValue&MASK7Bit)+(screenValue&MASK7Bit);
					overflow=pixel&0x1010100;
					overflow=overflow-(overflow>>8);
					screen[screenIndex] = overflow|pixel;
					
				}
			}
		}
	}
	
	//the texture mapper for rendering smoke sprite
	public static void renderSmokSprite(int[] sprite, double ratio, int xPos, int yPos, int width, int height, int alpha){
		int[] screen = Main.screen;
		int originalWidth = width;
		
		//find the size ratio between a sprite pixel and screen pixel
		double ratioInverse = 1/ratio;
		
		width = (int)(ratio*width);
		height = (int)(ratio*height);
		
		//only draw the part of the sprite that is inside the screen
		//define boundary
		int xTop = xPos - width/2;
		int yTop = yPos - height/2;
		int xBot = xPos + width/2;
		int yBot = yPos + height/2;
		
		//validate boundary
		boolean withinScreen = true;
		if(xTop < 0 || yTop < 0 || xBot > 639 || yBot > 479)
			withinScreen = false;
		
		//draw sprite 
		int screenIndex = 0;
		int SpriteValue = 0;
		
		

		if(withinScreen){
			for(int i = yTop, y = 0; i < yBot; i++, y++){
				int ratioInverseY = (int)(ratioInverse*y);
				for(int j = xTop, x = 0;  j < xBot; j++, x++){
					screenIndex = j + i*640;
					SpriteValue = sprite[(int)(ratioInverse*x) + ratioInverseY*originalWidth];
					
					
					if(SpriteValue == 0)
						continue;
					
					
					temp1 = screen[screenIndex];
					temp2= SpriteValue;
					r=(alpha*(((temp1>>16)&255)-((temp2>>16)&255))>>8)+((temp2>>16)&255);
					g=(alpha*(((temp1>>8)&255)-((temp2>>8)&255))>>8)+((temp2>>8)&255);
					b=(alpha*((temp1&255)-(temp2&255))>>8)+(temp2&255);
					screen[screenIndex] = (r<<16)|(g<<8)|b;
					
					
				
				}
			}
		}else{
			for(int i = yTop, y = 0; i < yBot; i++, y++){
				if(i < 0 || i >=480)
					continue;
				int ratioInverseY = (int)(ratioInverse*y);
				for(int j = xTop, x = 0;  j < xBot; j++, x++){
					screenIndex = j + i*640;
					if(j < 0 || j >= 640)
						continue;
					SpriteValue = sprite[(int)(ratioInverse*x) + ratioInverseY*originalWidth];
					
					if(SpriteValue == 0)
						continue;
					
					
					temp1 = screen[screenIndex];
					temp2= SpriteValue;
					r=(alpha*(((temp1>>16)&255)-((temp2>>16)&255))>>8)+((temp2>>16)&255);
					g=(alpha*(((temp1>>8)&255)-((temp2>>8)&255))>>8)+((temp2>>8)&255);
					b=(alpha*((temp1&255)-(temp2&255))>>8)+(temp2&255);
					screen[screenIndex] = (r<<16)|(g<<8)|b;
					
				}
			}
		}
	}
	
	//render text 
	public static void renderText(boolean[] pixels, int xPos, int yPos, int size, int color){
		int[] screen = Main.screen;
		for(int i = 0; i < 1024; i ++){
			int x = i%32;
			int y = i/32;
			int start = xPos + yPos*640;
			if(pixels[i]){
				if(size == 2){
					screen[start + x*2 + y*2*640] = color;
					screen[start + x*2 + 1 + y*2*640] = color;
					screen[start + x*2 + (y*2 + 1)*640 ] = color;
					screen[start + x*2 + 1 + (y*2+1)*640] = color;
				}
				
				if(size == 1){
					screen[start + x + y*640] = color;
					screen[start + x + 1 + y*640] = color;
				
				}
			}
			
			
			
		}
	}
}
