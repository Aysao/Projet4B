package PackageThreads;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;

import PackageClass.Orientation;
import PackageClass.Plateau;
import PackageClass.Player;
import PackageRender.JMenuMulti;

public class Client implements Runnable{

	private int port = 8080;
	private PrintWriter sisw;
	private BufferedReader sisr;
	private Socket socket;
	private String pseudo;
	private JMenuMulti jmm;
	private boolean host;
	private boolean game = true;
	private boolean flag = false;
	
	public Client(String s,boolean _host,JFrame j)
	{		
		pseudo = s;
		this.host = _host;
		
		try {
			socket = new Socket("127.0.0.1", port);
		} catch (IOException e) {						
			e.printStackTrace();
		}
		try {
			sisr = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			sisw = new PrintWriter(new BufferedWriter(
			            new OutputStreamWriter(socket.getOutputStream())),true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sisw.println(pseudo);
		
		if(host)
		{
			jmm = new JMenuMulti(j,pseudo,host,this);
			jmm.setVisible(true);
			flag = true;
		}
		else
		{
			jmm = new JMenuMulti(j,pseudo,false,this);
			jmm.setVisible(true);
		}
		
		
			

//		sisw.println("start");
//		try {
//			Plateau.StringToPlateau(sisr.readLine());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@Override
	public void run() {
		Player p1 = null;
		String str = "";
		int slash = 0;
		int point = 0;
		String nbPlayer = "0";
		String nbEnnemi = "0";
		String sender = "";
		String action = "";
	
		while(!str.equals("Demarrer") || str.equals("stop"))
		{
			
			try {
				str = sisr.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i = 0;i<str.length();i++)
			{
				if(str.charAt(i) == '/')
				{
					slash = i;
				}
			}
			for(int i = 0;i<str.length();i++)
			{
				if(str.charAt(i) == '.')
				{
					point = i;
				}
			}
			if(slash != 0)
				sender = str.substring(0,slash);
			if(point == 0 && slash != 0)
			{
				action = str.substring(slash+1);
			}
			else {
				action = str.substring(slash+1,point);
			}
			switch(action)
			{
				case "goennemi":
				{
					System.out.println("sender : "+sender);
					System.out.println("action : "+action);
					jmm.goennemi(sender);
				}break;
				case "goplayer":
				{
					jmm.goplayer(sender);
				}break;
				case "coop":
				{
					jmm.setGoPlayer();
					jmm.setBequipe(false);
					jmm.refreshtable();
				}break;
				case "equipe":
				{
					jmm.setBequipe(true);
					jmm.refreshtable();
				}break;
				case "ennemi":
				{
					if(!jmm.isInArray(jmm.getPlayercollection(), sender) && !jmm.isInArray(jmm.getEnnemicollection(), sender))
					{
						jmm.setArray(jmm.getEnnemicollection(), sender);
						jmm.refreshtable();
						nbEnnemi = str.substring(point+1);
					}
					
				}break;
				case "player":
				{
					if(!jmm.isInArray(jmm.getPlayercollection(), sender) && !jmm.isInArray(jmm.getEnnemicollection(), sender))
					{
						jmm.setArray(jmm.getPlayercollection(), sender);
						jmm.refreshtable();
						nbPlayer = str.substring(point+1);
					}
					
				}break;
				case "rien":
				{
					if(!jmm.isInArray(jmm.getPlayercollection(), sender) && !jmm.isInArray(jmm.getEnnemicollection(), sender))
					{
						if(jmm.isInArray(jmm.getPlayercollection(), pseudo))
						{
							sisw.println(pseudo+"/"+"player."+jmm.nbArray(jmm.getPlayercollection()));
						}
						else
						{
							sisw.println(pseudo+"/"+"ennemi."+jmm.nbArray(jmm.getEnnemicollection()));
						}
						if(jmm.nbArray(jmm.getPlayercollection()) <= jmm.nbArray(jmm.getEnnemicollection()))
						{
							jmm.setArray(jmm.getPlayercollection(), sender);
							jmm.refreshtable();
						}
						else
						{
							jmm.setArray(jmm.getEnnemicollection(), sender);
							jmm.refreshtable();
						}
					
						
					}
				}
			}
			slash = 0;
			point = 0;
			System.out.println("nbplayer : "+nbPlayer);
			System.out.println("nbennemi : "+nbEnnemi);
			System.out.println("nbplayer thread courant : "+jmm.nbArray(jmm.getPlayercollection()));
			System.out.println("nbennemi thread courant : "+jmm.nbArray(jmm.getEnnemicollection()));
			if(flag == false && nbPlayer.equals(""+jmm.nbArray(jmm.getPlayercollection())) && nbEnnemi.equals(""+jmm.nbArray(jmm.getEnnemicollection())))
			{
				System.out.println("je me cr�e dans ma liste");
				if(jmm.nbArray(jmm.getPlayercollection()) <= jmm.nbArray(jmm.getEnnemicollection()))
				{
					jmm.setArray(jmm.getPlayercollection(), pseudo);
					jmm.refreshtable();
				}
				else
				{
					jmm.setArray(jmm.getEnnemicollection(), pseudo);
					jmm.refreshtable();
				}
				flag = true;
			
			}
			if(str.equals("stop"))
			{
				game = false;
				jmm.close();
				this.close();
			}
		}
		while(game)
		{
			
			str = "";
			try {
				str = sisr.readLine();// lecture du message
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			switch(str)
			{
			case "NORD":
			{
				p1.setOrientation(Orientation.NORD);
				p1.setMouvement(true);
				p1.Deplacement();
			}break;
			
			case "SOUTH":
			{
				p1.setOrientation(Orientation.SOUTH);
				p1.setMouvement(true);
				p1.Deplacement();
			}break;
			case "EAST":
			{
				p1.setOrientation(Orientation.EAST);
				p1.setMouvement(true);
				p1.Deplacement();
			}break;
			case "WEST":
			{
				p1.setOrientation(Orientation.WEST);
				p1.setMouvement(true);
				p1.Deplacement();
			}break;
			case "SO":
			{
				p1.setMouvement(false);
			}break;
			case "bordure":
			{
				Plateau.stopBordure();
			}break;		
			case "getplayer":
			{
				p1 = (Player)Plateau.getPlayer().get(0);
			}break;
			case "new location":
			{			
				boolean x =false;
				boolean y = false;
				while(!y)
				{
					String s="";
					try {
						s = sisr.readLine();// lecture du message
						//System.out.println(str);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(!s.isEmpty()&&!s.isBlank())
					{
						if(!x)
						{
							System.out.println(s);
							Menu.getInstance().getP2().setPosX(Integer.parseInt(s));
							x=true;
							
						}
						else
						{
							System.out.println(s);
							Menu.getInstance().getP2().setPosY(Integer.parseInt(s));
							y=true;
						}						
					}							
				}
				Plateau.refreshEntity(Menu.getInstance().getP2());
							
			}break;
			case "win":
			{
				Menu.getInstance().getV().setVictory(true);
				Menu.getInstance().fin();
				close();				
			}break;
			case "loose":
			{
				Menu.getInstance().getV().setVictory(false);
				Menu.getInstance().fin();
				close();				
			}break;
			}
			if(str.matches("win") ||str.matches("loose"))
			{
				break;
			}
		}
	}
	
	private void close() {
		sisw.println("stop");
		try {
			sisr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sisw.close();
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void sendLine(String str)
	{
		sisw.println(str);
	}

	

	
	
}
