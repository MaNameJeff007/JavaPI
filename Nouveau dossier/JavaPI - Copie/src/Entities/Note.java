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
public class Note 
{
    private int id;
    private String  type;
    private int trimestre;
    private String enseignant_id;
    private String eleve_id;
    private String matiere;
    private double valeur;
    
    public Note(Note N)
    {
      this.id=N.id;
      this.type = N.type;
      this.trimestre = N.trimestre;
      this.enseignant_id = N.enseignant_id;
      this.eleve_id = N.eleve_id;
      this.matiere = N.matiere;
      this.valeur = N.valeur;  
    }

    public Note(String type, int trimestre, String enseignant_id, String eleve_id, String matiere, double valeur) 
    {
        this.type = type;
        this.trimestre = trimestre;
        this.enseignant_id = enseignant_id;
        this.eleve_id = eleve_id;
        this.matiere = matiere;
        this.valeur = valeur;
    }
    
    public Note(int id, String type, int trimestre, String enseignant_id, String eleve_id, String matiere, double valeur) 
    {
        this.id=id;
        this.type = type;
        this.trimestre = trimestre;
        this.enseignant_id = enseignant_id;
        this.eleve_id = eleve_id;
        this.matiere = matiere;
        this.valeur = valeur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }

    public String getEnseignant_id() {
        return enseignant_id;
    }

    public void setEnseignant_id(String enseignant_id) {
        this.enseignant_id = enseignant_id;
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

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }
    
    
    
    
}
