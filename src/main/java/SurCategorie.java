
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brenc
 */
public class SurCategorie {
    private int idSurCategorie;
    private ArrayList<Categorie> categorie;
    private char designation;
    
//Constructor    

    public SurCategorie(int idSurCategorie, ArrayList<Categorie> categorie, char designation) {
        this.idSurCategorie = idSurCategorie;
        this.categorie = categorie;
        this.designation = designation;
    }
//Get et Set

    public char getDesignation() {
        return designation;
    }

    public void setDesignation(char designation) {
        this.designation = designation;
    }

    public int getIdSurCategorie() {
        return idSurCategorie;
    }

    public ArrayList<Categorie> getCategorie() {
        return categorie;
    }

    public void setIdSurCategorie(int idSurCategorie) {
        this.idSurCategorie = idSurCategorie;
    }

    public void setCategorie(ArrayList<Categorie> categorie) {
        this.categorie = categorie;
    }
    
    
}
