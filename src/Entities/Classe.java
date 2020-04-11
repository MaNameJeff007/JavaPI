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
public class Classe {
    private int id;
    private String libelle;
    private int capacite;
    private int niveau ;

    public Classe(int id, String libelle, int capacite, int niveau) {
        this.id = id;
        this.libelle = libelle;
        this.capacite = capacite;
        this.niveau = niveau;
    }

    public Classe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "Classe{" +"id="+this.id +"libelle = "+this.libelle+"capacite="+this.capacite+"niveau = "+this.niveau+'}';
    }
    
    
    
}
