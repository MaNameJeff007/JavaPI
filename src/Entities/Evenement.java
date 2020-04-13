/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mohamed Turki
 */
public class Evenement {

    private int id;
    private int club_id;
    private String nom_evenement;
    private LocalDate heure_debut;
    private LocalDate heure_fin;
//    public Club[] c;
//    private Set<Club> listClub = new HashSet<Club>();

    
    
//    public Set<Club> getListClub() {
//        return listClub;
//    }
//
//    public void setListClub(Set<Club> listClub) {
//        this.listClub= listClub;
//    }
//
//    public void addUser(Club club) {
//        this.listClub.add(club);
//    }
//
//    public void removeClub(Club club) {
//        this.listClub.remove(club);
//    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClub_id() {
        return club_id;
    }

    public void setClub_id(int club_id) {
        this.club_id = club_id;
    }

    public LocalDate getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(LocalDate heure_debut) {
        this.heure_debut = heure_debut;
    }

    public Evenement(int id, int club_id, String nom_evenement, LocalDate heure_debut, LocalDate heure_fin) {
        this.id = id;
        this.club_id = club_id;
        this.nom_evenement = nom_evenement;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
    }

    public String getNom_evenement() {
        return nom_evenement;
    }

    public void setNom_evenement(String nom_evenement) {
        this.nom_evenement = nom_evenement;
    }

    public LocalDate getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(LocalDate heure_fin) {
        this.heure_fin = heure_fin;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", club_id=" + club_id + ", nom_evenement=" + nom_evenement + ", heure_debut=" + heure_debut + ", heure_fin=" + heure_fin + '}';
    }
}
