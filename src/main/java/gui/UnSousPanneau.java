/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 *
 * @author brenc
 */
public class UnSousPanneau extends VBox{
    
    private PageAccueil main;
    
    public UnSousPanneau(PageAccueil main) {
        this.main = main;
        Button bb = new Button("coucou");
        bb.setOnAction((t) -> {
            int curU = this.main.getUtilisateurCourant();
        });
        
    }
    
}
