/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author dell
 */
public class Coeff {
   private int id;
   private int valeur;
   private String matiere;
    private String niveau;
    
    

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public Coeff(int id, int valeur, String matiere, String niveau) {
        this.id = id;
        this.valeur = valeur;
        this.matiere = matiere;
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "Coeff{" + "id=" + id + ", valeur=" + valeur + ", matiere=" + matiere + ", niveau=" + niveau + '}';
    }

    
   

    

    public Coeff() {
    }

   
   
   
}
