/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package super_puissance4;

/**
 *
 * @author guilhem
 */
public class Jeton {
   String couleur;

    public Jeton(String color) {
        couleur = color;
    }

    public String lireCouleur() {
        return couleur;
    }

    public String getCouleur() {  
        if (couleur.equals("rouge")){
            return "R";
        }else return "J"; //on retourne seulement "R" ou "J" pour l'affichage de la grille
        
    }

    @Override
    public String toString() {
        if (couleur.equals("rouge")) return "R";
        else return "J";
    }
    
    
    
   
}
