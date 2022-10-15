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
    private PasswordField pfModDePasse;
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
    
    public PageConnection (Stage inStage) {
        
        this.inStage = inStage;
        
        this.lTitre = new Label ("Se Connecter");
        
        this.tfIdentifiant = new TextField ("Identifiant");
        this.lIdentifiant = new Label ("Identifiant : ");
        this.lMotDePasse = new Label ("Mot de Passe : ");
        this.pfModDePasse = new PasswordField ();
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
        HBox hbMotDePasse = new HBox (this.lMotDePasse, this.pfModDePasse);
        VBox vbConnection = new VBox (hbTitre, hbIdentifiant, hbMotDePasse, this.hlMDPOublie, this.hlNouvelUtilisateur);
        
        vbConnection.setSpacing(8);
        
        this.inStage.setTitle("Site Vente aux Enchères");
        
        this.setLeft(vbConnection);
        
        
        hlMDPOublie.setOnAction(new EventHandler<ActionEvent>() {
            private VBox vbRecreerMDP;
            private HBox hbNom;
            private HBox hbPrenom;
            private HBox hbMDP;
            private HBox hbConfirmationMDP;
            
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
                VBox vbRecreerMDP = new VBox (hbNom, hbPrenom, hbMDP, hbConfirmationMDP, this.bValider);
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
    }
    
}
