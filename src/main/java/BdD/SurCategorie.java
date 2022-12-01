package BdD;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brenc
 */
public class SurCategorie {
    private int idSurCategorie;
    private String designation;
    
//Constructor------------------------------   

    public SurCategorie(int idSurCategorie, String designation) {
        this.idSurCategorie = idSurCategorie;
        this.designation = designation;
    }
//Get et Set-------------------------------

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getIdSurCategorie() {
        return idSurCategorie;
    }


    public void setIdSurCategorie(int idSurCategorie) {
        this.idSurCategorie = idSurCategorie;
    }


//Lien avec PGSQL------------------------------------------------
        public static Connection connectGeneralPostGres(String host, int port, String database, String user, String pass) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://" + host + ":" + port
                        + "/" + database,
                user, pass);
        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        return con;
    }
   
    public static Connection defautConnect()
            throws ClassNotFoundException, SQLException {
        try{
        return connectGeneralPostGres("localhost", 5439, "postgres", "postgres", "pass");
        }catch(SQLException ex){
        return connectGeneralPostGres("localhost", 5432, "postgres", "postgres", "pass"); 
        }
    }
//Creer la table SurCategorie-----------------------------------------------
        public static void creeTableSurCategorie(Connection con)
            throws SQLException {
            System.out.println("dedans");
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            st.executeUpdate(
                    """
                    create table SurCategorie (
                        idSurCategorie serial primary key, 
                        designation varchar(30) not null unique
                    )
                    """);
//                    try ( Statement st2 = con.createStatement()) {
//            st.executeUpdate(
//                    """
//                    create table SurCategorie_Categorie (
//                        idSurCategorie integer not null, 
//                        idCategorie integer not null
//                    )
//                    """);
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }
               
  //Créer une surCategorie------------------------------------
    public static void createSurCategorie(Connection con, String designation)
            throws SQLException, idCategorieExisteDejaException {
        // je me place dans une transaction pour m'assurer que la sÃ©quence
        // test du nom - crÃ©ation est bien atomique et isolÃ©e
        con.setAutoCommit(false);
        try ( PreparedStatement chercheEmail = con.prepareStatement(
               "select designation from surCategorie where designation = ?")) {
                chercheEmail.setString(1, designation);
                ResultSet testEmail = chercheEmail.executeQuery();
                if (testEmail.next()) {
                throw new idCategorieExisteDejaException();
            }
            // lors de la creation du PreparedStatement, il faut que je prÃ©cise
            // que je veux qu'il conserve les clÃ©s gÃ©nÃ©rÃ©es
            try ( PreparedStatement pst = con.prepareStatement(
                    """
                insert into surCategorie (designation) values (?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, designation);
                pst.executeUpdate();
                con.commit();
            }
        } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }
        public static class idCategorieExisteDejaException extends Exception {
    }
  //Renvoie la liste des Surcatégories:
      public static List<SurCategorie> tousLesSurCategorie(Connection con) throws SQLException {
        List<SurCategorie> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                """
               select idSurCategorie,designation
                 from surCategorie
               """
        )) {
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new SurCategorie(rs.getInt("idSurCategorie")
                            ,rs.getString("designation")));          
                }
                return res;
            }
        }
    }
    
}
