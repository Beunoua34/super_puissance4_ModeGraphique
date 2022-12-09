/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package super_puissance4;

import java.util.Scanner;

/**
 *
 * @author guilh
 */
public class Partie {

    private Joueur[] listeJoueurs = new Joueur[2];
    private Joueur joueurCourant;
    private PlateauDeJeu plateau;
    private Joueur joueurSuivant;

    public Partie(Joueur joueur1, Joueur joueur2) {
        listeJoueurs[0] = joueur1;
        listeJoueurs[1] = joueur2;
        this.plateau = new PlateauDeJeu();
    }

    public void attribuerCouleurAuxJoueurs() {
        double alea = Math.random(); //on genere un double aleatoire entre 0 et 1   
        if (alea < 0.5) {       //une chance sur 2 d'etre inferieur a 0,5
            listeJoueurs[0].affecterCouleur("rouge");
            listeJoueurs[1].affecterCouleur("jaune");
        } else {
            listeJoueurs[0].affecterCouleur("jaune");
            listeJoueurs[1].affecterCouleur("rouge");

        }
    }

    public void creerEtAffecterJeton(Joueur player) {
        for (int i = 0; i < 30; i++) {
            player.ajouterJeton(new Jeton(player.getCouleur()));
        }
    }

    public void placerTrousNoirsEtDesintegrateurs() {
        double alea;
        int ligne;
        int colonne;
        for (int i = 0; i < 3; i++) { //on place d'abord 3 trous noirs et 3 desintegrateurs caches dedans
            alea = Math.random() * 6; //on genere un double aleatoire entre 0 et 6
            ligne = (int) alea; //puis on recupere la partie entiere de ce double pour avoir un indice de ligne aleatoire
            //pareil pour les colonnes
            alea = Math.random() * 7;
            colonne = (int) alea;
            while (plateau.presenceTrouNoir(ligne, colonne) == true) { //tant que les coordonnees tires sont celles d'une case deja remplie, on recommence
                alea = Math.random() * 6;
                ligne = (int) alea;
                alea = Math.random() * 7;
                colonne = (int) alea;
            }
            plateau.placerTrouNoir(ligne, colonne);
            plateau.placerDesintegrateur(ligne, colonne);
        }
        for (int i = 0; i < 2; i++) {//on place ensuite les 2 desintegrateurs
            alea = Math.random() * 6;
            ligne = (int) alea;
            alea = Math.random() * 7;
            colonne = (int) alea;
            while (plateau.presenceDesintegrateur(ligne, colonne) == true) { //tant que les coordonnees tires sont celles d'une case deja remplie, on recommence
                alea = Math.random() * 6;
                ligne = (int) alea;
                alea = Math.random() * 7;
                colonne = (int) alea;
            }
            plateau.placerDesintegrateur(ligne, colonne);
        }
        for (int i = 0; i < 2; i++) {//on place ensuite les 2 derniers trous noirs
            alea = Math.random() * 6;
            ligne = (int) alea;
            alea = Math.random() * 7;
            colonne = (int) alea;
            while (plateau.presenceDesintegrateur(ligne, colonne) == true || plateau.presenceTrouNoir(ligne, colonne) == true) { //les 2 trous noirs doivent etre poses sur une case sans desintegrateur ni trou noir
                alea = Math.random() * 6;
                ligne = (int) alea;
                alea = Math.random() * 7;
                colonne = (int) alea;
            }
            plateau.placerTrouNoir(ligne, colonne);
        }
    }

    public void initialiserPartie() {
        
        attribuerCouleurAuxJoueurs();
        creerEtAffecterJeton(listeJoueurs[0]);
        creerEtAffecterJeton(listeJoueurs[1]);
        placerTrousNoirsEtDesintegrateurs();
    }

