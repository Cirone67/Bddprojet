/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brenc
 */
public class Utilisateur {
    private char email;
    private int mdp;
    private int codePostale;
    private char nom;
    private char prenom;

    public char getEmail() {
        return email;
    }

    public int getMdp() {
        return mdp;
    }

    public int getCodePostale() {
        return codePostale;
    }

    public char getNom() {
        return nom;
    }

    public char getPrenom() {
        return prenom;
    }

    public void setEmail(char email) {
        this.email = email;
    }

    public void setMdp(int mdp) {
        this.mdp = mdp;
    }

    public void setCodePostale(int codePostale) {
        this.codePostale = codePostale;
    }

    public void setNom(char nom) {
        this.nom = nom;
    }

    public void setPrenom(char prenom) {
        this.prenom = prenom;
    }
    
    
}
