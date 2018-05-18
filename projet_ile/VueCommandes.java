package projet_ile;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.JFrame;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class VueCommandes extends JPanel {
	/**
	 * Pour que le bouton puisse transmettre ses ordres, on garde une référence
	 * au modèle.
	 */
	private IModele modele;
	private Controleur ctrl;

	/** Constructeur. */
	public VueCommandes(IModele modele, Controleur ctrl) {
		this.modele = modele;
		this.ctrl = ctrl;
		/**
		 * On crée un nouveau bouton, de classe [JButton], en précisant le texte
		 * qui doit l'étiqueter. Puis on ajoute ce bouton au panneau [this].
		 */
		JButton finDeTour = new JButton("fin de tour");

		JButton gauche = new JButton("←");
		JButton droite = new JButton("→");
		JButton haut = new JButton("↑");
		JButton bas = new JButton("↓");
		JButton ajout = new JButton("Ajouter joueur");
		JButton secher = new JButton("Assecher");
		JButton recupe = new JButton("Recupérer");
		JButton sac = new JButton("Sac de Sable");
		JButton heli = new JButton("Hélicoptère");
		JButton chercher = new JButton("Chercher une cle");
		
		this.add(finDeTour);
		this.add(gauche);
		this.add(droite);
		this.add(bas);
		this.add(haut);
		this.add(ajout);
		this.add(secher);
		this.add(recupe);
		this.add(sac);
		this.add(heli);
		this.add(chercher);

		Box box = Box.createVerticalBox();

		box.add(ajout);
		box.add(Box.createVerticalStrut(20));

		box.add(haut);
		box.add(Box.createVerticalStrut(10));
		box.add(gauche);
		box.add(Box.createVerticalStrut(10));
		box.add(droite);
		box.add(Box.createVerticalStrut(10));
		box.add(bas);
		box.add(Box.createVerticalStrut(10));
		box.add(recupe);
		box.add(Box.createVerticalStrut(10));
		box.add(secher);
		box.add(Box.createVerticalStrut(20));
		box.add(chercher);
		box.add(Box.createVerticalStrut(20));
		box.add(finDeTour);
		box.add(Box.createVerticalStrut(20));
		box.add(sac);
		box.add(Box.createVerticalStrut(20));
		box.add(heli);
		
		add(box);
		/**
		 * Le bouton, lorsqu'il est cliqué par l'utilisateur, produit un
		 * événement, de classe [ActionEvent].
		 *
		 * On a ici une variante du schéma observateur/observé : un objet
		 * implémentant une interface [ActionListener] va s'inscrire pour
		 * "écouter" les événements produits par le bouton, et recevoir
		 * automatiquements des notifications. D'autres variantes d'auditeurs
		 * pour des événements particuliers : [MouseListener],
		 * [KeyboardListener], [WindowListener].
		 *
		 * Cet observateur va enrichir notre schéma Modèle-Vue d'une couche
		 * intermédiaire Contrôleur, dont l'objectif est de récupérer les
		 * événements produits par la vue et de les traduire en instructions
		 * pour le modèle. Cette strate intermédiaire est potentiellement riche,
		 * et peut notamment traduire les mêmes événements de différentes fa莽ons
		 * en fonction d'un état de l'application. Ici nous avons un seul bouton
		 * réalisant une seule action, notre contrôleur sera donc
		 * particulièrement simple. Cela nécessite néanmoins la création d'une
		 * classe dédiée.
		 */
		/** Enregistrement du contrôleur comme auditeur du bouton. */

		
		finDeTour.addActionListener(ctrl);
		finDeTour.setActionCommand("finDeTour");
		
		gauche.addActionListener(ctrl);
		gauche.setActionCommand("gauche");

		droite.addActionListener(ctrl);
		droite.setActionCommand("droite");

		haut.addActionListener(ctrl);
		haut.setActionCommand("haut");

		bas.addActionListener(ctrl);
		bas.setActionCommand("bas");

		ajout.addActionListener(ctrl);
		ajout.setActionCommand("ajout");

		secher.addActionListener(ctrl);
		secher.setActionCommand("secher");

		recupe.addActionListener(ctrl);
		recupe.setActionCommand("recupe");

		sac.addActionListener(ctrl);
		//sac.addMouseListener(ctrl);
		sac.setActionCommand("sac");
		
		heli.addActionListener(ctrl);
		heli.setActionCommand("heli");
		
		chercher.addActionListener(ctrl);
		chercher.setActionCommand("chercher");
		
		/**
		 * Variante : une lambda-expression qui évite de créer une classe
		 * spécifique pour un contrôleur simplissime.
		 *
		 * JButton boutonAvance = new JButton(">"); this.add(boutonAvance);
		 * boutonAvance.addActionListener(e -> { modele.avance(); });
		 *
		 */

	}
	
	
}