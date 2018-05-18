package projet_ile;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class IVue {
	/**
	 * JFrame est une classe fournie pas Swing. Elle représente la fen��tre de
	 * l'application graphique.
	 */
	private JFrame frame;
	/**
	 * VueGrille et VueCommandes sont deux classes définies plus loin, pour nos
	 * deux parties de l'interface graphique.
	 */
	private VueGrille grille;
	private VueCommandes commandes;
	private PanneauJoueurs panneau;

	/** Construction d'une vue attachée �� un mod��le. */
	public IVue(IModele modele) {
		/** Définition de la fen��tre principale. */
		frame = new JFrame();
		frame.setTitle("Ile Interdite");
		/**
		 * On précise un mode pour disposer les différents éléments ��
		 * l'intérieur de la fen��tre. Quelques possibilités sont : -
		 * BorderLayout (défaut pour la classe JFrame) : chaque élément est
		 * disposé au centre ou le long d'un bord. - FlowLayout (défaut pour un
		 * JPanel) : les éléments sont disposés l'un �� la suite de l'autre, dans
		 * l'ordre de leur ajout, les lignes se formant de gauche �� droite et de
		 * haut en bas. Un élément peut passer �� la ligne lorsque l'on
		 * redimensionne la fen��tre. - GridLayout : les éléments sont disposés
		 * l'un �� la suite de l'autre sur une grille avec un nombre de lignes et
		 * un nombre de colonnes définis par le programmeur, dont toutes les
		 * cases ont la m��me dimension. Cette dimension est calculée en fonction
		 * du nombre de cases �� placer et de la dimension du contenant.
		 */
		frame.setLayout(new FlowLayout());
		
		Controleur ctrl = new Controleur (modele);

		/** Définition des deux vues et ajout �� la fen��tre. */
		panneau = new PanneauJoueurs(modele);
		frame.add(panneau);
		VueInstruction instruc = new VueInstruction(modele);
		frame.add(instruc);
		grille = new VueGrille(modele, ctrl);
		frame.add(grille);
		commandes = new VueCommandes(modele, ctrl);
		frame.add(commandes);
		
		

		/**
		 * Remarque : on peut passer �� la méthode [add] des param��tres
		 * supplémentaires indiquant où placer l'élément. Par exemple, si on
		 * avait conservé la disposition par défaut [BorderLayout], on aurait pu
		 * écrire le code suivant pour placer la grille �� gauche et les
		 * commandes �� droite. frame.add(grille, BorderLayout.WEST);
		 * frame.add(commandes, BorderLayout.EAST);
		 */

		/**
		 * Fin de la plomberie : - Ajustement de la taille de la fen��tre en
		 * fonction du contenu. - Indiquer qu'on quitte l'application si la
		 * fen��tre est fermée. - Préciser que la fen��tre doit bien apparaître ��
		 * l'écran.
		 */
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}