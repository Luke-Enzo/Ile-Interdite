package projet_ile;

public class Ile {
	/**
	 * L'amor√ßage est fait en cr®¶ant le mod®®le et la vue, par un simple appel ®§
	 * chaque constructeur. Ici, le mod®®le est cr®¶®¶ ind®¶pendamment (il s'agit
	 * d'une partie autonome de l'application), et la vue prend le mod®®le comme
	 * param®®tre (son objectif est de faire le lien entre mod®®le et
	 * utilisateur).
	 */
	public static void main(String[] args) {
		IModele modele = new IModele();
		IVue vue = new IVue(modele);
		//System.out.println(modele);
	}
}