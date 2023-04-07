package core;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class inputHandler {
	//these booleans indicate whether a particular key is pressed
	public static boolean UP, DOWN, RIGHT, LEFT, A, S, D, W, space, weapon1Selected, weapon2Selected, weapon3Selected, weapon4Selected, mousePressed, cursorInApplet, changeWeapon, L, I, M;
	
	//a flag that indicate whether the player is pressing a key
	public static boolean playerInAction;
	
	//mouse position
	public static int xPos = 320, yPos;
	
	public static void handleInput(){
		//don't do anything when the player pauses the game
		if(main.gamePaused || main.gameNotStart || main.gameOver || main.win)
			return;
		
		playerInAction = false;
		
		if(UP || DOWN || RIGHT || LEFT || A || S ||  D || W || space)
			playerInAction = true;
		
		if(RIGHT){
			playerTank.turnRight = true;
			
			camera.XZ_angle+=4;
			
			if(camera.XZ_angle >359)
				camera.XZ_angle= camera.XZ_angle - 360;
		}
		if(LEFT){
			playerTank.turnLeft = true;
			camera.XZ_angle-=4;
			if(camera.XZ_angle <0 )
				camera.XZ_angle= camera.XZ_angle + 360;
		}
		
		if(W){
			playerTank.forward = true;
		}

		if(S){
			playerTank.backward = true;
		}
		
		if(A){
			playerTank.moveLeft = true;
		}
		
		if(D){
			playerTank.moveRight = true;

		}
		
		//add ammo and health when cheat entered 
		if(I && L && M){
			main.PT.HP = 1499;
			main.PT.shells = 999;
			main.PT.rockets = 999;
			main.PT.slugs = 999;
			main.PT.plasma = 999;
		}
		
		if(space || mousePressed){
			if(gameHUD.loadingScreenPosition != 1234567 && gameHUD.loadingScreenPosition > 100)
				playerTank.firing = true;
		}
		
		if(weapon1Selected)
			main.PT.changeWeapon(1);
		
		if(weapon2Selected)
			main.PT.changeWeapon(2);
		
		if(weapon3Selected)
			main.PT.changeWeapon(3);
		
		if(weapon4Selected)
			main.PT.changeWeapon(4);
		
		if(changeWeapon){
			main.PT.changeWeapon(-1);
		}
		changeWeapon = false;
		
		//handle mouse event
		if((xPos < 140 || (xPos < 320 && yPos > 370)) && !LEFT && cursorInApplet){
			playerTank.turnLeft = true;
			camera.XZ_angle-=4;
			if(camera.XZ_angle <0 )
				camera.XZ_angle= camera.XZ_angle + 360;
		}
		if((xPos > 490 || (xPos > 320 && yPos > 370)) && !RIGHT && cursorInApplet){
			playerTank.turnRight = true;
			camera.XZ_angle+=4;
			if(camera.XZ_angle >359)
				camera.XZ_angle= camera.XZ_angle - 360;
		}
			
		if(xPos <= 490 && xPos >= 140 &&  yPos < 410 &&!RIGHT && !LEFT && cursorInApplet){
			int turretAngle = playerTank.turretAngle %360;
			int cameraAngle = (360 - camera.XZ_angle)%360;
			
			int yPos_ = 480-yPos - 59;
			int xPos_ = 0;
			
			double angle = (turretAngle - cameraAngle + 360)%360;
			
			
			
			if(angle > 180 && angle != 0){
				angle = (double)(360 - angle)/180*Math.PI;
				xPos_ = (int)(Math.tan(angle) * yPos_ + 320);
			}else{
				angle = angle/180*Math.PI;
				xPos_ = 320 - (int)(Math.tan(angle) * yPos_);
			}
			
			int difference = xPos - xPos_;
			
			if(difference > 0 && Math.abs(difference) > (10 + (480 - yPos)/100))
				playerTank.turnRight = true;
			if(difference < 0 && Math.abs(difference) > (10 + (480 - yPos)/100))
				playerTank.turnLeft = true;
	
		}
		
		
	}
	
	public  static void keyPressed(KeyEvent e){
		if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
			A = true;
		if(e.getKeyChar() == 's' || e.getKeyChar() == 'S')
			S = true;
		if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
			D = true;
		if(e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
			W = true;
			
		
		if(e.getKeyChar() == KeyEvent.VK_SPACE)
			space = true;
		if(e.getKeyCode() == KeyEvent.VK_UP){
			UP = true;
			gameHUD.upPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			DOWN = true;
			gameHUD.downPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			LEFT = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			RIGHT = true;
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE )
			gameHUD.escapePressed = true;
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			gameHUD.enterPressed = true;
		
		if(e.getKeyCode() == '1')
			weapon1Selected = true;
		if(e.getKeyCode() == '2')
			weapon2Selected = true;
		if(e.getKeyCode() == '3')
			weapon3Selected = true;
		if(e.getKeyCode() == '4')
			weapon4Selected = true;
		
		if(e.getKeyChar() == 'i' || e.getKeyChar() == 'I')
			I = true;
		
		if(e.getKeyChar() == 'l' || e.getKeyChar() == 'L')
			L = true;
		
		if(e.getKeyChar() == 'm' || e.getKeyChar() == 'M')
			M = true;
	}
	
	public  static void keyReleased(KeyEvent e){
		if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
			A = false;
		if(e.getKeyChar() == 's' || e.getKeyChar() == 'S')
			S = false;
		if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
			D = false;
		if(e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
			W = false;
		if(e.getKeyChar() == KeyEvent.VK_SPACE)
			space = false;
		if(e.getKeyCode() == KeyEvent.VK_UP)
			UP = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			DOWN = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			LEFT = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			RIGHT = false;
		if(e.getKeyCode() == '1')
			weapon1Selected = false;
		if(e.getKeyCode() == '2')
			weapon2Selected = false;
		if(e.getKeyCode() == '3')
			weapon3Selected = false;
		if(e.getKeyCode() == '4')
			weapon4Selected = false;
		
		if(e.getKeyChar() == 'i' || e.getKeyChar() == 'I')
			I = false;
		
		if(e.getKeyChar() == 'l' || e.getKeyChar() == 'L')
			L = false;
		
		if(e.getKeyChar() == 'm' || e.getKeyChar() == 'M')
			M = false;
	}
	
	 public static void keyTyped(KeyEvent e){
		 if(e.getKeyChar() == 'q' || e.getKeyChar() == 'Q')
			changeWeapon = true;
	 }
	
	public static void mouseMoved(MouseEvent e){
		xPos = e.getX();
		yPos = e.getY();
		
	
	}
	
	public static void mouseDragged(MouseEvent e){
		xPos = e.getX();
		yPos = e.getY();
	
	}
	

	public static void mousePressed(MouseEvent e){
		mousePressed = true;
		
		gameHUD.mousePressed = true;
		gameHUD.mouseXpos = e.getX();
		gameHUD.mouseYpos = e.getY();
	}
	
	public static void mouseReleased(MouseEvent e){
		mousePressed = false;
	
	}
	 public static void mouseEntered(MouseEvent e){
		 cursorInApplet = true;
	 }
	 public static void mouseExited(MouseEvent e){
		if(playerInAction == false)
			cursorInApplet = false;
	 }
	

}
