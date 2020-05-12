package PackageRender;
import javax.swing.*;

import PackageClass.Entity;
import PackageClass.Plateau;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import PackageThreads.Menu;





public class Render extends JFrame implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Largeur = 800;
	private int Hauteur = 600+37;
	private JPanel bottom;
	private boolean running = true;
	private ScoringFrame sc;
	public Render(int L,int H,KeyListener kl)
	{	
		Largeur = L;
		Hauteur = H+50;
		bottom = new JPanel();
		bottom.setPreferredSize(new Dimension(Largeur,Hauteur/15));
		bottom.setBackground(Color.black);
		sc = new ScoringFrame(Largeur,Hauteur);
		this.setSize(Largeur, Hauteur);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(sc,BorderLayout.NORTH);
		this.getContentPane().add(new GameFrame(Largeur,Hauteur),BorderLayout.CENTER);
		this.getContentPane().add(bottom,BorderLayout.SOUTH);
		this.addKeyListener(kl);
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) {
				
				if(e.getKeyChar() == KeyEvent.VK_ESCAPE) {
					if(running == true)
					{
						stop();
						System.exit(1);
					}
					if(running == false)
					{
						System.exit(1);
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
//	@Override
//	public void run() 
//	{
//		int fps = 10;
//		double timeTick = 1000000000 / fps;
//		double delta = 0;
//		long now;
//		long lastTime = System.nanoTime();
//		long timer = 0;
//		int tick = 0;
//
//		while(running)
//		{
//			now = System.nanoTime();
//			delta += (now - lastTime) / timeTick;
//			timer += now - lastTime;
//			lastTime = now;
//			
//			if(delta >= 1 && Menu.host)
//			{					
//				sc.setScoreP1();
//				sc.setHighScore();
//				//sc.setScoreP2();
//				this.repaint();
//				delta--;
//				tick++;
//			}
//			else if (delta >= 1)
//			{
//				sc.setScoreP1();
//				sc.setHighScore();	
//				//sc.setScoreP2();
//				this.repaint();
//				delta--;
//				tick++;	
//			}
//			else if(delta >= 1)
//			{
//				delta--;
//				tick++;
//			}
//			if(timer >= 1000000000)
//			{
//				System.out.println("fps : "+tick);
//				tick = 0;
//				timer = 0;
//			}
//		}
//		
//	} 
	
	@Override
	public void run() 
	{
		sc.setHighScore();
		
		while(running)
		{
			sc.setScoreP1();
			this.repaint();
			try {
				Thread.sleep(1000/60l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void stop()
	{
		running = false;
	}
}
