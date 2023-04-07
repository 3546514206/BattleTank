package core;
//hold all the enimies
public class Enemies {

	public static Model[] enemy;
	
	public static void init(){
		enemy = new Model[109];
		enemy[0] = new MediumTank(9.5,-0.975,5.5, 30);
		enemy[1] = new MediumTank(9,-0.975,5, 75);
		enemy[2] = new MediumTank(9,-0.975,5.5, 175);
		enemy[3] = new MediumTank(5.8,-0.975,5.5, 165);
		enemy[4] = new MediumTank(6.05,-0.975,5.7, 135);
		enemy[5] = new MediumTank(13.5,-0.975,5.5, 165);
		enemy[6] = new MediumTank(12.75,-0.975,5.7, 35);
		enemy[7] = new MissileLauncher(5.8,-0.975,6.2, 180);
		
		enemy[8] = new MediumTank(10,-0.975,8, 35);
		enemy[9] = new MediumTank(10.5,-0.975,7.75, 185);
		enemy[10] = new MediumTank(11,-0.975,7.75, 170);
		enemy[11] = new MediumTank(11.5,-0.975,8, 120);
		enemy[12] = new MissileLauncher(11,-0.975,8.25, 180);
		enemy[13] = new MissileLauncher(10.25,-0.975,8.25, 210);
		enemy[14] = new MediumTank(5.75,-0.975,12.3, 35);
		enemy[15] = new MediumTank(6,-0.975,12, 351);
		enemy[16] = new MediumTank(4.2,-0.975,9.5, 135);
		enemy[17] = new MediumTank(4.6,-0.975,9.5, 135);
		enemy[18] = new MissileLauncher(4.4,-0.975,9.8, 135);
		
		enemy[19] = new StealthTank(15,-0.975,10, 160);
		enemy[20] = new MediumTank(15,-0.975,9.7, 135);
		enemy[21] = new MediumTank(15.5,-0.975,9.7, 35);
		enemy[22] = new StealthTank(13,-0.975,11.5, 160);
		enemy[23] = new StealthTank(12.38,-0.975,11.9, 160);
		
		enemy[24] = new MediumTank(10.025,-0.975,13.625, 195);
		enemy[25] = new MediumTank(10.475,-0.975,13.625, 180);
		enemy[26] = new MediumTank(10.775,-0.975,13.625, 170);
		enemy[27] = new MediumTank(11.075,-0.975,13.625, 155);
		enemy[28] = new MediumTank(11.4,-0.975,13.625, 135);
		
		enemy[29] = new MissileLauncher(11,-0.975,14.125, 160);
		enemy[30] = new MissileLauncher(10.6,-0.975,14.125, 175);
		enemy[31] = new MissileLauncher(10.2,-0.975,14.125, 195);
		enemy[32] = new MediumTank(13.625,-0.975,14.625, 335);
		enemy[33] = new MediumTank(14.125,-0.975,14.875, 235);
		enemy[34] = new MediumTank(14.825,-0.975,14.875, 15);
		enemy[35] = new MissileLauncher(15.125,-0.975,14.625, 195);
		enemy[36] = new StealthTank(14.375,-0.975,15.125, 160);
		enemy[37] = new StealthTank(12.625,-0.975,14.625, 160);
		
		enemy[38] = new MediumTank(10.38,-0.975,18.4, 200);
		MediumTank temp = (MediumTank)enemy[38];
		temp.active = false;
		
		enemy[39] = new MediumTank(10.75,-0.975,18.4, 180);
		temp = (MediumTank)enemy[39];
		temp.active = false;
		
		enemy[40] = new MediumTank(11.08,-0.975,18.4, 150);
		temp = (MediumTank)enemy[40];
		temp.active = false;
		
		enemy[41] = new MissileLauncher(10.55,-0.975,18.705, 190);
		MissileLauncher temp2 = (MissileLauncher)enemy[41];
		temp2.active = false;
		
		enemy[42] = new MissileLauncher(10.99,-0.975,18.705, 160);
		temp2 = (MissileLauncher)enemy[42];
		temp2.active = false;
		
		enemy[43] = new MediumTank(3.375,-0.975,13.875, 200);
		enemy[44] = new MediumTank(3.125,-0.975,13.8, 180);
		enemy[45] = new MediumTank(2.855,-0.975,13.6, 160);
		
		enemy[46] = new MissileLauncher(3.1,-0.975,14.15, 180);
		
		enemy[47] = new MissileLauncher(3.325,-0.975,14.15, 180);
		
		enemy[48] = new MediumTank(4.875,-0.975,20.375, 160);
		enemy[49] = new MediumTank(5.125,-0.975,20.625, 160);
		
		enemy[50] = new MediumTank(6.625,-0.975,25.125, 190);
		enemy[51] = new MediumTank(6.875,-0.975,25.375, 160);
		
		enemy[52] = new StealthTank(3.375,-0.975,23.625, 160);
		enemy[53] = new StealthTank(3.625,-0.975,14.625, 160);
		enemy[54] = new StealthTank(3.625,-0.975,23.825, 160);
		
		enemy[55] = new StealthTank(7.125,-0.975,24.625, 160);
		enemy[56] = new StealthTank(4.125,-0.975,21.375, 160);
		enemy[57] = new StealthTank(3.375,-0.975,19.375, 160);
		
		enemy[58] = new MediumTank(5.625,-0.975,16.125, 210);
		enemy[59] = new MediumTank(5.925,-0.975,15.825, 160);
		
		enemy[60] = new StealthTank(5.875,-0.975,16.625, 160);
		enemy[61] = new StealthTank(1.375,-0.975,17.375, 160);
		enemy[62] = new MissileLauncher(6.375,-0.975,16.375, 160);
		
		enemy[63] = new GunTurret(10.125,-1, 17.625, 180);
		enemy[64] = new GunTurret(11.375,-1, 17.625, 180);
		enemy[65] = new GunTurret(11.125,-1, 23.375, 0);
		enemy[66] = new GunTurret(10.125,-1, 23.375, 0);
		enemy[67] = new GunTurret(7.875,-1, 20.875, 90);
		enemy[68] = new GunTurret(7.875,-1, 19.875, 90);
		enemy[69] = new GunTurret(14.375,-1, 19.875, 270);
		enemy[70] = new GunTurret(14.375,-1, 20.875, 270);
		
		enemy[71] = new MissileLauncher(11.875,-0.975,26.875, 160);
		enemy[72] = new MissileLauncher(12.375,-0.975,26.875, 60);
		enemy[73] = new MediumTank(12.475,-0.975,27.175, 190);
		enemy[74] = new MediumTank(12.875,-0.975,27.375, 10);
		enemy[75] = new MediumTank(10.625,-0.975,25.375, 10);
		enemy[76] = new MediumTank(10.975,-0.975,25.375, 100);
		enemy[77] = new StealthTank(11.875,-0.975,25.875, 160);
		
		enemy[78] = new StealthTank(10.875,-0.975,26.125, 160);
		
		enemy[79] = new MediumTank(18.875,-0.975,18.625, 10);
		enemy[80] = new MediumTank(18.675,-0.975,18.95, 110);
		enemy[81] = new MediumTank(19.075,-0.975,18.95, 210);
		
		enemy[82] = new MissileLauncher(17.125,-0.975,19.875, 160);
		
		enemy[83] = new GunTurret(8.375,-1, 30.875, 270);
		enemy[84] = new GunTurret(9.375,-1, 28.875, 270);
		enemy[85] = new GunTurret(11.125,-1, 29.875, 270);
		
		enemy[86] = new GunTurret(15.125,-1, 28.625, 183);
		enemy[87] = new GunTurret(15.625,-1, 27.625, 223);
		enemy[88] = new GunTurret(16.375,-1, 26.875, 163);
		
		enemy[89] = new MediumTank(16.625,-0.975,23.625, 210);
		enemy[90] = new MediumTank(16.375,-0.975,23.5, 110);
		enemy[91] = new MediumTank(16.875,-0.975,23.6, 310);
		
		enemy[92] = new StealthTank(16.875,-0.975,21.625, 160);
		enemy[93] = new StealthTank(17.375,-0.975,22.125, 160);
		enemy[94] = new StealthTank(17.875,-0.975,21.875, 160);
		
		enemy[95] = new MissileLauncher(16.125,-0.975,28.125, 60);

		enemy[96] = new MissileLauncher(10.805,-0.975,21.775, 325);
		temp2 = (MissileLauncher)enemy[96];
		temp2.active = false;
		
		enemy[97] = new MissileLauncher(10.775,-0.975,21.375, 205);
		temp2 = (MissileLauncher)enemy[97];
		temp2.active = false;
		
		enemy[98] = new MissileLauncher(10.475,-0.975,21.575, 85);
		temp2 = (MissileLauncher)enemy[98];
		temp2.active = false;
		
		enemy[99] = new MediumTank(10.7,-0.975,22.225, 0);
		temp = (MediumTank)enemy[99];
		temp.active = false;
		
		enemy[100] = new MediumTank(10.7,-0.975,20.875, 180);
		temp = (MediumTank)enemy[100];
		temp.active = false;
		
		enemy[101] = new MediumTank(11.377,-0.975,21.875, 300);
		temp = (MediumTank)enemy[101];
		temp.active = false;
		
		enemy[102] = new MediumTank(10,-0.975,21.875, 60);
		temp = (MediumTank)enemy[102];
		temp.active = false;
		
		enemy[103] = new MediumTank(10,-0.975,21.25, 120);
		temp = (MediumTank)enemy[103];
		temp.active = false;
		
		enemy[104] = new MediumTank(11.377,-0.975,21.25, 240);
		temp = (MediumTank)enemy[104];
		temp.active = false;
		
		enemy[105] = new StealthTank(12.625,-0.975,20.125, 160);
		StealthTank temp3 = (StealthTank)enemy[105];
		temp3.active = false;
		
		enemy[106] = new StealthTank(12.625,-0.975,20.625, 160);
		temp3 = (StealthTank)enemy[106];
		temp3.active = false;
		
		enemy[107] = new annihilator(12.625, -0.975, 21.375, 90);
		annihilator temp4 = (annihilator)enemy[107];
		temp4.active = false;
		
		enemy[108] = new annihilator(12.625, -0.975, 19.375, 90);
		temp4 = (annihilator)enemy[108];
		temp4.active = false;
		
	}
	
	
	
	public static void update(){
		for(int i = 0; i < enemy.length; i++)
			if(enemy[i] != null){
				if(enemy[i].getLifeSpan() <= 0)
					enemy[i] = null;
				else
					enemy[i].update();
			}
	}
	
}
