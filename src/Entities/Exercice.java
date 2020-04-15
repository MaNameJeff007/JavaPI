/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 21654
 */
public class Exercice {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String question;

    private String reponse;

    private String score;

    private Course courseId;

    private String option1;

    private String option2;

    private String option3;

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public Exercice() {
    }

    public Exercice(Integer id) {
        this.id = id;
    }

    public Exercice(Integer id, String question, String reponse, String score) {
        this.id = id;
        this.question = question;
        this.reponse = reponse;
        this.score = score;
    }

    public Exercice(String question, String reponse, String score) {
        this.id = id;
        this.question = question;
        this.reponse = reponse;
        this.score = score;
    }

    public Exercice(int id,String question, String reponse, String score, String option1, String option2, String option3) {
        this.id = id;
        this.question = question;
        this.reponse = reponse;
        this.score = score;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;

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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
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
        if (!(object instanceof Exercice)) {
            return false;
        }
        Exercice other = (Exercice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projet.Exercice[ id=" + id + " ]";
    }

}
