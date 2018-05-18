package projet_ile;

import projet_ile.Cellule.Etat;
import projet_ile.Cellule.Artefact;
import projet_ile.IModele;
import java.util.ArrayList;
import java.util.HashSet;

public class Joueur {

	public int posX, posY;
	public IModele modele;
	public deplace d;
	public boolean mort;
	public boolean dejaDeplacer;
	public boolean dejaChercher;
	public boolean dejaRecupere;
	public boolean dejaAssecher;
	public boolean dejaHelicop;
	public boolean dejaSac;
	public boolean hasChoice;
	protected HashSet<Artefact> cles;
	protected HashSet<Artefact> artefacts;

	enum deplace {
		GAUCHE, DROITE, HAUT, BAS
	}

	public Joueur(IModele modele, int x, int y) {
		this.posX = x;
		this.posY = y;
		this.modele = modele;
		this.d = null;
		this.mort = false;
		this.dejaDeplacer = false;
		this.dejaAssecher = false;
		this.dejaRecupere = false;
		this.dejaChercher = false;
		this.dejaHelicop = true;
		this.dejaSac = true;
		this.hasChoice = true;
		this.cles = new HashSet<Artefact>();
		this.artefacts = new HashSet<Artefact>();
	}

	public ArrayList<Cellule> cellulesAdjacentes() {

		// convention:
		// indice 0: centre, 1: gauche, 2: haut, 3: bas, 4: droite

		ArrayList<Cellule> res = new ArrayList<Cellule>(5);

		res.add(new Cellule(this.modele, this.posX, this.posY));

		if (this.posX - 1 >= 1) {
			res.add(this.modele.cellules[this.posX][this.posY]);
		} else {
			res.add(new Cellule(this.modele, -1, -1));
		}

		if (this.posY - 1 >= 1) {
			res.add(this.modele.cellules[this.posX][this.posY]);
		} else {
			res.add(new Cellule(this.modele, -1, -1));
		}

		if (this.posY + 1 <= this.modele.HAUTEUR) {
			res.add(this.modele.cellules[this.posX][this.posY]);
		} else {
			res.add(new Cellule(this.modele, -1, -1));
		}

		if (this.posX + 1 <= this.modele.HAUTEUR) {
			res.add(this.modele.cellules[this.posX][this.posY]);
		} else {
			res.add(new Cellule(this.modele, -1, -1));
		}

		// System.out.println(res.size());

		return res;
	}

	public void recupcles() {
		
		int a = (int) (Math.random() * 2);
		/*
		if (a == 1) {
			int r = (int) (Math.random() * 4);
			if (r == 0) {
				this.cles.add(Artefact.AIR);
				System.out.println(1);
			}
			if (r == 1) {
				this.cles.add(Artefact.EAU);
				System.out.println(2);

			}
			if (r == 2) {
				this.cles.add(Artefact.FEU);
				System.out.println(3);

			}
			if (r == 3) {
				this.cles.add(Artefact.TERRE);
				System.out.println(4);

			}
		}
		*/

		
	
		if (a == 1) {
			int r = (int) (Math.random() * 4);
			if (r == 0) {
				this.cles.add(Artefact.AIR);
				System.out.println(1);
			}
			if (r == 1) {
				this.cles.add(Artefact.EAU);
				System.out.println(2);

			}
			if (r == 2) {
				this.cles.add(Artefact.FEU);
				System.out.println(3);

			}
			if (r == 3) {
				this.cles.add(Artefact.TERRE);
				System.out.println(4);

			}
		}
	}

	public void recupArte(Artefact a) {
		this.artefacts.add(a);
	}
	

