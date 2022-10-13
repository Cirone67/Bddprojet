
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
    private char designation;
    
//Constructor------------------------------   

    public SurCategorie(int idSurCategorie, ArrayList<Categorie> categorie, char designation) {
        this.idSurCategorie = idSurCategorie;
        this.designation = designation;
    }
//Get et Set-------------------------------

    public char getDesignation() {
        return designation;
    }

    public void setDesignation(char designation) {
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
        return connectGeneralPostGres("localhost", 5439, "postgres", "postgres", "pass");
    }
//Creer la table SurCategorie-----------------------------------------------
        public static void creeTableSurCategorie(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            st.executeUpdate(
                    """
                    create table SurCategorie (
                        idSurCategorie integer not null unique primary key, 
                        designation varchar(30) not null unique,
                    )
                    """);
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
    public static void createCategorie(Connection con, int idSurCategorie, String designation)
            throws SQLException, idCategorieExisteDejaException {
        // je me place dans une transaction pour m'assurer que la sÃ©quence
        // test du nom - crÃ©ation est bien atomique et isolÃ©e
        con.setAutoCommit(false);
        try ( PreparedStatement chercheEmail = con.prepareStatement(
                "select idSurCategorie from surCategorie where idSurCategorie = ?")) {
            chercheEmail.setInt(1, idSurCategorie);
            ResultSet testEmail = chercheEmail.executeQuery();
            if (testEmail.next()) {
                throw new idCategorieExisteDejaException();
            }
            // lors de la creation du PreparedStatement, il faut que je prÃ©cise
            // que je veux qu'il conserve les clÃ©s gÃ©nÃ©rÃ©es
            try ( PreparedStatement pst = con.prepareStatement(
                    """
                insert into surCategorie (idSurCategorie,designation) values (?,?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pst.setInt(1, idSurCategorie);
                pst.setString(2, designation);
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
      public static List<Categorie> tousLesUtilisateurs(Connection con) throws SQLException {
        List<Categorie> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                """
               select idSurCategorie,designation
                 from surCategorie
               """
        )) {
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Categorie(rs.getInt("idSurCategorie")
                            ,rs.getString("designation")));          
                }
                return res;
            }
        }
    }
    
}
