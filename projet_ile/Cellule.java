package projet_ile;

public class Cellule {
	/** On conserve un pointeur vers la classe principale du mod¨¨le. */
	private IModele modele;

	enum Etat {
		NORMALE, INONDEE, SUBMERGEE
	}
	
	enum Artefact {
		AIR, EAU, TERRE, FEU
	}

	protected Etat etat;

	/** L'¨¦tat d'une cellule est donn¨¦ par un bool¨¦en. */
	// protected boolean etat;
	/**
	 * On stocke les coordonn¨¦es pour pouvoir les passer au mod¨¨le lors de
	 * l'appel ¨¤ [compteVoisines].
	 */
	private final int x, y;
	protected Artefact artefact;
	protected Artefact cle;
	protected boolean hasArtefact;
	protected boolean hasCle;
	protected boolean isHeliport;

	public Cellule(IModele modele, int x, int y) {
		this.modele = modele;
		this.etat = Etat.NORMALE;
		this.x = x;
		this.y = y;
		this.artefact = null;
		this.hasArtefact = false;
		this.cle = null;
		this.hasCle = false;
		this.isHeliport = false;

	}
	
	public void toBords () {
		this.etat = null;
		this.artefact = null;
		this.hasArtefact = false;
		this.cle = null;
		this.hasCle = false;
	}


	/**
	 * Le passage ¨¤ la g¨¦n¨¦ration suivante se fait en deux ¨¦tapes : - D'abord on
	 * calcule pour chaque cellule ce que sera sont ¨¦tat ¨¤ la g¨¦n¨¦ration
	 * suivante (m¨¦thode [evalue]). On stocke le r¨¦sultat dans un attribut
	 * suppl¨¦mentaire [prochainEtat]. - Ensuite on met ¨¤ jour l'ensemble des
	 * cellules (m¨¦thode [evolue]). Objectif : ¨¦viter qu'une ¨¦volution imm¨¦diate
	 * d'une cellule pollue la d¨¦cision prise pour une cellule voisine.
	 */
	private boolean prochainEtat;

	public String toString() {
		return "( " + this.x + " , " + this.y + " ) : " + this.switchString(this.etat);
	}
	
	public int getX () {
		return this.x;
	}
	
	public int getY () {
		return this.y;
	}

	public String switchString(Etat e) {

		switch (e) {
		case NORMALE:
			return "NORMALE";

		case SUBMERGEE:
			return "SUBMERGEE";

		case INONDEE:
			return "INONDEE";

		default:
			return "NORMALE";

		}
	}
	
	public void genererArtefact (int r) {
		
		if (r==0) {
			this.artefact = Artefact.AIR;
			this.hasArtefact = true;
		}
		if (r==1) {
			this.artefact = Artefact.EAU;
			this.hasArtefact = true;
		}
		if (r==2) {
			this.artefact = Artefact.FEU;
			this.hasArtefact = true;
		}
		if (r==3) {
			this.artefact = Artefact.TERRE;
			this.hasArtefact = true;
		}
		
	}
	
	public void genererCle (int r) {
		if (r==0) {
			this.cle = Artefact.AIR;
			this.hasCle = true;
		}
		if (r==1) {
			this.cle = Artefact.EAU;
			this.hasCle = true;
		}
		if (r==2) {
			this.cle = Artefact.FEU;
			this.hasCle = true;
		}
		if (r==3) {
			this.cle = Artefact.TERRE;
			this.hasCle = true;
		}
		
	}
	

	/**
	 * protected void evalue() { switch (this.modele.compteVoisines(x, y)) {
	 * case 2: prochainEtat = etat; break; case 3: prochainEtat = true; break;
	 * default: prochainEtat = false; } }
	 * 
	 * protected void evolue() { etat = prochainEtat; }
	 **/

	/** Un test ¨¤ l'usage des autres classes (sera utilis¨¦ par la vue). */

	public boolean estVivante() {
		return this.etat == Etat.NORMALE || this.etat == Etat.INONDEE;
	}

}
