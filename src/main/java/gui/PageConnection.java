/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author drumm
 */
public class PageConnection extends BorderPane {
    
    private Stage inStage;
    
    private TextField tfIdentifiant;
    private Label lIdentifiant;
    private PasswordField pfMotDePasse;
    private Label lMotDePasse;
    private Hyperlink hlMDPOublie;
    private Hyperlink hlNouvelUtilisateur;
    private Label lMail;
    private TextField tfMail;
    private Label lConfirmationMDP;
    private PasswordField pfConfirmationMDP;
    private Label lCodePostal;
    private TextField tfCodePostal;
    private Label lNom;
    private TextField tfNom;
    private Label lPrenom;
    private TextField tfPrenom;
    private Label lTitre;
    
    private Stage sMDPOublie;
    private Stage sNouvelUtilisateur;
    
    public PageConnection (Stage inStage) {
        
        this.inStage = inStage;
        
        this.lTitre = new Label ("Se Connecter");
        
        this.tfIdentifiant = new TextField ("Identifiant");
        this.lIdentifiant = new Label ("Identifiant : ");
        this.lMotDePasse = new Label ("Mot de Passe : ");
        this.pfMotDePasse = new PasswordField ();
        this.hlMDPOublie = new Hyperlink ("Mot de Passe oublié ?");
        this.hlNouvelUtilisateur = new Hyperlink ("Nouvel Utilisateur ?");
        
        this.lMail = new Label ("Mail : ");
        this.tfMail = new TextField ("Mail");
        this.lConfirmationMDP = new Label ("Confirmez le Mot de Passe : ");
        this.tfCodePostal = new TextField ("Code Postal");
        this.lCodePostal = new Label ("Code postal : ");
        this.tfNom = new TextField ("Nom");
        this.lNom = new Label ("Nom : ");
        this.tfPrenom = new TextField ("Prénom");
        this.lPrenom = new Label ("Prénom : ");
        
        //inStage.setResizable(false);
        
        HBox hbTitre = new HBox (this.lTitre);
        hbTitre.setAlignment(Pos.CENTER);
         
        HBox hbIdentifiant = new HBox (this.lIdentifiant, this.tfIdentifiant);
        HBox hbMotDePasse = new HBox (this.lMotDePasse, this.pfMotDePasse);
        VBox vbConnection = new VBox (hbTitre, hbIdentifiant, hbMotDePasse, this.hlMDPOublie, this.hlNouvelUtilisateur);
        
        vbConnection.setSpacing(8);
        
        this.inStage.setTitle("Site Vente aux Enchères");
        
        this.setLeft(vbConnection);
        
        
        hlMDPOublie.setOnAction(new EventHandler<ActionEvent>() {
            
            private Label lNom;
            private TextField tfNom;
            private Label lPrenom;
            private TextField tfPrenom;
            private Label lMDP;
            private PasswordField pfMDP;
            private Label lConfirmationMDP;
            private PasswordField pfConfirmationMDP;
            private Button bValider;
            
            @Override
            public void handle(ActionEvent event) {
                this.lNom = new Label ("Nom : ");
                this.tfNom = new TextField ();
                this.lPrenom = new Label ("Prénom : ");
                this.tfPrenom = new TextField ();
                this.lMDP = new Label ("Nouveau mot de passe : ");
                this.pfMDP = new PasswordField ();
                this.lConfirmationMDP = new Label ("Confirmez le mot de passe : ");
                this.pfConfirmationMDP = new PasswordField ();
                this.bValider = new Button ("Valider");
                
                HBox hbNom = new HBox (this.lNom, this.tfNom);
                HBox hbPrenom = new HBox (this.lPrenom, this.tfPrenom);
                HBox hbMDP = new HBox (this.lMDP, this.pfMDP);
                HBox hbConfirmationMDP = new HBox (this.lConfirmationMDP, this.pfConfirmationMDP);
                HBox hbValider = new HBox (this.bValider);
                hbValider.setAlignment(Pos.CENTER);
                
                VBox vbRecreerMDP = new VBox (hbNom, hbPrenom, hbMDP, hbConfirmationMDP, hbValider);
                vbRecreerMDP.setPadding(new javafx.geometry.Insets(15,15,15,15));
                vbRecreerMDP.setSpacing(8);
                Scene sTemp = new Scene(vbRecreerMDP);
                
                sMDPOublie = new Stage();
                sMDPOublie.setScene(sTemp);
                sMDPOublie.setTitle("Mot de passe oublié ?");
                sMDPOublie.setResizable(false);
                sMDPOublie.show();
            }
        });
        
        hlNouvelUtilisateur.setOnAction(new EventHandler<ActionEvent>() {
            private Label lNouvelUtilisateur;
            private Label lNom;
            private TextField tfNom;
            private Label lPrenom;
            private TextField tfPrenom;
            private Label lMail;
            private TextField tfMail;
            private Label lMDP;
            private PasswordField pfMDP;
            private Label lConfirmationMDP;
            private PasswordField pfConfirmationMDP;
            private Label lCodePostal;
            private TextField tfCodePostal;
            private Button bValider;
            
            @Override
            public void handle(ActionEvent event) {
                this.lNouvelUtilisateur = new Label ("Nouvel Utilisateur");
                this.lNom = new Label ("Nom : ");
                this.tfNom = new TextField ();
                this.lPrenom = new Label ("Prénom : ");
                this.tfPrenom = new TextField ();
                this.lMail = new Label ("Mail : ");
                this.tfMail = new TextField ();
                this.lMDP = new Label ("Mot de Passe : ");
                this.pfMDP = new PasswordField ();
                this.lConfirmationMDP = new Label ("Confirmer le mot de passe : ");
                this.pfConfirmationMDP = new PasswordField ();
                this.lCodePostal = new Label ("Code postal : ");
                this.tfCodePostal = new TextField ();
                this.bValider = new Button ("Valider");
                
                HBox hbNouvelUtilisateur = new HBox (this.lNouvelUtilisateur);
                hbNouvelUtilisateur.setAlignment(Pos.CENTER);
                
                HBox hbNom = new HBox (this.lNom, this.tfNom);
                HBox hbPrenom = new HBox (this.lPrenom, this.tfPrenom);
                HBox hbMail = new HBox (this.lMail, this.tfMail);
                HBox hbMDP = new HBox (this.lMDP, this.pfMDP);
                HBox hbConfirmationMDP = new HBox (this.lConfirmationMDP, this.pfConfirmationMDP);
                HBox hbCodePostal = new HBox (this.lCodePostal, this.tfCodePostal);
                
                HBox hbValider = new HBox (this.bValider);
                hbValider.setAlignment(Pos.CENTER);
                
                VBox vbNouvelUtilisateur = new VBox (hbNouvelUtilisateur, hbNom, hbPrenom, hbMail, hbMDP, hbConfirmationMDP, hbCodePostal, hbValider);
                vbNouvelUtilisateur.setPadding(new javafx.geometry.Insets(30,30,30,30));
                vbNouvelUtilisateur.setSpacing(8);
                
                Scene sTemp = new Scene (vbNouvelUtilisateur);
                
                sNouvelUtilisateur = new Stage ();
                sNouvelUtilisateur.setScene(sTemp);
                sNouvelUtilisateur.setTitle("Nouvel Utilisateur");
                sNouvelUtilisateur.setResizable(false);
                sNouvelUtilisateur.show();
            }
        });
    }
    
}
