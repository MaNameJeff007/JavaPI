/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author rami2
 */
public class Moyenne 
{
   private int id;
   private int trimestre;
   private double moyenne;
   private String eleve_id;
   private String matiere;

    public Moyenne(int trimestre, double moyenne, String eleve_id, String matiere) 
    {
        this.trimestre = trimestre;
        this.moyenne = moyenne;
        this.eleve_id = eleve_id;
        this.matiere = matiere;
    }
    
    public Moyenne()
    {
    }
    
    public Moyenne(int id, int trimestre, double moyenne, String eleve_id, String matiere) 
    {
        this.id=id;
        this.trimestre = trimestre;
        this.moyenne = moyenne;
        this.eleve_id = eleve_id;
        this.matiere = matiere;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    public String getEleve_id() {
        return eleve_id;
    }

    public void setEleve_id(String eleve_id) {
        this.eleve_id = eleve_id;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }
}
