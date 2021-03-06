package PackageRender;
import PackageClass.*;


import java.awt.*;
import Main.Main;
import javax.swing.*;




public class GameFrame extends JPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Largeur = 800-5;
	private int Hauteur = 600;
	private int BlockL = Largeur/Plateau.getLargeur();
	private int BlockH = Hauteur/Plateau.getHauteur();
	private Image iceblock;
	private Image fpengo;
	private Image bpengo;
	private Image rpengo;
	private Image lpengo;
	private Image fhunter;
	private Image bhunter;
	private Image rhunter;
	private Image lhunter;
	private Image diamond;
	private Image floor;
	private Image bordure1;
	private Image bordure2;
	private Image ennemiStun;
	
	public GameFrame(int L,int H)
	{
		
		if(Main.mode == 0)
		{
			iceblock = new ImageIcon("Image/GlaceBlock.png").getImage();
			fpengo = new ImageIcon("Image/Penguin1.png").getImage();
			diamond = new ImageIcon("Image/Diamant.png").getImage();
			bpengo = new ImageIcon("Image/PenguinDos.png").getImage();
			lpengo = new ImageIcon("Image/PenguinVersGauche.png").getImage();
			rpengo = new ImageIcon("Image/PenguinVersDroite.png").getImage();
			floor = new ImageIcon("Image/floor.png").getImage();
			bordure1 = new ImageIcon("Image/bordureClassic.png").getImage();
			bordure2 = new ImageIcon("Image/bordureActivate.png").getImage();
			fhunter = new ImageIcon("Image/Hunterface.png").getImage();
			bhunter = new ImageIcon("Image/Hunterdos.png").getImage();
			rhunter = new ImageIcon("Image/Hunterdroite.png").getImage();
			lhunter = new ImageIcon("Image/Huntergauche.png").getImage();
			ennemiStun = new ImageIcon("Image/Huntercroix.png").getImage();
		}
		else
		{
			iceblock = new ImageIcon(this.getClass().getResource("/GlaceBlock.png")).getImage();
			fpengo = new ImageIcon(this.getClass().getResource("/Penguin1.png")).getImage();
			diamond = new ImageIcon(this.getClass().getResource("/Diamant.png")).getImage();
			bpengo = new ImageIcon(this.getClass().getResource("/PenguinDos.png")).getImage();
			lpengo = new ImageIcon(this.getClass().getResource("/PenguinVersGauche.png")).getImage();
			rpengo = new ImageIcon(this.getClass().getResource("/PenguinVersDroite.png")).getImage();
			floor = new ImageIcon(this.getClass().getResource("/floor.png")).getImage();
			bordure1 = new ImageIcon(this.getClass().getResource("/bordureClassic.png")).getImage();
			bordure2 = new ImageIcon(this.getClass().getResource("/bordureActivate.png")).getImage();
			ennemiStun = new ImageIcon(this.getClass().getResource("/Huntercroix.png")).getImage();
			fhunter = new ImageIcon(this.getClass().getResource("/Hunterface.png")).getImage();
			bhunter = new ImageIcon(this.getClass().getResource("/Hunterdos.png")).getImage();
			rhunter = new ImageIcon(this.getClass().getResource("/Hunterdroite.png")).getImage();
			lhunter = new ImageIcon(this.getClass().getResource("/Huntergauche.png")).getImage();
		}
		Largeur = L-5;
		Hauteur = H-2*(H/15)-37;
		BlockH = Hauteur/Plateau.getHauteur();
		BlockL = Largeur/Plateau.getLargeur();		
	}
	public GameFrame()
	{
		
	}	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		for(int i = 0;i<Plateau.getHauteur();i++)
		{
			for(int j = 0;j<Plateau.getLargeur();j++)
			{
				if(Plateau.plateau[i][j] != null)
				{
					if(Plateau.plateau[i][j].getClass() == BlocN.class)
					{
						g.drawImage(iceblock,BlockL*j,BlockH*i,BlockL,BlockH, null);
					}
					if(Plateau.plateau[i][j].getClass() == BlocSpe.class)
					{
                    	g.drawImage(floor,BlockL*j,BlockH*i,BlockL,BlockH, null);
						g.drawImage(diamond,BlockL*j,BlockH*i,BlockL,BlockH, null);
					}
					if(Plateau.plateau[i][j].getClass() == Player.class)
					{
						Player p = (Player) Plateau.plateau[i][j];																		
						switch(p.getOrientation())
						{
						case NORD:{
							g.drawImage(floor,BlockL*j,BlockH*i,BlockL,BlockH, null);
                            g.drawImage(bpengo,BlockL*j,BlockH*i,BlockL,BlockH, null);
                        }break;
                        case EAST:{
                        	g.drawImage(floor,BlockL*j,BlockH*i,BlockL,BlockH, null);
                            g.drawImage(rpengo,BlockL*j,BlockH*i,BlockL,BlockH, null);
                        }break;
                        case WEST:{
                        	g.drawImage(floor,BlockL*j,BlockH*i,BlockL,BlockH, null);
                            g.drawImage(lpengo,BlockL*j,BlockH*i,BlockL,BlockH, null);
                        }break;
                        case SOUTH:{
                        	g.drawImage(floor,BlockL*j,BlockH*i,BlockL,BlockH, null);
                            g.drawImage(fpengo,BlockL*j,BlockH*i,BlockL,BlockH, null);
                        }break;
                        case SO:
						{
							g.drawImage(floor,BlockL*j,BlockH*i,BlockL,BlockH, null);
							g.drawImage(fpengo,BlockL*j,BlockH*i,BlockL,BlockH, null);
						}break;
						}						
					}
					if(Plateau.plateau[i][j].getClass() == Ennemi.class)
					{
						Ennemi en = (Ennemi)Plateau.plateau[i][j]; 
						g.drawImage(floor,BlockL*j,BlockH*i,BlockL,BlockH, null);
						if(en.stun)
						{
							g.drawImage(ennemiStun,BlockL*j,BlockH*i,BlockL,BlockH, null);
						}
						else {
							switch(en.getOrientation())
							{
							case NORD:{
	                            g.drawImage(bhunter,BlockL*j,BlockH*i,BlockL,BlockH, null);
	                        }break;
	                        case EAST:{
	                            g.drawImage(rhunter,BlockL*j,BlockH*i,BlockL,BlockH, null);
	                        }break;
	                        case WEST:{
	                            g.drawImage(lhunter,BlockL*j,BlockH*i,BlockL,BlockH, null);
	                        }break;
	                        case SOUTH:{
	                            g.drawImage(fhunter,BlockL*j,BlockH*i,BlockL,BlockH, null);
	                        }break;
	                        case SO:{
	                            g.drawImage(bhunter,BlockL*j,BlockH*i,BlockL,BlockH, null);
	                        }break;
							}						
						}					
					}
					if(Plateau.plateau[i][j].getClass() == Bordure.class)
					{
						Bordure b = (Bordure)Plateau.plateau[i][j];
						if(b.isActivate())	
						{						
							g.drawImage(bordure2,BlockL*j,BlockH*i,BlockL,BlockH, null);
						}							
						else
						{
							g.drawImage(bordure1,BlockL*j,BlockH*i,BlockL,BlockH, null);							
						}
					}
					if(Plateau.plateau[i][j].getClass() == String.class)
					{											
						g.fillRect(BlockL*j,BlockH*i,BlockL,BlockH);
						g.drawImage(floor,BlockL*j,BlockH*i,BlockL,BlockH, null);
					}					
				}
				g.setColor(Color.BLACK);
				g.drawRect(BlockL*j,BlockH*i,BlockL,BlockH);			
			}
		}
		g.dispose();
		
	}

} 
	


