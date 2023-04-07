package core;
//This class is similar to the rectangle class in java awt, except it handles floating points.
//Also unlike the rectangle in awt, the positive y axis points up

public class Rectangle2D {
	//the top left coordinates
	public double xPos; 
	public double yPos;
	
	public double length;
	public double height;
	
	public double radius;
	
	public Rectangle2D(double xPos, double yPos, double length, double height){
		this.xPos = xPos;
		this.yPos = yPos;
		this.length = length;
		this.height = height;
		radius = length/2;
	}
	
	//test if 2 rectangeles intersect each other
	public static boolean testIntersection(Rectangle2D rect1, Rectangle2D rect2){
		
		double bottom1 = rect1.yPos - rect1.height;
		double bottom2 = rect2.yPos - rect2.height;
		double top1 = rect1.yPos;
		double top2 = rect2.yPos;
		if(bottom1 > top2)		return false;
		if(top1 < bottom2)		return false;
		
		
		double right1 = rect1.xPos + rect1.length;
		double right2 = rect2.xPos + rect2.length;
		double left1 = rect1.xPos;
		double left2 = rect2.xPos;
		
		
		if(right1 < left2)		return false;
		if(left1 > right2)		return false;

		return true;

	}
	
	//change the origin of the rectangle
	public void update(Vector displacement){
		xPos+=displacement.x;
		yPos+=displacement.z;
	}
	
	public String toString(){
		return 	xPos + " " + yPos + " " + length + " " + height;
	}
}
