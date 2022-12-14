/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BdD;

import java.sql.Date;

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
  
  
}
