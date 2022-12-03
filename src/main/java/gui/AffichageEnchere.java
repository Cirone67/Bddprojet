/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static BdD.Utilisateur.defautConnect;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private Button bValiderEnchere;

    public AffichageEnchere(PageAccueil vue) {
        this.vue = vue;
    }

    public void fenetreEnchere() {

        this.lTitreFenetre = new Label("Créer une enchère");
        this.lNom = new Label("Nom de l'objet : ");
        this.tfNom = new TextField();
        this.lPrixInitial = new Label("Prix Initial : ");
        this.tfPrixInitial = new TextField();
        this.lDateDebut = new Label("Date de début : ");
        this.dpDateDebut = new DatePicker();
        this.lDateFin = new Label("Date de fin : ");
        this.dpDateFin = new DatePicker();
        this.lDescriptionCourte = new Label("Description courte : ");
        this.tfDescriptionCourte = new TextField();
        this.lDescriptionLongue = new Label("Description longue : ");
        this.tfDescriptionLongue = new TextField();
        this.lMoyenExpedition = new Label("Mode d'envoi : ");
        this.rbEnvoiPostal = new RadioButton("Envoi Postal");
        this.rbVenirChercher = new RadioButton("Se déplacer");
        this.bValiderEnchere = new Button("Valider");

        this.bTest = new Button("Test");

        ToggleGroup tgModeEnvoi = new ToggleGroup();
        this.rbEnvoiPostal.setToggleGroup(tgModeEnvoi);
        this.rbVenirChercher.setToggleGroup(tgModeEnvoi);

        VBox vbTitre = new VBox(this.lTitreFenetre);
        HBox hbNom = new HBox(this.lNom, this.tfNom);
        HBox hbPrix = new HBox(this.lPrixInitial, this.tfPrixInitial);
        HBox hbDateDebut = new HBox(this.lDateDebut, this.dpDateDebut);
        HBox hbDateFin = new HBox(this.lDateFin, this.dpDateFin);
        HBox hbDescriptionCoute = new HBox(this.lDescriptionCourte, this.tfDescriptionCourte);
        HBox hbDescriptionLongue = new HBox(this.lDescriptionLongue, this.tfDescriptionLongue);
        HBox hbMoyenExpedition = new HBox(this.lMoyenExpedition, this.rbEnvoiPostal, this.rbVenirChercher);
        VBox vbBoutonValider = new VBox (this.bValiderEnchere);

        vbBoutonValider.setAlignment(Pos.CENTER);
        vbTitre.setAlignment(Pos.CENTER);

        VBox vbEnsembleFenetre = new VBox(vbTitre, hbNom, hbPrix, hbDateDebut, hbDateFin, hbDescriptionCoute, hbDescriptionLongue, hbMoyenExpedition, vbBoutonValider);

        Scene sTemp = new Scene(vbEnsembleFenetre);

        sEnchere = new Stage();
        sEnchere.setScene(sTemp);
        sEnchere.setTitle("Mot de passe oublié ?");
        sEnchere.show();

        bValiderEnchere.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                String sNom = tfNom.getText();
                double dPrixInitial = getDoubleFromTextField(tfPrixInitial);
                LocalDate dDateDebut = dpDateDebut.getValue();
                dDateDebut.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//                dpDateDebut.setOnAction(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//                        LocalDate date = dpDateDebut.getValue();
//                        String format = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//                        System.out.println(format);
//                    }
//                });
                System.out.println(dDateDebut);
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });
    }

    public static double getDoubleFromTextField(TextField textField) {
        String text = textField.getText();
        return Double.parseDouble(text);
    }

}
