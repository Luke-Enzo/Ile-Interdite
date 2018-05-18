package projet_ile;

import projet_ile.Cellule.Etat;
import projet_ile.Joueur.deplace;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import projet_ile.Cellule.Artefact;
import java.lang.Math;
import java.util.HashSet;

public class IModele extends Observable {
	/** On fixe la taille de la grille. */
	public static final int HAUTEUR = 6, LARGEUR = 6, UN = 1;
	/** On stocke un tableau de cellules. */
	protected Cellule[][] cellules;

	public static final int DimX = LARGEUR + 2, DimY = HAUTEUR + 2;

	public int heliportX, heliportY;
	public ArrayList<Joueur> joueurs;
	public Cellule heliport;
	public HashSet<Artefact> artefactsObtenus;

	/** Construction : on initialise un tableau de cellules. */
	public IModele() {
		/**
		 * Pour éviter les problèmes aux bords, on ajoute une ligne et une
		 * colonne de chaque côté, dont les cellules n'évolueront pas.
		 */
		cellules = new Cellule[DimX][DimY];
		for (int i = 1; i <= LARGEUR; i++) {
			for (int j = 1; j <= HAUTEUR; j++) {
				cellules[i][j] = new Cellule(this, i, j);
			}
		}

		// pour les 4 bords
		for (int i = 0; i < IModele.DimX; i++) {
			cellules[i][0] = new Cellule(this, i, 0);
			cellules[i][0].toBords();
		}

		for (int i = 0; i < IModele.DimX; i++) {
			cellules[i][DimY - 1] = new Cellule(this, i, DimY - 1);
			cellules[i][DimY - 1].toBords();
		}

		for (int j = 0; j < IModele.DimY; j++) {
			cellules[0][j] = new Cellule(this, 0, j);
			cellules[0][j].toBords();
		}

		for (int j = 0; j < IModele.DimY; j++) {
			cellules[DimX - 1][j] = new Cellule(this, DimX - 1, j);
			cellules[DimX - 1][j].toBords();
		}

		this.joueurs = new ArrayList<Joueur>(4);
		init();

		this.heliportX = 1 + (int) (Math.random() * ((LARGEUR - 1) + 1));
		this.heliportY = 1 + (int) (Math.random() * ((HAUTEUR - 1) + 1));

		cellules[this.heliportX][this.heliportY].isHeliport = true;
		this.artefactsObtenus = new HashSet<Artefact>();

	}

	public void addJoueur() {
		for (int i = 0; i < 2; i++) {
			Joueur j = new Joueur(this, this.heliportX, this.heliportY);
			this.joueurs.add(j);
		}
		notifyObservers();
	}

	public Joueur getJoueur(int i) {
		return this.joueurs.get(i);
	}

	public String toString() {
		String s = "";
		for (int i = 1; i <= LARGEUR; i++) {
			for (int j = 1; j <= HAUTEUR; j++) {
				s += cellules[i][j].toString() + "\n";
			}
		}
		return s;
	}

	/**
	 * Initialisation aléatoire des cellules, exceptées celle des bords qui ont
	 * été ajoutés.
	 */
	public void init() {
		for (int i = 1; i <= LARGEUR; i++) {
			for (int j = 1; j <= HAUTEUR; j++) {
				cellules[i][j].etat = Etat.NORMALE;
			}
		}

		initArtefact();
		initCle();
	}

	public void initArtefact() {

		int x = 1 + (int) (Math.random() * ((LARGEUR - 1) + 1));
		int y = 1 + (int) (Math.random() * ((HAUTEUR - 1) + 1));

		for (int i = 0; i < 4; i++) {

			while (cellules[x][y].hasArtefact == true) {
				x = 1 + (int) (Math.random() * ((LARGEUR - 1) + 1));
				y = 1 + (int) (Math.random() * ((HAUTEUR - 1) + 1));
			}

			assert cellules[x][y].hasArtefact == false;
			cellules[x][y].genererArtefact(i);
			assert cellules[x][y].hasArtefact == true;

			System.out.println(Joueur.switchString(cellules[x][y].artefact) + " " + x + " " + y);
		}
	}

