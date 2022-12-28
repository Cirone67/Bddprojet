/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BdD;

import java.sql.Date;
import javafx.scene.image.ImageView;

/**
 *
 * @author brenc
 */
public class Affichage {
  private int idArticle;
  private String designation;
  private String descriptionCourte;
  private String descriptionLongue;
  private int expedition;
  private double prix;
  private Date dateDebut;
  private Date dateFin;
  private String URLPhoto;
  private ImageView image;

    public Affichage(int idArticle,String designation, String descriptionCourte, String descriptionLongue, int expedition, double prix, Date dateDebut, Date dateFin) {
        this.idArticle= idArticle;
        this.designation = designation;
        this.descriptionCourte = descriptionCourte;
        this.descriptionLongue = descriptionLongue;
        this.expedition = expedition;
        this.prix = prix;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Affichage(int idArticle, String designation, String descriptionCourte, String descriptionLongue, int expedition, double prix, Date dateDebut, Date dateFin, String URLPhoto) {
        this.idArticle = idArticle;
        this.designation = designation;
        this.descriptionCourte = descriptionCourte;
        this.descriptionLongue = descriptionLongue;
        this.expedition = expedition;
        this.prix = prix;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.URLPhoto = URLPhoto;
    }

    public Affichage(int idArticle, String designation, String descriptionCourte, String descriptionLongue, int expedition, double prix, Date dateDebut, Date dateFin, ImageView image) {
        this.idArticle = idArticle;
        this.designation = designation;
        this.descriptionCourte = descriptionCourte;
        this.descriptionLongue = descriptionLongue;
        this.expedition = expedition;
        this.prix = prix;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.image = image;
    }
    
    public String getURLPhoto() {
        return URLPhoto;
    }

    public ImageView getImage() {
        return image;
    }

    
    
    public int getIdArticle() {
        return idArticle;
    }

    public String getDesignation() {
        return designation;
    }

    public String getDescriptionCourte() {
        return descriptionCourte;
    }

    public String getDescriptionLongue() {
        return descriptionLongue;
    }

    public int getExpedition() {
        return expedition;
    }

    public double getPrix() {
        return prix;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public void setURLPhoto(String URLPhoto) {
        this.URLPhoto = URLPhoto;
    }

    public void setImage(ImageView Image) {
        this.image = Image;
    }
    
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setDescriptionCourte(String descriptionCourte) {
        this.descriptionCourte = descriptionCourte;
    }

    public void setDescriptionLongue(String descriptionLongue) {
        this.descriptionLongue = descriptionLongue;
    }

    public void setExpedition(int expedition) {
        this.expedition = expedition;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
  
    
  
}
