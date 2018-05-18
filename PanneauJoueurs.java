package projet_ile;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import projet_ile.Cellule.Etat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;


public class PanneauJoueurs extends JPanel implements Observer{
	
	private IModele modele;
	/** D¨¦finition d'une taille (en pixels) pour l'affichage des cellules. */
	private final static int TAILLE = 70;
	
	
	public JPanel panneauJ;
	public JLabel label;
	
	
	/** Constructeur. */
	public PanneauJoueurs(IModele modele) {
		this.modele = modele;
		/** On enregistre la vue [this] en tant qu'observateur de [modele]. */
		modele.addObserver(this);
		
		/**
		 * D¨¦finition et application d'une taille fixe pour cette zone de
		 * l'interface, calcul¨¦e en fonction du nombre de cellules et de la
		 * taille d'affichage.
		 */
		
		label = new JLabel("Panneau de Joueur", SwingConstants.LEFT);
		//label.setText("<html>First row<br>Second row</html>");
		label.setLayout(null);
		
	  
	    label.setVerticalAlignment(SwingConstants.TOP);
		//label.setOpaque(true);
		label.setBackground(Color.GREEN);
		label.setBounds(0, 0, TAILLE*3, 600);
		//label.setForeground(Color.GRAY);
		
		this.add(label);
		
		
		this.setLayout(null);
		Dimension dim = new Dimension(TAILLE * 3, TAILLE * IModele.DimY);
		this.setPreferredSize(dim);
		//this.joueur = new Cellule (modele, 3 , 3);
		//this.joueur =  new Cellule (modele,modele.joueur.posX,modele.joueur.posY);//modele.joueur.posX, modele.joueur.posY);
	    //GridBagConstraints gbc = new GridBagConstraints();
	}

	/**
	 * L'interface [Observer] demande de fournir une m¨¦thode [update], qui sera
	 * appel¨¦e lorsque la vue sera notifi¨¦e d'un changement dans le mod¨¨le. Ici
	 * on se content de r¨¦afficher toute la grille avec la m¨¦thode pr¨¦d¨¦finie
	 * [repaint].
	 */
	public void update() {
		majPossJoueurs();
	}
	
	public void majPossJoueurs() {
		label.setText(this.modele.possesionJoueur());
	}

}
