/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BdD;

import static BdD.Utilisateur.defautConnect;
import java.sql.Connection;

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
            } catch (Exception ex) {
            throw new Error(ex);
            }    
     
 }
public static void main(String[] args) {

            createTable();

        }
        
 }