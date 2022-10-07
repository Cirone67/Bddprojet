/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brenc
 */
public class Article {
  private int idArticle;
  private char descriptionCourte;
  private char descriptionLongue;
  private int expedition;
  //private  idPhoto
  private Categorie categorie;
  private char posseseur;

    public int getIdArticle() {
        return idArticle;
    }

    public char getDescriptionCourte() {
        return descriptionCourte;
    }

    public char getDescriptionLongue() {
        return descriptionLongue;
    }

    public int getExpedition() {
        return expedition;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public char getPosseseur() {
        return posseseur;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public void setDescriptionCourte(char descriptionCourte) {
        this.descriptionCourte = descriptionCourte;
    }

    public void setDescriptionLongue(char descriptionLongue) {
        this.descriptionLongue = descriptionLongue;
    }

    public void setExpedition(int expedition) {
        this.expedition = expedition;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public void setPosseseur(char posseseur) {
        this.posseseur = posseseur;
    }
  
  
}
