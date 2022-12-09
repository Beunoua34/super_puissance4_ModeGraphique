/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package super_puissance4;

/**
 *
 * @author guilhem
 */
public class CelluledeGrille {

    Jeton jetonCourant;
    private boolean avoirTrouNoir;

    public Jeton getJetonCourant() {
        return jetonCourant;
    }
    private boolean avoirDesintegrateur;

    public CelluledeGrille() {
        jetonCourant = null;
        avoirTrouNoir = false;
    }

    @Override
    public String toString() { //toString a modifier quand on voudra traiter les trou noirs -bien vu le sang je modifie ca en 2/2
        if (presenceTrouNoir()) { //si il y a un trou noir ET un desitegrateur, on affiche le trou noir
            return "@";
        } else {
            if (presenceDesintegrateur()) {
                return "D";
            }
            if (jetonCourant == null) {
                return ".";
            } else {
                if (jetonCourant != null) {
                    return jetonCourant.getCouleur();
                }
            }
        }
        return "Erreur"; //cas d'une eventuelle erreur
    }

    public boolean presenceJeton() {
        if (jetonCourant == null) {
            return false;
        } else {
            return true;
        }
    }

    public void affecterJeton(Jeton jeton) {
        jetonCourant = jeton;
    }

    public void supprimerJeton() {
        jetonCourant = null;
    }

    public String lireCouleurDuJeton() {
        if (jetonCourant != null) {
            return jetonCourant.lireCouleur(); //on utilise la methode de la classe Jeton
        } else {
            return "vide";
        }
    }

    public Jeton recupererJeton() { //cette fonction sert a enlever un jeton dans la version 1, mais renvoie aussi le jeton enleve pour la future version 3
        Jeton jeton_enleve; //on utlise une variable temporaire pour ne pas perdre le jeton
        jeton_enleve = jetonCourant;
        jetonCourant = null;
        return jeton_enleve;
    }

    public void placerTrouNoir() {
        avoirTrouNoir = true;
    }

    public void supprimerTrouNoir() {
        avoirTrouNoir = false;
    }

    public boolean presenceTrouNoir() {
        return avoirTrouNoir;
    }

    public void activerTrouNoir() {
        supprimerTrouNoir();
        supprimerJeton();
    }

    public boolean presenceDesintegrateur() {
        return avoirDesintegrateur;
    }

    public void placerDesintegrateur() {
        avoirDesintegrateur = true;
    }

    public void supprimerDesintegrateur() {
        avoirDesintegrateur = false;
    }

}