	public void initCle() {

		int x = 1 + (int) (Math.random() * ((LARGEUR - 1) + 1));
		int y = 1 + (int) (Math.random() * ((HAUTEUR - 1) + 1));

		for (int i = 0; i < 4; i++) {

			while (cellules[x][y].hasCle == true) {
				x = 1 + (int) (Math.random() * ((LARGEUR - 1) + 1));
				y = 1 + (int) (Math.random() * ((HAUTEUR - 1) + 1));
			}

			assert cellules[x][y].hasCle == false;
			cellules[x][y].genererCle(i);
			assert cellules[x][y].hasCle == true;
			System.out.println(Joueur.switchString(cellules[x][y].cle) + x + " " + y);
		}
	}

	public void inonAleatoire() {

		int x = 1 + (int) (Math.random() * ((LARGEUR - 1) + 1));
		int y = 1 + (int) (Math.random() * ((HAUTEUR - 1) + 1));

		while (this.cellules[x][y].etat == Etat.SUBMERGEE || !inonValide(x, y) || this.cellules[x][y].hasArtefact) {

			x = 1 + (int) (Math.random() * ((LARGEUR - 1) + 1));
			y = 1 + (int) (Math.random() * ((HAUTEUR - 1) + 1));

		}

		if (this.cellules[x][y].etat == Etat.INONDEE) {
			this.cellules[x][y].etat = Etat.SUBMERGEE;
		} else {
			this.cellules[x][y].etat = Etat.INONDEE;
		}
	}

	public boolean inonValide(int x, int y) {
		for (Joueur j : this.joueurs) {
			if (j.posX == x && j.posY == y) {
				return false;
			}
		}
		return true;
	}

	public void chercherCle(Joueur j) {
		if (this.cellules[j.posX][j.posY].hasCle) {
			j.cles.add(this.cellules[j.posX][j.posY].cle);
			this.cellules[j.posX][j.posY].cle = null;
			this.cellules[j.posX][j.posY].hasCle = false;
		} else {
			int a = (int) (Math.random() * 3);
			if (a == 2) {
				if (this.cellules[j.posX][j.posY].etat == Etat.INONDEE) {
					this.cellules[j.posX][j.posY].etat = Etat.SUBMERGEE;
				} else {
					this.cellules[j.posX][j.posY].etat = Etat.INONDEE;
				}
			}
		}
		notifyObservers();
	}

	public void finDeTour(Joueur j) {

		for (int i = 0; i < 3; i++) {
			inonAleatoire();
		}

		j.recupcles();
		if (this.cellules[j.posX][j.posX].etat == Etat.SUBMERGEE) {

		}

		notifyObservers();

	}

	public void bouger(deplace d, Joueur j) {

		j.deplacer(d);
		notifyObservers();

	}

	public void recuperer(Joueur j, Cellule c) {

		if (c.hasArtefact) {
			Artefact a = c.artefact;
			if (j.cles.contains(a)) {
				j.recupArte(a);
				this.artefactsObtenus.add(a);
				c.hasArtefact = false;
				c.artefact = null;
			}
		}
		// System.out.println("We should do something here");
		notifyObservers();

	}

	public void secher(Cellule c) {

		if (c.etat == Etat.INONDEE) {
			this.cellules[c.getX()][c.getY()].etat = Etat.NORMALE;
		}

		if (c.etat == Etat.SUBMERGEE) {
			this.cellules[c.getX()][c.getY()].etat = Etat.INONDEE;
		}

		notifyObservers();
	}

	/**
	 * Calcul de la génération suivante.
	 */

	public void avance() {
		/**
		 * On procède en deux étapes. - D'abord, pour chaque cellule on évalue
		 * ce que sera son état à la prochaine génération. - Ensuite, on
		 * applique les évolutions qui ont été calculées.
		 **/
		for (int i = 1; i < LARGEUR + 1; i++) {
			for (int j = 1; j < HAUTEUR + 1; j++) {
				// cellules[i][j].evalue();
			}
		}
		for (int i = 1; i < LARGEUR + 1; i++) {
			for (int j = 1; j < HAUTEUR + 1; j++) {
				// cellules[i][j].evolue();
			}
		}
		/**
		 * Pour finir, le modèle ayant changé, on signale aux observateurs
		 * qu'ils doivent se mettre à jour.
		 **/
		notifyObservers();
	}

	/**
	 * Méthode auxiliaire : compte le nombre de voisines vivantes d'une cellule
	 * désignée par ses coordonnées.
	 */

