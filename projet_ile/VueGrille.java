package projet_ile;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import projet_ile.Cellule.Etat;
import projet_ile.Cellule.Artefact;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JLabel;

public class VueGrille extends JPanel implements Observer {
	/** On maintient une r¨¦f¨¦rence vers le mod¨¨le. */
	private IModele modele;
	/** D¨¦finition d'une taille (en pixels) pour l'affichage des cellules. */
	private final static int TAILLE = 70;

	public ArrayList<Cellule> js;
	public JPanel panneauJ;
	public JLabel label;
	private Controleur ctrl;

	/** Constructeur. */
	public VueGrille(IModele modele, Controleur ctrl) {
		this.modele = modele;
		this.ctrl = ctrl;
		/** On enregistre la vue [this] en tant qu'observateur de [modele]. */
		modele.addObserver(this);

		/**
		 * D¨¦finition et application d'une taille fixe pour cette zone de
		 * l'interface, calcul¨¦e en fonction du nombre de cellules et de la
		 * taille d'affichage.
		 */
		/*
		 * label = new JLabel("Joueur"); label.setLayout(null);
		 * label.setOpaque(true); label.setBackground(Color.GREEN);
		 * label.setBounds(0, 0, 100, 100); label.setForeground(Color.GRAY);
		 * this.add(label);
		 */

		this.setLayout(null);
		Dimension dim = new Dimension(TAILLE * IModele.DimX, TAILLE * IModele.DimY);
		this.setPreferredSize(dim);
		// this.joueur = new Cellule (modele, 3 , 3);
		// this.joueur = new Cellule
		// (modele,modele.joueur.posX,modele.joueur.posY);//modele.joueur.posX,
		// modele.joueur.posY);
		this.js = new ArrayList<Cellule>();
		this.addMouseListener(ctrl);
	}

	/**
	 * L'interface [Observer] demande de fournir une m¨¦thode [update], qui sera
	 * appel¨¦e lorsque la vue sera notifi¨¦e d'un changement dans le mod¨¨le. Ici
	 * on se content de r¨¦afficher toute la grille avec la m¨¦thode pr¨¦d¨¦finie
	 * [repaint].
	 */
	public void update() {
		repaint();
		// majPossJoueurs();
	}

	public void majPossJoueurs() {
		label.setText(this.modele.possesionJoueur());
	}

	/**
	 * Les ¨¦l¨¦ments graphiques comme [JPanel] poss¨¨dent une m¨¦thode
	 * [paintComponent] qui d¨¦finit l'action ¨¤ accomplir pour afficher cet
	 * ¨¦l¨¦ment. On la red¨¦finit ici pour lui confier l'affichage des cellules.
	 *
	 * La classe [Graphics] regroupe les ¨¦l¨¦ments de style sur le dessin, comme
	 * la couleur actuelle.
	 */

	public void paintComponent(Graphics g) {
		super.repaint();
		/** Pour chaque cellule... */
		for (int i = 0; i < IModele.DimX; i++) {
			g.setColor(Color.WHITE);
			g.fillRect(i * TAILLE, 0, TAILLE, TAILLE);
		}

		for (int i = 0; i < IModele.DimX; i++) {
			g.setColor(Color.WHITE);
			g.fillRect(i * TAILLE, (IModele.DimY - 1) * TAILLE, TAILLE, TAILLE);
		}

		for (int j = 0; j < IModele.DimY; j++) {
			g.setColor(Color.WHITE);
			g.fillRect(0, j * TAILLE, TAILLE, TAILLE);
		}

		for (int j = 0; j < IModele.DimY; j++) {
			g.setColor(Color.WHITE);
			g.fillRect((IModele.DimX - 1) * TAILLE, j * TAILLE, TAILLE, TAILLE);
		}

		for (int i = 1; i <= IModele.LARGEUR; i++) {
			for (int j = 1; j <= IModele.HAUTEUR; j++) {
				/**
				 * ... Appeler une fonction d'affichage auxiliaire. On lui
				 * fournit les informations de dessin [g] et les coordonn¨¦es du
				 * coin en haut ¨¤ gauche.
				 */
				paint(g, modele.cellules[i][j], i * TAILLE, j * TAILLE);
				if (modele.cellules[i][j].isHeliport) {
					g.drawString("H", i * TAILLE + (int) (0.1* TAILLE), j * TAILLE + (int) (0.2 * TAILLE));
				}
				paintArtefact(g, modele.cellules[i][j]);
			}
		}

		for (Joueur el : this.modele.joueurs) {
			paintJoueur(g, el, this.modele.joueurs.indexOf(el));
		}
		
		

		// g.drawString("haha",200, 200);
		possesionJoueur(g);
		// paint(g, joueur, this.joueur.getX()*TAILLE,
		// this.joueur.getY()*TAILLE);
	}