    public void lancerPartie() {
        joueurCourant = listeJoueurs[0];//le joueur classé en premier comence
        joueurSuivant = listeJoueurs[1];
        boolean finito = false;
        while (finito == false) { //tant que la partie n'est pas finie
            Scanner sc;
            int colonneJouee;
            int choixJoueur = 99;//variable prenant le choix du joueur d'on initialise volontairement en un choix invalide
            boolean choixValide = false; //cette variable servira a verifier si le joueur fait un choix possible
            boolean recupValide;//variable attestant de la validite de la case que le joueur veut recuperer ou desintegrer
            sc = new Scanner(System.in);
            plateau.afficherGrilleSurConsole();
            if (joueurCourant == listeJoueurs[0]) {
                joueurSuivant = listeJoueurs[1];
            } else if (joueurCourant == listeJoueurs[1]) {
                joueurSuivant = listeJoueurs[0];//on inverse le joueur courant
            }
            System.out.println("Au tour de " + joueurCourant + " (couleur " + joueurCourant.getCouleur() + ")");
            System.out.println("Il vous reste " + joueurCourant.getReserveJetons().size() + " jetons et " + joueurCourant.getNombreDesintegrateurs() + " deintegrateurs."); //on utilise la fonction size() pour indiquer le nombre de jetons restants
            while (choixValide == false) {
                System.out.println("Que voulez vous faire?");
                System.out.println("Placer un jeton (1), en recuperer un (2) ou jouer un desintegrateur (3)?");
                choixJoueur = sc.nextInt();
                choixValide = true; //on dit que le choix est ppossible, et on va chercher des incoherences
                if (choixJoueur != 1 && choixJoueur != 2 && choixJoueur != 3) { //si le joueur ne tappe pas 1, 2 ou 3 le choix n'est pas valide
                    choixValide = false;
                }
                if (choixJoueur == 2) {
                    boolean pasDeJeton = true; //on part du principe que le joueur n'a pas de jeton pose sur la ligne, donc qu'il ne peut pas en recuperer
                    for (int ligne = 0; ligne < 6; ligne++) {
                        for (int colonne = 0; colonne < 7; colonne++) {
                            if (plateau.lireCouleurJeton(ligne, colonne) == joueurCourant.getCouleur()) {//si on trouve une case avec un jeton recuperable
                                pasDeJeton = false; //alors le choix est valide
                            }
                        }
                    }
                    if (pasDeJeton) { //si on n'a pas trouve de jeton recuperable, le choix n'est pas valide
                        System.out.println("Vous n'avez pas de jeton a retirer");
                        choixValide = false;
                    }
                }
                if (choixJoueur == 3) {
                    if (joueurCourant.getNombreDesintegrateurs() == 0) {
                        System.out.println("Vous n'avez pas de desintegrateur. Gros chien");
                        choixValide = false;
                    }
                    boolean pasDeJeton = true; //on part du principe qu'il n'y a pas de jeton desintegrable sur le plateau
                    for (int ligne = 0; ligne < 6; ligne++) {
                        for (int colonne = 0; colonne < 7; colonne++) { //on parcourt tout le tableau
                            if (plateau.lireCouleurJeton(ligne, colonne) == joueurSuivant.getCouleur()) {//si on trouve une case avec un jeton desintegrable
                                pasDeJeton = false; //alors le choix est valide
                            }
                        }
                    }
                    if (pasDeJeton) { //si on n'a pas trouve de jeton recuperable, le choix n'est pas valide
                        System.out.println("Vous n'avez pas de jeton a desintegrer");
                        choixValide = false;
                    }
                }
            }
///si le joueur joue un jeton
            if (choixJoueur == 1) {
                System.out.println("Sur quelle colonne voulez-vous jouer? (1 a 7)");
                colonneJouee = sc.nextInt(); //saisie sur l'interface
                while ((colonneJouee <= 0) || (colonneJouee >= 8)) {  //tant que l'utilisateur comprend que dalle, on lui redemande
                    System.out.println("Sur quelle colonne voulez-vous jouer? (1 a 7)");
                    colonneJouee = sc.nextInt();
                }
                int ligneJouee=6;
               // ligneJouee = plateau.ajouterJetonDansColonne(joueurCourant.getReserveJetons().get(0), colonneJouee - 1);
                while (ligneJouee == 99) { //tant que le joueur joue sur une colonne pleine, on lui demande de rejouer
                    System.out.println("Erreur: la colonne est pleine");
                    System.out.println("Sur quelle colonne voulez-vous jouer? (1 a 7)");
                    colonneJouee = sc.nextInt(); //saisie sur l'interface
                    //ligneJouee = plateau.ajouterJetonDansColonne(joueurCourant.getReserveJetons().get(0), colonneJouee - 1);
                }
                if (plateau.presenceTrouNoir(ligneJouee, colonneJouee - 1)) {//si le joueur a joue dans un trou noir
                    plateau.supprimerJeton(ligneJouee, colonneJouee - 1);
                    plateau.supprimerTrouNoir(ligneJouee, colonneJouee - 1);
                    System.out.println("Jeton mange par un trou noir. Cheh.");
                }
                if (plateau.presenceDesintegrateur(ligneJouee, colonneJouee - 1)) {
                    plateau.supprimerDesintegrateur(ligneJouee, colonneJouee - 1);
                    joueurCourant.obtenirDesintegrateur();
                    System.out.println("Vous avez recupere un desintagrateur");
                }
                System.out.println("Pion place");
                joueurCourant.jouerJeton();//si le colonne n'est pas pleine, on enleve le jeton place de la reserve du joueur
            }
/// si le joueur recupere un jeton
            if (choixJoueur == 2) {
                int ligneJouee = 99;
                colonneJouee = 99;
                recupValide = false;
                while (recupValide == false) {
                    recupValide = true;//on part du principe que le joueur veut recuperer une case valide
                    System.out.println("Sur quelle colonne est le jeton que vous voulez enlever (1 a 7)?");
                    colonneJouee = sc.nextInt();
                    System.out.println("Sur quelle ligne est le jeton que vous voulez recuperer? (1 a 6)");
                    ligneJouee = sc.nextInt();
                    if ((colonneJouee <= 0) || (colonneJouee >= 8) || (ligneJouee <= 0) || (ligneJouee >= 7)) {  //tant que l'utilisateur comprend que dalle, on lui redemande                       
                        System.out.println("Colonne de 1 a 7 et ligne de 1 a 6");
                        recupValide = false;

                    }
                    if (recupValide == true) { //si on a passe la premiere verification
                        if (plateau.lireCouleurJeton(ligneJouee - 1, colonneJouee - 1) != joueurCourant.getCouleur()) { //tant que le joueur ne joue pas sur un jeton qu'il possede
                            System.out.println("Il n'y a pas de jeton a vous sur  cette case.");
                            recupValide = false;
                        }
                    }
                }
                joueurCourant.ajouterJeton(plateau.recupererJeton(ligneJouee - 1, colonneJouee - 1)); //on enleve le jeton et on le redonne au joueur
                plateau.tasserColonne(colonneJouee - 1);
            }
/// si le joueur joue un desintegrateur
            if (choixJoueur == 3) {
                int ligneDesint = 99;
                colonneJouee = 99;
                boolean desintValide = false;
                while (desintValide == false) {
                    desintValide = true;
                    System.out.println("Sur quelle colonne est le jeton que vous voulez desintegrer?");
                    colonneJouee = sc.nextInt();
                    System.out.println("Sur quelle ligne est le jeton que vous voulez desintegrer?");
                    ligneDesint = sc.nextInt();

                    if ((colonneJouee <= 0) || (colonneJouee >= 8) || (ligneDesint <= 0) || (ligneDesint >= 7)) {  //tant que l'utilisateur comprend que dalle, on lui redemande
                        System.out.println("Colonne de 1 a 7 et ligne de 1 a 6");
                        desintValide = false;
                    }

                    if (desintValide == true) {
                        if (plateau.lireCouleurJeton(ligneDesint - 1, colonneJouee - 1) != joueurSuivant.getCouleur()) {
                            System.out.println("Il n'y a pas de jeton advesre sur  cette case.");
                            desintValide = false;
                        }
                    }
                }
                plateau.supprimerJeton(ligneDesint - 1, colonneJouee - 1);
                joueurCourant.utiliserDesintegrateur();
                plateau.tasserColonne(colonneJouee - 1);
            }

///
            if (plateau.etreGagnantePourCouleur(joueurSuivant.getCouleur())) { //on verifie que la victoire ne revienne pas a l'autre joueur suit un la suppression d'un jeton
                System.out.println("Felicitations, le joueur " + joueurSuivant + " a gagne");
                finito = true;
                plateau.afficherGrilleSurConsole();
            } else if (plateau.etreGagnantePourCouleur(joueurCourant.getCouleur())) {

                finito = true;
                plateau.afficherGrilleSurConsole();
                System.out.println("Felicitations, le joueur " + joueurCourant + " a gagne");
            }

            if (plateau.grilleRemplie()) {
                System.out.println("La grille est pleine: fin de partie");
                finito = true;
            }
            joueurCourant = joueurSuivant;
        }
    }   //on inverse le joueur courant
}
