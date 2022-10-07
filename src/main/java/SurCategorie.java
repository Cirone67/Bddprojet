
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
    
//Constructor    
    public SurCategorie(int idSurCategorie, ArrayList<Categorie> categorie) {
        this.idSurCategorie = idSurCategorie;
        this.categorie = categorie;
    }

//Get et Set    
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
