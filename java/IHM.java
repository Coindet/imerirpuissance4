import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 * 
 */

/**
 * @author imerir
 *
 */
public class IHM extends JFrame implements ActionListener{

	//IHMEcriture recept;
	int nombreCoupJoue=0;
	Joueur c=null;
	String s ;
	Connection th;
	boolean isYourTurn;
	int monTableauColonnePleine [];
	
	private static final long serialVersionUID = 1L;
	JMenuBar menuBar = new JMenuBar();
	JMenu menu1 = new JMenu("Partie");
	JMenuItem nouvelle = new JMenuItem("New");
	JMenuItem quitter = new JMenuItem("Quit");
	
	private JTextField tfHost=new JTextField("localhost");
	private JTextField tfNickName=new JTextField("nom");
	private JTextField tfPort=new JTextField("808080");
	Plateau monPlateau = new Plateau();

	private JButton colonne1 = new JButton("1");
	private JButton colonne2 = new JButton("2");
	private JButton colonne3 = new JButton("3");
	private JButton colonne4 = new JButton("4");
	private JButton colonne5 = new JButton("5");
	private JButton colonne6 = new JButton("6");
	private JButton colonne7 = new JButton("7");
	private JButton connect = new JButton("Connection");
	private JLabel HostLabel = new JLabel("Host:");
	private JLabel PortLabel = new JLabel("Port:");
	private JLabel NickLabel = new JLabel("Nom:");

