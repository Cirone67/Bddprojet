/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BdD;

import gui.PageAccueil;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

/**
 *
 * @author drumm
 */
public class CreationEnchere extends BorderPane {

    private PageAccueil vue;
    private Stage sFenetreCreation;
    private Label lPrixInitial;
    private TextField tfPrixInitial;
    private Label lDateDeDebut;
    private DatePicker dpDateDebut;
    private Label lDateDeFin;
    private DatePicker dpDateDeFin;
    private Label lNomObjet;
    private TextField tfNomObjet;
    private Label lDescriptionCourte;
    private TextField tfDescriptionCourte;
    private Label lDescriptionLongue;
    private TextField tfDescriptionLongue;
    private Label lModeEnvoi;
    private RadioButton rbEnvoiPostal;
    private RadioButton rbEnvoiParDeplacement;

    public CreationEnchere(PageAccueil vue) {
        this.vue = vue;
    }

    public void AfficheCreaEnchere(MouseEvent t) {
        
    }
}
