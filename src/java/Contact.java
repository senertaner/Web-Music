import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import javax.annotation.Resource;
 import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
 import javax.sql.DataSource;
 import javax.sql.rowset.CachedRowSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alica
 */
@ManagedBean(name="contacts")
public class Contact {
    String name, surname,eMail;
    String Yorum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getYorum() {
        return Yorum;
    }

    public void setYorum(String Yorum) {
        this.Yorum = Yorum;
    }
CachedRowSet rowSet=null;

   
DataSource dataSource;
    public Contact() {
        try {
           Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("jdbc/webmusic");
        } catch (NamingException e) {
             e.printStackTrace();
        }  
    }
    public String Add() throws SQLException{
    
        if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        
        Connection c=dataSource.getConnection();
        if (c==null) {
            throw new SQLException( "Unable to connect to DataSource" );
        }
        try {
            PreparedStatement add=c.prepareStatement("INSERT INTO CONTACT"
                    + "(NAME,SURNAME,EMAIL,COMMENTS)"+
	"VALUES ( ?, ?, ?, ?)");

            add.setString(1, getName());
            add.setString(2, getSurname());
            add.setString(3,getEMail());
         
            add.setString(4, getYorum());
            add.executeUpdate();
            return "anasayfa";
            
        } catch (Exception e) {
            return "anasayfa";
        }finally{
            c.close();
        }
    }
    
    public String Del() throws SQLException{
        if ( dataSource == null ){
            throw new SQLException( "Unable to obtain DataSource" );
        }
        Connection c=dataSource.getConnection();
        if (c==null) {
            throw new SQLException( "Unable to connect to DataSource" );
        }
        try {
            PreparedStatement del=c.prepareStatement("delete from CONTACT " +
  "where username=?");
            del.setString(1,getName());
            return "anasayfa";
        } catch (Exception e) {
            return "anasayfa";
        }finally{
             c.close();
        }

    }
    
   
}
