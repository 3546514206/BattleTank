package core;
public final class Vector {
	//x, y, z component of the vector
	public double x, y, z;

	//2d coordinate on screen
	public int screenX, screenY;

	public static final int Z_length = 475;

	public static double old_X, old_Y, old_Z, zInverse, lengthInverse;

	public Vector(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;

		//calculate its 2D location on the screen
		updateLocation();
	}

	public void add(Vector v){
		x+=v.x;
		y+=v.y;
		z+=v.z;
	}

	public void add(double a, double b, double c){
		x+=a;
		y+=b;
		z+=c;
	}

	public void add(Vector v, double scaler){
		x+=v.x*scaler;
		y+=v.y*scaler;
		z+=v.z*scaler;
	}

	public void subtract(Vector v, double scaler){
		x-=v.x*scaler;
		y-=v.y*scaler;
		z-=v.z*scaler;
	}

	public void subtract(Vector v){
		x-=v.x;
		y-=v.y;
		z-=v.z;
	}

	//amplify each component of the vector by a number
	public void scale(double a){
		x*=a;
		y*=a;
		z*=a;
	}

	//normalize the vector
	public void unit(){
		lengthInverse = 1/getLength();
		x = x*lengthInverse;
		y = y*lengthInverse;
		z = z*lengthInverse;
	}

	//find the magnitude pf the vector
	public double getLength(){
		return Math.sqrt(x*x + y*y + z*z);
	}

	//retrun the dot product of this vector with another vector
	public double dot(Vector v){
		return x*v.x + y*v.y + z*v.z;
	}

	public double dot(double a, double b, double c){
		return x*a + y*b + z*c;
	}

	//return the cross product of this vector with another vector
	public final Vector cross(Vector v){
		return new Vector(y*v.z - z*v.y, z*v.x - x*v.z, x*v.y - y*v.x);
	}

	//rotate the vector along Y axis
	public void  rotate_XZ(int angle){
		double sin = GameData.sin[angle];
		double cos = GameData.cos[angle];
		old_X = x;
		old_Z = z;
		x = cos*old_X - sin*old_Z;
		z = sin*old_X + cos*old_Z;
	}

	//rotate the vector along X axis
	public void rotate_YZ(int angle){
		double sin = GameData.sin[angle];
		double cos = GameData.cos[angle];
		old_Y = y;
		old_Z = z;
		y = cos*old_Y - sin*old_Z;
		z = sin*old_Y + cos*old_Z;
	}
	
	//rotate the vector along Z axis
	public void rotate_XY(int angle){
		double sin = GameData.sin[angle];
		double cos = GameData.cos[angle];
		old_X = x;
		old_Y = y;
		x = cos*old_X - sin*old_Y;
		y = sin*old_X + cos*old_Y;
	}

	//set all the component equal to the corresponding component of a given vector
	public void set(Vector v){
		x = v.x;
		y = v.y;
		z = v.z;
	}

	public void set(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	//set all the component to 0
	public void reset(){
		x = 0;
		y = 0;
		z = 0;
	
		
	}

	public void updateLocation(){
		//find the 2D screen location of this vector
		if(z <= 0.01){
			screenX = (int)(x*Z_length*100) + 320; screenY = (int)(-y*Z_length*100) + 240;
		}else{
			zInverse = Z_length/z;
			screenX = (int)(x*zInverse) + 320; screenY = (int)(-y*zInverse) + 240;
		}
	}
	
	//set the 2D location of this vector to the 2D location of a given vector
	public void setScreenLocation(Vector v){
		screenX = v.screenX;
		screenY = v.screenY;
	}

	public Vector myClone(){
		return new Vector(x,y,z);
	}

	public String toString(){
		return "(" + x + ", " + y + ", " + z + ")";
	}

}