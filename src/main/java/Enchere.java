
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brenc
 */
public class Enchere {
    private int idArticle;
    private String vendeur;
    private double prixIni;
    private double prix;
    private double dateDebut;
    private double dateFin;
    private int etat;
    private String acheteur;

//Constructors
     public Enchere(int idArticle, String vendeur, double prixIni,double prix, double dateDebut, double dateFin, int etat, String acheteur) {    
        this.idArticle = idArticle;
        this.vendeur = vendeur;
        this.prixIni = prixIni;
        this.prix = prix;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.etat = etat;
        this.acheteur = acheteur;
    }

//Programmation Java

//Lier à la connection au serveur
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
    
    public static void creeSchema(Connection con)
            throws SQLException {
        // je veux que le schema soit entierement cr�� ou pas du tout
        // je vais donc g�rer explicitement une transaction
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            // creation des tables
            st.executeUpdate(
                    """
                    create table Enchere (
                        id integer not null primary key
                        generated always as identity,
                    -- ceci est un exemple de commentaire SQL :
                    -- un commentaire commence par deux tirets,
                    -- et fini � la fin de la ligne
                    -- cela me permet de signaler que le petit mot cl�
                    -- unique ci-dessous interdit deux valeurs semblables
                    -- dans la colonne des noms.
                        nom varchar(30) not null unique,
                        pass varchar(30) not null
                    )
                    """);
            // si j'arrive jusqu'ici, c'est que tout s'est bien pass�
            // je confirme (commit) la transaction
            con.commit();
            // je retourne dans le mode par d�faut de gestion des transaction :
            // chaque ordre au SGBD sera consid�r� comme une transaction ind�pendante
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            // quelque chose s'est mal pass�
            // j'annule la transaction
            con.rollback();
            // puis je renvoie l'exeption pour qu'elle puisse �ventuellement
            // �tre g�r�e (message � l'utilisateur...)
            throw ex;
        } finally {
            // je reviens � la gestion par d�faut : une transaction pour
            // chaque ordre SQL
            con.setAutoCommit(true);
        }
    }
    
       public static void deleteSchema(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            // pour Ãªtre sÃ»r de pouvoir supprimer, il faut d'abord supprimer les liens
            // puis les tables
            // suppression des liens
            try {
                st.executeUpdate(
                        """
                    alter table aime
                        drop constraint fk_aime_u1
                             """);
                System.out.println("constraint fk_aime_u1 dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
            }
            try {
                st.executeUpdate(
                        """
                    alter table aime
                        drop constraint fk_aime_u2
                    """);
                System.out.println("constraint fk_aime_u2 dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
            }
            // je peux maintenant supprimer les tables
            try {
                st.executeUpdate(
                        """
                    drop table utilisateur1
                    """);
                System.out.println("dable aime dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the table was not created
            }
            try {
                st.executeUpdate(
                        """
                    drop table utilisateur
                    """);
                System.out.println("table utilisateur dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the table was not created
            }
        }
    }

       //Get et Set

    public double getPrix() {
        return prix;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public String getVendeur() {
        return vendeur;
    }

    public double getPrixIni() {
        return prixIni;
    }

    public double getDateDebut() {
        return dateDebut;
    }

    public double getDateFin() {
        return dateFin;
    }

    public int getEtat() {
        return etat;
    }

    public String getAcheteur() {
        return acheteur;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public void setVendeur(String vendeur) {
        this.vendeur = vendeur;
    }

    public void setPrixIni(double prixIni) {
        this.prixIni = prixIni;
    }

    public void setDateDebut(double dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(double dateFin) {
        this.dateFin = dateFin;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setAcheteur(String acheteur) {
        this.acheteur = acheteur;
    }
 
 
    //Main
    public static void main (String[] args) {
        try ( Connection con = defautConnect()) {
            System.out.println("connect� !!!");
            //deleteSchema(con);
            //menu(con);
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }
            
}
    

