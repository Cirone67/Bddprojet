/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BdD;

import static BdD.Utilisateur.defautConnect;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author brenc
 */
public class Main {
//Fonction pour créer toutes les tables à la suite.
 public static void createTable(){
     try ( Connection con = defautConnect()) {
            Article.creeTableArticle(con);
            Enchere.creeEnchere(con);
            SurCategorie.creeTableSurCategorie(con);
            Utilisateur.creeTableUtilisateur(con);
            Categorie.creeTableCategorie(con);
            Enchere.creeTableListPosseseur(con);
            Categorie.creeTableJoinCategorieArticle(con);
            
            } catch (Exception ex) {
            throw new Error(ex);
            }    
     
 }
 
 public static void createExemple(){
     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
     try ( Connection con = defautConnect()) {
         //Créer 2 surCatégorie
         SurCategorie.createSurCategorie(con, "SurCat1");
         SurCategorie.createSurCategorie(con, "SurCat2");
         //Créer 2 sous catégorie par Catégorie
            Categorie.createCategorie( con, "Auto et Moto", 1);
            Categorie.createCategorie( con, "Culture et Loisirs", 1);
            Categorie.createCategorie( con, "Jouets et Jeux", 1);
            Categorie.createCategorie( con, "Maison et Jardin", 2);
            Categorie.createCategorie( con, "multimédia", 2);
            Categorie.createCategorie( con, "Reconditionné", 2);
         //Creation de 2 utilisateur
         Utilisateur.createUtilisateur(con, "titi.toto@gmail.com", "pomme", "FR-80000", "titi", "toto", 0);
         Utilisateur.createUtilisateur(con, "tom.jerry@gmail.com", "patate", "FR-67000", "tom", "jerry", 1);
         //Créer 2 Article par Categorie idcategorie, String designation, int idSurCategorie
         ArrayList<String> Article1type = new ArrayList<>();
         ArrayList<String> Article2type = new ArrayList<>();
         Article1type.add("outil");
         Article1type.add("materiel");
         Article2type.add("sport");
         Article.createArticle(con, "marteau", "très dure", "bon etat", 0,Article1type , 1);
         Article.createArticle(con, "corde", "rien à dire", "fissure", 0,Article2type , 2);
         //Enchere lié aux articles
          Date dateDebut = new java.sql.Date(simpleDateFormat.parse("2022-12-4").getTime());
          Date dateFin = new java.sql.Date(simpleDateFormat.parse("2022-12-30").getTime());
         Enchere.createEnchere(con, 1, 100, 100, dateDebut, dateFin, 1);
         Enchere.createEnchere(con, 2, 50, 50, dateDebut, dateFin, 2);
         
         //Encherir
         
         
         //Rechercher
         
         //Voir son gain
         
         
            } catch (Exception ex) {
            throw new Error(ex);
            }    
 }
public static void main(String[] args) {

            createTable();
            createExemple();
        }
        
 }