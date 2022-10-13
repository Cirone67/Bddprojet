
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
public class Categorie {

private int idcategorie;
private String designation;
private int idSurCategorie;


//Contructor-----------------------------------------------
    public Categorie(int idcategorie, String designation) {
        this.idcategorie = idcategorie;
        this.designation = designation;
    }

//Set et Get-----------------------------------------
    public int getIdcategorie() {
        return idcategorie;
    }

    public String getDesignation() {
        return designation;
    }

    public void setIdcategorie(int idcategorie) {
        this.idcategorie = idcategorie;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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
//Creer la table Categorie-----------------------------------------------
        public static void creeTableCategorie(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            st.executeUpdate(
                    """
                    create table Categorie (
                        idCategorie integer  not null unique primary key,
                        designation varchar(30) not null unique,
                        idSurCategorie integer
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
//Créer une categorie------------------------------------
    public static void createCategorie(Connection con, int idCategorie, String designation, int idSurCategorie )
            throws SQLException, idCategorieExisteDejaException {
        // je me place dans une transaction pour m'assurer que la sÃ©quence
        // test du nom - crÃ©ation est bien atomique et isolÃ©e
        con.setAutoCommit(false);
        try ( PreparedStatement chercheEmail = con.prepareStatement(
                "select idCategorie from Categorie where idCategorie = ?")) {
            chercheEmail.setInt(1, idCategorie);
            ResultSet testEmail = chercheEmail.executeQuery();
            if (testEmail.next()) {
                throw new idCategorieExisteDejaException();
            }
            // lors de la creation du PreparedStatement, il faut que je prÃ©cise
            // que je veux qu'il conserve les clÃ©s gÃ©nÃ©rÃ©es
            try ( PreparedStatement pst = con.prepareStatement(
                    """
                insert into categorie (idCategorie,designation,idSurCategorie) values (?,?,?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pst.setInt(1, idCategorie);
                pst.setString(2, designation);
                pst.setInt(3, idSurCategorie);
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
  //Renvoie la liste des catégories:
      public static List<Categorie> tousLesCategorie(Connection con) throws SQLException {
        List<Categorie> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                """
               select idCategorie,designation
                 from Categorie
               """
        )) {
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Categorie(rs.getInt("idCategorie")
                            ,rs.getString("designation")));          
                }
                return res;
            }
        }
    }
      
          public static void main(String[] args) {
        try ( Connection con = defautConnect()) {
            System.out.println("connectÃ© !!!");
           //creeTableCategorie(con);
           //createCategorie(con,4568,"bob",5);
           //afficheTousLesUtilisateur(con);
           //deleteSchemaUtilisateur(con);
           //System.out.println(tousLesCategorie(con).get(0).getDesignation());
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }
}
