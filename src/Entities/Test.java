/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;

/**
 *
 *
 */
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String question;

    private String reponse;

    private int typeIntell;

    public Test() {
    }

    public Test(Integer id) {
        this.id = id;
    }

    public Test(Integer id, String question, String reponse, int typeIntell) {
        this.id = id;
        this.question = question;
        this.reponse = reponse;
        this.typeIntell = typeIntell;
    }

    public Test(String question, String reponse, int typeIntell) {
        this.id = id;
        this.question = question;
        this.reponse = reponse;
        this.typeIntell = typeIntell;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public int getTypeIntell() {
        return typeIntell;
    }

    public void setTypeIntell(int typeIntell) {
        this.typeIntell = typeIntell;
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
        if (!(object instanceof Test)) {
            return false;
        }
        Test other = (Test) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projet.Test[ id=" + id + " ]";
    }

}
