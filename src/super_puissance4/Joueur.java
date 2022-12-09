/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package super_puissance4;

import java.util.ArrayList;

public class Joueur {

     String nom;
     String couleur;
    ArrayList<Jeton> reserveJetons = new ArrayList<Jeton>();
    int nombreDesintegrateurs;

    public Joueur(String name) {
        nom = name;
        nombreDesintegrateurs = 0;
    }

    public void affecterCouleur(String color) {
        couleur = color;
    }

    public int nombreDeJetons() {
        return reserveJetons.size();
    }

    @Override
    public String toString() {
        return nom;
    }

    public void ajouterJeton(Jeton jeton) {
        reserveJetons.add(jeton);
    }

    public Jeton jouerJeton() {
        Jeton jetonJoue;
        jetonJoue = reserveJetons.get(0); //on recupere le jeton qui sera joue pour le renvoyer a la fin
        reserveJetons.remove(0);
        return jetonJoue;
    }

    public ArrayList<Jeton> getReserveJetons() {
        return reserveJetons;
    }

    public String getCouleur() {
        return couleur;
    }

    public void obtenirDesintegrateur() {
        nombreDesintegrateurs += 1;
    }

    public int getNombreDesintegrateurs() {
        return nombreDesintegrateurs;
    }

    public void utiliserDesintegrateur() {
        nombreDesintegrateurs -= 1;
    }
}
