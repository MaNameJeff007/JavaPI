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
public class Seance {

    private int id;
    private String jour;
    private String hdeb;
    private String hfin;
    private String enseignant;
    private String classe;
    private String salle;
    private String matiere;

    public String getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(String enseignant) {
        this.enseignant = enseignant;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public Seance(int id, String jour, String hdeb, String hfin, String enseignant, String classe, String salle, String matiere) {
        this.id = id;
        this.jour = jour;
        this.hdeb = hdeb;
        this.hfin = hfin;
        this.enseignant = enseignant;
        this.classe = classe;
        this.salle = salle;
        this.matiere = matiere;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public Seance() {
    }

    @Override
    public String toString() {
        return "Seance{" + "id=" + id + ", jour=" + jour + ", hdeb=" + hdeb + ", hfin=" + hfin + ", enseignant=" + enseignant + ", classe=" + classe + ", salle=" + salle + ", matiere=" + matiere + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getHdeb() {
        return hdeb;
    }

    public void setHdeb(String hdeb) {
        this.hdeb = hdeb;
    }

    public String getHfin() {
        return hfin;
    }

    public void setHfin(String hfin) {
        this.hfin = hfin;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

}
