package PackageThreads;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import PackageClass.Ennemi;
import PackageClass.Plateau;
import PackageClass.Player;
import PackageClass.Victory;
import PackageRender.Render;


public class Menu implements Runnable {

	public static Player p1;
	public static Ennemi p2;
	public static int ennemiVie=5;
	public static Ennemi e1;
	public static Ennemi e2;
	public static Ennemi e3;
	public static Ennemi e4;
	public static Render  r ;	
	public static Victory  v ;
	public static char avancer='Z';//touche de deplacement
	public static char reculer='S';
	public static char droite='D';
	public static char gauche='Q';
	private JFrame menuPrincipal;
	public static boolean host = true ;
	public static Client c;
	public static Serveur s;
	public static int mode ;
	public static boolean gamestart=true;
	
	
	
	
	
	public Menu(JFrame f,int i) 
	{
		mode=i;
		menuPrincipal = f;
		switch(i)
		{
			case 1://nb de joueur 
			{
				new Plateau();
				p1 = new Player(7,7);
				
				p1.setPseudo(JOptionPane.showInputDialog("Entrer votre pseudo :"));	
				e1 = new Ennemi();				
				e2 = new Ennemi();				
				e3 = new Ennemi();				
				v = new Victory();
				p1.start();
				e1.start();
				e2.start();//les e ont un thread associ� (pour pouvoir le stop grace a l'ennemi)
				e3.start();									
				r = new Render(600,800,p1.getpT().getKl());
				Thread t = new Thread(r);
				t.start();				
			}break;
			case 2:
			{
				if(host)
				{
					new Plateau();
					v = new Victory();	
					p1 = new Player(7,7);
					p1.setPseudo(JOptionPane.showInputDialog("Entrer votre pseudo :"));	
					p2= new Ennemi(8,8);
					s = new Serveur();
					Thread th = new Thread(s);
					th.start();
					gamestart=false;
					while(!gamestart)
					{
						System.out.println("waiting...");
					}
					p1.start();																					
					r = new Render(600,800,p1.getpT().getKl());					
					Thread t = new Thread(r);
					t.start();	
				}
				else
				{								
					c= new Client();
					Thread tc = new Thread(c);
					tc.start();
					p1 = new Player(7,7);
					p2 = new Ennemi(8,8);
					p2.setPlayed(true);
					p2.start();
					v = new Victory();																															
					r = new Render(600,800,p2.getpT().getKl());					
					Thread t = new Thread(r);
					t.start();					
				}				
			}
		}
	}
	public static void newEnnemi()
	{
		ennemiVie--;
		System.out.println("vie"+ennemiVie);
		if(ennemiVie==0)
		{
			v.setVictory(true);
			return;
		}
		else if(ennemiVie>=3)
		{
			Ennemi e=new Ennemi();
			e.start();	
		}		
	}

	public void fin() 
	{	
		try {
			p1.getScr().setScore();
		} catch (IOException e) {
			e.printStackTrace();
		}
		clear();
		if(v.isVictory() == false)
		{
			JOptionPane.showMessageDialog(r,"Perdu !");
		}
		if(v.isVictory() == true)
		{
			JOptionPane.showMessageDialog(r,"Gagn�!");
		}	
		
	}
	public void clear()
	{
		ennemiVie = 6;
		Plateau.clearEntity();//supprime thread player et ennemis restant
		r.stop();//stop refresh fenetre
		r.dispose();
		menuPrincipal.setVisible(true);		
	}
	@Override
	public void run() {		
		while(!(v.isVictory()) && p1.getVie() > 0)
		{
			try {
				Thread.sleep(100);
				Plateau.stopBordure();
				System.out.println("test");
				if(host &&mode==2)
				{
					s.sendLine("bordure");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		fin();
		
	}
	
}