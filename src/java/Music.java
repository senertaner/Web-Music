
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alica
 */
@ManagedBean(name = "musics")
public class Music {
 
    String name, artist, type, numberOfListining;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumberOfListining() {
        return numberOfListining;
    }

    public void setNumberOfListining(String numberOfListining) {
        this.numberOfListining = numberOfListining;
    }
    CachedRowSet rowSet = null;

    DataSource dataSource;

    public Music() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/webmusic");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public String Add() throws SQLException {

        if (dataSource == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        Connection c = dataSource.getConnection();
        if (c == null) {
            throw new SQLException("Unable to connect to DataSource");
        }
        try {
            PreparedStatement add = c.prepareStatement("INSERT INTO MUSICS"
                    + "(NAME,ARTIST,TYPE,NUMBEROFLİSTEN)"
                    + "VALUES ( ?, ?, ?, ?)");

            add.setString(1, getName());
            add.setString(2, getArtist());
            add.setString(3, getType());
            add.setInt(4, Integer.parseInt(getType()));
            add.executeUpdate();
            return "index";

        } catch (Exception e) {
            return "index";
        } finally {
            c.close();
        }
    }

    public String Del() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("Unable to obtain DataSource");
        }
        Connection c = dataSource.getConnection();
        if (c == null) {
            throw new SQLException("Unable to connect to DataSource");
        }
        try {
            PreparedStatement del = c.prepareStatement("delete from MUSICS "
                    + "where NAME=?");
            del.setString(1, getName());
            return "index";
        } catch (Exception e) {
            return "index";
        } finally {
            c.close();
        }

    }
}
   