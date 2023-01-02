/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import BdD.Affichage;
import BdD.Article;
import BdD.Utilisateur;
import static BdD.Utilisateur.defautConnect;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author drumm
 */
public class Rechercher {
    
    public AfficherEnchere aeFenetreAffichage;
    
    public Rechercher () {
        
    }
    
    public void rechercherEnchere (String sRecherche) {
        this.aeFenetreAffichage = new AfficherEnchere();
        ArrayList<String> alRechercheBasique = new ArrayList<>();
        alRechercheBasique = Article.decomposeRecherche(sRecherche);
        
        ArrayList<Affichage> alRechercheDecomposee = new ArrayList<>();
        
        try ( Connection con = defautConnect()) {
                alRechercheDecomposee = Article.ChercheArticle(con, alRechercheBasique);
//                aeFenetreAffichage.fenetreAffichageEnchere(alRechercheDecomposee, "Recherche");
            } catch (Exception ex) {
                throw new Error(ex);
            }
    }
    
}
