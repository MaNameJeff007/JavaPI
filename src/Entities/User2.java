/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;
import java.time.LocalDate;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

        
/**
 *
 * @author rami2
 */
public class User 
{
    private int identifiant;
    private String username;
    private String email;
    private int enabled;
    private String password;
    private Timestamp last_login;
    private String roles;
    private String nom;
    private String prenom;
    private LocalDate Date_Embauche;
    private LocalDate Date_Inscription;
    private String parent_id;
    private String classeeleve_id;
    private String classeenseignant_id;
    
    public User()
    {
        
    }
    
    public User(int identifiant, String prenom, String nom, String id_classe)
    {
        this.identifiant=identifiant;
        this.prenom=prenom;
        this.nom=nom;
        this.classeeleve_id=id_classe;      
    }

    public User(String username, String email, int enabled, String password, Timestamp last_login, String roles, String nom, String prenom, LocalDate Date_Embauche, LocalDate Date_Inscription, String parent_id, String classeeleve_id, String classeenseignant_id) {
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.password = password;
        this.last_login = last_login;
        this.roles = roles;
        this.nom = nom;
        this.prenom = prenom;
        this.Date_Embauche = Date_Embauche;
        this.Date_Inscription = Date_Inscription;
        this.parent_id = parent_id;
        this.classeeleve_id = classeeleve_id;
        this.classeenseignant_id = classeenseignant_id;
    }

    public User(User U) {
      this.identifiant=U.identifiant;
      this.username = null;
      this.email = null;
      this.enabled = 0;
      this.password = null;
      this.last_login = null;
      this.roles = null;
      this.nom = null;
      this.prenom = null;
      this.Date_Embauche = null;
      this.Date_Inscription = null;
      this.parent_id = null;
      this.classeeleve_id = null;
      this.classeenseignant_id = null;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getLast_login() {
        return last_login;
    }

    public void setLast_login(Timestamp last_login) {
        this.last_login = last_login;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDate_Embauche() {
        return Date_Embauche;
    }

    public void setDate_Embauche(LocalDate Date_Embauche) {
        this.Date_Embauche = Date_Embauche;
    }

    public LocalDate getDate_Inscription() {
        return Date_Inscription;
    }

    public void setDate_Inscription(LocalDate Date_Inscription) {
        this.Date_Inscription = Date_Inscription;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getClasseeleve_id() {
        return classeeleve_id;
    }

    public void setClasseeleve_id(String classeeleve_id) {
        this.classeeleve_id = classeeleve_id;
    }

    public String getClasseenseignant_id() 
    {
        return classeenseignant_id;
    }

    public void setClasseenseignant_id(String classeenseignant_id) {
        this.classeenseignant_id = classeenseignant_id;
    }  
}
