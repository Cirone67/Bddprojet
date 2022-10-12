/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author drumm
 */
public class PageAccueil extends BorderPane {
    
    private Controleur controleur;
    private Stage inStage;
    
    private TextField tfRechercher;
    
    private Button bSeConnecter;
    private Button bAccueil;
    private Button bMultimedia;
    private Button bMaisonEtJardin;
    private Button bJouetsEtJeux;
    private Button bCultureEtLoisirs;
    private Button bAutoEtMoto;
    private Button bReconditionne;
    
    private BorderPane bpEcranPrincipal;
    
    
    public PageAccueil (Stage inStage) {
        
        this.inStage = inStage;
        this.controleur = new Controleur(this);
        
        this.tfRechercher = new TextField ("Rechercher");
        
        this.bSeConnecter = new Button ("Se connecter");
        this.bAccueil = new Button ("Accueil");
        this.bMultimedia = new Button ("Multimédia");
        this.bMaisonEtJardin = new Button ("Maison et Jardin");
        this.bJouetsEtJeux = new Button ("Jouets et Jeux");
        this.bCultureEtLoisirs = new Button ("Culture et Loisirs");
        this.bAutoEtMoto = new Button ("Auto et Moto");
        this.bReconditionne = new Button ("Reconditionné");
    }

    public Controleur getControleur() {
        return controleur;
    }

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }
}
