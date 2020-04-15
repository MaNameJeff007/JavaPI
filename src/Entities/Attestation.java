/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Selim Chikh Zaouali
 */
public class Attestation {

    private int id;
    private LocalDateTime date;
    private String etat;
    private int parent;
    private String enfant;

    

    public Attestation(LocalDateTime date, String etat, int parent) {
        this.date = date;
        this.etat = etat;
        this.parent = parent;
    }

    public Attestation(LocalDateTime date, String etat, int parent, String enfant) {
        this.date = date;
        this.etat = etat;
        this.parent = parent;
        this.enfant = enfant;
    }

    public Attestation(int id, LocalDateTime date, String etat, int parent, String enfant) {
        this.date = date;
        this.etat = etat;
        this.parent = parent;
        this.enfant = enfant;
        this.id = id;
    }

    public void setEnfant(String enfant) {
        this.enfant = enfant;
    }

    public String getEnfant() {
        return enfant;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getEtat() {
        return etat;
    }

    public int getParent() {
        return parent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

}
