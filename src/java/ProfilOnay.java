
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

@ManagedBean(name = "profilOnay")

public class ProfilOnay {
    private String name,surname,username,mail,password,repass;
   private  DataSource dataSource;
    public ProfilOnay() {
         try {
           Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("jdbc/webmusic");
        } catch (NamingException e) {
             e.printStackTrace();
        }  
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepass() {
        return repass;
    }

    public void setRepass(String repass) {
        this.repass = repass;
    }
    public String güncelle()throws SQLException{
         if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        
        Connection c=dataSource.getConnection();
        if (c==null) {
            throw new SQLException( "Unable to connect to DataSource" );
        }
   
            
         try {
              if(repass.equals(password)){
            PreparedStatement add=c.prepareStatement("UPDATE  UYELER SET PASSWORD=?"                  
                    + "WHERE NAME=? AND SURNAME=? AND EMAIL=? and  USERNAME=?");

            add.setString(2, getName());
            add.setString(3, getSurname());
            add.setString(4,getMail());
            add.setString(5, username);
         
            add.setString(1, getPassword());
            add.executeUpdate();
            return "anasayfa";
            }else{
              
         FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Please check your information",
		 					"Please enter correct username and Password"));
         return "profil";
    }
            
        } catch (Exception e) {
            return "profil";
        }finally{
            c.close();
        }
    
    }
    
    
}
