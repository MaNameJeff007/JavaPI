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
public class Matiere {
      private int id;
    private String nom;
    private int nbH;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbH() {
        return nbH;
    }

    public void setNbH(int nbH) {
        this.nbH = nbH;
    }

    public Matiere(int id, String nom, int nbH) {
        this.id = id;
        this.nom = nom;
        this.nbH = nbH;
    }

    public Matiere() {
    }

    @Override
    public String toString() {
        return "Matiere{" + "id=" + id + ", nom=" + nom + ", nbH=" + nbH + '}';
    }
   
}
