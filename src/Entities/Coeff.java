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
   private int matiere;
    private int niveau;
    
    private String nomMatiere;
    private String NiveauEtude;

    public String getNomMatiere() {
        return nomMatiere;
    }

    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public String getNiveauEtude() {
        return NiveauEtude;
    }

    public void setNiveauEtude(String NiveauEtude) {
        this.NiveauEtude = NiveauEtude;
    }

    public Coeff(int valeur, int matiere, int niveau) {
        this.valeur = valeur;
        this.matiere = matiere;
        this.niveau = niveau;
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

    public int getMatiere() {
        return matiere;
    }

    public void setMatiere(int matiere) {
        this.matiere = matiere;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public Coeff(int id, int valeur, int matiere, int niveau, String nomMatiere, String NiveauEtude) {
        this.id = id;
        this.valeur = valeur;
        this.matiere = matiere;
        this.niveau = niveau;
        this.nomMatiere = nomMatiere;
        this.NiveauEtude = NiveauEtude;
    }

    

    public Coeff() {
    }

    @Override
    public String toString() {
        return "Coeff{" + "id=" + id + ", valeur=" + valeur + ", matiere=" + matiere + ", niveau=" + niveau + ", nomMatiere=" + nomMatiere + ", NiveauEtude=" + NiveauEtude + '}';
    }

   
   
   
}
