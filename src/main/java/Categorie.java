/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brenc
 */
public class Categorie {

private int idcategorie;
private char designation;


//Contructor
    public Categorie(int idcategorie, char designation) {
        this.idcategorie = idcategorie;
        this.designation = designation;
    }

//Set et Get
    public int getIdcategorie() {
        return idcategorie;
    }

    public char getDesignation() {
        return designation;
    }

    public void setIdcategorie(int idcategorie) {
        this.idcategorie = idcategorie;
    }

    public void setDesignation(char designation) {
        this.designation = designation;
    }


}
