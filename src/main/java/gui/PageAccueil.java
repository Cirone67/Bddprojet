/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author drumm
 */
public class PageAccueil extends BorderPane {
    
    private Controleur controleur;
    private Stage inStage;
    
    private int utilisateurCourant;
    
    private TextField tfRechercher;
    
    private Button bAccueil;
    private MenuButton bMultimedia;
    private MenuButton bMaisonEtJardin;
    private MenuButton bJouetsEtJeux;
    private MenuButton bCultureEtLoisirs;
    private MenuButton bAutoEtMoto;
    private MenuButton bReconditionne;
    
    private BorderPane bpEcranPrincipal;
    //private BorderPane bpEntete;
    
    
    public PageAccueil (Stage inStage,int utilisateurCourant) {
        this.utilisateurCourant = utilisateurCourant;
        this.inStage = inStage;
        this.controleur = new Controleur(this);
        
        this.tfRechercher = new TextField ("Rechercher");
        
        this.bAccueil = new Button ("Accueil");
        this.bMultimedia = new MenuButton ("Multimédia");
        this.bMaisonEtJardin = new MenuButton ("Maison et Jardin");
        this.bJouetsEtJeux = new MenuButton ("Jouets et Jeux");
        this.bCultureEtLoisirs = new MenuButton ("Culture et Loisirs");
        this.bAutoEtMoto = new MenuButton ("Auto et Moto");
        this.bReconditionne = new MenuButton ("Reconditionné");
        
        BorderPane bpEntete = new BorderPane ();
        ImageView ivLogoINSA = new ImageView(new Image("file:Image_INSA.png"));
        bpEntete.setCenter(this.tfRechercher);
        bpEntete.setLeft(ivLogoINSA);
        Background bgGrey = new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, null));
        bpEntete.setBackground(bgGrey);
        
        
        VBox vbRubriques = new VBox (this.bAccueil, this.bMultimedia, this.bMaisonEtJardin, this.bJouetsEtJeux, this.bCultureEtLoisirs,
            this.bAutoEtMoto, this.bReconditionne);
        vbRubriques.setSpacing(8);
        
        vbRubriques.setBackground(bgGrey);
        
        
        this.setTop(bpEntete);
        this.setLeft(vbRubriques);
    }

    public Controleur getControleur() {
        return controleur;
    }

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    /**
     * @return the utilisateurCourant
     */
    public int getUtilisateurCourant() {
        return utilisateurCourant;
    }
}
