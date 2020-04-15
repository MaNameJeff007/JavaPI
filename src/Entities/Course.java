/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 21654
 */
public class Course {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String nom;

    private String description;

    private String contenu;

    private int typeIntelligence;

    private int niveau;

    private Collection<Exercice> exerciceCollection;

    private FosUser userId;

    public Course() {
    }

    public Course(Integer id) {
        this.id = id;
    }

    public Course(Integer id, String nom, String description, String contenu, int typeIntelligence, int niveau) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.contenu = contenu;
        this.typeIntelligence = typeIntelligence;
        this.niveau = niveau;
    }

    public Course(String nom, String description, String contenu, int typeIntelligence) {
        this.nom = nom;
        this.description = description;
        this.contenu = contenu;
        this.typeIntelligence = typeIntelligence;
        //this.niveau = niveau;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getTypeIntelligence() {
        return typeIntelligence;
    }

    public void setTypeIntelligence(int typeIntelligence) {
        this.typeIntelligence = typeIntelligence;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    @XmlTransient
    public Collection<Exercice> getExerciceCollection() {
        return exerciceCollection;
    }

    public void setExerciceCollection(Collection<Exercice> exerciceCollection) {
        this.exerciceCollection = exerciceCollection;
    }

    public FosUser getUserId() {
        return userId;
    }

    public void setUserId(FosUser userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projet.Course[ id=" + id + " ]";
    }

}
