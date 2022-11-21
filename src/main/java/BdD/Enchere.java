package BdD;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

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
    private double prixIni;
    private double prix;
    private Date dateDebut;
    private Date dateFin; 
    private String acheteur;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//Constructors

    public Enchere(int idArticle, double prixIni, double prix, Date dateDebut, Date dateFin, String acheteur) {
        this.idArticle = idArticle;
        this.prixIni = prixIni;
        this.prix = prix;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
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
        try {
            return connectGeneralPostGres("localhost", 5439, "postgres", "postgres", "pass");
        } catch (SQLException ex) {
            return connectGeneralPostGres("localhost", 5432, "postgres", "postgres", "pass");
        }
    }
    // Creer Table Enchere   

    public static void creeEnchere(Connection con)
            throws SQLException {
        // je veux que le schema soit entierement cr�� ou pas du tout
        // je vais donc g�rer explicitement une transaction
        con.setAutoCommit(false);
        try (Statement st = con.createStatement()) {
            // creation des tables
            st.executeUpdate(
                    """
                    create table Enchere (
                        idArticle integer not null primary key,
                        vendeur varchar(30) not null,
                        prixIni integer not null,
                        prix integer,
                        dateDebut date not null,
                        dateFin date not null,
                        acheteur varchar(30)
                    )
                    """);
//                        st.executeUpdate(
//                    """
//                    alter table Enchere(
//                        add constraint fk_enchere_idArticle,
//                        foreign key (idArticle) references Article(idArticle)
//                    )
//                    """);
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

    //Créer la table des listes de posseseurs sucessif pour une enchère donnée.
    public static void creeTableListPosseseur(Connection con, int idArticle) throws SQLException {
        con.setAutoCommit(false);
        try (Statement st = con.createStatement()) {
            st.executeUpdate(
                    """
                    create ListPosseseur (
                        idArticle integer not null,
                        encherreur varchar(30)
                    )
                    """);
            con.commit();
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }
    //Permet de vider cette table de facon à alléger

    public static void UpdateDeleteListPosseseur(Connection con, int idArticle) throws SQLException {
        con.setAutoCommit(false);
        try (PreparedStatement pst = con.prepareStatement(
                """
                delete from ListPosseseur
                join Enchere on enchere.idArticle = ListPosseseur.idArticle
                where dateFin < ?
                """)) {
            pst.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            pst.executeUpdate();
            con.commit();
        }
        con.setAutoCommit(true);
    }

    //Pour se souvenir de l'enchérissement (Affichage)
    public static void UpdateListPosseseur(Connection con, int idArticle, String acheteur) throws SQLException {
        con.setAutoCommit(false);
        try (PreparedStatement pst = con.prepareStatement(
                """
                insert into ListPosseseur (idArticle,encherreur) values (?,?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, idArticle);
            pst.setString(2, acheteur);
            pst.executeUpdate();
            con.commit();
        } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }

    // creer une enchère
    public static void createEnchere(Connection con, int idArticle, String vendeur, double prixIni, double prix, Date dateDebut, Date dateFin, String acheteur)
            throws SQLException, EnchereExisteDejaException {
        // je me place dans une transaction pour m'assurer que la sÃ©quence
        // test du nom - crÃ©ation est bien atomique et isolÃ©e
        con.setAutoCommit(false);
        try (PreparedStatement chercheEmail = con.prepareStatement(
                "select idArticle from Enchere where idArticle = ?")) {
            chercheEmail.setInt(1, idArticle);
            ResultSet testEmail = chercheEmail.executeQuery();
            if (testEmail.next()) {
                throw new EnchereExisteDejaException();
            }
            // lors de la creation du PreparedStatement, il faut que je prÃ©cise
            // que je veux qu'il conserve les clÃ©s gÃ©nÃ©rÃ©es
            try (PreparedStatement pst = con.prepareStatement(
                    """
                insert into Enchere (idArticle,vendeur,prixIni,prix,dateDebut,dateFin,acheteur) values (?,?,?,?,?,?,?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pst.setInt(1, idArticle);
                pst.setString(2, vendeur);
                pst.setDouble(3, prixIni);
                pst.setDouble(4, prix);
                pst.setDate(5, dateDebut);
                pst.setDate(6, dateFin);
                pst.setString(7, acheteur);
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
//    public void demandeNouvelEnchere(Connection con,String desititre, String desietat,Date dateDebut,Date dateFin) throws SQLException, EnchereExisteDejaException {
//        boolean existe = true;
//        while (existe) {
//            int idArticle1 = Article.nomconnecttodesignation(con, desititre);
//            String vendeur = Lire.S();
//            int prixIni = Lire.i(); //Donner par l'utilisateur
//            int prix = prixIni;// auto
//            Date dateDebut = ;//Donner par l'utilisateur (format à transformer si nécessaire)
//            Date dateFin = ; //Donner par l'utilisateur
//            int etat = idStatuConnectToDesignation(con, desietat);
//            String acheteur = vendeur; //auto
//            try {
//                createEnchere(con, idArticle1,  vendeur,  prixIni, prix,  dateDebut,  dateFin,  etat, acheteur );
//                existe = false;
//            } catch (EnchereExisteDejaException ex) {
//                System.out.println("Enchere existe deja");
//            }
//        }
//    }

    //Table qui permet d'associer l'etat et sa désignation et (((faire la statistique du nombre d'enchère ouverte ( restreinit à l'admin).))))
    public static void creeAssocEtat(Connection con)
            throws SQLException {
        // je veux que le schema soit entierement cr�� ou pas du tout
        // je vais donc g�rer explicitement une transaction
        con.setAutoCommit(false);
        try (Statement st = con.createStatement()) {
            // creation des tables
            st.executeUpdate(
                    """
                    create table Etat (
                        idEtat interger not null,
                        designation varchar(30)
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

    public static void createEtat(Connection con) throws SQLException {
        con.setAutoCommit(false);
        try (PreparedStatement pst = con.prepareStatement(
                """
                insert into Etat (idEtat,designation) values (0,ouverte),
                insert into Etat (idEtat,designation) values (1,ferme)   
                """)) {
            pst.executeUpdate();
            con.commit();
        } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }

    // Fonction qui associe le statut 
    public static int idStatuConnectToDesignation(Connection con, String designation) throws SQLException {
        int res;
        try (PreparedStatement pst = con.prepareStatement(
                """
               select idEtat from Etat where designation = ?
               """
        )) {
            pst.setString(1, designation);
            pst.executeUpdate();
            try (ResultSet rs = pst.executeQuery()) {
                //while (rs.next()) {
                res = rs.getInt("idEtat");
                //} 
                return res;
            }
        }
    }

    public static class EnchereExisteDejaException extends Exception {
    }
    //Supprimer une enchère

//    public static void deleteSchema(Connection con) throws SQLException {
//        try (Statement st = con.createStatement()) {
//            // pour Ãªtre sÃ»r de pouvoir supprimer, il faut d'abord supprimer les liens
//            // puis les tables
//            // suppression des liens
//            try {
//                st.executeUpdate(
//                        """
//                    alter table Enchere
//                        drop constraint fk_aime_u1
//                             """);
//                System.out.println("constraint fk_aime_u1 dropped");
//            } catch (SQLException ex) {
//                // nothing to do : maybe the constraint was not created
//            }
//            try {
//                st.executeUpdate(
//                        """
//                    alter table aime
//                        drop constraint fk_aime_u2
//                    """);
//                System.out.println("constraint fk_aime_u2 dropped");
//            } catch (SQLException ex) {
//                // nothing to do : maybe the constraint was not created
//            }
//            // je peux maintenant supprimer les tables
//            try {
//                st.executeUpdate(
//                        """
//                    drop table utilisateur1
//                    """);
//                System.out.println("dable aime dropped");
//            } catch (SQLException ex) {
//                // nothing to do : maybe the table was not created
//            }
//            try {
//                st.executeUpdate(
//                        """
//                    drop table utilisateur
//                    """);
//                System.out.println("table utilisateur dropped");
//            } catch (SQLException ex) {
//                // nothing to do : maybe the table was not created
//            }
//        }
//    }
    //Get et Set
    public double getPrix() {
        return prix;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public double getPrixIni() {
        return prixIni;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public String getAcheteur() {
        return acheteur;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public void setPrixIni(double prixIni) {
        this.prixIni = prixIni;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }


    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setAcheteur(String acheteur) {
        this.acheteur = acheteur;
    }

    //Main
    public static void main(String[] args) {
        try (Connection con = defautConnect()) {
            System.out.println("connect� !!!");

            //deleteSchema(con);
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    //Permet au vendeur de modifier l'etat de l'enchère.  
    public void encherir(Connection con, Utilisateur encherreur, double prixPropose) throws SQLException {
        Enchere nouvelle = new Enchere(this.getIdArticle(), this.getPrixIni(), this.getPrix(), this.getDateDebut(), this.getDateFin(), this.getAcheteur());
        if (java.sql.Date.valueOf(LocalDate.now()).before(this.getDateFin()) && java.sql.Date.valueOf(LocalDate.now()).after(this.getDateDebut())) {
            if (prixPropose > nouvelle.prix && prixPropose > nouvelle.prixIni) {
                System.out.println(0);
                nouvelle.prix = prixPropose;
                nouvelle.acheteur = encherreur.getEmail();
                con.setAutoCommit(false);
                try (PreparedStatement pst = con.prepareStatement(
                        """
                update Enchere set prix = ?, acheteur = ? where idArticle = ?
                """)) {
                    pst.setDouble(1, nouvelle.prix);
                    pst.setString(2, nouvelle.acheteur);
                    pst.setInt(3, nouvelle.idArticle);
                    pst.executeUpdate();
                    con.commit();
                }
                con.setAutoCommit(true);
                UpdateListPosseseur(con, nouvelle.idArticle, nouvelle.acheteur);
            }
        }
    }
}
