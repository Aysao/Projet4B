package PackageRender;

import javax.swing.*;

import PackageClass.Ennemi;
import PackageClass.Entity;
import PackageClass.Menu;
import PackageClass.Plateau;
import PackageClass.Player;
import PackageClass.Victory;
import PackageThreads.ThreadPlayer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class JMenuPrincipal extends JFrame {
	private ThreadPlayer runtp;
	private JPanel pprincipal;
	private JPanel pbutton;
	private MButton b1;
	private MButton b2;
	private MButton b3;
	private MButton b4;
	private int choix;
	private JFrame f;
	public JMenuPrincipal() {	
		f = this;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,600);
		this.setTitle("Pengo !!");
		initComposant();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		actionButton();
		}
	
	private void initComposant()
	{
		pprincipal = new JPanel();
		pprincipal.setBackground(Color.black);
		pprincipal.setLayout(null);
		pbutton = new JPanel();
		pbutton.setLayout(new GridLayout(2,2));
		b1 = new MButton("res/GlaceBlock.png");
		b2 = new MButton("res/GlaceBlock.png");
		b3 = new MButton("res/GlaceBlock.png");
		b4 = new MButton("res/GlaceBlock.png");
		b1.setText("Partie Solo");
		b2.setText("Partie Multi");
		b3.setText("Option");
		b4.setText("Quitter");
		b1.setRollover("res/blurGlaceBlock.png");
		b2.setRollover("res/blurGlaceBlock.png");
		b3.setRollover("res/blurGlaceBlock.png");
		b4.setRollover("res/blurGlaceBlock.png");
		b1.setPressed("res/click.png");
		b2.setPressed("res/click.png");
		b3.setPressed("res/click.png");
		b4.setPressed("res/click.png");
		b1.setBorder(null);
		b2.setBorder(null);
		b3.setBorder(null);
		b4.setBorder(null);
		pbutton.add(b1);
		pbutton.add(b2);
		pbutton.add(b3);
		pbutton.add(b4);
		pprincipal.add(pbutton);
		//pbutton.setBounds(new Rectangle((f.getWidth()/4),f.getHeight()/4,(f.getWidth()/4)*2,(f.getHeight()/4)*2));
		pbutton.setBounds((this.getWidth()/4),this.getHeight()/4,(this.getWidth()/4)*2,(this.getHeight()/4)*2);
		
		this.add(pprincipal);
		
	}
	private void actionButton()
	{
		b1.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				choix = 1;
				Menu m = new Menu(f,choix);
				Thread t = new Thread(m);
				t.start();
				f.setVisible(false);
			}
			
		});
		b4.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
			
		});
			
		
	}
	public int getChoix() {
		return choix;
	}
}