	/**
	 * protected int compteVoisines(int x, int y) { int res = 0; /** Stratégie
	 * simple à écrire : on compte les cellules vivantes dans le carré 3x3
	 * centré autour des coordonnées (x, y), puis on retire 1 si la cellule
	 * centrale est elle-même vivante. On n'a pas besoin de traiter à part les
	 * bords du tableau de cellules gr芒ce aux lignes et colonnes supplémentaires
	 * qui ont été ajoutées de chaque côté (dont les cellules sont mortes et
	 * n'évolueront pas).
	 **/
	/**
	 * for (int i = x - 1; i <= x + 1; i++) { for (int j = y - 1; j <= y + 1;
	 * j++) { if (cellules[i][j].etat) { res++; } } } return (res -
	 * ((cellules[x][y].etat) ? 1 : 0));
	 **/
	/**
	 * L'expression [(c)?e1:e2] prend la valeur de [e1] si [c] vaut [true] et
	 * celle de [e2] si [c] vaut [false]. Cette dernière ligne est donc
	 * équivalente à int v; if (cellules[x][y].etat) { v = res - 1; } else { v =
	 * res - 0; } return v;
	 **/
	// }

	/**
	 * Une méthode pour renvoyer la cellule aux coordonnées choisies (sera
	 * utilisée par la vue).
	 */
	public Cellule getCellule(int x, int y) {
		return cellules[x][y];
	}

	public String possesionJoueur() {
		// "<html>First row<br>Second row</html>"
		String s = "";
		if (this.joueurs.isEmpty()) {
			return "";
		} else {
			s += "<html>";
			for (Joueur j : this.joueurs) {
				s += "Joueur " + (this.joueurs.indexOf(j) + 1);
				s += j.printcles();
				s += j.printArte();
				s += "<br>";
			}
			s += "</html>";
		}

		return s;
	}

	public String instrJoueurs() {
		String s = "";
		if (this.joueurs.isEmpty()) {
			return "";
		} else {
			s += "<html>";
			for (Joueur j : this.joueurs) {
				s += "Joueur " + (this.joueurs.indexOf(j) + 1);
				s += j.printInstr();
				s += "<br>";
				s += "<br>";
			}
			s += "</html>";
		}

		return s;
	}

	public void helicopter(int x, int y, int index) {
		Joueur j = this.getJoueur(index);
		j.posX = x;
		j.posY = y;
		notifyObservers();
	}

	public void echapper(int index, int x, int y) {
		Joueur j = this.getJoueur(index);

		if (this.cellules[j.posX][j.posY].etat == Etat.SUBMERGEE) {
			if (abs(x - j.posX) <= 1 && abs(y - j.posY) <= 1 && x >= 1 && x <= this.LARGEUR && y >= 1
					&& y <= this.HAUTEUR) {
				j.posX = x;
				j.posY = y;
			}
		}
		if (this.cellules[j.posX][j.posY].etat == Etat.SUBMERGEE) {
			j.mort = true;
			System.out.println("You lost");
		}

	}

	private int abs(int i) {
		if (i < 0) {
			return -i;
		}
		return i;
	}

	public boolean gameOver() {
		if (this.cellules[this.heliportX][this.heliportY].etat == Etat.SUBMERGEE) {
			return true;
		}

		for (Joueur j : this.joueurs) {
			if (this.cellules[j.posX][j.posY].etat == Etat.SUBMERGEE) {
				return true;
			}
		}

		return false;
	}

	public void reset() {
		for (int i = 1; i <= LARGEUR; i++) {
			for (int j = 1; j <= HAUTEUR; j++) {
				cellules[i][j].etat = Etat.NORMALE;
			}
		}
	}

	public boolean win() {
		if (this.artefactsObtenus.contains(Artefact.AIR) && this.artefactsObtenus.contains(Artefact.FEU)
				&& this.artefactsObtenus.contains(Artefact.TERRE) && this.artefactsObtenus.contains(Artefact.EAU)) {
			for (Joueur j : this.joueurs) {
				if (!(j.posX == this.heliportX && j.posY == this.heliportY)) {
					return false;
				}
			return true;
			}
		}
		return false;

		
	}

	/**
	 * Notez qu'à l'intérieur de la classe [CModele], la classe interne est
	 * connue sous le nom abrégé [Cellule]. Son nom complet est
	 * [CModele.Cellule], et cette version complète est la seule à pouvoir être
	 * utilisée depuis l'extérieur de [CModele]. Dans [CModele], les deux
	 * fonctionnent.
	 */
}