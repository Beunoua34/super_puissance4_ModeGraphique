/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package super_puissance4;

/**
 *
 * @author guilh
 */
public class PlateauDeJeu {

    CelluledeGrille[][] grille = new CelluledeGrille[6][7];

    public PlateauDeJeu() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                grille[i][j] = new CelluledeGrille(); //les cellules sont vides initialement
            }
        }
    }

    public int ajouterJetonDansColonne(Joueur joueurCourant, int colonne) {
        for (int ligne = 0; ligne <= 6; ligne++) {
            if (grille[ligne][colonne].presenceJeton() == false) {
                Jeton unJeton=joueurCourant.reserveJetons.get(0);
                
                grille[ligne][colonne].affecterJeton(unJeton); //si la case du bas est libre, on y ajoute le jeton
                return ligne; //puis on retourne l'indice de la ligne
            }
        }
        return 99; //si aucune case n'est vide, il y a une erreur, donc on retourne 99 pour le signaler
    }

    public boolean grilleRemplie() {
        for (int ligne = 0; ligne < 6; ligne++) {
            for (int colonne = 0; colonne < 7; colonne++) {
                if (grille[ligne][colonne].presenceJeton() == false) {
                    return false; //des qu'on trouve une case vide, la grille n'est pas pleine
                }
            }
        }
        return true; //si on a trouve aucune case vide, la grille est pleine
    }

    public boolean presenceJeton(int x, int y) {
        return grille[x][y].presenceJeton();
    }

    public String lireCouleurJeton(int x, int y) {
        return grille[x][y].lireCouleurDuJeton();
    }

    public boolean ligneGagnantePourCouleur(String couleur) {
        for (int ligne = 0; ligne < 6; ligne++) {
            for (int colonne = 0; colonne < 4; colonne++) {

                if (grille[ligne][colonne].lireCouleurDuJeton().equals(couleur) && grille[ligne][colonne + 1].lireCouleurDuJeton().equals(couleur) && grille[ligne][colonne + 2].lireCouleurDuJeton().equals(couleur) && grille[ligne][colonne + 3].lireCouleurDuJeton().equals(couleur)) {
                    return true; //si la case etudiee et les 3 à sa droite sont de la bonne couleur, on retourne true
                }

            }
        }
        return false; //si on n'a trouve aucune case gagnante, on retourne false
    }

    public boolean colonneGagnantePourCouleur(String couleur) {
        for (int ligne = 0; ligne < 3; ligne++) {
            for (int colonne = 0; colonne < 7; colonne++) {

                if (grille[ligne][colonne].lireCouleurDuJeton().equals(couleur) && grille[ligne + 1][colonne].lireCouleurDuJeton().equals(couleur) && grille[ligne + 2][colonne].lireCouleurDuJeton().equals(couleur) && grille[ligne + 3][colonne].lireCouleurDuJeton().equals(couleur)) {
                    return true; //si la case etudiee et les 3 à sa droite sont de la bonne couleur, on retourne true
                }
            }
        }
        return false; //si on n'a trouve aucune case gagnante, on retourne false
    }

    public boolean diagonaleMontanteGagnantePourCouleur(String couleur) {
        for (int ligne = 0; ligne < 3; ligne++) {
            for (int colonne = 0; colonne < 4; colonne++) {

                if (grille[ligne][colonne].lireCouleurDuJeton().equals(couleur) && grille[ligne + 1][colonne + 1].lireCouleurDuJeton().equals(couleur) && grille[ligne + 2][colonne + 2].lireCouleurDuJeton().equals(couleur) && grille[ligne + 3][colonne + 3].lireCouleurDuJeton().equals(couleur)) {
                    return true; //si la case etudiee et les 3 dans sa diagonale au dessus sont de la bonne couleur, on retourne true
                }

            }
        }
        return false; //si on n'a trouve aucune case gagnante, on retourne false
    }

    public boolean diagonaleDesencanteGagnantePourCouleur(String couleur) {
        for (int ligne = 6; ligne < 3; ligne--) {
            for (int colonne = 0; colonne < 4; colonne++) {

                if (grille[ligne][colonne].lireCouleurDuJeton().equals(couleur) && grille[ligne - 1][colonne + 1].lireCouleurDuJeton().equals(couleur) && grille[ligne - 2][colonne + 2].lireCouleurDuJeton().equals(couleur) && grille[ligne - 3][colonne + 3].lireCouleurDuJeton().equals(couleur)) {
                    return true; //si la case etudiee et les 3 dans sa diagonale en dessous sont de la bonne couleur, on retourne true
                }

            }
        }
        return false; //si on n'a trouve aucune case gagnante, on retourne false
    }

    public Boolean etreGagnantePourCouleur(String couleur) {
        if (diagonaleDesencanteGagnantePourCouleur(couleur)) {
            return true;
        } else if (diagonaleMontanteGagnantePourCouleur(couleur)) {
            return true;
        } else if (colonneGagnantePourCouleur(couleur)) {
            return true;
        } else return ligneGagnantePourCouleur(couleur); //si aucune ligne, colonne ou diagonale n'est gagnante, on retourne false car la grille n'est ps gagnante
        

    }

    public void afficherGrilleSurConsole() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(grille[i][j]+ " ");
            }
            System.out.println(" "+ (i+1));//retour a la ligne
        }
        System.out.println("");
        System.out.println("1 2 3 4 5 6 7");
    }

    public void viderGrille(Joueur j1, Joueur j2) {
        for (int ligne = 0; ligne < 6; ligne++) {
            for (int colonne = 0; colonne < 7; colonne++) {
                if (grille[ligne][colonne].getJetonCourant().getCouleur().equals(j1.getCouleur())) { //on verifie à quel joueur appartient chaque jeton
                    j1.ajouterJeton(grille[ligne][colonne].getJetonCourant());//on rend le jeton au joueur
                    grille[ligne][colonne].supprimerJeton(); //et on enleve le jeton de la grille
                }
                if (!grille[ligne][colonne].getJetonCourant().getCouleur().equals(j2.getCouleur())) {
                } else {
                    //meme demarche pour l'autre joueur
                    j2.ajouterJeton(grille[ligne][colonne].getJetonCourant());//on rend le jeton au joueur
                    grille[ligne][colonne].supprimerJeton(); //et on enleve le jeton de la grille
                }
            }
        }

    }

    public void tasserColonne(int colonne) {
        for (int ligne = 0; ligne <5; ligne++) {
            if (grille[ligne][colonne].presenceJeton() == false) { //si la case est vide
                if (grille[ligne +1 ][colonne].presenceJeton() == true) {//et si sa case au dessus est pleine
                    grille[ligne][colonne].affecterJeton(grille[ligne +1][colonne].getJetonCourant());//on prend le jeton de la case superieure et on le met dans la case traitee
                    grille[ligne + 1][colonne].supprimerJeton();//et on le supprime de la case au dessu
                }
            }
        }
    }
    public boolean presenceTrouNoir(int ligne, int colonne){
       return grille[ligne][colonne].presenceTrouNoir();
    }
    public void placerTrouNoir(int ligne, int colonne){
        grille[ligne][colonne].placerTrouNoir();
    }
    public void supprimerTrouNoir(int ligne, int colonne){
        grille[ligne][colonne].supprimerTrouNoir();
    }
    public void placerDesintegrateur(int ligne, int colonne){
        grille[ligne][colonne].placerDesintegrateur();
    }
    public void supprimerDesintegrateur(int ligne, int colonne){
        grille[ligne][colonne].supprimerDesintegrateur();
    }
    public boolean presenceDesintegrateur(int ligne, int colonne){
       return grille[ligne][colonne].presenceDesintegrateur();
    }
    public void supprimerJeton(int ligne, int colonne){
        grille[ligne][colonne].supprimerJeton();
    }
    public Jeton recupererJeton(int ligne, int colonne){
        return grille[ligne][colonne].recupererJeton();
    }
    public void tasserGrille() {
        for (int i=0;i<7;i++){
            tasserColonne(i);
        }
    }
}