	public void paintJoueur(Graphics g, Joueur j, int i) {
		g.setColor(Color.GREEN);
		g.fillOval(j.posX * TAILLE + (int) (0.25 * TAILLE), j.posY * TAILLE + (int) (0.25 * TAILLE),
				(int) (0.5 * TAILLE), (int) (0.5 * TAILLE));
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(i+1),j.posX * TAILLE + (int) (0.5 * TAILLE), j.posY * TAILLE + (int) (0.5 * TAILLE));
		// System.out.println(this.joueur.getX());
		// System.out.println(this.joueur.getY());
		// g.fillRect(this.joueur.getX()+(int)0.25*TAILLE,
		// this.joueur.getY()+(int)0.25*TAILLE
		// , (int)0.5*TAILLE, (int)0.5*TAILLE);
	}

	public void ajouteJoueur(Joueur j) {
		Cellule c = new Cellule(this.modele, j.posX, j.posY);
		this.js.add(c);
	}

	public void possesionJoueur(Graphics g) {
		g.drawString(this.modele.possesionJoueur(), 20, 580);

	}

	/**
	 * Fonction auxiliaire de dessin d'une cellule. Ici, la classe [Cellule] ne
	 * peut ¨ºtre d¨¦sign¨¦e que par l'interm¨¦diaire de la classe [CModele] ¨¤
	 * laquelle elle est interne, d'ou le type [CModele.Cellule]. Ceci serait
	 * impossible si [Cellule] ¨¦tait d¨¦clar¨¦e priv¨¦e dans [CModele].
	 */

	private void paint(Graphics g, Cellule c, int x, int y) {
		// S¨¦lection d'une couleur.
		if (c.etat == Etat.NORMALE) {
			g.setColor(Color.YELLOW);
		} else if (c.etat == Etat.INONDEE) {
			g.setColor(Color.BLUE);
		} else {
			g.setColor(Color.GRAY);
		}
		// Coloration d'un rectangle.
		g.fillRect(x, y, TAILLE, TAILLE);
		g.setColor(Color.BLACK);
		g.fillRect(x, y, TAILLE, 1);
		g.fillRect(x, y, 1, TAILLE);
		g.fillRect(x + TAILLE, y, 1, TAILLE);
		g.fillRect(x, y + TAILLE, TAILLE, 1);

		// paintJoueur(g);
	}
	
	private void paintArtefact(Graphics g, Cellule c) {
		if (c.hasArtefact) {
			Artefact a = c.artefact;
			switch (a) {
			
			case AIR:
				g.setColor(Color.PINK);
				break;

			case FEU:
				g.setColor(Color.ORANGE);
				break;
			case TERRE:
				g.setColor(Color.DARK_GRAY);
				break;
			case EAU:
				g.setColor(Color.CYAN);
				break;
			}
			g.fillRect(c.getX() * TAILLE + (int) (0.25 * TAILLE), c.getY() * TAILLE + (int) (0.25 * TAILLE),
					(int) (0.5 * TAILLE), (int) (0.5 * TAILLE));
		}
	}

	public static int toCelluleCoor(int pixel) {

		return (int) (pixel / TAILLE);

	}

}