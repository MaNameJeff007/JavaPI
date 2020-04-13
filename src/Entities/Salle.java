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
public class Salle {
     private String libelle; 
     private int id;

    @Override
    public String toString() {
        return "Salle{" + "libelle=" + libelle + ", id=" + id + '}';
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Salle() {
    }

    public Salle(String libelle) {
        this.libelle = libelle;
    }

    public Salle(String libelle, int id) {
        this.libelle = libelle;
        this.id = id;
    }
}
