package core;
import java.awt.*;

public class Polygon3D {
	//The vertex of the polygon with the respect of the world/camera coordinate
	public Vector[] vertex3D, tempVertex;
	
	//The vertex of the polygon after clipping
	public Vector[] vertex2D;
	
	//the normal of the polygon with the respect of the world/camera coordinate
	public Vector realNormal, normal;
	
	//the centre of the polygon with the respect of the world/camera coordinate
	public Vector realCentre, centre;
	
	//The number of vertex
	public int L;
	
	// A rough 2D boundary of the polygon on the screen
	public Rectangle bound;
	public int xMin, yMin, xMax, yMax;
	
	//whether the polygon is completely bounded by the screen
	public boolean withinViewScreen;
	
	//These 3 vectors map the 3 corners of the texture to the world coordinate
	public Vector origin, rightEnd, bottomEnd;
	
	//The texture that bonds to this polygon
	public Texture myTexture;
	
	//Information about the texture
	public int textureWidth, textureHeight, heightMask, widthMask, widthBits, heightBits, bigHeight;
	
	//The size of one texel
	public double textureScaleX, textureScaleY;
	
	//the index of top and bottom scanline of the polygon
	public int start = 479, end = 0;
	
	//some variables involved in texture mapping
	//sorry if the names dont make any sense
	public double A_offset, B_offset, C_offset;
	
	//the alpha value of the polygon
	public int alpha = 0;
	
	//light intensity adjustment for lightman polygons
	public short variation;
	
	//A pool of vectors which will be used for vector arithmetic
	public static Vector
		tempVector1 = new Vector(0,0,0),
		tempVector2 = new Vector(0,0,0),
		tempVector3 = new Vector(0,0,0),
		tempVector4 = new Vector(0,0,0);
	
	//This varaible indicates whether the polygon is parallel to the X-Z plane in world coordinate.
	public boolean faceVerticalPolygon;
	
	//whether the polygon is visible
	public boolean visible;
	
	//type of the polygon
	//0 = polygons that don't have a texture 
	//1 = terrain, 2 = lightMap, 3 = lightMap (blue channel only) 
	//5 = water, 6 = polygons belong to a model object
	//7 = soild polygon
	public int type; 
	
	//the diffuse/ambient intensity of this polygon
	public int diffuse_I;
	public int Ambient_I = 28;  //the default ambient intensity is 28
	
	//this varible describe how well the polygon reflect light
	public double  diffuse_coefficient = 1;
	
	public boolean constantI;
	
	//the color of polygon if it is defined as soild 
	public short color;
	
	//Constuctor of the polygon class, it will only accept convex polygons
	public Polygon3D(Vector[] vertex3D, Vector origin, Vector rightEnd, Vector bottomEnd, Texture myTexture, double scaleX, double scaleY, int type){
		this.type = type;
		this.vertex3D = vertex3D;
		this.myTexture = myTexture;
		L = vertex3D.length;
		bound = new Rectangle(0,0,0,0);
		diffuse_I = 31;
		
		//set the tempVertex to the vertex3D
		tempVertex = new Vector[L];
		for(int i = 0; i < L; i++){
			tempVertex[i] = new Vector(0,0,0);
			tempVertex[i].set(vertex3D[i]);
		}
		
		//find normal vector of the polygon (in world coordinate)
		tempVector1.set(tempVertex[1]);
		tempVector1.subtract(tempVertex[0]);
		tempVector2.set(tempVertex[2]);
		tempVector2.subtract(tempVertex[1]);
		realNormal = tempVector1.cross(tempVector2);
		realNormal.unit();
		normal = new Vector(0,0,0);
		normal.set(realNormal);
		
		//check whether this polygon is parallel to the X-Z plane
		if(Math.abs(normal.y) > 0.99)
			faceVerticalPolygon = true;
		else
			faceVerticalPolygon = false;
		
		//find centre of the polygon (in world coordinate)
		realCentre = new Vector(0,0,0);
		for(int i = 0; i < tempVertex.length; i++)
			realCentre.add(tempVertex[i]);
		realCentre.scale(1.0/tempVertex.length);
		centre = new Vector(0,0,0);
		centre.set(realCentre);
		
		if(origin != null){
			this.origin = origin.myClone();
			this.rightEnd = rightEnd.myClone();
			this.bottomEnd = bottomEnd.myClone();
		}
		
		//get the texture information if the polygon is bonded with a texture
		if(myTexture != null){
			
			textureWidth = myTexture.width;
			textureHeight = myTexture.height;
			heightMask = myTexture.heightMask;
			widthMask = myTexture.widthMask;
			widthBits = myTexture.widthBits;
			heightBits = myTexture.heightBits;
			bigHeight = textureHeight << 21;
		
			//find how big is one texel of the texture in the world coordinate
			tempVector1.set(origin);
			tempVector1.subtract(rightEnd);
			double l = tempVector1.getLength();
			textureScaleX = l/myTexture.width;

			tempVector1.set(origin);
			tempVector1.subtract(bottomEnd);
			l = tempVector1.getLength();
			textureScaleY = l/myTexture.height;
			
			textureScaleX = textureScaleX/scaleX;
			textureScaleY = textureScaleY/scaleY;
		}
		
		//init vertex2D, notice that the size of vertex2D is bigger than vertex3D, because after clipping
		//it is possilbe to generate more vertex for the polygon.
		vertex2D = new Vector[L+1];
		for(int i = 0; i < vertex2D.length; i++)
			vertex2D[i] = new Vector(0,0,0);
		
		//find the initial diffuse intensity of this polygon
		findDiffuse();
	}
	
