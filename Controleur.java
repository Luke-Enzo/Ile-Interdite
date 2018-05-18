package projet_ile;

import projet_ile.Cellule.Etat;
import projet_ile.Joueur.deplace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JLabel;

public class Controleur implements ActionListener, MouseListener {
	/**
	 * On garde un pointeur vers le modèle, car le contrôleur doit provoquer un
	 * appel de méthode du modèle. Remarque : comme cette classe est interne,
	 * cette inscription explicite du modèle est inutile. On pourrait se
	 * contenter de faire directement référence au modèle enregistré pour la
	 * classe englobante [VueCommandes].
	 */
	IModele modele;

	public int currentJoueurIndex = 0;
	private boolean sacPressed;
	private boolean heliPressed;
	private boolean finDeTourPressed;
	private boolean chercherPressed;
	private boolean ajoutPressed;
	VueCommandes commandes;
	JFrame frame;

	public Controleur(IModele modele) {
		this.modele = modele;

		this.sacPressed = false;
		this.heliPressed = false;
		this.finDeTourPressed = false;
		this.chercherPressed = false;
		this.ajoutPressed = false;
	}

	/**
	 * Action effectuée à réception d'un événement : appeler la méthode [avance]
	 * du modèle.
	 */
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();

		if (command.equals("ajout") && !this.ajoutPressed) {
			this.modele.addJoueur();
			this.ajoutPressed = true;
		}

		Joueur j = this.modele.getJoueur(currentJoueurIndex);
		j.hasChoice = true;

		if (command.equals("gauche")) {
			modele.bouger(deplace.GAUCHE, j);
			// System.out.println(modele.joueur.posX + command);
		}

		if (command.equals("droite")) {
			modele.bouger(deplace.DROITE, j);
		}

		if (command.equals("haut")) {
			modele.bouger(deplace.HAUT, j);
		}

		if (command.equals("bas")) {
			modele.bouger(deplace.BAS, j);
		}

		if (command.equals("secher")) {
			// modele.secher(this.modele.cellules[1][1]);
			// closeThis();
			// 创建新的窗口
			/*
			 * JFrame frame = new JFrame("新窗口"); // 设置在屏幕的位置
			 * frame.setLocation(100, 50); // 窗体大小 frame.setSize(500, 500); //
			 * 显示窗体 frame.setVisible(true);
			 * 
			 * JButton jb = new JButton("j"); frame.add(jb);
			 * jb.addActionListener(this); jb.setActionCommand("jb");
			 */
			if (j.dejaAssecher == false) {
				fenetre();
			} else {
				System.out.println("Vous avez deja asseche !");
			}

		}

		if (command.equals("C")) {
			j.dejaAssecher = true;
			modele.secher(modele.cellules[j.posX][j.posY]);
			frame.dispose();
		}

		if (command.equals("G")) {
			if (j.posX - 1 >= 1) {
				j.dejaAssecher = true;
				modele.secher(modele.cellules[j.posX - 1][j.posY]);
				frame.dispose();
			}

		}

		if (command.equals("H")) {
			if (j.posY - 1 >= 1) {
				j.dejaAssecher = true;
				modele.secher(modele.cellules[j.posX][j.posY - 1]);
				frame.dispose();
			}
		}

		if (command.equals("B")) {
			if (j.posY + 1 <= this.modele.HAUTEUR) {
				j.dejaAssecher = true;
				modele.secher(modele.cellules[j.posX][j.posY + 1]);
				frame.dispose();
			}
		}

		if (command.equals("D")) {
			if (j.posX + 1 <= this.modele.LARGEUR) {
				j.dejaAssecher = true;
				modele.secher(modele.cellules[j.posX + 1][j.posY]);
				frame.dispose();
			}
		}
		

		if (command.equals("recupe")) {
			if (j.dejaRecupere == false) {
				j.dejaRecupere = true;
				modele.recuperer(j, modele.cellules[j.posX][j.posY]);
				
			} else {
				System.out.println("Vous avez deja recuperer !");
			}	
		}
		
		if (command.equals("chercher")) {
			this.chercherPressed = true;
			if (j.dejaChercher == false) {
				j.dejaChercher = true;
				
				int a = (int) (Math.random() * 2);
				if (a == 1) {
					j.dejaSac = false;
				}
				a = (int) (Math.random() * 2);
				if (a == 1) {
					j.dejaHelicop = false;
				}
				
				modele.chercherCle(j);
			
				//System.out.println("yes");
			} else {
				System.out.println("Vous avez deja cherche !");
			}
		}

		if (command.equals("finDeTour")) {
			this.finDeTourPressed = true;
			j.dejaDeplacer = false;
			j.dejaAssecher = false;
			j.dejaRecupere = false;
			j.dejaChercher = false;
			modele.finDeTour(j);
			
	
			currentJoueurIndex = (currentJoueurIndex + 1) % this.modele.joueurs.size();
			System.out.println("A votre tour, joueur " + (currentJoueurIndex + 1));
			
		}
		
