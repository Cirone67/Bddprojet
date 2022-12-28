/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import BdD.Enchere;
import static BdD.Utilisateur.defautConnect;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
public class CreerEnchere extends BorderPane {

    private PageAccueil vue;
    private Stage sEnchere;
    private Button bTest;
    private Enchere creerEnchere;

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
    private TextField tfURLPhoto;
    private Label lURLPhoto;
    private RadioButton rbEnvoiPostal;
    private RadioButton rbVenirChercher;
    private Label lTitreFenetre;
    private Button bValiderEnchere;
    private CheckBox cbMultimdia;
    private CheckBox cbMaisonEtJardin;
    private CheckBox cbJouetsEtJeux;
    private CheckBox cbCultureEtLoisirs;
    private CheckBox cbAutoEtMoto;
    private CheckBox cbReconditionne;
    private Label lCategorie;

    public CreerEnchere(PageAccueil vue) {
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
        this.lURLPhoto = new Label("URL Photo : ");
        this.tfURLPhoto = new TextField();
        this.lMoyenExpedition = new Label("Mode d'envoi : ");
        this.rbEnvoiPostal = new RadioButton("Envoi Postal");
        this.rbVenirChercher = new RadioButton("Se déplacer");
        this.bValiderEnchere = new Button("Valider");
        this.cbMultimdia = new CheckBox("Multimédia");
        this.cbMaisonEtJardin = new CheckBox("Maison et Jardin");
        this.cbJouetsEtJeux = new CheckBox("Jouets et Jeux");
        this.cbCultureEtLoisirs = new CheckBox("Culture et Loisirs");
        this.cbAutoEtMoto = new CheckBox("Auto et Moto");
        this.cbReconditionne = new CheckBox("Reconditionné");
        this.lCategorie = new Label("Choisir la catégorie correspondante");

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
        HBox hbURLPhoto = new HBox(this.lURLPhoto,this.tfURLPhoto);
        HBox hbMoyenExpedition = new HBox(this.lMoyenExpedition, this.rbEnvoiPostal, this.rbVenirChercher);
        VBox vbBoutonValider = new VBox(this.bValiderEnchere);
        VBox vbAppelCategotie = new VBox(this.lCategorie);
        HBox hbCategorieEtage1 = new HBox(this.cbMultimdia, this.cbMaisonEtJardin, this.cbJouetsEtJeux);
        HBox hbCategorieEtage2 = new HBox(this.cbCultureEtLoisirs, this.cbAutoEtMoto, this.cbReconditionne);
        VBox vbCategorie = new VBox(hbCategorieEtage1, hbCategorieEtage2);

        vbBoutonValider.setAlignment(Pos.CENTER);
        vbTitre.setAlignment(Pos.CENTER);

        VBox vbEnsembleFenetre = new VBox(vbTitre, hbNom, hbPrix, hbDateDebut, hbDateFin, hbDescriptionCoute, hbDescriptionLongue,hbURLPhoto, hbMoyenExpedition, vbAppelCategotie, vbCategorie, vbBoutonValider);

        Scene sTemp = new Scene(vbEnsembleFenetre);

        sEnchere = new Stage();
        sEnchere.setScene(sTemp);
        sEnchere.setTitle("Créer une enchère");
        sEnchere.show();

        bValiderEnchere.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                String sNom = tfNom.getText();
                double dPrixInitial = getDoubleFromTextField(tfPrixInitial);
                LocalDate dDateDebut = dpDateDebut.getValue();
                LocalDate dDateFin = dpDateFin.getValue();
                String sDescriptionCourte = tfDescriptionCourte.getText();
                String sDescriptionLongue = tfDescriptionLongue.getText();
                String sURLPhoto = tfURLPhoto.getText();
                ArrayList<String> alRubriques = new ArrayList<>();
                int iModeEnvoi = 0;

                if (rbEnvoiPostal.isSelected()) {
                    iModeEnvoi = 1;
                }

                if (rbVenirChercher.isSelected()) {
                    iModeEnvoi = 2;
                }

                if (cbMultimdia.isSelected()) {
                    alRubriques.add("Multimédia");
                }
                if (cbMaisonEtJardin.isSelected()) {
                    alRubriques.add("Maison et Jardin");
                }
                if (cbJouetsEtJeux.isSelected()) {
                    alRubriques.add("Jouets et Jeux");
                }
                if (cbCultureEtLoisirs.isSelected()) {
                    alRubriques.add("Culture et Loisirs");
                }
                if (cbAutoEtMoto.isSelected()) {
                    alRubriques.add("Auto et Moto");
                }
                if (cbReconditionne.isSelected()) {
                    alRubriques.add("Reconditionné");
                }

                LocalDate ldDateDebut = dpDateDebut.getValue();
                String dDebut = ldDateDebut.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                SimpleDateFormat simpleDateFormatDebut = new SimpleDateFormat("yyyy-MM-dd");
                Date dDebutString = new java.sql.Date(simpleDateFormatDebut.parse(dDebut).getTime());
                
                LocalDate ldDateFin = dpDateFin.getValue();
                String dFin = ldDateFin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                SimpleDateFormat simpleDateFormatFin = new SimpleDateFormat("yyyy-MM-dd");
                Date dFinString = new java.sql.Date(simpleDateFormatFin.parse(dFin).getTime());

                creerEnchere.createElement(con, dPrixInitial, dDebutString, dFinString, vue.getUtilisateurCourant(), sNom, sDescriptionCourte, sDescriptionLongue,
                        iModeEnvoi, alRubriques,sURLPhoto);
                
                sEnchere.close();
                
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
