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
      private int enseignant;
      private int classe;
      private int salle;
     private int matiere;
     
     private String nomMatiere;
     private String salleCours;
     private String nomEnseigant;
    private String prenomomEnseigant;
      private String Niveau;
      

    public String getNomMatiere() {
        return nomMatiere;
    }

    public void setNomMatiere(String matiereNom) {
        this.nomMatiere = matiereNom;
    }

    public String getSalleCours() {
        return salleCours;
    }

    public void setSalleCours(String salleCours) {
        this.salleCours = salleCours;
    }

    public String getNomEnseigant() {
        return nomEnseigant;
    }

    public void setNomEnseigant(String nomEnseigant) {
        this.nomEnseigant = nomEnseigant;
    }

    public String getPrenomomEnseigant() {
        return prenomomEnseigant;
    }

    public void setPrenomomEnseigant(String prenomomEnseigant) {
        this.prenomomEnseigant = prenomomEnseigant;
    }

    public String getNiveau() {
        return Niveau;
    }

    public void setNiveau(String Niveau) {
        this.Niveau = Niveau;
    }

    @Override
    public String toString() {
        return "Seance{" + "id=" + id + ", jour=" + jour + ", hdeb=" + hdeb + ", hfin=" + hfin + ", enseignant=" + enseignant + ", classe=" + classe + ", salle=" + salle + ", matiere=" + matiere + ", matiereNom=" + nomMatiere + ", salleCours=" + salleCours + ", nomEnseigant=" + nomEnseigant + ", prenomomEnseigant=" + prenomomEnseigant + ", Niveau=" + Niveau + '}';
    }

    public Seance(int id, String jour, String hdeb, String hfin, int enseignant, int classe, int salle, int matiere, String matiereNom, String salleCours, String nomEnseigant, String prenomomEnseigant, String Niveau) {
        this.id = id;
        this.jour = jour;
        this.hdeb = hdeb;
        this.hfin = hfin;
        this.enseignant = enseignant;
        this.classe = classe;
        this.salle = salle;
        this.matiere = matiere;
        this.nomMatiere = matiereNom;
        this.salleCours = salleCours;
        this.nomEnseigant = nomEnseigant;
        this.prenomomEnseigant = prenomomEnseigant;
        this.Niveau = Niveau;
    }
     
    
    public Seance() {
    }

    public Seance(int id, String jour, String hdeb, String hfin, int enseignant, int classe, int salle, int matiere) {
        this.id = id;
        this.jour = jour;
        this.hdeb = hdeb;
        this.hfin = hfin;
        this.enseignant = enseignant;
        this.classe = classe;
        this.salle = salle;
        this.matiere = matiere;
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

    public int getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(int enseignant) {
        this.enseignant = enseignant;
    }

    public int getClasse() {
        return classe;
    }

    public void setClasse(int classe) {
        this.classe = classe;
    }

    public int getSalle() {
        return salle;
    }

    public void setSalle(int salle) {
        this.salle = salle;
    }

    public int getMatiere() {
        return matiere;
    }

    public void setMatiere(int matiere) {
        this.matiere = matiere;
    }
      
      
    
}
