/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static BdD.Utilisateur.defautConnect;
import java.sql.Connection;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;

/**
 *
 * @author drumm
 */
public class InfoUtilisateur {

    private ObservableList<String> obToutesInfos;

    public InfoUtilisateur() {

    }

    public void afficherInfoUtilisateur(int utilisateurCourant) {
        try ( Connection con = defautConnect()) {
//            TableView tvInfoUtilisateur = new TableView();
//            this.obToutesInfos = FXCollections.observableArrayList(alEnchere);
//            this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

}
