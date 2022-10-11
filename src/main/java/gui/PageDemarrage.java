/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author drumm
 */

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PageDemarrage extends BorderPane{
    
    private Label lIdentifiant;
    private TextField tfIdentifiant;
    private Label lMDP;
    private PasswordField pfMDP;
    private Hyperlink hlMDPOublié;
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
    private TextField tfRechercher;
    private Button bSeConnecter;
    private BorderPane bpEcranPrincipal;
    
    public void start(Stage primaryStage) throws Exception {
        
    }
    
}
