/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mohamed Turki
 */
public class Club {

    private int id;
    private int user_id;
    private String nom_resp;

  
    private String nomclub;
    private String nom_image;
//    private Set<User> listUser = new HashSet<User>();

    public Club(int id, int user_id, String nomclub, String nom_image,String nom_resp) {
        this.id = id;
        this.user_id = user_id;
        this.nomclub = nomclub;
        this.nom_image = nom_image;
        this.nom_resp=nom_resp;
    }

    public Club() {
    }


  public String getNom_resp() {
        return nom_resp;
    }

    public void setNom_resp(String nom_resp) {
        this.nom_resp = nom_resp;
    }
//    public Set<User> getListUser() {
//        return listUser;
//    }
//
//    public void setListUser(Set<User> listUser) {
//        this.listUser = listUser;
//    }
//
//    public void addUser(User user) {
//        this.listUser.add(user);
//    }
//
//    public void removeUser(User user) {
//        this.listUser.remove(user);
//    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNomclub() {
        return nomclub;
    }

    public void setNomclub(String nomclub) {
        this.nomclub = nomclub;
    }

    public String getNom_image() {
        return nom_image;
    }

    public void setNom_image(String nom_image) {
        this.nom_image = nom_image;
    }

    @Override
    public String toString() {
        return "Club{" + "id=" + id + ", user_id=" + user_id + ", nomclub=" + nomclub + ", nom_image=" + nom_image + '}';
    }
}