	public void deplacer(deplace d) {
		/**
		 * if (d == deplace.GAUCHE) { if ((this.posX - 1) >= 1) {
		 * //System.out.println(this.posX); this.posX -- ;
		 * //System.out.println(this.posX + "ssssssss"); } }
		 * 
		 * if (d == deplace.DROITE) { if (this.posX + 1 <= this.modele.LARGEUR)
		 * { this.posX += 1; //System.out.println("kkkkkk"); } }
		 * 
		 * if (d == deplace.HAUT) { if ((this.posY - 1) >= 1) {
		 * //System.out.println(this.posY); this.posY --;
		 * //System.out.println(this.posY + "ssssssss");
		 * //System.out.println(this.posY); } }
		 * 
		 * if (d == deplace.BAS) { if (this.posY + 1 <= this.modele.HAUTEUR) {
		 * this.posY += 1; } }
		 **/

		switch (d) {
		case GAUCHE:
			if ((this.posX - 1) >= 1) {
				// System.out.println(this.posX);
				if (this.modele.cellules[this.posX - 1][this.posY].etat != Etat.SUBMERGEE
						&& this.dejaDeplacer == false) {
					this.posX--;
					this.dejaDeplacer = true;
				} else {
					if (this.dejaDeplacer == true) {
						System.out.println("Vous avez deja deplace !!");
					} else {
						System.out.println("Case submergee, deplacement non-valide");
					}
				}
				// System.out.println(this.posX + "ssssssss");
			}
			break;

		case DROITE:
			if (this.posX + 1 <= this.modele.LARGEUR) {
				if (this.modele.cellules[this.posX + 1][this.posY].etat != Etat.SUBMERGEE
						&& this.dejaDeplacer == false) {
					this.posX++;
					this.dejaDeplacer = true;
				} else {
					if (this.dejaDeplacer == true) {
						System.out.println("Vous avez deja deplace !!");
					} else {
						System.out.println("Case submergee, deplacement non-valide");
					}
				}
			}
			break;

		case HAUT:
			if ((this.posY - 1) >= 1) {

				// System.out.println(this.posY);
				if (this.modele.cellules[this.posX][this.posY - 1].etat != Etat.SUBMERGEE
						&& this.dejaDeplacer == false) {
					this.posY--;
					this.dejaDeplacer = true;
				} else {
					if (this.dejaDeplacer == true) {
						System.out.println("Vous avez deja deplace !!");
					} else {
						System.out.println("Case submergee, deplacement non-valide");
					}
				}
				// System.out.println(this.posY + "ssssssss");
				// System.out.println(this.posY);
			}
			break;

		case BAS:
			if (this.posY + 1 <= this.modele.HAUTEUR) {
				if (this.modele.cellules[this.posX][this.posY + 1].etat != Etat.SUBMERGEE
						&& this.dejaDeplacer == false) {
					this.posY++;
					this.dejaDeplacer = true;
				} else {
					if (this.dejaDeplacer == true) {
						System.out.println("Vous avez deja deplace !!");
					} else {
						System.out.println("Case submergee, deplacement non-valide");
					}
				}
			}
			break;
		}
		// System.out.println(this.posX);
		// System.out.println(this.posY);
	}

	public static String switchString(Artefact e) {

		switch (e) {
		case AIR:
			return "AIR";

		case FEU:
			return "FEU";

		case TERRE:
			return "TERRE";

		case EAU:
			return "EAU";

		default:
			return "NULL";

		}
	}

	public String printcles() {
		String s = "<br>cles: " + "<br>";
		if (this.cles.isEmpty()) {
			return s + "None" + "<br>";
		} else {
			for (Artefact a : this.cles) {
				//System.out.println("cles: ");
				//System.out.println(switchString(a));
				s += switchString(a) + "<br>";
			}
			//s += "<br>";
			//System.out.println(s);
			return s;
		}
		
	}

	public String printArte() {
		//"<html>First row<br>Second row</html>"
		String s = "<br>artefacts: " + "<br>";
		if (this.artefacts.isEmpty()) {
			return s + "None" + "<br>";
		} else {
			for (Artefact a : this.artefacts) {
				//System.out.println("Artefacts: ");
				//System.out.println(switchString(a));
				s += switchString(a) + "<br>";
			}
			s += "<br>";
			//System.out.println(s);
			return s;
		}
	}
	
	public String printInstr() {
		String s = "";
		
		s += "<br>Deplacer: ";
		if (this.dejaDeplacer) {
			s += "Non";
		} else {
			s += "Oui";
		}
		
		s += "<br>Assecher: ";
		if (this.dejaAssecher) {
			s += "Non";
		} else {
			s += "Oui";
		}
		
		s += "<br>Recuperer: ";
		if (this.dejaRecupere) {
			s += "Non";
		} else {
			s +="Oui";
		}
		
		s += "<br>Chercher: ";
		if (this.dejaChercher) {
			s += "Non";
		} else {
			s +="Oui";
		}
		
		s += "<br>Sac de Sable: ";
		if (this.dejaSac) {
			s += "Non";
		} else {
			s +="Oui";
		}
		
		s += "<br>Helicoptere: ";
		if (this.dejaHelicop){
			s += "Non";
			//System.out.println("yes");
		} else {
			s +="Oui";
		}
		
		return s;
	}

}
