/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.scene.layout.BorderPane;

/**
 *
 * @author drumm
 */
public class MainPane extends BorderPane {
    
    private Controleur controleur;
    
    public MainPane () {
        
        this.controleur = new Controleur(this);
    }

    public Controleur getControleur() {
        return controleur;
    }

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }
}
