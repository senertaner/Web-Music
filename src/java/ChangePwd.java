
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alica
 */
@ManagedBean
public class ChangePwd {
    private String name, surname,password,Email;
   
    private DataSource dataSource;
    public ChangePwd() {
          try {
           Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("jdbc/webmusic");
        } catch (NamingException e) {
             e.printStackTrace();
        }  
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String change()throws SQLException{
        if(password.equals("")||name.equals("")||Email.equals("")||surname.equals("")){
             FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Please fill all information",
		 					"Please enter correct username and Password"));
         return "sifremiUnuttum";
        }else{
        if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        
        Connection c=dataSource.getConnection();
        if (c==null) {
            throw new SQLException( "Unable to connect to DataSource" );
        }
        try {
            PreparedStatement add=c.prepareStatement("UPDATE  UYELER SET PASSWORD=?"                  
                    + "WHERE NAME=? AND SURNAME=? AND EMAIL=?");

            add.setString(2, getName());
            add.setString(3, getSurname());
            add.setString(4,getEmail());
         
            add.setString(1, getPassword());
            add.executeUpdate();
            return "index";
            
        } catch (Exception e) {
            return "index";
        }finally{
            c.close();
        }
    }
    }
}