	public IHM () {
		 
		monTableauColonnePleine = new int[7];
		for(int i=0;i<7;i++) monTableauColonnePleine[i]=0;
		
		setTitle("Puissance 4");
		setJMenuBar(menuBar);
		menuBar.add(menu1);	
		menu1.add(nouvelle);
		menu1.add(quitter);
		
	
this.setLayout(new BorderLayout());

JPanel center = new JPanel();
JPanel east=new JPanel();
JPanel south=new JPanel();
JPanel north= new JPanel();
this.add("Center",center);
this.add("East",east);
this.add("South",south);
this.add("North",north);


north.setLayout(new FlowLayout(FlowLayout.LEFT,0,2));

//north.setPreferredSize(new Dimension(80,80));
colonne1.setPreferredSize(new Dimension(80,20));
colonne2.setPreferredSize(new Dimension(80,20));
colonne3.setPreferredSize(new Dimension(80,20));
colonne4.setPreferredSize(new Dimension(80,20));
colonne5.setPreferredSize(new Dimension(80,20));
colonne6.setPreferredSize(new Dimension(80,20));
colonne7.setPreferredSize(new Dimension(80,20));

	

north.add("West",colonne1);

north.add("East",colonne2);
north.add("East",colonne3);
north.add("East",colonne4);
north.add("East",colonne5);
north.add("East",colonne6);
north.add("East",colonne7);


center.setLayout(new BorderLayout());
center.setPreferredSize(new Dimension(2*80, 6*80));
center.add("Center",monPlateau);
//

east.setBorder(new LineBorder(Color.BLACK));
east.setLayout(new GridLayout(0,1));
east.add("North",connect);
east.add("North",HostLabel);
tfHost.setEditable(false);
east.add("North",tfHost);

east.add("North",PortLabel);
tfPort.setEditable(false);
east.add("North",tfPort);
east.add("North",NickLabel);
east.add("North",tfNickName);

south.setLayout(new BorderLayout());

south.setLayout(new GridLayout(0,2));
south.add("Center",HostLabel);
tfHost.setEditable(false);
south.add("Center",tfHost);
south.add("Center",PortLabel);
tfPort.setEditable(false);
south.add("Center",tfPort);
south.add("Center",NickLabel);
south.add("Center",tfNickName);
south.add("South",connect);

	connect.addActionListener(this);
	quitter.addActionListener(this);
	tfNickName.addActionListener(this);
	
	colonne1.addActionListener(this);
	colonne2.addActionListener(this);
	colonne3.addActionListener(this);
	colonne4.addActionListener(this);
	colonne5.addActionListener(this);
	colonne6.addActionListener(this);
	colonne7.addActionListener(this);
	

	}

//-----------------------------------------------------------------------------------------------
	public void requeteEgalite(){
		c.getCanalEcriture().println("4_"+monPlateau.player+"_"+nombreCoupJoue+"_");
		nombreCoupJoue=0;
		JOptionPane.showMessageDialog(null, "Egalité ! ");
		monPlateau.reinitPlateau();
	}
		
//-----------------------------------------------------------------------------------------------
	public void GriserColonnePleine(){
		
		if(monTableauColonnePleine[0]==1) colonne1.setEnabled(false);
		if(monTableauColonnePleine[1]==1) colonne2.setEnabled(false);
		if(monTableauColonnePleine[2]==1) colonne3.setEnabled(false);
		if(monTableauColonnePleine[3]==1) colonne4.setEnabled(false);
		if(monTableauColonnePleine[4]==1) colonne5.setEnabled(false);
		if(monTableauColonnePleine[5]==1) colonne6.setEnabled(false);
		if(monTableauColonnePleine[6]==1) colonne7.setEnabled(false);
		
		int nbColPleine=0;
		for(int i=0;i<=6;i++){
			nbColPleine = nbColPleine+monTableauColonnePleine[i];
		}
		if(nbColPleine==7) requeteEgalite();
	}
	
//-----------------------------------------------------------------------------------------------
	public void MasquerBouton(boolean b){
		colonne1.setEnabled(b);
		colonne2.setEnabled(b);
		colonne3.setEnabled(b);
		colonne4.setEnabled(b);
		colonne5.setEnabled(b);
		colonne6.setEnabled(b);
		colonne7.setEnabled(b);
		
		GriserColonnePleine();
	}

//------------------------------------------------------------------------------------------------
	public void requeteNouvellePartie(){
		
		c.getCanalEcriture().println("5_"+monPlateau.player+"_"+nombreCoupJoue+"_");
		nombreCoupJoue=0;
		monPlateau.reinitPlateau();
	}

//------------------------------------------------------------------------------------------------
	public void popUpVictoire(int typeVictoire){
		String message ="";
		
		switch(typeVictoire){
			case 1: message= "Vous avez gagne verticalement !"; break;
			case 2: message= "Vous avez gagne horizontalement !"; break;
			case 3:	message= "Vous avez gagne diagonalement !"; break;
			
			default:break;
			
		}
		if(typeVictoire!=0){
			JOptionPane.showMessageDialog(null, message);
			JOptionPane.showMessageDialog(null, "Début nouvelle partie .");
			requeteNouvellePartie();
		}
	}
//-----------------------------------------------------------------------------------------------	
	public void CreerClient(){
				
					
		IHMinter recept = new IHMinter(){
	
			public void write(String message) {
					String FirstValue = ""+message.charAt(0);		
					switch(Integer.parseInt(FirstValue)){
					
					case 1:	String PlayerValue = ""+message.charAt(2);
							monPlateau.player = Integer.parseInt(PlayerValue)+1;
							if(monPlateau.player==1){
								isYourTurn = true;
								MasquerBouton(isYourTurn);
							}
							else {
								isYourTurn = false;
								MasquerBouton(isYourTurn);
							}
							break;
					
					case 2: String ColumnValue = ""+message.charAt(2);
							String ColorValue = ""+message.charAt(4);monTableauColonnePleine[Integer.parseInt(ColumnValue)]=monPlateau.AjouterPion(Integer.parseInt(ColumnValue), Integer.parseInt(ColorValue));
							nombreCoupJoue++;
							isYourTurn = true;
							MasquerBouton(isYourTurn);
							break;
						
					case 3:	JOptionPane.showMessageDialog(null, "Egalité !");
							monPlateau.reinitPlateau();	
							JOptionPane.showMessageDialog(null, "Début nouvelle partie .");
							break;
					
					case 4:	//textarea.append("\n"+message);
							JOptionPane.showMessageDialog(null, "Vous avez lamentablement perdu..");
							JOptionPane.showMessageDialog(null, "Début nouvelle partie .");
							monPlateau.reinitPlateau();	
							break;
							
					case 5: JOptionPane.showMessageDialog(null, "L'adversaire a fui comme un lâche.");
							break;		
							 
					default:break;	
					}
					
					}
						
			};
				
			try {
					c = new Joueur(tfHost.getText(),Integer.parseInt(tfPort.getText()),tfNickName.getText());
					th=new Connection(c.getCanalLecture(),recept);
				    th.start();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	}

	

//---------------------------GESTION EVENT------------------------------------------------	
	public void actionPerformed(ActionEvent e) {
		
	
		
		if(e.getSource()==quitter){
					c.getCanalEcriture().println("6_");
					c.getCanalEcriture().println("quit");
					System.exit(0);
				
		}
		
	//---------------------------------connect---------------------------------	
	if(e.getSource()==connect){
	
		CreerClient();

	
	}
	
//-------------------------------------------------------------------------------------------
	if(e.getSource()==colonne1){
		

		monTableauColonnePleine[0]=monPlateau.AjouterPion(0, monPlateau.player);
		nombreCoupJoue++;

		c.getCanalEcriture().println("3_0_"+monPlateau.player);
		popUpVictoire(monPlateau.gagnant(0, monPlateau.player));
		isYourTurn = false;
		MasquerBouton(isYourTurn);
	}
	
//-------------------------------------------------------------------------------------------
	if(e.getSource()==colonne2){
		

		monTableauColonnePleine[1]=monPlateau.AjouterPion(1, monPlateau.player);
		nombreCoupJoue++;

		c.getCanalEcriture().println("3_1_"+monPlateau.player);
		popUpVictoire(monPlateau.gagnant(1, monPlateau.player));	
		isYourTurn = false;
		MasquerBouton(isYourTurn);	
	}	
//-------------------------------------------------------------------------------------------
	if(e.getSource()==colonne3){
			

		monTableauColonnePleine[2]=monPlateau.AjouterPion(2, monPlateau.player);
		nombreCoupJoue++;
		c.getCanalEcriture().println("3_2_"+monPlateau.player);
		popUpVictoire(monPlateau.gagnant(2, monPlateau.player));
		
		isYourTurn = false;
		MasquerBouton(isYourTurn);	
		}
		
//-------------------------------------------------------------------------------------------
	if(e.getSource()==colonne4){
			

		nombreCoupJoue++;
		monTableauColonnePleine[3]=monPlateau.AjouterPion(3, monPlateau.player);
		c.getCanalEcriture().println("3_3_"+monPlateau.player);
		popUpVictoire(monPlateau.gagnant(3, monPlateau.player));

		isYourTurn = false;
		MasquerBouton(isYourTurn);	
		}
		
//-------------------------------------------------------------------------------------------
	if(e.getSource()==colonne5){
			

		nombreCoupJoue++;
		monTableauColonnePleine[4]=monPlateau.AjouterPion(4, monPlateau.player);
		c.getCanalEcriture().println("3_4_"+monPlateau.player);

		popUpVictoire(monPlateau.gagnant(4, monPlateau.player));
		isYourTurn = false;
		MasquerBouton(isYourTurn);
		}		
	
//-------------------------------------------------------------------------------------------
	if(e.getSource()==colonne6){
			
		nombreCoupJoue++;
		monTableauColonnePleine[5]=monPlateau.AjouterPion(5, monPlateau.player);
		c.getCanalEcriture().println("3_5_"+monPlateau.player);
		popUpVictoire(monPlateau.gagnant(5, monPlateau.player));
		
		isYourTurn = false;
		MasquerBouton(isYourTurn);	
		}
	
//-------------------------------------------------------------------------------------------
if(e.getSource()==colonne7){
			

		monTableauColonnePleine[6]=monPlateau.AjouterPion(6, monPlateau.player);
		nombreCoupJoue++;
		c.getCanalEcriture().println("3_6_"+monPlateau.player);
		popUpVictoire(monPlateau.gagnant(6, monPlateau.player));
		
		isYourTurn = false;
		MasquerBouton(isYourTurn);	
		}
	}

}

