/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
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
    private Button bMultimedia;
    private Button bMaisonEtJardin;
    private Button bJouetsEtJeux;
    private Button bCultureEtLoisirs;
    private Button bAutoEtMoto;
    private Button bReconditionne;

    private BorderPane bpEcranPrincipal;
    private AffichageEnchere afficherEnchere;
    //private BorderPane bpEntete;

    public PageAccueil(Stage inStage, int utilisateurCourant) {
        this.utilisateurCourant = utilisateurCourant;
        this.inStage = inStage;
        this.controleur = new Controleur(this);
        this.afficherEnchere = new AffichageEnchere(this);

        this.tfRechercher = new TextField("Rechercher");

        this.bAccueil = new Button("Accueil");
        this.bMultimedia = new Button("Multimédia");
        this.bMaisonEtJardin = new Button("Maison et Jardin");
        this.bJouetsEtJeux = new Button("Jouets et Jeux");
        this.bCultureEtLoisirs = new Button("Culture et Loisirs");
        this.bAutoEtMoto = new Button("Auto et Moto");
        this.bReconditionne = new Button("Reconditionné");
        

        BorderPane bpEntete = new BorderPane();
        ImageView ivLogoINSA = new ImageView(new Image("file:Image_INSA.png"));
        bpEntete.setCenter(this.tfRechercher);
        bpEntete.setLeft(ivLogoINSA);
        Background bgGrey = new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, null));
        bpEntete.setBackground(bgGrey);

        VBox vbRubriques = new VBox(this.bAccueil, this.bMultimedia, this.bMaisonEtJardin, this.bJouetsEtJeux, this.bCultureEtLoisirs,
                this.bAutoEtMoto, this.bReconditionne);
        vbRubriques.setSpacing(8);

        vbRubriques.setBackground(bgGrey);

        this.setTop(bpEntete);
        this.setLeft(vbRubriques);
        
        bMultimedia.setOnAction((t) -> {
            this.afficherEnchere.fenetreEnchere("Multimedia");
        });

        //Group root = new Group();
        ScrollBar sbAffichagePrincipal = new ScrollBar();
        sbAffichagePrincipal.setMin(0);
        sbAffichagePrincipal.setMax(100);
        sbAffichagePrincipal.setOrientation(Orientation.VERTICAL);
        sbAffichagePrincipal.setLayoutX(this.getWidth()/* - sbAffichagePrincipal.getWidth()*/);
        sbAffichagePrincipal.setPrefHeight(180);
        sbAffichagePrincipal.setMax(360);
        //root.getChildren().addAll(vbRubriques, sbAffichagePrincipal);

        vbRubriques.setLayoutX(5);
        vbRubriques.setSpacing(10);

        sbAffichagePrincipal.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
                vbRubriques.setLayoutY(-new_val.doubleValue());
            }
        });

        this.setRight(sbAffichagePrincipal);
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