		if (command.equals("sac") && j.dejaSac == false) {
			this.sacPressed = true;
		}

		if (command.equals("heli") && j.dejaHelicop == false) {
			this.heliPressed = true;
		}
		
		if (modele.gameOver()) {
			System.out.println("Game Over");
			end();
			//System.exit(0);
		}
		
		if (modele.win()) {
			System.out.println("YOU WIN");
			win();
			//System.exit(0);
		}
		


		// j = this.modele.getJoueur(indiceJ);
		// System.out.println("À votre tour, joueur " + (indiceJ+1));

	}
	
	public void win() {
		frame = new JFrame("WIN ");
	
		frame.setLocation(100, 50);
	
		frame.setSize(400, 400);
	
		frame.setVisible(true);
		frame.setLayout(null);
		JLabel label = new JLabel ("WIN");
		
		frame.add(label);
		
	}
	
	public void end() {
		frame = new JFrame("Game Over ");
		// 设置在屏幕的位置
		frame.setLocation(100, 50);
		// 窗体大小
		frame.setSize(400, 400);
		// 显示窗体
		frame.setVisible(true);
		frame.setLayout(null);
		JLabel label = new JLabel ("Game Over");
		
		frame.add(label);
		
		
	}

	public void fenetre() {

		frame = new JFrame("choisir une case adjacente. ");
		// 设置在屏幕的位置
		frame.setLocation(100, 50);
		// 窗体大小
		frame.setSize(400, 400);
		// 显示窗体
		frame.setVisible(true);
		frame.setLayout(null);

		JButton H = new JButton("↑");
		JButton B = new JButton("↓");
		JButton G = new JButton("←");
		JButton D = new JButton("→");
		JButton C = new JButton("O");

		C.setSize(60, 60);
		C.setLocation(150, 150);

		H.setSize(60, 60);
		H.setLocation(150, 50);

		B.setSize(60, 60);
		B.setLocation(150, 250);

		G.setSize(60, 60);
		G.setLocation(50, 150);

		D.setSize(60, 60);
		D.setLocation(250, 150);

		frame.add(H);

		frame.add(C);
		frame.add(G);
		frame.add(B);
		frame.add(D);

		H.addActionListener(this);
		B.addActionListener(this);
		G.addActionListener(this);
		D.addActionListener(this);
		C.addActionListener(this);

		H.setActionCommand("H");
		B.setActionCommand("B");
		G.setActionCommand("G");
		D.setActionCommand("D");
		C.setActionCommand("C");
	}

	@Override

	public void mouseReleased(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {

		if (this.sacPressed && modele.getJoueur(currentJoueurIndex).dejaSac == false) {
			int x = VueGrille.toCelluleCoor(e.getX());
			int y = VueGrille.toCelluleCoor(e.getY());
			
			this.sacPressed = false;
			modele.getJoueur(currentJoueurIndex).dejaSac = true;
			// System.out.println(e.getX());
			// System.out.println(e.getY());
			modele.secher(modele.cellules[x][y]);
			// System.out.println(x);
			// System.out.println(y);
			// System.out.println(modele.cellules[x][y].switchString(modele.cellules[x][y].etat));
		
		}

		if (this.heliPressed && modele.getJoueur(currentJoueurIndex).dejaHelicop == false) {
			int x = VueGrille.toCelluleCoor(e.getX());
			int y = VueGrille.toCelluleCoor(e.getY());
			this.heliPressed = false;
			modele.getJoueur(currentJoueurIndex).dejaHelicop = true;
			
			modele.helicopter(x, y, currentJoueurIndex);
			
			// System.out.println("Already pressed");
		}

		if (this.finDeTourPressed && modele.getJoueur(currentJoueurIndex).hasChoice) {
			
			int x = VueGrille.toCelluleCoor(e.getX());
			int y = VueGrille.toCelluleCoor(e.getY());
			modele.echapper(currentJoueurIndex, x, y);
			modele.getJoueur(currentJoueurIndex).hasChoice = false;
			this.finDeTourPressed = false;
			
			if (modele.gameOver() && modele.getJoueur(currentJoueurIndex).hasChoice) {
				System.out.println("Game Over");
				modele.reset();
			}
		}
		
		if (this.chercherPressed && 
				modele.cellules[modele.getJoueur(currentJoueurIndex).posX]
						[modele.getJoueur(currentJoueurIndex).posY].etat 
						== Etat.SUBMERGEE) {
			int x = VueGrille.toCelluleCoor(e.getX());
			int y = VueGrille.toCelluleCoor(e.getY());
			modele.echapper(currentJoueurIndex, x, y);
			this.chercherPressed = false;
			
		}

	}

}