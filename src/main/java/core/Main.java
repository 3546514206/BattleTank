package core;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.awt.image.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class main extends JFrame implements KeyListener, ActionListener, MouseMotionListener, MouseListener{
	
	private static final long serialVersionUID = 1L;
	public Ticker t;
	public int sleepTime;
	public static int[] screen;
	public static int[] stencilBuffer;
	public static int[] stencilBuffer2;
	public static short[] lightMap;
	public BufferedImage doubleBuffer;
	public camera Camera;
	public inputHandler myInputHandler;
	public static texture[] textures;
	public static boolean[] terrainBuffer;
	public static boolean terrainBufferFlag;
	public static int polyCount;
	public static JPanel panel;
	
	public static terrain Terrain;
	
	public static int timer;
	public static long lastTime;
	public static long tm;
	
	public static playerTank PT;
	
	//flag which indicate whether the user has terminated the current applet
	public static boolean appletDestoried;
	
	//game status
	public static boolean gameNotStart, gamePaused, gameOver, win;
	
	public static int screen_w = 640;
	public static int screen_h = 480;
	public static int half_screen_w = screen_w/2;
	public static int half_screen_h = screen_h/2;
	public static int screen_pixel_count = screen_w * screen_h;
	
	public static void main(String[] args){
		new main();
	}
	
	public main(){
		
		setTitle("Battle Tank 2");
		panel= (JPanel) this.getContentPane();
		panel.setPreferredSize(new Dimension(screen_w, screen_h));
		panel.setMinimumSize(new Dimension(screen_w,screen_h));
		panel.setLayout(null);     
		
		setResizable(false); 
		pack();
		setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gameNotStart = true;
		appletDestoried = false;
		screen = null;
		stencilBuffer = null;
		lightMap = null;
		textures = null;
		
		Camera = null;
		Terrain = null;
		gameData.destory();
		terrainBuffer = null;
		System.gc();
		
		//Make an array of int which holds screen pixels data
		doubleBuffer =  new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		DataBuffer dest = doubleBuffer.getRaster().getDataBuffer();
		screen = ((DataBufferInt)dest).getData();
		
		//create buffers which will temporally hold a proportion of the screen buffer
		stencilBuffer = new int[640*480];
		stencilBuffer2 = new int[640*480];
		
		//make an array of short that hold all the lighting information on the terrain
		lightMap = new short[640*480];
		
		//The terrain buffer indicates whether a particular pixel is already filled by a terrain
		//polygon during a game frame
		terrainBuffer = new boolean[640*480];
	
		//Load textures
		String imageFolder = "../Image/";
		
	
		textures = new texture[63];
		try {
			textures[0] = new texture(ImageIO.read(getClass().getResource(imageFolder + "1.jpg")), 9, 9, "normal");
			textures[1] = new texture(ImageIO.read(getClass().getResource(imageFolder + "3.jpg")), 8, 8, "normal");
			textures[2] = new texture(ImageIO.read(getClass().getResource(imageFolder + "1.jpg")), 9, 9, "beachSlope");
			textures[3] = new texture(ImageIO.read(getClass().getResource(imageFolder + "1.jpg")), 9, 9, "oceanFloor");
			textures[4] = new texture(ImageIO.read(getClass().getResource(imageFolder + "5.jpg")), 7, 7, "water");
			textures[5] = new texture(ImageIO.read(getClass().getResource(imageFolder + "4.jpg")), 7, 7, "normal");
			textures[6] = new texture(ImageIO.read(getClass().getResource(imageFolder + "6.jpg")), 8, 8, "normal");
			textures[7] = new texture(ImageIO.read(getClass().getResource(imageFolder + "7.jpg")), 8, 8, "normal");
			textures[8] = new texture(ImageIO.read(getClass().getResource(imageFolder + "8.jpg")), 7, 7, "shadow");
			textures[9] = new texture(ImageIO.read(getClass().getResource(imageFolder + "9.jpg")), 5, 7, "normal");
			textures[10] = new texture(ImageIO.read(getClass().getResource(imageFolder + "10.jpg")), 7, 7, "shadow");
			textures[11] = new texture(ImageIO.read(getClass().getResource(imageFolder + "11.jpg")), 6, 6, "normal");
			textures[12] = new texture(ImageIO.read(getClass().getResource(imageFolder + "12.jpg")), 8, 6, "normal");
			textures[13] = new texture(ImageIO.read(getClass().getResource(imageFolder + "13.jpg")), 6, 6, "normal");
			textures[14] = new texture(ImageIO.read(getClass().getResource(imageFolder + "14.jpg")), 7, 7, "shadow");
			textures[15] = new texture(ImageIO.read(getClass().getResource(imageFolder + "15.jpg")), 7, 7, "shadow");
			textures[16] = new texture(ImageIO.read(getClass().getResource(imageFolder + "16.jpg")), 1, 1, "normal");
			textures[17] = new texture(ImageIO.read(getClass().getResource(imageFolder + "17.jpg")), 8, 8, "explosion");
			textures[18] = new texture(ImageIO.read(getClass().getResource(imageFolder + "18.jpg")), 8, 8, "explosion");
			textures[19] = new texture(ImageIO.read(getClass().getResource(imageFolder + "19.jpg")), 8, 8, "explosion");
			textures[20] = new texture(ImageIO.read(getClass().getResource(imageFolder + "20.jpg")), 8, 8, "explosion");
			textures[21] = new texture(ImageIO.read(getClass().getResource(imageFolder + "2.jpg")), 8, 8, "light");
			textures[22] = new texture(ImageIO.read(getClass().getResource(imageFolder + "21.jpg")), 6, 6, "normal");
			textures[23] = new texture(ImageIO.read(getClass().getResource(imageFolder + "22.jpg")), 6, 6, "normal");
			textures[24] = new texture(ImageIO.read(getClass().getResource(imageFolder + "23.jpg")), 6, 6, "normal");
			textures[25] = new texture(ImageIO.read(getClass().getResource(imageFolder + "24.jpg")), 1, 1, "normal");
			textures[26] = new texture(ImageIO.read(getClass().getResource(imageFolder + "25.jpg")), 1, 1, "normal");
			textures[27] = new texture(ImageIO.read(getClass().getResource(imageFolder + "26.jpg")), 6, 6, "normal");
			textures[28] = new texture(ImageIO.read(getClass().getResource(imageFolder + "27.jpg")), 6, 6, "normal");
			textures[29] = new texture(ImageIO.read(getClass().getResource(imageFolder + "28.jpg")), 6, 6, "normal");
			textures[30] = new texture(ImageIO.read(getClass().getResource(imageFolder + "29.jpg")), 6, 6, "normal");
			textures[31] = new texture(ImageIO.read(getClass().getResource(imageFolder + "30.jpg")), 6, 6, "normal");
			textures[32] = new texture(ImageIO.read(getClass().getResource(imageFolder + "31.jpg")), 1, 1, "normal");
			textures[33] = new texture(ImageIO.read(getClass().getResource(imageFolder + "32.jpg")), 6, 6, "normal");
			textures[34] = new texture(ImageIO.read(getClass().getResource(imageFolder + "33.jpg")), 1, 1, "normal");
			textures[35] = new texture(ImageIO.read(getClass().getResource(imageFolder + "34.jpg")), 6, 6, "normal");
			textures[36] = new texture(ImageIO.read(getClass().getResource(imageFolder + "35.jpg")), 7, 7, "shadow");
			textures[37] = new texture(ImageIO.read(getClass().getResource(imageFolder + "36.jpg")), 7, 7, "shadow");
			textures[38] = new texture(ImageIO.read(getClass().getResource(imageFolder + "37.jpg")), 6, 6, "normal");
			textures[39] = new texture(ImageIO.read(getClass().getResource(imageFolder + "38.jpg")), 1, 1, "normal");
			textures[40] = new texture(ImageIO.read(getClass().getResource(imageFolder + "40.jpg")), 8, 8, "normal");
			textures[41] = new texture(ImageIO.read(getClass().getResource(imageFolder + "41.jpg")), 7, 7, "normal");
			textures[42] = new texture(ImageIO.read(getClass().getResource(imageFolder + "42.jpg")), 7, 7, "shadow");
			textures[43] = new texture(ImageIO.read(getClass().getResource(imageFolder + "43.jpg")), 7, 7, "shadow");
			textures[44] = new texture(ImageIO.read(getClass().getResource(imageFolder + "44.jpg")), 7, 7, "shadow");
			textures[45] = new texture(ImageIO.read(getClass().getResource(imageFolder + "45.jpg")), 5, 5, "shadow");
			textures[46] = new texture(ImageIO.read(getClass().getResource(imageFolder + "46.jpg")), 5, 5, "shadow");
			textures[47] = new texture(ImageIO.read(getClass().getResource(imageFolder + "47.jpg")), 7, 7, "shadow");
			textures[48] = new texture(ImageIO.read(getClass().getResource(imageFolder + "48.jpg")), 6, 6, "normal");
			textures[49] = new texture(ImageIO.read(getClass().getResource(imageFolder + "49.jpg")), 1, 1, "normal");
			textures[50] = new texture(ImageIO.read(getClass().getResource(imageFolder + "50.jpg")), 7, 7, "shadow");
			textures[51] = new texture(ImageIO.read(getClass().getResource(imageFolder + "51.jpg")), 6, 6, "normal");
			textures[52] = new texture(ImageIO.read(getClass().getResource(imageFolder + "52.jpg")), 1, 1, "normal");
			textures[53] = new texture(ImageIO.read(getClass().getResource(imageFolder + "53.jpg")), 7, 7, "normal");
			textures[54] = new texture(ImageIO.read(getClass().getResource(imageFolder + "54.jpg")), 5, 5, "normal");
			textures[55] = new texture(ImageIO.read(getClass().getResource(imageFolder + "55.jpg")), 5, 5, "normal");
			textures[56] = new texture(ImageIO.read(getClass().getResource(imageFolder + "56.jpg")), 7, 7, "shadow");
			textures[57] = new texture(ImageIO.read(getClass().getResource(imageFolder + "57.jpg")), 6, 6, "smoke");
			textures[58] = new texture(ImageIO.read(getClass().getResource(imageFolder + "58.jpg")), 6, 6, "normal");
			textures[59] = new texture(ImageIO.read(getClass().getResource(imageFolder + "59.jpg")), 6, 6, "normal");
			textures[60] = new texture(ImageIO.read(getClass().getResource(imageFolder + "60.jpg")), 6, 6, "normal");
			textures[61] = new texture(ImageIO.read(getClass().getResource(imageFolder + "61.jpg")), 7, 7, "shadow");
			textures[62] = new texture(ImageIO.read(getClass().getResource(imageFolder + "62.jpg")), 8, 8, "normal");

		}catch(Exception e) {e.printStackTrace();}


		//Create look up tables
		gameData.makeData();
		
		//init text factory
		textFactory.init();
		
		//init game hud
		gameHUD.init();
		
		//init camera
		Camera = new camera();
	
		//init terrain
		obstacleMap.init();
		powerUps.init();
		Terrain = new terrain();
		
		//init projectiles, powerups
		projectiles.init();
		
		//init player tank
		PT = new playerTank(1000,-0.975,2.5);	
		
		//init enemies
		enemies.init();
		
		


		//Add ticker
		timer = 0;
		sleepTime = 35;
		tm = System.currentTimeMillis();
		t = new Ticker(sleepTime);
		t.addActionListener(this);
		
		t.start();
		
		System.out.println("Started!");
		System.gc();
	}
	
	//free resources when the browser decides to end the applet
	public void destroy(){
		t.stop();
		System.gc();
		
		appletDestoried = true;
	}
	
	//This method is called every time the ticker ticks (game loop)
	public final void actionPerformed(ActionEvent e){	
		if(appletDestoried){
			System.gc();
			return;
		}
		
		
		if(timer ==2) {
			//Add key handler
			panel.addKeyListener(this);
			panel.addMouseMotionListener(this);
			panel.addMouseListener(this);
			panel.requestFocus();
		}
			
		
		polyCount = 0;
		
		//cap frame rate to around 30
		timer++;
		tm+=sleepTime;	
		long temp = Math.max(0, tm - System.currentTimeMillis());
		if(temp == 0)
			temp = (long)(lastTime*0.5);
		if(temp > 33)
			temp = 33;
		t.setDelay((int)temp);
		lastTime = temp;
		
		//handle input
		inputHandler.handleInput();
		

		Camera.update();
		
		//update game components,process game logic, move things around etc...
		modelDrawList.makeList();
		
		Terrain.update();
		PT.update();
		enemies.update();
		projectiles.update();
		powerUps.update();
		gameEventHandler.processEvent();
	
		//draw terrain
		if(terrainBufferFlag == true)
			terrainBufferFlag = false;
		else
			terrainBufferFlag = true;
		Terrain.draw();
		
		//draw game components
		modelDrawList.sort();
		modelDrawList.draw();
		
	
		gameHUD.update();
		//draw game HUD
		gameHUD.draw();
		
	
		//copy the screen buffer to video memory
		if(this.getGraphics() != null)
			paintComponent(panel.getGraphics());
	}
	
	
	public final void paintComponent(Graphics g){		
		
		//copy the pixel information to the video memory
		
		g.drawImage(doubleBuffer, 0, 0, this);
	}
	

	
	//copy screen content to a second stencilBuffer
	public static void copyScreen(){
		for(int i = 0; i < 640 * 480; i++){
			stencilBuffer2[i] = screen[i];
		}
	}
	
	//read keyboard inputs
	public final void keyPressed(KeyEvent e){
		inputHandler.keyPressed(e);
	}
	
	public final void keyReleased(KeyEvent e){
		inputHandler.keyReleased(e);
		
	}
	
	//read mouse input
	public final void mouseMoved(MouseEvent e){
		inputHandler.mouseMoved(e);
	}
	
	 public final void mouseDragged(MouseEvent e){
		 inputHandler.mouseDragged(e);
	 }
	
	
	
	 public final void mousePressed(MouseEvent e){
		 inputHandler.mousePressed(e);
		
		 
	 }
	 public final void mouseReleased(MouseEvent e){
		 inputHandler.mouseReleased(e);
			
	 }
	
	 public final void mouseEntered(MouseEvent e){
		 inputHandler.mouseEntered(e);
	 }
	 public final void mouseExited(MouseEvent e){
		 inputHandler.mouseExited(e);
	 }
	 
	 public final void keyTyped(KeyEvent e){
		
		 inputHandler.keyTyped(e);
	 }
   
	
	
	//unused method
	public final void update(Graphics g){}
   
  
    public final void mouseClicked(MouseEvent e){}
  
		
	

}