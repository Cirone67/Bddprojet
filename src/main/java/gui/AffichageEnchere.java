/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author drumm
 */
public class AffichageEnchere extends BorderPane {

    private PageAccueil vue;
    private Stage sEnchere;
    private Button bTest;
    
    private Label lPrixInitial;
    private TextField tfPrixInitial;
    private Label lDateDebut;
    private DatePicker dpDateDebut;
    private Label lDateFin;
    private DatePicker dpDateFin;
    private Label lNom;
    private TextField tfNom;
    private Label lDescriptionCourte;
    private TextField tfDescriptionCourte;
    private Label lDescriptionLongue;
    private TextField tfDescriptionLongue;
    private Label lMoyenExpedition;
    private RadioButton rbEnvoiPostal;
    private RadioButton rbVenirChercher;
    private Label lTitreFenetre;

    public AffichageEnchere(PageAccueil vue) {
        this.vue = vue;
    }

    public void fenetreEnchere() {
        
        this.lTitreFenetre = new Label ("Créer une enchère");
        this.lNom = new Label ("Nom de l'objet : ");
        this.tfNom = new TextField ();
        this.lPrixInitial = new Label ("Prix Initial : ");
        this.lDateDebut = new Label ("Date de début : ");
        this.dpDateDebut = new DatePicker ();
        this.dpDateFin = new DatePicker ();
        this.lDescriptionCourte = new Label ("Description courte : ");
        this.tfDescriptionCourte = new TextField ();
        this.lDescriptionLongue = new Label ("Description longue : ");
        this.tfDescriptionLongue = new TextField ();
        this.lMoyenExpedition = new Label ("Mode d'envoi : ");
        this.rbEnvoiPostal = new RadioButton ("Envoi Postal");
        this.rbVenirChercher = new RadioButton ("Se déplacer");
        
        this.bTest = new Button ("Test");

        Scene sTemp = new Scene(bTest);
        
        sEnchere = new Stage();
        sEnchere.setScene(sTemp);
        sEnchere.setTitle("Mot de passe oublié ?");
        sEnchere.setResizable(false);
        sEnchere.show();
    }

}