	//update this polygon based on camera movement in each frame
	public void update(){		
		//back-face culling
		tempVector1.set(Camera.position);
		tempVector1.subtract(vertex3D[0]);
		if(tempVector1.dot(realNormal) <= 0){
			visible = false;
			return;
		}
		
		
		//update vertex according to camera orientation
		double x = 0,y = 0, z = 0, 
		camX = Camera.position.x, camY = Camera.position.y, camZ = Camera.position.z,
		sinXZ = GameData.sin[Camera.XZ_angle],
		cosXZ = GameData.cos[Camera.XZ_angle],
		sinYZ = GameData.sin[Camera.YZ_angle],
		cosYZ = GameData.cos[Camera.YZ_angle];
		
		for(int i = 0; i < L; i++){
			//shifting
			x = vertex3D[i].x - camX;
		 	y = vertex3D[i].y - camY;
			z = vertex3D[i].z - camZ;
			
			//rotating
			tempVertex[i].x = cosXZ*x - sinXZ*z;
			tempVertex[i].z = sinXZ*x + cosXZ*z;
			
			z = tempVertex[i].z;
			
			tempVertex[i].y = cosYZ*y - sinYZ*z;
			tempVertex[i].z = sinYZ*y + cosYZ*z;
			
			//update 3d position on screen
			tempVertex[i].updateLocation();
		}
		
		//Find A rough 2D rectangular boundary of the polygon.
		xMax = tempVertex[0].screenX;
		xMin = xMax;
		yMax = tempVertex[0].screenY;
		yMin = yMax;
		for(int i = 1; i < tempVertex.length; i++){
			xMax = Math.max(xMax, tempVertex[i].screenX);
			xMin = Math.min(xMin, tempVertex[i].screenX);
			yMax = Math.max(yMax, tempVertex[i].screenY);
			yMin = Math.min(yMin, tempVertex[i].screenY);
		}
		bound.setLocation(xMin,yMin);
		bound.setSize( xMax-xMin + 1, yMax-yMin);
			
		//Test whether the rectangle intersects the screen.
		visible = Camera.screen.intersects(bound);
		
	
		if(visible){
			//find normal vector (in camera coordinate)
			//the vector is calculated from the cross product of 2 neighbor edges.
			//since only the direction is important, it will not be normalized
			tempVector1.set(tempVertex[1]);
			tempVector1.subtract(tempVertex[0]);
			tempVector2.set(tempVertex[2]);
			tempVector2.subtract(tempVertex[1]);
			normal = tempVector1.cross(tempVector2);
			
			//find centre (in camera coordinate)
			//the vector is calculated by averaging all the vertex of the polygon
			centre.reset();
			for(int i = 0; i < L; i++)
				centre.add(tempVertex[i]);
			centre.scale(1.0/L);
			
			//test whether the polygon is completely inside the screen area
			withinViewScreen = Camera.screen.contains(xMin, yMin) && Camera.screen.contains(xMax, yMax);
		}
	}
	
	
	//find diffuse intensity of this polygon
	public void findDiffuse(){
		//calculate the diffuse intensity from sun light
		double I = realNormal.dot(0.3773502691896258, 0.7773502691896258, 0.7773502691896258);
		diffuse_I = Ambient_I + (int)(diffuse_coefficient*I*20);
		
		if(constantI)
			diffuse_I = 45;
	}
	
	//find the normal vector of this polygon in world coordinate
	public void findRealNormal(){
		tempVector1.set(vertex3D[1]);
		tempVector1.subtract(vertex3D[0]);
		tempVector2.set(vertex3D[2]);
		tempVector2.subtract(vertex3D[1]);
		realNormal = tempVector1.cross(tempVector2);
		realNormal.unit();
	}
	
	public void draw(){
		//send this polygon to rasterizer
		if(visible){
			Main.polyCount++;
			Rasterizer.rasterize(this);
		}
	}
}
