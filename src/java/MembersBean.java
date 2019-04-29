
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.RowSet;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alica
 */

@ManagedBean(name="membersBean")
@RequestScoped 
public class MembersBean {
    ArrayList<MembersBean> arrayList=new ArrayList<>();
    private String name,surname,eMail,password,username;
  
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<MembersBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<MembersBean> arrayList) {
        this.arrayList = arrayList;
    }
    
CachedRowSet rowSet=null;

   
DataSource dataSource;
    public MembersBean() {
        try {
           Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("jdbc/webmusic");
        } catch (NamingException e) {
             e.printStackTrace();
        }  
    }
    
    //  private String name,surname,eMail,password,username;
    public String Add() throws SQLException{
       if(password.equals("")||name.equals("")||eMail.equals("")||surname.equals("")||username.equals("")){
             FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Please fill all information",
		 					"Please enter correct username and Password"));
         return "uyeol";
        }else{
        if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        
        Connection c=dataSource.getConnection();
        if (c==null) {
            throw new SQLException( "Unable to connect to DataSource" );
        }
        try {
            PreparedStatement add=c.prepareStatement("INSERT INTO UYELER"
                    + "(NAME,SURNAME,USERNAME,EMAIL,PASSWORD)"+
	"VALUES ( ?, ?, ?, ?, ?)");

            add.setString(1, getName());
            add.setString(2, getSurname());
            add.setString(3,getUsername());
            add.setString(4, getEMail());
            add.setString(5, getPassword());
            add.executeUpdate();
            return "index";
            
        } catch (Exception e) {
            return "index";
        }finally{
            c.close();
        }}
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
            PreparedStatement del=c.prepareStatement("delete from members " +
  "where username=?");
            del.setString(1, getUsername());
            return "index";
        } catch (Exception e) {
            return "index";
        }finally{
             c.close();
        }

    }
    public static String getUserName() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		return session.getAttribute("username").toString();
	}
    public String loginControl() {
                
		boolean valid = Login.validate(username, password);
		if (valid) {
                    HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                    session.setAttribute("username", username);
                       
                                
			return "anasayfa";
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username and Passowrd",
							"Please enter correct username and Password"));
			return "uyegirisi";
		}
	}


 private String musicname,type,artist,numberoflisten;

    public String getMusicname() {
        return musicname;
    }

    public void setMusicname(String musicname) {
        this.musicname = musicname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getNumberoflisten() {
        return numberoflisten;
    }

    public void setNumberoflisten(String numberoflisten) {
        this.numberoflisten = numberoflisten;
    }
    
    private CachedRowSet rowSet2=null;
    private DataSource dataSource2;
  
        private CachedRowSet rowSet3=null;
    private DataSource dataSource3;
    
       private DataSource dataSource4;
    
    public ResultSet HitListele() throws SQLException {
        try {
           Context ctx = new InitialContext();
			dataSource2 = (DataSource)ctx.lookup("jdbc/webmusic");
        } catch (NamingException e) {
             e.printStackTrace();
        }  
        
         if (dataSource2 == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        Connection c = dataSource2.getConnection();
        if (c == null) {
            throw new SQLException("Unable to connect to DataSource");
        }
        try {
            PreparedStatement add = c.prepareStatement("Select * from MUSICS ORDER BY NUMBEROFLISTEN DESC");
            rowSet2=new com.sun.rowset.CachedRowSetImpl();
            rowSet2.populate(add.executeQuery());
            
            return rowSet2;
        } finally {
            
            c.close();
        }
     
}
    DataSource ds;
      private CachedRowSet rs=null;
 public ResultSet SanatciListele() throws SQLException {
        try {
           Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("jdbc/webmusic");
        } catch (NamingException e) {
             e.printStackTrace();
        }  
        
         if (ds == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        Connection c = ds.getConnection();
        if (c == null) {
            throw new SQLException("Unable to connect to DataSource");
        }
        try {
            PreparedStatement add = c.prepareStatement("Select Distinct ARTIST from MUSICS");
            rs=new com.sun.rowset.CachedRowSetImpl();
            rs.populate(add.executeQuery());
            
            return rs;
        } finally {
            
            c.close();
        }
 }  
    
    
 public ResultSet KisiselListele() throws SQLException {
        try {
           Context ctx = new InitialContext();
			dataSource3 = (DataSource)ctx.lookup("jdbc/webmusic");
        } catch (NamingException e) {
             e.printStackTrace();
        }  
        
         if (dataSource3 == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        Connection c = dataSource3.getConnection();
        if (c == null) {
            throw new SQLException("Unable to connect to DataSource");
        }
        try {
            PreparedStatement add = c.prepareStatement("Select MUSICNAME from RELATIONUSERSTOMUSICS");
            rowSet3=new com.sun.rowset.CachedRowSetImpl();
            rowSet3.populate(add.executeQuery());
            
            return rowSet3;
        } finally {
            
            c.close();
        }
 }
       public String  AddToUserLikes() throws SQLException 
       {
         
           Map<String,String> params=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
           String name=params.get("name");
  
            if (dataSource == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        Connection c = dataSource.getConnection();
        if (c == null) {
            throw new SQLException("Unable to connect to DataSource");
        }
        try{
            
            PreparedStatement add = c.prepareStatement("INSERT INTO APP.RELATIONUSERSTOMUSICS (USERNAME,MUSICNAME) VALUES(?,?)");
            add.setString(1,getUserName());
            add.setString(2,name);
            add.executeUpdate();
        
        }
                finally {
            
            c.close();

        }
 return "anasayfa";
       }
        
       public String  DelToUserLikes() throws SQLException 
       {
            try {
           Context ctx = new InitialContext();
			dataSource4 = (DataSource)ctx.lookup("jdbc/webmusic");
        } catch (NamingException e) {
             e.printStackTrace();
        }  
        
           Map<String,String> params=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
           String name2=params.get("name2");
  
            if (dataSource4 == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        Connection c = dataSource4.getConnection();
        if (c == null) {
            throw new SQLException("Unable to connect to DataSource");
        }
        try{
            
            PreparedStatement add = c.prepareStatement("DELETE FROM RELATIONUSERSTOMUSICS WHERE USERNAME=? AND MUSICNAME=?");
            add.setString(1,getUserName());
            add.setString(2,name2);
            add.executeUpdate();
        
        }
                finally {
            
            c.close();

        }
 return "anasayfa";
       }
        ResultSet rowSet_music = null;

    DataSource dataSource_music;
       
  public String Listele() throws SQLException {
        ArrayList arr=new ArrayList();
        String a="";
          try {
            Context ctx = new InitialContext();
            dataSource_music = (DataSource) ctx.lookup("jdbc/webmusic");
        } catch (NamingException e) {
            e.printStackTrace();
        }
         if (dataSource_music == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

                
        Connection c = dataSource_music.getConnection();
        if (c == null) {
            throw new SQLException("Unable to connect to DataSource");
        }
        try {
            PreparedStatement add = c.prepareStatement("Select A.PATH from MUSICS A,"
                    + "RELATIONUSERSTOMUSICS B, UYELER C WHERE " +
                    "A.NAME=B.MUSICNAME AND C.USERNAME=B.USERNAME AND C.USERNAME=?");
            add.setString(1, getUserName());
            rowSet_music=add.executeQuery();
            while (rowSet_music.next()) {
                arr.add(rowSet_music.getString("PATH"));
            }

        } catch (Exception e) {
           return a;
        } finally {
                      c.close();
        }
        for (int i = 0; i < arr.size(); i++) {
            if((arr.size())-1==i){
               a=a+arr.get(i);
            }else{
                a=a+arr.get(i)+"|";
            }
            
            
        }
        
        return a;

}
}
